package com.example.eftimis.transport;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class  SQLiteDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "info.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_NAME = "profile";
    public static final String TABLE_NAME_COMPANY = "company";
    public static final String COLUMN_ID =  "userid";
    public static final String COLUMN_FIRSTNAME =  "firstname";
    public static final String COLUMN_LASTNAME =  "lastname";
    public static final String COLUMN_EMAIL =  "email";
    public static final String COLUMN_PASSWORD =  "password";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_MIN_COST = "mincost";
    public static final String COLUMN_COST_KILO = "costkilo";
    public static final String COLUMN_BREAK = "break";
    public static final String COLUMN_FAST = "fast";
    public static final String COLUMN_IMAGE = "image";
    public static  final String COLUMN_NAME = "name";
    public static  final String COLUMN_ADDRESS = "address";

    // User SQL query for registration
    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FIRSTNAME + " TEXT, "+
                    COLUMN_LASTNAME + " TEXT, "+
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT"  + ")";

    // Company Sql query for registration
       private static final String CREATE_TABLE_COMPANY_QUERY
            = "CREATE TABLE"  +  TABLE_NAME_COMPANY  +  " (" +
            COLUMN_NAME  + "TEXT ,"  +
            COLUMN_ADDRESS  + "TEXT," +
            COLUMN_EMAIL +"TEXT,"+
            COLUMN_ID  +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_BREAK  + "TEXT,"+
            COLUMN_COST_KILO +"TEXT,"+
            COLUMN_MIN_COST+"TEXT,"+
            COLUMN_PHONE + "TEXT,"+
            COLUMN_PASSWORD +"TEXT,"+
            COLUMN_IMAGE +"BLOB,"+
            COLUMN_FAST+"TEXT"+")";


    //modified constructor


    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
        sqLiteDatabase.execSQL(CREATE_TABLE_COMPANY_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_COMPANY);
        onCreate(sqLiteDatabase);
    }

    public Cursor getInformations(SQLiteDatabase sqLiteDatabase ){

              String offeres []  = {SQLiteDBHelper.COLUMN_MIN_COST, SQLiteDBHelper.COLUMN_COST_KILO,SQLiteDBHelper.COLUMN_FAST,SQLiteDBHelper.COLUMN_BREAK};

              Cursor  cursor = sqLiteDatabase.query(SQLiteDBHelper.TABLE_NAME_COMPANY,offeres,null,null,null,null,null);

              return cursor;
    }
}
