package com.example.lab9memoriestracker.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.example.lab9memoriestracker.Model.MemoryModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MemoryDB extends SQLiteOpenHelper {
    public static final String DB_NAME="MemoryDB";
    public static final int DB_VERSION=1;
    public final String MEMORY_TABLE="Memory";

    public MemoryDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+MEMORY_TABLE+"(MemoryID INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT UNIQUE ,Title TEXT,Description TEXT,Location TEXT,Date TEXT,Image BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean AddMemory(String Title, String Description, String Location, String Date, Bitmap Image)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
       Image.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] bytearray=stream.toByteArray();
        cv.put("Title",Title);
        cv.put("Description",Description);
        cv.put("Location",Location);
        cv.put("Date",Date);
      cv.put("Image",bytearray);
        try {
            db.insert(MEMORY_TABLE,null,cv);
            db.close();
            return true;
        }
        catch (Exception e) {
            db.close();
            return false;
        }
    }
    public List<MemoryModel> GetMemories()
    {
        List<MemoryModel> memoryModelList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cs=db.rawQuery("SELECT * FROM "+MEMORY_TABLE,null);
        if(cs.moveToFirst())
        {
            while(!cs.isAfterLast())
            {
                memoryModelList.add(new MemoryModel(cs.getInt(cs.getColumnIndex("MemoryID")),cs.getString(cs.getColumnIndex("Title")),
                cs.getString(cs.getColumnIndex("Description")),
                        cs.getString(cs.getColumnIndex("Location")),
                        cs.getString(cs.getColumnIndex("Date")),
                        cs.getBlob(cs.getColumnIndex("Image"))));

                cs.moveToNext();
            }
        }
        cs.close();
        db.close();
        return memoryModelList;
    }
   /* public MemoryModel GetBookByID(int ID)
    {MemoryModel memoryModel=null;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cs=db.rawQuery("SELECT * FROM "+MEMORY_TABLE+" WHERE Book_ID="+ID,null);
        if(cs.moveToFirst())
        {
            memoryModel=new MemoryModel(cs.getInt(cs.getColumnIndex("Book_ID")),
                    cs.getString(cs.getColumnIndex("Cover_Photo")),
                    cs.getString(cs.getColumnIndex("Title")),
                    cs.getString(cs.getColumnIndex("Author")),
                    cs.getString(cs.getColumnIndex("Genre")),
                    cs.getString(cs.getColumnIndex("OLanguage")),
                    cs.getString(cs.getColumnIndex("Description")),
                    cs.getInt(cs.getColumnIndex("PublishedYear")),
                    cs.getInt(cs.getColumnIndex("NumberofPages")));
        }
        return memoryModel;
    }*/
    public void UpdateBook(int MemoryID,String Title, String Description, String Location, String Date, Bitmap Image)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Image.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] bytearray=stream.toByteArray();
        cv.put("Title",Title);
        cv.put("Description",Description);
        cv.put("Location",Location);
        cv.put("Date",Date);
        cv.put("Image",bytearray);

        String where = "MemoryID=?";
        String[] whereArgs = new String[] {String.valueOf(MemoryID)};

        try {
            db.update(MEMORY_TABLE, cv, where, whereArgs);
            db.close();
        } catch (Exception e) {
db.close();
        }
    }
    public void RemoveMemory(int MemoryID)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+MEMORY_TABLE+" WHERE MemoryID="+MemoryID);
        db.close();
    }
}
