package stu.xuronghao.ledger.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class SwitchAdapter extends FragmentPagerAdapter {

    private List<Fragment> pageList;

    public SwitchAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public SwitchAdapter(FragmentManager fragmentManager, List<Fragment> list) {
        super(fragmentManager);
        this.pageList = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return pageList.get(position);
    }

    @Override
    public int getCount() {
        return pageList.size();
    }
}
