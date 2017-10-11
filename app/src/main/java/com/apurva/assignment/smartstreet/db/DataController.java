package com.apurva.assignment.smartstreet.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.apurva.assignment.smartstreet.entity.SmartStreetLight;
import com.apurva.assignment.smartstreet.tables.LightInformationTable;


public class DataController {
    private static final String DATABASE_NAME = "SmartStreetAssignment.mDb";
    private static final int DATABASE_VERSION = 1;
    private DataBaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    public DataController(Context context) {
        mDbHelper = new DataBaseHelper(context);
    }

    public void open() {
        mDb = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    private static final String SMART_TREE_TABLE_NAME = LightInformationTable.getTableName();

    private static final String CREATE_TABLE_SMART_TREE = "CREATE TABLE " + SMART_TREE_TABLE_NAME + " ("
            + LightInformationTable.LIGHT_ID.getColumnName() + " " + LightInformationTable.LIGHT_ID.getColumnDataType()
            + ", " +
            LightInformationTable.NAME.getColumnName() + " " + LightInformationTable.NAME.getColumnDataType()
            + ", " +
            LightInformationTable.DESCRIPTION.getColumnName() + " " + LightInformationTable.DESCRIPTION.getColumnDataType()
            + ", " +
            LightInformationTable.INSTALLATION_DATE.getColumnName() + " " + LightInformationTable.INSTALLATION_DATE.getColumnDataType()
            + ", " +
            LightInformationTable.LATITUDE.getColumnName() + " " + LightInformationTable.LONGITUDE.getColumnDataType()
            + ", " +
            LightInformationTable.LONGITUDE.getColumnName() + " " + LightInformationTable.LATITUDE.getColumnDataType()
            + ");";
    private static final String DROP_TABLE_SMART_TREE = "DROP TABLE IF EXISTS " + SMART_TREE_TABLE_NAME;

    private static final String COMMENT_TABLE_NAME = "CommentTable";
    private static final String COLUMN_COMMENT = "comment";

    private static final String CREATE_TABLE_COMMENT = "CREATE TABLE " + COMMENT_TABLE_NAME + " ("
            + COLUMN_COMMENT + " TEXT NOT NULL);";
    private static final String DROP_TABLE_COMMENT = "DROP TABLE IF EXISTS " + COMMENT_TABLE_NAME;


    public long insertTreeData(int id, String name, String description, String installation_date, String latitude, String longitude) {
        ContentValues content = new ContentValues();
        content.put(LightInformationTable.LIGHT_ID.getColumnName() , id);
        content.put(LightInformationTable.NAME.getColumnName() , name);
        content.put(LightInformationTable.DESCRIPTION.getColumnName() , description);
        content.put(LightInformationTable.INSTALLATION_DATE.getColumnName() , installation_date);
        content.put(LightInformationTable.LATITUDE.getColumnName() , latitude);
        content.put(LightInformationTable.LONGITUDE.getColumnName() , longitude);
        return mDb.insertOrThrow(SMART_TREE_TABLE_NAME, null, content);
    }

    public SmartStreetLight getData(String name) {
        String selectQuery = "SELECT  * FROM " + LightInformationTable.getTableName()+ " WHERE "
                              + LightInformationTable.NAME.getColumnName() + " = '" + name + "';";
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        SmartStreetLight light = null;
        String[] data = null;
        if (cursor.moveToFirst()) {
            do {
                int lightId = cursor.getInt(0);
                String nameIn = cursor.getString(1);
                String description = cursor.getString(2);
                String installationDate = cursor.getString(3);
                String latitude = cursor.getString(4);
                String longitude = cursor.getString(5);

                light = new SmartStreetLight(lightId, nameIn, description, installationDate, latitude, longitude);
            } while (cursor.moveToNext());
        }
        db.close();
        return light;
    }

    public long insertComment(String comment) {
        ContentValues content = new ContentValues();
        content.put(COLUMN_COMMENT, comment);
        return mDb.insertOrThrow(COMMENT_TABLE_NAME, null, content);
    }

    private static class DataBaseHelper extends SQLiteOpenHelper {
        public DataBaseHelper(Context context) {
            super(context,DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE_SMART_TREE);
                db.execSQL(CREATE_TABLE_COMMENT);

                String insertStringPrefix = "INSERT INTO " + LightInformationTable.getTableName() +
                        " (" + LightInformationTable.LIGHT_ID.getColumnName() + ","
                        + LightInformationTable.NAME.getColumnName() + ","
                        + LightInformationTable.DESCRIPTION.getColumnName() + ","
                        + LightInformationTable.INSTALLATION_DATE.getColumnName() + ","
                        + LightInformationTable.LATITUDE.getColumnName() + ","
                        + LightInformationTable.LONGITUDE.getColumnName() +
                        ") values(";

                for(int i= 0; i<1; i++) {
                    String insertString = insertStringPrefix + "1,'Street Light 1','VA Palo Alto Health Care','2017-08-09','37.3947053','-122.1571566');";
                    db.execSQL(insertString);
                }

                db.execSQL(insertStringPrefix + "2,'Street Light 2','Henry M Gunn High School','2017-08-09','37.3947053','-122.1571566');");
                db.execSQL(insertStringPrefix + "3,'Street Light 3','Santa Rita Elementary School','2017-08-09','37.4013876','-122.1398188');");
                db.execSQL(insertStringPrefix + "4,'Street Light 4','Tesla Motors HQ','2017-08-09','37.3947053','-122.1571566');");
                db.execSQL(insertStringPrefix + "5,'Street Light 5','Electric Power Research Institute (EPRI)','2017-08-09','37.3986261','-122.1418787');");
                db.execSQL(insertStringPrefix + "6,'Street Light 6','Terman Middle School','2017-08-09','@37.4013876','-122.1398188');");
                db.execSQL(insertStringPrefix + "7,'Street Light 7','Gardner Bullis Elementary School','2017-08-09','37.3881247','-122.1444537');");
                db.execSQL(insertStringPrefix + "8,'Street Light 8','Pinewood School-Upper Campus,'2017-08-09','37.3881247','-122.1444537');");
                db.execSQL(insertStringPrefix + "9,'Street Light 9','Safeway','2017-08-09','37.3881247',',-122.1444537');");

            } catch(SQLiteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE_SMART_TREE);
            db.execSQL(DROP_TABLE_COMMENT);
            onCreate(db);
        }
    }
}