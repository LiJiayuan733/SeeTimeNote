package top.thzscc.app.seetime.Fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.IEventHandler;

import top.thzscc.app.seetime.R;

public class NoteBrowserFragment extends Fragment {
    private LinearLayout mBrowserParent;
    private AgentWeb mAgentWeb;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_browser,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBrowserParent=getView().findViewById(R.id.noteBrowserParent);

        mAgentWeb=AgentWeb.with(this)
                .setAgentWebParent(mBrowserParent,new LinearLayout.LayoutParams(-1,-1))
                .useDefaultIndicator()
                .setEventHanadler(new IEventHandler() {
                    @Override
                    public boolean onKeyDown(int keyCode, KeyEvent event) {
                        return false;
                    }

                    @Override
                    public boolean back() {
                        if(mAgentWeb.back()){
                            return true;
                        }
                        return false;
                    }
                })
                .createAgentWeb().ready().go("https://cn.bing.com/");
    }
}
