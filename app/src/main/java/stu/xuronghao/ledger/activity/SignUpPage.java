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
import stu.xuronghao.ledger.handler.DataPuller;
import stu.xuronghao.ledger.handler.GetHttpResponse;

public class SignUpPage extends AppCompatActivity {
    private Context context = this;
    private GetHttpResponse response = new GetHttpResponse();
    private User user = new User();
    private DataPuller dataPuller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dataPuller = new DataPuller();
        Button btn_SignUp = findViewById(R.id.btn_SignUp);
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSignUpInfo()) {
                    if (dataPuller.signUpSender(user)) {
                        Intent intent = new Intent(SignUpPage.this, LoginPage.class);
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

        Button btn_Cancel = findViewById(R.id.btn_SignUp_Cancel);
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(SignUpPage.this,LoginPage.class);
                //startActivity(intent);
                finish();
            }
        });
    }

    private boolean getSignUpInfo() {
        //截取输入
        EditText userNo = findViewById(R.id.etx_SignUp_UserNo),
                userName = findViewById(R.id.etx_SignUp_UserName),
                userPasswd = findViewById(R.id.etx_SignUp_Passwd),
                Confirm = findViewById(R.id.etx_SignUp_Confirm);
        String No = userNo.getText().toString(),
                Name = userName.getText().toString(),
                Passwd = userPasswd.getText().toString(),
                str = Confirm.getText().toString();

        if (CheckInput(No, Name, Passwd, str)) {
            user.setUserNo(No);
            user.setUserName(Name);
            user.setUserPasswd(Passwd);
            return true;
        }
        return false;
    }

    private boolean CheckInput(String userNo, String userName,
                               String userPasswd, String Confirm) {
        if (isEmail(userNo)) {
            if (!(!userName.isEmpty() && (userName.length() < 3 || userName.length() > 16))) {
                if (!userPasswd.isEmpty()) {
                    if (userPasswd.length() < 6) {
                        Toast toast = Toast.makeText(context, "密码最少要6位哦！", Toast.LENGTH_LONG);
                        toast.show();
                    } else if (userPasswd.equals(Confirm)) {
                        return true;
                    } else {
                        Toast toast = Toast.makeText(context, "请再次确认密码！", Toast.LENGTH_LONG);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(context, "密码不能为空！", Toast.LENGTH_LONG);
                    toast.show();
                }
            } else {
                Toast toast = Toast.makeText(context, "昵称长度超出限制啦！", Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(context, "邮箱格式有问题！！", Toast.LENGTH_LONG);
            toast.show();
        }
        return false;
    }

    private boolean isEmail(String email) {
        if (null == email || "".equals(email)) {
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}