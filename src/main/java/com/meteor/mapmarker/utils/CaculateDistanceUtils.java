package com.meteor.mapmarker.utils;

import com.meteor.mapmarker.pojo.Marker;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.text.Collator;
import java.util.*;

public class CaculateDistanceUtils {
    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 通过经纬度获取距离(单位：米)
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s*1000;
        return s;
    }

    //获得我当前位置附近3km内的标记点(自己除外)
    public static List<Marker> getNearby(String latitude,String longitude,List<Marker> markerList){
        ArrayList<Marker> markers = new ArrayList<>();
        for (Marker marker:markerList){
            double distance = getDistance(Double.parseDouble(latitude), Double.parseDouble(longitude), Double.parseDouble(marker.getLatitude()), Double.parseDouble(marker.getLongitude()));
            if (distance<=3000 && distance >= 3){
                BigDecimal decimal = new BigDecimal(distance);
                distance = decimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                marker.setDistance(distance);
                markers.add(marker);
            }
        }
        //排序
        List<Marker> sorts = sort(markers);
        return sorts;
    }

    private static List<Marker> sort(List<Marker> list) {
        Collections.sort(list, new Comparator<Marker>() {
            @Override
            public int compare(Marker v1, Marker v2) {

                if( v1.getDistance() < v2.getDistance() ){
                    return -1;
                }
                if(v1.getDistance() == v2.getDistance()){
                    return 0;
                }
                return 1;
            }
        });
        return list;
    }

}
