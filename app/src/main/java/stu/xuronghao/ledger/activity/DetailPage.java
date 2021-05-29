package stu.xuronghao.ledger.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.entity.Cost;
import stu.xuronghao.ledger.entity.Income;
import stu.xuronghao.ledger.handler.consts.ConstantVariable;
import stu.xuronghao.ledger.handler.network.DataPuller;
import stu.xuronghao.ledger.handler.validator.Validator;

public class DetailPage extends AppCompatActivity {
    private int typeCode;
    private Cost cost;
    private Income income;
    private Context context;
    private final DataPuller dataPuller = new DataPuller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        context = this;
        //获取按钮控件
        Button btnCancel = findViewById(R.id.btn_Detail_Cancel);
        Button btnSave = findViewById(R.id.btn_Detail_Save);
        Button btnDel = findViewById(R.id.btn_Detail_Del);
        //获取输入框控件
        EditText etxEvent = findViewById(R.id.etx_Detail_CostEvent);
        EditText etxMoney = findViewById(R.id.etx_Detail_CostMoney);
        EditText etxRemark = findViewById(R.id.etx_Detail_CostRemark);
        //获取字体控件
        TextView txvDate = findViewById(R.id.txv_Detail_DateTime);
        TextView txvType = findViewById(R.id.txv_Detail_CostType);

        typeCode = getIntent().getIntExtra(ConstantVariable.TYPE_CODE, 0);

        if (ConstantVariable.COST_CODE == typeCode) {
            cost = (Cost) getIntent().getSerializableExtra(ConstantVariable.COST_TYPE);
            assert cost != null;
            etxEvent.setText(cost.getCostEvent());
            etxMoney.setText(String.valueOf(cost.getCostAmount()));
            etxRemark.setText(cost.getCostRemark());
            txvDate.setText(cost.getCostDate());
            txvType.setText(cost.getCostType());
        } else if (ConstantVariable.INCOME_CODE == typeCode) {
            income = (Income) getIntent().getSerializableExtra(ConstantVariable.INCOME_TYPE);
            assert income != null;
            etxEvent.setText(income.getIncEvent());
            etxMoney.setText(String.valueOf(income.getIncAmount()));
            etxRemark.setText(income.getIncRemark());
            txvDate.setText(income.getIncDate());
            txvType.setText(income.getIncType());
        }


        btnCancel.setOnClickListener(v -> finish());

        btnDel.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(ConstantVariable.TEXT_CAUTION);
            builder.setIcon(R.drawable.icon_warn);
            builder.setMessage(ConstantVariable.TEXT_DELETE_HINT_MSG);
            builder.setPositiveButton(ConstantVariable.TEXT_CONFIRM, (dialog, which) -> {
                if (delete(typeCode)) {
                    Toast toast = Toast.makeText(context, ConstantVariable.INFO_DELETE_SUCCESS, Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                    return;
                }
                Toast toast = Toast.makeText(context, ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_SHORT);
                toast.show();
                dialog.dismiss();
            });
            builder.setNegativeButton(ConstantVariable.TEXT_CANCEL, (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        btnSave.setOnClickListener(v -> {
            if (update(typeCode)) {
                Toast toast = Toast.makeText(context, ConstantVariable.INFO_UPDATE_SUCCESS, Toast.LENGTH_SHORT);
                toast.show();
                finish();
                return;
            }
            Toast toast = Toast.makeText(context, ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_SHORT);
            toast.show();
        });
    }

    private boolean delete(int index) {
        if (ConstantVariable.COST_CODE == index)
            return dataPuller.deleteCost(cost);
        else if (ConstantVariable.INCOME_CODE == index)
            return dataPuller.deleteIncome(income);
        return false;
    }

    private boolean update(int index) {
        EditText etxEvent = findViewById(R.id.etx_Detail_CostEvent);
        EditText etxMoney = findViewById(R.id.etx_Detail_CostMoney);
        EditText etxRemark = findViewById(R.id.etx_Detail_CostRemark);

        //数据提取
        String event = etxEvent.getText().toString();
        String money = etxMoney.getText().toString();
        String remark = etxRemark.getText().toString();
        if (Validator.checkBillInfoInput(event, money,context)) {
            if (ConstantVariable.COST_CODE == index) {
                cost.setCostEvent(event);
                cost.setCostAmount(Double.parseDouble(money));
                cost.setCostRemark(remark);
                return dataPuller.updateCost(cost);
            } else if (ConstantVariable.INCOME_CODE == index) {
                income.setIncEvent(event);
                income.setIncAmount(Double.parseDouble(money));
                income.setIncRemark(remark);
                return dataPuller.updateIncome(income);
            }
        }
        return false;
    }
}