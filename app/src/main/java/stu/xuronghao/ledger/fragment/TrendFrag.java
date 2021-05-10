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
import stu.xuronghao.ledger.handler.ConstantVariable;
import stu.xuronghao.ledger.handler.DataPuller;

public class TrendFrag extends Fragment {
    private View rootView;
    private User user;
    private DataPuller dataPuller = new DataPuller();
    private int currentYear;
    private int destinationYear;
    Double[] monthlyCost = new Double[12];
    Double[] monthlyIncome = new Double[12];
    Double[] monthlySurplus = new Double[12];
    private TrendData yearlyData;
    private TrendData[] monthlyData = new TrendData[12];
    private String startDate;
    private String endDate;

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
        user = (User) getActivity().getIntent().getSerializableExtra(ConstantVariable.USER);
        setCurrentYear();
        setDateRange();
        setDateChanger();
        buildTrendChart();
    }

    //获取当前月份
    private void setCurrentYear() {
        TimeZone zone = TimeZone.getTimeZone(ConstantVariable.TIME_ZONE);
        Calendar calendar = Calendar.getInstance(zone);
        currentYear = calendar.get(Calendar.YEAR);
        destinationYear = currentYear;
    }

    //设置日期范围
    private void setDateRange() {
        //获取月初日期
        startDate = destinationYear + ConstantVariable.START_OF_YEAR;

        //获取月末日期
        endDate = destinationYear + ConstantVariable.END_OF_YEAR;
    }

    //设置日期选择器
    private void setDateChanger() {
        //图像控件
        ImageView arrowLeft = rootView.findViewById(R.id.arrow_Trend_LastMonth);
        ImageView arrowRight = rootView.findViewById(R.id.arrow_Trend_NextMonth);

        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destinationYear--;
                setDateRange();
                buildTrendChart();
            }
        });

        arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (destinationYear < currentYear) {
                    destinationYear++;
                    setDateRange();
                    buildTrendChart();
                } else {
                    Toast toast = Toast.makeText(getContext(),
                            ConstantVariable.HINT_DATE_TO_FUTURE, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }

    //生成图形化报表
    private void buildTrendChart() {
        //获取可视化控件
        AAChartView trendView = rootView.findViewById(R.id.TrendChart);
        AAChartModel trendModel;
        //获取列表控件
        ListView listView = rootView.findViewById(R.id.lv_Trend_DivideByTypes);
        //暂存所有数据
        List<Cost> tempCost = dataPuller.pullCostOfBetween(user, startDate, endDate);
        List<Income> tempIncome = dataPuller.pullIncomeOfBetween(user, startDate, endDate);
        if (tempCost == null || tempIncome == null) {
            Toast toast = Toast.makeText(getContext(),
                    ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        double amount;
        int size = Math.max(tempCost.size(), tempIncome.size());
        int month;
        String date;
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

        //数据处理
        for (int i = 0; i < size; i++) {
            //支出
            if (tempCost.size() >= i + 1) {
                Cost cost = tempCost.get(i);
                amount = cost.getCostAmount();
                date = cost.getCostDate();
                //从日期中截取月份
                month = Integer.parseInt(date.split(ConstantVariable.DATE_REGEX)[1]);
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
                month = Integer.parseInt(date.split(ConstantVariable.DATE_REGEX)[1]);
                //进行月度累计
                monthlyIncome[month - 1] += amount;
            }
        }
        //分别存储信息
        ArrayList<TrendData> listData = new ArrayList<>();
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
        TextView txvYearTit = rootView.findViewById(R.id.txv_Trend_Year);
        TextView txvTotalIncome = rootView.findViewById(R.id.txv_Trend_Total_Income);
        TextView txvTotalCost = rootView.findViewById(R.id.txv_Trend_Total_Cost);
        TextView txvTotalSurplus = rootView.findViewById(R.id.txv_Trend_Total_Surplus);

        txvYearTit.setText(destinationYear + "年");
        txvTotalIncome.setText(yearlyData.getIncome() + "元");
        txvTotalCost.setText(yearlyData.getCost() + "元");
        if (yearlyData.getSurplus() > 0) {
            txvTotalSurplus.setTextColor(txvTotalSurplus.getResources().getColor(R.color.possurplus));
        } else {
            txvTotalSurplus.setTextColor(txvTotalSurplus.getResources().getColor(R.color.negsurplus));
        }
        txvTotalSurplus.setText(yearlyData.getSurplus() + "元");

        //生成下方列表
        TrendDataAdapter adapter = new TrendDataAdapter(getContext(), listData);
        listView.setAdapter(adapter);
    }
}