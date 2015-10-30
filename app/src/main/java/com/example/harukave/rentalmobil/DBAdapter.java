package com.example.harukave.rentalmobil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HarukaVe on 28-Oct-15.
 */
public class DBAdapter extends SQLiteOpenHelper {
    private static final String DB_NAME = "rental";
    private static final String TABLE_NAME = "pelanggan";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "nama";
    private static final String COL_EMAIL = "email";
    private static final String COL_NOMOR = "nomor";
    private static final String COL_ALAMAT = "alamat";
    private static final String COL_MOBIL = "mobil";
    private static final String COL_HARI = "hari";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS" + TABLE_NAME + ";";

    private SQLiteDatabase sqLiteDatabase = null;

    public DBAdapter(Context context) {

        super(context, DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
    }

    @Override
    public void openDB() {
        if (sqLiteDatabase == null) {
            sqLiteDatabase = getWritableDatabase();
        }
    }

    public void closeDB() {
        if (sqLiteDatabase != null) {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
    }

    public void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + COL_NAME + "TEXT," + COL_EMAIL + "TEXT," + COL_NOMOR + "INTEGER," + COL_ALAMAT + "TEXT," + COL_MOBIL + "TEXT," + COL_HARI + "TEXT);");

    }

    public void save(Pesan pesan) {
        sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, pesan.getNama());
        contentValues.put(COL_EMAIL, pesan.getEmail());
        contentValues.put(COL_NOMOR, pesan.getNomor());
        contentValues.put(COL_ALAMAT, pesan.getAlamat());
        contentValues.put(COL_MOBIL, pesan.getMobil());
        contentValues.put(COL_HARI, pesan.getHari());

        sqLiteDatabase.insertWithOnConflict(TABLE_NAME, null, contentValues, sqLiteDatabase.CONFLICT_IGNORE);

        sqLiteDatabase.close();

    }

    public void updatePesan(Pesan editPesan) {
        sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, editPesan.getNama());
        cv.put(COL_EMAIL, editPesan.getEmail());
        cv.put(COL_NOMOR, editPesan.getNomor());
        cv.put(COL_ALAMAT, editPesan.getAlamat());
        cv.put(COL_MOBIL, editPesan.getMobil());
        cv.put(COL_HARI, editPesan.getHari());

        String whereClause = COL_ID + "==?";
        String whereArgs[] = new String[]{editPesan.getId()};
        sqLiteDatabase.update(TABLE_NAME, cv, whereClause, whereArgs);
        sqLiteDatabase.close();
    }

    public void delete(Pesan pesan) {
        sqLiteDatabase = getWritableDatabase();
        String whereClause = COL_ID + "==?";
        String whereArgs[] = new String[]{String.valueOf(pesan.getId())};
        sqLiteDatabase.delete(TABLE_NAME, whereClause, whereArgs);
        sqLiteDatabase.close();
    }

    public void deleteAll() {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, null, null);
        sqLiteDatabase.close();
    }

    public List<Pesan> getAllPesan() {
        sqLiteDatabase = getWritableDatabase();

        Cursor cursor = this.sqLiteDatabase.query(TABLE_NAME, new String[]{COL_ID, COL_NAME, COL_EMAIL, COL_NOMOR, COL_ALAMAT, COL_MOBIL, COL_HARI}, null, null, null, null, null, null, null, null, null);
        List<Pesan> pesans = new ArrayList< ~ > ();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Pesan pesan = new Pesan();
                pesan.setId(cursor.getString(cursor.getColumnIndex(COL_ID)));
                pesan.setNama(cursor.getString(cursor.getColumnIndex(COL_NAME)));
                pesan.setEmail(cursor.getString(cursor.getColumnIndex(COL_EMAIL)));
                pesan.setNomor(cursor.getString(cursor.getColumnIndex(COL_NOMOR)));
                pesan.setAlamat(cursor.getString(cursor.getColumnIndex(COL_ALAMAT)));
                pesan.setMobil(cursor.getString(cursor.getColumnIndex(COL_MOBIL)));
                pesan.setHari(cursor.getString(cursor.getColumnIndex(COL_HARI)));
                pesan.add(pesan);
            }
            sqLiteDatabase.close();
            return pesans;
        } else {
            sqLiteDatabase.close();
            return new ArrayList<Pesan>();
        }

    }
}
