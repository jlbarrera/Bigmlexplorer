package com.e.bigmlexplorer;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;


import org.bigml.binding.AuthenticationException;
import org.bigml.binding.BigMLClient;
import org.bigml.binding.LocalEnsemble;
import org.bigml.binding.LocalPredictiveModel;
import org.bigml.binding.localmodel.Prediction;
import org.bigml.binding.utils.Utils;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;
import com.e.bigmlexplorer.actionablemodels.*;
import org.bigml.binding.LocalPredictiveModel;

public class ModelDetail extends AppCompatActivity {

    public static String MODEL_ID;
    public static String MODEL_NAME;
    public static String MODEL_TYPE;
    public static String DATASET_ID;
    public static String OBJECTIVE;
    public static LocalPredictiveModel LOCALMODEL;
    public static LocalEnsemble LOCALENSEMBLE;
    public static Map<Integer, String> slider_input_fields = new HashMap<Integer, String>();
    public static Map<Integer, String> categorical_input_fields = new HashMap<Integer, String>();
    public static Map<String, Long> categorical_options_input_fields = new HashMap<String, Long>();
    public static Map<Integer, Object> input_fields = new HashMap<Integer, Object>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_detail);

        // Clear field lists
        categorical_input_fields.clear();
        slider_input_fields.clear();
        categorical_options_input_fields.clear();
        input_fields.clear();

        // Get parameters from Models Activity
        Intent intent = getIntent();
        MODEL_ID = intent.getStringExtra(modelFragment.DETAIL_MODEL);
        MODEL_NAME = intent.getStringExtra(modelFragment.MODEL_NAME);
        MODEL_TYPE = intent.getStringExtra(modelFragment.MODEL_TYPE);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(MODEL_NAME);
        }

        // API call to get model's dataset
        @SuppressLint("StaticFieldLeak") AsyncTask asynctask = new AsyncTask() {
            @Override
            protected JSONObject doInBackground(Object[] objects) {
                BigMLClient api = null;
                JSONObject dataset = null;

                try {
                    api = new BigMLClient(
                            MainActivity.USERNAME,
                            MainActivity.API_KEY,
                            MainActivity.STORAGE
                    );

                    JSONObject model = null;
                    JSONObject model_object = null;
                    if (MODEL_TYPE.equals("Model")) {
                        model = api.getModel(MODEL_ID);
                        model_object = (JSONObject) model.get("object");
                        LOCALMODEL = new LocalPredictiveModel(model);
                    } else if (MODEL_TYPE.equals("Ensemble")) {
                        model = api.getEnsemble(MODEL_ID);
                        model_object = (JSONObject) model.get("object");
                        LOCALENSEMBLE = new LocalEnsemble(api, model);
                    }

                    DATASET_ID = (String) model_object.get("dataset");
                    OBJECTIVE = (String) model_object.get("objective_field_name");
                    dataset = api.getDataset(DATASET_ID);

                } catch (AuthenticationException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return dataset;
            }

            @Override
            protected void onPostExecute(Object o) {
                JSONObject dataset = (JSONObject) o;
                // Read Model's fields and build the view form
                createInputFields(dataset);
            }
        };

        asynctask.execute();
    }


    /**
     * Read dataset fields and types and create input fields in the Activity
     */
    public void createInputFields(JSONObject dataset) {

        JSONObject dataset_object = (JSONObject) dataset.get("object");
        JSONObject objective_field = (JSONObject) dataset_object.get("objective_field");
        String objective_field_id = (String) objective_field.get("id");

        // Get fields from dataset and build the view
        JSONObject fields = (JSONObject) Utils.getJSONObject(
                dataset, "object.fields");

        for (Object k : fields.keySet()) {

            String key = (String) k;
            
            if (fields.get(key) instanceof JSONObject && !objective_field_id.equals(key)) {
                JSONObject field = (JSONObject) fields.get(key);
                int Id = Integer.parseInt(key);
                input_fields.put(Id, null);

                // Common fields values
                String field_type = (String) field.get("optype");
                JSONObject summary = (JSONObject) field.get("summary");
                String label = (String) field.get("label");

                // Numeric fields
                if (field_type.equals("numeric")) {
                    int maximum = 0;
                    int minimum = 0;
                    Object max = summary.get("maximum");
                    Object min = summary.get("minimum");
                    maximum = ((Number) max).intValue();
                    minimum = ((Number) min).intValue();
                    createSlider(Id, 0, maximum, minimum, label);
                    slider_input_fields.put(Id, key);
                }

                // Categorical fields
                if (field_type.equals("categorical")) {
                    JSONArray categories = (JSONArray) summary.get("categories");
                    int len = categories.size();
                    String[] options = new String[len];
                    for (int i = 0; i < len; i++) {
                        JSONArray category = (JSONArray) categories.get(i);
                        Long category_id = (Long) category.get(1);
                        String category_name = (String) category.get((0));
                        categorical_options_input_fields.put(category_name, category_id);
                        options[i] = category_name;
                    }
                    createDropDown(Id, options, label);
                    categorical_input_fields.put(Id, key);
                }

            }

        }

        // Update Objective in the view
        TextView prediction = findViewById(R.id.prediction);
        prediction.setText(OBJECTIVE);

        // Hide the loading
        ProgressBar loading = findViewById(R.id.progressbar_loading);
        loading.setVisibility(View.GONE);
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

        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                try {
                    predict();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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

        dropDown.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                try {
                    predict();
                } catch (Exception e) {
                    e.printStackTrace();
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
     */
    public void predict() throws Exception {

        // Build the JSON Body and input fields
        JSONObject jsonBody = new JSONObject();
        JSONObject inputData = new JSONObject();

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
                Long option_id = categorical_options_input_fields.get(dropdownValue);
                inputData.put(categorical_input_fields.get(key), dropdownValue);
                input_fields.put(key, dropdownValue);
            }
        }
        jsonBody.put("model", MODEL_ID);
        jsonBody.put("input_data", inputData);

        String result = null;
        result = offlinePrediction(inputData);

        TextView prediction = findViewById(R.id.prediction);
        prediction.setText(OBJECTIVE + ": " + result);

    }

    public String offlinePrediction(JSONObject inputData) throws Exception {

        String result = "";
        if (MODEL_TYPE.equals("Model")) {
            Prediction prediction = LOCALMODEL.predict(inputData);
            result = (String) prediction.get("prediction");
        } else if (MODEL_TYPE.equals("Ensemble")) {
            HashMap<String, Object> prediction = LOCALENSEMBLE.predict(inputData, null, null, null, null, null, true, null);
            result = (String) prediction.get("prediction");
        }

        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.download:
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.model_menu, menu);
        return true;
    }


}
