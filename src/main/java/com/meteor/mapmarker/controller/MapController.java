package com.meteor.mapmarker.controller;

import com.meteor.mapmarker.pojo.Marker;
import com.meteor.mapmarker.utils.CaculateDistanceUtils;
import com.meteor.mapmarker.utils.IDUtils;
import com.meteor.mapmarker.utils.JsonUtils;
import com.meteor.mapmarker.utils.VisiableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
public class MapController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //初始化数据，获得经纬度同时，获得经纬度上的文字
    //map格式：   key:   经度:纬度          value:文字说明
    @GetMapping("/init")
    public String init(String latitude,String longitude){
//        System.out.println(latitude+"/"+longitude);

        //获得redis里的值并拼接
        List<Marker> markerList = getMarkerList();
        //3公里内的标记点展示，并按距离远近排序
        List<Marker>  nearbys = CaculateDistanceUtils.getNearby(latitude, longitude, markerList);

        //我的位置
        Marker myLocation = new Marker();
        myLocation.setId("0");
        myLocation.setTitle("我的位置");
        myLocation.setLongitude(longitude);
        myLocation.setLatitude(latitude);
        myLocation.setIconPath("../resource/myLocation.png");
        System.out.println("地图初始化...");
//        redisTemplate.opsForValue(0,latitude + ":" + longitude, TimeUnit.HOURS);
//        redisTemplate.opsForValue().set("0",latitude + ":" + longitude+":"+"我的位置", 1,TimeUnit.HOURS);
        nearbys.add(myLocation);
        return JsonUtils.objectToJson(nearbys);
    }

    //新增标记点
    @GetMapping("/add")
    public String add(String latitude,String longitude,String address){
        String id = IDUtils.genItemId()+"";
//        redisTemplate.opsForValue().set(id,latitude+":"+longitude+":"+address);
        redisTemplate.opsForValue().set(id,latitude+":"+longitude+":"+address,1, TimeUnit.HOURS);
        return id;
    }

    //查询标记点并将其改变样式
    @GetMapping("/getMarkered")
    public String getMarkered(String id){
        String[] values = redisTemplate.opsForValue().get(id).split(":");
        Map map = new HashMap<>();
        Marker myselected = new Marker();
        myselected.setId(id);
        myselected.setLongitude(values[1]);
        myselected.setLatitude(values[0]);
        myselected.setTitle(values[2]);
        myselected.setIconPath("../resource/selected.png");
        map.put("myselected",myselected);
        return JsonUtils.objectToJson(map);
    }

    //移动地图
    @GetMapping("/move2Location")
    public String move2Location(String initLatitude,String initLongitude
    ,String minLongitude,String minLatitude,String maxLongitude,String maxLatitude){

        List<Marker> markerList = getMarkerList();

        //判断是否在地图视野内
        List<Marker> visiableList = VisiableUtils.isVisiable(markerList,minLatitude,minLongitude,maxLatitude,maxLongitude);

        //我的位置
        Marker myLocation = new Marker();
        myLocation.setId("0");
        myLocation.setTitle("我的位置");
        myLocation.setLongitude(initLongitude);
        myLocation.setLatitude(initLatitude);
        myLocation.setIconPath("../resource/myLocation.png");

        visiableList.add(myLocation);

        return JsonUtils.objectToJson(visiableList);
    }

    private List<Marker> getMarkerList() {
        Set<String> keys = redisTemplate.keys("*");
        List<Marker> markerList = new ArrayList();
        if(!keys.isEmpty()){
            for (String key: keys){
                Marker temp = new Marker();
                //准备标记点列表
                temp.setId(key);
                String[] values = redisTemplate.opsForValue().get(key).split(":");

                temp.setLatitude(values[0]);
                temp.setLongitude(values[1]);
                temp.setTitle(redisTemplate.opsForValue().get(key).split(":")[2]);
                temp.setIconPath("../resource/location.png");
                markerList.add(temp);
            }
        }
        return markerList;
    }
}
