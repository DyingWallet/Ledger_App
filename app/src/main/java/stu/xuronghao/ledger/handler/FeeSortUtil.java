package stu.xuronghao.ledger.handler;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import stu.xuronghao.ledger.entity.TotalFee;

public class FeeSortUtil {
    Comparator<TotalFee> feeComparatorByAmount = new Comparator<TotalFee>() {
        @Override
        public int compare(TotalFee item1, TotalFee item2) {
            if (item1.getFee() == item2.getFee()) return 0;
            else if (item1.getFee() < item2.getFee()) return 1;
            else return -1;
        }
    };

    public void sortByAmount(List<TotalFee> totalFees) {
        Collections.sort(totalFees, feeComparatorByAmount);
    }
}