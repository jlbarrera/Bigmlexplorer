package com.e.bigmlexplorer;

import android.content.Context;
import android.os.AsyncTask;

import org.json.simple.JSONObject;

import static com.e.bigmlexplorer.ModelDetail.MODEL_NAME;
import static com.e.bigmlexplorer.ModelDetail.MODEL_TYPE;
import static com.e.bigmlexplorer.ModelDetail.PREDICTION;

public class DatabasePredictionTask extends AsyncTask<JSONObject, Integer, Boolean>  {

    private Context mContext;

    public DatabasePredictionTask (Context context){
        mContext = context;
    }

    protected Boolean doInBackground(JSONObject... inputData) {
        PredictionSQLiteOpenHelper db = new PredictionSQLiteOpenHelper(mContext);
        int count = inputData.length;

        for (int i = 0; i < count; i++) {
            db.savePrediction(inputData[i], PREDICTION, MODEL_TYPE, MODEL_NAME);
        }

        return true;
    }

}
