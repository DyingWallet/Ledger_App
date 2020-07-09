package stu.xuronghao.ledger.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.adapter.SwitchAdapter;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.fragment.PushCostFrag;
import stu.xuronghao.ledger.fragment.PushIncomeFrag;

public class PushDataPage extends AppCompatActivity {

    private final        int    Cost_Pusher   = 0;
    private final        int    Income_Pusher = 1;
    private static final String ARG_USER_INFO = "user";

    //参数声明
    private ViewPager            viewPager;
    private MenuItem             menuItem;
    private BottomNavigationView navigationView;
    private User                 user;

    //声明Fragment对象
    PushCostFrag   pushCostFrag   = new PushCostFrag();
    PushIncomeFrag pushIncomeFrag = new PushIncomeFrag();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_data_page);

        user = (User) getIntent().getSerializableExtra(ARG_USER_INFO);

        //初始化导航
        initView();

        int index = getIntent().getIntExtra("index", 0);
        Log.w("index is: ", "" + index);

        toView(index);


    }

    //初始化ViewPager
    private void initView() {
        Log.w("initView!", "ViewPager Init!");
        viewPager = findViewById(R.id.vp_Pusher);
        navigationView = findViewById(R.id.pusher_top_switch);

        List<Fragment> pageList = new ArrayList<>();

        pageList.add(pushCostFrag);
        pageList.add(pushIncomeFrag);

        //设置导航条监听器
        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        menuItem = item;
                        switch (item.getItemId()) {
                            case R.id.costPusher:
                                viewPager.setCurrentItem(Cost_Pusher);
                                Log.w("PushDataPage00000", "" + item.getItemId());
                                break;
                            case R.id.incomePusher:
                                viewPager.setCurrentItem(Income_Pusher);
                                Log.w("PushDataPage11111", "" + item.getItemId());
                                break;
                        }
                        return false;
                    }
                }
        );

        //设置ViewPager监听器

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    navigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = navigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(new SwitchAdapter(getSupportFragmentManager(), pageList));
    }

    private void toView(int index) {
        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            navigationView.getMenu().getItem(0).setChecked(false);
        }
        menuItem = navigationView.getMenu().getItem(index);
        menuItem.setChecked(true);
        viewPager.setCurrentItem(index);
    }
}