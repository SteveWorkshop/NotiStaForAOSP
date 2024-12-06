package io.github.materialapps.notistar.ui.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.materialapps.notistar.R;
import io.github.materialapps.notistar.databinding.FragmentHomeViewBinding;
import io.github.materialapps.notistar.entity.NotiDumpItem;
import io.github.materialapps.notistar.ui.adapter.NotiAdapter;

public class HomeViewFragment extends Fragment {

    private HomeViewViewModel mViewModel;

    private FragmentHomeViewBinding binding;

    public static HomeViewFragment newInstance() {
        return new HomeViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //inflater.inflate(R.layout.fragment_home_view, container, false)
        binding=FragmentHomeViewBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.mainToolbar);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewViewModel.class);
        // TODO: Use the ViewModel
        initView();
    }

    private void initView(){
        NotiAdapter adapter=new NotiAdapter(NotiAdapter.callback);
        adapter.setBar(((item, index) -> {
            Bundle bundle=new Bundle();
            bundle.putSerializable("noti_item",item);
            NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
            NavController navController = navHostFragment.getNavController();
            navController.navigate(R.id.navDetailFragment,bundle);
        }));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.listFlyoutArea.notiList.setLayoutManager(layoutManager);
        binding.listFlyoutArea.notiList.setAdapter(adapter);
        //绑定关系
        mViewModel.getAllData().observe(getViewLifecycleOwner(),o->{
            adapter.submitList((PagedList<NotiDumpItem>) o);
            binding.listFlyoutArea.notiList.scrollToPosition(0);
        });
    }
}