package com.example.samparksuchiapplication.DataBase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import com.example.samparksuchiapplication.Model.ContactDetailsModel;

import static android.icu.text.MessagePattern.ArgType.SELECT;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String TABLE_NAME = "ContactDetailsTable";
    public static final String COLUMN__RECORDID = "recordid";
    public static final String COLUMN__MEMBERCODE = "memberCode";
    public static final String COLUMN__CONTACTNAME = "contactname";
    public static final String COLUMN__OCCUPATION = "occupation";
    public static final String COLUMN__ADDRESS = "address";
    public static final String COLUMN__CITY = "city";
    public static final String COLUMN__PINCODE = "pincode";
    public static final String COLUMN__STATE = "state";
    public static final String COLUMN_CONTACT_NUMBER = "contactnumber";
    public static final String COLUMN_CONTACT_NUMBER1 = "contactnumber1";
    public static final String COLUMN_EMAIL_ID = "emailid";
    public static final String COLUMN__BIRTHDATE = "birthdate";
    public static final String COLUMN_ANNIVERSARYDATE = "anniversarydate";
    public static final String COLUMN_PHOTO = "photo";
    public static final String COLUMN_DD = "dd";
    public static final String COLUMN_BMONTH = "bmonth";
    public static final String COLUMN_ADATE = "adate";
    public static final String COLUMN_AMONTH = "amonth";
    public static final String COLUMN_BMONTHDATE = "bmonthdate";
    public static final String COLUMN_AMONTHDATE = "amonthdate";
    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table ContactDetailsTable " +
                        "( recordid text,memberCode text,contactname text," +
                        "birthdate text,anniversarydate text,contactnumber text," +
                        "occupation text,address text,city text,pincode text,state text," +
                        "contactnumber1 text,emailid text,photo text,dd text,bmonth text," +
                        "adate text,amonth text,bmonthdate text,amonthdate text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS ContactDetailsTable");
        onCreate(db);
    }

    public void insertContacts(List<ContactDetailsModel> contactDetailsModel) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < contactDetailsModel.size(); i++) {
            try {
                contentValues.put("recordid", contactDetailsModel.get(i).getRecordId());
                contentValues.put("memberCode", contactDetailsModel.get(i).getMemberCode());
                contentValues.put("contactname", contactDetailsModel.get(i).getContactName());
                contentValues.put("occupation", contactDetailsModel.get(i).getOccupation());
                contentValues.put("address", contactDetailsModel.get(i).getAddress());
                contentValues.put("city", contactDetailsModel.get(i).getCity());
                contentValues.put("pincode", contactDetailsModel.get(i).getPinCode());
                contentValues.put("state", contactDetailsModel.get(i).getState());
                contentValues.put("contactnumber", contactDetailsModel.get(i).getPhoneNumber());
                contentValues.put("contactnumber1", contactDetailsModel.get(i).getPhonenNumber1());
                contentValues.put("emailid", contactDetailsModel.get(i).getEmailId());
                contentValues.put("photo", contactDetailsModel.get(i).getPhotoUrl());

                try{
                    if (contactDetailsModel.get(i).getBMonthDate()!=null){
                        contentValues.put("bmonthdate", contactDetailsModel.get(i).getBMonthDate());
                        Log.e("BIRTHDAYMONTHDATE",""+ contentValues);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try{
                    if (contactDetailsModel.get(i).getAMonthDate()!=null){
                        contentValues.put("amonthdate", contactDetailsModel.get(i).getAMonthDate());
                        Log.e("ANNIVERSARYMONTHDATE",""+ contentValues);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (contactDetailsModel.get(i).getBirthDate() != null) {
                        contentValues.put("birthdate", contactDetailsModel.get(i).getBirthDate());
                        Log.e("PUTBIRTH", "" + contentValues);
                        String date = contactDetailsModel.get(i).getBirthDate();
                        String[] date1 = date.split("-");
                        Calendar beginTime = Calendar.getInstance();
                        beginTime.set(Integer.parseInt(date1[2]), Integer.parseInt(date1[1]),
                                Integer.parseInt(date1[0]));
                        contentValues.put("dd", Integer.parseInt(date1[2]));
                        contentValues.put("bmonth", Integer.parseInt(date1[1]));
                        Log.e("bDate", "" + Integer.parseInt(date1[2]));
                        Log.e("bmonth", "" + Integer.parseInt(date1[1]));
                    } else {
                        contentValues.put("birthdate", "null");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                try {
                    if (contactDetailsModel.get(i).getAnniversaryDate() != null) {
                        contentValues.put("anniversarydate", contactDetailsModel.get(i).getAnniversaryDate());
                        String dd = contactDetailsModel.get(i).getAnniversaryDate();
                        String[] dd1 = dd.split("-");
                        Calendar beginTime1 = Calendar.getInstance();
                        beginTime1.set(Integer.parseInt(dd1[2]), Integer.parseInt(dd1[1]), Integer.parseInt(dd1[0]));
                        contentValues.put("adate", Integer.parseInt(dd1[2]));
                        contentValues.put("amonth", Integer.parseInt(dd1[1]));
                        Log.e("aDate", "" + Integer.parseInt(dd1[2]));
                        Log.e("aMonth", "" + Integer.parseInt(dd1[1]));
                    } else {
                        contentValues.put("anniversarydate", "null");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                db.insert("ContactDetailsTable", null, contentValues);
            } catch (Exception e) {
                Toast.makeText(context, "" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    public List<ContactDetailsModel> getAllContacts() {
        List<ContactDetailsModel> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));

                try {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return list;
    }

    public List<ContactDetailsModel> getAllBirthDates() {
        List<ContactDetailsModel> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                list.add(model);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return list;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public List<ContactDetailsModel> getDate(int date1, int bmonth) {
        List<ContactDetailsModel> list = new ArrayList<>();

        // Select All Query
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ContactDetailsTable WHERE dd = '" + date1 + "' and bmonth = '" + bmonth + "' or adate = '" + date1 + "' and amonth = '" + bmonth + "'", null);
//        Cursor cursor = db.rawQuery("SELECT * FROM ContactDetailsTable WHERE contactname = 'Harsh Maloo'", null);

        //SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));

                try {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                list.add(model);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public List<ContactDetailsModel> getMonth(int bmonth) {
        List<ContactDetailsModel> list = new ArrayList<>();

        // Select All Query
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ContactDetailsTable WHERE bmonth = '" + bmonth + "' or  amonth = '" + bmonth + "' ORDER BY bmonth,amonth ASC", null);
//        Cursor cursor = db.rawQuery("SELECT * FROM ContactDetailsTable WHERE contactname = 'Harsh Maloo'", null);

        //SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));

                try {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                list.add(model);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return list;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ArrayList<ContactDetailsModel> getAllSearchFieldsData(String name, String occupation,String city, String toBirthDate, String fromBirthDate, String toAniversaryDate, String fromAniversaryDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname='"+name+"' and occupation ='"+occupation+"' and city='"+city+"' and " +
                "birthdate>='"+toBirthDate+"' AND birthdate<='"+fromBirthDate+"' and anniversarydate>='"+toAniversaryDate+"' AND anniversarydate<='"+fromAniversaryDate+"'  ",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return list;
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ArrayList<ContactDetailsModel> getAllSearchFieldsDataDummy(String name, String occupation,String city, String toBirthDate, String fromBirthDate, String toAniversaryDate, String fromAniversaryDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname='"+name+"' and occupation ='"+occupation+"' and city='"+city+"' and " +
                "bmonthdate >='"+toBirthDate+"' AND bmonthdate <='"+fromBirthDate+"' and amonthdate >='"+toAniversaryDate+"' AND amonthdate <='"+fromAniversaryDate+"' order by bmonthdate,amonthdate  ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ArrayList<ContactDetailsModel> getSearchResult(String name, String occupation,
                                                          String city, String toBirthDate, String fromBirthDate, String toAniversaryDate, String fromAniversaryDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=null;

        try {
//            cursor = db.rawQuery("SELECT * from ContactDetailsTable " +
//            " WHERE CASE WHEN '"+name+"' IS NULL THEN '' ELSE contactname= '"+name+"' END" +
//            "AND  CASE WHEN '"+occupation+"' IS NULL THEN '' ELSE occupation= '"+occupation+"' END" +
//            "AND  CASE WHEN '"+city+"' IS NULL THEN '' ELSE city= '"+city+"' END", null);

//            cursor = db.rawQuery("SELECT * from ContactDetailsTable  WHERE CASE WHEN ISNULL('"+name+"','')=''  " +
//                    "then '' else  contactname= '"+name+"' end =ISNULL('"+name+"','') AND " +
//                    "CASE WHEN ISNULL ('"+occupation+"','')='' then '' else  occupation= '"+occupation+"' end =ISNULL('"+occupation+"','')",
//                    null);

//            cursor = db.rawQuery("SELECT * from ContactDetailsTable  WHERE  " +
//                    "IFNULL('"+name+"','') and  IFNULL('"+occupation+"','') ",null);

cursor = db.rawQuery("SELECT * from ContactDetailsTable  where case  when contactname = '"+name+"'  then  contactname = '"+name+"' " +
        "when occupation = '"+occupation+"' then  occupation ='"+occupation+"' else '' end ",null);

        } catch (Exception e) {
            Toast.makeText(context,""+e.toString(),Toast.LENGTH_LONG).show();
            Log.e("SQLITE",""+e.toString());
        }

        Log.e("QUERY", "" + cursor);
        Log.e("CURSORCOUNT", "" + cursor.getCount());

        if (cursor.moveToFirst())
        {
            do
                {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return list;
    }

    public ArrayList<ContactDetailsModel> getSearchNameData(String name) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' ",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchOccupationData(String occupation) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where occupation LIKE '"+occupation+"%' ",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public ArrayList<ContactDetailsModel> getSearchCityData(String city) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where city LIKE '"+city+"%' ",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchBirthDateData(String TobirthDate,String FrombirthDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where birthdate>='"+TobirthDate+"' and birthdate<='"+FrombirthDate+"' ",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchBirthDateDatadummy(String value1,String value2) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where bmonthdate>='"+value1+"' and bmonthdate <='"+value2+"' order by bmonthdate,amonthdate  ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                    model.setBMonthDate(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTHDATE)));
                    model.setAMonthDate(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTHDATE)));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchAnniversaryDateDatadummy(String value1,String value2) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where amonthdate between  '"+value1+"' and '"+value2+"' order by amonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                    model.setBMonthDate(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTHDATE)));
                    model.setAMonthDate(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTHDATE)));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchAnniversaryDateData(String ToAnniversaryDate,String FromAnniversaryDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where anniversarydate>='"+ToAnniversaryDate+"' and anniversarydate<='"+FromAnniversaryDate+"' ",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public ArrayList<ContactDetailsModel> getSearchNameAndOccupationData(String name,String occupation) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and occupation LIKE '"+occupation+"%' ",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public ArrayList<ContactDetailsModel> getSearchNameAndCityData(String name,String city) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and city LIKE '"+city+"%' ",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchNameAndBirthDateData(String name,String TobirthDate,String FrombirthDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and birthdate>='"+TobirthDate+"'  and birthdate<='"+FrombirthDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchNameAndBirthDateDataDummy(String name,String TobirthDate,String FrombirthDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%'" +
                "and bmonthdate >='"+TobirthDate+"'  and bmonthdate<='"+FrombirthDate+"' order by bmonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchNameAndAnniversaryDateData(String name,String ToAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and anniversarydate >='"+ToAnniDate+"'  and anniversarydate<='"+FromAnniDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchNameAndAnniversaryDateDataDummy(String name,String ToAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and amonthdate >='"+ToAnniDate+"'  and amonthdate<='"+FromAnniDate+"' order by amonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchOccupationAndCityeData(String occupation,String city) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where occupation LIKE '"+occupation+"%' and city LIKE '"+city+"%' ",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public ArrayList<ContactDetailsModel> getSearchOccupationAndBirthDateData(String occupation,String toBirthdate,String fromBirthDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where occupation LIKE '"+occupation+"%' and birthdate>='"+toBirthdate+"' and birthdate<='"+fromBirthDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public ArrayList<ContactDetailsModel> getSearchOccupationAndBirthDateDataDummy(String occupation,String toBirthdate,String fromBirthDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where occupation LIKE '"+occupation+"%' " +
                "and bmonthdate>='"+toBirthdate+"' and bmonthdate<='"+fromBirthDate+"' order by bmonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchOccupationAndAnniDateData(String occupation,String toAnnidate,String fromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where occupation LIKE '"+occupation+"%' and anniversarydate>='"+toAnnidate+"' and anniversarydate<='"+fromAnniDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public ArrayList<ContactDetailsModel> getSearchOccupationAndAnniDateDataDummy(String occupation,String toAnnidate,String fromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where occupation LIKE '"+occupation+"%' and amonthdate>='"+toAnnidate+"' and amonthdate<='"+fromAnniDate+"' order by bmonthdate,amonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchCityAndBirthDateData(String city,String toBirthDate,String fromBirthDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where city LIKE '"+city+"%' and birthdate>='"+toBirthDate+"' and birthdate<='"+fromBirthDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchCityAndBirthDateDataDummy(String city,String toBirthDate,String fromBirthDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where city LIKE '"+city+"%' and bmonthdate >='"+toBirthDate+"' and bmonthdate <='"+fromBirthDate+"' order by bmonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchCityAndAnniDateData(String city,String toAnnidate,String fromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where city LIKE '"+city+"%' and anniversarydate>='"+toAnnidate+"' and anniversarydate<='"+fromAnniDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public ArrayList<ContactDetailsModel> getSearchCityAndAnniDateDataDummy(String city,String toAnnidate,String fromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where city LIKE '"+city+"%' and amonthdate >='"+toAnnidate+"' and amonthdate <='"+fromAnniDate+"' order by bmonthdate,amonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchBirthAndAnniDateData(String toBirthDate,String FromBirthDate,String toAnnidate,String fromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where birthdate>='"+toBirthDate+"' and birthdate<='"+FromBirthDate+"' and  anniversarydate>='"+toAnnidate+"' and anniversarydate<='"+fromAnniDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchBirthAndAnniDateDataDummy(String toBirthDate,String FromBirthDate,String toAnnidate,String fromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where bmonthdate >='"+toBirthDate+"' and bmonthdate <='"+FromBirthDate+"' and  amonthdate >='"+toAnnidate+"' and amonthdate <='"+fromAnniDate+"' order by bmonthdate,amonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchNameOccupationCityData(String name,String occupation,String city) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and occupation LIKE '"+occupation+"%' and city LIKE '"+city+"%' ",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchNameBirthDateAnniversaryDateData(String name,String toBirthDate,String FromBirthDate,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and birthdate>= '"+toBirthDate+"' and birthdate<='"+FromBirthDate+"' and anniversarydate>='"+toAnniDate+"' and anniversarydate<='"+FromAnniDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchNameBirthDateAnniversaryDateDataDummy(String name,String toBirthDate,String FromBirthDate,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and bmonthdate >= '"+toBirthDate+"' and bmonthdate <='"+FromBirthDate+"' and amonthdate >='"+toAnniDate+"' and amonthdate <='"+FromAnniDate+"' order by bmonthdate,amonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchNameCityBirthDateData(String name,String city,String toBirthDate,String FromBirthDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and city LIKE '"+city+"%' and birthdate>= '"+toBirthDate+"' and birthdate<='"+FromBirthDate+"' ",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }




    public ArrayList<ContactDetailsModel> getSearchNameCityBirthDateDataDummy(String name,String city,String toBirthDate,String FromBirthDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and city LIKE '"+city+"%' and bmonthdate >= '"+toBirthDate+"' and bmonthdate <='"+FromBirthDate+"'  order by bmonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public ArrayList<ContactDetailsModel> getSearchNameCityAnniversaryDateData(String name,String city,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and city LIKE '"+city+"%' and anniversarydate>='"+toAnniDate+"' and anniversarydate<='"+FromAnniDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }



    public ArrayList<ContactDetailsModel> getSearchNameCityAnniversaryDateDataDummy(String name,String city,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and city LIKE '"+city+"%' and amonthdate >='"+toAnniDate+"' and amonthdate <='"+FromAnniDate+"' order by bmonthdate,amonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchNameOccupationBirthDateData(String name,String occupation,String toBirthDate,String FromBirthDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and occupation LIKE '"+occupation+"%' and birthdate>='"+toBirthDate+"' and birthdate<='"+FromBirthDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }



    public ArrayList<ContactDetailsModel> getSearchNameOccupationBirthDateDataDummy(String name,String occupation,String toBirthDate,String FromBirthDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and occupation LIKE '"+occupation+"%' and bmonthdate >='"+toBirthDate+"' and bmonthdate <='"+FromBirthDate+"'  order by bmonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public ArrayList<ContactDetailsModel> getSearchNameOccupationAnniversaryDateData(String name,String occupation,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and occupation LIKE '"+occupation+"%' and anniversarydate>='"+toAnniDate+"' and anniversarydate<='"+FromAnniDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }



    public ArrayList<ContactDetailsModel> getSearchNameOccupationAnniversaryDateDataDummy(String name,String occupation,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and occupation LIKE '"+occupation+"%' and amonthdate>='"+toAnniDate+"' and amonthdate<='"+FromAnniDate+"'  order by bmonthdate,amonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchNameOccupationBirthDateAnniDateData(String name,String occupation,String toBirthDate,String FromBirthDate,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and occupation LIKE '"+occupation+"%' and birthdate>='"+toBirthDate+"' and birthdate<='"+FromBirthDate+"' and anniversarydate>='"+toAnniDate+"' and anniversarydate<='"+FromAnniDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }



    public ArrayList<ContactDetailsModel> getSearchNameOccupationBirthDateAnniDateDataDummy(String name,String occupation,String toBirthDate,String FromBirthDate,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and occupation LIKE '"+occupation+"%' and bmonthdate>='"+toBirthDate+"' and bmonthdate<='"+FromBirthDate+"' and amonthdate>='"+toAnniDate+"' and amonthdate<='"+FromAnniDate+"'  order by bmonthdate,amonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchNameCityBirthDateAnniDateData(String name,String city,String toBirthDate,String FromBirthDate,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and city LIKE '"+city+"%' and birthdate>='"+toBirthDate+"' and birthdate<='"+FromBirthDate+"' and anniversarydate>='"+toAnniDate+"' and anniversarydate<='"+FromAnniDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }



    public ArrayList<ContactDetailsModel> getSearchNameCityBirthDateAnniDateDataDummy(String name,String city,String toBirthDate,String FromBirthDate,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and city LIKE '"+city+"%' and bmonthdate>='"+toBirthDate+"' and bmonthdate<='"+FromBirthDate+"' and amonthdate>='"+toAnniDate+"' and amonthdate<='"+FromAnniDate+"'  order by bmonthdate,amonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public ArrayList<ContactDetailsModel> getSearchOccupationBirthDateAnniDateData(String Occupation,String toBirthDate,String FromBirthDate,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where occupation LIKE '"+Occupation+"%'  and birthdate>='"+toBirthDate+"' and birthdate<='"+FromBirthDate+"' and anniversarydate>='"+toAnniDate+"' and anniversarydate<='"+FromAnniDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchOccupationBirthDateAnniDateDataDummy(String Occupation,String toBirthDate,String FromBirthDate,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where occupation LIKE '"+Occupation+"%'  and bmonthdate>='"+toBirthDate+"' and bmonthdate<='"+FromBirthDate+"' and amonthdate>='"+toAnniDate+"' and amonthdate<='"+FromAnniDate+"'  order by bmonthdate,amonthdate  ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public ArrayList<ContactDetailsModel> getSearchOccupationCityBirthDateAnniDateData(String Occupation,String city,String toBirthDate,String FromBirthDate,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where occupation LIKE '"+Occupation+"%' and city LIKE '"+city+"%' and birthdate>='"+toBirthDate+"' and birthdate<='"+FromBirthDate+"' and anniversarydate>='"+toAnniDate+"' and anniversarydate<='"+FromAnniDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchOccupationCityBirthDateAnniDateDataDummy(String Occupation,String city,String toBirthDate,String FromBirthDate,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where occupation LIKE '"+Occupation+"%' and city LIKE '"+city+"%' and bmonthdate>='"+toBirthDate+"' and bmonthdate<='"+FromBirthDate+"' and amonthdate>='"+toAnniDate+"' and amonthdate<='"+FromAnniDate+"' order by bmonthdate,amonthdate  ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchOccupationCityBirthDateData(String Occupation,String city,String toBirthDate,String FromBirthDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where occupation LIKE '"+Occupation+"%' and city LIKE '"+city+"%' and birthdate>='"+toBirthDate+"' and birthdate<='"+FromBirthDate+"' ",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }



    public ArrayList<ContactDetailsModel> getSearchOccupationCityBirthDateDataDummy(String Occupation,String city,String toBirthDate,String FromBirthDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where occupation LIKE '"+Occupation+"%' and city LIKE '"+city+"%' and bmonthdate>='"+toBirthDate+"' and bmonthdate<='"+FromBirthDate+"' order by bmonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public ArrayList<ContactDetailsModel> getSearchOccupationCityAnniDateData(String Occupation,String city,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where occupation LIKE '"+Occupation+"%' and city LIKE '"+city+"%'  and anniversarydate>='"+toAnniDate+"' and anniversarydate<='"+FromAnniDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }



    public ArrayList<ContactDetailsModel> getSearchOccupationCityAnniDateDataDummy(String Occupation,String city,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where occupation LIKE '"+Occupation+"%' and city LIKE '"+city+"%'  and amonthdate>='"+toAnniDate+"' and amonthdate<='"+FromAnniDate+"'  order by bmonthdate,amonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public ArrayList<ContactDetailsModel> getSearchNameOccupationCityAnniDateData(String name,String Occupation,String city,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and occupation LIKE '"+Occupation+"%' and city LIKE '"+city+"%'  and anniversarydate>='"+toAnniDate+"' and anniversarydate<='"+FromAnniDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }



    public ArrayList<ContactDetailsModel> getSearchNameOccupationCityAnniDateDataDummy(String name,String Occupation,String city,String toAnniDate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and occupation LIKE '"+Occupation+"%' and city LIKE '"+city+"%'  and amonthdate>='"+toAnniDate+"' and amonthdate<='"+FromAnniDate+"'  order by bmonthdate,amonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchNameOccupationCityBirthDateData(String name,String Occupation,String city,String toBirthdate,String FromBirthDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and occupation LIKE '"+Occupation+"%' and city LIKE '"+city+"%'  and birthdate>='"+toBirthdate+"' and birthdate<='"+FromBirthDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchNameOccupationCityBirthDateDataDummy(String name,String Occupation,String city,String toBirthdate,String FromBirthDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where contactname LIKE '"+name+"%' and occupation LIKE '"+Occupation+"%' and city LIKE '"+city+"%'  and bmonthdate>='"+toBirthdate+"' and bmonthdate<='"+FromBirthDate+"'  order by bmonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchCityBirthDateAnniDateData(String city,String toBirthdate,String FromBirthDate,String toAnnidate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where city LIKE '"+city+"%' and birthdate>='"+toBirthdate+"' and birthdate<='"+FromBirthDate+"' and anniversarydate>='"+toAnnidate+"' and anniversarydate<='"+FromAnniDate+"'",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getSearchCityBirthDateAnniDateDataDummy(String city,String toBirthdate,String FromBirthDate,String toAnnidate,String FromAnniDate) {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable  where city LIKE '"+city+"%' and bmonthdate>='"+toBirthdate+"' and bmonthdate<='"+FromBirthDate+"' and amonthdate>='"+toAnnidate+"' and amonthdate<='"+FromAnniDate+"'  order by bmonthdate,amonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<ContactDetailsModel> getAllRecordsFromDB() {
        ArrayList<ContactDetailsModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ContactDetailsTable order by bmonthdate,amonthdate ASC",null);

        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public  ArrayList<ContactDetailsModel> updateContactName(){
        ArrayList<ContactDetailsModel> list = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Update ContactDetailsTable SET contactname = UPPER(contactname)",null);
        if (cursor.moveToFirst())
        {
            do
            {
                ContactDetailsModel model = new ContactDetailsModel();
                model.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN__CONTACTNAME)));
                model.setBirthDate(cursor.getString(cursor.getColumnIndex(COLUMN__BIRTHDATE)));
                model.setAnniversaryDate(cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARYDATE)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER)));
                model.setPhonenNumber1(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NUMBER1)));
                model.setRecordId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN__RECORDID))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN__ADDRESS)));
                model.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN__OCCUPATION)));
                model.setCity(cursor.getString(cursor.getColumnIndex(COLUMN__CITY)));
                model.setState(cursor.getString(cursor.getColumnIndex(COLUMN__STATE)));
                model.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setPhotoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                model.setMemberCode(cursor.getString(cursor.getColumnIndex(COLUMN__MEMBERCODE)));
                model.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN__PINCODE)));
                try
                {
                    model.setDd(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DD))));
                    model.setbMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BMONTH))));
                    model.setaDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ADATE))));
                    model.setAMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AMONTH))));
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }
}
//        Cursor cursor = db.rawQuery("SELECT * FROM ContactDetailsTable WHERE contactname = '"+name+"' " +
//                "and occupation = '"+occupation+"' and city = '"+city+"' or birthdate BETWEEN '"+toBirthDate+"' " +
//                "and '"+fromBirthDate+"'", null);
//        if(name.equals("")){
//            name="Empty";
//        }if(occupation.equals("")){
//        occupation="Empty";
//         }if(city.equals("")){
//        city="Empty";
//         }if (toBirthDate.equals("")){
//            toBirthDate="Empty";
//         }if (fromBirthDate.equals("")){
//        fromBirthDate="Empty";
//            }
//        if (toAniversaryDate.equals("")){
//            toAniversaryDate="Empty";
//        }if (fromAniversaryDate.equals("")){
//        fromAniversaryDate="Empty";
//        }


//            Cursor cursor = db.rawQuery( "SELECT * FROM ContactDetailsTable " +
//                "where   ( contactname = '"+name+"') " +
//                "and (occupation = '"+occupation+"') " +
//                "and   (city = '"+city+"') " +
//                "and  (birthdate >='"+toBirthDate+"' and birthdate<='"+fromBirthDate+"') " +
//                "and  (anniversarydate>= '"+toAniversaryDate+"' and " +
//                " anniversarydate<= '"+fromAniversaryDate+"') ",null);
