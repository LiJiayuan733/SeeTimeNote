package top.thzscc.app.seetime;

import android.annotation.SuppressLint;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.viewpager2.widget.ViewPager2;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

import top.thzscc.app.seetime.Adapter.MainViewPager2Adapter;
import top.thzscc.app.seetime.Adapter.Pager2TouchListener;
import top.thzscc.app.seetime.Utils.*;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private ViewPager2 vp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextUtils.setNowContext(this);
        setContentView(R.layout.activity_main);

        getPermission();    //获取权限

        //初始化界面
        initView();
        //初始化监听
        initListener();

        CommonUtils.FullScreen(this);
    }

    private void getPermission(){
        verifyStoragePermissions();
        XXPermissions.with(this).permission(Permission.SYSTEM_ALERT_WINDOW).request(new OnPermissionCallback(){

            @Override
            public void onGranted(List<String> permissions, boolean all) {
                if(!all){
                    Toast.makeText(MainActivity.this, "部分权限获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDenied(List<String> permissions, boolean never) {
                if (never) {
                    Toast.makeText(MainActivity.this, "被永久拒绝授权，请手动授予权限", Toast.LENGTH_SHORT).show();
                    // 如果是被永久拒绝就跳转到应用权限系统设置页面
                    XXPermissions.startPermissionActivity(MainActivity.this, permissions);
                } else {
                    Toast.makeText(MainActivity.this,"获取权限失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void verifyStoragePermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) { // 申请权限
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    public void initView(){
        vp2=findViewById(R.id.mainViewPager);
        vp2.setAdapter(new MainViewPager2Adapter(this));
        vp2.setCurrentItem(0);
    }
    @SuppressLint("ClickableViewAccessibility")
    public void initListener(){
        vp2.setOnTouchListener(new Pager2TouchListener());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断是否为从系统权限页返回
        if (requestCode == XXPermissions.REQUEST_CODE) {
            if (XXPermissions.isGranted(this, Permission.RECORD_AUDIO) && XXPermissions.isGranted(this, Permission.Group.CALENDAR)) {
                Toast.makeText(this, "获取权限成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"获取权限失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
//    public void toNote(){
//        //获取剪切板列表
//        TransmitUtils.pasteItemList=new ArrayList<>();
//        ClipboardManager cm = ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE));
//        for(int i=0;i<cm.getPrimaryClip().getItemCount();i++){
//            TransmitUtils.pasteItemList.add(new PasteItem(new Date(),cm.getPrimaryClip().getItemAt(i).coerceToText(this).toString()));
//        }
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}