package top.thzscc.app.seetime.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import top.thzscc.app.seetime.Adapter.MainPager2Adapter;
import top.thzscc.app.seetime.R;
import top.thzscc.app.seetime.Utils.CommonUtils;
import top.thzscc.app.seetime.Utils.ContextUtils;

public class NoteView extends AppCompatActivity {
    private ViewPager2 vp2;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextUtils.setNowContext(this);
        setContentView(R.layout.activity_mainv2);
        vp2=findViewById(R.id.mainPager);
        vp2.setAdapter(new MainPager2Adapter(this));
        vp2.setOnTouchListener(new View.OnTouchListener() {
            /*  防止ViewPager2过于灵敏
             * */
            private float startX;
            private float startY;
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                ViewPager2 vp=(ViewPager2)v;
                switch (ev.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //设置起始点
                        startX=ev.getX();
                        startY=ev.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //获取移动距离
                        float disX=(ev.getX()-startX<0)?startX-ev.getX():ev.getX()-startX;
                        float disY=(ev.getY()-startY<0)?startY-ev.getY():ev.getY()-startY;
                        if(disX<disY){
                            //禁止vp的滑动
                            vp.setUserInputEnabled(false);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        //恢复滑动距离
                        startX=0;
                        startY=0;
                        //恢复vp的滑动
                        vp.setUserInputEnabled(true);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
                return false;
            }
        });
        CommonUtils.FullScreen(this);
    }
}
