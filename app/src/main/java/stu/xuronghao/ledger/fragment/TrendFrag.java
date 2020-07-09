package stu.xuronghao.ledger.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import stu.xuronghao.ledger.AAChartCoreLib.AAChartCreator.AAChartModel;
import stu.xuronghao.ledger.AAChartCoreLib.AAChartCreator.AAChartView;
import stu.xuronghao.ledger.AAChartCoreLib.AAChartCreator.AASeriesElement;
import stu.xuronghao.ledger.AAChartCoreLib.AAChartEnum.AAChartType;
import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.adapter.TrendDataAdapter;
import stu.xuronghao.ledger.entity.Cost;
import stu.xuronghao.ledger.entity.Income;
import stu.xuronghao.ledger.entity.TrendData;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.DataPuller;

public class TrendFrag extends Fragment {
    private static final String ARG_USER_INFO = "user";

    private View       rootView;
    private User       user;
    private DataPuller dataPuller = new DataPuller();
    private int        currentYear,
            destinationYear;
    Double[] monthlyCost    = new Double[12];
    Double[] monthlyIncome  = new Double[12];
    Double[] monthlySurplus = new Double[12];
    private TrendData   yearlyData;
    private TrendData[] monthlyData = new TrendData[12];
    private String      startDate,
            endDate;
    private Calendar calendar;

    public TrendFrag() {
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
        rootView = inflater.inflate(R.layout.fragment_trend, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user = (User) getActivity().getIntent().getSerializableExtra(ARG_USER_INFO);
        setCurrentYear();
        setDateRange();
        setDateChanger();
        buildTrendChart();
    }

    //获取当前月份
    private void setCurrentYear() {
        TimeZone zone = TimeZone.getTimeZone("GMT+8:00");
        calendar = Calendar.getInstance(zone);
        currentYear = calendar.get(Calendar.YEAR);
        destinationYear = currentYear;
    }

    //设置日期范围
    private void setDateRange() {
        //获取月初日期
        startDate = destinationYear + "-01-01";

        //获取月末日期
        endDate = destinationYear + "-12-31";
    }

    //设置日期选择器
    private void setDateChanger() {
        //图像控件
        ImageView arrow_Left = rootView.findViewById(R.id.arrow_Trend_LastMonth);
        ImageView arrow_Right = rootView.findViewById(R.id.arrow_Trend_NextMonth);

        arrow_Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destinationYear--;
                setDateRange();
                buildTrendChart();
            }
        });

        arrow_Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (destinationYear < currentYear) {
                    destinationYear++;
                    setDateRange();
                    buildTrendChart();
                } else {
                    Toast toast = Toast.makeText(getContext(),
                            "果然不能预知未来呢~~~", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }

    //获取月度信息
    //    private void getMonthInfo(){
    //        //月度
    //        TextView txv_monthIncome = rootView.findViewById(R.id.txv_Trend_Month_Income);
    //        TextView txv_monthCost = rootView.findViewById(R.id.txv_Trend_Month_Cost);
    //        TextView txv_monthSurplus = rootView.findViewById(R.id.txv_Trend_Month_Surplus);
    //
    //    }

    //生成图形化报表
    private void buildTrendChart() {
        //获取可视化控件
        AAChartView trendView = rootView.findViewById(R.id.TrendChart);
        AAChartModel trendModel;
        //获取列表控件
        ListView listView = rootView.findViewById(R.id.lv_Trend_DivideByTypes);
        //暂存所有数据
        List<Cost> tempCost = dataPuller.PullCostOfBetween(user, startDate, endDate);
        List<Income> tempIncome = dataPuller.PullIncomeOfBetween(user, startDate, endDate);
        if (tempCost == null || tempIncome == null) {
            Toast toast = Toast.makeText(getContext(),
                    "似乎和服务器君失去了联系...请检查网络连接哦~~~", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        double amount;
        int size = Math.max(tempCost.size(), tempIncome.size());
        int index, month;
        String type, date;
        String[] months = new String[12];
        //进行初始化
        for (int i = 0; i < 12; i++) {
            monthlyData[i] = new TrendData(destinationYear, i + 1);
            yearlyData = new TrendData(destinationYear, 0);
            monthlyCost[i] = 0.0;
            monthlyIncome[i] = 0.0;
            monthlySurplus[i] = 0.0;
            if ((i + 1) < 10)
                months[i] = "0" + (i + 1) + "月";
            else
                months[i] = (i + 1) + "月";
        }
        //        yearlyData = new TrendData(destinationYear,0);

        //数据处理
        for (int i = 0; i < size; i++) {
            //支出
            if (tempCost.size() >= i + 1) {
                Cost cost = tempCost.get(i);
                amount = cost.getCostAmount();
                date = cost.getCostDate();
                //从日期中截取月份
                month = Integer.parseInt(date.split("-")[1]);
                //进行分类累计
                //进行月度累计
                monthlyCost[month - 1] += amount;
            }
            //收入
            if (tempIncome.size() >= i + 1) {
                Income income = tempIncome.get(i);
                amount = income.getIncAmount();
                date = income.getIncDate();
                //从日其中截取月份
                month = Integer.parseInt(date.split("-")[1]);
                //进行月度累计
                monthlyIncome[month - 1] += amount;
            }
        }
        //分别存储信息
        ArrayList<TrendData> listData = new ArrayList<TrendData>();
        //计算结余
        for (int i = 0; i < 12; i++) {
            monthlySurplus[i] = monthlyIncome[i] - monthlyCost[i];
            monthlyData[i].setCost(monthlyCost[i]);
            monthlyData[i].setIncome(monthlyIncome[i]);
            monthlyData[i].setSurplus(monthlySurplus[i]);
            //计算年度信息
            yearlyData.setIncome(yearlyData.getIncome() + monthlyIncome[i]);
            yearlyData.setCost(yearlyData.getCost() + monthlyCost[i]);
            if (!(monthlyIncome[i] == 0 && monthlyCost[i] == 0 && monthlySurplus[i] == 0))
                listData.add(monthlyData[i]);
        }
        yearlyData.setSurplus(yearlyData.getIncome() - yearlyData.getCost());
        //生成趋势报表
        trendModel = new AAChartModel()
                .chartType(AAChartType.Line)
                .title("")
                .backgroundColor("#FFC107")
                .dataLabelsEnabled(false)
                .yAxisGridLineWidth(0f);
        trendModel.yAxisTitle = "金额";
        trendModel.categories(months);
        trendModel.series(new AASeriesElement[]{
                new AASeriesElement().name("收入").data(monthlyIncome).color("#2BA951"),
                new AASeriesElement().name("支出").data(monthlyCost).color("#D33428"),
                new AASeriesElement().name("结余").data(monthlySurplus).color("#2BB1EA")
        });
        //绘制线条
        trendView.aa_drawChartWithChartModel(trendModel);

        //设置年度信息
        TextView txv_yearTit = rootView.findViewById(R.id.txv_Trend_Year);
        TextView txv_totalIncome = rootView.findViewById(R.id.txv_Trend_Total_Income);
        TextView txv_totalCost = rootView.findViewById(R.id.txv_Trend_Total_Cost);
        TextView txv_totalSurplus = rootView.findViewById(R.id.txv_Trend_Total_Surplus);

        txv_yearTit.setText(destinationYear + "年");
        txv_totalIncome.setText(yearlyData.getIncome() + "元");
        txv_totalCost.setText(yearlyData.getCost() + "元");
        if(yearlyData.getSurplus()>0){
            txv_totalSurplus.setTextColor(txv_totalSurplus.getResources().getColor(R.color.possurplus));
        } else {
            txv_totalSurplus.setTextColor(txv_totalSurplus.getResources().getColor(R.color.negsurplus));
        }
        txv_totalSurplus.setText(yearlyData.getSurplus() + "元");

        //生成下方列表
        TrendDataAdapter adapter = new TrendDataAdapter(getContext(), listData);
        listView.setAdapter(adapter);
    }
}