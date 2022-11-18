package com.example.roomdatabase.TypeConvers;

import androidx.room.TypeConverter;

import com.example.roomdatabase.EntityClass.UserModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class TypeConvertersData {

        @TypeConverter
        public  Date fromTimestamp(Long value) {
            return value == null ? null : new Date(value);
        }

        @TypeConverter
        public  Long dateToTimestamp(Date date) {
            return date == null ? null : date.getTime();
        }
    @TypeConverter
    public String getLocation(List<UserModel> strings) {
        if (strings == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<UserModel>>() {
        }.getType();
        return gson.toJson(strings, type);
    }

    @TypeConverter
    public List<UserModel> stringToList(String list) {
        if (list == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<UserModel>>() {
        }.getType();
        return gson.fromJson(list, type);
    }

}
