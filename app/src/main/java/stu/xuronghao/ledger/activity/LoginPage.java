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

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.ConstantVariable;
import stu.xuronghao.ledger.handler.DataPuller;
import stu.xuronghao.ledger.handler.Validator;


public class LoginPage extends AppCompatActivity {
    private final Context context = this;
    private User user = new User();
    private final DataPuller dataPuller = new DataPuller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btn_Login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getLoginInfo()) {
                    User temp = dataPuller.loginSender(user);
                    if (temp == null) {
                        Toast toast = Toast.makeText(context,
                                ConstantVariable.ERR_CONNECT_FAILED, Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }
                    user = temp;
                    checkStatus();
                }
            }
        });
        TextView txvSignUp = findViewById(R.id.txv_Login_SignUp);
        txvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSignUp();
            }
        });
        TextView txvForHelp = findViewById(R.id.txv_Login_ForHelp);
        txvForHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipLogin();
            }
        });
    }

    private boolean getLoginInfo() {
        EditText userNo = findViewById(R.id.etxv_Login_userNo);
        EditText userPasswd = findViewById(R.id.etxv_Login_Passwd);
        String userNoStr = userNo.getText().toString();
        String userPasswdStr = userPasswd.getText().toString();

        if (Validator.checkLoginInput(userNoStr, userPasswdStr,context)) {
            user.setUserNo(userNo.getText().toString());
            user.setUserPasswd(userPasswd.getText().toString());
            user.setUserStatus(ConstantVariable.ACTIVE);
            return true;
        }
        return false;
    }

    private void checkStatus() {
        if (user.getUserStatus() == ConstantVariable.LOGGED_IN) {
            //登陆成功
            Intent intent = new Intent(LoginPage.this, HomePage.class);
            //注意传递对象需要对象继承Serializable
            intent.putExtra(ConstantVariable.USER, user);
            Toast toast = Toast.makeText(
                    context, "欢迎回来，" + user.getUserName() + "君!", Toast.LENGTH_LONG);
            toast.show();
            startActivity(intent);
            finish();
        } else if (user.getUserStatus() == ConstantVariable.FROZEN_USER) {
            Toast toast = Toast.makeText(context,
                    ConstantVariable.HINT_USER_FROZEN, Toast.LENGTH_LONG);
            toast.show();
        } else if (user.getUserStatus() == ConstantVariable.NO_USER) {
            Toast toast = Toast.makeText(context,
                    ConstantVariable.HINT_USER_NOT_FOUND, Toast.LENGTH_LONG);
            toast.show();
        } else if (user.getUserStatus() == ConstantVariable.WRONG_PASSWD) {
            Toast toast = Toast.makeText(context,
                    ConstantVariable.HINT_WRONG_PASSWD, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void gotoSignUp() {
        Intent intent = new Intent(LoginPage.this, SignUpPage.class);
        startActivity(intent);
    }

    private void skipLogin() {
        User testUser = new User();
        testUser.setUserNo("dw@qq.com");
        testUser.setUserName("dww");
        testUser.setUserPasswd("123456");
        testUser.setUserCredits(0);
        testUser.setUserStatus(1);
        Intent intent = new Intent(LoginPage.this, HomePage.class);
        intent.putExtra("user", testUser);
        Toast toast = Toast.makeText(context, "---调试账户---", Toast.LENGTH_SHORT);
        toast.show();
        startActivity(intent);
        finish();
    }

}