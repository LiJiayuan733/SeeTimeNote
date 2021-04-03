package top.thzscc.app.seetime.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import top.thzscc.app.seetime.Fragment.NoteBrowserFragment;
import top.thzscc.app.seetime.Fragment.NoteFragment;
import top.thzscc.app.seetime.Fragment.NotePaste;
import top.thzscc.app.seetime.Fragment.NotePasteBoardFragment;
import top.thzscc.app.seetime.Struck.PasteItem;

public class MainPager2Adapter extends FragmentStateAdapter {
    private List<PasteItem> pasteItemList;
    public MainPager2Adapter(@NonNull FragmentActivity fragmentActivity,List<PasteItem> pasteItemList) {
        super(fragmentActivity);
        this.pasteItemList=pasteItemList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new NoteFragment();
            case 1:
                return new NoteBrowserFragment();
        }
        return null;
    }
    public void get(){

    }
    @Override
    public int getItemCount() {
        return 2;
    }
}