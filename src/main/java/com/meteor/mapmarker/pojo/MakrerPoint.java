package com.meteor.mapmarker.pojo;

import javax.print.DocFlavor;
import java.io.Serializable;

public class MakrerPoint implements Serializable {

    private Long id;
    //经度
    private Long longitude;
    //纬度
    private Long latitude;
    //后期可能需要上传图片或视频的路径
    private String path;
    //文字描述
    private String description;
    //后期可能需要，高发路段级别1:高发路段   2：普通    3：较少
    private int MakerLevele;
    /*为小程序准备的数据*/
    //marker的title 实际显示时，应该是  路段名称:文字描述的格式（eg 三孝口:该路段目前有抓拍点，请路过的驾驶员朋友注意）
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MakrerPoint(Long id, Long longitude, Long latitude, String path, String description, int makerLevele) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.path = path;
        this.description = description;
        MakerLevele = makerLevele;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMakerLevele() {
        return MakerLevele;
    }

    public void setMakerLevele(int makerLevele) {
        MakerLevele = makerLevele;
    }
}
