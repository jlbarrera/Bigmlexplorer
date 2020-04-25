package com.e.bigmlexplorer;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;

public class ModelDetail extends AppCompatActivity {

    public static String MODEL_ID;
    public static String DATASET_ID;
    public static Map<Integer, String> slider_input_fields = new HashMap<Integer, String>();
    public static Map<Integer, String> categorical_input_fields = new HashMap<Integer, String>();
    public static Map<String, Integer> categorical_options_input_fields = new HashMap<String, Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_detail);

        getSupportActionBar().setTitle("Model detail");

        // Get parameters from Models Activity
        Intent intent = getIntent();
        MODEL_ID = intent.getStringExtra(modelFragment.DETAIL_MODEL);

        String URL = "https://bigml.io/andromeda/model/" + MODEL_ID + "?username=" + MainActivity.USERNAME + ";api_key=" + MainActivity.API_KEY;
        RequestQueue queue = Volley.newRequestQueue(this);

        // Get dataset id and create input fields
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Parse json Models
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            TextView text_model_name = findViewById(R.id.modelname);
                            text_model_name.setText(jsonObject.getString("name"));

                            DATASET_ID = jsonObject.getString("dataset").split("/")[1];
                            createInputFields();

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

                                    // Numeric fields
                                    if (field.getString("optype").equals("numeric")) {
                                        int maximum = field.getJSONObject("summary").getInt("maximum");
                                        int minimum = field.getJSONObject("summary").getInt("minimum");
                                        String label = field.getString("label");
                                        createSlider(Id, 1, maximum, minimum, label);
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
        dropDown.setId(Id);
        dropDown.setThreshold(0);
        dropDown.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {
                            dropDown.showDropDown();
                        }
                    }
                });
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

    /**
     * Create a prediction from input values
     *
     * @param view
     */
    public void predict(View view) {

        // Build the JSON Body
        JSONObject jsonBody = new JSONObject();
        JSONObject inputData = new JSONObject();
        try {
            // Get values from sliders
            LinearLayout sliderContainer = findViewById(R.id.fieldsContainer);
            for (Integer key : slider_input_fields.keySet()) {
                @SuppressLint("ResourceType") Slider slider = sliderContainer.findViewById(key);
                inputData.put(slider_input_fields.get(key), slider.getValue());
            }
            // Get values from dropdown (categorical)
            LinearLayout fieldsContainer = findViewById(R.id.fieldsContainer);
            for (Integer key : categorical_input_fields.keySet()) {
                AutoCompleteTextView dropdown = fieldsContainer.findViewById(key);
                int option_id = categorical_options_input_fields.get(dropdown.getText().toString());
                inputData.put(categorical_input_fields.get(key), option_id);
            }
            jsonBody.put("model", "model/" + MODEL_ID);
            jsonBody.put("input_data", inputData);
        } catch (JSONException e) {
            e.printStackTrace();
        }


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
