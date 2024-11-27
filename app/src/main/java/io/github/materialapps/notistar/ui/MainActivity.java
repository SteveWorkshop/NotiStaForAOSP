package io.github.materialapps.notistar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

import io.github.materialapps.notistar.R;
import io.github.materialapps.notistar.databinding.ActivityMainBinding;
import io.github.materialapps.notistar.service.DumpNotificationService;
import io.github.materialapps.notistar.util.DialogUtil;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private NavHostFragment navHostFragment;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        navHostFragment= (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
        navController=navHostFragment.getNavController();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //todo:collapse nav bar
        binding.navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.menu_all:{
                    navController.navigate(R.id.homeViewFragment,null);
                    break;
                }
                case R.id.menu_app_category:{
                    navController.navigate(R.id.appViewFragment,null);
                    break;
                }
                default: {
                    Toast.makeText(this, "敬请期待，好东西就要来了", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            return false;
        });

        //检测必要的权限
        if(NotificationManagerCompat.getEnabledListenerPackages(this).contains(getPackageName()))
        {
            //启动监听服务
            Intent intent=new Intent(this,DumpNotificationService.class);
            startService(intent);
        }
        else{
            //申请通知权限
            MaterialAlertDialogBuilder builder=new MaterialAlertDialogBuilder(this);
            builder.setTitle("必要权限申请");
            builder.setMessage("本软件需要通知访问权限以读取您的所有通知。本软件免费开源，除非您配置并主动使用在线功能，否则本软件绝对不会监听或上传您的任何隐私信息，请放心开启");
            builder.setCancelable(false);
            builder.setPositiveButton("好的，去开启",((dialog, which) -> {
                Intent intent=new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivity(intent);
                DialogUtil.canCloseDialog(dialog,true);
            }));
            builder.setNegativeButton("不要~俺就不~=￣ω￣=",((dialog, which) -> {
                Toast.makeText(this, "非常抱歉，无法在不启用通知权限的情况下使用此软件/(ㄒoㄒ)/~~", Toast.LENGTH_SHORT).show();
                DialogUtil.canCloseDialog(dialog,true);
                finish();
            }));
            builder.setNeutralButton("查看软件源代码",((dialog, which) -> {

                DialogUtil.canCloseDialog(dialog, false);

            }));
            builder.show();
        }
    }


}