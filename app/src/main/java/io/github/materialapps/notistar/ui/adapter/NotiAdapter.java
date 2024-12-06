package io.github.materialapps.notistar.ui.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

import io.github.materialapps.notistar.R;
import io.github.materialapps.notistar.entity.NotiDumpItem;
import lombok.Getter;
import lombok.Setter;

public class NotiAdapter extends PagedListAdapter<NotiDumpItem,NotiAdapter.ViewHolder> {

    public static final Callback callback=new Callback();

    @Getter
    @Setter
    private Bar bar;

    public NotiAdapter(@NonNull DiffUtil.ItemCallback<NotiDumpItem> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_noti_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        //todo:列表点击事件回调
        holder.itemView.setOnClickListener(v->{
            int absoluteAdapterPosition = holder.getAbsoluteAdapterPosition();
            NotiDumpItem item = getItem(absoluteAdapterPosition);
            bar.foo(item,absoluteAdapterPosition);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //渲染
        NotiDumpItem item=getItem(position);
        if(item!=null){
            String strDateFormat = "yyyy/MM/dd HH:mm";
            SimpleDateFormat spf=new SimpleDateFormat(strDateFormat);
            long when=item.getWhen();
            //todo:根据包名查app信息
            holder.txbSendTime.setText(spf.format(when));
            holder.txbAppName.setText(item.getPackageName());
            //todo:加载图片

            //todo:处理细节美化
            holder.txbNotiTitle.setText(item.getTitle()+"\n"+item.getSubText());
            holder.txbNotiContent.setText(item.getContent()+"\n"+item.getBigText());

        }

    }


    public static  class Callback extends DiffUtil.ItemCallback<NotiDumpItem>{

        @Override
        public boolean areItemsTheSame(@NonNull NotiDumpItem oldItem, @NonNull NotiDumpItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull NotiDumpItem oldItem, @NonNull NotiDumpItem newItem) {
            return oldItem.equals(newItem);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        View notiView;
        TextView txbAppName;
        TextView txbSendTime;
        ImageView imgNotiIcon;
        TextView txbNotiTitle;
        TextView txbNotiContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notiView=itemView;

            txbAppName=itemView.findViewById(R.id.txb_app_name);
            txbSendTime=itemView.findViewById(R.id.txb_send_time);
            imgNotiIcon=itemView.findViewById(R.id.img_noti_icon);
            txbNotiTitle=itemView.findViewById(R.id.txb_noti_title);
            txbNotiContent=itemView.findViewById(R.id.txb_noti_text);
        }
    }

    public interface Bar{
        void foo(NotiDumpItem item,int index);
    }
}
