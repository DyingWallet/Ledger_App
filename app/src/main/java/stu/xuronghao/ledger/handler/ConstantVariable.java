package stu.xuronghao.ledger.handler;

import java.util.HashMap;
import java.util.Map;

public class ConstantVariable {
    //params
    public static final String USER = "user";
    public static final String USER_NO = "userNo";
    public static final String BEGIN_DATE = "beginDate";
    public static final String END_DATE = "endDate";
    public static final String COST_TYPE = "cost";
    public static final String INCOME_TYPE = "income";
    public static final String DATA_LIST = "data";
    public static final String FEE_LIST = "fee";
    public static final String MONTH_DATA_LIST = "month";
    public static final String TIME_ZONE = "GMT+8:00";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String START_OF_DAY = " 00:00:00";
    public static final String END_OF_DAY = " 24:00:00";
    public static final String START_OF_YEAR = "-01-01";
    public static final String END_OF_YEAR = "-12-31";
    public static final String URL = "url";

    public static final String TYPE_CODE = "typeCode";

    public static final String SPLITTER_REGEX = "<<->>";
    public static final String EMAIL_REGEX = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static final String TIME_REGEX = "\\s";
    public static final String DATE_REGEX = "-";

    //text
    public static final String TEXT_COST = "支出";
    public static final String TEXT_INCOME = "收入";
    public static final String TEXT_COST_EVENT = "支出事件：";
    public static final String TEXT_COST_AMOUNT = "支出金额：";
    public static final String TEXT_COST_TYPE = "支出类型：";
    public static final String TEXT_INCOME_EVENT = "收入事件：";
    public static final String TEXT_INCOME_AMOUNT = "收入金额：";
    public static final String TEXT_INCOME_TYPE = "收入类型：";
    public static final String TEXT_REMARK = "备注：";
    public static final String TEXT_YUAN = "元";

    public static final String TEXT_CAUTION = "！！>>>注意<<<！！";
    public static final String TEXT_DELETE_HINT_MSG = "注意！资料删掉的话，治账酱就再也想不起来了哦。要继续吗？";
    public static final String TEXT_CONFIRM = ">>>继续<<<";
    public static final String TEXT_CANCEL = ">>>算了<<<";

    //item
    public static final String ITEM_TITLE = "itemTitle";
    public static final String ITEM_CONTENT = "itemContent";
    public static final String ITEM_TYPE = "itemType";

    //state
    public static final int COST_CODE = 0;
    public static final int INCOME_CODE = 1;
    public static final int START_CODE = 0;
    public static final int END_CODE = 1;

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
    public static final String INFO_RECEIVED = "好的，反馈收到了！";
    public static final String INFO_SIGN_UP_SUCCESS = "好的！治账酱记住你了！";
    public static final String INFO_OPERATE_SUCCESS = "收到！";
    public static final String INFO_DELETE_SUCCESS = "好的，我已经忘掉这条了！";
    public static final String INFO_UPDATE_SUCCESS = "修改已生效！";

    //hint
    public static final String HINT_EMPTY_CONTENT = "内容不能为空哦...";
    public static final String HINT_EMPTY_TITLE = "想个标题吧...";
    public static final String HINT_EMPTY_AMOUNT = "请输入金额！";
    public static final String HINT_EMPTY_EVENT = "请输入事件！";
    public static final String HINT_DATE_TO_FUTURE = "怎么想都不可能预知未来吧！";

    public static final String HINT_EMPTY_EMAIL = "邮箱不能为空！";
    public static final String HINT_WRONG_EMAIL_FORMAT = "邮箱格式有问题！";
    public static final String HINT_WRONG_NICKNAME_LENGTH = "昵称长度超出限制啦！";
    public static final String HINT_EMPTY_PASSWD = "密码不能为空！";
    public static final String HINT_WRONG_PASSWD_LENGTH = "密码最少要6位哦！";
    public static final String HINT_CONFIRM_PASSWD = "请再次确认密码！";
    public static final String HINT_USER_EXIST = "该账号已经被注册了哦，换一个吧";

    public static final String HINT_USER_FROZEN = "该账户已停止使用!";
    public static final String HINT_USER_NOT_FOUND = "亲，你还没注册呢！";
    public static final String HINT_WRONG_PASSWD = "亲，密码错了哟！";

    public static final String HINT_IN_DEVELOPING = "功能还在开发中...";

    //error
    public static final String ERR_CONNECT_FAILED = "似乎和服务器失去了联系...请检查网络连接哦~~~";

    //Logger
    public static final String LOG_URL = "url:";
    public static final String LOG_JSON = "response Json: ";
    public static final String LOG_USER_JSON = "userJson";
    public static final String INDEX = "index";


    private static final Map<String, String[]> TYPE;

    static {
        TYPE = new HashMap<>();
        TYPE.put(COST_TYPE, new String[]{"餐饮", "交通", "服饰", "日用", "其他"});
        TYPE.put(INCOME_TYPE, new String[]{"工资", "奖金", "借款", "红包", "其他"});
    }

    private static final Map<String, Integer> COST_INDEX_MAP;

    static {
        COST_INDEX_MAP = new HashMap<>();
        COST_INDEX_MAP.put("餐饮", 0);
        COST_INDEX_MAP.put("交通", 1);
        COST_INDEX_MAP.put("服饰", 2);
        COST_INDEX_MAP.put("日用", 3);
        COST_INDEX_MAP.put("其他", 4);
    }

    private static final Map<String, Integer> INCOME_INDEX_MAP;

    static {
        INCOME_INDEX_MAP = new HashMap<>();
        INCOME_INDEX_MAP.put("工资", 0);
        INCOME_INDEX_MAP.put("奖金", 1);
        INCOME_INDEX_MAP.put("借款", 2);
        INCOME_INDEX_MAP.put("红包", 3);
        INCOME_INDEX_MAP.put("其他", 4);
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


    public static Integer getTypeIndex(int typeCode, String key) {
        switch (typeCode) {
            case COST_CODE:
                return COST_INDEX_MAP.get(key);
            case INCOME_CODE:
                return INCOME_INDEX_MAP.get(key);
            default:
                return -1;
        }
    }

}