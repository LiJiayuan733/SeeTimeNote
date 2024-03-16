package top.thzscc.app.seetime.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import top.thzscc.app.seetime.Adapter.NoteListAdapter;
import top.thzscc.app.seetime.R;
import top.thzscc.app.seetime.Utils.*;
import top.thzscc.app.seetime.ViewData.NoteData;

public class NoteListFragment extends Fragment {
    private RecyclerView noteList;          //列表视图
    private ImageView addButton;            //添加按钮
    private ImageView removeButton;         //删除按钮
    private ImageView uploadButton;         //上传数据
    private ImageView downloadButton;       //下载数据
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notelist,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //测试数据
        initData();
        //初始化界面
        initView();
        //初始化监听
        initListener();
    }
    public void initData(){
        try {
            TransmitUtils.noteDataList= FileUtils.geNote(getContext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void initView(){
        noteList=getView().findViewById(R.id.mainNoteList);
        addButton=getView().findViewById(R.id.mainAddButton);
        removeButton=getView().findViewById(R.id.mainRemoveButton);
        uploadButton=getView().findViewById(R.id.mainUploadCloud);
        downloadButton=getView().findViewById(R.id.mainDownloadCloud);
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
        uploadButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(ContextUtils.getContext(),"上传中...",Toast.LENGTH_SHORT).show();
                ServerUtils.UploadNoteToServer(TransmitUtils.noteDataList);
            }
        });
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContextUtils.getContext(),"下载中...",Toast.LENGTH_SHORT).show();
                ServerUtils.DownloadNoteFromServer(getActivity(),noteList);
            }
        });
    }
    public void save(){
        try {
            FileUtils.write(Objects.requireNonNull(getContext()),"note.fl",FileUtils.reNoteFile(TransmitUtils.noteDataList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroy() {
        save();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        noteList.getAdapter().notifyDataSetChanged();
    }
}
