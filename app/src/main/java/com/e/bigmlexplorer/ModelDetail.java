package com.e.bigmlexplorer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;
import com.e.bigmlexplorer.actionablemodels.*;

public class ModelDetail extends AppCompatActivity {

    public static String MODEL_ID;
    public static String DATASET_ID;
    public static String OBJECTIVE;
    public static Boolean OFFLINE;
    public static Map<Integer, String> slider_input_fields = new HashMap<Integer, String>();
    public static Map<Integer, String> categorical_input_fields = new HashMap<Integer, String>();
    public static Map<String, Integer> categorical_options_input_fields = new HashMap<String, Integer>();
    public static Map<Integer, Object> input_fields = new HashMap<Integer, Object>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get parameters from Models Activity
        Intent intent = getIntent();
        MODEL_ID = intent.getStringExtra(modelFragment.DETAIL_MODEL);

        String URL = "https://bigml.io/andromeda/model/" + MODEL_ID + "?username=" + MainActivity.USERNAME + ";api_key=" + MainActivity.API_KEY;
        RequestQueue queue = Volley.newRequestQueue(this);

        // Get dataset id and create input fields and set/unset offline mode
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Parse json Models
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            getSupportActionBar().setTitle(jsonObject.getString("name"));

                            DATASET_ID = jsonObject.getString("dataset").split("/")[1];
                            OBJECTIVE = jsonObject.getString("objective_field_name");
                            OFFLINE = islocalModel(); // If the model is downloaded, we set the offline prediction

                            // Read Model's fields and build the view form
                            createInputFields();

                            // Disable Predict button if offline mode
                            if (OFFLINE) {
                                Button predict_button = findViewById(R.id.predict);
                                predict_button.setEnabled(false);
                                predict_button.setText(R.string.offline_mode);
                            }

                            TextView prediction = findViewById(R.id.prediction);
                            prediction.setText(OBJECTIVE);

                            ProgressBar loading = findViewById(R.id.progressbar_loading);
                            loading.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    /**
     * Read dataset fields and types and create input fields in the Activity
     */
    @SuppressLint("ResourceType")
    public void createInputFields() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "https://bigml.io/andromeda/dataset/" + DATASET_ID + "?username=" + MainActivity.USERNAME + ";api_key=" + MainActivity.API_KEY;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Parse json Models
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject objective_field = jsonObject.getJSONObject("objective_field");
                            JSONObject fields = jsonObject.getJSONObject("fields");
                            Iterator<String> keys = fields.keys();

                            while (keys.hasNext()) {
                                String key = keys.next();
                                if (fields.get(key) instanceof JSONObject && !objective_field.getString("id").equals(key)) {
                                    JSONObject field = (JSONObject) fields.get(key);
                                    int Id = Integer.parseInt(key);
                                    input_fields.put(Id, null);

                                    // Numeric fields
                                    if (field.getString("optype").equals("numeric")) {
                                        int maximum = field.getJSONObject("summary").getInt("maximum");
                                        int minimum = field.getJSONObject("summary").getInt("minimum");
                                        String label = field.getString("label");
                                        createSlider(Id, 0, maximum, minimum, label);
                                        slider_input_fields.put(Id, key);
                                    }

                                    // Categorical fields
                                    if (field.getString("optype").equals("categorical")) {
                                        JSONArray categories = field.getJSONObject("summary").getJSONArray("categories");
                                        int len = categories.length();
                                        String[] options = new String[len];
                                        for (int i = 0; i < len; i++) {
                                            JSONArray category = categories.getJSONArray(i);
                                            int category_id = (int) category.get(1);
                                            String category_name = (String) category.get((0));
                                            categorical_options_input_fields.put(category_name, category_id);
                                            options[i] = category_name;
                                        }
                                        String label = field.getString("label");
                                        createDropDown(Id, options, label);
                                        categorical_input_fields.put(Id, key);
                                    }
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    /**
     * Create slider from numeric datatype
     *
     * @param Id
     * @param stepSize
     * @param maximum
     * @param minimum
     * @param label
     */
    public void createSlider(int Id, int stepSize, int maximum, int minimum, String label) {
        LinearLayout fieldsContainer = findViewById(R.id.fieldsContainer);

        Slider slider = new Slider(this);
        slider.setStepSize(stepSize);
        slider.setValueTo(maximum);
        slider.setValueFrom(minimum);
        slider.setId(Id);
        if (OFFLINE) { // To avoid making to much API request
            slider.addOnChangeListener(new Slider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    predict();
                }
            });
        }
        TextView textview = new TextView(this);
        textview.setText(label);

        fieldsContainer.addView(
                textview,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        fieldsContainer.addView(
                slider,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * Create a dropdown field from categorical data type
     *
     * @param Id
     * @param options
     * @param label
     */
    public void createDropDown(int Id, String[] options, String label) {

        LinearLayout fieldsContainer = findViewById(R.id.fieldsContainer);

        TextInputLayout textInputLayout = new TextInputLayout(ModelDetail.this);
        TextInputLayout.LayoutParams textInputPosition = new TextInputLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                ModelDetail.this,
                R.layout.dropdown_menu_popup_item,
                options);

        final AutoCompleteTextView dropDown = new AutoCompleteTextView(ModelDetail.this);
        dropDown.setInputType(InputType.TYPE_NULL);
        dropDown.setId(Id);
        dropDown.setThreshold(0);
        dropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDown.showDropDown();
            }
        });

        if (OFFLINE) { // To avoid making to much API request
            dropDown.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    predict();
                }

            });
        }

        dropDown.setAdapter(adapter);
        textInputLayout.addView(dropDown, textInputPosition);

        TextView textview = new TextView(this);
        textview.setText(label);

        fieldsContainer.addView(
                textview,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        fieldsContainer.addView(
                textInputLayout,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void startPrediction(View view) {
        predict();
    }

    /**
     * Create a prediction from input values
     *
     */
    public void predict() {

        // Build the JSON Body and input fields
        JSONObject jsonBody = new JSONObject();
        JSONObject inputData = new JSONObject();
        try {
            // Get values from sliders
            LinearLayout sliderContainer = findViewById(R.id.fieldsContainer);
            for (Integer key : slider_input_fields.keySet()) {
                Slider slider = sliderContainer.findViewById(key);
                float sliderValue = slider.getValue();
                if (sliderValue != 0.0) {
                    inputData.put(slider_input_fields.get(key), slider.getValue());
                    input_fields.put(key, slider.getValue());
                }
            }
            // Get values from dropdown (categorical)
            LinearLayout fieldsContainer = findViewById(R.id.fieldsContainer);
            for (Integer key : categorical_input_fields.keySet()) {
                AutoCompleteTextView dropdown = fieldsContainer.findViewById(key);
                String dropdownValue = dropdown.getText().toString();
                if (!dropdownValue.equals("")) {
                    int option_id = categorical_options_input_fields.get(dropdownValue);
                    inputData.put(categorical_input_fields.get(key), option_id);
                    input_fields.put(key, dropdownValue);
                }
            }
            jsonBody.put("model", "model/" + MODEL_ID);
            jsonBody.put("input_data", inputData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (OFFLINE) { // Check if it's a local model
            String result = offlinePrediction();
            TextView prediction = findViewById(R.id.prediction);
            prediction.setText(OBJECTIVE + ": " + result);
        } else {  // Online prediction

            // POST to prediction endpoint
            String URL = "https://bigml.io/andromeda/prediction/?username=" + MainActivity.USERNAME + ";api_key=" + MainActivity.API_KEY;
            final String requestBody = jsonBody.toString().replaceAll("\\\\", "");

            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    // Parse prediction
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String prediction_result = jsonResponse.get("output").toString();
                        String objective_field_name = jsonResponse.get("objective_field_name").toString();
                        TextView prediction = findViewById(R.id.prediction);
                        prediction.setText(objective_field_name + ": " + prediction_result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    TextView prediction = findViewById(R.id.prediction);
                    prediction.setText(error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException error) {
                        return null;
                    }
                }
            };

            queue.add(stringRequest);
        }

    }

    public boolean islocalModel() {
        try {
            String className = "com.e.bigmlexplorer.actionablemodels."+OBJECTIVE;
            Class.forName(className);
            return true;
        } catch( ClassNotFoundException e ) {
            return false;
        }
    }

    public String offlinePrediction() {
        String result = "";

        // Titanic Survival Model
        if (OBJECTIVE.equals("Survived") && input_fields.size() == 4) {
            Double age = input_fields.get(0) == null ?  null: new Double(input_fields.get(0).toString());
            String classDept = input_fields.get(1) == null ?  null: input_fields.get(1).toString();
            Double fareToday = input_fields.get(2) == null ?  null: new Double(input_fields.get(2).toString());
            String joined = input_fields.get(3) == null ?  null: input_fields.get(3).toString();

            result = Survived.predictSurvived(age, classDept, fareToday, joined);
        }

        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
