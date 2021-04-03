package top.thzscc.app.seetime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.xtoast.XToast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hotapk.fastandrutils.utils.FFileUtils;
import top.thzscc.app.seetime.Adapter.NoteListAdapter;
import top.thzscc.app.seetime.Struck.PasteItem;
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
    private ImageView removeButton;
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
        noteList=findViewById(R.id.mainNoteList);
        addButton=findViewById(R.id.mainAddButton);
        removeButton=findViewById(R.id.mainRemoveButton);
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
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContextUtils.getContext(),"点击你要删除的便签",Toast.LENGTH_SHORT).show();
                TransmitUtils.remove=true;
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
    public void toNote(){
        TransmitUtils.pasteItemList=new ArrayList<>();
        ClipboardManager cm = ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE));
        for(int i=0;i<cm.getPrimaryClip().getItemCount();i++){
            TransmitUtils.pasteItemList.add(new PasteItem(new Date(),cm.getPrimaryClip().getItemAt(i).coerceToText(this).toString()));
        }
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