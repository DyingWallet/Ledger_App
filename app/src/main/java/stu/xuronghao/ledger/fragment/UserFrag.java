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
import stu.xuronghao.ledger.handler.ConstantVariable;

public class UserFrag extends Fragment {

    // 参数设置
    private User user;
    private View rootView;

    public UserFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        TextView userName = rootView.findViewById(R.id.txv_UserFrag_UserName);
        userName.setText(user.getUserName());

        ImageView anno = rootView.findViewById(R.id.img_UserFrag_Anno_BG);
        ImageView feedback = rootView.findViewById(R.id.img_UserFrag_Feedback_BG);
        ImageView shop = rootView.findViewById(R.id.img_UserFrag_Shop_BG);
        ImageView storage = rootView.findViewById(R.id.img_UserFrag_Storage_BG);


        storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getContext(), ConstantVariable.HINT_IN_DEVELOPING, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getContext(), ConstantVariable.HINT_IN_DEVELOPING, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedbackPage.class);
                intent.putExtra(ConstantVariable.USER, user);
                startActivity(intent);
            }
        });

        anno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AnnoPage.class);
                intent.putExtra(ConstantVariable.USER, user);
                startActivity(intent);
            }
        });
    }
}