package top.thzscc.app.seetime.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

import top.thzscc.app.seetime.Adapter.NoteListAdapter;
import top.thzscc.app.seetime.R;
import top.thzscc.app.seetime.Utils.ContextUtils;
import top.thzscc.app.seetime.Utils.TransmitUtils;
import top.thzscc.app.seetime.ViewData.NoteData;

public class NoteListFragment extends Fragment {
    private RecyclerView noteList;          //列表视图
    private ImageView addButton;            //添加按钮
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //测试数据
        testData();
        //初始化界面
        initView();
        //初始化监听
        initListener();
    }
    public void testData(){
        TransmitUtils.noteDataList.add(new NoteData(new Date(),"Hello World"));
    }
    public void initView(){
        noteList=getView().findViewById(R.id.mainNoteList);
        addButton=getView().findViewById(R.id.mainAddButton);

        noteList.setLayoutManager(new LinearLayoutManager(ContextUtils.getContext()));
        noteList.setAdapter(new NoteListAdapter());
    }
    public void initListener(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteData n=new NoteData(new Date(),"开始记录吧!");
                TransmitUtils.index=((NoteListAdapter)noteList.getAdapter()).addNote(n);
                getActivity().getSupportFragmentManager().beginTransaction().add(new NoteFragment(),"Node"+TransmitUtils.index);
            }
        });
    }
}
