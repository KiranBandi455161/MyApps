package com.example.roomdatabase.EntityClass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "titleTable")
public class TitleEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    private int key;

    @ColumnInfo(name = "templatekey")
    private int templateKey;

    @ColumnInfo(name = "titleName")
    private String titleName;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getTitleName() {
        return titleName;
    }
    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public int getTemplateKey() {
        return templateKey;
    }

    public void setTemplateKey(int templateKey) {
        this.templateKey = templateKey;
    }
}
