package top.thzscc.app.seetime.Utils;

import android.content.Context;

public class ContextUtils {
    private static Context context;
    public static void setNowContext(Context context){
        ContextUtils.context=context;
    }
    public static Context getContext(){
        return ContextUtils.context;
    }
}
