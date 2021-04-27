package stu.xuronghao.ledger.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.ConstantVariable;
import stu.xuronghao.ledger.handler.DataPuller;
import stu.xuronghao.ledger.handler.GetHttpResponse;
import stu.xuronghao.ledger.handler.Validator;

public class SignUpPage extends AppCompatActivity {
    private final Context context = this;
    private final DataPuller dataPuller = new DataPuller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button btnSignUp = findViewById(R.id.btn_SignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = getSignUpInfo();
                if (user != null) {
                    if (dataPuller.signUpSender(user)) {
                        Intent intent = new Intent(SignUpPage.this, LoginPage.class);
                        intent.putExtra(ConstantVariable.USER, user);
                        Toast toast = Toast.makeText(context,
                                "好的！治账酱记住你了！", Toast.LENGTH_LONG);
                        toast.show();
                        startActivity(intent);
                        finish();
                    } else {
                        Toast toast = Toast.makeText(context,
                                "该账号已经被注册了哦，换一个吧", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        });

        Button btnCancel = findViewById(R.id.btn_SignUp_Cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private User getSignUpInfo() {
        //截取输入
        EditText userNo = findViewById(R.id.etx_SignUp_UserNo);
        EditText userName = findViewById(R.id.etx_SignUp_UserName);
        EditText userPasswd = findViewById(R.id.etx_SignUp_Passwd);
        EditText confirm = findViewById(R.id.etx_SignUp_Confirm);
        String userNoStr = userNo.getText().toString();
        String userNameStr = userName.getText().toString();
        String userPasswdStr = userPasswd.getText().toString();
        String confirmStr = confirm.getText().toString();

        if (Validator.checkSignUpInput(userNoStr, userNameStr, userPasswdStr, confirmStr, context)) {
            return new User(userNoStr, userNameStr, userPasswdStr);
        }
        return null;
    }
}