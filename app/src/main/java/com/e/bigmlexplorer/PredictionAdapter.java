package com.e.bigmlexplorer;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class PredictionAdapter extends BaseAdapter {

    private List<Map<String, String>> predictions;
    private Context context;

    //public constructor
    public PredictionAdapter(Context context, List<Map<String, String>> predictions) {
        this.context = context;
        this.predictions = predictions;
    }

    @Override
    public int getCount() {
        return predictions.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.prediction_elem_list, parent, false);
        }

        // get current item to be displayed
        final Map<String, String> prediction = predictions.get(position);

        // get the TextView for item name and item description
        TextView textViewItemName = convertView.findViewById(R.id.prediction_result);
        TextView textViewItemDescription = convertView.findViewById(R.id.prediction_date);

        //sets the text for item name and item description from the current item object
        textViewItemName.setText(prediction.get("prediction_result"));
        textViewItemDescription.setText(prediction.get("date"));


        final ImageButton delete = convertView.findViewById(R.id.delete_btn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PredictionSQLiteOpenHelper db = new PredictionSQLiteOpenHelper(context);
                int id = Integer.parseInt(prediction.get("id"));
                if (db.deletePrediction(id)) {
                    predictions.remove(position);
                    notifyDataSetChanged();
                }

            }});

        return convertView;
    }
}
