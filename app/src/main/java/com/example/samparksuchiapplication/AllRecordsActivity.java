package com.example.samparksuchiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.samparksuchiapplication.Adapter.AllRecordsAdapter;
import com.example.samparksuchiapplication.Model.ContactDetailsModel;
import java.util.ArrayList;

public class AllRecordsActivity extends AppCompatActivity implements AllRecordsAdapter.ItemviewCallBack {
    ArrayList<ContactDetailsModel> myList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_records_activity);
        recyclerView = findViewById(R.id.rv_all_records);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        myList = (ArrayList<ContactDetailsModel>) getIntent().getSerializableExtra("list");
        try {
            getAllSearchData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllSearchData() {
            AllRecordsAdapter allRecordsAdapter = new AllRecordsAdapter(myList,getApplicationContext(),AllRecordsActivity.this);
            recyclerView.setAdapter(allRecordsAdapter);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
