package top.thzscc.app.seetime.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.IEventHandler;

import top.thzscc.app.seetime.R;

public class NoteBrowserFragment extends Fragment {
    private LinearLayout mBrowserParent;
    private AgentWeb mAgentWeb;
    private EditText mUrlTo;
    private boolean isScrollX = false;
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
                .createAgentWeb().ready().go("https://www.baidu.com/");
        mAgentWeb.getWebCreator().getWebView().setOnTouchListener(new View.OnTouchListener() {
            private float startX;
            private float startY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getPointerCount(event) == 1) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            startX=event.getX();
                            startY=event.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            float disX=(event.getX()-startX<0)?startX-event.getX():event.getX()-startX;
                            float disY=(event.getY()-startY<0)?startY-event.getY():event.getY()-startY;
                            if(disX<disY&&disX+disY>10){
                                isScrollX=false;
                                //禁止vp的滑动
                                v.getParent().getParent()
                                        .requestDisallowInterceptTouchEvent(!isScrollX);
                            }else if(isScrollX=false){
                                isScrollX=true;
                                v.getParent().getParent().
                                        requestDisallowInterceptTouchEvent(true);
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            //恢复滑动距离
                            startX=0;
                            startY=0;
                            //恢复vp的滑动
                            v.getParent().getParent().
                                    requestDisallowInterceptTouchEvent(true);
                            break;
                    }
                } else {
                    //使webview可以双指缩放（前提是webview必须开启缩放功能，并且加载的网页也支持缩放）
                    v.getParent().getParent().
                            requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
        mUrlTo=getView().findViewById(R.id.fg_br_urlTo);
        mUrlTo.setText(mAgentWeb.getWebCreator().getWebView().getUrl());
        mUrlTo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mAgentWeb.getUrlLoader().loadUrl(mUrlTo.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    public void onDestroyView() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroyView();
    }
}
