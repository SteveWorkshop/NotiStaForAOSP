package io.github.materialapps.notistar.ui.fragment;

import android.content.pm.ApplicationInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;

import io.github.materialapps.notistar.BaseApplication;
import io.github.materialapps.notistar.config.DBConfig;
import io.github.materialapps.notistar.dao.AppDao;
import io.github.materialapps.notistar.dao.NotiDao;
import io.github.materialapps.notistar.dao.impl.AppDaoImpl;
import io.github.materialapps.notistar.entity.AppItem;
import io.github.materialapps.notistar.util.PackageUtil;
import lombok.Getter;
import lombok.Setter;

public class AppViewViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public static final int PAGE_SIZE = 15;
    public static final  boolean ENABLE_PLACEHOLDERS = false;

    @Getter
    @Setter
    private MutableLiveData<List<AppItem>> allData=new MutableLiveData<>();

    @Getter
    @Setter
    private LiveData notiLiveData;

    @Getter
    @Setter
    private PagedList.Config.Builder builder;

    private NotiDao notiDao;
    private AppDao appDao;

    @Getter
    @Setter
    private MutableLiveData<String> currentPackageName=new MutableLiveData<>("");

    public AppViewViewModel()
    {
        init();
    }

    //好像搞错了。。。。。。。
    public void loadAppDataList()
    {
        new Thread(()->{
            List<AppItem> all = appDao.getAll();
            allData.postValue(all);
        }).start();
    }

    public  void loadNoiByPackage(String pkgName)
    {
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(notiDao.getByPackageName_v2(pkgName),builder.build());
        notiLiveData=livePagedListBuilder.build();
    }

    private void init(){
        builder=new PagedList.Config.Builder();
        builder.setPageSize(PAGE_SIZE);                       //配置分页加载的数量
        builder.setEnablePlaceholders(ENABLE_PLACEHOLDERS);     //配置是否启动PlaceHolders
        builder.setInitialLoadSizeHint(PAGE_SIZE);

        notiDao= DBConfig.getInstance(BaseApplication.getApplication()).getNotiDao();
        appDao=new AppDaoImpl();


        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(notiDao.getByPackageName_v2(currentPackageName.getValue()),builder.build());
        notiLiveData=livePagedListBuilder.build();
    }
}