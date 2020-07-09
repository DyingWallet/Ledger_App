package stu.xuronghao.ledger.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.adapter.SwitchAdapter;


public class RecordFrag extends Fragment {
    // 参数声明
    private View                 rootView;
    private ViewPager            viewPager;
    private MenuItem             menuItem;
    private BottomNavigationView navigationView;

    //声明子Fragment对象
    CostFrag   costFrag   = new CostFrag();
    IncomeFrag incomeFrag = new IncomeFrag();

    public RecordFrag() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_record, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    //初始化ViewPager
    private void initView() {
        //        Log.w("initView!", "ViewPager Init!");
        viewPager = rootView.findViewById(R.id.vp_Record_Top_Switch);
        navigationView = rootView.findViewById(R.id.Record_Top_Switch);

        List<Fragment> pageList = new ArrayList<>();

        pageList.add(costFrag);
        pageList.add(incomeFrag);

        //设置导航条监听器
        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        menuItem = item;
                        switch (item.getItemId()) {
                            case R.id.costFrag:
                                viewPager.setCurrentItem(0);
                                //                                Log.w("00000", "" + item.getItemId());
                                break;
                            case R.id.incomeFrag:
                                viewPager.setCurrentItem(1);
                                //                                Log.w("11111", "" + item.getItemId());
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

        viewPager.setAdapter(new SwitchAdapter(getChildFragmentManager(), pageList));

    }
}