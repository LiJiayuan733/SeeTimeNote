package top.thzscc.app.seetime.Adapter;

import android.view.MotionEvent;
import android.view.View;
import androidx.viewpager2.widget.ViewPager2;

public class Pager2TouchListener implements View.OnTouchListener{
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
}
