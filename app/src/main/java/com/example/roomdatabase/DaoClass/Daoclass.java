package com.example.roomdatabase.DaoClass;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomdatabase.EntityClass.LatestRecords;
import com.example.roomdatabase.EntityClass.TemplateEntity;
import com.example.roomdatabase.EntityClass.TitleEntity;
import com.example.roomdatabase.EntityClass.UserModel;

import java.util.List;

@Dao
public interface Daoclass {

    @Insert
    void insertAllData(UserModel model);

    @Insert
    void insertLatestRecords(LatestRecords lastetRecords);

    @Insert
    void insertTitleName(TitleEntity titleEntity);


    @Insert
    void insertTemplateName(TemplateEntity templateEntity);

    @Query("select * from titleTable WHERE `templatekey` =:tpkey")
    TitleEntity getTitle(int tpkey);

    //Select All Data
    @Query("select * from  user WHERE `foreigenkey` =:tempkey")
    List<UserModel> getAllData(int tempkey);

    @Query("select * from savedRecords WHERE `userkey` =:userKey")
    List<LatestRecords> getUpdatedRecords(int userKey);

    //DELETE DATA
    @Query("delete from savedRecords")
    void deleteData();

    @Query("DELETE FROM savedRecords WHERE `key` =:userKey")
    void deleteSingleItem(int userKey);

    @Query("select * from template_table")
     List<TemplateEntity> getTemplateList();

    @Update
    void upDateCount(UserModel userModel);

    @Query("UPDATE user SET count=:count WHERE `key` =:key")
    void restCountValue(int count , int key);

    @Query("UPDATE user SET name=:name ,count=:count WHERE `key` =:key")
    void updateList(String name , int count,int key);

    @Query("UPDATE template_table SET templatename=:name WHERE `templatekey` =:key")
    void updateTempList(String name ,int key);

    //Update Data
    @Query("UPDATE titleTable SET titleName=:titleName WHERE `key` =:titleKey")
    void updateTitleName(String titleName, int titleKey);

// delete Data

    @Query("DELETE FROM user WHERE `key` = :id")
    void deleteUserRecord(int id);


    @Query("DELETE FROM template_table WHERE `templatekey` = :id")
    void deleteTemplate(int id);


    @Query("DELETE FROM savedRecords WHERE `userkey` = :key")
    void deleteTempSavedRecords(int key);


    @Query("DELETE FROM user WHERE `foreigenkey` = :key")
    void deleteTempUserRecords(int key);

    @Query("DELETE FROM TITLETABLE WHERE `templatekey` = :key")
    void deleteTempTitleRecords(int key);

   /* @Query("update user SET name= :name ,address =:address, phoneno =:phoneno where `key`= :key")
    void updateData(String name, String phoneno, String address, int key);
*/

}
