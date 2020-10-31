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
import com.example.samparksuchiapplication.Model.ContactDetailsModel;
import java.util.ArrayList;

public class SearchPeopleResultActivity extends AppCompatActivity
{
    LinearLayout linearLayout;
    ArrayList<ContactDetailsModel> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_people_result);
        linearLayout = findViewById(R.id.ll);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SearchPeopleResult");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        myList = (ArrayList<ContactDetailsModel>) getIntent().getSerializableExtra("list");
        getContactResult();
    }

    private void getContactResult() {
        for (int i=0;i<myList.size();i++){
            ContactDetailsModel model = new ContactDetailsModel();

            model.setRecordId(myList.get(i).getRecordId());
            model.setContactName(myList.get(i).getContactName());
            model.setBirthDate(myList.get(i).getBirthDate());
            model.setPhoneNumber(myList.get(i).getPhoneNumber());
            model.setAnniversaryDate(myList.get(i).getAnniversaryDate());
            model.setCity(myList.get(i).getCity());
            model.setOccupation(myList.get(i).getOccupation());
        }

        LayoutInflater linflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for ( int i=0;i<myList.size();i++) {
            View myView = linflater.inflate(R.layout.search_people_result_list, null); //here item is the the layout you want to inflate
            TextView name = myView.findViewById(R.id.tvName);
            TextView city = myView.findViewById(R.id.tvcity);
            TextView number = myView.findViewById(R.id.tvNumber);
            TextView bDate = myView.findViewById(R.id.tvBDate);
            TextView aDate = myView.findViewById(R.id.tvADate);
            TextView occupation = myView.findViewById(R.id.tvoccupation);


            name.setText(myList.get(i).getContactName());
            bDate.setText(myList.get(i).getBirthDate());
            number.setText(myList.get(i).getPhoneNumber());
            city.setText(myList.get(i).getCity());
            aDate.setText(myList.get(i).getAnniversaryDate());
            occupation.setText(myList.get(i).getOccupation());

            if (myList.get(i).getAnniversaryDate().equals(null) || myList.get(i).getAnniversaryDate().equalsIgnoreCase("null")){
                aDate.setText("-");
            }

            linearLayout.addView(myView);

            final int finalI = i;
            myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(getApplicationContext(),ShowIndivitualRecordAcitivity.class);
                        intent.putExtra("RecordId",myList.get(finalI).getRecordId());
                        intent.putExtra("ContactName",myList.get(finalI).getContactName());
                        intent.putExtra("MemberCode",myList.get(finalI).getMemberCode());

                        if (myList.get(finalI).getPhotoUrl()!=null){
                            intent.putExtra("Photo",myList.get(finalI).getPhotoUrl());
                        }

                        if (myList.get(finalI).getAddress()!=null){
                            intent.putExtra("Address",myList.get(finalI).getAddress());
                        }
                        if (myList.get(finalI).getOccupation()!=null){
                            intent.putExtra("Occupation",myList.get(finalI).getOccupation());
                        }
                        if (myList.get(finalI).getPhoneNumber()!=null){
                            intent.putExtra("PhoneNumber",myList.get(finalI).getPhoneNumber());
                        }
                        if (myList.get(finalI).getPhonenNumber1()!=null){
                            intent.putExtra("PhoneNumber1",myList.get(finalI).getPhonenNumber1());
                        }
                        if (myList.get(finalI).getEmailId()!=null){
                            intent.putExtra("EmailId",myList.get(finalI).getEmailId());
                        }
                        if (myList.get(finalI).getBirthDate()!=null){
                            intent.putExtra("BirthDate",myList.get(finalI).getBirthDate());
                        }
                        if (myList.get(finalI).getAnniversaryDate()!=null){
                            intent.putExtra("AnniversaryDate",myList.get(finalI).getAnniversaryDate());
                        }
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
}
