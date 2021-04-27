package stu.xuronghao.ledger.fragment;

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

    private static final String[] costType = {"餐饮", "交通", "服饰", "日用", "其他"};
    private static final String ARG_USER_INFO = "user";

    private View rootView;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private String selected;
    private User user;
    private DataPuller dataPuller = new DataPuller();

    public PushCostFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        if (getArguments() != null) {
        //        }
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

        user = (User) getActivity().getIntent().getSerializableExtra(ARG_USER_INFO);

        Button pushCost = rootView.findViewById(R.id.btn_PushCost);
        Button cancel = rootView.findViewById(R.id.btn_PushCost_Cancel);

        spinner = rootView.findViewById(R.id.sp_CostType);
        //进行数据填充
        adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_sel, costType);
        //自定义下拉样式
        adapter.setDropDownViewResource(R.layout.spinner_item_drop);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = costType[position];
                Log.w("selected:", selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pushCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (push()) {
                    Toast toast = Toast.makeText(getContext(), "好的！服务器君记住了！", Toast.LENGTH_SHORT);
                    toast.show();
                    getActivity().finish();
                } else {
                    Toast toast = Toast.makeText(getContext(),
                            ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    private boolean push() {
        Cost cost;

        //获取输入对象
        EditText etx_CostEvent = rootView.findViewById(R.id.etx_CostEvent);
        EditText etx_CostMoney = rootView.findViewById(R.id.etx_CostMoney);
        EditText etx_CostRemark = rootView.findViewById(R.id.etx_CostRemark);

        //数据提取
        String event = etx_CostEvent.getText().toString(),
                money = etx_CostMoney.getText().toString(),
                remark = etx_CostRemark.getText().toString(),
                dateStr = DateHandler.getCurrentDatetime();

        //输入检查
        if (Validator.checkBillInfoInput(event, money,getContext())) {
            cost = new Cost(event, selected, Double.parseDouble(money),
                    dateStr, remark, user.getUserNo());
            return dataPuller.pushCost(cost);
        }
        return false;
    }
}