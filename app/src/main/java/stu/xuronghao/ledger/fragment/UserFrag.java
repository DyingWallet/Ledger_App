package stu.xuronghao.ledger.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.activity.AnnoPage;
import stu.xuronghao.ledger.activity.FeedbackPage;
import stu.xuronghao.ledger.entity.User;

public class UserFrag extends Fragment {


    // 设置需要从bundle中取出的数据的Key值
    private static final String ARG_USER_INFO = "user";

    // 参数设置
    private Bundle args;
    private User user;
    private View rootView;

    public UserFrag() {
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
        rootView = inflater.inflate(R.layout.fragment_user, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取用户信息
        user = (User) getActivity().getIntent().getSerializableExtra(ARG_USER_INFO);
//        Log.w("user frag user test", user.toString());
        TextView userName = rootView.findViewById(R.id.txv_UserFrag_UserName);
        userName.setText(user.getUserName());

        ImageView anno = rootView.findViewById(R.id.img_UserFrag_Anno_BG);
        ImageView feedback = rootView.findViewById(R.id.img_UserFrag_Feedback_BG);
        ImageView shop = rootView.findViewById(R.id.img_UserFrag_Shop_BG);
        ImageView storage = rootView.findViewById(R.id.img_UserFrag_Storage_BG);


        storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getContext(), "功能还在开发中哦......", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getContext(), "功能还在开发中哦......", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedbackPage.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        anno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AnnoPage.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }
}