package com.example.samparksuchiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.samparksuchiapplication.Adapter.ViewPhotoAdapter;
import com.example.samparksuchiapplication.Model.AlbumModel;
import com.example.samparksuchiapplication.Model.DataProccessor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayPhotoAlbumActivity extends AppCompatActivity implements ViewPhotoAdapter.ImageCallBack {
    GridView gridView;
    ArrayList<AlbumModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_photo_album_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Photos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        gridView = findViewById(R.id.simpleGridView);

        Intent mIntent = getIntent();
        int albumID = mIntent.getIntExtra("AlbumID", 0);
        try {
            getPhotos(albumID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getPhotos(int albumID) throws JSONException {
        DataProccessor dataProccessor = new DataProccessor(getApplicationContext());
        String response = dataProccessor.getStr("Response");
        JSONObject jsonObject = new JSONObject(response);
        JSONArray detailsarray = jsonObject.getJSONArray("details");

        for (int i = 0; i < detailsarray.length(); i++) {
            AlbumModel albumModel = new AlbumModel();
            JSONObject detail = detailsarray.getJSONObject(i);

            int detailsalbumId = detail.getInt("AlbumId");
            int detailId = detail.getInt("DetailId");
            if (albumID == detailsalbumId) {
                String photoPath = detail.getString("PhotoPath");
                albumModel.setPhoto(photoPath);
                albumModel.setAlbumID(detailsalbumId);
                albumModel.setDetailID(detailId);
                list.add(albumModel);
            }
            ViewPhotoAdapter adapter = new ViewPhotoAdapter(getApplicationContext(), R.layout.view_photo_adapter_list, list, DisplayPhotoAlbumActivity.this);
            gridView.setAdapter(adapter);
        }
    }

        @Override
        public boolean onOptionsItemSelected (MenuItem item)
        {
            if (item.getItemId() == android.R.id.home)
                onBackPressed();
            return super.onOptionsItemSelected(item);
        }

    @Override
    public void getImagePath(String photoPath) {
        Intent intent = new Intent(this,ViewPhotoActivity.class);
        intent.putExtra("ImagePath",photoPath);
        startActivity(intent);
    }
}
