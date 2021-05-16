package stu.xuronghao.ledger.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.adapter.ChatListAdapter;
import stu.xuronghao.ledger.entity.ChatInfo;
import stu.xuronghao.ledger.entity.Cost;
import stu.xuronghao.ledger.entity.Income;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.ConstantVariable;
import stu.xuronghao.ledger.handler.DataPuller;
import stu.xuronghao.ledger.handler.DateHandler;
import stu.xuronghao.ledger.handler.Validator;

public class ChatToRecordPage extends AppCompatActivity {
    private ChatListAdapter adapter;
    private ListView listView;
    private List<ChatInfo> infoList;
    private User user;
    private ChatInfo userInfo;
    private Context context;
    private String selected;
    private View view;
    private EditText etxEvent;
    private AVLoadingIndicatorView indicatorView;
    private String event;
    private String money;
    private String remark;
    private String dateStr;
    private AsyncChatTask asyncChatTask;
    private AsyncPullHistoryTask asyncPullHistoryTask;

    private final DataPuller dataPuller = new DataPuller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_to_record_page);
        user = (User) getIntent().getSerializableExtra(ConstantVariable.USER);
        context = this;
        asyncPullHistoryTask = new AsyncPullHistoryTask();
        asyncPullHistoryTask.execute();
    }

    private void showPusherDialog(int mode) {
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        view = LayoutInflater.from(this).inflate(R.layout.chat_dialog, null, false);
        Button cancel = view.findViewById(R.id.btn_Chat_Dialog_Cancel);
        Button add = view.findViewById(R.id.btn_Chat_Dialog_Push);
        TextView txvEvent = view.findViewById(R.id.txv_Chat_Dialog_Event);
        TextView txvAmount = view.findViewById(R.id.txv_Chat_Dialog_Amount);
        TextView txvType = view.findViewById(R.id.txv_Chat_Dialog_Type);
        TextView txvRemark = view.findViewById(R.id.txv_Chat_Dialog_Remark);
        Spinner spinner = view.findViewById(R.id.sp_Chat_Type);
        etxEvent = view.findViewById(R.id.etx_Chat_Dialog_Event);

        etxEvent.setText(mode == ConstantVariable.COST_CODE ? ConstantVariable.TEXT_COST : ConstantVariable.TEXT_INCOME);
        txvEvent.setText(mode == ConstantVariable.COST_CODE ? ConstantVariable.TEXT_COST_EVENT : ConstantVariable.TEXT_INCOME_EVENT);
        txvAmount.setText(mode == ConstantVariable.COST_CODE ? ConstantVariable.TEXT_COST_AMOUNT : ConstantVariable.TEXT_INCOME_AMOUNT);
        txvType.setText(mode == ConstantVariable.COST_CODE ? ConstantVariable.TEXT_COST_TYPE : ConstantVariable.TEXT_INCOME_TYPE);
        txvRemark.setText(ConstantVariable.TEXT_REMARK);

        ArrayAdapter<String> spAdapter = new ArrayAdapter<>(context, R.layout.spinner_item_sel,
                ConstantVariable.getTypeArray(mode == ConstantVariable.COST_CODE ? ConstantVariable.COST_TYPE : ConstantVariable.INCOME_TYPE));
        spAdapter.setDropDownViewResource(R.layout.spinner_item_drop);
        spinner.setAdapter(spAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = ConstantVariable.getType(
                        mode == ConstantVariable.COST_CODE ? ConstantVariable.COST_TYPE : ConstantVariable.INCOME_TYPE,
                        position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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

    private class AsyncPullHistoryTask extends AsyncTask<Void,Void,List<ChatInfo>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listView = findViewById(R.id.lv_chat);
            indicatorView = findViewById(R.id.avi_chat);
            //返回
            ImageView cancel = findViewById(R.id.img_chat_page_cancel);
            cancel.setOnClickListener(v -> finish());
            Button costBtn = findViewById(R.id.btn_Cost_Dialog);
            costBtn.setOnClickListener(v -> showPusherDialog(ConstantVariable.COST_CODE));
            Button incomeBtn = findViewById(R.id.btn_Income_Dialog);
            incomeBtn.setOnClickListener(v -> showPusherDialog(ConstantVariable.INCOME_CODE));
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

    private class AsyncChatTask extends AsyncTask<Integer, Void, ChatInfo> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            indicatorView.show();
            userInfo = new ChatInfo(user.getUserNo(), dateStr,
                    dateStr + "：" + selected + ConstantVariable.TEXT_COST + Double.parseDouble(money),
                    1);
            infoList.add(userInfo);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected ChatInfo doInBackground(Integer... mode) {
            ChatInfo npcInfo = null;
            if (ConstantVariable.COST_CODE == mode[0]) {
                Cost cost = new Cost(event, selected, Double.parseDouble(money),
                        dateStr, remark, user.getUserNo());
                npcInfo = dataPuller.requestCostChat(cost, userInfo);
            } else if (ConstantVariable.INCOME_CODE == mode[0]) {
                Income income = new Income(event, selected, Double.parseDouble(money),
                        dateStr, remark, user.getUserNo());
                npcInfo = dataPuller.requestIncomeChat(income, userInfo);
            }
            if (npcInfo == null) {
                return null;
            }
            return npcInfo;
        }

        @Override
        protected void onPostExecute(ChatInfo chatInfo) {
            super.onPostExecute(chatInfo);
            if(chatInfo != null){
                infoList.add(chatInfo);
                adapter.notifyDataSetChanged();
            }else {
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
}