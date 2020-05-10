package com.e.bigmlexplorer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import org.bigml.binding.AuthenticationException;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.bigml.binding.BigMLClient;


public class predictionFragment extends Fragment {

    View view;
    ListView listview;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    ProgressBar loading;
    public static String DETAIL_MODEL = "detail_model";
    public static String MODEL_NAME = "model_name";
    public static String MODEL_TYPE = "model_type";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Initialize variables
        view = inflater.inflate(R.layout.fragment_prediction, container, false);
        listview = view.findViewById(R.id.prediction_list);
        list = new ArrayList<String>();
        loading = view.findViewById(R.id.progressbar_loading);

        PredictionSQLiteOpenHelper prediction_db = new PredictionSQLiteOpenHelper(getActivity());
        Cursor local_predictions = prediction_db.getPredictions();
        if (local_predictions.moveToFirst()) {
            do {
                list.add(local_predictions.getString(4));
            } while (local_predictions.moveToNext());
        }

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        loading.setVisibility(View.GONE);
        listview.setAdapter(adapter);

        return view;
    }

}

