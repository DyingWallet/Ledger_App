package stu.xuronghao.ledger.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.consts.ConstantVariable;
import stu.xuronghao.ledger.handler.network.DataPuller;
import stu.xuronghao.ledger.handler.validator.Validator;


public class LoginPage extends AppCompatActivity {
    private final Context context = this;
    private User user = new User();
    private final DataPuller dataPuller = new DataPuller();

    TextView txvSignUp;
    TextView txvForHelp;
    EditText edtxUserNo;
    EditText edtxUserPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btn_Login);
        btnLogin.setOnClickListener(v -> login());

        edtxUserNo = findViewById(R.id.etxv_Login_userNo);
        edtxUserPasswd = findViewById(R.id.etxv_Login_Passwd);
        txvSignUp = findViewById(R.id.txv_Login_SignUp);
        txvForHelp = findViewById(R.id.txv_Login_ForHelp);

        txvSignUp.setOnClickListener(v -> gotoSignUp());
        txvForHelp.setOnClickListener(v -> testLogin());

        String[] permissions = {Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
        };
        //验证是否许可权限
        for (String str : permissions) {
            if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                this.requestPermissions(permissions, ConstantVariable.REQUEST_CODE_CONTRACT);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent signUoInfo = getIntent();
        User signUpUser;
        if ((signUpUser = (User) signUoInfo.getSerializableExtra(ConstantVariable.USER)) != null) {
            user = signUpUser;
            edtxUserNo.setText(user.getUserNo());
            edtxUserPasswd.setText(user.getUserPasswd());
        }
    }

    private void login() {
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

    private boolean getLoginInfo() {
        String userNoStr = edtxUserNo.getText().toString();
        String userPasswdStr = edtxUserPasswd.getText().toString();

        if (Validator.checkLoginInput(userNoStr, userPasswdStr, context)) {
            user.setUserNo(edtxUserNo.getText().toString());
            user.setUserPasswd(edtxUserPasswd.getText().toString());
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

    private void testLogin() {
        edtxUserNo.setText("dw@qq.com");
        edtxUserPasswd.setText("123456");
        login();
    }

}