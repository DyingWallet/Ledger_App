package stu.xuronghao.ledger.handler;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

import stu.xuronghao.ledger.entity.Anno;
import stu.xuronghao.ledger.entity.ChatInfo;
import stu.xuronghao.ledger.entity.Cost;
import stu.xuronghao.ledger.entity.Feedback;
import stu.xuronghao.ledger.entity.Income;
import stu.xuronghao.ledger.entity.User;

public class DataPuller {
    private static final String          Login = "User/Login";
    private static final String          SignUp   = "User/SignUp";
    private static final String AllCosts = "Cost/queryByCostUser";
    private static final String CostsByDate = "Cost/queryByCostDateOfUser";
    private static final String AllIncomes = "Income/queryByIncomeUser";
    private static final String IncomesByDate = "Income/queryByIncomeDateOfUser";
    private static final String PushFeedback = "Feedback/insertFb";
    private static final String PullAllAnno = "Anno/queryAllAnno";
    private static final String PullAllHistory = "GetReply/getHistoryByUser";
    private static final String CostChat = "Cost/costChat";
    private static final String insertCost = "Cost/insertCost";
    private static final String updateCost = "Cost/updateCost";
    private static final String deleteCost = "Cost/deleteCost";
    private static final String IncomeChat = "Income/incomeChat";
    private static final String insertIncome = "Income/insertIncome";
    private static final String updateIncome = "Income/updateIncome";
    private static final String deleteIncome = "Income/deleteIncome";
    private       GetHttpResponse response;

    public DataPuller() {
    }

    //登录
    public User LoginSender(User user) {
        User temp;
        //获取登录信息的JSON字串
        response = new GetHttpResponse();
        String userJson = JSON.toJSONString(user, SerializerFeature.WriteClassName);
        response.setService(Login);
        response.setJsonStr(userJson);
        userJson = "";
        userJson = response.Poster();
        //        Log.w("userJson:", userJson);
        if (userJson.equals(""))
            return null;
        temp = JSON.parseObject(userJson, User.class);
        //        JSONObject userJson = new JSON.parseObject(user);
        return temp;
    }

    //注册
    public boolean SignUpSender(User user){
        response = new GetHttpResponse();
        String userJson = JSON.toJSONString(user,SerializerFeature.WriteClassName);
        response.setService(SignUp);
        response.setJsonStr(userJson);
        userJson = response.Poster();
        if(userJson.equals(""))
            return false;
        return (boolean) JSON.parse(userJson);
    }

    //获取全部支出数据
    public List<Cost> PullCostOf(User user) {
        String Json;
        List<Cost> temp;
        response = new GetHttpResponse();
        //设置请求
        response.setService(AllCosts);
        response.setParams("userNo", user.getUserNo());
        Log.w("url: ", response.getUrl());
        //用Getter方法从服务器拉取数据
        Json = response.Getter();
        //        Log.w("response Json: ", Json);
        if (Json.equals(""))
            return null;
        //类型转换
        temp = JSONObject.parseArray(Json, Cost.class);
        //        Log.w("user0: ", temp.get(0).toString());
        return temp;
    }

    //获取全部收入数据
    public List<Income> PullIncomeOf(User user) {
        String Json;
        List<Income> temp;
        response = new GetHttpResponse();
        //设置请求
        response.setService(AllIncomes);
        response.setParams("userNo", user.getUserNo());
        Log.w("url: ", response.getUrl());
        //用Getter方法从服务器拉取数据
        Json = response.Getter();
        //        Log.w("response Json: ", Json);
        if (Json.equals(""))
            return null;
        //类型转换
        temp = JSONObject.parseArray(Json, Income.class);
        //        Log.w("user0: ", temp.get(0).toString());
        return temp;
    }

    //按时间获取支出数据
    public List<Cost> PullCostOfBetween(User user, String beginDate, String endDate) {
        String Json;
        List<Cost> temp;
        response = new GetHttpResponse();
        //设置请求
        response.setService(CostsByDate);
        response.setParams("userNo", user.getUserNo());
        response.setParams("beginDate", beginDate);
        response.setParams("endDate", endDate);
        Log.w("url: ", response.getUrl());
        //用Getter方法从服务器拉取数据
        Json = response.Getter();
        //        Log.w("response Json: ", Json);
        if (Json.equals(""))
            return null;
        //类型转换
        temp = JSONObject.parseArray(Json, Cost.class);
        //        Log.w("user0: ", temp.get(0).toString());
        return temp;
    }

    //按时间获取收入数据
    public List<Income> PullIncomeOfBetween(User user, String beginDate, String endDate) {
        String Json;
        List<Income> temp;
        response = new GetHttpResponse();
        //设置请求
        response.setService(IncomesByDate);
        response.setParams("userNo", user.getUserNo());
        response.setParams("beginDate", beginDate);
        response.setParams("endDate", endDate);
        Log.w("url: ", response.getUrl());
        //用Getter方法从服务器拉取数据
        Json = response.Getter();
        //        Log.w("response Json: ", Json);
        if (Json.equals(""))
            return null;
        //类型转换
        temp = JSONObject.parseArray(Json, Income.class);
        //        Log.w("user0: ", temp.get(0).toString());
        return temp;
    }

    //提交反馈
    public boolean HandOverFb(Feedback feedback){
        String fbJson;
        response = new GetHttpResponse();
        fbJson = JSON.toJSONString(feedback,SerializerFeature.WriteClassName);
        response.setService(PushFeedback);
        response.setJsonStr(fbJson);
        fbJson = response.Poster();
        if(fbJson.equals(""))
            return false;
        return (boolean)JSON.parse(fbJson);
    }

    //获取公告
    public List<Anno> PullAnnos() {
        String Json;
        List<Anno> temp;
        response = new GetHttpResponse();
        response.setService(PullAllAnno);
        Log.w("url: ", response.getUrl());
        Json = response.Getter();
        if(Json.equals(""))
            return null;
        temp = JSONObject.parseArray(Json,Anno.class);
        return temp;
    }

    //获取历史互动记录
    public List<ChatInfo> PullHistoryOf(String userNo){
        String Json;
        List<ChatInfo> temp;

        response = new GetHttpResponse();

        response.setService(PullAllHistory);
        response.setParams("userNo",userNo);
        Log.w("url: ", response.getUrl());
        Json = response.Getter();

        if (Json.equals(""))
            return null;
        //类型转换
        temp = JSONObject.parseArray(Json, ChatInfo.class);
        //        Log.w("user0: ", temp.get(0).toString());
        return temp;
    }

    //请求互动
    //请求收入互动
    public ChatInfo requestIncomeChat(Income income,ChatInfo info){
        String Json,incJson,infJson;
        ChatInfo temp;
        response = new GetHttpResponse();

        incJson = JSON.toJSONString(income,SerializerFeature.WriteClassName);
        infJson = JSON.toJSONString(info,SerializerFeature.WriteClassName);

        Json = incJson + "<<->>" + infJson;
        response.setService(IncomeChat);
        response.setJsonStr(Json);
        Log.w("url: ", response.getUrl());
        Json = response.Poster();
        if(Json.equals(""))
            return null;
        temp = JSON.parseObject(Json,ChatInfo.class);
        return temp;
    }

    //请求支出互动
    public ChatInfo requestCostChat(Cost cost,ChatInfo info){
        String Json,costJson,infJson;
        ChatInfo temp;
        response = new GetHttpResponse();

        costJson = JSON.toJSONString(cost,SerializerFeature.WriteClassName);
        infJson = JSON.toJSONString(info,SerializerFeature.WriteClassName);

        Json = costJson + "<<->>" + infJson;
        response.setService(CostChat);
        response.setJsonStr(Json);
        Log.w("url: ", response.getUrl());
        Json = response.Poster();
        if(Json.equals(""))
            return null;
        temp = JSON.parseObject(Json,ChatInfo.class);
        return temp;
    }

    //记录收入
    public boolean PushIncome(Income income){
        String Json;
        response = new GetHttpResponse();
        Json = JSON.toJSONString(income,SerializerFeature.WriteClassName);
        response.setService(insertIncome);
        response.setJsonStr(Json);

        Log.w("url: ", response.getUrl());
        Json = response.Poster();

        if(Json.equals(""))
            return false;
        return (boolean)JSON.parse(Json);
    }

    //记录支出
    public boolean PushCost(Cost cost){
        String Json;
        response = new GetHttpResponse();
        Json = JSON.toJSONString(cost,SerializerFeature.WriteClassName);
        response.setService(insertCost);
        response.setJsonStr(Json);

        Log.w("url: ", response.getUrl());
        Json = response.Poster();

        if(Json.equals(""))
            return false;
        return (boolean)JSON.parse(Json);
    }

    //更新支出
    public boolean UPDCost(Cost cost){
        String Json;
        response = new GetHttpResponse();
        Json = JSON.toJSONString(cost, SerializerFeature.WriteClassName);
        response.setService(updateCost);
        response.setJsonStr(Json);
        Log.w("url: ", response.getUrl());
        Json = response.Poster();
        if(Json.equals(""))
            return false;
        return (boolean) JSON.parse(Json);
    }

    //更新收入
    public boolean UPDIncome(Income income){
        String Json;
        response = new GetHttpResponse();
        Json = JSON.toJSONString(income, SerializerFeature.WriteClassName);
        response.setService(updateIncome);
        response.setJsonStr(Json);
        Log.w("url: ", response.getUrl());
        Json = response.Poster();
        if(Json.equals(""))
            return false;
        return (boolean) JSON.parse(Json);
    }

    //删除支出记录
    public boolean DELCost(Cost cost){
        String Json;
        response = new GetHttpResponse();
        Json = JSON.toJSONString(cost);
        response.setJsonStr(Json);
        response.setService(deleteCost);
        Log.w("url: ", response.getUrl());
        Json = response.Poster();
        if(Json.equals(""))
            return false;
        return (boolean) JSON.parse(Json);
    }

    //删除收入记录
    public boolean DELIncome(Income income){
        String Json;
        response = new GetHttpResponse();
        Json = JSON.toJSONString(income);
        response.setJsonStr(Json);
        response.setService(deleteIncome);
        Log.w("url: ", response.getUrl());
        Json = response.Poster();
        if(Json.equals(""))
            return false;
        return (boolean) JSON.parse(Json);

    }
}
