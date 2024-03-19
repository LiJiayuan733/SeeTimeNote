package top.thzscc.app.seetime.Utils;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import top.thzscc.app.seetime.Struck.GPSPosition;
import top.thzscc.app.seetime.ViewData.NoteData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServerUtils {
    public static final String SERVER_NAME = "map.seetimeuc.cn";
    public static final int SERVER_PORT = 8095;
    public static void DownloadNoteFromServer(Activity activity,RecyclerView noteList){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket=new Socket(SERVER_NAME,SERVER_PORT);
                    PrintWriter pw=new PrintWriter(socket.getOutputStream());
                    BufferedReader br =new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    pw.println("DownloadNoteContext");
                    pw.flush();

                    String line=br.readLine();
                    int len=Integer.parseInt(line);
                    char[] buf= new char[len];
                    br.read(buf,0,len);

                    pw.println("OK");
                    pw.flush();
                    br.close();
                    pw.close();
                    socket.close();

                    ArrayList<NoteData> noteData=new ArrayList<>();
                    String s=new String(buf);
                    String[] noteSl=s.split("#noteSplit#");
                    for(String sl:noteSl){
                        String[] r=sl.split("#split#");
                        noteData.add(new NoteData(new Date(Long.parseLong(r[0])),r[1]));
                    }
                    TransmitUtils.noteDataList=noteData;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            noteList.getAdapter().notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public static void UploadNoteToServer(List<NoteData> list){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket=new Socket(SERVER_NAME,SERVER_PORT);
                    PrintWriter pw=new PrintWriter(socket.getOutputStream());
                    BufferedReader br =new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    pw.println("UpdateNoteContext");
                    pw.flush();

                    StringBuffer sb=new StringBuffer();
                    for (NoteData nd:list){
                        sb.append(nd.date.getTime()+"#split#"+nd.content+"#noteSplit#");
                    }
                    String s=sb.toString();
                    s=s.substring(0,s.length()-"#noteSplit#".length());

                    pw.println(s.length());
                    pw.flush();

                    pw.write(s);
                    pw.flush();

                    String response=br.readLine();
                    if (response.equals("OK")){
                        pw.close();
                        br.close();
                        socket.close();
                    }else{
                        throw new IOException("Upload failed...");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    public static void DeleteIfExits(String time){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket=new Socket(SERVER_NAME,SERVER_PORT);
                    PrintWriter pw=new PrintWriter(socket.getOutputStream());
                    BufferedReader br =new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    pw.println("DeleteIfExits");
                    pw.flush();

                    pw.println(time.length());
                    pw.flush();

                    pw.write(time);
                    pw.flush();

                    String response=br.readLine();
                    if (response.equals("OK")){
                        pw.close();
                        br.close();
                        socket.close();
                    }else{
                        throw new IOException("Delete failed...");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    public static void GPSPositionGet(long StartTime,long EndTime){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TransmitUtils.positions=new ArrayList<>();
                    Socket socket=new Socket(SERVER_NAME,SERVER_PORT);
                    PrintWriter pw=new PrintWriter(socket.getOutputStream());
                    BufferedReader br =new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    pw.println("GPSPositionGet");
                    pw.flush();

                    pw.println(StartTime);
                    pw.flush();

                    pw.println(EndTime);
                    pw.flush();

                    String line;
                    while ((line = br.readLine()) != null) {
                        if(line.equals("END")){
                            pw.println("OK");
                            pw.flush();
                        }else{
                            //处理数据
                            //pw.println(rs.getLong("Time")+","+rs.getDouble("wgs84_lng")+","+rs.getDouble("wgs84_lat")+","+rs.getDouble("gcj02_lng")+","+rs.getDouble("gcj02_lat")+","+rs.getDouble("bd09_lng")+","+rs.getDouble("bd09_lat")+","+rs.getDouble("r"));
                            //1710812663000,2910.85826,11929.33786,2910.85826,11929.33786,2910.897054034114,11929.33596196586,1.28
                            String[] data =line.split(",");
                            GPSPosition pos=new GPSPosition();
                            pos.time = Long.parseLong(data[0]);
                            pos.wgs84_lng = Double.parseDouble(data[1]);
                            pos.wgs84_lat = Double.parseDouble(data[2]);
                            pos.gcj02_lng = Double.parseDouble(data[3]);
                            pos.gcj02_lat = Double.parseDouble(data[4]);
                            pos.bd09_lng = Double.parseDouble(data[5]);
                            pos.bd09_lat = Double.parseDouble(data[6]);
                            pos.r = Double.parseDouble(data[7]);
                            pos.status = 1;
                            TransmitUtils.positions.add(pos);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
