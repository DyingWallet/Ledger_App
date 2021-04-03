package stu.xuronghao.ledger.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import java.util.List;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.adapter.ChatListAdapter;
import stu.xuronghao.ledger.entity.ChatInfo;
import stu.xuronghao.ledger.entity.Cost;
import stu.xuronghao.ledger.entity.Income;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.DataPuller;
import stu.xuronghao.ledger.handler.DateHandler;

public class ChatToRecordPage extends AppCompatActivity {
    private static final String[] costType = {"餐饮", "交通", "服饰", "日用", "其他"};
    private static final String[] incomeType = {"工资", "奖金", "借款", "红包", "其他"};
    private static final String ARG_USER_INFO = "user";

    private ChatListAdapter adapter;
    private ArrayAdapter<String> spAdapter;
    private ListView listView;
    private List<ChatInfo> infoList;
    private ChatInfo userInfo,
            npcInfo;
    private User user;
    private DataPuller dataPuller = new DataPuller();
    private Context context;
    private String selected;
    private Cost cost;
    private Income income;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_to_record_page);
        context = this;
        listView = findViewById(R.id.lv_chat);
        user = (User) getIntent().getSerializableExtra("user");
        //返回
        ImageView cancel = findViewById(R.id.img_chat_page_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buildListView();

        final Button costBtn = findViewById(R.id.btn_Cost_Dialog);
        costBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCostDialog();
            }
        });

        final Button incomeBtn = findViewById(R.id.btn_Income_Dialog);
        incomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIncomeDialog();
            }
        });

    }

    private void buildListView() {
        infoList = dataPuller.PullHistoryOf(user.getUserNo());

        if (infoList == null) {
            Toast toast = Toast.makeText(context,
                    "似乎和服务器君失去了联系...请检查网络连接哦~~~", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        adapter = new ChatListAdapter(context, infoList);
        listView.setAdapter(adapter);
    }

    private void showCostDialog() {
        view = LayoutInflater.from(this).inflate(R.layout.chat_dialog, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        Button cancel = view.findViewById(R.id.btn_Chat_Dialog_Cancel);
        Button push = view.findViewById(R.id.btn_Chat_Dialog_Push);
        TextView txv_Event = view.findViewById(R.id.txv_Chat_Dialog_Event);
        TextView txv_Amount = view.findViewById(R.id.txv_Chat_Dialog_Amount);
        TextView txv_Type = view.findViewById(R.id.txv_Chat_Dialog_Type);
        TextView txv_Remark = view.findViewById(R.id.txv_Chat_Dialog_Remark);
        Spinner spinner = view.findViewById(R.id.sp_Chat_Type);

        txv_Event.setText("支出事件：");
        txv_Amount.setText("支出金额：");
        txv_Type.setText("支出类型：");
        txv_Remark.setText("备注：");

        spAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item_sel, costType);
        spAdapter.setDropDownViewResource(R.layout.spinner_item_drop);
        spinner.setAdapter(spAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = costType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //向服务器推送信息
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入对象
                EditText etx_CostEvent = view.findViewById(R.id.etx_Chat_Dialog_Event);
                EditText etx_CostMoney = view.findViewById(R.id.etx_Chat_Dialog_Amount);
                EditText etx_CostRemark = view.findViewById(R.id.etx_Chat_Dialog_Remark);

                //数据提取
                String event = etx_CostEvent.getText().toString(),
                        money = etx_CostMoney.getText().toString(),
                        remark = etx_CostRemark.getText().toString(),
                        dateStr = DateHandler.getCurrentDatetime();
                if (CheckInput(event, money)) {
                    cost = new Cost(event, selected, Double.parseDouble(money),
                            dateStr, remark, user.getUserNo());
                    userInfo = new ChatInfo(user.getUserNo(), dateStr,
                            dateStr + "：" + selected + "支出" + Double.parseDouble(money),
                            1);
                    infoList.add(userInfo);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                    npcInfo = dataPuller.requestCostChat(cost, userInfo);
                    if (npcInfo == null) {
                        Toast toast = Toast.makeText(context,
                                "似乎和服务器君失去了联系...请检查网络连接哦~~~", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        npcInfo.setIsMeSend(0);
                        infoList.add(npcInfo);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showIncomeDialog() {
        income = new Income();
        view = LayoutInflater.from(this).inflate(R.layout.chat_dialog, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        Button cancel = view.findViewById(R.id.btn_Chat_Dialog_Cancel);
        Button push = view.findViewById(R.id.btn_Chat_Dialog_Push);
        TextView txv_Event = view.findViewById(R.id.txv_Chat_Dialog_Event);
        TextView txv_Amount = view.findViewById(R.id.txv_Chat_Dialog_Amount);
        TextView txv_Type = view.findViewById(R.id.txv_Chat_Dialog_Type);
        TextView txv_Remark = view.findViewById(R.id.txv_Chat_Dialog_Remark);
        Spinner spinner = view.findViewById(R.id.sp_Chat_Type);
        EditText text = view.findViewById(R.id.etx_Chat_Dialog_Event);
        text.setText("收入");

        txv_Event.setText("收入事件：");
        txv_Amount.setText("收入金额：");
        txv_Type.setText("收入类型：");
        txv_Remark.setText("备注：");

        spAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item_sel, incomeType);
        spAdapter.setDropDownViewResource(R.layout.spinner_item_drop);
        spinner.setAdapter(spAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = incomeType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //向服务器推送信息
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入对象
                EditText etx_CostEvent = view.findViewById(R.id.etx_Chat_Dialog_Event);
                EditText etx_CostMoney = view.findViewById(R.id.etx_Chat_Dialog_Amount);
                EditText etx_CostRemark = view.findViewById(R.id.etx_Chat_Dialog_Remark);

                //数据提取
                String event = etx_CostEvent.getText().toString(),
                        money = etx_CostMoney.getText().toString(),
                        remark = etx_CostRemark.getText().toString(),
                        dateStr = DateHandler.getCurrentDatetime();
                if (CheckInput(event, money)) {
                    income = new Income(event, selected, Double.parseDouble(money),
                            dateStr, remark, user.getUserNo());
                    userInfo = new ChatInfo(user.getUserNo(), dateStr,
                            dateStr + "：" + selected + "收入" + Double.parseDouble(money),
                            1);
                    infoList.add(userInfo);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                    npcInfo = dataPuller.requestIncomeChat(income, userInfo);
                    if (npcInfo == null) {
                        Toast toast = Toast.makeText(context,
                                "似乎和服务器君失去了联系...请检查网络连接哦~~~", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        npcInfo.setIsMeSend(0);
                        infoList.add(npcInfo);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private boolean CheckInput(String event, String money) {
        if (!event.isEmpty()) {
            if (!money.isEmpty()) {
                return true;
            } else {
                Toast toast = Toast.makeText(context, "请输入金额！", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(context, "请输入事件！", Toast.LENGTH_SHORT);
            toast.show();
        }
        return false;
    }

}