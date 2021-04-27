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
import stu.xuronghao.ledger.handler.ConstantVariable;
import stu.xuronghao.ledger.handler.DataPuller;
import stu.xuronghao.ledger.handler.DateHandler;
import stu.xuronghao.ledger.handler.Validator;

public class PushIncomeFrag extends Fragment {
    private View rootView;
    private String selected;
    private User user;
    private final DataPuller dataPuller = new DataPuller();

    public PushIncomeFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        user = (User) getActivity().getIntent().getSerializableExtra(ConstantVariable.USER);

        Button pushIncome = rootView.findViewById(R.id.btn_PushIncome);
        Button cancel = rootView.findViewById(R.id.btn_PushIncome_Cancel);

        Spinner spinner = rootView.findViewById(R.id.sp_IncomeType);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item_sel, ConstantVariable.getTypeArray(ConstantVariable.INCOME_TYPE));

        adapter.setDropDownViewResource(R.layout.spinner_item_drop);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = ConstantVariable.getType(ConstantVariable.INCOME_TYPE,position);
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
        Income income;

        //获取输入对象
        EditText etxIncomeEvent = rootView.findViewById(R.id.etx_IncomeEvent);
        EditText etxIncomeMoney = rootView.findViewById(R.id.etx_IncomeMoney);
        EditText etxIncomeRemark = rootView.findViewById(R.id.etx_IncomeRemark);

        //数据提取
        String event = etxIncomeEvent.getText().toString();
        String money = etxIncomeMoney.getText().toString();
        String remark = etxIncomeRemark.getText().toString();
        String dateStr = DateHandler.getCurrentDatetime();

        //输入检查
        if (Validator.checkBillInfoInput(event, money, getContext())) {
            income = new Income(event, selected, Double.parseDouble(money),
                    dateStr, remark, user.getUserNo());
            return dataPuller.pushIncome(income);
        }
        return false;
    }
}