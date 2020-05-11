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
    PredictionAdapter adapter;
    ProgressBar loading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Initialize variables
        view = inflater.inflate(R.layout.fragment_prediction, container, false);
        listview = view.findViewById(R.id.prediction_list);
        loading = view.findViewById(R.id.progressbar_loading);
        List<Map<String, String>> prediction_list = new ArrayList<Map<String, String>>();

        PredictionSQLiteOpenHelper prediction_db = new PredictionSQLiteOpenHelper(getActivity());
        Cursor local_predictions = prediction_db.getPredictions();
        if (local_predictions.moveToFirst()) {
            do {
                Map<String, String> prediction_data = new HashMap<String, String>(2);
                prediction_data.put("id", local_predictions.getString(0));
                prediction_data.put("prediction_result", local_predictions.getString(2));
                prediction_data.put("date", local_predictions.getString(4));
                prediction_list.add(prediction_data);
            } while (local_predictions.moveToNext());
        }

        adapter = new PredictionAdapter(getActivity(), prediction_list);
        loading.setVisibility(View.GONE);
        listview.setAdapter(adapter);

        return view;
    }



}

