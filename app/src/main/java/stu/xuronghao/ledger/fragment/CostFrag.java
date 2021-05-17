package stu.xuronghao.ledger.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.activity.ChatToRecordPage;
import stu.xuronghao.ledger.activity.DetailPage;
import stu.xuronghao.ledger.activity.PushDataPage;
import stu.xuronghao.ledger.adapter.BillDataAdapter;
import stu.xuronghao.ledger.entity.Cost;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.ConstantVariable;
import stu.xuronghao.ledger.handler.DataPuller;

public class CostFrag extends Fragment {
    private FragmentActivity activity;
    private User user;
    private View rootView;
    private Context context;
    private List<Cost> costList;
    private ListView listView;
    private AVLoadingIndicatorView indicatorView;
    private AsyncCostPuller listPuller;

    public CostFrag() {
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
        rootView = inflater.inflate(R.layout.fragment_cost, container, false);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //进行组建初始化
        setFloatBtn();
        if(user == null)
            user = (User) getActivity().getIntent().getSerializableExtra(ConstantVariable.USER);
    }

    @Override
    public void onResume() {
        super.onResume();
        activity = this.getActivity();
        listPuller = new AsyncCostPuller();
        indicatorView = rootView.findViewById(R.id.avi_cost);
        //获取列表对象
        listView = rootView.findViewById(R.id.lv_CostDataList);

        listPuller.execute();
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
            Log.w("addBtn: ", "Ready to add cost!");
            Intent intent = new Intent(getActivity(), PushDataPage.class);
            intent.putExtra(ConstantVariable.TYPE_CODE, ConstantVariable.COST_CODE);
            intent.putExtra(ConstantVariable.USER, user);
            startActivity(intent);
        });

        final FloatingActionButton refreshBtn = rootView.findViewById(R.id.refresh_cost);
        refreshBtn.setOnClickListener(v -> {
            //更新事件
            listPuller = new AsyncCostPuller();
            listPuller.execute();
            Toast toast = Toast.makeText(context, "收到服务器君发送的消费数据！", Toast.LENGTH_SHORT);
            toast.show();
        });
    }

    private class AsyncCostPuller extends AsyncTask<Void,Void,List<HashMap<String, String>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            indicatorView.show();
        }

        @Override
        protected List<HashMap<String, String>> doInBackground(Void... voids) {
            DataPuller dataPuller = new DataPuller();
            costList = dataPuller.pullCostOf(user);
            if (costList == null) {
                return null;
            }
            //用HashMap来传递内容
            List<HashMap<String, String>> mapArrayList = new ArrayList<>();
            for (int i = 0; i < costList.size(); i++) {
                Cost temp = costList.get(i);
                String title = temp.getCostEvent() + ": " + temp.getCostType() + " " + temp.getCostAmount() + "元";
                HashMap<String, String> map = new HashMap<>();
                map.put(ConstantVariable.ITEM_TITLE, title);
                map.put(ConstantVariable.ITEM_TYPE,temp.getCostType());
                map.put(ConstantVariable.ITEM_CONTENT, temp.getCostDate());
                mapArrayList.add(map);
            }
            return mapArrayList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> mapArrayList) {
            super.onPostExecute(mapArrayList);

            if(mapArrayList != null){
                //用HashMap将数据传入ListView适配器
                BillDataAdapter adapter = new BillDataAdapter(activity, ConstantVariable.COST_CODE, mapArrayList);

                //应用适配器并更新ListView
                listView.setAdapter(adapter);

                listView.setOnItemClickListener((parent, view, position, id) -> {
                    Intent intent = new Intent(getActivity(), DetailPage.class);
                    intent.putExtra(ConstantVariable.TYPE_CODE, ConstantVariable.COST_CODE);
                    intent.putExtra("cost", costList.get(position));
                    startActivity(intent);
                });

            }else {
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

}