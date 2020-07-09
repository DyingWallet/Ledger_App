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
import stu.xuronghao.ledger.entity.Income;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.DataPuller;

public class IncomeFrag extends Fragment {

    // 设置需要从传入的bundle中获取的数据的Key值
    private static final String ARG_USER_INFO    = "user";
    private static final int    To_Income_Pusher = 1;

    //进行收入图表类型匹配
    private static final HashMap<String, Integer> incomeTypeMap =
            new HashMap<String, Integer>() {
                {
                    put("工资", R.drawable.icon_salary);
                    put("奖金", R.drawable.icon_bonus);
                    put("借款", R.drawable.icon_loan);
                    put("红包", R.drawable.icon_redpkt);
                    put("其他", R.drawable.icon_other_income);
                }
            };

    // 参数声明
    //    private Bundle          args;
    private View rootView;
    private User user;
    List<Income> incList;
    ListView     listView;

    public IncomeFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
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

        //进行组建初始化
        //buildListView();
        setFloatBtn();

        user = (User) getActivity().getIntent().getSerializableExtra(ARG_USER_INFO);
        Log.w("income frag user test", user.toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        buildListView();
    }

    private void buildListView() {
        //从服务器拉取数据
        DataPuller dataPuller = new DataPuller();
        incList = dataPuller.PullIncomeOf(user);
        if (incList == null) {
            Toast toast = Toast.makeText(getContext(),
                    "似乎和服务器君失去了联系...请检查网络连接哦~~~", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        //获取列表对象
        listView = rootView.findViewById(R.id.lv_IncomeDataList);
        //用HashMap来传递内容
        ArrayList<HashMap<String, Object>> mapArrayList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < incList.size(); i++) {
            Income temp = incList.get(i);
            String Tit = temp.getIncEvent() + ": " + temp.getIncType() + " " + temp.getIncAmount() + "元";
            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("ItemImage", R.drawable.icon_income);
            map.put("ItemImage",incomeTypeMap.get(temp.getIncType()));
            map.put("ItemTitle", Tit);
            map.put("ItemContent", temp.getIncDate());
            mapArrayList.add(map);
        }
        //用HashMap将数据传入ListView适配器
        BillDataAdapter adapter = new BillDataAdapter(this.getActivity(), mapArrayList);
        //应用适配器并更新ListView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailPage.class);
                intent.putExtra("index", 1);
                intent.putExtra("income", incList.get(position));
                startActivity(intent);
            }
        });
    }

    private void setFloatBtn() {

        final FloatingActionButton chatBtn = rootView.findViewById(R.id.to_chat);
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("chatBtn","Ready to chat!");
                Intent intent = new Intent(getActivity(), ChatToRecordPage.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

        final FloatingActionButton addBtn = rootView.findViewById(R.id.add_income);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //新增支出
                Log.w("addBtn: ", "Ready to add income!");
                Intent intent = new Intent(getActivity(), PushDataPage.class);
                intent.putExtra("index", To_Income_Pusher);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        final FloatingActionButton refreshBtn = rootView.findViewById(R.id.refresh_income);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新事件
                buildListView();
                Toast toast = Toast.makeText(getContext(), "收到服务器君发送的收入数据！", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

}