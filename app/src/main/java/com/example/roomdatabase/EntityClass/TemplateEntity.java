package com.example.roomdatabase.EntityClass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "template_table")
public class TemplateEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "templatekey")
    private int key;

    @ColumnInfo(name = "templatename")
    private String templateName;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
