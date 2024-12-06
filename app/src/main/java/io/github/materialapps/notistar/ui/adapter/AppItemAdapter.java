package io.github.materialapps.notistar.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.materialapps.notistar.R;
import io.github.materialapps.notistar.entity.AppItem;
import lombok.Getter;
import lombok.Setter;

public class AppItemAdapter extends RecyclerView.Adapter<AppItemAdapter.ViewHolder> {

    @Getter
    @Setter
    private Bar bar;

    @Getter
    @Setter
    private List<AppItem> mData;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_flyout,parent,false);
        //todo:点击跳转
        ViewHolder vh=new ViewHolder(view);
        vh.itemView.setOnClickListener(v->{
            //条件查询
            int position = vh.getAbsoluteAdapterPosition();
            AppItem item = mData.get(position);
            bar.foo(item.getPackageInfoName(),position);
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppItem item=mData.get(position);
        if(item!=null)
        {
            holder.txbAppListAppName.setText(item.getAppName());
            //todo：加载图像缓存或者直接通过PM做
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        ImageView imgAppListIcon;
        TextView txbAppListAppName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            imgAppListIcon=itemView.findViewById(R.id.img_app_list_icon);
            txbAppListAppName=itemView.findViewById(R.id.txb_app_list_app_name);
        }
    }

    public interface Bar{
        void foo(String pkgName,int position);
    }
}
