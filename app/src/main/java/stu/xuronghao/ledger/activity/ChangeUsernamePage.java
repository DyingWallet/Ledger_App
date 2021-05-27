package stu.xuronghao.ledger.activity;

import android.content.Context;
import android.content.Intent;
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

public class ChangeUsernamePage extends AppCompatActivity {
    private User user;
    private Context context;
    private String newUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);

        context = this;
        user = (User) getIntent().getSerializableExtra(ConstantVariable.USER);
        EditText etxChangeUsername = findViewById(R.id.etx_change_username_Username);
        Button confirm = findViewById(R.id.btn_change_username);
        Button cancel = findViewById(R.id.btn_change_username_Cancel);

        confirm.setOnClickListener(v -> {
            newUsername = etxChangeUsername.getText().toString();
            if (Validator.checkUsername(newUsername, context)) {
                User temp = new User(user);
                temp.setUserName(newUsername);
                DataPuller dataPuller = new DataPuller();
                if (dataPuller.updateUserInfo(temp)) {
                    user = dataPuller.getUserInfo(user);
                    Intent res = new Intent();
                    res.putExtra(ConstantVariable.USER,user);
                    setResult(1,res);
                    finish();
                }
            }
        });
        cancel.setOnClickListener(v -> finish());
    }
}