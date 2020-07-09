package stu.xuronghao.ledger.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.DataPuller;


public class LoginPage extends AppCompatActivity {
    private static final int
            LoggedIn = 2,
            Activited          = 1,
            FrozenUser         = 0,
            NoUser             = -1,
            WrongPasswd        = -2;
    private Context    context    = this;
    private User       user       = new User();
    private DataPuller dataPuller = new DataPuller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_Login = findViewById(R.id.btn_Login);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getLoginInfo()) {
                    User temp = dataPuller.LoginSender(user);
                    if(temp == null){
                        Toast toast = Toast.makeText(context,
                                "似乎和服务器君失去了联系...请检查网络连接哦~~~", Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }
                    user = temp;
                    CheckStatus();
                }
            }
        });

        TextView txv_SignUp = findViewById(R.id.txv_Login_SignUp);

        txv_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoSignUp();
            }
        });

        TextView txv_ForHelp = findViewById(R.id.txv_Login_ForHelp);

        txv_ForHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkipLogin();
            }
        });

    }

    private boolean getLoginInfo() {
        EditText userNo = findViewById(R.id.etxv_Login_userNo);
        EditText userPasswd = findViewById(R.id.etxv_Login_Passwd);
        String No = userNo.getText().toString(),
                Passwd = userPasswd.getText().toString();

        if (CheckInput(No, Passwd)) {
            user.setUserNo(userNo.getText().toString());
            user.setUserPasswd(userPasswd.getText().toString());
            user.setUserStatus(Activited);
            return true;
        }
        return false;
    }

    private boolean CheckInput(String userNo, String Passwd) {
        if (isEmail(userNo)) {
            if (!Passwd.isEmpty()) {
                if(Passwd.length() >= 6 && Passwd.length() <= 16)
                    return true;
                else {
                    Toast toast = Toast.makeText(context, "密码长度超出限制啦！", Toast.LENGTH_LONG);
                    toast.show();
                }
            } else {
                Toast toast = Toast.makeText(context, "密码不能为空哦！", Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(context, "请输入正确的邮箱哦！", Toast.LENGTH_LONG);
            toast.show();
        }
        return false;
    }

    private boolean isEmail(String email){
        if (null==email || "".equals(email)){
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private void CheckStatus() {
        if (user.getUserStatus() == LoggedIn) {
            //登陆成功
            Intent intent = new Intent(LoginPage.this, HomePage.class);
            //注意传递对象需要对象继承Serializable
            intent.putExtra("user", user);
            Toast toast = Toast.makeText(
                    context, "欢迎回来，" + user.getUserName() + "君!", Toast.LENGTH_LONG);
            toast.show();
            startActivity(intent);
            finish();
        } else if (user.getUserStatus() == FrozenUser) {
            Toast toast = Toast.makeText(context,
                    "该账户已停止使用!", Toast.LENGTH_LONG);
            toast.show();
        } else if (user.getUserStatus() == NoUser) {
            Toast toast = Toast.makeText(context,
                    "亲，你还没注册呢！", Toast.LENGTH_LONG);
            toast.show();
        } else if (user.getUserStatus() == WrongPasswd) {
            Toast toast = Toast.makeText(context,
                    "亲，密码错了哟！", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void GotoSignUp() {
        Intent intent = new Intent(LoginPage.this, SignUpPage.class);
        startActivity(intent);
    }

    private void SkipLogin() {
        User user = new User();
        user.setUserNo("dw@qq.com");
        user.setUserName("dww");
        user.setUserPasswd("123456");
        user.setUserCredits(0);
        user.setUserStatus(1);
        Intent intent = new Intent(LoginPage.this, HomePage.class);
        intent.putExtra("user", user);
        Toast toast = Toast.makeText(context, "你又要来调教我了吗？程序员早晚头秃！Wryyyyy!", Toast.LENGTH_SHORT);
        toast.show();
        startActivity(intent);
        finish();
    }

}