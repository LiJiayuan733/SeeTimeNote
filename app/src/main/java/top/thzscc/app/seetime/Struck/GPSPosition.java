package top.thzscc.app.seetime.Struck;

import java.util.Date;

public class GPSPosition {
    public double time;
    public double wgs84_lng;
    public double wgs84_lat;
    public double gcj02_lng;
    public double gcj02_lat;
    public double bd09_lng;
    public double bd09_lat;
    public double status;
    public double r;

    public GPSPosition(double lng, double lat){
        wgs84_lat=lat;
        wgs84_lng=lng;
        GPSPosition.wgs84togcj02(this);
        GPSPosition.gcj02tobd09(this);
    }
    public GPSPosition(){

    }

    public static double x_PI = 3.14159265358979324 * 3000.0 / 180.0;
    public static double PI = 3.1415926535897932384626;
    public static double a = 6378245.0;
    public static double ee = 0.00669342162296594323;
    public static double transformlat(double lng, double lat) {
        double lat_ = lat;
        double lng_ = lng;
        double ret = -100.0 + 2.0 * lng_ + 3.0 * lat_ + 0.2 * lat_ * lat_ + 0.1 * lng_ * lat_ + 0.2 * Math.sqrt(Math.abs(lng_));
        ret += (20.0 * Math.sin(6.0 * lng_ * Math.PI) + 20.0 * Math.sin(2.0 * lng_ * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat_ * Math.PI) + 40.0 * Math.sin(lat_ / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat_ / 12.0 * Math.PI) + 320 * Math.sin(lat_ * Math.PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    public static double transformlng(double lng, double lat) {
        double lat_ = lat;
        double lng_ = lng;
        double ret = 300.0 + lng_ + 2.0 * lat_ + 0.1 * lng_ * lng_ + 0.1 * lng_ * lat_ + 0.1 * Math.sqrt(Math.abs(lng_));
        ret += (20.0 * Math.sin(6.0 * lng_ * Math.PI) + 20.0 * Math.sin(2.0 * lng_ * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng_ * Math.PI) + 40.0 * Math.sin(lng_ / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng_ / 12.0 * Math.PI) + 300.0 * Math.sin(lng_ / 30.0 * Math.PI)) * 2.0 / 3.0;
        return ret;
    }

    public static boolean out_of_china(double lng, double lat) {
        return !(lng > 73.66 && lng < 135.05 && lat > 3.86 && lat < 53.55);
    }
    public static GPSPosition wgs84togcj02(GPSPosition position){
        if (out_of_china(position.wgs84_lng, position.wgs84_lat)){
            position.gcj02_lng = position.wgs84_lng;
            position.gcj02_lat = position.wgs84_lat;
        }else{
            double dlat = transformlat(position.wgs84_lng - 105.0, position.wgs84_lat - 35.0);
            double dlng = transformlng(position.wgs84_lng - 105.0, position.wgs84_lat - 35.0);
            double radlat = position.wgs84_lat / 180.0 * Math.PI;
            double magic = Math.sin(radlat);
            magic = 1 - ee * magic * magic;
            double sqrtmagic = Math.sqrt(magic);
            dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * Math.PI);
            dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * Math.PI);
            position.gcj02_lat = position.wgs84_lat + dlat;
            position.gcj02_lng = position.wgs84_lng + dlng;
        }
        return position;
    }

    public static GPSPosition gcj02tobd09(GPSPosition position){
        double z = Math.sqrt(position.gcj02_lng * position.gcj02_lng  + position.gcj02_lat * position.gcj02_lat) + 0.00002 * Math.sin(position.gcj02_lat * Math.PI);
        double theta = Math.atan2(position.gcj02_lat, position.gcj02_lng ) + 0.000003 * Math.cos(position.gcj02_lng  * Math.PI);
        position.bd09_lng = z * Math.cos(theta) + 0.0065;
        position.bd09_lat = z * Math.sin(theta) + 0.006;
        return position;
    }
    public static GPSPosition parse_cmd(String GPS_cmd){
        String[] GPS_cmd_split = GPS_cmd.split(",");
        if(GPS_cmd_split.length!=15){
            return null;
        }else{
            if (GPS_cmd_split[2].equals("")||GPS_cmd_split[4].equals("")||GPS_cmd_split[6].equals("0")){
                return null;
            }else{
                double lat=Double.parseDouble(GPS_cmd_split[2].substring(0,2))+Double.parseDouble(GPS_cmd_split[2].substring(2))/60;
                double lng=Double.parseDouble(GPS_cmd_split[4].substring(0,3))+Double.parseDouble(GPS_cmd_split[4].substring(3))/60;
                GPSPosition position = new GPSPosition(lng,lat);
                String timeStr = GPS_cmd_split[1];
                int hour = Integer.parseInt(timeStr.substring(0,2));
                int minute = Integer.parseInt(timeStr.substring(2,4));
                int second = Integer.parseInt(timeStr.substring(4,6));
                Date nowDate= new Date(System.currentTimeMillis());
                Date date=new Date(nowDate.getYear(),nowDate.getMonth(),nowDate.getDate(),hour,minute,second);
                position.time=date.getTime();
                position.status=Integer.parseInt(GPS_cmd_split[6]);
                position.r=Double.parseDouble(GPS_cmd_split[8]);
                return position;
            }
        }
    }

    @Override
    public String toString() {
        return "{"+
                " 'wgs84_lng' : '"+ wgs84_lng +"'"+
                ", 'wgs84_lat' : '" + wgs84_lat +"'"+
                ", 'gcj02_lng' : '" + gcj02_lng +"'"+
                ", 'gcj02_lat' : '" + gcj02_lat +"'"+
                ", 'bd09_lng' : '" + bd09_lng +"'"+
                ", 'bd09_lat' : '" + bd09_lat +"'"+
                ", 'status' : '" + status +"'"+
                ", 'r' : '" + r +"'"+
                '}';
    }
}
