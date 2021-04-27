package stu.xuronghao.ledger.fragment;

import android.content.Intent;
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

import com.getbase.floatingactionbutton.FloatingActionButton;

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
    private View rootView;
    private User user;
    List<Cost> costList;
    ListView listView;

    public CostFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        //buildListView();
        setFloatBtn();
        user = (User) getActivity().getIntent().getSerializableExtra(ConstantVariable.USER);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.w("resume: ", "called!");
        buildListView();
    }

    private void buildListView() {
        //从服务器拉取数据
        DataPuller dataPuller = new DataPuller();
        costList = dataPuller.pullCostOf(user);
        if (costList == null) {
            Toast toast = Toast.makeText(getContext(),
                    ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        //获取列表对象
        listView = rootView.findViewById(R.id.lv_CostDataList);
        //用HashMap来传递内容
        ArrayList<HashMap<String, String>> mapArrayList = new ArrayList<>();
        for (int i = 0; i < costList.size(); i++) {
            Cost temp = costList.get(i);
            String title = temp.getCostEvent() + ": " + temp.getCostType() + " " + temp.getCostAmount() + "元";
            HashMap<String, String> map = new HashMap<>();
            map.put(ConstantVariable.ITEM_TITLE, title);
            map.put(ConstantVariable.ITEM_TYPE,temp.getCostType());
            map.put(ConstantVariable.ITEM_CONTENT, temp.getCostDate());
            mapArrayList.add(map);
        }
        //用HashMap将数据传入ListView适配器
        BillDataAdapter adapter = new BillDataAdapter(this.getActivity(),ConstantVariable.COST_CODE , mapArrayList);
        //应用适配器并更新ListView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailPage.class);
                intent.putExtra(ConstantVariable.TYPE_CODE, ConstantVariable.COST_CODE);
                intent.putExtra("cost", costList.get(position));
                startActivity(intent);
            }
        });
    }

    private void setFloatBtn() {

        final FloatingActionButton chatBtn = rootView.findViewById(R.id.to_chat);
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("chatBtn", "Ready to chat!");
                Intent intent = new Intent(getActivity(), ChatToRecordPage.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        final FloatingActionButton addBtn = rootView.findViewById(R.id.add_cost);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //新增支出
                Log.w("addBtn: ", "Ready to add cost!");
                Intent intent = new Intent(getActivity(), PushDataPage.class);
                intent.putExtra(ConstantVariable.TYPE_CODE, ConstantVariable.COST_CODE);
                intent.putExtra(ConstantVariable.USER, user);
                startActivity(intent);
            }
        });

        final FloatingActionButton refreshBtn = rootView.findViewById(R.id.refresh_cost);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新事件
                buildListView();
                Toast toast = Toast.makeText(getContext(), "收到服务器君发送的消费数据！", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}