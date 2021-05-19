package stu.xuronghao.ledger.handler;


import android.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ConstantVariable {
    //params
    public static final String APP_ID = "=528359aa";
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
    public static final String LANGUAGE = "zh_cn";
    public static final String MANDARIN = "mandarin";
    public static final String RESULT_TYPE = "json";
    public static final String WORDS = "ws";
    public static final String CHINESE_WORDS = "cw";
    public static final String SINGLE_WORD = "w";
    public static final String ERR_XUNFEI = "讯飞:";
    public static final String NULL_STR = "NULL";
    public static final String ZERO_STR = "00";

    public static final String TYPE_CODE = "typeCode";

    public static final String SPLITTER_REGEX = "<<->>";
    public static final String EMAIL_REGEX = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static final String TIME_REGEX = "\\s";
    public static final String DATE_REGEX = "-";
    public static final String AMOUNT_REGEX = "(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?";
    public static final String DOT_REGEX = ".";

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
    public static final int REQUEST_CODE_CONTRACT = 101;
    public static final int REQUEST_CODE = 1;
    public static final int COST_CODE = 0;
    public static final int INCOME_CODE = 1;
    public static final int START_CODE = 0;
    public static final int END_CODE = 1;
    public static final int ERROR_CODE = -1;

    //userState
    public static final int LOGGED_IN = 2;
    public static final int ACTIVE = 1;
    public static final int FROZEN_USER = 0;
    public static final int NO_USER = -1;
    public static final int WRONG_PASSWD = -2;

    //weightFactor
    public static final int NORMAL_TYPE_WEIGHT_FACTOR = 5;
    public static final int OTHER_TYPE_WEIGHT_FACTOR = 1;

    public static final String FB_TYPE_ADVICE = "建议";
    public static final String FB_TYPE_BUG = "漏洞";
    public static final String FB_TYPE_FROZE = "冻结";
    public static final String OTHER_TYPE = "其他";

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

    public static final String HINT_VOICE = "说出的账目信息中请尽量包含收、发、出、入类的关键词";

    //error
    public static final String ERR_CONNECT_FAILED = "似乎和服务器失去了联系...请检查网络连接哦";
    public static final String ERR_RESOLVE_VOICE_FAILED = "你说的账目信息有点复杂，理解不能啊";

    //Logger
    public static final String LOG_URL = "url:";
    public static final String LOG_JSON = "response Json: ";
    public static final String LOG_USER_JSON = "userJson";
    public static final String INDEX = "index";


    private static final Map<String, String[]> TYPE;
    static {
        TYPE = new HashMap<>();
        TYPE.put(COST_TYPE, new String[]{"餐饮", "交通", "服饰", "日用", "其他"});
        TYPE.put(INCOME_TYPE, new String[]{"工资", "奖金", "补贴", "红包", "其他"});
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
        INCOME_INDEX_MAP.put("补贴", 2);
        INCOME_INDEX_MAP.put("红包", 3);
        INCOME_INDEX_MAP.put("其他", 4);
    }

    private static final List<Pair<String,Integer>> COST_TYPE_LIST;
    static {
        COST_TYPE_LIST = new LinkedList<>();
        int weight;
        //极高
        weight = WeightValue.ABSOLUTE;
        COST_TYPE_LIST.add(new Pair<>("购入",weight));
        COST_TYPE_LIST.add(new Pair<>("购买",weight));
        COST_TYPE_LIST.add(new Pair<>("买入",weight));
        COST_TYPE_LIST.add(new Pair<>("被骗",weight));
        COST_TYPE_LIST.add(new Pair<>("税",weight));
        COST_TYPE_LIST.add(new Pair<>("网购",weight));

        //高
        weight = WeightValue.HIGH;
        COST_TYPE_LIST.add(new Pair<>("购",weight));
        COST_TYPE_LIST.add(new Pair<>("买",weight));
        COST_TYPE_LIST.add(new Pair<>("交",weight));
        COST_TYPE_LIST.add(new Pair<>("缴",weight));
        COST_TYPE_LIST.add(new Pair<>("看",weight));
        COST_TYPE_LIST.add(new Pair<>("用了",weight));
        COST_TYPE_LIST.add(new Pair<>("花了",weight));
        COST_TYPE_LIST.add(new Pair<>("吃了",weight));
        COST_TYPE_LIST.add(new Pair<>("喝了",weight));
        COST_TYPE_LIST.add(new Pair<>("订购",weight));
        COST_TYPE_LIST.add(new Pair<>("支出",weight));
        COST_TYPE_LIST.add(new Pair<>("支付",weight));
        COST_TYPE_LIST.add(new Pair<>("开支",weight));
        COST_TYPE_LIST.add(new Pair<>("旅游",weight));
        COST_TYPE_LIST.add(new Pair<>("扣费",weight));
        COST_TYPE_LIST.add(new Pair<>("发红包",weight));

        //中
        weight = WeightValue.MID;
        COST_TYPE_LIST.add(new Pair<>("订了",weight));
        COST_TYPE_LIST.add(new Pair<>("借出",weight));
        COST_TYPE_LIST.add(new Pair<>("借给",weight));
        COST_TYPE_LIST.add(new Pair<>("出借",weight));
        COST_TYPE_LIST.add(new Pair<>("住院",weight));
        COST_TYPE_LIST.add(new Pair<>("住宿",weight));
        COST_TYPE_LIST.add(new Pair<>("住宾馆",weight));
        COST_TYPE_LIST.add(new Pair<>("充值",weight));
        COST_TYPE_LIST.add(new Pair<>("租",weight));
        COST_TYPE_LIST.add(new Pair<>("乘",weight));
        COST_TYPE_LIST.add(new Pair<>("坐",weight));
        COST_TYPE_LIST.add(new Pair<>("转账",weight));
        COST_TYPE_LIST.add(new Pair<>("打赏",weight));

        //低
        weight = WeightValue.LOW;
        COST_TYPE_LIST.add(new Pair<>("租",weight));
        COST_TYPE_LIST.add(new Pair<>("赔了",weight));
        COST_TYPE_LIST.add(new Pair<>("充",weight));
        COST_TYPE_LIST.add(new Pair<>("续",weight));
    }

    private static final List<Pair<String,Integer>> INCOME_TYPE_LIST;
    static {
        INCOME_TYPE_LIST = new LinkedList<>();
        int weight;
        //极高
        weight = WeightValue.ABSOLUTE;
        INCOME_TYPE_LIST.add(new Pair<>("收", weight));
        INCOME_TYPE_LIST.add(new Pair<>("卖了", weight));
        INCOME_TYPE_LIST.add(new Pair<>("卖出", weight));
        INCOME_TYPE_LIST.add(new Pair<>("赚了", weight));
        INCOME_TYPE_LIST.add(new Pair<>("发工资", weight));
        INCOME_TYPE_LIST.add(new Pair<>("收到工资", weight));

        //高
        weight = WeightValue.HIGH;
        INCOME_TYPE_LIST.add(new Pair<>("工资", weight));
        INCOME_TYPE_LIST.add(new Pair<>("卖出", weight));
        INCOME_TYPE_LIST.add(new Pair<>("收到打赏", weight));

        //中
        weight = WeightValue.MID;
        INCOME_TYPE_LIST.add(new Pair<>("收入", weight));
        INCOME_TYPE_LIST.add(new Pair<>("红包", weight));
        INCOME_TYPE_LIST.add(new Pair<>("补贴", weight));
        INCOME_TYPE_LIST.add(new Pair<>("奖金", weight));

        //低
        weight = WeightValue.LOW;
        INCOME_TYPE_LIST.add(new Pair<>("生活费", weight));
        INCOME_TYPE_LIST.add(new Pair<>("借了", weight));
    }

    private static final Map<String, String> COST_MAP_TO_TYPE;
    static {
        COST_MAP_TO_TYPE = new HashMap<>();
        //餐饮
        COST_MAP_TO_TYPE.put("饭", "餐饮");
        COST_MAP_TO_TYPE.put("餐", "餐饮");
        COST_MAP_TO_TYPE.put("饮", "餐饮");
        COST_MAP_TO_TYPE.put("面", "餐饮");
        COST_MAP_TO_TYPE.put("肉", "餐饮");
        COST_MAP_TO_TYPE.put("鸡蛋", "餐饮");
        COST_MAP_TO_TYPE.put("鸭蛋", "餐饮");
        COST_MAP_TO_TYPE.put("鹅蛋", "餐饮");
        COST_MAP_TO_TYPE.put("咸蛋", "餐饮");
        COST_MAP_TO_TYPE.put("牛奶", "餐饮");
        COST_MAP_TO_TYPE.put("羊奶", "餐饮");
        COST_MAP_TO_TYPE.put("酸奶", "餐饮");
        COST_MAP_TO_TYPE.put("粮", "餐饮");
        COST_MAP_TO_TYPE.put("油", "餐饮");
        COST_MAP_TO_TYPE.put("米", "餐饮");
        COST_MAP_TO_TYPE.put("鱼", "餐饮");
        COST_MAP_TO_TYPE.put("菜", "餐饮");
        COST_MAP_TO_TYPE.put("包子", "餐饮");
        COST_MAP_TO_TYPE.put("馒头", "餐饮");
        COST_MAP_TO_TYPE.put("豆", "餐饮");
        COST_MAP_TO_TYPE.put("火锅", "餐饮");
        COST_MAP_TO_TYPE.put("干锅", "餐饮");
        //交通
        COST_MAP_TO_TYPE.put("单车", "交通");
        COST_MAP_TO_TYPE.put("飞机", "交通");
        COST_MAP_TO_TYPE.put("航班", "交通");
        COST_MAP_TO_TYPE.put("地铁", "交通");
        COST_MAP_TO_TYPE.put("公交", "交通");
        COST_MAP_TO_TYPE.put("打的", "交通");
        COST_MAP_TO_TYPE.put("打车", "交通");
        COST_MAP_TO_TYPE.put("的士", "交通");
        COST_MAP_TO_TYPE.put("火车", "交通");
        //服饰
        COST_MAP_TO_TYPE.put("衣", "服饰");
        COST_MAP_TO_TYPE.put("裙", "服饰");
        COST_MAP_TO_TYPE.put("裤", "服饰");
        COST_MAP_TO_TYPE.put("袜", "服饰");
        COST_MAP_TO_TYPE.put("鞋", "服饰");
        COST_MAP_TO_TYPE.put("手套", "服饰");
        //日用
        COST_MAP_TO_TYPE.put("洗面奶", "日用");
        COST_MAP_TO_TYPE.put("洗发水", "日用");
        COST_MAP_TO_TYPE.put("香波", "日用");
        COST_MAP_TO_TYPE.put("话费", "日用");
        COST_MAP_TO_TYPE.put("化妆品", "日用");
        COST_MAP_TO_TYPE.put("护肤品", "日用");
        COST_MAP_TO_TYPE.put("锅", "日用");
        COST_MAP_TO_TYPE.put("碗", "日用");
    }

    private static final Map<String, String> INCOME_MAP_TO_TYPE;
    static {
        INCOME_MAP_TO_TYPE = new HashMap<>();
        //工资
        INCOME_MAP_TO_TYPE.put("工资", "工资");
        INCOME_MAP_TO_TYPE.put("薪水", "工资");
        //奖金
        INCOME_MAP_TO_TYPE.put("奖金", "奖金");
        //补贴
        INCOME_MAP_TO_TYPE.put("补", "补贴");
        //红包
        INCOME_MAP_TO_TYPE.put("红包", "红包");
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
    public static String getTypeByTypeStr(int i, String type) {
        return TYPE.get(type)[i];
    }

    public static String getTypeByTypeCode(int index, int typeCode){
        switch (typeCode){
            case COST_CODE:
                return getTypeByTypeStr(index, COST_TYPE);
            case INCOME_CODE:
                return getTypeByTypeStr(index, INCOME_TYPE);
            default:
                return NULL_STR;
        }
    }

    public static Integer getTypeIndex(String key, int typeCode) {
        switch (typeCode) {
            case COST_CODE:
                return COST_INDEX_MAP.get(key);
            case INCOME_CODE:
                return INCOME_INDEX_MAP.get(key);
            default:
                return ERROR_CODE;
        }
    }

    public static String getCostOrIncomeType(String str, int typeCode) {
        if (COST_CODE == typeCode) {
            if (COST_MAP_TO_TYPE.containsKey(str)) {
                return COST_MAP_TO_TYPE.get(str);
            }
        } else if (INCOME_CODE == typeCode) {
            if (INCOME_MAP_TO_TYPE.containsKey(str)) {
                return INCOME_MAP_TO_TYPE.get(str);
            }
        }
        return OTHER_TYPE;
    }

    public static List<Pair<String, Integer>> getCostTypeList() {
        return COST_TYPE_LIST;
    }

    public static List<Pair<String, Integer>> getIncomeTypeList() {
        return INCOME_TYPE_LIST;
    }
}