package com.example.samparksuchiapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samparksuchiapplication.Model.GoodThoughtsModel;
import com.example.samparksuchiapplication.R;

import java.util.List;

public class GoodThoughtsListViewAdapter extends RecyclerView.Adapter<GoodThoughtsListViewAdapter.ViewHolder> {
    List<GoodThoughtsModel> list;
    Context context;
    NoticeAdapterCallBack noticeAdapterCallBack;


    public GoodThoughtsListViewAdapter(List<GoodThoughtsModel> noticeBeanList, Context context, NoticeAdapterCallBack callBack){
        this.list = noticeBeanList;
        this.context = context;
        this.noticeAdapterCallBack = callBack;
    }

    @NonNull
    @Override
    public GoodThoughtsListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notice_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodThoughtsListViewAdapter.ViewHolder holder, int position) {
        GoodThoughtsModel bean = list.get(position);

        holder.title.setText(bean.getTitle());
        holder.description.setText(bean.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title,description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvnoticetitle);
            description = itemView.findViewById(R.id.tvnoticedescription);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
                noticeAdapterCallBack.getNoticeId(list.get(position).getNoticeId());
        }
    }
    public interface NoticeAdapterCallBack{
        void getNoticeId(int noticeId);
    }
}
