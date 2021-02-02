package stu.xuronghao.ledger.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.don.pieviewlibrary.PercentPieView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.adapter.PieDataAdapter;
import stu.xuronghao.ledger.entity.Cost;
import stu.xuronghao.ledger.entity.Income;
import stu.xuronghao.ledger.entity.TotalFee;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.DataPuller;
import stu.xuronghao.ledger.handler.FeeSortUtil;

public class PieFrag extends Fragment {
    //设置模式
    private static final int COST_MODE = 0;
    private static final int INCOME_MODE = 1;
    private static final int[] colors = {R.color.picaPink, R.color.dining,
            R.color.trans, R.color.cloth, R.color.daily};
    private static final int[] costIcons = {R.drawable.icon_dining, R.drawable.icon_trans,
            R.drawable.icon_cloth, R.drawable.icon_daily, R.drawable.icon_other_cost};
    private static final int[] incomeIcons = {R.drawable.icon_salary, R.drawable.icon_bonus,
            R.drawable.icon_loan, R.drawable.icon_redpkt, R.drawable.icon_other_income};
    private static final String ARG_USER_INFO = "user";
    private static final String[] costType = {"餐饮", "交通", "服饰", "日用", "其他"};
    private static final String[] incomeType = {"工资", "奖金", "借款", "红包", "其他"};

    private static final HashMap<String, Integer> costTypeMap =
            new HashMap<String, Integer>() {
                {
                    put("餐饮", 0);
                    put("交通", 1);
                    put("服饰", 2);
                    put("日用", 3);
                    put("其他", 4);
                }
            };

    private static final HashMap<String, Integer> incomeTypeMap =
            new HashMap<String, Integer>() {
                {
                    put("工资", 0);
                    put("奖金", 1);
                    put("借款", 2);
                    put("红包", 3);
                    put("其他", 4);
                }
            };
    //模式标记
    private static int mode = COST_MODE;

    private View rootView;
    private User user;
    private DataPuller dataPuller = new DataPuller();
    private int currentMonth,
            destinationMonth;
    private String startDate,
            endDate;
    private List<Cost> costList;
    private List<Income> incomeList;
    private Calendar calendar;

    public PieFrag() {
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
        rootView = inflater.inflate(R.layout.fragment_pie, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user = (User) getActivity().getIntent().getSerializableExtra(ARG_USER_INFO);
        //获取当前月份
        setCurrentMonth();
        //设置日期范围
        setDateRange();
        //设置日期转换器
        setDateChanger();
        //生成图形化报表
        buildPieView(mode);
        //设置模式转换监听器
        ImageView img_switch = rootView.findViewById(R.id.pie_click_switch);
        img_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode != COST_MODE) {
                    mode = COST_MODE;
                } else {
                    mode = INCOME_MODE;
                }
                buildPieView(mode);
            }
        });
    }

    //获取当前月份
    private void setCurrentMonth() {
        TimeZone zone = TimeZone.getTimeZone("GMT+8:00");
        calendar = Calendar.getInstance(zone);
        currentMonth = calendar.get(Calendar.MONTH);
        destinationMonth = currentMonth;
    }

    //设置日期范围
    private void setDateRange() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar.set(Calendar.MONTH, destinationMonth);
        //获取月初日期
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        startDate = dateFormat.format(calendar.getTime()) + " 00:00:00";

        //获取月末日期
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        endDate = dateFormat.format(calendar.getTime()) + " 24:00:00";
    }

    //设置日期选择器
    private void setDateChanger() {
        //图像控件
        ImageView arrow_Left = rootView.findViewById(R.id.arrow_Pie_LastMonth);
        ImageView arrow_Right = rootView.findViewById(R.id.arrow_Pie_NextMonth);

        arrow_Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destinationMonth--;
                setDateRange();
                buildPieView(mode);
            }
        });

        arrow_Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (destinationMonth < currentMonth) {
                    destinationMonth++;
                    setDateRange();
                    buildPieView(mode);
                } else {
                    Toast toast = Toast.makeText(getContext(), "果然不能预知未来呢~~~", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }

    //生成图形化报表
    private void buildPieView(int mode) {
        //文本控件
        TextView txv_StartDate = rootView.findViewById(R.id.txv_Pie_StartDate);
        TextView txv_EndDate = rootView.findViewById(R.id.txv_Pie_EndDate);
        TextView pieTit = rootView.findViewById(R.id.pie_chart_title);
        TextView pieAmount = rootView.findViewById(R.id.pie_chart_money);
        //可视化控件
        PercentPieView pieView = rootView.findViewById(R.id.pie_Report);
        //列表控件
        ListView listView = rootView.findViewById(R.id.lv_Pie_DivideByTypes);
        //适配器
        PieDataAdapter adapter;
        //设置始止时间
        txv_StartDate.setText(startDate.split("\\s")[0]);
        txv_EndDate.setText(endDate.split("\\s")[0]);
        //设置比较器
//        FeeSortUtil sortUtil = new FeeSortUtil();
        String total;
        double tmp = 0;
        int[] data = new int[5];
        if (mode == COST_MODE) {
            //消费报表
            costList = dataPuller.PullCostOfBetween(user, startDate, endDate);
            if (costList == null) {
                Toast toast = Toast.makeText(getContext(),
                        "似乎和服务器君失去了联系...请检查网络连接哦~~~", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            double[] costs = {0, 0, 0, 0, 0};

            for (Cost cost : costList) {
                //costType = {"餐饮","交通","服饰","日用","其他"}
                String type = cost.getCostType();
                int index = costTypeMap.get(type).intValue();
                costs[index] += cost.getCostAmount();
            }
            for (int i = 0; i < costs.length; i++) {
                data[i] = (int) costs[i];
                tmp += costs[i];
            }
            total = tmp + "元";
            pieView.setData(data, costType, colors);
            //设置金额总和
            pieAmount.setText(total);
            //设置报表标题
            pieTit.setText("总消费");
            //分类列表
            ArrayList<TotalFee> feeList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                if (costs[i] != 0)
                    feeList.add(new TotalFee(costIcons[i], costs[i], costType[i]));
            }
            new FeeSortUtil().sortByAmount(feeList);
            adapter = new PieDataAdapter(getContext(), feeList);
            listView.setAdapter(adapter);
        } else if (mode == INCOME_MODE) {
            //收入报表
            incomeList = dataPuller.PullIncomeOfBetween(user, startDate, endDate);
            if (incomeList == null) {
                Toast toast = Toast.makeText(getContext(),
                        "似乎和服务器君失去了联系...请检查网络连接哦~~~", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            double[] incomes = {0, 0, 0, 0, 0};

            for (Income income : incomeList) {
                //incomeType = {"工资","奖金","借款","红包","其他"};
                String type = income.getIncType();
                int index = incomeTypeMap.get(type);
                incomes[index] += income.getIncAmount();
            }
            for (int i = 0; i < incomes.length; i++) {
                data[i] = (int) incomes[i];
                tmp += incomes[i];
            }
            total = tmp + "元";
            pieView.setData(data, incomeType, colors);
            pieAmount.setText(total);
            pieTit.setText("总收入");
            //分类列表
            ArrayList<TotalFee> feeList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                if (incomes[i] != 0)
                    feeList.add(new TotalFee(incomeIcons[i], incomes[i], incomeType[i]));
            }
            new FeeSortUtil().sortByAmount(feeList);
            adapter = new PieDataAdapter(getContext(), feeList);
            listView.setAdapter(adapter);
        }
    }
}