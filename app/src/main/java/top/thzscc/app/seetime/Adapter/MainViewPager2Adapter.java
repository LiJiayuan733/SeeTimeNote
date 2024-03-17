package top.thzscc.app.seetime.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import top.thzscc.app.seetime.Fragment.NoteListFragment;
import top.thzscc.app.seetime.Fragment.NoteMapBrowserFragment;

public class MainViewPager2Adapter extends FragmentStateAdapter {
    public MainViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new NoteListFragment();
            case 1:
                return new NoteMapBrowserFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
