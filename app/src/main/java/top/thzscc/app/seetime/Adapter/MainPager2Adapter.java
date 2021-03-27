package top.thzscc.app.seetime.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import top.thzscc.app.seetime.Fragment.NoteBrowserFragment;
import top.thzscc.app.seetime.Fragment.NoteFragment;

public class MainPager2Adapter extends FragmentStateAdapter {

    public MainPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
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

    @Override
    public int getItemCount() {
        return 2;
    }
}