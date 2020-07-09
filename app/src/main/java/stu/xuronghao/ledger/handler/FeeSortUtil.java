package stu.xuronghao.ledger.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import stu.xuronghao.ledger.entity.TotalFee;

public class FeeSortUtil{
    Comparator<TotalFee> feeComparatorByAmount = new Comparator<TotalFee>() {
        @Override
        public int compare(TotalFee item1, TotalFee item2) {
            if(item1.getFee() < item2.getFee())
                return 1;
            return -1;
        }
    };

    public void sortByAmount(ArrayList<TotalFee> totalFees){
        Collections.sort(totalFees,feeComparatorByAmount);
    }
}
