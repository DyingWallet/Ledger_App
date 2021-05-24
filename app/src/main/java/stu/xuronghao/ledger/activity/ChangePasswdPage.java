package stu.xuronghao.ledger.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.ConstantVariable;
import stu.xuronghao.ledger.handler.DataPuller;
import stu.xuronghao.ledger.handler.Validator;

public class ChangePasswdPage extends AppCompatActivity {
    private User user;
    private Context context;
    private String originPasswd;
    private String newPasswd;
    private String confirmPasswd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passwd);
        context = this;
        user = (User) getIntent().getSerializableExtra(ConstantVariable.USER);
        EditText etxOriginPasswd = findViewById(R.id.etx_change_passwd_Origin);
        EditText etxChangePasswd = findViewById(R.id.etx_change_passwd_Passwd);
        EditText etxConfirm = findViewById(R.id.etx_change_passwd_Confirm);

        Button confirm = findViewById(R.id.btn_change_passwd);
        Button cancel = findViewById(R.id.btn_change_passwd_Cancel);

        confirm.setOnClickListener(v -> {
            originPasswd = etxOriginPasswd.getText().toString();
            newPasswd = etxChangePasswd.getText().toString();
            confirmPasswd = etxConfirm.getText().toString();

            if (Validator.checkChangePasswd(user, originPasswd, newPasswd, confirmPasswd, context)) {
                User temp = new User(user);
                temp.setUserPasswd(newPasswd);
                DataPuller dataPuller = new DataPuller();
                if (dataPuller.updateUserInfo(temp)) {
                    user = dataPuller.getUserInfo(user);
                }
            }
        });
        cancel.setOnClickListener(v -> finish());
    }
}