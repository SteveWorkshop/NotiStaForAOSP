package io.github.materialapps.notistar.ui.fragment;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import io.github.materialapps.notistar.BaseApplication;
import io.github.materialapps.notistar.config.DBConfig;
import io.github.materialapps.notistar.dao.NotiDao;
import io.github.materialapps.notistar.entity.NotiDumpItem;
import io.github.materialapps.notistar.ui.BaseViewModel;
import lombok.Getter;
import lombok.Setter;

public class HomeViewViewModel extends BaseViewModel {
    // TODO: Implement the ViewModel
    public static final int PAGE_SIZE = 15;
    public static final  boolean ENABLE_PLACEHOLDERS = false;

    private NotiDao notiDao;

    @Getter
    @Setter
    private LiveData allData;//暂时让他可修改便于操作？

    @Getter
    @Setter
    private MutableLiveData<Boolean> loading=new MutableLiveData<>(false);

    @Getter
    @Setter
    private MutableLiveData<Boolean> faliure=new MutableLiveData<>(false);

    @Getter
    @Setter
    private PagedList.Config.Builder builder;

    public HomeViewViewModel(LifecycleOwner owner){
        init();
    }

    public HomeViewViewModel(){
        init();
    }

    private void init(){
        builder=new PagedList.Config.Builder();
        builder.setPageSize(PAGE_SIZE);                       //配置分页加载的数量
        builder.setEnablePlaceholders(ENABLE_PLACEHOLDERS);     //配置是否启动PlaceHolders
        builder.setInitialLoadSizeHint(PAGE_SIZE);

        notiDao= DBConfig.getInstance(BaseApplication.getApplication()).getNotiDao();
        loadAllByPage();
    }

    public void loadAllByPage(){
        LivePagedListBuilder livePagedListBuilder=new LivePagedListBuilder(notiDao.getAll_v2(),builder.build());
        allData=livePagedListBuilder.build();
    }

    public void loadByQueryByPage(String query){
        LivePagedListBuilder livePagedListBuilder=new LivePagedListBuilder(notiDao.getByContent_v2(query),builder.build());
        allData=livePagedListBuilder.build();
    }

    public void loadByPackageByPage(String packageName){
        LivePagedListBuilder livePagedListBuilder=new LivePagedListBuilder(notiDao.getByPackageName_v2(packageName),builder.build());
        allData=livePagedListBuilder.build();
    }

    //好像没什么用？不应该这样操作？
    public void receiveNotification(NotiDumpItem item)
    {
        //todo:may modify or pre-process something here...
        new Thread(()->{
            loading.postValue(true);
            Long nwid = notiDao.add(item);
            loading.postValue(true);
            if(nwid>0)
            {
                loading.postValue(true);
            }
            else{
                faliure.postValue(true);
            }

        }).start();
    }

    
}