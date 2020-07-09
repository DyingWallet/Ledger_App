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
import stu.xuronghao.ledger.entity.Income;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.DataPuller;
import stu.xuronghao.ledger.handler.DateHandler;

public class PushIncomeFrag extends Fragment {

    private static final String[] incomeType    = {"工资", "奖金", "借款", "红包", "其他"};
    private static final String   ARG_USER_INFO = "user";

    private View                 rootView;
    private Spinner              spinner;
    private ArrayAdapter<String> adapter;
    private String               selected;
    private User                 user;
    private DataPuller dataPuller = new DataPuller();

    public PushIncomeFrag() {
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
        rootView = inflater.inflate(R.layout.fragment_push_income, container, false);

        Log.w("PIF: ", "Called!");
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        user = (User) getActivity().getIntent().getSerializableExtra(ARG_USER_INFO);

        Button pushIncome = rootView.findViewById(R.id.btn_PushIncome);
        Button cancel = rootView.findViewById(R.id.btn_PushIncome_Cancel);

        spinner = rootView.findViewById(R.id.sp_IncomeType);

        adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_sel, incomeType);

        adapter.setDropDownViewResource(R.layout.spinner_item_drop);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = incomeType[position];
                Log.w("selected:", selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pushIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (push()) {
                    Toast toast = Toast.makeText(getContext(), "好的！服务器君记住了！", Toast.LENGTH_SHORT);
                    toast.show();
                    getActivity().finish();
                } else {
                    Toast toast = Toast.makeText(getContext(),
                            "似乎和服务器君失去了联系...请检查网络连接哦~~~", Toast.LENGTH_LONG);
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
        Income income;

        //获取输入对象
        EditText etx_IncomeEvent = rootView.findViewById(R.id.etx_IncomeEvent);
        EditText etx_IncomeMoney = rootView.findViewById(R.id.etx_IncomeMoney);
        EditText etx_IncomeRemark = rootView.findViewById(R.id.etx_IncomeRemark);

        //数据提取
        String event = etx_IncomeEvent.getText().toString(),
                money = etx_IncomeMoney.getText().toString(),
                remark = etx_IncomeRemark.getText().toString(),
                dateStr = DateHandler.getCurrentDatetime();

        //输入检查
        if (CheckInput(event, money)) {
            income = new Income(event, selected, Double.parseDouble(money),
                    dateStr, remark, user.getUserNo());
            return dataPuller.PushIncome(income);
        }
        return false;
    }

    private boolean CheckInput(String event, String money) {
        if (!event.isEmpty()) {
            if (!money.isEmpty() && Double.parseDouble(money) != 0) {
                return true;
            } else {
                Toast toast = Toast.makeText(getContext(), "请输入金额！", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(getContext(), "请输入事件！", Toast.LENGTH_SHORT);
            toast.show();
        }
        return false;
    }

}