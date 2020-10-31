package com.example.samparksuchiapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.samparksuchiapplication.Adapter.ViewAlbumAdapter;
import com.example.samparksuchiapplication.Adapter.ViewPhotoAdapter;
import com.example.samparksuchiapplication.Model.AlbumModel;
import com.example.samparksuchiapplication.Model.DataProccessor;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlbumPhotoActivity extends AppCompatActivity  implements ViewAlbumAdapter.AlbumNameCallBack{
   // List<AlbumModel> list;
    LinearLayout linearLayout;
    GridView gridView;
    ArrayList<AlbumModel> list = new ArrayList<>();
    private String mJSONURLString = "http://btwebservices.biyanitechnologies.com/galaxybackupservices/galaxy1.svc/GetBtAlbum";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_photo_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Albums");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        gridView = findViewById(R.id.albumGridView);
//        linearLayout = findViewById(R.id.ll_photo);
    //    list = new ArrayList<>();
        getAlbums();
    }

    private void getAlbums() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, mJSONURLString, null, new Response.Listener<JSONObject>()
        {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            DataProccessor dataProccessor = new DataProccessor(getApplicationContext());
                            dataProccessor.setStr("Response", String.valueOf(response));

                            JSONArray albumarray = response.getJSONArray("albums");
                            for(int i=0;i<albumarray.length();i++){
                                AlbumModel albumModel = new AlbumModel();
                                JSONObject album = albumarray.getJSONObject(i);

                                final String albumId = album.getString("AlbumId");
                                String albumName = album.getString("AlbumNamel");
                                albumModel.setAlbumID(Integer.parseInt(albumId));
                                albumModel.setAlbum(albumName);
                                list.add(albumModel);
                               // getAlbumName(list);
                            }

                            ViewAlbumAdapter adapter = new ViewAlbumAdapter(AlbumPhotoActivity.this,R.layout.view_album_adapter_list, list, AlbumPhotoActivity.this);
                            gridView.setAdapter(adapter);
                        }catch (JSONException e){
                            Toast.makeText(getApplicationContext(),""+e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(getApplicationContext(),"please check your internet connection",Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

    private void getAlbumName(final List<AlbumModel> list) {
        LayoutInflater linflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for ( int i=0;i<list.size();i++) {
            View myView = linflater.inflate(R.layout.album_list, null); //here item is the the layout you want to inflate
            TextView name = myView.findViewById(R.id.tv_album);
            MaterialCardView cardView = myView.findViewById(R.id.cv_album);

            name.setText(list.get(i).getAlbum());
            linearLayout.addView(myView);

            final int finalI = i;
            myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AlbumPhotoActivity.this, DisplayPhotoAlbumActivity.class);
                    intent.putExtra("AlbumID",list.get(finalI).getAlbumID());
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getAlbumID(int albumID) {
        Intent intent = new Intent(AlbumPhotoActivity.this, DisplayPhotoAlbumActivity.class);
        intent.putExtra("AlbumID",albumID);
        startActivity(intent);
    }
}
