package stu.xuronghao.ledger.handler;

import java.util.HashMap;
import java.util.Map;

import stu.xuronghao.ledger.R;

public class ConstantVariable {
    //params
    public static final String USER = "user";
    public static final String USER_NO = "userNo";
    public static final String BEGIN_DATE = "beginDate";
    public static final String END_DATE = "endDate";
    public static final String COST_TYPE = "cost";
    public static final String INCOME_TYPE = "income";
    public static final String TIME_ZONE = "GMT+8:00";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String COST_CN = "支出";
    public static final String INCOME_CN = "收入";

    public static final String TYPE_CODE = "typeCode";

    public static final String SPLITTER_REGEX = "<<->>";
    public static final String EMAIL_REGEX = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    //item
    public static final String ITEM_TITLE = "itemTitle";
    public static final String ITEM_CONTENT = "itemContent";
    public static final String ITEM_TYPE = "itemType";

    //state
    public static final int COST_CODE = 0;
    public static final int INCOME_CODE = 1;

    //userState
    public static final int LOGGED_IN = 2;
    public static final int ACTIVE = 1;
    public static final int FROZEN_USER = 0;
    public static final int NO_USER = -1;
    public static final int WRONG_PASSWD = -2;

    public static final String FB_TYPE_ADVICE = "建议";
    public static final String FB_TYPE_BUG = "漏洞";
    public static final String FB_TYPE_FROZE = "冻结";

    //info
    public static final String INFO_RECEIVED = "好的，治账酱收到了！";

    //hint
    public static final String HINT_EMPTY_CONTENT = "内容不能为空哦...";
    public static final String HINT_EMPTY_TITLE = "想个标题吧...";
    public static final String HINT_EMPTY_AMOUNT = "请输入金额！";
    public static final String HINT_EMPTY_EVENT = "请输入事件！";

    public static final String HINT_EMPTY_EMAIL = "";
    public static final String HINT_WRONG_EMAIL_FORMAT = "邮箱格式有问题！";
    public static final String HINT_WRONG_NICKNAME_LENGTH = "昵称长度超出限制啦！";
    public static final String HINT_EMPTY_PASSWD = "密码不能为空！";
    public static final String HINT_WRONG_PASSWD_LENGTH = "密码最少要6位哦！";
    public static final String HINT_CONFIRM_PASSWD = "请再次确认密码！";


    //error
    public static final String ERR_CONNECT_FAILED = "似乎和服务器君失去了联系...请检查网络连接哦~~~";

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

    private static final Map<String, Integer> INCOME_ICON_MAP;

    static {
        INCOME_ICON_MAP = new HashMap<>();
        INCOME_ICON_MAP.put("工资", R.drawable.icon_salary);
        INCOME_ICON_MAP.put("奖金", R.drawable.icon_bonus);
        INCOME_ICON_MAP.put("借款", R.drawable.icon_loan);
        INCOME_ICON_MAP.put("红包", R.drawable.icon_redpkt);
        INCOME_ICON_MAP.put("其他", R.drawable.icon_other_income);
    }

    private static final Map<String, Integer> COST_ICON_MAP;

    static {
        COST_ICON_MAP = new HashMap<>();
        COST_ICON_MAP.put("餐饮", R.drawable.icon_dining);
        COST_ICON_MAP.put("交通", R.drawable.icon_trans);
        COST_ICON_MAP.put("服饰", R.drawable.icon_cloth);
        COST_ICON_MAP.put("日用", R.drawable.icon_daily);
        COST_ICON_MAP.put("其他", R.drawable.icon_other_cost);
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
     * @param type ConstantVariable.COST_TYPE/INCOME_TYPE
     * @param i    下标
     * @return 对应的类型数据
     */
    public static String getType(String type, int i) {
        return TYPE.get(type)[i];
    }

    public static Integer getIcon(int typeCode, String key) {
        if (COST_CODE == typeCode)
            return COST_ICON_MAP.get(key);
        else if (INCOME_CODE == typeCode)
            return INCOME_ICON_MAP.get(key);
        else return -1;
    }
}