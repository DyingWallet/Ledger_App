package stu.xuronghao.ledger.handler;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import stu.xuronghao.ledger.entity.TotalFee;

public class ListSortUtil {
    Comparator<TotalFee> feeComparatorByAmount = (item1, item2) -> {
        if (item1.getFee().equals(item2.getFee())) return 0;
        else if (item1.getFee() < item2.getFee()) return 1;
        else return -1;
    };

    Comparator<Map.Entry<Integer,Integer>> weightTableSort = (item1,item2)->{
        //从大到小排序
        return item2.getValue() - item1.getValue();
    };

    public void sortByAmount(List<TotalFee> totalFees) {
        totalFees.sort(feeComparatorByAmount);
    }

    public void sortByWeight(List<Map.Entry<Integer,Integer>> weightTable){
        weightTable.sort(weightTableSort);
    }
}