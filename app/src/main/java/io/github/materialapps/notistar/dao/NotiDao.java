package io.github.materialapps.notistar.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.github.materialapps.notistar.entity.NotiDumpItem;

@Dao
public interface NotiDao {

    @Insert
    Long add(NotiDumpItem item);

    @Update
    int update(NotiDumpItem item);

    //todo:性能优化
    @Deprecated
    @Query("select * from NotiDumpItem order by `when` desc")
    List<NotiDumpItem> getAll();

    @Query("select * from NotiDumpItem order by `when` desc")
    DataSource.Factory<Integer,NotiDumpItem> getAll_v2();

    @Deprecated
    @Query("select * from NotiDumpItem where content like '%'+:content+'%' order by `when` desc")
    List<NotiDumpItem> getByContent(String content);

    @Query("select * from NotiDumpItem where content like '%'+:content+'%' order by `when` desc")
    DataSource.Factory<Integer,NotiDumpItem> getByContent_v2(String content);

    @Query("select * from NotiDumpItem where packageName=:packageName order by `when` desc")
    List<NotiDumpItem> getByPackageName(String packageName);

    @Query("select * from NotiDumpItem where packageName=:packageName order by `when` desc")
    DataSource.Factory<Integer,NotiDumpItem> getByPackageName_v2(String packageName);

    @Query("update NotiDumpItem set isDeleted=1 where id=:id")
    int deleteById(Long id);

    @Query("update NotiDumpItem set isDeleted=0 where id=:id")
    int recycleById(Long id);

    @Query("delete from NotiDumpItem where id=:id and isDeleted=1")
    int eraseById(Long id);
}
