package com.example.roomdatabase.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.roomdatabase.EntityClass.TemplateEntity;
import com.example.roomdatabase.EntityClass.UserModel;

import java.util.List;

public class TemplateWithUsers {

    @Embedded
    public TemplateEntity templateEntity;

    @Relation(parentColumn = "id", entityColumn = "templateKey", entity = UserModel.class)
    public List<UserModel> userModels;
}
