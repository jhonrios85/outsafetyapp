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
public class InspeccionDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLOutsafetyHelper dbHelper;

    private String[] allColumns = {Inspeccion.COLUMN_ID
            , Inspeccion.COLUMN_ID_EMPRESA
            , Inspeccion.COLUMN_ID_INSPECCION
            , Inspeccion.COLUMN_DESC_INSPECCION
            , Inspeccion.COLUMN_ID_RIESGO};

    public InspeccionDataSource(Context context) {
        dbHelper = new SQLOutsafetyHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Inspeccion CreateInspeccion(String intIdEmpresa, String intIdInspeccion, String strDescripcionInspeccion, String intIdRiesgo) {

        ContentValues values = new ContentValues();
        values.put(Inspeccion.COLUMN_ID_EMPRESA, intIdEmpresa);
        values.put(Inspeccion.COLUMN_ID_INSPECCION, intIdInspeccion);
        values.put(Inspeccion.COLUMN_DESC_INSPECCION, strDescripcionInspeccion);
        values.put(Inspeccion.COLUMN_ID_RIESGO, intIdRiesgo);

        long insertId = database.insert(Inspeccion.NAME
                , null,
                values);
        Cursor cursor = database.query(Inspeccion.NAME
                , allColumns
                , Inspeccion.COLUMN_ID + " = " + insertId
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Inspeccion newInspeccion = cursorToInspeccion(cursor);
        cursor.close();
        return newInspeccion;
    }


    public Inspeccion GetInspeccion(String intIdInspeccion) {

        Cursor cursor = database.query(Inspeccion.NAME
                , allColumns
                , Inspeccion.COLUMN_ID_INSPECCION + " = '" + intIdInspeccion + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Inspeccion newInspeccion = cursorToInspeccion(cursor);
        cursor.close();
        return newInspeccion;
    }

    private Inspeccion cursorToInspeccion(Cursor cursor) {
        Inspeccion comment = new Inspeccion();

        comment.setId(cursor.getLong(0));
        comment.setIntIdEmpresa(cursor.getString(1));
        comment.setIntIdInspeccion(cursor.getString(2));
        comment.setStrDescripcionInspeccion(cursor.getString(3));
        comment.setIntIdRiesgo(cursor.getString(4));

        return comment;
    }

    public void truncateTable() {
        database.execSQL("delete from " + Inspeccion.NAME);
    }

    public List<Inspeccion> GetAll() {
        List<Inspeccion> lstResult = new ArrayList<Inspeccion>();
        Cursor cursor = database.query(Inspeccion.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Inspeccion newItem = cursorToInspeccion(cursor);
            lstResult.add(newItem);
            cursor.moveToNext();
        }
        cursor.close();
        return lstResult;
    }

    public List<Inspeccion> GetInspeccionesByCentroTrabajo(String strIdCentroTrabajo) {
        List<Inspeccion> lstResult = new ArrayList<Inspeccion>();
        Cursor cursor = database.query(Inspeccion.NAME
                , allColumns
                , Inspeccion.COLUMN_ID_EMPRESA + " = '" + strIdCentroTrabajo + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Inspeccion newItem = cursorToInspeccion(cursor);
            lstResult.add(newItem);
            cursor.moveToNext();
        }
        cursor.close();
        return lstResult;
    }

    public int GetCount() {
        Cursor cursor = database.query(Inspeccion.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);

        return cursor.getCount();
    }
}
