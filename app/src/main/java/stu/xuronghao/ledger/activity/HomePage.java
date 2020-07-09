package stu.xuronghao.ledger.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import stu.xuronghao.ledger.R;
import stu.xuronghao.ledger.entity.User;

public class HomePage extends AppCompatActivity {
    private static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //获取页面容器Host
        //Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_frag);
        //获取底部导航栏
        BottomNavigationView navigationView = findViewById(R.id.nav_bar);
        //        BottomNavigationView fragNavView = findViewById(R.id.top_Switch);
        //获取NavController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_frag);

        NavigationUI.setupWithNavController(navigationView, navController);
        //        NavigationUI.setupWithNavController(fragNavView,navController);

        //        int id =  navController.getCurrentDestination().getId();

        //        Log.w("id: ",""+id);
        //获取登录用户信息
        Intent login_info = getIntent();
        user = (User) login_info.getSerializableExtra("user");
        final Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_frag).navigateUp();
    }
}