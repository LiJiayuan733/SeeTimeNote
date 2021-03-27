package top.thzscc.app.seetime;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.xtoast.XToast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import cn.hotapk.fastandrutils.utils.FFileUtils;
import top.thzscc.app.seetime.Adapter.NoteListAdapter;
import top.thzscc.app.seetime.Utils.CommonUtils;
import top.thzscc.app.seetime.Utils.ContextUtils;
import top.thzscc.app.seetime.Utils.FileUtils;
import top.thzscc.app.seetime.Utils.JumpViewTo;
import top.thzscc.app.seetime.Utils.TransmitUtils;
import top.thzscc.app.seetime.ViewData.NoteData;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private RecyclerView noteList;          //列表视图
    private ImageView addButton;            //添加按钮
    private XToast xToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextUtils.setNowContext(this);
        setContentView(R.layout.activity_main);

        getPermission();
        try {
            TransmitUtils.noteDataList=FileUtils.geNote(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //初始化界面
        initView();
        //初始化监听
        initListener();

        CommonUtils.FullScreen(this);
        showXToast();
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
    private void showXToast(){
        //TODO: 实现粘贴板跟踪
        this.xToast=new XToast<>(getApplication())
                .setView(R.layout.float_copy)
                .setDraggable().setOnClickListener(new XToast.OnClickListener<View>() {
                    @Override
                    public void onClick(XToast<?> toast, View view) {

                    }
                }).show();
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
        noteList=findViewById(R.id.mainNoteList);
        addButton=findViewById(R.id.mainAddButton);

        noteList.setLayoutManager(new LinearLayoutManager(ContextUtils.getContext()));
        noteList.setAdapter(new NoteListAdapter());
    }
    public void initListener(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteData n=new NoteData(new Date(),"开始记录吧!");
                TransmitUtils.index=((NoteListAdapter)noteList.getAdapter()).addNote(n);
                JumpViewTo.NoteView();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断是否为从系统权限页返回
        if (requestCode == XXPermissions.REQUEST_CODE) {
            if (XXPermissions.isGranted(this, Permission.RECORD_AUDIO) &&
                    XXPermissions.isGranted(this, Permission.Group.CALENDAR)) {
                Toast.makeText(this, "获取权限成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"获取权限失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        noteList.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        save();
        super.onDestroy();
    }
    public void save(){
        try {
            FileUtils.write(this,"note.fl",FileUtils.reNoteFile(TransmitUtils.noteDataList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}