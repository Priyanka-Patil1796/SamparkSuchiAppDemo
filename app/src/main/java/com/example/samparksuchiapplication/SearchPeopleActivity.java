package com.example.samparksuchiapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.samparksuchiapplication.DataBase.DBHelper;
import com.example.samparksuchiapplication.Model.ContactDetailsModel;
import com.example.samparksuchiapplication.Model.DataProccessor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SearchPeopleActivity  extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText etName,etOccupation,etCity,etToBirthDate,etFromBirthDate,
            etToAnniversaryDate,etFromAnniDate,etToBDateDummy,etFromBDateDummy,etToAnniversaryDatedummy,etFromAnniDatedummy;
    Button btnFromDate,btnToDate,btnFromAnnidate,btnToAnniDate;
    Button btnSearch;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int day,month,year;
    private Calendar mcalendar;
    ArrayList<ContactDetailsModel> list;
    ArrayList<ContactDetailsModel> modelList;
    ProgressBar progressBar;
    ArrayList<ContactDetailsModel> allList;
    TextView tvResetBDate,tvResetADate;
    private SimpleDateFormat dateFormatter;
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar1 = Calendar.getInstance();
    CharSequence strFromBDate = null;
    CharSequence strToBDate = null;
    CharSequence strFromADate = null;
    CharSequence strToADate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_people_activity);
        etToBDateDummy = findViewById(R.id.et_tobirthdatedummy);
        etFromBDateDummy = findViewById(R.id.et_frombirthdatedummy);
        etToAnniversaryDatedummy = findViewById(R.id.et_toanniversarydatedummy);
        etFromAnniDatedummy = findViewById(R.id.et_fromanniversarydatedummy);
        progressBar = findViewById(R.id.pb_dialogue);
        btnFromDate = findViewById(R.id.btnFromBDate);
        btnToDate = findViewById(R.id.btnToBDate);
        btnFromAnnidate = findViewById(R.id.btnFromAnniDate);
        btnToAnniDate = findViewById(R.id.btnToAnniDate);
        etName = findViewById(R.id.et_name);
        etOccupation = findViewById(R.id.et_occupation);
        etCity = findViewById(R.id.et_city);
        etToBirthDate = findViewById(R.id.et_tobirthdate);
        etFromBirthDate = findViewById(R.id.et_frombirthdate);
        etToAnniversaryDate = findViewById(R.id.et_toanniversarydate);
        etFromAnniDate = findViewById(R.id.et_fromanniversarydate);
        btnSearch = findViewById(R.id.btn_search);
        tvResetBDate = findViewById(R.id.tvResetBirthDate);
        tvResetADate = findViewById(R.id.tvResetAnniDate);
        list= new ArrayList<>();
        allList = new ArrayList<>();
        mcalendar = Calendar.getInstance();
        day=mcalendar.get(Calendar.DAY_OF_MONTH);
        year=mcalendar.get(Calendar.YEAR);
        month=mcalendar.get(Calendar.MONTH);
        etName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


        tvResetBDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etToBDateDummy.getText().toString().isEmpty() && etFromBDateDummy.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"please enter value",Toast.LENGTH_SHORT).show();
                } else {
                    if (!etToBDateDummy.getText().toString().isEmpty() && !etFromBDateDummy.getText().toString().isEmpty()){
                        etToBDateDummy.getText().clear();
                        etFromBDateDummy.getText().clear();
                        etToBirthDate.getText().clear();
                        etFromBirthDate.getText().clear();
                    }
                    else if (!etToBDateDummy.getText().toString().isEmpty() && etFromBDateDummy.getText().toString().isEmpty()){
                        etToBDateDummy.getText().clear();
                        etToBirthDate.getText().clear();
                    }
                    else if (etToBDateDummy.getText().toString().isEmpty() && !etFromBDateDummy.getText().toString().isEmpty()){
                        etFromBDateDummy.getText().clear();
                        etFromBirthDate.getText().clear();
                    }
                }
            }
        });

        tvResetADate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etToAnniversaryDatedummy.getText().toString().isEmpty() && etFromAnniDatedummy.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"please enter value",Toast.LENGTH_SHORT).show();
                } else {
                    if (!etToAnniversaryDatedummy.getText().toString().isEmpty() && !etFromAnniDatedummy.getText().toString().isEmpty()){
                        etToAnniversaryDatedummy.getText().clear();
                        etFromAnniDatedummy.getText().clear();
                        etToAnniversaryDate.getText().clear();
                        etFromAnniDate.getText().clear();
                    }
                    else if (!etToAnniversaryDatedummy.getText().toString().isEmpty() && etFromAnniDatedummy.getText().toString().isEmpty()){
                        etToAnniversaryDatedummy.getText().clear();
                        etToAnniversaryDate.getText().clear();
                    }
                    else if (etToAnniversaryDatedummy.getText().toString().isEmpty() && !etFromAnniDatedummy.getText().toString().isEmpty()){
                        etFromAnniDatedummy.getText().clear();
                        etFromAnniDate.getText().clear();
                    }
                }
            }
        });


//        etToBirthDate.setOnTouchListener(new View.OnTouchListener(){
//            public boolean onTouch(View v, MotionEvent event) {
//                DataProccessor dataProccessor = new DataProccessor(getApplicationContext());
//                dataProccessor.setInt("KEY1",0);
//                showDatePickerDialoue();
//                return false;
//            }
//        });

        btnFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataProccessor dataProccessor = new DataProccessor(getApplicationContext());
                dataProccessor.setInt("KEY1",0);
                showDatePickerDialoue();
            }
        });

        btnToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataProccessor dataProccessor = new DataProccessor(getApplicationContext());
                dataProccessor.setInt("KEY1",1);
                showDatePickerDialoue();
            }
        });

        btnFromAnnidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataProccessor dataProccessor = new DataProccessor(getApplicationContext());
                dataProccessor.setInt("KEY1",2);
                showDatePickerDialoue();
            }
        });

        btnToAnniDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataProccessor dataProccessor = new DataProccessor(getApplicationContext());
                dataProccessor.setInt("KEY1",3);
                showDatePickerDialoue();
            }
        });
//        etFromBirthDate.setOnTouchListener(new View.OnTouchListener(){
//            public boolean onTouch(View v, MotionEvent event) {
//
//                return false;
//            }
//        });
//
//        etToAnniversaryDate.setOnTouchListener(new View.OnTouchListener(){
//            public boolean onTouch(View v, MotionEvent event) {
//                DatePickerDialog dateDlg = new DatePickerDialog(SearchPeopleActivity.this,
//                        new DatePickerDialog.OnDateSetListener()
//                        {
//
//                            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth)
//                            {
//                                CharSequence strDate = null;
//                                Time chosenDate = new Time();
//                                chosenDate.set(dayOfMonth, monthOfYear, year);
//                                long dtDob = chosenDate.toMillis(true);
//
//                                strDate = DateFormat.format("yyyy-MM-dd", dtDob);
//
//                                etToAnniversaryDate.setText(strDate);
//                            }}, year,month,day);
//
//                dateDlg.show();
//                return false;
//            }
//        });
//
//        etFromAnniDate.setOnTouchListener(new View.OnTouchListener(){
//            public boolean onTouch(View v, MotionEvent event) {
//                DatePickerDialog dateDlg = new DatePickerDialog(SearchPeopleActivity.this,
//                        new DatePickerDialog.OnDateSetListener()
//                        {
//
//                            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth)
//                            {
//                                CharSequence strDate = null;
//                                Time chosenDate = new Time();
//                                chosenDate.set(dayOfMonth, monthOfYear, year);
//                                long dtDob = chosenDate.toMillis(true);
//
//                                strDate = DateFormat.format("yyyy-MM-dd", dtDob);
//
//                                etFromAnniDate.setText(strDate);
//                            }}, year,month,day);
//
//                dateDlg.show();
//                return false;
//            }
//        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                if (checkValidation())
                {
                    progressBar.setVisibility(View.VISIBLE);
                    DBHelper helper = new DBHelper(getApplicationContext());

                    //all fields
   if(!etName.getText().toString().isEmpty() && !etOccupation.getText().toString().isEmpty() &&
           !etCity.getText().toString().isEmpty() && !etToBirthDate.getText().toString().isEmpty() && !etFromBirthDate.getText().toString().isEmpty() &&
           !etToAnniversaryDate.getText().toString().isEmpty() && !etFromAnniDate.getText().toString().isEmpty()){

       helper.getAllSearchFieldsDataDummy(etName.getText().toString(),
               etOccupation.getText().toString(),etCity.getText().toString(),
               String.valueOf(strFromBDate),String.valueOf(strToBDate),
               String.valueOf(strFromADate),String.valueOf(strToADate));

//                        helper.getAllSearchFieldsData(etName.getText().toString(),
//                                etOccupation.getText().toString(),etCity.getText().toString(),
//                                etToBirthDate.getText().toString(),etFromBirthDate.getText().toString(),
//                                etToAnniversaryDate.getText().toString(),etFromAnniDate.getText().toString());

                       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
                       intent.putExtra("list", list);
                       startActivity(intent);
                       progressBar.setVisibility(View.GONE);
             }

   //separate fields
   else if (!etName.getText().toString().isEmpty() &&etOccupation.getText().toString().isEmpty() && etCity.getText().toString().isEmpty()
   && etToBirthDate.getText().toString().isEmpty()  &&etFromBirthDate.getText().toString().isEmpty() &&
           etToAnniversaryDate.getText().toString().isEmpty() &&etFromAnniDate.getText().toString().isEmpty()){
                   list = helper.getSearchNameData(etName.getText().toString().trim());
                   Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
                   intent.putExtra("list", list);
                   startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (etName.getText().toString().isEmpty() && !etOccupation.getText().toString().isEmpty() && etCity.getText().toString().isEmpty()
           && etToBirthDate.getText().toString().isEmpty()  &&etFromBirthDate.getText().toString().isEmpty() && etToAnniversaryDate.getText().toString().isEmpty() &&etFromAnniDate.getText().toString().isEmpty()){
                   list =helper.getSearchOccupationData(etOccupation.getText().toString().trim());
                   Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
                   intent.putExtra("list", list);
                   startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (etName.getText().toString().isEmpty() && etOccupation.getText().toString().isEmpty() && !etCity.getText().toString().isEmpty()
           && etToBirthDate.getText().toString().isEmpty()  &&etFromBirthDate.getText().toString().isEmpty() && etToAnniversaryDate.getText().toString().isEmpty() &&etFromAnniDate.getText().toString().isEmpty()){
                   list =helper.getSearchCityData(etCity.getText().toString().trim());
                   Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
                   intent.putExtra("list", list);
                   startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (etName.getText().toString().isEmpty() && etOccupation.getText().toString().isEmpty() && etCity.getText().toString().isEmpty()
           && !etToBirthDate.getText().toString().isEmpty()  && !etFromBirthDate.getText().toString().isEmpty()
           && etToAnniversaryDate.getText().toString().isEmpty()
           &&etFromAnniDate.getText().toString().isEmpty()){

       list = helper.getSearchBirthDateDatadummy(String.valueOf(strFromBDate),String.valueOf(strToBDate));

//       list = helper.getSearchBirthDateData(etToBirthDate.getText().toString(),etFromBirthDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (etName.getText().toString().isEmpty() && etOccupation.getText().toString().isEmpty() && etCity.getText().toString().isEmpty()
           && etToBirthDate.getText().toString().isEmpty()  && etFromBirthDate.getText().toString().isEmpty() &&
           !etToAnniversaryDate.getText().toString().isEmpty() && !etFromAnniDate.getText().toString().isEmpty()){

       list = helper.getSearchAnniversaryDateDatadummy(String.valueOf(strFromADate),String.valueOf(strToADate));

//       list = helper.getSearchAnniversaryDateData(etToAnniversaryDate.getText().toString(),etFromAnniDate.getText().toString());
                   Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
                   intent.putExtra("list", list);
                   startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etName.getText().toString().isEmpty() && !etOccupation.getText().toString().isEmpty()){

                   list = helper.getSearchNameAndOccupationData(etName.getText().toString().trim(),etOccupation.getText().toString().trim());
                   Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
                   intent.putExtra("list", list);
                   startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etName.getText().toString().isEmpty() && !etCity.getText().toString().isEmpty()){
       list = helper.getSearchNameAndCityData(etName.getText().toString().trim(),etCity.getText().toString().trim());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etName.getText().toString().isEmpty() && !etToBirthDate.getText().toString().isEmpty()
           && !etFromBirthDate.getText().toString().isEmpty()){

       list = helper.getSearchNameAndBirthDateDataDummy(etName.getText().toString().trim(),
               String.valueOf(strFromBDate),String.valueOf(strToBDate));

//       list = helper.getSearchNameAndBirthDateData(etName.getText().toString().trim(),etToBirthDate.getText().toString(),etFromBirthDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etName.getText().toString().isEmpty() && !etToAnniversaryDate.getText().toString().isEmpty()
           && !etFromAnniDate.getText().toString().isEmpty()){

       list = helper.getSearchNameAndAnniversaryDateDataDummy(etName.getText().toString().trim(),
               String.valueOf(strFromADate),String.valueOf(strToADate));

//       list = helper.getSearchNameAndAnniversaryDateData(etName.getText().toString().trim(),etToAnniversaryDate.getText().toString(),etFromAnniDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etOccupation.getText().toString().isEmpty() && !etCity.getText().toString().isEmpty()){
       list = helper.getSearchOccupationAndCityeData(etOccupation.getText().toString().trim(),etCity.getText().toString().trim());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etOccupation.getText().toString().isEmpty() && !etToBirthDate.getText().toString().isEmpty() &&
           !etFromBirthDate.getText().toString().isEmpty()){

       list =  helper.getSearchOccupationAndBirthDateDataDummy(etOccupation.getText().toString().trim(),
               String.valueOf(strFromBDate),String.valueOf(strToBDate));

//       list =  helper.getSearchOccupationAndBirthDateData(etOccupation.getText().toString().trim(),etToBirthDate.getText().toString(),etFromBirthDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etOccupation.getText().toString().isEmpty() &&
           !etToAnniversaryDate.getText().toString().isEmpty() && !etFromAnniDate.getText().toString().isEmpty()){

       list = helper.getSearchOccupationAndAnniDateDataDummy(etOccupation.getText().toString().trim(),
               String.valueOf(strFromADate),String.valueOf(strToADate));

//       list = helper.getSearchOccupationAndAnniDateData(etOccupation.getText().toString().trim(),etToAnniversaryDate.getText().toString(),etFromAnniDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etCity.getText().toString().isEmpty() && !etToBirthDate.getText().toString().isEmpty() &&
           !etFromBirthDate.getText().toString().isEmpty()){
       list = helper.getSearchCityAndBirthDateDataDummy(etCity.getText().toString().trim(),
               String.valueOf(strFromBDate),String.valueOf(strToBDate));


//       list = helper.getSearchCityAndBirthDateData(etCity.getText().toString().trim(),etToBirthDate.getText().toString(),etFromBirthDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etCity.getText().toString().isEmpty() && !etToAnniversaryDate.getText().toString().isEmpty()
           && !etFromAnniDate.getText().toString().isEmpty()){
       list = helper.getSearchCityAndAnniDateDataDummy(etCity.getText().toString().trim(),
               String.valueOf(strFromADate),String.valueOf(strToADate));


//       list = helper.getSearchCityAndAnniDateData(etCity.getText().toString().trim(),etToAnniversaryDate.getText().toString(),etFromAnniDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etToBirthDate.getText().toString().isEmpty() && !etFromBirthDate.getText().toString().isEmpty()
           && !etToAnniversaryDate.getText().toString().isEmpty() && !etFromAnniDate.getText().toString().isEmpty()){

       list = helper.getSearchBirthAndAnniDateDataDummy(String.valueOf(strFromBDate),String.valueOf(strToBDate),String.valueOf(strFromADate),String.valueOf(strToADate));


//       list = helper.getSearchBirthAndAnniDateData(etToBirthDate.getText().toString(),etFromBirthDate.getText().toString(),etToAnniversaryDate.getText().toString(),etFromAnniDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etName.getText().toString().isEmpty() && !etOccupation.getText().toString().isEmpty() &&
           !etCity.getText().toString().isEmpty()){
       list = helper.getSearchNameOccupationCityData(etName.getText().toString().trim(),etOccupation.getText().toString().trim(),etCity.getText().toString().trim());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etName.getText().toString().isEmpty() && !etToBirthDate.getText().toString().isEmpty()
           && !etFromBirthDate.getText().toString().isEmpty()&& !etToAnniversaryDate.getText().toString().isEmpty() &&
           !etFromAnniDate.getText().toString().isEmpty()){
       list = helper.getSearchNameBirthDateAnniversaryDateDataDummy(etName.getText().toString().trim(),String.valueOf(strFromBDate),String.valueOf(strToBDate),String.valueOf(strFromADate),String.valueOf(strToADate));


      // list = helper.getSearchNameBirthDateAnniversaryDateData(etName.getText().toString().trim(),etToBirthDate.getText().toString(),etFromBirthDate.getText().toString(),etToAnniversaryDate.getText().toString(),etFromAnniDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etName.getText().toString().isEmpty() && !etCity.getText().toString().isEmpty() &&
           !etToBirthDate.getText().toString().isEmpty()  && !etFromBirthDate.getText().toString().isEmpty()){
       list = helper.getSearchNameCityBirthDateDataDummy(etName.getText().toString().trim(),etCity.getText().toString().trim(),
               String.valueOf(strFromBDate),String.valueOf(strToBDate));

//       list = helper.getSearchNameCityBirthDateData(etName.getText().toString().trim(),etCity.getText().toString().trim(),etToBirthDate.getText().toString(),etFromBirthDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etName.getText().toString().isEmpty() && !etCity.getText().toString().isEmpty() &&
           !etToAnniversaryDate.getText().toString().isEmpty()  && !etFromAnniDate.getText().toString().isEmpty()){
       list = helper.getSearchNameCityAnniversaryDateDataDummy(etName.getText().toString().trim(),etCity.getText().toString().trim(),
               String.valueOf(strFromADate),String.valueOf(strToADate));

//       list = helper.getSearchNameCityAnniversaryDateData(etName.getText().toString().trim(),etCity.getText().toString().trim(),etToAnniversaryDate.getText().toString(),etFromAnniDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etName.getText().toString().isEmpty() && !etOccupation.getText().toString().isEmpty() &&
           !etToBirthDate.getText().toString().isEmpty()  && !etFromBirthDate.getText().toString().isEmpty()){

       list = helper.getSearchNameOccupationBirthDateDataDummy(etName.getText().toString().trim(),etOccupation.getText().toString().trim(),
               String.valueOf(strFromBDate),String.valueOf(strToBDate));


//       list = helper.getSearchNameOccupationBirthDateData(etName.getText().toString().trim(),etOccupation.getText().toString().trim(),etToBirthDate.getText().toString(),etFromBirthDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etName.getText().toString().isEmpty() && !etOccupation.getText().toString().isEmpty() &&
           !etToAnniversaryDate.getText().toString().isEmpty()  && !etFromAnniDate.getText().toString().isEmpty()){

       list = helper.getSearchNameOccupationAnniversaryDateDataDummy(etName.getText().toString().trim(),etOccupation.getText().toString().trim(),
               String.valueOf(strFromADate),String.valueOf(strToADate));

//       list = helper.getSearchNameOccupationAnniversaryDateData(etName.getText().toString().trim(),etOccupation.getText().toString().trim(),etToAnniversaryDate.getText().toString(),etFromAnniDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etName.getText().toString().isEmpty() && !etOccupation.getText().toString().isEmpty() &&
           !etToBirthDate.getText().toString().isEmpty()  && !etFromBirthDate.getText().toString().isEmpty() &&
           !etToAnniversaryDate.getText().toString().isEmpty() &&!etFromAnniDate.getText().toString().isEmpty()){

       list = helper.getSearchNameOccupationBirthDateAnniDateDataDummy(etName.getText().toString().trim(),
               etOccupation.getText().toString().trim(),
               String.valueOf(strFromBDate),String.valueOf(strToBDate),
               String.valueOf(strFromADate),String.valueOf(strToADate));

//       list = helper.getSearchNameOccupationBirthDateAnniDateData(etName.getText().toString().trim(),etOccupation.getText().toString().trim(),etToBirthDate.getText().toString(),etFromBirthDate.getText().toString(),etToAnniversaryDate.getText().toString(),etFromAnniDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etName.getText().toString().isEmpty() && !etCity.getText().toString().isEmpty() &&
           !etToBirthDate.getText().toString().isEmpty()  && !etFromBirthDate.getText().toString().isEmpty()
           && !etToAnniversaryDate.getText().toString().isEmpty() &&!etFromAnniDate.getText().toString().isEmpty()){

       helper.getSearchNameCityBirthDateAnniDateDataDummy(etName.getText().toString().trim(),etCity.getText().toString().trim(),
               String.valueOf(strFromBDate),String.valueOf(strToBDate),
               String.valueOf(strFromADate),String.valueOf(strToADate));


//       helper.getSearchNameCityBirthDateAnniDateData(etName.getText().toString().trim(),etCity.getText().toString().trim(),etToBirthDate.getText().toString(),etFromBirthDate.getText().toString(),etToAnniversaryDate.getText().toString(),etFromAnniDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etName.getText().toString().isEmpty() && !etOccupation.getText().toString().isEmpty()
           && !etCity.getText().toString().isEmpty() &&
           !etToBirthDate.getText().toString().isEmpty()
           && !etFromBirthDate.getText().toString().isEmpty()){

       list = helper.getSearchNameOccupationCityBirthDateDataDummy(etName.getText().toString().trim(),etOccupation.getText().toString().trim(),etCity.getText().toString().trim(),
               String.valueOf(strFromBDate),String.valueOf(strToBDate));

//       list = helper.getSearchNameOccupationCityBirthDateData(etName.getText().toString().trim(),etOccupation.getText().toString().trim(),etCity.getText().toString().trim(),etToBirthDate.getText().toString(),etFromBirthDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etName.getText().toString().isEmpty() && !etOccupation.getText().toString().isEmpty() &&
           !etCity.getText().toString().isEmpty() && !etToAnniversaryDate.getText().toString().isEmpty()
           && !etFromAnniDate.getText().toString().isEmpty()){

       list = helper.getSearchNameOccupationCityAnniDateDataDummy(etName.getText().toString().trim(),etOccupation.getText().toString().trim(),etCity.getText().toString().trim(),
               String.valueOf(strFromADate),String.valueOf(strToADate));

//       list = helper.getSearchNameOccupationCityAnniDateData(etName.getText().toString().trim(),etOccupation.getText().toString().trim(),etCity.getText().toString().trim(),etToAnniversaryDate.getText().toString(),etFromAnniDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etCity.getText().toString().isEmpty() && !etToBirthDate.getText().toString().isEmpty() &&
           !etFromBirthDate.getText().toString().isEmpty() &&!etToAnniversaryDate.getText().toString().isEmpty()
           && !etFromAnniDate.getText().toString().isEmpty()){

       list = helper.getSearchCityBirthDateAnniDateDataDummy(etCity.getText().toString().trim(),
               String.valueOf(strFromBDate),String.valueOf(strToBDate),
               String.valueOf(strFromADate),String.valueOf(strToADate));


//       list = helper.getSearchCityBirthDateAnniDateData(etCity.getText().toString().trim(),etToBirthDate.getText().toString(),etFromBirthDate.getText().toString(),etToAnniversaryDate.getText().toString(),etFromAnniDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etOccupation.getText().toString().isEmpty() && !etToBirthDate.getText().toString().isEmpty() &&
           !etFromBirthDate.getText().toString().isEmpty() &&!etToAnniversaryDate.getText().toString().isEmpty()
           && !etFromAnniDate.getText().toString().isEmpty()){

       list = helper.getSearchOccupationBirthDateAnniDateDataDummy(etOccupation.getText().toString().trim(),
               String.valueOf(strFromBDate),String.valueOf(strToBDate),
               String.valueOf(strFromADate),String.valueOf(strToADate));

//       list = helper.getSearchOccupationBirthDateAnniDateData(etOccupation.getText().toString().trim(),etToBirthDate.getText().toString(),etFromBirthDate.getText().toString(),etToAnniversaryDate.getText().toString(),etFromAnniDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etOccupation.getText().toString().isEmpty() && !etCity.getText().toString().isEmpty()
           &&!etToBirthDate.getText().toString().isEmpty() && !etFromBirthDate.getText().toString().isEmpty()){

       list = helper.getSearchOccupationCityBirthDateDataDummy(etOccupation.getText().toString().trim(),etCity.getText().toString().trim(),
               String.valueOf(strFromBDate),String.valueOf(strToBDate));

//       list = helper.getSearchOccupationCityBirthDateData(etOccupation.getText().toString().trim(),etCity.getText().toString().trim(),etToBirthDate.getText().toString(),etFromBirthDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etOccupation.getText().toString().isEmpty() && !etCity.getText().toString().isEmpty()
           &&!etToAnniversaryDate.getText().toString().isEmpty() && !etFromAnniDate.getText().toString().isEmpty()){

       list = helper.getSearchOccupationCityAnniDateDataDummy(etOccupation.getText().toString().trim(),etCity.getText().toString().trim(),
               String.valueOf(strFromADate),String.valueOf(strToADate));

//       list = helper.getSearchOccupationCityAnniDateData(etOccupation.getText().toString().trim(),etCity.getText().toString().trim(),etToAnniversaryDate.getText().toString(),etFromAnniDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
   }
   else if (!etOccupation.getText().toString().isEmpty() && !etCity.getText().toString().isEmpty() &&
           !etToBirthDate.getText().toString().isEmpty() && !etFromBirthDate.getText().toString().isEmpty()
           && !etToAnniversaryDate.getText().toString().isEmpty() && !etFromAnniDate.getText().toString().isEmpty()){

       list = helper.getSearchOccupationCityBirthDateAnniDateDataDummy(etOccupation.getText().toString().trim(),etCity.getText().toString().trim(),
               String.valueOf(strFromBDate),String.valueOf(strToBDate),
               String.valueOf(strFromADate),String.valueOf(strToADate));

//       list = helper.getSearchOccupationCityBirthDateAnniDateData(etOccupation.getText().toString().trim(),etCity.getText().toString().trim(),etToBirthDate.getText().toString(),etFromBirthDate.getText().toString(),etToAnniversaryDate.getText().toString(),etFromAnniDate.getText().toString());
       Intent intent = new Intent(getApplicationContext(), SearchPeopleResultActivity.class);
       intent.putExtra("list", list);
       startActivity(intent);
       progressBar.setVisibility(View.GONE);
       }
        progressBar.setVisibility(View.GONE);
        }
      }
   });
}

    private void showDatePickerDialoue() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(SearchPeopleActivity.this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private boolean checkValidation() {
        DBHelper helper = new DBHelper(getApplicationContext());
        if (etName.getText().toString().isEmpty() && etOccupation.getText().toString().isEmpty()
                && etCity.getText().toString().isEmpty() && etToBirthDate.getText().toString().isEmpty()
                &&etFromBirthDate.getText().toString().isEmpty() && etToAnniversaryDate.getText().toString().isEmpty()
                && etFromAnniDate.getText().toString().isEmpty()) {

            allList = helper.getAllRecordsFromDB();
            Intent intent = new Intent(getApplicationContext(), AllRecordsActivity.class);
            intent.putExtra("list", allList);
            startActivity(intent);
            progressBar.setVisibility(View.GONE);

//            Toast.makeText(getApplicationContext(), "please enter any one of field", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        DataProccessor dataProccessor = new DataProccessor(getApplicationContext());
        int key = dataProccessor.getInt("KEY1");
        if (key==0){
            CharSequence strDate = null;
            CharSequence strDate1 = null;

            Time chosenDate = new Time();
            chosenDate.set(dayOfMonth, month, year);
            long dtDob = chosenDate.toMillis(true);
            strDate = DateFormat.format("dd/MM/yyyy", dtDob);
            etToBDateDummy.setText(strDate);

            strDate1 = DateFormat.format("yyyy-MM-dd", dtDob);
            etToBirthDate.setText(strDate1);

            strFromBDate = DateFormat.format("MM-dd", dtDob);

        } else if (key==1){
            CharSequence strDate = null;
            CharSequence strDate1 = null;
            Time chosenDate = new Time();
            chosenDate.set(dayOfMonth, month, year);
            long dtDob = chosenDate.toMillis(true);
            strDate = DateFormat.format("dd/MM/yyyy", dtDob);
            etFromBDateDummy.setText(strDate);

            strDate1 = DateFormat.format("yyyy-MM-dd", dtDob);
            etFromBirthDate.setText(strDate1);

            strToBDate = DateFormat.format("MM-dd", dtDob);
        } else if (key==2){
            CharSequence strDate = null;
            CharSequence strDate1 = null;
            Time chosenDate = new Time();
            chosenDate.set(dayOfMonth, month, year);
            long dtDob = chosenDate.toMillis(true);
            strDate = DateFormat.format("dd/MM/yyyy", dtDob);
            etToAnniversaryDatedummy.setText(strDate);

            strDate1 = DateFormat.format("yyyy-MM-dd", dtDob);
            etToAnniversaryDate.setText(strDate1);

            strFromADate = DateFormat.format("MM-dd", dtDob);
        } else if (key==3){
            CharSequence strDate = null;
            CharSequence strDate1 = null;
            Time chosenDate = new Time();
            chosenDate.set(dayOfMonth, month, year);
            long dtDob = chosenDate.toMillis(true);
            strDate = DateFormat.format("dd/MM/yyyy", dtDob);
            etFromAnniDatedummy.setText(strDate);

            strDate1 = DateFormat.format("yyyy-MM-dd", dtDob);
            etFromAnniDate.setText(strDate1);

            strToADate = DateFormat.format("MM-dd", dtDob);
        }
    }
}
