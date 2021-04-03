package top.thzscc.app.seetime.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import top.thzscc.app.seetime.Adapter.PasteBoardAdapter;
import top.thzscc.app.seetime.R;
import top.thzscc.app.seetime.Struck.PasteItem;
import top.thzscc.app.seetime.Utils.ContextUtils;

public class NotePasteBoardFragment extends Fragment {
    public boolean iscan=false;
    public RecyclerView rv;
    private List<PasteItem> pasteItemList;
    public NotePasteBoardFragment(List<PasteItem> pasteItemList){
        this.pasteItemList=pasteItemList;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.view_paste_board,container,false);
        RecyclerView rv=v.findViewById(R.id.pasteboard);
        rv.setLayoutManager(new LinearLayoutManager(ContextUtils.getContext()));
        if(iscan!=false) {
            rv.setAdapter(new PasteBoardAdapter(pasteItemList));
        }
        rv.setAdapter(new PasteBoardAdapter(new ArrayList<>()));
        return v;
    }
    public void realSet(){
        rv.setAdapter(new PasteBoardAdapter(pasteItemList));
    }
}
