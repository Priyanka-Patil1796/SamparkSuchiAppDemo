package com.example.samparksuchiapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
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
import com.example.samparksuchiapplication.DataBase.DBHelper;
import com.example.samparksuchiapplication.Model.ContactDetailsModel;
import com.example.samparksuchiapplication.Model.DataProccessor;
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

public class MainActivity extends AppCompatActivity implements ContactDetailsAdapter.NumberCallBack {
    MaterialCardView cardView, cardViewFamily, cardViewSearch, cardViewgallery, cardViewPrevious, cardViewMonthly, cardViewGood;
    TextView tvShowate;
    RecyclerView recyclerView;
    List<ContactDetailsModel> list;
    List<ContactDetailsModel> list1;
    LinearLayout linearLayout;
    ContactDetailsModel model;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    private String mJSONURLString = "http://btwebservices.biyanitechnologies.com/galaxybackupservices/galaxy1.svc/GetBtContactData";


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.pb);
        recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        cardView = findViewById(R.id.cv);
        tvShowate = findViewById(R.id.tvShowDate);
        cardViewFamily = findViewById(R.id.cvFamily);
        cardViewSearch = findViewById(R.id.cvSearch);
        cardViewgallery = findViewById(R.id.cvgallery);
        cardViewPrevious = findViewById(R.id.cvPrevious);
        cardViewMonthly = findViewById(R.id.cvMonthly);
        cardViewGood = findViewById(R.id.cvGoodThoughts);
        cardView.setBackgroundResource(R.drawable.card_edge);
        cardViewFamily.setBackgroundResource(R.drawable.card_edge1);
        cardViewSearch.setBackgroundResource(R.drawable.card_edge1);
        cardViewgallery.setBackgroundResource(R.drawable.card_edge1);
        cardViewPrevious.setBackgroundResource(R.drawable.card_edge1);
        cardViewMonthly.setBackgroundResource(R.drawable.card_edge1);
        cardViewGood.setBackgroundResource(R.drawable.card_edge1);
        linearLayout = findViewById(R.id.container_destacado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("loading..");
        progressBar.setVisibility(View.VISIBLE);
        DBHelper helper = new DBHelper(getApplicationContext());

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
                                        model.setBirthDate(dateString);
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
                                        model.setAnniversaryDate(dateString1);
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

        //        Log.v("SQLITE",""+list);
//        LayoutInflater linflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        for (int i=0;i<list1.size();i++)
//        {
//            View myView = linflater.inflate(R.layout.contact_details_list, null); //here item is the the layout you want to inflate
//            TextView name = myView.findViewById(R.id.tvName);
//            TextView date = myView.findViewById(R.id.tvdate);
//            TextView aDate = myView.findViewById(R.id.tvADate);
//            TextView number = myView.findViewById(R.id.tvNumber);
//            TextView number2 = myView.findViewById(R.id.tvPhoneNumber2);
//
//            try {
//                name.setText(list1.get(i).getContactName());
//                date.setText(list1.get(i).getBirthDate());
//                if (list1.get(i).getAnniversaryDate()!=null){
//                    aDate.setText(list1.get(i).getAnniversaryDate());
//                } else {
//                    aDate.setVisibility(View.GONE);
//                }
//                if(list1.get(i).getPhoneNumber()!=null){
//                    number.setText(list1.get(i).getPhoneNumber());
//                } else {
//                    number.setVisibility(View.GONE);
//                }
//                if(list1.get(i).getPhonenNumber1()!=null){
//                    number2.setText(list1.get(i).getPhonenNumber1());
//                } else {
//                    number2.setVisibility(View.GONE);
//                }
//                linearLayout.addView(myView);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
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
}
