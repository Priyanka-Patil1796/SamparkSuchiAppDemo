package com.example.samparksuchiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.github.barteksc.pdfviewer.PDFView;

public class ViewPDFActivity extends AppCompatActivity {
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pdf_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ViewPDF");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        pdfView = findViewById(R.id.pdfView);

        Intent mIntent = getIntent();
        int position = mIntent.getIntExtra("Position", 0);

        if (position==1){
            pdfView.fromAsset("biyani.pdf").load();
        } else if (position==2){
            pdfView.fromAsset("Choice.pdf").load();
        }
        else if (position==3){
            pdfView.fromAsset("MayTaskSheet.pdf").load();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
