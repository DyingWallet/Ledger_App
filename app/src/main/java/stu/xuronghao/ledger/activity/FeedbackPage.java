package stu.xuronghao.ledger.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.entity.Feedback;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.DataPuller;
import stu.xuronghao.ledger.handler.DateHandler;

public class FeedbackPage extends AppCompatActivity {
    private User user;
    private Feedback feedback;
    private RadioGroup group;
    private Context context;
    private DataPuller dataPuller = new DataPuller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_page);
        context = this;
        user = (User) getIntent().getSerializableExtra("user");
        //与默认选中项对齐
        feedback = new Feedback();
        feedback.setFbType("建议");
        EditText Content = findViewById(R.id.etx_fb_Content);
        //获取按钮控件
        Button push = findViewById(R.id.btn_fb_push);
        Button cancel = findViewById(R.id.btn_fb_Cancel);

        //设置为多行输入
        Content.setGravity(Gravity.TOP);
        Content.setSingleLine(false);
        Content.setHorizontallyScrolling(false);

        //获取选项组
        group = findViewById(R.id.fb_type_group);

        //RadioGroup监听器
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_advise:
                        feedback.setFbType("建议");
                        break;
                    case R.id.rbtn_bug:
                        feedback.setFbType("漏洞");
                        break;
                    case R.id.rbtn_froze:
                        feedback.setFbType("冻结");
                        break;
                }
            }
        });

        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getInputInfo()) {
                    if (dataPuller.HandOverFb(feedback)) {
                        Toast toast = Toast.makeText(context, "好的，治账酱收到了！", Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                    } else {
                        Toast toast = Toast.makeText(context,
                                "似乎和服务器君失去了联系...请检查网络连接哦~~~", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean getInputInfo() {
        //获取输入框组件
        EditText fbTitle = findViewById(R.id.etx_fb_Title);
        EditText fbContent = findViewById(R.id.etx_fb_Content);

        String title = fbTitle.getText().toString();
        String content = fbContent.getText().toString();

        if (checkInput(title, content)) {
            feedback.setFbTitle(title);
            feedback.setFbContent(content);
            feedback.setFbDate(DateHandler.getCurrentDatetime());
            feedback.setUserNo(user.getUserNo());
            feedback.setFbRead(0);
            return true;
        }
        return false;
    }

    private boolean checkInput(String title, String content) {
        if (!title.equals("")) {
            if (!content.equals("")) {
                return true;
            } else {
                Toast toast = Toast.makeText(context, "说点什么吧...", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(context, "想个标题吧...", Toast.LENGTH_SHORT);
            toast.show();
        }
        return false;
    }
}