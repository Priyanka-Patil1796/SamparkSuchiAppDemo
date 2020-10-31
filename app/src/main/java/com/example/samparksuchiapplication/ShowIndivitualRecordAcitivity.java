package com.example.samparksuchiapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import com.squareup.picasso.Picasso;

public class ShowIndivitualRecordAcitivity extends AppCompatActivity {
    TextView tvName,tvAddress,tvOccupation,tvContactNumber,tvEmailAddress,tvBirthDate,tvAnniversaryDate;
    ImageView imageView;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_indivitual_result_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        initUI();
        cardView.setBackgroundResource(R.drawable.card_edge1);

        Bundle bundle = getIntent().getExtras();
        String recID = bundle.getString("RecordId");
        String name = bundle.getString("ContactName");
        String memberCode = bundle.getString("MemberCode");
        String address = bundle.getString("Address");
        String occupation = bundle.getString("Occupation");
        String phoneNumber = bundle.getString("PhoneNumber");
        String phoneNumber1 = bundle.getString("PhoneNumber1");
        String emailId = bundle.getString("EmailId");
        String birthDate = bundle.getString("BirthDate");
        String anniversaryDate = bundle.getString("AnniversaryDate");
        String photo = bundle.getString("Photo");

        try {
            if (photo.equals("")){
            }else {
                Picasso.with(getApplicationContext()).setLoggingEnabled(true);
                Picasso.with(getApplicationContext()).load(photo).into(imageView);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),""+e.toString(),Toast.LENGTH_SHORT).show();
        }


        try {
            tvName.setText(name);

            if(address.equals("null")){
                tvAddress.setText("-");
            } else {
                tvAddress.setText(address);
            }
            if (occupation.equals("null")){
                tvOccupation.setText("-");
            } else {
                tvOccupation.setText(occupation);
            }

            if (phoneNumber!=null && phoneNumber1!=null){
                tvContactNumber.setText(phoneNumber + "\n" +phoneNumber1);
            }
            else if (phoneNumber!=null && phoneNumber1==null)
            {
                tvContactNumber.setText(phoneNumber);
            }
            else if (phoneNumber==null && phoneNumber1!=null)
            {
                tvContactNumber.setText(phoneNumber1);
            }else {
                tvContactNumber.setText("-");
            }
            if (emailId.equals("null")){
                tvEmailAddress.setText("-");
            } else {
                tvEmailAddress.setText(emailId);
            }
            if (birthDate.equals("null")){
                tvBirthDate.setText("-");
            } else {
                tvBirthDate.setText(birthDate);
            }
            if (anniversaryDate.equals("null")){
                tvAnniversaryDate.setText("-");
            }else {
                tvAnniversaryDate.setText(anniversaryDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        tvName = findViewById(R.id.tv_name);
        tvAddress = findViewById(R.id.tv_address);
        tvOccupation = findViewById(R.id.tv_occupation);
        tvContactNumber = findViewById(R.id.tv_contact_number);
        tvEmailAddress = findViewById(R.id.tv_email_address);
        tvBirthDate = findViewById(R.id.tv_birth_day);
        tvAnniversaryDate = findViewById(R.id.tv_anniversary_date);
        imageView = findViewById(R.id.iv_display_image);
        cardView = findViewById(R.id.cv_in);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
