package io.github.materialapps.notistar.service;

//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
import android.app.Notification;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.RemoteViews;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.github.materialapps.notistar.BaseApplication;
import io.github.materialapps.notistar.config.DBConfig;
import io.github.materialapps.notistar.dao.NotiDao;
import io.github.materialapps.notistar.entity.NotiDumpItem;
import io.github.materialapps.notistar.util.IDUtil;

public class DumpNotificationService extends NotificationListenerService {

    public static final String UNSUPPORTED_CONTENT="暂不支持此类型的控件";

    private NotiDao notiDao;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        System.out.println("===============================来了");

        notiDao= DBConfig.getInstance(BaseApplication.getApplication()).getNotiDao();


        NotiDumpItem item=new NotiDumpItem();
        String packageName = sbn.getPackageName();
        Notification notification = sbn.getNotification();

        String content="";

        Bundle extras = notification.extras;
        RemoteViews contentView = notification.contentView;

        if(contentView!=null){
            //todo：暂不支持复杂视图的解析
        }

        String category = notification.category;
        String channelId = notification.getChannelId();
        int importance = notification.priority;
        String key = sbn.getKey();

        String title = (String) extras.get(Notification.EXTRA_TITLE);//通知标题
        String subText = (String) extras.get(Notification.EXTRA_SUB_TEXT);//通知附加内容
        String text = (String) extras.get(Notification.EXTRA_TEXT);//通知内容

        long when = notification.when;
        final String[] fileName = {null};//一眼丁真，我测你马。。。。。
        Bitmap bitmap = extras.getParcelable(Notification.EXTRA_PICTURE);
        if(bitmap!=null){
           new Thread(()->{
               try {
                   fileName[0] = IDUtil.getRandomName();
                   File dir=getExternalFilesDir(null);//android/data/package/files
                   File myFile=new File(dir.getAbsolutePath()+"/icons");
                   if(!myFile.exists())
                   {
                       myFile.mkdir();
                   }
                   //存储文件
                   File img=new File(myFile, fileName[0]);
                   if(img.exists())
                   {
                       img.delete();
                   }
                   img.createNewFile();
                   FileOutputStream fs=new FileOutputStream(img);
                   bitmap.compress(Bitmap.CompressFormat.PNG,100,fs);//？？
                   fs.flush();
                   fs.close();

               } catch (IOException e) {
                   e.printStackTrace();
               }
           }).start();
        }

        String name=fileName[0];
        String bigText = (String) extras.getCharSequence(Notification.EXTRA_BIG_TEXT);


        //持久化
        item.setKey(key);
        item.setCategory(category);
        item.setPackageName(packageName);
        item.setImportance(importance);
        item.setIconPath(name);
        item.setWhen(when);

        item.setTitle(title);
        item.setSubText(subText);
        item.setContent(content);
        item.setBigText(bigText);

        //插入数据
        notiDao.add(item);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        System.out.println("==============================走了");
    }
}