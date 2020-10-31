package com.example.samparksuchiapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.samparksuchiapplication.Model.AlbumModel;
import com.example.samparksuchiapplication.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ViewPhotoAdapter extends ArrayAdapter {
    ArrayList<AlbumModel> albumList = new ArrayList<>();
    Context context;
    ImageCallBack imageCallBack;

    public ViewPhotoAdapter(Context context, int textViewResourceId, ArrayList objects, ImageCallBack imageCallBack) {
        super(context, textViewResourceId, objects);
        albumList = objects;
        this.context = context;
        this.imageCallBack = imageCallBack;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.view_photo_adapter_list, null);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        Picasso.with(context).load(albumList.get(position).getPhoto()).placeholder(R.drawable.biyani).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageCallBack.getImagePath(albumList.get(position).getPhoto());
            }
        });

        return v;
    }

    public interface  ImageCallBack {
        void getImagePath(String photoPath);
    }
}
