package stu.xuronghao.ledger.handler;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.List;

import stu.xuronghao.ledger.entity.Anno;
import stu.xuronghao.ledger.entity.ChatInfo;
import stu.xuronghao.ledger.entity.Cost;
import stu.xuronghao.ledger.entity.Feedback;
import stu.xuronghao.ledger.entity.Income;
import stu.xuronghao.ledger.entity.User;

public class DataPuller {
    private GetHttpResponse response;

    //登录
    public User loginSender(User user) {
        User temp;
        //获取登录信息的JSON字串
        response = new GetHttpResponse();
        String userJson = JSON.toJSONString(user, SerializerFeature.WriteClassName);
        response.setService(ServerPath.LOG_IN);
        response.setJsonStr(userJson);
        userJson = response.Poster();
        Log.w(ConstantVariable.LOG_USER_JSON, userJson);
        if ("".equals(userJson))
            return null;
        temp = JSON.parseObject(userJson, User.class);
        return temp;
    }

    //注册
    public boolean signUpSender(User user) {
        response = new GetHttpResponse();
        String userJson = JSON.toJSONString(user, SerializerFeature.WriteClassName);
        response.setService(ServerPath.SIGN_UP);
        response.setJsonStr(userJson);
        userJson = response.Poster();
        if ("".equals(userJson))
            return false;
        return (boolean) JSON.parse(userJson);
    }

    //获取全部支出数据
    public List<Cost> pullCostOf(User user) {
        String json;
        List<Cost> temp;
        response = new GetHttpResponse();
        //设置请求
        response.setService(ServerPath.ALL_COSTS);
        response.setParams(ConstantVariable.USER_NO, user.getUserNo());
        Log.w(ConstantVariable.LOG_URL, response.getUrl());
        //用Getter方法从服务器拉取数据
        json = response.Getter();
        Log.w(ConstantVariable.LOG_JSON, json);
        if ("".equals(json))
            return new ArrayList<>();
        //类型转换
        temp = JSON.parseArray(json, Cost.class);
        return temp;
    }

    //获取全部收入数据
    public List<Income> pullIncomeOf(User user) {
        String json;
        List<Income> temp;
        response = new GetHttpResponse();
        //设置请求
        response.setService(ServerPath.ALL_INCOMES);
        response.setParams(ConstantVariable.USER_NO, user.getUserNo());
        Log.w(ConstantVariable.LOG_URL, response.getUrl());
        //用Getter方法从服务器拉取数据
        json = response.Getter();
        Log.w(ConstantVariable.LOG_JSON, json);
        if ("".equals(json))
            return new ArrayList<>();
        //类型转换
        temp = JSON.parseArray(json, Income.class);
        return temp;
    }

    //按时间获取支出数据
    public List<Cost> pullCostOfBetween(User user, String beginDate, String endDate) {
        String json;
        List<Cost> temp;
        response = new GetHttpResponse();
        //设置请求
        response.setService(ServerPath.COSTS_BY_DATE);
        response.setParams(ConstantVariable.USER_NO, user.getUserNo());
        response.setParams(ConstantVariable.BEGIN_DATE, beginDate);
        response.setParams(ConstantVariable.END_DATE, endDate);
        Log.w(ConstantVariable.LOG_URL, response.getUrl());
        //用Getter方法从服务器拉取数据
        json = response.Getter();
        Log.w(ConstantVariable.LOG_JSON, json);
        if ("".equals(json))
            return new ArrayList<>();
        //类型转换
        temp = JSON.parseArray(json, Cost.class);
        return temp;
    }

    //按时间获取收入数据
    public List<Income> pullIncomeOfBetween(User user, String beginDate, String endDate) {
        String json;
        List<Income> temp;
        response = new GetHttpResponse();
        //设置请求
        response.setService(ServerPath.INCOMES_BY_DATE);
        response.setParams(ConstantVariable.USER_NO, user.getUserNo());
        response.setParams(ConstantVariable.BEGIN_DATE, beginDate);
        response.setParams(ConstantVariable.END_DATE, endDate);
        Log.w(ConstantVariable.LOG_URL, response.getUrl());
        //用Getter方法从服务器拉取数据
        json = response.Getter();
        Log.w(ConstantVariable.LOG_JSON, json);
        if ("".equals(json))
            return new ArrayList<>();
        //类型转换
        temp = JSON.parseArray(json, Income.class);
        return temp;
    }

    //提交反馈
    public boolean handOverFb(Feedback feedback) {
        String json;
        response = new GetHttpResponse();
        json = JSON.toJSONString(feedback, SerializerFeature.WriteClassName);
        response.setService(ServerPath.PUSH_FEEDBACK);
        response.setJsonStr(json);
        json = response.Poster();
        if ("".equals(json))
            return false;
        return (boolean) JSON.parse(json);
    }

    //获取公告
    public List<Anno> pullAnnos() {
        String json;
        List<Anno> temp;
        response = new GetHttpResponse();
        response.setService(ServerPath.PULL_ALL_ANNO);
        Log.w(ConstantVariable.LOG_URL, response.getUrl());
        json = response.Getter();
        if ("".equals(json))
            return new ArrayList<>();
        temp = JSON.parseArray(json, Anno.class);
        return temp;
    }

    //获取历史互动记录
    public List<ChatInfo> pullHistoryOf(String userNo) {
        String json;
        List<ChatInfo> temp;

        response = new GetHttpResponse();

        response.setService(ServerPath.PULL_ALL_HISTORY);
        response.setParams(ConstantVariable.USER_NO, userNo);
        Log.w(ConstantVariable.LOG_URL, response.getUrl());
        json = response.Getter();

        if ("".equals(json))
            return new ArrayList<>();
        //类型转换
        temp = JSON.parseArray(json, ChatInfo.class);
        return temp;
    }

    //请求互动
    //请求收入互动
    public ChatInfo requestIncomeChat(Income income, ChatInfo info) {
        String json;
        String incJson;
        String infJson;
        ChatInfo temp;
        response = new GetHttpResponse();

        incJson = JSON.toJSONString(income, SerializerFeature.WriteClassName);
        infJson = JSON.toJSONString(info, SerializerFeature.WriteClassName);

        json = incJson + ConstantVariable.SPLITTER + infJson;
        response.setService(ServerPath.INCOME_CHAT);
        response.setJsonStr(json);
        Log.w(ConstantVariable.LOG_URL, response.getUrl());
        json = response.Poster();
        if ("".equals(json))
            return null;
        temp = JSON.parseObject(json, ChatInfo.class);
        return temp;
    }

    //请求支出互动
    public ChatInfo requestCostChat(Cost cost, ChatInfo info) {
        String json;
        String costJson;
        String infJson;
        ChatInfo temp;
        response = new GetHttpResponse();

        costJson = JSON.toJSONString(cost, SerializerFeature.WriteClassName);
        infJson = JSON.toJSONString(info, SerializerFeature.WriteClassName);

        json = costJson + ConstantVariable.SPLITTER + infJson;
        response.setService(ServerPath.COST_CHAT);
        response.setJsonStr(json);
        Log.w(ConstantVariable.LOG_URL, response.getUrl());
        json = response.Poster();
        if ("".equals(json))
            return null;
        temp = JSON.parseObject(json, ChatInfo.class);
        return temp;
    }

    //记录收入
    public boolean pushIncome(Income income) {
        String json;
        response = new GetHttpResponse();
        json = JSON.toJSONString(income, SerializerFeature.WriteClassName);
        response.setService(ServerPath.INSERT_INCOME);
        response.setJsonStr(json);

        Log.w(ConstantVariable.LOG_URL, response.getUrl());
        json = response.Poster();

        if ("".equals(json))
            return false;
        return (boolean) JSON.parse(json);
    }

    //记录支出
    public boolean pushCost(Cost cost) {
        String json;
        response = new GetHttpResponse();
        json = JSON.toJSONString(cost, SerializerFeature.WriteClassName);
        response.setService(ServerPath.INSERT_COST);
        response.setJsonStr(json);

        Log.w(ConstantVariable.LOG_URL, response.getUrl());
        json = response.Poster();

        if ("".equals(json))
            return false;
        return (boolean) JSON.parse(json);
    }

    //更新支出
    public boolean updateCost(Cost cost) {
        String json;
        response = new GetHttpResponse();
        json = JSON.toJSONString(cost, SerializerFeature.WriteClassName);
        response.setService(ServerPath.UPDATE_COST);
        response.setJsonStr(json);
        Log.w(ConstantVariable.LOG_URL, response.getUrl());
        json = response.Poster();
        if ("".equals(json))
            return false;
        return (boolean) JSON.parse(json);
    }

    //更新收入
    public boolean updateIncome(Income income) {
        String json;
        response = new GetHttpResponse();
        json = JSON.toJSONString(income, SerializerFeature.WriteClassName);
        response.setService(ServerPath.UPDATE_INCOME);
        response.setJsonStr(json);
        Log.w(ConstantVariable.LOG_URL, response.getUrl());
        json = response.Poster();
        if ("".equals(json))
            return false;
        return (boolean) JSON.parse(json);
    }

    //删除支出记录
    public boolean deleteCost(Cost cost) {
        String json;
        response = new GetHttpResponse();
        json = JSON.toJSONString(cost);
        response.setJsonStr(json);
        response.setService(ServerPath.DELETE_COST);
        Log.w(ConstantVariable.LOG_URL, response.getUrl());
        json = response.Poster();
        if ("".equals(json))
            return false;
        return (boolean) JSON.parse(json);
    }

    //删除收入记录
    public boolean deleteIncome(Income income) {
        String json;
        response = new GetHttpResponse();
        json = JSON.toJSONString(income);
        response.setJsonStr(json);
        response.setService(ServerPath.DELETE_INCOME);
        Log.w(ConstantVariable.LOG_URL, response.getUrl());
        json = response.Poster();
        if ("".equals(json))
            return false;
        return (boolean) JSON.parse(json);

    }
}
