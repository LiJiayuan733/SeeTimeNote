package top.thzscc.app.seetime.Fragment;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sendtion.xrichtext.RichTextEditor;

import java.util.ArrayList;
import java.util.Date;

import top.thzscc.app.seetime.Adapter.MainPager2Adapter;
import top.thzscc.app.seetime.Adapter.PasteListAdapter;
import top.thzscc.app.seetime.R;
import top.thzscc.app.seetime.Struck.PasteItem;
import top.thzscc.app.seetime.Utils.ContextUtils;
import top.thzscc.app.seetime.Utils.HeightProvider;
import top.thzscc.app.seetime.Utils.TransmitUtils;
import top.thzscc.app.seetime.View.NoteView;

import static androidx.core.content.ContextCompat.getSystemService;

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
        mNoteEditor.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /*if(hasFocus&& (((NotePasteBoardFragment)(((MainPager2Adapter)NoteView.noteView.vp2.getAdapter()).get())).iscan==false)){
                    toNote();
                    Log.d("debug", "onFocusChange: dsdsdd");
                    ((NotePasteBoardFragment)(((MainPager2Adapter)NoteView.noteView.vp2.getAdapter()).get())).iscan=true;
                    ((NotePasteBoardFragment)(((MainPager2Adapter)NoteView.noteView.vp2.getAdapter()).get())).realSet();
                }*/
            }
            public void toNote(){
                TransmitUtils.pasteItemList=new ArrayList<>();
                ClipboardManager cm = ((ClipboardManager)ContextUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE));
                for(int i=0;i<cm.getPrimaryClip().getItemCount();i++){
                    TransmitUtils.pasteItemList.add(new PasteItem(new Date(),cm.getPrimaryClip().getItemAt(i).coerceToText(ContextUtils.getContext()).toString()));
                }
            }
        });
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
