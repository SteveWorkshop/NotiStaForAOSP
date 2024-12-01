package io.github.materialapps.notistar.ui.fragment;

import android.content.pm.ApplicationInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.github.materialapps.notistar.dao.AppDao;
import io.github.materialapps.notistar.dao.impl.AppDaoImpl;
import io.github.materialapps.notistar.entity.AppItem;
import io.github.materialapps.notistar.util.PackageUtil;
import lombok.Getter;
import lombok.Setter;

public class AppViewViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    @Getter
    @Setter
    private MutableLiveData<List<AppItem>> allData;

    private AppDao appDao;

    public AppViewViewModel()
    {
        appDao=new AppDaoImpl();
    }

    public void loadAppDataList()
    {
        new Thread(()->{
            List<AppItem> all = appDao.getAll();
            allData.postValue(all);
        }).start();
    }
}