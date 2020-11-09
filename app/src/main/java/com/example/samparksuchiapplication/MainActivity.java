package com.example.samparksuchiapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.animation.ArgbEvaluator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.samparksuchiapplication.Adapter.ContactDetailsAdapter;
import com.example.samparksuchiapplication.Adapter.GoodThoughtsAdapter;
import com.example.samparksuchiapplication.DataBase.DBHelper;
import com.example.samparksuchiapplication.Model.ContactDetailsModel;
import com.example.samparksuchiapplication.Model.DataProccessor;
import com.example.samparksuchiapplication.Model.GoodThoughtsModel;
import com.google.android.material.card.MaterialCardView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ContactDetailsAdapter.NumberCallBack, GoodThoughtsAdapter.ViewMoreCallBack {
    CardView cardView, cardViewFamily, cardViewSearch, cardViewgallery, cardViewPrevious,
            cardViewMonthly, cardViewBhajan;
    TextView tvShowate;
    RecyclerView recyclerView;
    List<ContactDetailsModel> list;
    List<ContactDetailsModel> list1;
    List<GoodThoughtsModel> goodThoughtsModelList;
    LinearLayout linearLayout;
    ContactDetailsModel model;
    GoodThoughtsModel goodThoughtsModel;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    ViewPager viewPager;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    Integer[] colors = null;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private String mJSONURLString = "http://btwebservices.biyanitechnologies.com/galaxybackupservices/galaxy1.svc/GetBtContactData";
    private String mGoodThoughtString = "http://btwebservices.biyanitechnologies.com/btfamilyapp/ProductService.svc/GetNotice";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.ViewPagerThoughts);
        sliderDotspanel = findViewById(R.id.SliderDots);
        progressBar = findViewById(R.id.pb);
        recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
      //  cardView = findViewById(R.id.cv);
        tvShowate = findViewById(R.id.tvShowDate);
        cardViewFamily = findViewById(R.id.cvFamily);
        cardViewSearch = findViewById(R.id.cvSearch);
        cardViewgallery = findViewById(R.id.cvgallery);
        cardViewPrevious = findViewById(R.id.cvPrevious);
        cardViewMonthly = findViewById(R.id.cvMonthly);
        cardViewBhajan = findViewById(R.id.cvGoodThoughts);
//        cardView.setBackgroundResource(R.drawable.card_edge);
        cardViewFamily.setBackgroundResource(R.drawable.card_edge1);
        cardViewSearch.setBackgroundResource(R.drawable.card_edge1);
        cardViewgallery.setBackgroundResource(R.drawable.card_edge1);
        cardViewPrevious.setBackgroundResource(R.drawable.card_edge1);
        cardViewMonthly.setBackgroundResource(R.drawable.card_edge1);
        cardViewBhajan.setBackgroundResource(R.drawable.card_edge1);
        linearLayout = findViewById(R.id.container_destacado);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(R.string.app_title);
//        getSupportActionBar().setSubtitle(R.string.app_name);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setHomeButtonEnabled(false);


        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("loading..");
        progressBar.setVisibility(View.VISIBLE);
        DBHelper helper = new DBHelper(getApplicationContext());

        getGoodThoughtsOnView();
        //getGoodThoughts();
        getTodaysDate();
        Log.e("SIZE",""+helper.getAllContacts().size());
        Log.e("BirthDates",""+helper.getAllBirthDates());

        // getContactDetails();
        if (helper.getAllContacts().size()>0){
            getList();
            progressBar.setVisibility(View.GONE);
        } else if (helper.getAllContacts().size()==0){
            setData();
            getList();
            progressBar.setVisibility(View.GONE);
        }

        cardViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchPeopleActivity.class);
                startActivity(intent);
            }
        });

        cardViewgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AlbumPhotoActivity.class);
                startActivity(intent);
            }
        });

        cardViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PDFActivity.class);
                startActivity(intent);
            }
        });

        cardViewMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MonthlyCalenderActivity.class);
                startActivity(intent);
            }
        });

        cardViewBhajan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, BhajanSangrahaActivity.class);
//                startActivity(intent);
            }
        });
    }

    private void getGoodThoughts() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, mGoodThoughtString,
                null,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        goodThoughtsModelList = new ArrayList<>();
                        try {
                            DataProccessor dataProccessor = new DataProccessor(getApplicationContext());
                            dataProccessor.setStr("ThoughtsResponse", jsonArray.toString());
                            //JSONArray jsonArray = new JSONArray(response);

                            GoodThoughtsModel goodThoughtsModel = new GoodThoughtsModel();
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            goodThoughtsModel.setTitle(jsonObject.getString("Title"));
                            goodThoughtsModel.setNoticeId(jsonObject.getInt("NoticeId"));
                            goodThoughtsModel.setDescription(jsonObject.getString("Decription"));
                            goodThoughtsModel.setAttachment(jsonObject.getString("Attachment"));
                            goodThoughtsModel.setViewMore("View");
                            goodThoughtsModelList.add(goodThoughtsModel);

                            goodThoughtsModel = new GoodThoughtsModel();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(1);
                            goodThoughtsModel.setTitle(jsonObject1.getString("Title"));
                            goodThoughtsModel.setNoticeId(jsonObject1.getInt("NoticeId"));
                            goodThoughtsModel.setDescription(jsonObject1.getString("Decription"));
                            goodThoughtsModel.setAttachment(jsonObject1.getString("Attachment"));
                            goodThoughtsModel.setViewMore("View");
                            goodThoughtsModelList.add(goodThoughtsModel);

                            goodThoughtsModel = new GoodThoughtsModel();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(2);
                            goodThoughtsModel.setTitle(jsonObject2.getString("Title"));
                            goodThoughtsModel.setNoticeId(jsonObject2.getInt("NoticeId"));
                            goodThoughtsModel.setDescription(jsonObject2.getString("Decription"));
                            goodThoughtsModel.setAttachment(jsonObject2.getString("Attachment"));
                            goodThoughtsModel.setViewMore("View");
                            goodThoughtsModelList.add(goodThoughtsModel);

                            goodThoughtsModel = new GoodThoughtsModel();
                            goodThoughtsModel.setViewMore("View More");
                            goodThoughtsModelList.add(goodThoughtsModel);
                            //getGoodThoughtsOnView();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            final GoodThoughtsAdapter adapter = new GoodThoughtsAdapter(goodThoughtsModelList,MainActivity.this,MainActivity.this);
                            viewPager.setAdapter(adapter);

                            dotscount = adapter.getCount();
                            dots = new ImageView[dotscount];

                            for (int i = 0; i < dotscount; i++) {

                                dots[i] = new ImageView(MainActivity.this);
                                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                params.setMargins(8, 0, 8, 0);

                                sliderDotspanel.addView(dots[i], params);
                            }

                            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

                            Integer[] colors_temp = {
                                    getResources().getColor(R.color.red),
                                    getResources().getColor(R.color.color3),
                                    getResources().getColor(R.color.coloradd),
                                    getResources().getColor(R.color.color4)
                            };

                            colors = colors_temp;

                            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                    if ((position < adapter.getCount() - 1) && position < (colors.length - 1)) {
                                        viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position] + 1));
                                    } else {
                                        viewPager.setBackgroundColor(colors[colors.length - 1]);
                                    }
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    for (int i = 0; i < dotscount; i++) {
                                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                                    }
                                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),""+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        // Do something when error occurred
                        Toast.makeText(getApplicationContext(),"please check your internet connection", Toast.LENGTH_SHORT).show();
                        Log.e("error",""+error.toString());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void getGoodThoughtsOnView() {
        try {
            DataProccessor dataProccessor = new DataProccessor(getApplicationContext());
            String response = dataProccessor.getStr("ThoughtsResponse");

            if (response.equals("DNF") || response.equalsIgnoreCase("null")
                    || response.equalsIgnoreCase(null)) {
                getGoodThoughts();
            } else {
                getPrefThoughts(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPrefThoughts(String response) {
//        List<ContactDetailsModel> tt = new ArrayList<>();
//        DBHelper dbHelper = new DBHelper(getApplicationContext());
//        tt = dbHelper.updateContactName();
//        Log.e("TT",""+tt);

        List<GoodThoughtsModel> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);

            GoodThoughtsModel goodThoughtsModel = new GoodThoughtsModel();
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            goodThoughtsModel.setTitle(jsonObject.getString("Title"));
            goodThoughtsModel.setNoticeId(jsonObject.getInt("NoticeId"));
            goodThoughtsModel.setDescription(jsonObject.getString("Decription"));
            goodThoughtsModel.setAttachment(jsonObject.getString("Attachment"));
            goodThoughtsModel.setViewMore("View");
            list.add(goodThoughtsModel);

            goodThoughtsModel = new GoodThoughtsModel();
            JSONObject jsonObject1 = jsonArray.getJSONObject(1);
            goodThoughtsModel.setTitle(jsonObject1.getString("Title"));
            goodThoughtsModel.setNoticeId(jsonObject1.getInt("NoticeId"));
            goodThoughtsModel.setDescription(jsonObject1.getString("Decription"));
            goodThoughtsModel.setAttachment(jsonObject1.getString("Attachment"));
            goodThoughtsModel.setViewMore("View");
            list.add(goodThoughtsModel);

            goodThoughtsModel = new GoodThoughtsModel();
            JSONObject jsonObject2 = jsonArray.getJSONObject(2);
            goodThoughtsModel.setTitle(jsonObject2.getString("Title"));
            goodThoughtsModel.setNoticeId(jsonObject2.getInt("NoticeId"));
            goodThoughtsModel.setDescription(jsonObject2.getString("Decription"));
            goodThoughtsModel.setAttachment(jsonObject2.getString("Attachment"));
            goodThoughtsModel.setViewMore("View");
            list.add(goodThoughtsModel);

            goodThoughtsModel = new GoodThoughtsModel();
            goodThoughtsModel.setViewMore("View More");
            list.add(goodThoughtsModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final GoodThoughtsAdapter adapter = new GoodThoughtsAdapter(list, getApplicationContext(), MainActivity.this);
        viewPager.setAdapter(adapter);

        dotscount = adapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        Integer[] colors_temp = {
                getResources().getColor(R.color.red),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.coloradd),
                getResources().getColor(R.color.color4)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if ((position < adapter.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position] + 1));
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void setData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, mJSONURLString,
                null,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(JSONArray response) {
                        list = new ArrayList<>();
                        try {
                            for (int i = 0; i <response.length(); i++) {
                                model = new ContactDetailsModel();
                                JSONObject student = response.getJSONObject(i);
                                String recId = student.getString("RecId");
                                String memberCode = student.getString("MemberCode");
                                String name = student.getString("FullName");
                                String occupation = student.getString("Occupation");
                                String address = student.getString("PostalAddress");
                                String city = student.getString("City");
                                String pincode = student.getString("Pincode");
                                String state = student.getString("State");
                                String emailId = student.getString("EmailId");
                                String birthday = student.getString("Birthday");
                                String weddding = student.getString("Weddding");
                                String photo = student.getString("UploadPhoto");
                                String pNo = student.getString("ContactNo1");
                                String pNo1 = student.getString("ContactNo2");

                                model.setRecordId(Integer.parseInt(recId));
                                model.setMemberCode(memberCode);
                                model.setContactName(name);
                                model.setCity(city);
                                model.setAddress(address);
                                model.setPinCode(pincode);
                                model.setState(state);
                                model.setEmailId(emailId);
                                model.setOccupation(occupation);
                                model.setPhoneNumber(pNo);
                                model.setPhonenNumber1(pNo1);
                                model.setPhotoUrl(photo);

//                                try {
//                                    long value = parseDate(birthday);
//                                    String BirthdayDate = getStringDateFromMilisecond(value);
//                                    Log.e("BirthdayDate",""+BirthdayDate);
//                                    model.setBirthDate(BirthdayDate);
//
//                                    long weddingValue = parseDate(weddding);
//                                    String WeddingDate = getStringDateFromMilisecond(weddingValue);
//                                    Log.e("WeddingDate",""+WeddingDate);
//                                    model.setAnniversaryDate(WeddingDate);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }

                                if (birthday.equals("null") || birthday.equalsIgnoreCase(null)) {

                                } else {
                                    try {
                                        String v = birthday.replaceAll("/", "");
                                        String v1 = v.replaceAll("Date", "");
                                        String v2 = v1.replace("(", "").replace(")", "");

                                        String longV = v2;
                                        long millisecond = Long.parseLong(longV);
                                        String dateString = String.valueOf(DateFormat.format("yyyy-MM-dd",
                                                new Date(millisecond)));
                                        String MonthDate = String.valueOf(DateFormat.format("MM-dd",
                                                new Date(millisecond)));
                                        model.setBirthDate(dateString);
                                        model.setBMonthDate(MonthDate);
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if (weddding.equals("null") || weddding.equalsIgnoreCase(null)) {
                                }

                                else {
                                    try {
                                        String date = weddding.replaceAll("/", "");
                                        String date1 = date.replaceAll("Date", "");
                                        String date2 = date1.replace("(", "").replace(")", "");

                                        String longV1 = date2;
                                        long millisecond1 = Long.parseLong(longV1);
                                        String dateString1 = String.valueOf(DateFormat.format("yyyy-MM-dd", new Date(millisecond1)));
                                        String monthDate = String.valueOf(DateFormat.format("MM-dd", new Date(millisecond1)));
                                        model.setAnniversaryDate(dateString1);
                                        model.setAMonthDate(monthDate);
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                }
                                list.add(model);
                            }

                            Log.e("List",""+list);
                            try{
                                DBHelper helper = new DBHelper(getApplicationContext());
                                helper.insertContacts(list);
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(),""+e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),""+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        // Do something when error occurred
                        Toast.makeText(getApplicationContext(),"please check your internet connection", Toast.LENGTH_SHORT).show();
                        Log.e("error",""+error.toString());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
        jsonArrayRequest.setRetryPolicy(new RetryPolicy() {
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

    private String getStringDateFromMilisecond(long value) {
        Date currentDate = new Date(value);
        java.text.DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(currentDate);
    }

    private static long parseDate(String birthday) {
        try {
            return Long.valueOf(birthday.replace("/Date(","").replace(")/",""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void refresh() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void getList() {
        progressDialog.setMessage("loading...");
        DBHelper helper = new DBHelper(getApplicationContext());
        list1 = new ArrayList<>();
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
        list1 = helper.getDate(Integer.parseInt(date1[0]),Integer.parseInt(date1[1]));
        Log.e("Getlist",""+list1);
        DataProccessor proccessor = new DataProccessor(getApplicationContext());
        proccessor.setIntDay("Day",Integer.parseInt(date1[0]));
        proccessor.setIntMonth("Month",Integer.parseInt(date1[1]));

        for(int i=0;i<list1.size();i++){
            if (list1.get(i).getAnniversaryDate().equalsIgnoreCase("null")){
                if (list1.get(i).getaDate()==Integer.parseInt(date1[0]) && list1.get(i).getAMonth()==Integer.parseInt(date1[1])){
                    list1.remove(i);
                    i--;
                } else {

                }
            }
        }

        ContactDetailsAdapter adapter = new ContactDetailsAdapter(list1,getApplicationContext(),MainActivity.this);
        recyclerView.setAdapter(adapter);
        progressDialog.cancel();
    }

    private void getTodaysDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        tvShowate.setText(formattedDate);
    }

    @Override
    public void getNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData( Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    @Override
    public void getRow(String contactName, String occupation, String city, String address, String emailId,
                       String phoneNumber, String phonenNumber1, String birthDate, String anniversaryDate,
                       int recordID,String memberCode,String url) {

        try {
            Intent intent = new Intent(getApplicationContext(), ShowIndivitualRecordAcitivity.class);
            intent.putExtra("RecordId", recordID);
            intent.putExtra("ContactName", contactName);
            intent.putExtra("MemberCode", memberCode);

            if (url != null) {
                intent.putExtra("Photo", url);
            }
            if (city != null) {
                intent.putExtra("City", city);
            }
            if (address != null) {
                intent.putExtra("Address", address);
            }
            if (occupation != null) {
                intent.putExtra("Occupation", occupation);
            }
            if (phoneNumber != null) {
                intent.putExtra("PhoneNumber", phoneNumber);
            }
            if (phonenNumber1 != null) {
                intent.putExtra("PhoneNumber1", phonenNumber1);
            }
            if (emailId != null) {
                intent.putExtra("EmailId", emailId);
            }
            if (birthDate != null) {
                intent.putExtra("BirthDate", birthDate);
            }
            if (anniversaryDate != null) {
                intent.putExtra("AnniversaryDate", anniversaryDate);
            }
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getViewMore() {
        Intent intent = new Intent(this,GoodThoughtsListActivity.class);
        startActivity(intent);
    }

    @Override
    public void getPagePosition(int noticeId) {
        Intent intent = new Intent(this, ViewGoodThoughtsActivity.class);
        intent.putExtra("noticeId",noticeId);
        startActivity(intent);
    }

    @Override
    public void getImageUrl(String attachment) {
        Intent intent=new Intent(this, ViewPhotoActivity.class);
        intent.putExtra("ImagePath",attachment);
        startActivity(intent);
    }
}
