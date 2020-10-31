package com.example.samparksuchiapplication;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.samparksuchiapplication.Adapter.CalenderAdapter;
import com.example.samparksuchiapplication.Adapter.SwipeAdapter;
import com.example.samparksuchiapplication.DataBase.DBHelper;
import com.example.samparksuchiapplication.Model.ContactDetailsModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MonthlyCalenderActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    DBHelper helper;
    List<ContactDetailsModel> myList;
    ViewPager viewPager;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    Integer[] colors = null;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    CalenderAdapter adapter;
    ImageView leftArrow,rightArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthly_calender_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MonthlyCalender");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        leftArrow = findViewById(R.id.iv_left_arrow);
        rightArrow = findViewById(R.id.iv_right_arrow);
        linearLayout = findViewById(R.id.ll_monthly_calender);
        viewPager = findViewById(R.id.ViewPagerUser);
        sliderDotspanel = findViewById(R.id.SliderDots);
        helper = new DBHelper(getApplicationContext());
        myList = new ArrayList<>();

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tab = viewPager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    viewPager.setCurrentItem(tab);
                } else if (tab == 0) {
                    viewPager.setCurrentItem(tab);
                }
            }
        });


        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tab = viewPager.getCurrentItem();
                tab++;
                viewPager.setCurrentItem(tab);
            }
        });

        viewPager.setOffscreenPageLimit(1);
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);
        viewPager.setCurrentItem(0);

       // getMonthlyCalender();
    }

    private void getMonthlyCalender() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        String date = formattedDate;
        String[] date1 = date.split("/");
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(Integer.parseInt(date1[2]), Integer.parseInt(date1[1]), Integer.parseInt(date1[0]));
        Log.e("Day",""+Integer.parseInt(date1[0]));
        Log.e("Month",""+Integer.parseInt(date1[1]));
        myList = helper.getMonth(Integer.parseInt(date1[1]));

        for(int i=0;i<myList.size();i++)
        {
            if (myList.get(i).getAnniversaryDate().equalsIgnoreCase("null")){
                if (myList.get(i).getAMonth()==Integer.parseInt(date1[1])){
                    myList.remove(i);
                    i--;
                } else {

                }
            }
        }

//        try {
//            adapter = new CalenderAdapter(myList,MonthlyCalenderActivity.this);
//            viewPager.setAdapter(adapter);
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(),""+e.toString(),Toast.LENGTH_SHORT).show();
//        }
//
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
       //  getList(myList);
    }

    private void getList(List<ContactDetailsModel> myList) {
        LayoutInflater linflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for ( int i=0;i<myList.size();i++)
        {
            View myView = linflater.inflate(R.layout.search_people_result_list, null); //here item is the the layout you want to inflate
            TextView name = myView.findViewById(R.id.tvName);
            TextView city = myView.findViewById(R.id.tvcity);
            TextView number = myView.findViewById(R.id.tvNumber);
            TextView bDate = myView.findViewById(R.id.tvBDate);
            TextView aDate = myView.findViewById(R.id.tvADate);
            TextView occupation = myView.findViewById(R.id.tvoccupation);

            name.setText(myList.get(i).getContactName());
            bDate.setText(myList.get(i).getBirthDate());
//            number.setText(myList.get(i).getPhoneNumber());
            city.setText(myList.get(i).getCity());
            aDate.setText(myList.get(i).getAnniversaryDate());
            occupation.setText(myList.get(i).getOccupation());

            if (myList.get(i).getAnniversaryDate().equals(null) || myList.get(i).getAnniversaryDate().equalsIgnoreCase("null")){
                aDate.setText("-");
            }
            if (myList.get(i).getPhoneNumber()!=null && myList.get(i).getPhonenNumber1()!=null)
            {
                number.setText(myList.get(i).getPhoneNumber()+"\n"+myList.get(i).getPhonenNumber1());
            }
            else if (myList.get(i).getPhoneNumber()!=null && myList.get(i).getPhonenNumber1()==null)
            {
                number.setText(myList.get(i).getPhoneNumber());
            }
            else if (myList.get(i).getPhoneNumber()==null && myList.get(i).getPhonenNumber1()!=null)
            {
                number.setText(myList.get(i).getPhonenNumber1());
            }
            linearLayout.addView(myView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
