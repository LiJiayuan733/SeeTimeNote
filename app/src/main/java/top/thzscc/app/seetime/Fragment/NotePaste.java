package top.thzscc.app.seetime.Fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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

import top.thzscc.app.seetime.Adapter.PasteListAdapter;
import top.thzscc.app.seetime.R;
import top.thzscc.app.seetime.Utils.ContextUtils;
import top.thzscc.app.seetime.Utils.TransmitUtils;

public class NotePaste extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fload_copy_list,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView rc=getView().findViewById(R.id.float_copy_list);
        rc.setLayoutManager(new LinearLayoutManager(getContext()));
        rc.setAdapter(new PasteListAdapter());
    }
}
