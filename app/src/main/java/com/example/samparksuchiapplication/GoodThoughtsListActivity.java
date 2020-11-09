package com.example.samparksuchiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samparksuchiapplication.Adapter.GoodThoughtsListViewAdapter;
import com.example.samparksuchiapplication.Model.DataProccessor;
import com.example.samparksuchiapplication.Model.GoodThoughtsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GoodThoughtsListActivity extends AppCompatActivity implements GoodThoughtsListViewAdapter.NoticeAdapterCallBack {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.good_thoughts_list_activity);
        recyclerView = findViewById(R.id.rvThoughts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        
        getList();
    }

    private void getList() {
        DataProccessor dataProccessor = new DataProccessor(getApplicationContext());
        String res = dataProccessor.getStr("ThoughtsResponse");
        List<GoodThoughtsModel> list = new ArrayList<>();

        if (res.equalsIgnoreCase("") || res.equals(null)){
            Toast.makeText(getApplicationContext(),"loading....",Toast.LENGTH_SHORT).show();
        }
        try {
            JSONArray jsonArray = new JSONArray(res);
            for (int i=0;i<jsonArray.length();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                GoodThoughtsModel bean = new GoodThoughtsModel();

                bean.setNoticeId(jsonObject.getInt("NoticeId"));
                bean.setTitle(jsonObject.getString("Title"));
                bean.setDescription(jsonObject.getString("Decription"));
                bean.setAttachment(jsonObject.getString("Attachment"));
                //createNoticeBean.setExpiryDate(Long.parseLong(jsonObject.getString("ExpiryDate")));
                // createNoticeBean.setProductId(jsonObject.getInt("ProductId"));

                list.add(bean);
            }
            GoodThoughtsListViewAdapter adapter = new GoodThoughtsListViewAdapter(list,getApplicationContext(),GoodThoughtsListActivity.this);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getNoticeId(int noticeId) {
        // finish();
        Intent intent = new Intent(GoodThoughtsListActivity.this,ViewGoodThoughtsActivity.class);
        intent.putExtra("noticeId",noticeId);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
