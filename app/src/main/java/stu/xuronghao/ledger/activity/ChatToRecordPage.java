package stu.xuronghao.ledger.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.adapter.ChatListAdapter;
import stu.xuronghao.ledger.entity.ChatInfo;
import stu.xuronghao.ledger.entity.Cost;
import stu.xuronghao.ledger.entity.Income;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.consts.ConstantVariable;
import stu.xuronghao.ledger.handler.network.DataPuller;
import stu.xuronghao.ledger.handler.DateHandler;
import stu.xuronghao.ledger.handler.validator.StringChecker;
import stu.xuronghao.ledger.handler.validator.Validator;

public class ChatToRecordPage extends AppCompatActivity {
    private ChatListAdapter adapter;
    private ListView listView;
    private List<ChatInfo> infoList;
    private User user;
    private ChatInfo userInfo;
    private Context context;
    private String selected;
    private AVLoadingIndicatorView indicatorView;
    private int typeCode = ConstantVariable.COST_CODE;
    private String event;
    private String money;
    private String remark;
    private String dateStr;
    private AsyncChatTask asyncChatTask;

    private final DataPuller dataPuller = new DataPuller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_to_record_page);
        user = (User) getIntent().getSerializableExtra(ConstantVariable.USER);
        context = this;
        listView = findViewById(R.id.lv_chat);
        indicatorView = findViewById(R.id.avi_chat);
        SpeechUtility.createUtility(context, SpeechConstant.APPID + ConstantVariable.APP_ID);

        Button costBtn = findViewById(R.id.btn_Cost_Dialog);
        Button incomeBtn = findViewById(R.id.btn_Income_Dialog);
        costBtn.setOnClickListener(v -> {
            typeCode = ConstantVariable.COST_CODE;
            showPusherDialog();
        });
        incomeBtn.setOnClickListener(v -> {
            typeCode = ConstantVariable.INCOME_CODE;
            showPusherDialog();
        });

        ImageView cancel = findViewById(R.id.img_chat_page_cancel);
        cancel.setOnClickListener(v -> finish());
        ImageView voiceBtn = findViewById(R.id.img_voice);
        voiceBtn.setOnClickListener(v -> {
            //申请权限
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.RECORD_AUDIO) ==
                    PackageManager.PERMISSION_GRANTED) {
                // You can use the API that requires the permission.
                initSpeech();
            } else {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, ConstantVariable.REQUEST_CODE);
            }
        });
        AsyncPullHistoryTask asyncPullHistoryTask = new AsyncPullHistoryTask();
        asyncPullHistoryTask.execute();
    }

    private void showPusherDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_dialog, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();
        Button cancel = view.findViewById(R.id.btn_Chat_Dialog_Cancel);
        Button add = view.findViewById(R.id.btn_Chat_Dialog_Push);
        TextView txvEvent = view.findViewById(R.id.txv_Chat_Dialog_Event);
        TextView txvAmount = view.findViewById(R.id.txv_Chat_Dialog_Amount);
        TextView txvType = view.findViewById(R.id.txv_Chat_Dialog_Type);
        TextView txvRemark = view.findViewById(R.id.txv_Chat_Dialog_Remark);
        Spinner spinner = view.findViewById(R.id.sp_Chat_Type);
        EditText etxEvent = view.findViewById(R.id.etx_Chat_Dialog_Event);

        etxEvent.setText(typeCode == ConstantVariable.COST_CODE ? ConstantVariable.TEXT_COST : ConstantVariable.TEXT_INCOME);
        txvEvent.setText(typeCode == ConstantVariable.COST_CODE ? ConstantVariable.TEXT_COST_EVENT : ConstantVariable.TEXT_INCOME_EVENT);
        txvAmount.setText(typeCode == ConstantVariable.COST_CODE ? ConstantVariable.TEXT_COST_AMOUNT : ConstantVariable.TEXT_INCOME_AMOUNT);
        txvType.setText(typeCode == ConstantVariable.COST_CODE ? ConstantVariable.TEXT_COST_TYPE : ConstantVariable.TEXT_INCOME_TYPE);
        txvRemark.setText(ConstantVariable.TEXT_REMARK);

        ArrayAdapter<String> spAdapter = new ArrayAdapter<>(context, R.layout.spinner_item_sel,
                ConstantVariable.getTypeArray(typeCode == ConstantVariable.COST_CODE ? ConstantVariable.COST_TYPE : ConstantVariable.INCOME_TYPE));
        spAdapter.setDropDownViewResource(R.layout.spinner_item_drop);
        spinner.setAdapter(spAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = ConstantVariable.getTypeByTypeStr(position,
                        typeCode == ConstantVariable.COST_CODE ? ConstantVariable.COST_TYPE : ConstantVariable.INCOME_TYPE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Not Used
            }
        });

        //向服务器推送信息
        add.setOnClickListener(v -> {
            //获取输入对象
            EditText etxMoney = view.findViewById(R.id.etx_Chat_Dialog_Amount);
            EditText etxRemark = view.findViewById(R.id.etx_Chat_Dialog_Remark);

            //数据提取
            event = etxEvent.getText().toString();
            money = etxMoney.getText().toString();
            remark = etxRemark.getText().toString();
            dateStr = DateHandler.getCurrentDatetime();
            if (Validator.checkBillInfoInput(event, money, context)) {
                dialog.dismiss();
                asyncChatTask = new AsyncChatTask();
                asyncChatTask.execute();
            }
        });

        cancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void initSpeech() {
        RecognizerDialog mDialog = new RecognizerDialog(context, null);
        mDialog.setParameter(SpeechConstant.LANGUAGE, ConstantVariable.LANGUAGE);
        mDialog.setParameter(SpeechConstant.ACCENT, ConstantVariable.MANDARIN);
        mDialog.setParameter(SpeechConstant.ASR_PTT,"1");

        mDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                if (!isLast) {
                    //获取分词list
                    List<String> wordList = parseWordArray(recognizerResult.getResultString());
                    String result = parseVoice(wordList);
                    String tmp;
                    int type;
                    int typeIndex;
                    String typeStr;
                    String moneyStr = ConstantVariable.NULL_STR;

                    //获取金额
                    if (result.contains("块")) tmp = result.replace("块", ".");
                    else tmp = result;
                    Pattern pattern = Pattern.compile(ConstantVariable.AMOUNT_REGEX);
                    Matcher matcher = pattern.matcher(tmp);
                    if (matcher.find()) {
                        moneyStr = matcher.group();
                    }
                    //保证小数点后数据的正确
                    moneyStr = StringChecker.checkDoubleValue(moneyStr);
                    //获取收支
                    type = StringChecker.getCostOrIncomeCode(result);
                    //获取收支类型
                    typeStr = StringChecker.getCostOrIncomeTypeStr(wordList, type);
                    if (!Validator.checkVoiceParseResult(moneyStr, typeStr, type)) {
                        //字符串解析失败，终止
                        Toast toast = Toast.makeText(context,
                                ConstantVariable.ERR_RESOLVE_VOICE_FAILED, Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    //页面跳转
                    typeIndex = ConstantVariable.getTypeIndex(typeStr,typeCode);
                    typeCode = type;
                    showVoicePusherDialog(moneyStr, result, typeIndex);
                }
            }

            @Override
            public void onError(SpeechError speechError) {
                Log.e(ConstantVariable.ERR_XUNFEI,speechError.getErrorCode() + speechError.getErrorDescription());
            }
        });
        mDialog.show();
        TextView recorderDialogTextView = (TextView) mDialog.getWindow().getDecorView().findViewWithTag("textlink");

        recorderDialogTextView.setText(ConstantVariable.HINT_VOICE);
    }

    private void showVoicePusherDialog(String montyStr, String remarkStr, int typeIndex) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_dialog, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        Button cancel = view.findViewById(R.id.btn_Chat_Dialog_Cancel);
        Button add = view.findViewById(R.id.btn_Chat_Dialog_Push);
        TextView txvEvent = view.findViewById(R.id.txv_Chat_Dialog_Event);
        TextView txvAmount = view.findViewById(R.id.txv_Chat_Dialog_Amount);
        TextView txvType = view.findViewById(R.id.txv_Chat_Dialog_Type);
        TextView txvRemark = view.findViewById(R.id.txv_Chat_Dialog_Remark);
        Spinner spinner = view.findViewById(R.id.sp_Chat_Type);
        //获取输入对象
        EditText etxMoney = view.findViewById(R.id.etx_Chat_Dialog_Amount);
        EditText etxRemark = view.findViewById(R.id.etx_Chat_Dialog_Remark);
        EditText etxEvent = view.findViewById(R.id.etx_Chat_Dialog_Event);

        txvEvent.setText(typeCode == ConstantVariable.COST_CODE ? ConstantVariable.TEXT_COST_EVENT : ConstantVariable.TEXT_INCOME_EVENT);
        txvAmount.setText(typeCode == ConstantVariable.COST_CODE ? ConstantVariable.TEXT_COST_AMOUNT : ConstantVariable.TEXT_INCOME_AMOUNT);
        txvType.setText(typeCode == ConstantVariable.COST_CODE ? ConstantVariable.TEXT_COST_TYPE : ConstantVariable.TEXT_INCOME_TYPE);
        txvRemark.setText(ConstantVariable.TEXT_REMARK);

        etxEvent.setText(typeCode == ConstantVariable.COST_CODE ? ConstantVariable.TEXT_COST : ConstantVariable.TEXT_INCOME);
        //设置语音识别获取的结果值
        etxMoney.setText(montyStr);
        etxRemark.setText(remarkStr);


        ArrayAdapter<String> spAdapter = new ArrayAdapter<>(context, R.layout.spinner_item_sel,
                ConstantVariable.getTypeArray(typeCode == ConstantVariable.COST_CODE ? ConstantVariable.COST_TYPE : ConstantVariable.INCOME_TYPE));
        spAdapter.setDropDownViewResource(R.layout.spinner_item_drop);
        spinner.setAdapter(spAdapter);

        spinner.setSelection(typeIndex);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = ConstantVariable.getTypeByTypeStr(position,
                        typeCode == ConstantVariable.COST_CODE ? ConstantVariable.COST_TYPE : ConstantVariable.INCOME_TYPE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Not Used
            }
        });

        //向服务器推送信息
        add.setOnClickListener(v -> {
            //数据提取
            event = etxEvent.getText().toString();
            money = etxMoney.getText().toString();
            remark = etxRemark.getText().toString();
            dateStr = DateHandler.getCurrentDatetime();
            if (Validator.checkBillInfoInput(event, money, context)) {
                dialog.dismiss();
                asyncChatTask = new AsyncChatTask();
                asyncChatTask.execute();
            }
        });

        cancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private List<String> parseWordArray(String resultString){
        List<String> singleWordList = new ArrayList<>();
        JSONArray array = JSON.parseObject(resultString).getJSONArray(ConstantVariable.WORDS);
        for (int i = 0; i < array.size(); i++) {
            JSONObject wordsObj = array.getJSONObject(i);
            JSONArray cnWordsArr = wordsObj.getJSONArray(ConstantVariable.CHINESE_WORDS);
            JSONObject singleWordObj = cnWordsArr.getJSONObject(0);
            singleWordList.add(singleWordObj.getString(ConstantVariable.SINGLE_WORD));
        }
        return singleWordList;
    }

    private String parseVoice(List<String> stringList) {
        StringBuilder sb = new StringBuilder();
        for (String str : stringList) {
            sb.append(str);
        }
        return sb.toString();
    }

    private class AsyncPullHistoryTask extends AsyncTask<Void, Void, List<ChatInfo>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            indicatorView.show();
        }

        @Override
        protected List<ChatInfo> doInBackground(Void... voids) {
            infoList = dataPuller.pullHistoryOf(user.getUserNo());
            return infoList;
        }

        @Override
        protected void onPostExecute(List<ChatInfo> chatInfos) {
            super.onPostExecute(chatInfos);
            if (chatInfos != null) {
                adapter = new ChatListAdapter(context, infoList);
                listView.setAdapter(adapter);
            } else {
                Toast toast = Toast.makeText(context,
                        ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_SHORT);
                toast.show();
            }
            indicatorView.hide();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            indicatorView.hide();
        }
    }

    private class AsyncChatTask extends AsyncTask<Void, Void, ChatInfo> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            indicatorView.show();
            String billType = ConstantVariable.COST_CODE == typeCode ? ConstantVariable.TEXT_COST : ConstantVariable.TEXT_INCOME;
            userInfo = new ChatInfo(user.getUserNo(), dateStr,
                    dateStr + "：" + selected +
                            billType +
                            Double.parseDouble(money),
                    1);
            infoList.add(userInfo);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected ChatInfo doInBackground(Void... voids) {
            ChatInfo npcInfo = null;
            if (ConstantVariable.COST_CODE == typeCode) {
                Cost cost = new Cost(event, selected, Double.parseDouble(money),
                        dateStr, remark, user.getUserNo());
                npcInfo = dataPuller.requestCostChat(cost, userInfo);
            } else if (ConstantVariable.INCOME_CODE == typeCode) {
                Income income = new Income(event, selected, Double.parseDouble(money),
                        dateStr, remark, user.getUserNo());
                npcInfo = dataPuller.requestIncomeChat(income, userInfo);
            }
            return npcInfo;
        }

        @Override
        protected void onPostExecute(ChatInfo chatInfo) {
            super.onPostExecute(chatInfo);
            if (chatInfo != null) {
                infoList.add(chatInfo);
                adapter.notifyDataSetChanged();
            } else {
                Toast toast = Toast.makeText(context,
                        ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_SHORT);
                toast.show();
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            indicatorView.hide();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            indicatorView.hide();
        }
    }
}