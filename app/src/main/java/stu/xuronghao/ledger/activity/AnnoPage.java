package stu.xuronghao.ledger.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.adapter.AnnoListAdapter;
import stu.xuronghao.ledger.entity.Anno;
import stu.xuronghao.ledger.entity.AnnoInfo;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.DataPuller;

public class AnnoPage extends AppCompatActivity {

    private User user;
    private ListView listView;
    private List<Anno> annoList;
    private AnnoListAdapter adapter;
    private DataPuller dataPuller = new DataPuller();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anno_page);
        context = this;
        listView = findViewById(R.id.lv_anno_list);
        user = (User) getIntent().getSerializableExtra("user");

        //返回
        ImageView img_back = findViewById(R.id.img_Anno_Back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //拉取数据并建立公告列表
        buildListView();
    }

    private void buildListView() {
        annoList = dataPuller.pullAnnos();
        if (annoList == null) {
            Toast toast = Toast.makeText(context,
                    "似乎和服务器君失去了联系...请检查网络连接哦~~~", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        ArrayList<AnnoInfo> infoList = new ArrayList<>();
        for (Anno anno : annoList)
            infoList.add(new AnnoInfo(anno.getAnnoTitle(), anno.getAnnoDate()));
        adapter = new AnnoListAdapter(context, infoList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog(annoList.get(position));
            }
        });

        listView.setAdapter(adapter);
    }

    private void showDialog(Anno anno) {
        View view = LayoutInflater.from(this).inflate(R.layout.anno_detail, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        Button confirm = view.findViewById(R.id.btn_Anno_Detail_Dismiss);
        TextView title = view.findViewById(R.id.txv_Anno_Detail_Title);
        TextView admin = view.findViewById(R.id.txv_Anno_Detail_Admin);
        TextView datetime = view.findViewById(R.id.txv_Anno_Detail_Datetime);
        TextView content = view.findViewById(R.id.txv_Anno_Detail_Content);

        content.setSingleLine(false);
        content.setMovementMethod(ScrollingMovementMethod.getInstance());
        //获取内容
        title.setText(anno.getAnnoTitle());
        admin.setText(anno.getAdminNo());
        datetime.setText(anno.getAnnoDate());
        content.setText(anno.getAnnoContent());


        //设置监听器
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}