package stu.xuronghao.ledger.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringChecker {
    //判断账目收支类型
    public static int CostOrIncome(List<String> wordList) {
        int code = ConstantVariable.ERROR_CODE;

        for (String word : wordList) {
            if (code == ConstantVariable.ERROR_CODE) {
                code = ConstantVariable.getCostOrIncomeCode(word);
            }
        }
        return code;
    }

    public static String CostOrIncomeType(List<String> wordList, int typeCode) {
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

    public static String CheckDoubleValue(String moneyStr) {
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