package stu.xuronghao.ledger.handler;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class ConstantVariable {
    public static final String ARG_USER_INFO = "user";
    public static final String USER_NO = "userNo";
    public static final String BEGIN_DATE = "beginDate";
    public static final String END_DATE = "endDate";
    public static final String COST_TYPE = "cost";
    public static final String INCOME_TYPE = "income";
    public static final String SPLITTER = "<<->>";

    //Logger
    public static final String LOG_URL = "url:";
    public static final String LOG_JSON = "response Json: ";
    public static final String LOG_USER_JSON = "userJson";


    private static final Map<String, String[]> TYPE;
    static {
        TYPE = new HashMap<>();
        TYPE.put(COST_TYPE, new String[]{"餐饮", "交通", "服饰", "日用", "其他"});
        TYPE.put(INCOME_TYPE, new String[]{"工资", "奖金", "借款", "红包", "其他"});
    }

    private ConstantVariable() {
    }

    /**
     * 获取类型数组
     *
     * @param type ConstantVariable.COST_TYPE/INCOME_TYPE
     * @return 对应的类型数组
     */
    public static String[] getTypeArray(String type) {
        return TYPE.get(type);
    }

    /**
     *
     * @param type ConstantVariable.COST_TYPE/INCOME_TYPE
     * @param i 下标
     * @return
     */
    public static String getType(String type, int i) {
        return TYPE.get(type)[i];
    }
}
