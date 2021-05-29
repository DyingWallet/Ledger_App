package stu.xuronghao.ledger.handler.validator;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stu.xuronghao.ledger.handler.ListSortUtil;
import stu.xuronghao.ledger.handler.consts.ConstantVariable;
import stu.xuronghao.ledger.handler.consts.WeightValue;

public class StringChecker {
    private StringChecker() {
    }

    //判断账目收入、支出类型
    public static int getCostOrIncomeCode(String result) {
        int weightVal = 0;
        List<Pair<String, Integer>> costTypeList = ConstantVariable.getCostTypeList();
        List<Pair<String, Integer>> incomeTypeList = ConstantVariable.getIncomeTypeList();

        //使用循环判断字符串中是否包含相关收入、支出的关键词，并根据权重累加其权值
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

    public static String getCostOrIncomeTypeStr(List<String> wordList, int typeCode) {
        //初始化权重表
        Map<Integer, Integer> weightTable = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            weightTable.put(i, 0);
        }

        String tmpStr;
        //检查是否能够判断支出、收入
        if (ConstantVariable.ERROR_CODE != typeCode) {
            for (String word : wordList) {
                tmpStr = ConstantVariable.getCostOrIncomeType(word, typeCode);
                int index = ConstantVariable.getTypeIndex(tmpStr, typeCode);
                //统计关键词出现权重
                if (ConstantVariable.OTHER_TYPE_INDEX != index)
                    weightTable.replace(index, weightTable.get(index) + WeightValue.NORMAL_TYPE_WEIGHT_FACTOR);
                else
                    weightTable.replace(index, weightTable.get(index) + WeightValue.OTHER_TYPE_WEIGHT_FACTOR);
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