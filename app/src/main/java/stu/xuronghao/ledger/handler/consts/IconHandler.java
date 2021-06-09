package stu.xuronghao.ledger.handler.consts;

import java.util.HashMap;
import java.util.Map;

import stu.xuronghao.ledger.R;

public class IconHandler {
    //注意，如果要对类型数组进行增减操作，需要同步对图标数组，颜色数组进行对应操作
    //花销所用的相关图标
    private static final int[] costIcons = {R.drawable.icon_dining, R.drawable.icon_trans,
            R.drawable.icon_cloth, R.drawable.icon_daily, R.drawable.icon_other_cost};
    //收入所用的相关图标
    private static final int[] incomeIcons = {R.drawable.icon_salary, R.drawable.icon_bonus,
            R.drawable.icon_subsidy, R.drawable.icon_redpkt, R.drawable.icon_other_income};

    private static final Map<String, Integer> COST_ICON_MAP;

    static {
        COST_ICON_MAP = new HashMap<>();
        //COST_ICON_MAP.put("餐饮", R.drawable.icon_dining);
        //COST_ICON_MAP.put("交通", R.drawable.icon_trans);
        //COST_ICON_MAP.put("服饰", R.drawable.icon_cloth);
        //COST_ICON_MAP.put("日用", R.drawable.icon_daily);
        //COST_ICON_MAP.put("其他", R.drawable.icon_other_cost);
        //注意，在修改列表项后记得同步更新图标列表，否则可能出现数组越界
        for (int i = 0; i < ConstantVariable.getTypeArray(ConstantVariable.COST_TYPE).length; i++) {
            COST_ICON_MAP.put(ConstantVariable.getTypeByTypeCode(i, ConstantVariable.COST_CODE), costIcons[i]);
        }
    }

    private static final Map<String, Integer> INCOME_ICON_MAP;

    static {
        INCOME_ICON_MAP = new HashMap<>();
        //INCOME_ICON_MAP.put("工资", R.drawable.icon_salary);
        //INCOME_ICON_MAP.put("奖金", R.drawable.icon_bonus);
        //INCOME_ICON_MAP.put("补贴", R.drawable.icon_subsidy);
        //INCOME_ICON_MAP.put("红包", R.drawable.icon_redpkt);
        //INCOME_ICON_MAP.put("其他", R.drawable.icon_other_income);
        for (int i = 0; i < ConstantVariable.getTypeArray(ConstantVariable.INCOME_TYPE).length; i++) {
            INCOME_ICON_MAP.put(ConstantVariable.getTypeByTypeCode(i, ConstantVariable.INCOME_CODE), incomeIcons[i]);
        }
    }

    public static Integer getIcon(int typeCode, String key) {
        switch (typeCode) {
            case ConstantVariable.COST_CODE:
                return COST_ICON_MAP.get(key);
            case ConstantVariable.INCOME_CODE:
                return INCOME_ICON_MAP.get(key);
            default:
                return -1;
        }
    }

    public static int getIconByArray(int typeCode, int index) {
        switch (typeCode) {
            case ConstantVariable.COST_CODE:
                return costIcons[index];
            case ConstantVariable.INCOME_CODE:
                return incomeIcons[index];
            default:
                return -1;
        }
    }
}
