package com.example.roomdatabase.EntityClass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.roomdatabase.TypeConvers.TypeConvertersData;

import java.util.Date;
import java.util.List;
@Entity(tableName = "savedRecords")
public class LatestRecords {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    private int key;

    @ColumnInfo(name = "userkey")
    private int userkey;

    @ColumnInfo()
    @TypeConverters(TypeConvertersData.class)
    private String savingTime;

    @ColumnInfo()
    @TypeConverters(TypeConvertersData.class)
    private List<UserModel> userModels;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getSavingTime() {
        return savingTime;
    }

    public void setSavingTime(String savingTime) {
        this.savingTime = savingTime;
    }

    public List<UserModel> getUserModels() {
        return userModels;
    }

    public void setUserModels(List<UserModel> userModels) {
        this.userModels = userModels;
    }

    public int getUserkey() {
        return userkey;
    }

    public void setUserkey(int userkey) {
        this.userkey = userkey;
    }
}
