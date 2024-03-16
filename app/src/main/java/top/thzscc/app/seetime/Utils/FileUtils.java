package top.thzscc.app.seetime.Utils;


import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hotapk.fastandrutils.utils.FFileUtils;
import top.thzscc.app.seetime.ViewData.NoteData;

public class FileUtils {
    /**
     * @param context 上下文
     * @param name 文件名
     * @param Content 要写入内容
     * */
    public static void write(Context context,String name,String Content) throws IOException {
        /*File file=new File(context.getFilesDir(),name);
        if (!file.exists()){
            file.createNewFile();
        }*/
        try (FileOutputStream fos = context.openFileOutput(name, Context.MODE_PRIVATE)) {
            fos.write(Content.getBytes());
        }

    }
    /**
     * @param context 上下文
     * @param name 文件名
     * @return 文件内容
     * */
    public static String read(Context context,String name) throws FileNotFoundException {
        FileInputStream fis = context.openFileInput(name);
        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        }
        return stringBuilder.toString();
    }

    /* Note File Format
       $data#split#$content#noteSplit#$data#split#$content
     */

    /**
     * @param list 日记列表
     * @return 格式化后文件内容
     * */
    public static String reNoteFile(List<NoteData> list){
        StringBuffer sb=new StringBuffer();
        for (NoteData nd:list){
            sb.append(nd.date.getTime()+"#split#"+nd.content+"#noteSplit#");
        }
        String s=sb.toString();
        s=s.substring(0,s.length()-"#noteSplit#".length());
        return s;
    }
    /**
     * @param context 上下文
     * @return 读取到的日记列表
     * */
    public static List<NoteData> geNote(Context context) throws FileNotFoundException {
        ArrayList<NoteData> noteData=new ArrayList<>();
        String s=read(context,"note.fl");
        Log.d("debug", "geNote: "+s);
        String[] noteSl=s.split("#noteSplit#");
        for(String sl:noteSl){
            String[] r=sl.split("#split#");
            noteData.add(new NoteData(new Date(Long.parseLong(r[0])),r[1]));
        }
        return noteData;
    }
}
