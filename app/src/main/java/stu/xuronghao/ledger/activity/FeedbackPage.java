package stu.xuronghao.ledger.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.entity.Feedback;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.consts.ConstantVariable;
import stu.xuronghao.ledger.handler.network.DataPuller;
import stu.xuronghao.ledger.handler.DateHandler;

public class FeedbackPage extends AppCompatActivity {
    private User user;
    private Feedback feedback;
    private Context context;
    private final DataPuller dataPuller = new DataPuller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_page);
        context = this;
        user = (User) getIntent().getSerializableExtra(ConstantVariable.USER);
        //与默认选中项对齐
        feedback = new Feedback();
        feedback.setFbType(ConstantVariable.FB_TYPE_ADVICE);
        EditText content = findViewById(R.id.etx_fb_Content);
        //获取按钮控件
        Button push = findViewById(R.id.btn_fb_push);
        Button cancel = findViewById(R.id.btn_fb_Cancel);

        //设置为多行输入
        content.setGravity(Gravity.TOP);
        content.setSingleLine(false);
        content.setHorizontallyScrolling(false);

        //获取选项组
        RadioGroup group = findViewById(R.id.fb_type_group);

        //RadioGroup监听器
        group.setOnCheckedChangeListener((group1, checkedId) -> {
            if(R.id.rbtn_advise == checkedId)
                feedback.setFbType(ConstantVariable.FB_TYPE_ADVICE);
            else if(R.id.rbtn_bug == checkedId)
                feedback.setFbType(ConstantVariable.FB_TYPE_BUG);
            else if(R.id.rbtn_froze == checkedId)
                feedback.setFbType(ConstantVariable.FB_TYPE_FROZE);
        });

        push.setOnClickListener(v -> {
            if (getInputInfo()) {
                if (dataPuller.handOverFb(feedback)) {
                    Toast toast = Toast.makeText(context, ConstantVariable.INFO_RECEIVED, Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                } else {
                    Toast toast = Toast.makeText(context,
                            ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        cancel.setOnClickListener(v -> finish());
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
        if (!"".equals(title)) {
            if (!"".equals(content)) {
                return true;
            } else {
                Toast toast = Toast.makeText(context, ConstantVariable.HINT_EMPTY_CONTENT, Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(context, ConstantVariable.HINT_EMPTY_TITLE, Toast.LENGTH_SHORT);
            toast.show();
        }
        return false;
    }
}