package com.e.bigmlexplorer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class modelFragment extends Fragment {

    View view;
    ListView listview;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    public static String DETAIL_MODEL = "detail_model";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_model, container, false);
        listview = view.findViewById(R.id.model_list);
        list = new ArrayList<String>();

        // Instantiate the RequestQueue to models endpoint
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String URL = "https://bigml.io/andromeda/model?username="+MainActivity.USERNAME+";api_key="+MainActivity.API_KEY;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ArrayList<String> model_list_names = getModelListNames(response);
                        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, model_list_names);
                        listview.setAdapter(adapter);

                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getActivity(), ModelDetail.class);
                                intent.putExtra(DETAIL_MODEL, list.get(position));
                                startActivity(intent);
                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

        return view;
    }

    /**
     * Parse JSON response and return Model list
     * @return Model list
     */
    public ArrayList<String> getModelListNames(String response) {

        ArrayList<String> model_list_names = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("objects");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                list.add(jo.getString("resource").split("/")[1]);
                model_list_names.add(jo.getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return model_list_names;
    }
}

