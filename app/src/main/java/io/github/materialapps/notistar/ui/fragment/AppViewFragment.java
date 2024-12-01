package io.github.materialapps.notistar.ui.fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.materialapps.notistar.R;
import io.github.materialapps.notistar.entity.AppItem;

public class AppViewFragment extends Fragment {

    private AppViewViewModel mViewModel;

    public static AppViewFragment newInstance() {
        return new AppViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_app_view, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AppViewViewModel.class);
        // TODO: Use the ViewModel
        //观察app加载
        mViewModel.getAllData().observe(getViewLifecycleOwner(), new Observer<List<AppItem>>() {
            @Override
            public void onChanged(List<AppItem> appItems) {
                //加载app列表
            }
        });
    }

}