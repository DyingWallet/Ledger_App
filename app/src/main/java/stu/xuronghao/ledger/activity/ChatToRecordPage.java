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
import stu.xuronghao.ledger.handler.ConstantVariable;
import stu.xuronghao.ledger.handler.DataPuller;
import stu.xuronghao.ledger.handler.DateHandler;
import stu.xuronghao.ledger.handler.Validator;

public class ChatToRecordPage extends AppCompatActivity {
    private ChatListAdapter adapter;
    private ArrayAdapter<String> spAdapter;
    private ListView listView;
    private List<ChatInfo> infoList;
    private ChatInfo userInfo;
    private ChatInfo npcInfo;
    private User user;
    private Context context;
    private String selected;
    private View view;
    private final DataPuller dataPuller = new DataPuller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_to_record_page);
        context = this;
        listView = findViewById(R.id.lv_chat);
        user = (User) getIntent().getSerializableExtra(ConstantVariable.USER);
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
        infoList = dataPuller.pullHistoryOf(user.getUserNo());

        if (infoList == null) {
            Toast toast = Toast.makeText(context,
                    ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_SHORT);
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
        TextView txvEvent = view.findViewById(R.id.txv_Chat_Dialog_Event);
        TextView txvAmount = view.findViewById(R.id.txv_Chat_Dialog_Amount);
        TextView txvType = view.findViewById(R.id.txv_Chat_Dialog_Type);
        TextView txvRemark = view.findViewById(R.id.txv_Chat_Dialog_Remark);
        Spinner spinner = view.findViewById(R.id.sp_Chat_Type);
        EditText text = view.findViewById(R.id.etx_Chat_Dialog_Event);
        text.setText(ConstantVariable.COST_CN);

        txvEvent.setText("支出事件：");
        txvAmount.setText("支出金额：");
        txvType.setText("支出类型：");
        txvRemark.setText("备注：");

        spAdapter = new ArrayAdapter<>(context, R.layout.spinner_item_sel, ConstantVariable.getTypeArray(ConstantVariable.COST_TYPE));
        spAdapter.setDropDownViewResource(R.layout.spinner_item_drop);
        spinner.setAdapter(spAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = ConstantVariable.getType(ConstantVariable.COST_TYPE, position);
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
                EditText etxCostEvent = view.findViewById(R.id.etx_Chat_Dialog_Event);
                EditText etxCostMoney = view.findViewById(R.id.etx_Chat_Dialog_Amount);
                EditText etxCostRemark = view.findViewById(R.id.etx_Chat_Dialog_Remark);

                //数据提取
                String event = etxCostEvent.getText().toString();
                String money = etxCostMoney.getText().toString();
                String remark = etxCostRemark.getText().toString();
                String dateStr = DateHandler.getCurrentDatetime();
                if (Validator.checkBillInfoInput(event, money,context)) {
                    Cost cost = new Cost(event, selected, Double.parseDouble(money),
                            dateStr, remark, user.getUserNo());
                    userInfo = new ChatInfo(user.getUserNo(), dateStr,
                            dateStr + "：" + selected + ConstantVariable.COST_CN + Double.parseDouble(money),
                            1);
                    infoList.add(userInfo);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                    npcInfo = dataPuller.requestCostChat(cost, userInfo);
                    if (npcInfo == null) {
                        Toast toast = Toast.makeText(context,
                                ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_SHORT);
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
        view = LayoutInflater.from(this).inflate(R.layout.chat_dialog, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        Button cancel = view.findViewById(R.id.btn_Chat_Dialog_Cancel);
        Button push = view.findViewById(R.id.btn_Chat_Dialog_Push);
        TextView txvEvent = view.findViewById(R.id.txv_Chat_Dialog_Event);
        TextView txvAmount = view.findViewById(R.id.txv_Chat_Dialog_Amount);
        TextView txvType = view.findViewById(R.id.txv_Chat_Dialog_Type);
        TextView txvRemark = view.findViewById(R.id.txv_Chat_Dialog_Remark);
        Spinner spinner = view.findViewById(R.id.sp_Chat_Type);
        EditText text = view.findViewById(R.id.etx_Chat_Dialog_Event);
        text.setText(ConstantVariable.INCOME_CN);

        txvEvent.setText("收入事件：");
        txvAmount.setText("收入金额：");
        txvType.setText("收入类型：");
        txvRemark.setText("备注：");

        spAdapter = new ArrayAdapter<>(context, R.layout.spinner_item_sel, ConstantVariable.getTypeArray(ConstantVariable.INCOME_TYPE));
        spAdapter.setDropDownViewResource(R.layout.spinner_item_drop);
        spinner.setAdapter(spAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = ConstantVariable.getType(ConstantVariable.INCOME_TYPE, position);
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
                EditText etxIncomeEvent = view.findViewById(R.id.etx_Chat_Dialog_Event);
                EditText etxIncomeMoney = view.findViewById(R.id.etx_Chat_Dialog_Amount);
                EditText etxIncomeRemark = view.findViewById(R.id.etx_Chat_Dialog_Remark);

                //数据提取
                String event = etxIncomeEvent.getText().toString();
                String money = etxIncomeMoney.getText().toString();
                String remark = etxIncomeRemark.getText().toString();
                String dateStr = DateHandler.getCurrentDatetime();
                if (Validator.checkBillInfoInput(event, money,context)) {
                    Income income = new Income(event, selected, Double.parseDouble(money),
                            dateStr, remark, user.getUserNo());
                    userInfo = new ChatInfo(user.getUserNo(), dateStr,
                            dateStr + "：" + selected + ConstantVariable.INCOME_CN + Double.parseDouble(money),
                            1);
                    infoList.add(userInfo);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                    npcInfo = dataPuller.requestIncomeChat(income, userInfo);
                    if (npcInfo == null) {
                        Toast toast = Toast.makeText(context,
                                ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_SHORT);
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
}