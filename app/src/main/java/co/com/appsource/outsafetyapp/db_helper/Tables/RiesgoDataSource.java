package co.com.appsource.outsafetyapp.db_helper.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.com.appsource.outsafetyapp.db_helper.SQLOutsafetyHelper;

/**
 * Created by JANUS on 27/11/2015.
 */
public class RiesgoDataSource {
    // Database fields
    private SQLiteDatabase database;
    private SQLOutsafetyHelper dbHelper;

    private String[] allColumns = {Riesgo.COLUMN_ID
            , Riesgo.COLUMN_ID_RIESGO
            , Riesgo.COLUMN_DESCRIPCION_RIESGO
            , Riesgo.COLUMN_ID_CENTRO_TRABAJO};

    public RiesgoDataSource(Context context) {
        dbHelper = new SQLOutsafetyHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Riesgo CreateRiesgo(String intIdRiesgo, String strDescripcionRiesgo, String strIdCentroTrabajo) {

        ContentValues values = new ContentValues();
        values.put(Riesgo.COLUMN_ID_RIESGO, intIdRiesgo);
        values.put(Riesgo.COLUMN_DESCRIPCION_RIESGO, strDescripcionRiesgo);
        values.put(Riesgo.COLUMN_ID_CENTRO_TRABAJO, strIdCentroTrabajo);

        long insertId = database.insert(Riesgo.NAME
                , null,
                values);
        Cursor cursor = database.query(Riesgo.NAME
                , allColumns
                , Riesgo.COLUMN_ID + " = " + insertId
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Riesgo newRiesgo = cursorToRiesgo(cursor);
        cursor.close();
        return newRiesgo;
    }


    public Riesgo GetRiesgo(String intIdRiesgo) {

        Cursor cursor = database.query(Riesgo.NAME
                , allColumns
                , Riesgo.COLUMN_ID_RIESGO + " = '" + intIdRiesgo + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Riesgo newRiesgo = cursorToRiesgo(cursor);
        cursor.close();
        return newRiesgo;
    }

    private Riesgo cursorToRiesgo(Cursor cursor) {
        Riesgo comment = new Riesgo();

        comment.setId(cursor.getLong(0));
        comment.setIntIdRiesgo(cursor.getString(1));
        comment.setStrDescripcionRiesgo(cursor.getString(2));
        comment.setStrIdCentroTrabajo(cursor.getString(3));

        return comment;
    }

    public void truncateTable() {
        database.execSQL("delete from " + Riesgo.NAME);
    }

    public List<Riesgo> GetAll() {
        List<Riesgo> lstResult = new ArrayList<Riesgo>();
        Cursor cursor = database.query(Riesgo.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Riesgo newItem = cursorToRiesgo(cursor);
            lstResult.add(newItem);
            cursor.moveToNext();
        }
        cursor.close();
        return lstResult;
    }

    public List<Riesgo> GetRiesgosByCentroTrabajo(String strIdCentroTrabajo) {
        List<Riesgo> lstResult = new ArrayList<Riesgo>();
        Cursor cursor = database.query(Riesgo.NAME
                , allColumns
                , Riesgo.COLUMN_ID_CENTRO_TRABAJO + " = '" + strIdCentroTrabajo + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Riesgo newItem = cursorToRiesgo(cursor);
            lstResult.add(newItem);
            cursor.moveToNext();
        }
        cursor.close();
        return lstResult;
    }

    public int GetCount() {
        Cursor cursor = database.query(Riesgo.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);

        return cursor.getCount();
    }

}
