package com.e.bigmlexplorer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.gesture.Prediction;

import androidx.annotation.Nullable;

import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PredictionSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Predictions.db";
    private static final String PREDICTION_TABLE = "predictions";
    private static final String PREDICTION_ID = "id";
    private static final String TYPE = "type";
    private static final String PREDICTION = "prediction";
    private static final String INPUT_DATA = "input_data";
    private static final String DATE = "date";
    private static final String MODEL = "model";
    private static final String[] PREDICTION_COLUMNS = {PREDICTION_ID, TYPE, PREDICTION, INPUT_DATA, DATE, MODEL};

    public PredictionSQLiteOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PREDICTION_TABLE + "(" +
                "" + PREDICTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + TYPE + " TEXT,  " +
                "" + PREDICTION + " TEXT, " +
                "" + INPUT_DATA + " TEXT, " +
                "" + DATE + " DATETIME, " +
                "" + MODEL + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PREDICTION_TABLE);
        db.execSQL("CREATE TABLE " + PREDICTION_TABLE + "(" +
                "" + PREDICTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + TYPE + " TEXT,  " +
                "" + PREDICTION + " TEXT, " +
                "" + INPUT_DATA + " TEXT, " +
                "" + DATE + " DATETIME, " +
                "" + MODEL + " TEXT)"
        );
    }

    public boolean savePrediction(JSONObject inputData, String prediction, String type, String model) {
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        ContentValues new_prediction = new ContentValues();
        new_prediction.put(TYPE, type);
        new_prediction.put(PREDICTION, prediction);
        new_prediction.put(INPUT_DATA, inputData.toString());
        new_prediction.put(DATE, dateFormat.format(date));
        new_prediction.put(MODEL, model);

        long row = db.insert(PREDICTION_TABLE, null, new_prediction);

        boolean result = (row != -1) ? true : false;
        return result;

    }

    public Cursor getPredictions() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor predictions = db.query(PREDICTION_TABLE,PREDICTION_COLUMNS, null,null, null, null, null);
        return predictions;
    }

    public boolean deletePrediction(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String where = PREDICTION_ID + " == " + id;
        return (db.delete(PREDICTION_TABLE, where, null)) > 0;
    }

}
