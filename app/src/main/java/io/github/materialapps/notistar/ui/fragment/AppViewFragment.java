package io.github.materialapps.notistar.ui.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.materialapps.notistar.R;
import io.github.materialapps.notistar.dao.AppDao;
import io.github.materialapps.notistar.dao.impl.AppDaoImpl;
import io.github.materialapps.notistar.databinding.FragmentAppViewBinding;
import io.github.materialapps.notistar.entity.AppItem;
import io.github.materialapps.notistar.entity.NotiDumpItem;
import io.github.materialapps.notistar.ui.adapter.AppItemAdapter;
import io.github.materialapps.notistar.ui.adapter.NotiAdapter;

public class AppViewFragment extends Fragment {

    private AppViewViewModel mViewModel;

    private FragmentAppViewBinding binding;

    public static AppViewFragment newInstance() {
        return new AppViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //inflater.inflate(R.layout.fragment_app_view, container, false);
        binding=FragmentAppViewBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.appListMainToolbar);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AppViewViewModel.class);
        // TODO: Use the ViewModel
        initView();
    }

    private void initView()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        AppItemAdapter adapter=new AppItemAdapter();

        adapter.setBar(((pkgName, position) -> {
            Toast.makeText(getActivity(), "?", Toast.LENGTH_SHORT).show();
            mViewModel.getCurrentPackageName().setValue(pkgName);
            mViewModel.loadNoiByPackage(pkgName);
        }));

        //todo:temporary workaround
        AppDao dao=new AppDaoImpl();

        new  Thread(()->{
            List<AppItem> all = dao.getAll();
           getActivity().runOnUiThread(()->{
               adapter.setMData(all);
               binding.appSelectScrollList.setLayoutManager(layoutManager);
               binding.appSelectScrollList.setAdapter(adapter);
           });
        }).start();




        //adapter.setMData(new ArrayList<>());//啥情况？？？？


        //观察app加载
        mViewModel.getAllData().observe(getViewLifecycleOwner(), appItems -> {
            //加载app列表
            adapter.setMData(appItems);
            adapter.notifyDataSetChanged();
        });

        //通知初始化
        NotiAdapter mAdapter=new NotiAdapter(NotiAdapter.callback);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.listAppFlyoutArea.notiList.setLayoutManager(mLayoutManager);
        mViewModel.getNotiLiveData().observe(getViewLifecycleOwner(),o->{
            mAdapter.submitList((PagedList<NotiDumpItem>) o);
            binding.listAppFlyoutArea.notiList.scrollToPosition(0);
        });
    }
}