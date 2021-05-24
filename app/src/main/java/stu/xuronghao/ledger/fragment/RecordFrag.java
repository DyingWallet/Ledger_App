package stu.xuronghao.ledger.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.activity.ChatToRecordPage;
import stu.xuronghao.ledger.activity.PushDataPage;
import stu.xuronghao.ledger.adapter.SwitchAdapter;
import stu.xuronghao.ledger.entity.Cost;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.ConstantVariable;
import stu.xuronghao.ledger.handler.DataPuller;
import stu.xuronghao.ledger.handler.Validator;

public class RecordFrag extends Fragment {
    // 参数声明
    private int typeCode = 0;
    private User user;
    private View rootView;
    private Context context;
    private MenuItem menuItem;
    private ViewPager viewPager;
    private TextView txvMonthlyBudget;
    private TextView txvMonthlyCost;
    private TextView txvMonthlySurplus;
    private TextView txvDashBoardHint;
    private LinearLayout linearLayoutDashBoard;
    private FloatingActionsMenu floatMenu;
    private BottomNavigationView navigationView;
    private AVLoadingIndicatorView indicatorView;
    private AsyncDashBoardBuilder dashBoardBuilder;
    private AsyncDashBoardPusher dashBoardPusher;
    private AsyncTask<Void,Void,List<HashMap<String, String>>> listPuller;

    //List对象
    List<Cost> costList;
    String budget;
    //声明子Fragment对象
    CostFrag costFrag = new CostFrag();
    IncomeFrag incomeFrag = new IncomeFrag();

    public RecordFrag() {
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
        rootView = inflater.inflate(R.layout.fragment_record, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(user == null)
            user = (User)getActivity().getIntent().getSerializableExtra(ConstantVariable.USER);
        floatMenu = rootView.findViewById(R.id.float_Menu);
        indicatorView = rootView.findViewById(R.id.avi_record);
        txvMonthlyBudget = rootView.findViewById(R.id.dash_board_budget);
        txvMonthlyCost = rootView.findViewById(R.id.dash_board_cost);
        txvMonthlySurplus = rootView.findViewById(R.id.dash_board_surplus);
        txvDashBoardHint = rootView.findViewById(R.id.dash_board_hint);
        linearLayoutDashBoard = rootView.findViewById(R.id.dash_board_linear);
        linearLayoutDashBoard.setOnClickListener(v -> showPusherDialog());
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        setFloatBtn();
        dashBoardBuilder = new AsyncDashBoardBuilder();
        dashBoardBuilder.execute();
    }

    //初始化ViewPager
    private void initView() {
        viewPager = rootView.findViewById(R.id.vp_Record_Top_Switch);
        navigationView = rootView.findViewById(R.id.Record_Type_Switch);

        List<Fragment> pageList = new ArrayList<>();

        pageList.add(costFrag);
        pageList.add(incomeFrag);

        //设置导航条监听器
        navigationView.setOnNavigationItemSelectedListener(
                item -> {
                    menuItem = item;
                    //0-花销，1-收入
                    if(R.id.costFrag == item.getItemId()){
                        viewPager.setCurrentItem(ConstantVariable.COST_CODE);
                        typeCode = ConstantVariable.COST_CODE;
                    }else{
                        viewPager.setCurrentItem(ConstantVariable.INCOME_CODE);
                        typeCode = ConstantVariable.INCOME_CODE;
                    }
                    floatMenu.collapse();
                    return false;
                }
        );

        //设置ViewPager监听器

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Not Used
            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    navigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = navigationView.getMenu().getItem(position);
                if(R.id.costFrag == menuItem.getItemId()){
                    typeCode = ConstantVariable.COST_CODE;
                }else{
                    typeCode = ConstantVariable.INCOME_CODE;
                }
                floatMenu.collapse();
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Not Used
            }
        });

        viewPager.setAdapter(new SwitchAdapter(getChildFragmentManager(), pageList));
    }

    private void setFloatBtn() {

        final FloatingActionButton chatBtn = rootView.findViewById(R.id.to_chat);
        chatBtn.setOnClickListener(v -> {
            Log.w("chatBtn", "Ready to chat!");
            Intent intent = new Intent(getActivity(), ChatToRecordPage.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });

        final FloatingActionButton addBtn = rootView.findViewById(R.id.add_cost);
        addBtn.setOnClickListener(v -> {
            //新增支出
            Log.w("addBtn: ", "Ready to add cost/income!");
            Intent intent = new Intent(getActivity(), PushDataPage.class);
            intent.putExtra(ConstantVariable.TYPE_CODE, ConstantVariable.COST_CODE);
            intent.putExtra(ConstantVariable.USER, user);
            startActivity(intent);
        });

        final FloatingActionButton refreshBtn = rootView.findViewById(R.id.refresh_cost);
        refreshBtn.setOnClickListener(v -> {
            //更新事件
            if(typeCode == ConstantVariable.COST_CODE) {
                listPuller = costFrag.costPullerFactory();
                listPuller.execute();
            }else if(typeCode == ConstantVariable.INCOME_CODE){
                listPuller = incomeFrag.incomePullerFactory();
                listPuller.execute();
            }
            Toast toast = Toast.makeText(context, "收到服务器君发送的消费数据！", Toast.LENGTH_SHORT);
            toast.show();
        });
    }

    private void showPusherDialog(){
        View view = LayoutInflater.from(context).inflate(R.layout.dash_board_setting_dialog,null,false);
        final AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();

        Button add = view.findViewById(R.id.btn_Dash_Board_Budget_Push);
        Button cancel = view.findViewById(R.id.btn_Dash_Board_Budget_Cancel);

        add.setOnClickListener(v->{
            EditText etxBudget = view.findViewById(R.id.etx_Dash_Board_Budget_Amount);
            budget = etxBudget.getText().toString();
            if(Validator.checkDashBoardBudget(budget)){
                dialog.dismiss();
                dashBoardPusher = new AsyncDashBoardPusher();
                dashBoardPusher.execute();
            }
        });
        cancel.setOnClickListener(v->dialog.dismiss());
        dialog.show();
    }

    private class AsyncDashBoardBuilder extends AsyncTask<Void,Void, Map<String,Double>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            indicatorView.show();
        }

        @Override
        protected Map<String,Double> doInBackground(Void... voids) {
            Map<String,Double> result = new HashMap<>();
            double costAmount = 0;
            double userBudget;
            DataPuller dataPuller = new DataPuller();
            User temp = dataPuller.getUserInfo(user);
            costList = dataPuller.pullCostOf(user);
            if(temp == null || costList == null)
                return null;
            user = temp;
            userBudget = user.getUserBudget();
            for (Cost c : costList) {
                costAmount += c.getCostAmount();
            }

            result.put(ConstantVariable.DASHBOARD_BUDGET,userBudget);
            result.put(ConstantVariable.DASHBOARD_COST,costAmount);
            result.put(ConstantVariable.DASHBOARD_SURPLUS,userBudget - costAmount);
            return result;
        }

        @Override
        protected void onPostExecute(Map<String, Double> results) {
            super.onPostExecute(results);
            if(results!=null) {
                double userBudget = results.get(ConstantVariable.DASHBOARD_BUDGET);
                double userCost = results.get(ConstantVariable.DASHBOARD_COST);
                TextView txvDashBoardSurplusTit = rootView.findViewById(R.id.dash_board_surplus_tit);
                if(userBudget >= userCost){
                    //未超支
                    txvDashBoardSurplusTit.setText(ConstantVariable.HINT_REST_TO_COST);
                    linearLayoutDashBoard.setBackgroundColor(ContextCompat.getColor(context,R.color.themePink));
                    if(userBudget/userCost>=2){
                        txvDashBoardHint.setText(ConstantVariable.HINT_POSITIVE_BUDGET_HIGH);
                        txvMonthlySurplus.setTextColor(ContextCompat.getColor(context,R.color.green));
                    }else {
                        txvDashBoardHint.setText(ConstantVariable.HINT_POSITIVE_BUDGET_LOW);
                        txvMonthlySurplus.setTextColor(ContextCompat.getColor(context,R.color.pureWhite));
                    }
                }else {
                    //超支
                    txvDashBoardSurplusTit.setText(ConstantVariable.WARNING_OVER_COST);
                    txvMonthlySurplus.setTextColor(ContextCompat.getColor(context,R.color.pureWhite));
                    double overCost = userCost - userBudget;
                    if(userBudget / overCost > 1.5){
                        //低超支
                        txvDashBoardHint.setText(ConstantVariable.HINT_NEGATIVE_BUDGET_LOW);
                        linearLayoutDashBoard.setBackgroundColor(ContextCompat.getColor(context,R.color.orange));
                    }else {
                        //高超支
                        txvDashBoardHint.setText(ConstantVariable.HINT_NEGATIVE_BUDGET_HIGH);
                        linearLayoutDashBoard.setBackgroundColor(ContextCompat.getColor(context,R.color.warnRed));
                    }
                }

                txvMonthlyBudget.setText(String.format(Locale.CHINA, "%.2f", userBudget));
                txvMonthlyCost.setText(String.format(Locale.CHINA, "%.2f", results.get(ConstantVariable.DASHBOARD_COST)));
                txvMonthlySurplus.setText(String.format(Locale.CHINA, "%.2f", results.get(ConstantVariable.DASHBOARD_SURPLUS)));
            } else {
                Toast toast = Toast.makeText(context,
                        ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_LONG);
                toast.show();
            }
            indicatorView.hide();
        }
    }

    private class AsyncDashBoardPusher extends AsyncTask<Void,Void,Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            indicatorView.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean res;
            DataPuller dataPuller = new DataPuller();
            user.setUserBudget(Double.parseDouble(budget));
            res = dataPuller.updateUserInfo(user);
            return res;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            boolean res = aBoolean;
            if(res) {
                dashBoardBuilder = new AsyncDashBoardBuilder();
                dashBoardBuilder.execute();
            }else {
                Toast toast = Toast.makeText(context,
                        ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_LONG);
                toast.show();
            }
            indicatorView.hide();
        }
    }
}