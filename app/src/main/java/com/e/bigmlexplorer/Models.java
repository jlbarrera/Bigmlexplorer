package com.e.bigmlexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Models extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_models);

        getSupportActionBar().setTitle("Models");

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update:
                finish();
                startActivity(getIntent());
                return true;
            case R.id.saved_predictions:
                Intent intent_prediction = new Intent(this, SavedPredictions.class);
                startActivity(intent_prediction);
                return true;
            case R.id.settings:
                Intent intent_settings = new Intent(this, MainActivity.class);
                startActivity(intent_settings);
                return true;
            case R.id.logout:
                /* DO DELETE */
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
