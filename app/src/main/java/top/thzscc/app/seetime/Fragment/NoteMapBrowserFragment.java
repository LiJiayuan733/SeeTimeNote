package top.thzscc.app.seetime.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.*;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.Fragment;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.IEventHandler;
import top.thzscc.app.seetime.Interface.MapJsInterface;
import top.thzscc.app.seetime.R;

public class NoteMapBrowserFragment extends Fragment {
    private AgentWeb mAgentWeb;
    private LinearLayout mMapBrowserParent;
    private boolean isScrollX = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_browser,container,false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mMapBrowserParent=getView().findViewById(R.id.noteMapBrowserParent);
        mAgentWeb=AgentWeb.with(this)
                .setAgentWebParent(mMapBrowserParent,new LinearLayout.LayoutParams(-1,-1))
                .useDefaultIndicator(getResources().getColor(R.color.c月白,null),3).addJavascriptInterface("GPSp",new MapJsInterface(mAgentWeb,getContext()))
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
                .createAgentWeb().ready().go("file:///android_asset/index.html");
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
    }

    @Override
    public void onDestroyView() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroyView();
    }
}
