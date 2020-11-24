package com.meteor.mapmarker.utils;

import com.meteor.mapmarker.pojo.Marker;

import java.util.ArrayList;
import java.util.List;

public class VisiableUtils {


    public static List<Marker> isVisiable(List<Marker> markerList, String minLatitude, String minLongitude, String maxLatitude, String maxLongitude) {

        List<Marker> temp = new ArrayList<>();

        for(Marker marker : markerList){
                if(    Double.parseDouble(marker.getLatitude()) <=Double.parseDouble(maxLatitude)
                    && Double.parseDouble(marker.getLatitude()) >= Double.parseDouble(minLatitude)
                    && Double.parseDouble(marker.getLongitude())<=Double.parseDouble(maxLongitude)
                    && Double.parseDouble(marker.getLongitude())>=Double.parseDouble(minLongitude)
            ){
                temp.add(marker);
            }
        }
        return temp;
    }
}
