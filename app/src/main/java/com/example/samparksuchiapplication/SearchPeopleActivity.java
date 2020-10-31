package com.example.samparksuchiapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.samparksuchiapplication.DataBase.DBHelper;
import com.example.samparksuchiapplication.Model.ContactDetailsModel;
import java.util.ArrayList;
import java.util.Calendar;

public class SearchPeopleActivity  extends AppCompatActivity {
    EditText etName,etOccupation,etCity,etBirthDate,etToBirthDate,etAnniversaryDate,etToAnniDate;
    Button btnSearch;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int day,month,year;
    private Calendar mcalendar;
    ArrayList<ContactDetailsModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_people_activity);
        etName = findViewById(R.id.et_name);
        etOccupation = findViewById(R.id.et_occupation);
        etCity = findViewById(R.id.et_city);
        etBirthDate = findViewById(R.id.et_birthdate);
        etToBirthDate = findViewById(R.id.et_tobirthdate);
        etAnniversaryDate = findViewById(R.id.et_anniversarydate);
        etToAnniDate = findViewById(R.id.et_toanniversarydate);
        btnSearch = findViewById(R.id.btn_search);
        list= new ArrayList<>();
        mcalendar = Calendar.getInstance();
        day=mcalendar.get(Calendar.DAY_OF_MONTH);
        year=mcalendar.get(Calendar.YEAR);
        month=mcalendar.get(Calendar.MONTH);

        etName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SearchPeople");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        etBirthDate.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                DatePickerDialog dateDlg = new DatePickerDialog(SearchPeopleActivity.this,
                        new DatePickerDialog.OnDateSetListener()
                {
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth)
                    {
                        CharSequence strDate = null;
                        Time chosenDate = new Time();
                        chosenDate.set(dayOfMonth, monthOfYear, year);
                        long dtDob = chosenDate.toMillis(true);

                        strDate = DateFormat.format("yyyy-MM-dd", dtDob);

                        etBirthDate.setText(strDate);
                    }}, year,month,day);
                dateDlg.show();
                return false;
            }
        });

        etToBirthDate.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                DatePickerDialog dateDlg = new DatePickerDialog(SearchPeopleActivity.this,
                        new DatePickerDialog.OnDateSetListener()
                        {

                            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth)
                            {
                                CharSequence strDate = null;
                                Time chosenDate = new Time();
                                chosenDate.set(dayOfMonth, monthOfYear, year);
                                long dtDob = chosenDate.toMillis(true);

                                strDate = DateFormat.format("yyyy-MM-dd", dtDob);

                                etToBirthDate.setText(strDate);
                            }}, year,month,day);

                dateDlg.show();
                return false;
            }
        });

        etAnniversaryDate.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                DatePickerDialog dateDlg = new DatePickerDialog(SearchPeopleActivity.this,
                        new DatePickerDialog.OnDateSetListener()
                        {

                            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth)
                            {
                                CharSequence strDate = null;
                                Time chosenDate = new Time();
                                chosenDate.set(dayOfMonth, monthOfYear, year);
                                long dtDob = chosenDate.toMillis(true);

                                strDate = DateFormat.format("yyyy-MM-dd", dtDob);

                                etAnniversaryDate.setText(strDate);
                            }}, year,month,day);

                dateDlg.show();
                return false;
            }
        });

        etToAnniDate.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                DatePickerDialog dateDlg = new DatePickerDialog(SearchPeopleActivity.this,
                        new DatePickerDialog.OnDateSetListener()
                        {

                            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth)
                            {
                                CharSequence strDate = null;
                                Time chosenDate = new Time();
                                chosenDate.set(dayOfMonth, monthOfYear, year);
                                long dtDob = chosenDate.toMillis(true);

                                strDate = DateFormat.format("yyyy-MM-dd", dtDob);

                                etToAnniDate.setText(strDate);
                            }}, year,month,day);

                dateDlg.show();
                return false;
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
//                if (checkValidation())
                {
//                   DBHelper helper = new DBHelper(getApplicationContext());
//                   list =  helper.getSearchResult(etName.getText().toString(),
//                           etOccupation.getText().toString(),etCity.getText().toString(),
//                           etBirthDate.getText().toString(),etToBirthDate.getText().toString(),
//                           etAnniversaryDate.getText().toString(),etToAnniDate.getText().toString());
//                       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
//                       intent.putExtra("list", list);
//                       startActivity(intent);
                }
            }
        });
    }

    private boolean checkValidation() {
        if (etName.getText().toString().isEmpty()) {
            etName.setError("enter name");
            return false;
        } else if ( etOccupation.getText().toString().isEmpty() ) {
            etOccupation.setError("enter occupation");
            return false;
        } else if ( etCity.getText().toString().isEmpty()) {
            etCity.setError("enter city");
            return false;
        }
//        else if (etBirthDate.getText().toString().isEmpty())
//        {
//           Toast.makeText(getApplicationContext(),"please enter valid date", Toast.LENGTH_SHORT).show();
//            return false;
//        } else  if ( etToBirthDate.getText().toString().isEmpty())
//        {
//            Toast.makeText(getApplicationContext(),"please enter valid date", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        else  if (etToAnniDate.getText().toString().isEmpty())
//        {
//            .setError("enter name");
//            return false;
//        } else  if (  etToAnniDate.getText().toString().isEmpty())
//        {
//            etCity.setError("enter name");
//            return false;
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
