package top.thzscc.app.seetime.ViewData;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteData {
    public SimpleDateFormat sdf;
    public Date date;
    public String content;
    public NoteData(Date date,String content){
        this.date=date;
        this.content=content;
    }
    public SimpleDateFormat getDataFormat(){
        if(sdf==null){
            sdf=new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        }
        return sdf;
    }
}
