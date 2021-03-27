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
    public static void write(Context context,String name,String Content) throws IOException {
        /*File file=new File(context.getFilesDir(),name);
        if (!file.exists()){
            file.createNewFile();
        }*/
        try (FileOutputStream fos = context.openFileOutput(name, Context.MODE_PRIVATE)) {
            fos.write(Content.getBytes());
        }

    }
    public static String read(Context context,String name) throws FileNotFoundException {
        FileInputStream fis = context.openFileInput(name);
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
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
    public static String reNoteFile(List<NoteData> list){
        StringBuffer sb=new StringBuffer();
        for (NoteData nd:list){
            sb.append(nd.date.getTime()+"#split#"+nd.content+"#noteSplit#");
        }
        String s=sb.toString();
        s=s.substring(0,s.length()-"#noteSplit#".length());
        return s;
    }
    public static List<NoteData> geNote(Context context) throws FileNotFoundException {
        ArrayList<NoteData> noteData=new ArrayList<>();
        String s=read(context,"note.fl");
        Log.d("debug", "geNote: "+s);
        String[] noteSl=s.split("#noteSplit#");
        for(String sl:noteSl){
            String[] r=sl.split("#split#");
            noteData.add(new NoteData(new Date(Long.valueOf(r[0])),r[1]));
        }
        return noteData;
    }
}
