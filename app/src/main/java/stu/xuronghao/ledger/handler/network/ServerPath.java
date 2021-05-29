package stu.xuronghao.ledger.handler.network;

public class ServerPath {
    public static final String LOG_IN = "User/Login";
    public static final String SIGN_UP = "User/SignUp";
    public static final String UPDATE_USER_INFO = "User/updateUserInfo";
    public static final String GET_USER_INFO = "User/getUserInfo";
    public static final String ALL_COSTS = "Cost/queryByCostUser";
    public static final String COSTS_BY_DATE = "Cost/queryByCostDateOfUser";
    public static final String ALL_INCOMES = "Income/queryByIncomeUser";
    public static final String INCOMES_BY_DATE = "Income/queryByIncomeDateOfUser";
    public static final String PUSH_FEEDBACK = "Feedback/insertFb";
    public static final String PULL_ALL_ANNO = "Anno/queryAllAnno";
    public static final String PULL_ALL_HISTORY = "GetReply/getHistoryByUser";
    public static final String COST_CHAT = "Cost/costChat";
    public static final String INSERT_COST = "Cost/insertCost";
    public static final String UPDATE_COST = "Cost/updateCost";
    public static final String DELETE_COST = "Cost/deleteCost";
    public static final String INCOME_CHAT = "Income/incomeChat";
    public static final String INSERT_INCOME = "Income/insertIncome";
    public static final String UPDATE_INCOME = "Income/updateIncome";
    public static final String DELETE_INCOME = "Income/deleteIncome";

    private ServerPath() {
    }
}