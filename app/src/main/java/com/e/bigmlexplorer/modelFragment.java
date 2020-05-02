package com.e.bigmlexplorer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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


public class modelFragment extends Fragment {

    View view;
    ListView listview;
    SimpleAdapter adapter;
    ArrayList<String> list;
    ProgressBar loading;
    public static String DETAIL_MODEL = "detail_model";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Initialize variables
        view = inflater.inflate(R.layout.fragment_model, container, false);
        listview = view.findViewById(R.id.model_list);
        list = new ArrayList<String>();
        loading = view.findViewById(R.id.progressbar_loading);

        // API call to get model list
        @SuppressLint("StaticFieldLeak") AsyncTask asynctask = new AsyncTask() {
            @Override
            protected List<Map<String, String>> doInBackground(Object[] objects) {
                BigMLClient api = null;
                List<Map<String, String>> model_list_names = null;

                try {
                    api = new BigMLClient(
                            MainActivity.USERNAME,
                            MainActivity.API_KEY,
                            MainActivity.STORAGE
                    );
                    JSONObject models = api.listModels(null);
                    model_list_names = getModelListNames(models);
                } catch (AuthenticationException | JSONException e) {
                    e.printStackTrace();
                }

                return model_list_names;
            }

            @Override
            protected void onPostExecute(Object o) {
                List<Map<String, String>> model_list_names = (List<Map<String, String>>) o;
                loadModelListView(model_list_names);
            }

        };

        asynctask.execute();

        return view;
    }

    /**
     * Parse JSON response and return Model list
     * @return Model list
     */
    public List<Map<String, String>> getModelListNames(JSONObject jsonObject) throws JSONException {

        List<Map<String, String>> model_list_names = new ArrayList<Map<String, String>>();
        JSONArray jsonArray = (JSONArray) jsonObject.get("objects");

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jo = (JSONObject) jsonArray.get(i);
            Boolean ensemble = (Boolean) jo.get("ensemble");
            if (!ensemble) {
                Map<String, String> model_data = new HashMap<String, String>(2);
                model_data.put("title", (String) jo.get("name"));
                model_data.put("subtitle", "Model");
                model_list_names.add(model_data);
                String resource = (String) jo.get("resource");
                list.add(resource.split("/")[1]);
            }
        }

        return model_list_names;
    }

    /**
     * Print model list to the View
     * @param model_list_names
     */
    public void loadModelListView(List<Map<String, String>> model_list_names) {
        adapter = new SimpleAdapter(getActivity(), model_list_names,
                R.layout.model_elem_list,
                new String[]{"title", "subtitle"},
                new int[]{R.id.model_name,
                        R.id.model_type});

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ModelDetail.class);
                intent.putExtra(DETAIL_MODEL, list.get(position));
                startActivity(intent);
            }
        });
        loading.setVisibility(View.GONE);
        listview.setAdapter(adapter);
    }

}

