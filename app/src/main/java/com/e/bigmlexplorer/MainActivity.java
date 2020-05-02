package com.e.bigmlexplorer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static SharedPreferences sharedpreferences;
    public static String PREFERENCES = "preferences";
    public static String USERNAME_LABEL = "username";
    public static String API_KEY_LABEL  = "api_key";
    public static String USERNAME = "";
    public static String API_KEY = "";
    public static String STORAGE = "./storage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize username and api key from Shared Preferences
        SharedPreferences sharedPref = getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE);
        USERNAME = sharedPref.getString(MainActivity.USERNAME_LABEL, "");
        API_KEY = sharedPref.getString(MainActivity.API_KEY_LABEL, "");

        if (!USERNAME.equals("") && !API_KEY.equals("")) {
            Intent intent = new Intent(MainActivity.this, Models.class);
            startActivity(intent);
        }
        
    }

    /**
     * Login
     * @param view
     */
    public void login(View view) {

        SaveCredentials(); // Save username and api key

        // Open Models activity
        Intent intent = new Intent(MainActivity.this, Models.class);
        startActivity(intent);
    }

    /**
     * Save username and api Key in Shared Preferences
     */
    private void SaveCredentials() {
        MainActivity.sharedpreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor sp_editor = MainActivity.sharedpreferences.edit();

        EditText current_username = findViewById(R.id.username);
        EditText current_api_key = findViewById(R.id.apikey);

        USERNAME = current_username.getText().toString();
        API_KEY = current_api_key.getText().toString();

        sp_editor.putString(USERNAME_LABEL, USERNAME);
        sp_editor.putString(API_KEY_LABEL, API_KEY);
        sp_editor.commit();
    }
}
