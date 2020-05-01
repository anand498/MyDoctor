package com.example.mydoctor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;


class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "PatientData.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "patient";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_MEDICINE = "medicine";
    private static final String COLUMN_SYMPTOMS = "symptoms";
    private static final String COLUMN_REACTION = "reaction";
    private static final String COLUMN_COMMENTS = "comments";


    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String date= UpdateActivity.getdate();
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_AGE + " TEXT, " +
                COLUMN_PHONE + " TEXT," +
                COLUMN_ADDRESS + " TEXT, " + COLUMN_DATE+ " TEXT, " +
                COLUMN_SYMPTOMS + " TEXT, " +COLUMN_MEDICINE + " TEXT, " +COLUMN_REACTION + " TEXT, " +
                COLUMN_COMMENTS + " TEXT );";
        db.execSQL(query);
    }

    public String checkAlreadyExist(SQLiteDatabase db,String phone,String name)
    {
//
        Cursor cursor = db.rawQuery("select * from " +
                    TABLE_NAME + " where " + COLUMN_PHONE + " = ? AND " + COLUMN_NAME + " = ?   ",
            new String[] {phone,name });
        String c=cursor.toString();
        Log.e("check",c);
//        Cursor cursor = myDB.query(TABLE_NAME, q, COLUMN_PHONE + "=?",
//                new String[] { phone }+" AND ", null, null, null, null);
//        Cursor cursor = myDB.query(TABLE_NAME, q, COLUMN_NAME + "=?",
//                new String[] { phone }, null, null, null, null);
        if (cursor.getCount() > 0)
        {
            return "exists";
        }
        else
            return "no-entry";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    void addPatient(String name, String age, String phone, String address, String symptoms, String medicine, String reaction,
                 String comments){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_DATE,UpdateActivity.getdate());
        cv.put(COLUMN_SYMPTOMS, symptoms);
        cv.put(COLUMN_MEDICINE, medicine);
        cv.put(COLUMN_REACTION, reaction);
        cv.put(COLUMN_COMMENTS, comments);
        String check =checkAlreadyExist(db,phone,name);
        if(check== "exists")
        {
            Toast.makeText(context, "Entry already exists", Toast.LENGTH_SHORT).show();
            Log.e("existence","entry is there");
        }
        else {
            long result = db.insert(TABLE_NAME, null, cv);
            if (result == -1) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    Cursor readAllData(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db!=null){
              cursor =  db.query(TABLE_NAME, null, null,
                    null, null, null, COLUMN_NAME + " ASC", null);
            int count=cursor.getCount();
            Log.e("cursor count",String.valueOf(count));
        }

        return cursor;

    }




    void updateData(String row_id, String name, String age, String phone, String address,String date, String symptoms, String medicine, String reaction,
                    String comments){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_SYMPTOMS, symptoms);
        cv.put(COLUMN_DATE,date);
        cv.put(COLUMN_MEDICINE, medicine);
        cv.put(COLUMN_REACTION, reaction);
        cv.put(COLUMN_COMMENTS, comments);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readData(String searchname){
        String text= searchname;

        SQLiteDatabase myDB = this.getReadableDatabase();
        String[] q={COLUMN_ID, COLUMN_NAME,COLUMN_AGE,COLUMN_PHONE,COLUMN_ADDRESS,COLUMN_DATE,COLUMN_SYMPTOMS,COLUMN_MEDICINE,COLUMN_REACTION,COLUMN_COMMENTS};
        Cursor cursor = myDB.query(TABLE_NAME, q, COLUMN_NAME + "=?",
                new String[] { searchname }, null, null, null, null);
        return cursor;
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        context.deleteDatabase(DATABASE_NAME);
//        db.execSQL("DELETE FROM " + TABLE_NAME);
    }



}
