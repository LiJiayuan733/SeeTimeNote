package top.thzscc.app.seetime.Utils;

import java.util.ArrayList;
import java.util.List;

import top.thzscc.app.seetime.ViewData.NoteData;

//数据传递
public class TransmitUtils {
    public static String useLog;
    public static int index;
    public static List<NoteData> noteDataList=new ArrayList<>();
    public static NoteData get(){
        return noteDataList.get(index);
    }
    public static void set(NoteData data){
        noteDataList.set(index,data);
    }
}
