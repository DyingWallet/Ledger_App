package stu.xuronghao.ledger.fragment;

import android.os.AsyncTask;
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
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.Collectors;

import stu.xuronghao.ledger.AAChartCoreLib.AAChartCreator.AAChartModel;
import stu.xuronghao.ledger.AAChartCoreLib.AAChartCreator.AAChartView;
import stu.xuronghao.ledger.AAChartCoreLib.AAChartCreator.AASeriesElement;
import stu.xuronghao.ledger.AAChartCoreLib.AAChartEnum.AAChartType;
import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.activity.DetailPage;
import stu.xuronghao.ledger.adapter.BillDataAdapter;
import stu.xuronghao.ledger.adapter.PieDataAdapter;
import stu.xuronghao.ledger.entity.Cost;
import stu.xuronghao.ledger.entity.Income;
import stu.xuronghao.ledger.entity.TotalFee;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.ColorsHandler;
import stu.xuronghao.ledger.handler.ConstantVariable;
import stu.xuronghao.ledger.handler.DataPuller;
import stu.xuronghao.ledger.handler.DateHandler;
import stu.xuronghao.ledger.handler.FeeSortUtil;
import stu.xuronghao.ledger.handler.IconHandler;

public class PieFrag extends Fragment {
    //模式标记
    private static int mode = ConstantVariable.COST_CODE;

    private User user;
    private View rootView;
    private int currentMonth;
    private int destinationMonth;
    private String startDate;
    private String endDate;
    private String total;
    //图标控件
    private ImageView imgSwitch;
    private ImageView arrowLeft;
    private ImageView arrowRight;
    //文本控件
    TextView txvStartDate;
    TextView txvEndDate;
    //可视化控件
    AAChartView pieView;
    //列表控件
    ListView listView;
    //适配器
    PieDataAdapter adapter;
    private AVLoadingIndicatorView indicatorView;
    private AsyncPiePuller asyncPiePuller;

    public PieFrag() {
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
        rootView = inflater.inflate(R.layout.fragment_pie, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user = (User) getActivity().getIntent().getSerializableExtra(ConstantVariable.USER);
        imgSwitch = rootView.findViewById(R.id.pie_click_switch);
        arrowLeft = rootView.findViewById(R.id.arrow_Pie_LastMonth);
        arrowRight = rootView.findViewById(R.id.arrow_Pie_NextMonth);
        indicatorView = rootView.findViewById(R.id.avi_pie);
        txvStartDate = rootView.findViewById(R.id.txv_Pie_StartDate);
        txvEndDate = rootView.findViewById(R.id.txv_Pie_EndDate);
        pieView = rootView.findViewById(R.id.pie_chart);
        listView = rootView.findViewById(R.id.lv_Pie_DivideByTypes);
        //获取当前月份
        currentMonth = DateHandler.getCurrentMonth();
        destinationMonth = currentMonth;
        //设置日期范围
        setDateRange();
        //设置日期转换器
        setDateChanger();
        //设置模式转换监听器
        imgSwitch.setOnClickListener(v -> {
            if (ConstantVariable.COST_CODE != mode) {
                mode = ConstantVariable.COST_CODE;
            } else {
                mode = ConstantVariable.INCOME_CODE;
            }
            asyncPiePuller = new AsyncPiePuller();
            asyncPiePuller.execute();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //生成图形化报表
        asyncPiePuller = new AsyncPiePuller();
        asyncPiePuller.execute();
    }

    //设置日期范围
    private void setDateRange() {
        startDate = DateHandler.setDateRange(ConstantVariable.START_CODE, destinationMonth);
        endDate = DateHandler.setDateRange(ConstantVariable.END_CODE, destinationMonth);
    }

    //设置日期选择器
    private void setDateChanger() {
        //图像控件
        arrowLeft.setOnClickListener(v -> {
            destinationMonth--;
            setDateRange();
            asyncPiePuller = new AsyncPiePuller();
            asyncPiePuller.execute();
        });
        arrowRight.setOnClickListener(v -> {
            if (destinationMonth < currentMonth) {
                destinationMonth++;
                setDateRange();
                asyncPiePuller = new AsyncPiePuller();
                asyncPiePuller.execute();
            } else {
                Toast toast = Toast.makeText(getContext(), "果然不能预知未来呢~~~", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    private class AsyncPiePuller extends AsyncTask<Void, Void, Map<String, List>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            indicatorView.show();
        }

        @Override
        protected Map<String, List> doInBackground(Void... voids) {
            DataPuller dataPuller = new DataPuller();
            List<TotalFee> feeList = new ArrayList<>();
            List<Object[]> data = new ArrayList<>();
            Map<String, List> result = new HashMap<>();
            double tmp = 0;
            double[] arr = {0, 0, 0, 0, 0};
            String modeType = ConstantVariable.COST_CODE == mode? ConstantVariable.COST_TYPE : ConstantVariable.INCOME_TYPE;

            if (ConstantVariable.COST_CODE == mode) {
                //消费报表
                List<Cost> costList = dataPuller.pullCostOfBetween(user, startDate, endDate);
                if (costList != null) {
                    for (Cost cost : costList) {
                        String type = cost.getCostType();
                        int index = ConstantVariable.getTypeIndex(ConstantVariable.COST_CODE, type);
                        arr[index] += cost.getCostAmount();
                    }
                } else {
                    Toast toast = Toast.makeText(getContext(),
                            ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_LONG);
                    toast.show();
                }
            } else if (ConstantVariable.INCOME_CODE == mode) {
                //收入报表
                List<Income> incomeList = dataPuller.pullIncomeOfBetween(user, startDate, endDate);
                if (incomeList != null) {
                    for (Income income : incomeList) {
                        String type = income.getIncType();
                        int index = ConstantVariable.getTypeIndex(ConstantVariable.INCOME_CODE, type);
                        arr[index] += income.getIncAmount();
                    }
                } else {
                    Toast toast = Toast.makeText(getContext(),
                            ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            for (int i = 0; i < arr.length; i++) {
                if (arr[i] != 0) {
                    data.add(new Object[]{ConstantVariable.getType(modeType, i), arr[i]});
                    feeList.add(new TotalFee(IconHandler.getIconByArray(mode, i),
                            arr[i], ConstantVariable.getType(modeType, i)));
                    tmp += arr[i];
                }
            }
            total = tmp + "元";

            result.put(ConstantVariable.FEE_LIST, feeList);
            result.put(ConstantVariable.DATA_LIST, data);
            return result;
        }

        @Override
        protected void onPostExecute(Map<String, List> result) {
            super.onPostExecute(result);
            List<TotalFee> feeList = result.get(ConstantVariable.FEE_LIST);
            List<Object[]> dataList = result.get(ConstantVariable.DATA_LIST);
            String totalStr = ConstantVariable.COST_CODE == mode ? "总消费:" + total : "总收入:" + total;

            Object[] data = dataList.stream().filter(Objects::nonNull).toArray();

            new FeeSortUtil().sortByAmount(feeList);

            AAChartModel pieModel = new AAChartModel()
                    .chartType(AAChartType.Pie)
                    .title(totalStr)
                    .backgroundColor("#FFFFFF")
                    .dataLabelsEnabled(true)
                    .series(new AASeriesElement[]{
                            new AASeriesElement()
                            .size("100%")
                            .innerSize("50%")
                            .borderWidth(3f)
                            .allowPointSelect(false)
                            .data(data)
                    })
                    .yAxisTitle("111")
                    .colorsTheme(ColorsHandler.getColorStr(data.length))
                    .animationDuration(1000);

            pieView.aa_drawChartWithChartModel(pieModel);
            //设置日期
            txvStartDate.setText(startDate.split(ConstantVariable.TIME_REGEX)[0]);
            txvEndDate.setText(endDate.split(ConstantVariable.TIME_REGEX)[0]);
            adapter = new PieDataAdapter(getContext(), feeList);
            listView.setAdapter(adapter);
            indicatorView.hide();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            indicatorView.hide();
        }
    }

}