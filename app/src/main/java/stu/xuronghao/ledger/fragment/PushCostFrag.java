package stu.xuronghao.ledger.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.entity.Cost;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.ConstantVariable;
import stu.xuronghao.ledger.handler.DataPuller;
import stu.xuronghao.ledger.handler.DateHandler;
import stu.xuronghao.ledger.handler.Validator;

public class PushCostFrag extends Fragment {
    private User user;
    private View rootView;
    private Context context;
    private String selected;
    private DataPuller dataPuller = new DataPuller();

    public PushCostFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_push_cost, container, false);

        Log.w("PCF: ", "Called!");
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        user = (User) getActivity().getIntent().getSerializableExtra(ConstantVariable.USER);

        Button pushCost = rootView.findViewById(R.id.btn_PushCost);
        Button cancel = rootView.findViewById(R.id.btn_PushCost_Cancel);

        Spinner spinner = rootView.findViewById(R.id.sp_CostType);
        //进行数据填充
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item_sel, ConstantVariable.getTypeArray(ConstantVariable.COST_TYPE));
        //自定义下拉样式
        adapter.setDropDownViewResource(R.layout.spinner_item_drop);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = ConstantVariable.getTypeByTypeStr(position, ConstantVariable.COST_TYPE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Not Used
            }
        });

        pushCost.setOnClickListener(v -> {
            if (push()) {
                Toast toast = Toast.makeText(context, ConstantVariable.INFO_OPERATE_SUCCESS, Toast.LENGTH_SHORT);
                toast.show();
                getActivity().finish();
            } else {
                Toast toast = Toast.makeText(context,
                        ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_LONG);
                toast.show();
            }
        });

        cancel.setOnClickListener(v -> getActivity().finish());
    }

    private boolean push() {
        Cost cost;

        //获取输入对象
        EditText etxCostEvent = rootView.findViewById(R.id.etx_CostEvent);
        EditText etxCostMoney = rootView.findViewById(R.id.etx_CostMoney);
        EditText etxCostRemark = rootView.findViewById(R.id.etx_CostRemark);

        //数据提取
        String event = etxCostEvent.getText().toString();
        String money = etxCostMoney.getText().toString();
        String remark = etxCostRemark.getText().toString();
        String dateStr = DateHandler.getCurrentDatetime();

        //输入检查
        if (Validator.checkBillInfoInput(event, money,getContext())) {
            cost = new Cost(event, selected, Double.parseDouble(money),
                    dateStr, remark, user.getUserNo());
            return dataPuller.pushCost(cost);
        }
        return false;
    }
}