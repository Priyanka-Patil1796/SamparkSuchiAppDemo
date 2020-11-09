package com.example.samparksuchiapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowIndivitualRecordAcitivity extends AppCompatActivity  {
    TextView tvName, tvCity, tvAddress, tvOccupation, tvContactNumber, tvEmailAddress, tvBirthDate, tvAnniversaryDate;
    CircleImageView imageView;
    // CardView cardView;
    String photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_indivitual_result_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        initUI();
        //cardView.setBackgroundResource(R.drawable.card_edge1);

        Bundle bundle = getIntent().getExtras();
        String recID = bundle.getString("RecordId");
        String name = bundle.getString("ContactName");
        String memberCode = bundle.getString("MemberCode");
        String city = bundle.getString("City");
        String address = bundle.getString("Address");
        String occupation = bundle.getString("Occupation");
        final String phoneNumber = bundle.getString("PhoneNumber");
        String phoneNumber1 = bundle.getString("PhoneNumber1");
        String emailId = bundle.getString("EmailId");
        String birthDate = bundle.getString("BirthDate");
        String anniversaryDate = bundle.getString("AnniversaryDate");
        photoUrl = bundle.getString("Photo");


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowIndivitualRecordAcitivity.this, ViewPhotoActivity.class);
                intent.putExtra("ImagePath", photoUrl);
                startActivity(intent);
            }
        });

        try {
            if (photoUrl.equals("")) {
            } else {
//////                AsyncGettingBitmapFromUrl asyncGettingBitmapFromUrl = new AsyncGettingBitmapFromUrl();
//////                new AsyncGettingBitmapFromUrl().execute(photoUrl);
////
                new AsyncTaskLoadImage(imageView,photoUrl).execute();
////                // Picasso.get().load(photoUrl).placeholder(R.drawable.biyani).into(imageView);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "" + e.toString(), Toast.LENGTH_SHORT).show();
        }

        try {
            tvName.setText(name);
            if (city.equals("null") || city.equals(null) || city.equals("")) {
                tvCity.setText("-");
            } else {
                tvCity.setText(address);
            }
            if (address.equals("null") || address.equals(null) || address.equals("")) {
                tvAddress.setText("-");
            } else {
                tvAddress.setText(address);
            }
            if (occupation.equals("null") || occupation.equals(null) || occupation.equals("")) {
                tvOccupation.setText("-");
            } else {
                tvOccupation.setText(occupation);
            }

            if (phoneNumber != null && phoneNumber1 != null) {
                tvContactNumber.setText(phoneNumber + "\n" + phoneNumber1);
            } else if (phoneNumber != null && phoneNumber1 == null) {
                tvContactNumber.setText(phoneNumber);
            } else if (phoneNumber == null && phoneNumber1 != null) {
                tvContactNumber.setText(phoneNumber1);
            } else {
                tvContactNumber.setText("-");
            }
            if (emailId.equals("null") || emailId.equals(null) || emailId.equals("")) {
                tvEmailAddress.setText("-");
            } else {
                tvEmailAddress.setText(emailId);
            }
            if (birthDate.equals("null") || birthDate.equals(null) || birthDate.equals("")) {
                tvBirthDate.setText("-");
            } else {
                // tvBirthDate.setText(birthDate);

                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                String inputDateStr = birthDate;
                Date date = inputFormat.parse(inputDateStr);
                String BirthdayDate = outputFormat.format(date);
                tvBirthDate.setText(BirthdayDate);
            }
            if (anniversaryDate.equals("null") || anniversaryDate.equals(null) || anniversaryDate.equals("")) {
                tvAnniversaryDate.setText("-");
            } else {
                //tvAnniversaryDate.setText(anniversaryDate);

                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                String inputDateStr = anniversaryDate;
                Date date = inputFormat.parse(inputDateStr);
                String AnniversaryDate = outputFormat.format(date);
                tvAnniversaryDate.setText(AnniversaryDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        tvName = findViewById(R.id.tv_name);
        tvCity = findViewById(R.id.tv_city);
        tvAddress = findViewById(R.id.tv_address);
        tvOccupation = findViewById(R.id.tv_occupation);
        tvContactNumber = findViewById(R.id.tv_contact_number);
        tvEmailAddress = findViewById(R.id.tv_email_address);
        tvBirthDate = findViewById(R.id.tv_birth_day);
        tvAnniversaryDate = findViewById(R.id.tv_anniversary_date);
        imageView = findViewById(R.id.iv_display_image);
        // cardView = findViewById(R.id.cv_in);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }


    private class AsyncGettingBitmapFromUrl extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            System.out.println("doInBackground");
            Bitmap bitmap = null;
            bitmap = downloadImage(photoUrl);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            System.out.println("bitmap" + bitmap);
        }
    }

    public static  Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            stream = getHttpConnection(url);
            bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
            stream.close();
        }
        catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("downloadImage"+ e1.toString());
        }
        return bitmap;
    }

    public static  InputStream getHttpConnection(String urlString)  throws IOException {

        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("downloadImage" + ex.toString());
        }
        return stream;
    }


    public class AsyncTaskLoadImage extends AsyncTask<String, String, Bitmap> {
        private final static String TAG = "AsyncTaskLoadImage";
        private ImageView imageView;
        private String photoUrl;

        public AsyncTaskLoadImage(ImageView imageView,String photoUrl) {
            this.imageView = imageView;
            this.photoUrl = photoUrl;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(photoUrl);
                BitmapFactory.Options bfo = new BitmapFactory.Options();
                bfo.outWidth = 620;
                bfo.outHeight = 350;
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, bfo);
                //bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            try {
                int width, height;
                height = bitmap.getHeight();
                width = bitmap.getWidth();

                Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(bmpGrayscale);
                Paint paint = new Paint();
                ColorMatrix cm = new ColorMatrix();
                cm.setSaturation(0);
                ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
                paint.setColorFilter(f);
                c.drawBitmap(bitmap, 0, 0, paint);
                imageView.setImageBitmap(bmpGrayscale);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}