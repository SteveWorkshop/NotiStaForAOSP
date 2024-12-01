package io.github.materialapps.notistar.util;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

import io.github.materialapps.notistar.BaseApplication;

public class PackageUtil {
    public static String getNameByPackage(String packageName) throws PackageManager.NameNotFoundException {
        PackageManager pm= BaseApplication.getApplication().getPackageManager();
        ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 0);

        //PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
        String name = applicationInfo.loadLabel(pm).toString();
        return name;
    }

    public static List<ApplicationInfo> getAll()
    {
        PackageManager pm= BaseApplication.getApplication().getPackageManager();
       return   pm.getInstalledApplications(0);
    }
}
