package stu.xuronghao.ledger.fragment;

import android.os.AsyncTask;
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

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import stu.xuronghao.ledger.handler.DateHandler;

public class TrendFrag extends Fragment {
    private User user;
    private View rootView;
    private int currentYear;
    private int destinationYear;
    private String startDate;
    private String endDate;
    private TrendData yearlyData;
    Double[] monthlyCost = new Double[12];
    Double[] monthlyIncome = new Double[12];
    Double[] monthlySurplus = new Double[12];
    ImageView arrowLeft;
    ImageView arrowRight;
    AAChartView trendView;
    TextView txvYearTit;
    TextView txvTotalIncome;
    TextView txvTotalCost;
    TextView txvTotalSurplus;
    ListView listView;
    AsyncTrendPuller asyncTrendPuller;

    private AVLoadingIndicatorView indicatorView;

    public TrendFrag() {
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
        rootView = inflater.inflate(R.layout.fragment_trend, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user = (User) getActivity().getIntent().getSerializableExtra(ConstantVariable.USER);

        txvYearTit = rootView.findViewById(R.id.txv_Trend_Year);
        txvTotalIncome = rootView.findViewById(R.id.txv_Trend_Total_Income);
        txvTotalCost = rootView.findViewById(R.id.txv_Trend_Total_Cost);
        txvTotalSurplus = rootView.findViewById(R.id.txv_Trend_Total_Surplus);
        //获取可视化控件
        trendView = rootView.findViewById(R.id.trend_chart);
        listView = rootView.findViewById(R.id.lv_Trend_DivideByTypes);
        indicatorView = rootView.findViewById(R.id.avi_trend);

        currentYear = DateHandler.getCurrentYear();
        destinationYear = currentYear;
        setDateRange();
        setDateChanger();
    }

    @Override
    public void onStart() {
        super.onStart();
        asyncTrendPuller = new AsyncTrendPuller();
        asyncTrendPuller.execute();
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
        arrowLeft = rootView.findViewById(R.id.arrow_Trend_LastMonth);
        arrowRight = rootView.findViewById(R.id.arrow_Trend_NextMonth);

        arrowLeft.setOnClickListener(v -> {
            destinationYear--;
            setDateRange();
            asyncTrendPuller = new AsyncTrendPuller();
            asyncTrendPuller.execute();
        });

        arrowRight.setOnClickListener(v -> {
            if (destinationYear < currentYear) {
                destinationYear++;
                setDateRange();
                asyncTrendPuller = new AsyncTrendPuller();
                asyncTrendPuller.execute();
            } else {
                Toast toast = Toast.makeText(getContext(),
                        ConstantVariable.HINT_DATE_TO_FUTURE, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    private class AsyncTrendPuller extends AsyncTask<Void,Void, Map<String,List>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            indicatorView.show();
        }

        @Override
        protected Map<String,List> doInBackground(Void... voids) {
            DataPuller dataPuller = new DataPuller();
            //拉取数据
            List<Cost> tempCost = dataPuller.pullCostOfBetween(user, startDate, endDate);
            List<Income> tempIncome = dataPuller.pullIncomeOfBetween(user, startDate, endDate);
            //分别存储信息
            List<TrendData> listData = new ArrayList<>();
            List<String> months = new ArrayList<>();
            Map<String,List> result = new HashMap<>();

            TrendData[] monthlyData = new TrendData[12];

            if (tempCost != null && tempIncome != null) {
                double amount;
                int size = Math.max(tempCost.size(), tempIncome.size());
                int month;
                String date;
                //进行初始化
                for (int i = 0; i < 12; i++) {
                    monthlyData[i] = new TrendData(destinationYear, i + 1);
                    yearlyData = new TrendData(destinationYear, 0);
                    monthlyCost[i] = 0.0;
                    monthlyIncome[i] = 0.0;
                    monthlySurplus[i] = 0.0;
                    if ((i + 1) < 10)
                        months.add("0" + (i + 1) + "月");
                    else
                        months.add((i + 1) + "月");
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
            }else {
                return null;
            }
            result.put(ConstantVariable.DATA_LIST,listData);
            result.put(ConstantVariable.MONTH_DATA_LIST,months);
            return result;
        }

        @Override
        protected void onPostExecute(Map<String,List> result) {
            super.onPostExecute(result);
            if (result != null) {//UI绘制
                List<TrendData> listData = result.get(ConstantVariable.DATA_LIST);
                List<String> monthDataList = result.get(ConstantVariable.MONTH_DATA_LIST);
                String[] months = monthDataList.toArray(new String[0]);
                AAChartModel trendModel = new AAChartModel()
                        .chartType(AAChartType.Line)
                        .title("年度走势")
                        .backgroundColor("#FFFFFF")
                        .dataLabelsEnabled(false)
                        .yAxisGridLineWidth(0f)
                        .markerRadius(2f)
                        .animationDuration(1000);
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
                if (yearlyData.getSurplus() > 0) {
                    txvTotalSurplus.setTextColor(txvTotalSurplus.getResources().getColor(R.color.possurplus));
                } else {
                    txvTotalSurplus.setTextColor(txvTotalSurplus.getResources().getColor(R.color.negsurplus));
                }
                String yearTitle = destinationYear + "年";
                String totalIncome = yearlyData.getIncome() + "元";
                String totalCost = yearlyData.getCost() + "元";
                String surplus = yearlyData.getSurplus() + "元";
                txvYearTit.setText(yearTitle);
                txvTotalIncome.setText(totalIncome);
                txvTotalCost.setText(totalCost);
                txvTotalSurplus.setText(surplus);

                //生成下方列表
                TrendDataAdapter adapter = new TrendDataAdapter(getContext(), listData);
                listView.setAdapter(adapter);
            } else {
                Toast toast = Toast.makeText(getContext(),
                        ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_LONG);
                toast.show();

            }
            indicatorView.hide();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            indicatorView.hide();
        }
    }
}