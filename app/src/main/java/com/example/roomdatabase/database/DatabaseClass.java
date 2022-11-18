package com.example.roomdatabase.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.roomdatabase.DaoClass.Daoclass;
import com.example.roomdatabase.EntityClass.LatestRecords;
import com.example.roomdatabase.EntityClass.TemplateEntity;
import com.example.roomdatabase.EntityClass.TitleEntity;
import com.example.roomdatabase.EntityClass.UserModel;
import com.example.roomdatabase.TypeConvers.TypeConvertersData;

@Database(entities = {TemplateEntity.class,UserModel.class, LatestRecords.class, TitleEntity.class}, version = 3)
public abstract class DatabaseClass extends RoomDatabase {
    private static DatabaseClass instance;


    @TypeConverters(TypeConvertersData.class)
    public abstract Daoclass getDao();

    public static DatabaseClass getDatabase(final Context context) {
        if (instance == null) {
            synchronized (DatabaseClass.class) {
                instance = Room.databaseBuilder(context, DatabaseClass.class, "DATABASE").allowMainThreadQueries().build();
            }
        }
        return instance;


    }


}
