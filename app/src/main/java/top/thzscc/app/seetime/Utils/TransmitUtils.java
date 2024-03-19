package top.thzscc.app.seetime.Utils;

import java.util.ArrayList;
import java.util.List;

import top.thzscc.app.seetime.Struck.GPSPosition;
import top.thzscc.app.seetime.Struck.PasteItem;
import top.thzscc.app.seetime.ViewData.NoteData;

//数据传递
public class TransmitUtils {
    public static boolean remove=false;
    public static ArrayList<String> data;
    public static List<PasteItem> pasteItemList;
    public static String useLog;
    public static int index;
    public static List<NoteData> noteDataList=new ArrayList<>();
    public static NoteData get(){
        return noteDataList.get(index);
    }
    public static List<GPSPosition> positions;
    public static void set(NoteData data){
        noteDataList.set(index,data);
    }
}
