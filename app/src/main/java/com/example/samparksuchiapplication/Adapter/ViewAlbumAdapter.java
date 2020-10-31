package com.example.samparksuchiapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samparksuchiapplication.Model.AlbumModel;
import com.example.samparksuchiapplication.R;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewAlbumAdapter extends ArrayAdapter {
    ArrayList<AlbumModel> albumList = new ArrayList<>();
    Context context;
    AlbumNameCallBack albumNameCallBack;

    public ViewAlbumAdapter(Context context, int textViewResourceId, ArrayList objects, AlbumNameCallBack albumNameCallBack) {
        super(context, textViewResourceId, objects);
        albumList = objects;
        this.context = context;
        this.albumNameCallBack = albumNameCallBack;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.view_album_adapter_list, null);
        TextView textView = (TextView) v.findViewById(R.id.tvAlbumName);
        MaterialCardView cardView = (MaterialCardView) v.findViewById(R.id.cv_album);
        textView.setText(albumList.get(position).getAlbum());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                albumNameCallBack.getAlbumID(albumList.get(position).getAlbumID());
            }
        });

        return v;
    }

//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
////        View v = convertView;
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        convertView = inflater.inflate(R.layout.view_album_adapter_list, null);
//        TextView textView = (TextView) convertView.findViewById(R.id.tvAlbumName);
//        textView.setText(albumList.get(position).getAlbum());
//        MaterialCardView cardView = (MaterialCardView) convertView.findViewById(R.id.cv_album);
//
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                albumNameCallBack.getAlbumID(albumList.get(position).getAlbumID());
//            }
//        });
//        return convertView;
//    }

    public interface  AlbumNameCallBack {
        void getAlbumID(int albumID);
    }
}
