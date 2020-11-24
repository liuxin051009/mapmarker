package com.meteor.mapmarker;

import com.meteor.mapmarker.pojo.MakrerPoint;
import com.meteor.mapmarker.utils.CaculateDistanceUtils;
import com.meteor.mapmarker.utils.IntervalUtil;
import com.meteor.mapmarker.utils.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapmarkerApplicationTests{
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void setOrGet(){
        //上线就注释掉，否则会干扰系统初始化
//        MakrerPoint makrerPoint = new MakrerPoint(Long.parseLong("1"),11l,22l,"测试上传地址","测试描述",1);
//
//        redisTemplate.opsForValue().set(makrerPoint.getLongitude()+":"+makrerPoint.getLatitude().toString(), JsonUtils.objectToJson(makrerPoint));
//        System.out.println(redisTemplate.opsForValue().get(makrerPoint.getLongitude()+":"+makrerPoint.getLatitude().toString()));
    }

    @Test
    public void getAll(){
        Set<String> keys = redisTemplate.keys("*");
        System.out.println(keys);
    }

    @Test
    public void getDis(){
        //31.824095,117.30974
        //31.815025,117.2211
        double distance = CaculateDistanceUtils.getDistance(31.815025,117.2211, 31.824095,117.30974);
        System.out.println(distance);
    }

    @Test
    public void removeList(){
        List<String> test = new LinkedList<String>();
        test.add("0");
        test.add("1");
        test.add("2");
        test.add("3");
        test.add("4");
        test.add("5");
        List<String> result = test.subList(0, test.size() - 1);
        System.out.println(result);

    }

//    @Test
//    public void testUtil(){
//        IntervalUtil intervalUtil = new IntervalUtil();
//        intervalUtil.isInTheInterval()
//
//    }
}

