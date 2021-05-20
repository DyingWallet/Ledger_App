package stu.xuronghao.ledger.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.iflytek.cloud.Setting;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.entity.User;
import stu.xuronghao.ledger.handler.ConstantVariable;

public class HomePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Setting.setLocationEnable(false);
        setContentView(R.layout.activity_home_page);

        //获取底部导航栏
        BottomNavigationView navigationView = findViewById(R.id.nav_bar);
        //获取NavController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_frag);
        NavigationUI.setupWithNavController(navigationView, navController);
        //获取登录用户信息
        Intent logInInfo = getIntent();
        User user = (User) logInInfo.getSerializableExtra(ConstantVariable.USER);
        final Bundle bundle = new Bundle();
        bundle.putSerializable(ConstantVariable.USER, user);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_frag).navigateUp();
    }
}