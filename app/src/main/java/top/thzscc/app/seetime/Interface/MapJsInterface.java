package top.thzscc.app.seetime.Interface;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import com.just.agentweb.AgentWeb;
import top.thzscc.app.seetime.Struck.GPSPosition;
import top.thzscc.app.seetime.Utils.TransmitUtils;

public class MapJsInterface {
    private AgentWeb agent;

    private Context context;

    public MapJsInterface(AgentWeb agent, Context context) {
        this.agent = agent;
        this.context = context;
    }

    @JavascriptInterface
    public String getPositions(){
        String Text="[";
        for(GPSPosition pos:TransmitUtils.positions){
            Text+="{\"lat\":"+pos.gcj02_lat+",\"lng\":"+pos.gcj02_lng+"},";
        }
        return Text.substring(0,Text.length()-1)+"]";
    }
    @JavascriptInterface
    public void debug(String text){
        Log.d("HTML", text);
    }
}
