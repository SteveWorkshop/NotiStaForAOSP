package io.github.materialapps.notistar.dao.impl;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.github.materialapps.notistar.dao.AppDao;
import io.github.materialapps.notistar.entity.AppItem;
import io.github.materialapps.notistar.util.PackageUtil;

public class AppDaoImpl implements AppDao {
    @Override
    public List<AppItem> getAll() {
        List<ApplicationInfo> all = PackageUtil.getAll();

        List<AppItem> ret=new ArrayList<>();
        for (ApplicationInfo applicationInfo : all) {
            //todo；显示图标
            AppItem item=new AppItem();
            item.setPackageInfoName(applicationInfo.packageName);
            String appName=null;

            try {
                appName=PackageUtil.getNameByPackage(applicationInfo.packageName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                appName=applicationInfo.packageName;
            }
            item.setAppName(appName);
            ret.add(item);
        }
        ret.sort((Comparator.comparing(AppItem::getAppName)));
        return ret;
    }
}
