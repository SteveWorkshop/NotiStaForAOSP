package io.github.materialapps.notistar;

import android.app.Application;
import android.content.Context;

import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.color.DynamicColors;

public class BaseApplication extends Application {
    private static volatile Context context;

    public static synchronized Context getApplication()
    {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
