package stu.xuronghao.ledger.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.activity.AnnoPage;
import stu.xuronghao.ledger.activity.ChangePasswdPage;
import stu.xuronghao.ledger.activity.ChangeUsernamePage;
import stu.xuronghao.ledger.activity.FeedbackPage;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.ConstantVariable;
import stu.xuronghao.ledger.handler.DataPuller;

public class UserFrag extends Fragment {

    // 参数设置
    private User user;
    private View rootView;
    private Context context;
    private TextView txvUserName;

    public UserFrag() {
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
        rootView = inflater.inflate(R.layout.fragment_user, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取用户信息
        user = (User) getActivity().getIntent().getSerializableExtra(ConstantVariable.USER);

        ImageView anno = rootView.findViewById(R.id.img_UserFrag_Anno_BG);
        ImageView feedback = rootView.findViewById(R.id.img_UserFrag_Feedback_BG);
        //ImageView shop = rootView.findViewById(R.id.img_UserFrag_Shop_BG);
        //ImageView storage = rootView.findViewById(R.id.img_UserFrag_Storage_BG);
        ImageView setting = rootView.findViewById(R.id.img_UserFrag_Setting_BG);
        txvUserName = rootView.findViewById(R.id.txv_UserFrag_UserName);


        //storage.setOnClickListener(v -> {
        //    Toast toast = Toast.makeText(context, ConstantVariable.HINT_IN_DEVELOPING, Toast.LENGTH_SHORT);
        //    toast.show();
        //});
        //
        //shop.setOnClickListener(v -> {
        //    Toast toast = Toast.makeText(context, ConstantVariable.HINT_IN_DEVELOPING, Toast.LENGTH_SHORT);
        //    toast.show();
        //});

        feedback.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FeedbackPage.class);
            intent.putExtra(ConstantVariable.USER, user);
            startActivity(intent);
        });

        anno.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AnnoPage.class);
            intent.putExtra(ConstantVariable.USER, user);
            startActivity(intent);
        });

        setting.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), ChangePasswdPage.class);
            intent.putExtra(ConstantVariable.USER,user);
            startActivity(intent);
        });

        txvUserName.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), ChangeUsernamePage.class);
            intent.putExtra(ConstantVariable.USER,user);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        DataPuller dataPuller = new DataPuller();
        user = dataPuller.getUserInfo(user);
        getActivity().getIntent().putExtra(ConstantVariable.USER,user);
        txvUserName.setText(user.getUserName());
    }
}