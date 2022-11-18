package com.example.roomdatabase.EntityClass;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.RESTRICT;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.roomdatabase.TypeConvers.TypeConvertersData;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "user")

public class UserModel implements Serializable {

    //Primary key
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    private int key;

    @ColumnInfo(name = "foreigenkey")
    private long templateParentId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "name")
    private String vehicleName;


    @ColumnInfo(name = "count")
    private int count;


    @ColumnInfo(name = "startTime")
    @TypeConverters(TypeConvertersData.class)
    private Date startTime;


    @ColumnInfo(name = "lastTime")
    @TypeConverters(TypeConvertersData.class)
    private Date lastTime;



    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public long getTemplateParentId() {
        return templateParentId;
    }

    public void setTemplateParentId(long templateParentId) {
        this.templateParentId = templateParentId;
    }
}
