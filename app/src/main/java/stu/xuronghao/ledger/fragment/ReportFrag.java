package stu.xuronghao.ledger.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.adapter.SwitchAdapter;

public class ReportFrag extends Fragment {
    // 参数声明
    private View rootView;
    private MenuItem menuItem;
    private BottomNavigationView navigationView;

    //声明子Fragment对象
    PieFrag pieFrag = new PieFrag();
    TrendFrag lineFrag = new TrendFrag();

    public ReportFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        if (getArguments() != null) {
        //        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_report, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        ViewPager viewPager = rootView.findViewById(R.id.vp_Report_Top_Switch);
        navigationView = rootView.findViewById(R.id.Report_Top_Switch);

        List<Fragment> pageList = new ArrayList<>();

        pageList.add(pieFrag);
        pageList.add(lineFrag);

        //设置导航条监听器
        navigationView.setOnNavigationItemSelectedListener(
                item -> {
                    menuItem = item;
                    switch (item.getItemId()) {
                        case R.id.pieFrag:
                            viewPager.setCurrentItem(0);
                            break;
                        case R.id.lineFrag:
                            viewPager.setCurrentItem(1);
                            break;
                    }
                    return false;
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