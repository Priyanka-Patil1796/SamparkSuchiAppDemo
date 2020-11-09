package com.example.samparksuchiapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samparksuchiapplication.Adapter.AllRecordsAdapter;
import com.example.samparksuchiapplication.Model.ContactDetailsModel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchPeopleResultActivity extends AppCompatActivity implements AllRecordsAdapter.ItemviewCallBack
{
    LinearLayout linearLayout;
    ArrayList<ContactDetailsModel> myList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_people_result);
        linearLayout = findViewById(R.id.ll);
        recyclerView = findViewById(R.id.rv_all_records);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        myList = (ArrayList<ContactDetailsModel>) getIntent().getSerializableExtra("list");
        try {
            //getContactResult(myList);
            AllRecordsAdapter allRecordsAdapter = new AllRecordsAdapter(myList,getApplicationContext(),SearchPeopleResultActivity.this);
            recyclerView.setAdapter(allRecordsAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getContactResult(ArrayList<ContactDetailsModel> myList) throws ParseException {

        for (int i = 0; i< this.myList.size(); i++){
            ContactDetailsModel model = new ContactDetailsModel();

            model.setRecordId(this.myList.get(i).getRecordId());
            model.setContactName(this.myList.get(i).getContactName());
            model.setBirthDate(this.myList.get(i).getBirthDate());
            model.setPhoneNumber(this.myList.get(i).getPhoneNumber());
            model.setAnniversaryDate(this.myList.get(i).getAnniversaryDate());
            model.setCity(this.myList.get(i).getCity());
            model.setOccupation(this.myList.get(i).getOccupation());
        }

        try {
            LayoutInflater linflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (int i = 0; i < this.myList.size(); i++) {
                View myView = linflater.inflate(R.layout.search_people_result_list, null); //here item is the the layout you want to inflate
                TextView name = myView.findViewById(R.id.tvName);
                TextView city = myView.findViewById(R.id.tvcity);
                TextView number = myView.findViewById(R.id.tvNumber);
                TextView bDate = myView.findViewById(R.id.tvBDate);
                TextView aDate = myView.findViewById(R.id.tvADate);
                TextView occupation = myView.findViewById(R.id.tvoccupation);

                name.setText(this.myList.get(i).getContactName());

                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                String inputDateStr = this.myList.get(i).getBirthDate();
                Date date = inputFormat.parse(inputDateStr);
                String BirthdayDate = outputFormat.format(date);

//            bDate.setText(myList.get(i).getBirthDate());
                bDate.setText(BirthdayDate);

                DateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat outputFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                String inputDateStr1 = this.myList.get(i).getAnniversaryDate();
                Date dateA = inputFormat1.parse(inputDateStr1);
                String AnniversaryDate = outputFormat1.format(dateA);

//            aDate.setText(myList.get(i).getAnniversaryDate());
                aDate.setText(AnniversaryDate);

                number.setText(this.myList.get(i).getPhoneNumber());
                city.setText(this.myList.get(i).getCity());
                occupation.setText(this.myList.get(i).getOccupation());

                if (this.myList.get(i).getAnniversaryDate().equals(null) || this.myList.get(i).getAnniversaryDate().equalsIgnoreCase("null")) {
                    aDate.setText("-");
                }

                linearLayout.addView(myView);

                final int finalI = i;
                myView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent(getApplicationContext(), ShowIndivitualRecordAcitivity.class);
                            intent.putExtra("RecordId", SearchPeopleResultActivity.this.myList.get(finalI).getRecordId());
                            intent.putExtra("ContactName", SearchPeopleResultActivity.this.myList.get(finalI).getContactName());
                            intent.putExtra("MemberCode", SearchPeopleResultActivity.this.myList.get(finalI).getMemberCode());

                            if (SearchPeopleResultActivity.this.myList.get(finalI).getPhotoUrl() != null) {
                                intent.putExtra("Photo", SearchPeopleResultActivity.this.myList.get(finalI).getPhotoUrl());
                            }

                            if (SearchPeopleResultActivity.this.myList.get(finalI).getAddress() != null) {
                                intent.putExtra("Address", SearchPeopleResultActivity.this.myList.get(finalI).getAddress());
                            }
                            if (SearchPeopleResultActivity.this.myList.get(finalI).getOccupation() != null) {
                                intent.putExtra("Occupation", SearchPeopleResultActivity.this.myList.get(finalI).getOccupation());
                            }
                            if (SearchPeopleResultActivity.this.myList.get(finalI).getPhoneNumber() != null) {
                                intent.putExtra("PhoneNumber", SearchPeopleResultActivity.this.myList.get(finalI).getPhoneNumber());
                            }
                            if (SearchPeopleResultActivity.this.myList.get(finalI).getPhonenNumber1() != null) {
                                intent.putExtra("PhoneNumber1", SearchPeopleResultActivity.this.myList.get(finalI).getPhonenNumber1());
                            }
                            if (SearchPeopleResultActivity.this.myList.get(finalI).getEmailId() != null) {
                                intent.putExtra("EmailId", SearchPeopleResultActivity.this.myList.get(finalI).getEmailId());
                            }
                            if (SearchPeopleResultActivity.this.myList.get(finalI).getBirthDate() != null) {
                                intent.putExtra("BirthDate", SearchPeopleResultActivity.this.myList.get(finalI).getBirthDate());
                            }
                            if (SearchPeopleResultActivity.this.myList.get(finalI).getAnniversaryDate() != null) {
                                intent.putExtra("AnniversaryDate", SearchPeopleResultActivity.this.myList.get(finalI).getAnniversaryDate());
                            }
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getCallBack(String contactName, String occupation, String city, String address, String emailId, String phoneNumber, String phonenNumber1, String birthDate, String anniversaryDate, int recordId, String memberCode, String photoUrl) {
        try {
            Intent intent = new Intent(getApplicationContext(), ShowIndivitualRecordAcitivity.class);
            intent.putExtra("RecordId", recordId);
            intent.putExtra("ContactName", contactName);
            intent.putExtra("MemberCode", memberCode);

            if (photoUrl != null) {
                intent.putExtra("Photo", photoUrl);
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
}
