package stu.xuronghao.ledger.fragment;

import android.content.Context;
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

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import stu.xuronghao.ledger.AAChartCoreLib.AAChartCreator.AAChartModel;
import stu.xuronghao.ledger.AAChartCoreLib.AAChartCreator.AAChartView;
import stu.xuronghao.ledger.AAChartCoreLib.AAChartCreator.AASeriesElement;
import stu.xuronghao.ledger.AAChartCoreLib.AAChartEnum.AAChartType;
import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.adapter.PieDataAdapter;
import stu.xuronghao.ledger.entity.Cost;
import stu.xuronghao.ledger.entity.Income;
import stu.xuronghao.ledger.entity.TotalFee;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.consts.ColorsHandler;
import stu.xuronghao.ledger.handler.consts.ConstantVariable;
import stu.xuronghao.ledger.handler.network.DataPuller;
import stu.xuronghao.ledger.handler.DateHandler;
import stu.xuronghao.ledger.handler.ListSortUtil;
import stu.xuronghao.ledger.handler.consts.IconHandler;

public class PieFrag extends Fragment {
    //模式标记
    private static int typeCode = ConstantVariable.COST_CODE;
    private User user;
    private View rootView;
    private Context context;
    private int currentMonth;
    private int destinationMonth;
    private String startDate;
    private String endDate;
    private String total;
    //文本控件
    private TextView txvStartDate;
    private TextView txvEndDate;
    private TextView txvNoRecord;
    //可视化控件
    private AAChartView pieView;
    //列表控件
    private ListView listView;
    private AVLoadingIndicatorView indicatorView;
    private AsyncPiePuller asyncPiePuller;

    public PieFrag() {
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
        rootView = inflater.inflate(R.layout.fragment_pie, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user = (User) getActivity().getIntent().getSerializableExtra(ConstantVariable.USER);
        //图标控件
        indicatorView = rootView.findViewById(R.id.avi_pie);
        txvStartDate = rootView.findViewById(R.id.txv_Pie_StartDate);
        txvEndDate = rootView.findViewById(R.id.txv_Pie_EndDate);
        txvNoRecord = rootView.findViewById(R.id.pie_no_record_notice);
        pieView = rootView.findViewById(R.id.pie_chart);
        listView = rootView.findViewById(R.id.lv_Pie_DivideByTypes);
        //获取当前月份
        currentMonth = DateHandler.getCurrentMonth();
        destinationMonth = currentMonth;
        //设置模式转换监听器
        ImageView imgSwitch = rootView.findViewById(R.id.pie_click_switch);
        imgSwitch.setOnClickListener(v -> {
            if (ConstantVariable.COST_CODE != typeCode) {
                typeCode = ConstantVariable.COST_CODE;
            } else {
                typeCode = ConstantVariable.INCOME_CODE;
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

    private class AsyncPiePuller extends AsyncTask<Void, Void, Map<String, List>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txvNoRecord.setVisibility(View.INVISIBLE);
            indicatorView.show();
            //设置日期范围
            setDateRange();
            //设置日期转换器
            setDateChanger();
        }

        @Override
        protected Map<String, List> doInBackground(Void... voids) {
            DataPuller dataPuller = new DataPuller();
            List<TotalFee> feeList = new ArrayList<>();
            List<Object[]> data = new ArrayList<>();
            Map<String, List> result = new HashMap<>();
            double tmp = 0;
            double[] arr = new double[ConstantVariable.getTypeArray(ConstantVariable.COST_TYPE).length];
            String modeType = ConstantVariable.COST_CODE == typeCode ? ConstantVariable.COST_TYPE : ConstantVariable.INCOME_TYPE;

            if (ConstantVariable.COST_CODE == typeCode) {
                //消费报表
                List<Cost> costList = dataPuller.pullCostOfBetween(user, startDate, endDate);
                if (costList != null) {
                    for (Cost cost : costList) {
                        String type = cost.getCostType();
                        int index = ConstantVariable.getTypeIndex(type, ConstantVariable.COST_CODE);
                        arr[index] += cost.getCostAmount();
                    }
                } else {
                    return null;
                }
            } else if (ConstantVariable.INCOME_CODE == typeCode) {
                //收入报表
                List<Income> incomeList = dataPuller.pullIncomeOfBetween(user, startDate, endDate);
                if (incomeList != null) {
                    for (Income income : incomeList) {
                        String type = income.getIncType();
                        int index = ConstantVariable.getTypeIndex(type, ConstantVariable.INCOME_CODE);
                        arr[index] += income.getIncAmount();
                    }
                } else {
                    return null;
                }
            }

            for (int i = 0; i < arr.length; i++) {
                if (arr[i] != 0) {
                    data.add(new Object[]{
                            ConstantVariable.getTypeByTypeStr(i, modeType), arr[i]
                    });
                    feeList.add(new TotalFee(IconHandler.getIconByArray(typeCode, i),
                            arr[i], ConstantVariable.getTypeByTypeStr(i, modeType)));
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
            if (result != null) {
                List<TotalFee> feeList = result.get(ConstantVariable.FEE_LIST);
                List<Object[]> dataList = result.get(ConstantVariable.DATA_LIST);
                if (feeList.isEmpty()) {
                    txvNoRecord.setVisibility(View.VISIBLE);
                }
                String totalStr = ConstantVariable.COST_CODE == typeCode ? "总消费:" + total : "总收入:" + total;

                Object[] data = dataList.stream().filter(Objects::nonNull).toArray();

                new ListSortUtil().sortByAmount(feeList);

                AAChartModel pieModel = new AAChartModel()
                        .chartType(AAChartType.Pie)
                        .title(totalStr)
                        .backgroundColor("#FFFFFF")
                        .dataLabelsEnabled(true)
                        .yAxisTitle("111")
                        .colorsTheme(ColorsHandler.getColorStr(data.length))
                        .animationDuration(1000)
                        .series(new AASeriesElement[]{
                                new AASeriesElement()
                                        .size("100%")
                                        .innerSize("50%")
                                        .borderWidth(3f)
                                        .allowPointSelect(false)
                                        .data(data)
                        });

                pieView.aa_drawChartWithChartModel(pieModel);
                //设置日期
                txvStartDate.setText(startDate.split(ConstantVariable.TIME_REGEX)[0]);
                txvEndDate.setText(endDate.split(ConstantVariable.TIME_REGEX)[0]);
                //适配器
                PieDataAdapter adapter = new PieDataAdapter(context, feeList);
                listView.setAdapter(adapter);
            } else {
                Toast toast = Toast.makeText(context,
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

        //设置日期选择器
        private void setDateChanger() {
            //图像控件
            ImageView arrowLeft = rootView.findViewById(R.id.arrow_Pie_LastMonth);
            ImageView arrowRight = rootView.findViewById(R.id.arrow_Pie_NextMonth);
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
                    Toast toast = Toast.makeText(context, ConstantVariable.HINT_DATE_TO_FUTURE, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }

        //设置日期范围
        private void setDateRange() {
            startDate = DateHandler.setDateRange(ConstantVariable.START_CODE, destinationMonth);
            endDate = DateHandler.setDateRange(ConstantVariable.END_CODE, destinationMonth);
        }

    }
}