package stu.xuronghao.ledger.handler;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringChecker {
    private StringChecker() {
    }

    //判断账目收支类型
    public static int costOrIncome(String result) {
        int weightVal = 0;
        List<Pair<String, Integer>> costTypeList = ConstantVariable.getCostTypeList();
        List<Pair<String, Integer>> incomeTypeList = ConstantVariable.getIncomeTypeList();

        for (Pair<String, Integer> cost : costTypeList) {
            if (result.contains(cost.first))
                weightVal -= cost.second;
        }
        for (Pair<String, Integer> income : incomeTypeList) {
            if (result.contains(income.first))
                weightVal += income.second;
        }
        if (weightVal == 0) return ConstantVariable.ERROR_CODE;
        else return weightVal < 0 ? ConstantVariable.COST_CODE : ConstantVariable.INCOME_CODE;
    }

    public static String costOrIncomeType(List<String> wordList, int typeCode) {
        //初始化权重表
        Map<Integer, Integer> weightTable = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            weightTable.put(i, 0);
        }

        String tmpStr;
        if (ConstantVariable.ERROR_CODE != typeCode) {
            for (String word : wordList) {
                tmpStr = ConstantVariable.getCostOrIncomeType(word, typeCode);
                int index = ConstantVariable.getTypeIndex(tmpStr, typeCode);
                //统计关键词出现权重
                if (4 != index)
                    weightTable.replace(index, weightTable.get(index) + ConstantVariable.NORMAL_TYPE_WEIGHT_FACTOR);
                else
                    weightTable.replace(index, weightTable.get(index) + ConstantVariable.OTHER_TYPE_WEIGHT_FACTOR);
            }
            List<Map.Entry<Integer, Integer>> sortList = new ArrayList<>(weightTable.entrySet());
            new ListSortUtil().sortByWeight(sortList);
            return ConstantVariable.getTypeByTypeCode(sortList.get(0).getKey(), typeCode);
        }
        return ConstantVariable.NULL_STR;
    }

    public static String checkDoubleValue(String moneyStr) {
        StringBuilder sb = new StringBuilder(moneyStr);
        if (moneyStr.contains(ConstantVariable.DOT_REGEX)) {
            String[] strArr = moneyStr.split(ConstantVariable.DOT_REGEX, -1);
            String last = strArr[strArr.length - 1];
            if (last.isEmpty()) {
                sb.append(ConstantVariable.ZERO_STR);
            }
        }
        return sb.toString();
    }
}