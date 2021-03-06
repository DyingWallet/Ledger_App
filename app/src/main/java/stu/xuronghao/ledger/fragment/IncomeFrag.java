package stu.xuronghao.ledger.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.activity.DetailPage;
import stu.xuronghao.ledger.adapter.BillDataAdapter;
import stu.xuronghao.ledger.entity.Income;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.consts.ConstantVariable;
import stu.xuronghao.ledger.handler.network.DataPuller;

public class IncomeFrag extends Fragment {
    // 参数声明
    private FragmentActivity activity;
    private View rootView;
    private User user;
    private Context context;
    private List<Income> incList;
    private ListView listView;
    private AVLoadingIndicatorView indicatorView;
    private AsyncIncomePuller listPuller;

    public IncomeFrag() {
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
        rootView = inflater.inflate(R.layout.fragment_income, container, false);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //进行组件初始化
        user = (User) getActivity().getIntent().getSerializableExtra(ConstantVariable.USER);
    }

    @Override
    public void onResume() {
        super.onResume();
        activity = this.getActivity();
        listPuller = new AsyncIncomePuller();
        indicatorView = rootView.findViewById(R.id.avi_income);
        listView = rootView.findViewById(R.id.lv_IncomeDataList);

        listPuller.execute();
    }

    class AsyncIncomePuller extends AsyncTask<Void, Void, List<HashMap<String, String>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            indicatorView.show();
        }

        @Override
        protected List<HashMap<String, String>> doInBackground(Void... voids) {
            //从服务器拉取数据
            DataPuller dataPuller = new DataPuller();
            incList = dataPuller.pullIncomeOf(user);
            if (incList == null) {
                return null;
            }
            //获取列表对象
            listView = rootView.findViewById(R.id.lv_IncomeDataList);
            //用HashMap来传递内容
            ArrayList<HashMap<String, String>> mapArrayList = new ArrayList<>();
            for (int i = 0; i < incList.size(); i++) {
                Income temp = incList.get(i);
                String title = temp.getIncEvent() + ": " + temp.getIncType() + " " + temp.getIncAmount() + "元";
                HashMap<String, String> map = new HashMap<>();
                map.put(ConstantVariable.ITEM_TITLE, title);
                map.put(ConstantVariable.ITEM_TYPE, temp.getIncType());
                map.put(ConstantVariable.ITEM_CONTENT, temp.getIncDate());
                mapArrayList.add(map);
            }
            return mapArrayList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> mapArrayList) {
            super.onPostExecute(mapArrayList);
            if (mapArrayList != null) {//用HashMap将数据传入ListView适配器
                BillDataAdapter adapter = new BillDataAdapter(activity, ConstantVariable.INCOME_CODE, mapArrayList);

                //应用适配器并更新ListView
                listView.setAdapter(adapter);

                listView.setOnItemClickListener((parent, view, position, id) -> {
                    Intent intent = new Intent(getActivity(), DetailPage.class);
                    //设置类型
                    intent.putExtra(ConstantVariable.TYPE_CODE, ConstantVariable.INCOME_CODE);
                    //传递数据
                    intent.putExtra(ConstantVariable.INCOME_TYPE, incList.get(position));
                    startActivity(intent);
                });
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
    }

    public AsyncIncomePuller incomePullerFactory() {
        return new AsyncIncomePuller();
    }
}