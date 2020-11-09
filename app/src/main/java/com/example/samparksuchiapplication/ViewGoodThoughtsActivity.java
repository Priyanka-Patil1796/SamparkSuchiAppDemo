package com.example.samparksuchiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.samparksuchiapplication.Model.DataProccessor;
import com.example.samparksuchiapplication.Model.GoodThoughtsModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewGoodThoughtsActivity extends AppCompatActivity {

    int noticeId;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    TextView title, des, date;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_good_thoughts_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView = findViewById(R.id.iv_notice);
        title = findViewById(R.id.tvsetTitle);
        des = findViewById(R.id.tvsetDes);
        try {
            Intent intent = getIntent();
            noticeId = intent.getIntExtra("noticeId", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataProccessor dataProccessor = new DataProccessor(getApplicationContext());
        String res = dataProccessor.getStr("ThoughtsResponse");

        List<GoodThoughtsModel> noticeBeanList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(res);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getInt("NoticeId") == noticeId) {
                    GoodThoughtsModel createNoticeBean = new GoodThoughtsModel();
                    createNoticeBean.setTitle(jsonObject.getString("Title"));
                    createNoticeBean.setDescription(jsonObject.getString("Decription"));
                    createNoticeBean.setAttachment(jsonObject.getString("Attachment"));
                    String date = jsonObject.getString("ExpiryDate");
                    String v = date.replaceAll("/", "");
                    String v1 = v.replaceAll("Date", "");
                    String v2 = v1.replace("(", "").replace(")", "");

                    String longV = v2;
                    long millisecond = Long.parseLong(longV);
                    String dateString = getDate(millisecond, "dd/MM/yyyy");
                    createNoticeBean.setDate(dateString);
                    noticeBeanList.add(createNoticeBean);
                    getNotice(noticeBeanList);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getNotice(final List<GoodThoughtsModel> noticeBeanList) {
        for (int position=0;position<noticeBeanList.size();position++){
            if (noticeBeanList.get(position).getAttachment().equalsIgnoreCase("")){
                imageView.setVisibility(View.GONE);
            }
            else
            {
                Picasso.get().load(noticeBeanList.get(position).getAttachment()).placeholder(R.drawable.biyani).into(imageView);
            }
            title.setText(noticeBeanList.get(position).getTitle());
            des.setText(noticeBeanList.get(position).getDescription());

            final int finalPosition = position;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(ViewGoodThoughtsActivity.this, ViewPhotoActivity.class);
                    intent.putExtra("ImagePath",noticeBeanList.get(finalPosition).getAttachment());
                    startActivity(intent);
                }
            });
        }
    }

    private  static String getDate(long milliSeconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
