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
import com.example.samparksuchiapplication.Model.AlbumModel;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import java.util.List;

public class PDFActivity extends AppCompatActivity
   {
    LinearLayout linearLayout;
    List<AlbumModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PDF");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        linearLayout = findViewById(R.id.ll_pdf_activity);

        list = new ArrayList<>();
        getPdfFiles();
    }

    private void getPdfFiles()
    {
        AlbumModel albumModel = new AlbumModel();
        albumModel.setPdfName("Biyani Pdf");
        albumModel.setPosition(1);
        list.add(albumModel);

        albumModel = new AlbumModel();
        albumModel.setPdfName("Choice Pdf");
        albumModel.setPosition(2);
        list.add(albumModel);

        albumModel = new AlbumModel();
        albumModel.setPdfName("MayTaskSheet Pdf");
        albumModel.setPosition(3);
        list.add(albumModel);

        LayoutInflater linflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for ( int i=0;i<list.size();i++) {
            View myView = linflater.inflate(R.layout.pdf_list, null); //here item is the the layout you want to inflate
            TextView name = myView.findViewById(R.id.tv_pdf_file);
            MaterialCardView cardView = myView.findViewById(R.id.cv_pdf);

            name.setText(list.get(i).getPdfName());
            linearLayout.addView(myView);

            final int finalI = i;
            myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PDFActivity.this,ViewPDFActivity.class);
                    intent.putExtra("Position",list.get(finalI).getPosition());
                    startActivity(intent);
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
