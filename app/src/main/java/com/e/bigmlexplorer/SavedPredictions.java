package com.e.bigmlexplorer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class SavedPredictions extends AppCompatActivity {

    final String fileName = "Predictions.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.local_predictions);
        }

        setContentView(R.layout.activity_saved_predictions);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.share:
                try {
                    writeToCSV();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                shareToCSV();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.predictions_menu, menu);
        return true;
    }

    public void writeToCSV() throws IOException {
        String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        String filePath = baseDir + File.separator + fileName;

        CSVWriter writer;

        FileWriter mFileWriter = new FileWriter(filePath);;


        writer = new CSVWriter(mFileWriter);

        String[] header = {"id", "prediction_result", "date"};
        writer.writeNext(header);

        PredictionSQLiteOpenHelper prediction_db = new PredictionSQLiteOpenHelper(this);
        Cursor local_predictions = prediction_db.getPredictions();
        if (local_predictions.moveToFirst()) {
            do {
                String[] data = {
                    local_predictions.getString(0),
                    local_predictions.getString(2),
                        local_predictions.getString(4)
                };

                writer.writeNext(data);

            } while (local_predictions.moveToNext());
        }

        writer.close();
    }

    public void shareToCSV() {
        String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        String filePath = baseDir + File.separator + fileName;
        Intent sharingIntent = new Intent();
        sharingIntent.setAction(Intent.ACTION_SEND);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse( filePath ) ) ;
        sharingIntent.setType("text/csv");
        startActivity(Intent.createChooser(sharingIntent, "share file with"));
    }
}