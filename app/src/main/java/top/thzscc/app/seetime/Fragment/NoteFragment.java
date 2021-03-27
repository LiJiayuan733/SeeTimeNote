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

import com.sendtion.xrichtext.RichTextEditor;

import top.thzscc.app.seetime.R;
import top.thzscc.app.seetime.Utils.ContextUtils;
import top.thzscc.app.seetime.Utils.HeightProvider;
import top.thzscc.app.seetime.Utils.TransmitUtils;

public class NoteFragment extends Fragment {
    private ImageView mSaveButton;
    private ImageView mBackButton;
    private RichTextEditor mNoteEditor;
    private ViewGroup mToolsView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initListener();
    }
    public void initView(){
        mSaveButton=getView().findViewById(R.id.noteBarSaveButton);
        mNoteEditor=getView().findViewById(R.id.noteEditor);
        mBackButton=getView().findViewById(R.id.noteBarBackButton);
        mToolsView=getView().findViewById(R.id.noteToolsBar);
    }
    public void initData(){
        int index=mNoteEditor.getLastIndex();
        mNoteEditor.addEditTextAtIndex(0, TransmitUtils.get().content);
    }
    public void initListener(){
        new HeightProvider(getActivity()).init().setHeightListener(new HeightProvider.HeightListener() {
            @Override
            public void onHeightChanged(int height) {
                if(height>0){
                    mToolsView.setPadding(0,0,0,height);
                }else {
                    mToolsView.setPadding(0,0,0,0);
                }
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransmitUtils.get().content="";
                TransmitUtils.useLog="Note:setNoteData";
                for(RichTextEditor.EditData edit:mNoteEditor.buildEditData()){
                    if(edit.inputStr!=null){
                        TransmitUtils.get().content+=edit.inputStr;
                    }
                }
                Toast.makeText(ContextUtils.getContext(),"保存成功",Toast.LENGTH_SHORT).show();
            }
        });
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransmitUtils.get().content="";
                TransmitUtils.useLog="Note:setNoteData";
                for(RichTextEditor.EditData edit:mNoteEditor.buildEditData()){
                    if(edit.inputStr!=null){
                        TransmitUtils.get().content+=edit.inputStr;
                    }
                }
                getActivity().finish();
            }
        });
    }

}
