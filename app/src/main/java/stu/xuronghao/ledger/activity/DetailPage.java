package stu.xuronghao.ledger.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.entity.Cost;
import stu.xuronghao.ledger.entity.Income;
import stu.xuronghao.ledger.handler.DataPuller;

public class DetailPage extends AppCompatActivity {

    private static final int
            COST = 0,
            INCOME = 1;

    private int index;
    private Cost cost;
    private Income income;
    private Context context;
    private DataPuller dataPuller = new DataPuller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        context = this;
        //获取按钮控件
        Button btn_Cancel = findViewById(R.id.btn_Detail_Cancel),
                btn_Save = findViewById(R.id.btn_Detail_Save),
                btn_Del = findViewById(R.id.btn_Detail_Del);
        //获取输入框控件
        EditText etx_Event = findViewById(R.id.etx_Detail_CostEvent),
                etx_Money = findViewById(R.id.etx_Detail_CostMoney),
                etx_Remark = findViewById(R.id.etx_Detail_CostRemark);
        //获取字体控件
        TextView txv_Date = findViewById(R.id.txv_Detail_DateTime);
        TextView txv_Type = findViewById(R.id.txv_Detail_CostType);

        index = getIntent().getIntExtra("index", 0);

        if (index == COST) {
            cost = (Cost) getIntent().getSerializableExtra("cost");
            assert cost != null;
            etx_Event.setText(cost.getCostEvent());
            etx_Money.setText(String.valueOf(cost.getCostAmount()));
            etx_Remark.setText(cost.getCostRemark());
            txv_Date.setText(cost.getCostDate());
            txv_Type.setText(cost.getCostType());
        } else if (index == INCOME) {
            income = (Income) getIntent().getSerializableExtra("income");
            assert income != null;
            etx_Event.setText(income.getIncEvent());
            etx_Money.setText(String.valueOf(income.getIncAmount()));
            etx_Remark.setText(income.getIncRemark());
            txv_Date.setText(income.getIncDate());
            txv_Type.setText(income.getIncType());
        }


        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("！！>>>注意<<<！！");
                builder.setIcon(R.drawable.icon_warn);
                builder.setMessage("注意！资料删掉的话，治账酱就再也想不起来了哦。要继续吗？");
                builder.setPositiveButton(">>>继续<<<", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (delete(index)) {
                            Toast toast = Toast.makeText(context, "哎嘿~~治账酱已经忘得一干二净了！", Toast.LENGTH_SHORT);
                            toast.show();
                            finish();
                            return;
                        }
                        Toast toast = Toast.makeText(context, "似乎和服务器君失去了联系...请检查网络连接哦~~~000", Toast.LENGTH_SHORT);
                        toast.show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("<<<算了>>>", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (update(index)) {
                    Toast toast = Toast.makeText(context, "修改已生效了哦！", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                    return;
                }
                Toast toast = Toast.makeText(context, "似乎和服务器君失去了联系...请检查网络连接哦~~~111", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private boolean delete(int index) {
        if (index == COST)
            return dataPuller.DELCost(cost);
        else if (index == INCOME)
            return dataPuller.DELIncome(income);
        return false;
    }

    private boolean update(int index) {
        EditText etx_Event = findViewById(R.id.etx_Detail_CostEvent),
                etx_Money = findViewById(R.id.etx_Detail_CostMoney),
                etx_Remark = findViewById(R.id.etx_Detail_CostRemark);

        //数据提取
        String event = etx_Event.getText().toString(),
                money = etx_Money.getText().toString(),
                remark = etx_Remark.getText().toString();
        if (CheckInput(event, money)) {
            if (index == COST) {
                cost.setCostEvent(event);
                cost.setCostAmount(Double.parseDouble(money));
                cost.setCostRemark(remark);
                return dataPuller.UPDCost(cost);
            } else if (index == INCOME) {
                income.setIncEvent(event);
                income.setIncAmount(Double.parseDouble(money));
                income.setIncRemark(remark);
                return dataPuller.UPDIncome(income);
            }
        }
        return false;
    }

    private boolean CheckInput(String event, String money) {
        if (!event.isEmpty()) {
            if (!money.isEmpty() && Double.parseDouble(money) != 0) {
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