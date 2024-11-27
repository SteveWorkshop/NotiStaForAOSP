package io.github.materialapps.notistar.config;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import io.github.materialapps.notistar.dao.ChatRecordDao;
import io.github.materialapps.notistar.dao.NotiDao;
import io.github.materialapps.notistar.entity.ChatRecord;
import io.github.materialapps.notistar.entity.NotiDumpItem;

@Database(version = 1,entities = {NotiDumpItem.class, ChatRecord.class},exportSchema = false)
public abstract class DBConfig extends RoomDatabase {
    public static final String DB_NAME="notistar.db";
    private static volatile DBConfig instance;

    public static synchronized DBConfig getInstance(Context context)
    {
        if(instance==null)
        {
            instance=create(context.getApplicationContext());
        }
        return instance;
    }

    @NonNull
    private static DBConfig create(final Context context)
    {
        return Room.databaseBuilder(context, DBConfig.class,DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public abstract NotiDao getNotiDao();
    public abstract ChatRecordDao getChatRecordDao();
}
