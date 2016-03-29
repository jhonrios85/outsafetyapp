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
 * Created by JANUS on 26/11/2015.
 */
public class ParametroDataSource {
    // Database fields
    private SQLiteDatabase database;
    private SQLOutsafetyHelper dbHelper;

    private String[] allColumns = {Parametro.COLUMN_ID
            , Parametro.COLUMN_ID_PARAMETRO
            , Parametro.COLUMN_ID_INSPECCION
            , Parametro.COLUMN_ID_RIESGO
            , Parametro.COLUMN_DESC_PARAMETRO
            , Parametro.COLUMN_RUTA_IMAGEN
            , Parametro.COLUMN_ID_EMPRESA
            , Parametro.COLUMN_FEC_USER
            , Parametro.COLUMN_CUMPLE};

    public ParametroDataSource(Context context) {
        dbHelper = new SQLOutsafetyHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Parametro CreateParametro(String intIdParametro
            , String intIdInspeccion
            , String intIdRiesgo
            , String strDescripcionParametro
            , String strRutaImagen
            , String intIdEmpresa
            , String dtfecuser
            , String boolCumple) {

        ContentValues values = new ContentValues();
        values.put(Parametro.COLUMN_ID_PARAMETRO, intIdParametro);
        values.put(Parametro.COLUMN_ID_INSPECCION, intIdInspeccion);
        values.put(Parametro.COLUMN_ID_RIESGO, intIdRiesgo);
        values.put(Parametro.COLUMN_DESC_PARAMETRO, strDescripcionParametro);
        values.put(Parametro.COLUMN_RUTA_IMAGEN, strRutaImagen);
        values.put(Parametro.COLUMN_ID_EMPRESA, intIdEmpresa);
        values.put(Parametro.COLUMN_FEC_USER, dtfecuser);
        values.put(Parametro.COLUMN_CUMPLE, boolCumple);

        long insertId = database.insert(Parametro.NAME
                , null,
                values);
        Cursor cursor = database.query(Parametro.NAME
                , allColumns
                , Parametro.COLUMN_ID + " = " + insertId
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Parametro newParametro = cursorToParametro(cursor);
        cursor.close();
        return newParametro;
    }


    public Parametro GetArea(String intIdEmpresa, String intIdPersona) {

        boolean isUserLocal = false;

        Cursor cursor = database.query(CentroTrabajo.NAME
                , allColumns
                , Parametro.COLUMN_ID_EMPRESA + " = '" + intIdEmpresa + "' and " + Parametro.COLUMN_ID_EMPRESA + " = '" + intIdPersona + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Parametro newParametro = cursorToParametro(cursor);
        cursor.close();
        return newParametro;
    }

    public List<Parametro> GetParametrosByInspeccion(String strIdInspeccion) {
        List<Parametro> lstResult = new ArrayList<Parametro>();
        Cursor cursor = database.query(Parametro.NAME
                , allColumns
                , Parametro.COLUMN_ID_INSPECCION + " = '" + strIdInspeccion + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Parametro newItem = cursorToParametro(cursor);
            lstResult.add(newItem);
            cursor.moveToNext();
        }
        cursor.close();
        return lstResult;
    }

    private Parametro cursorToParametro(Cursor cursor) {
        Parametro comment = new Parametro();

        comment.setId(cursor.getLong(0));
        comment.setIntIdParametro(cursor.getString(1));
        comment.setIntIdInspeccion(cursor.getString(2));
        comment.setIntIdRiesgo(cursor.getString(3));
        comment.setStrDescripcionParametro(cursor.getString(4));
        comment.setStrRutaImagen(cursor.getString(5));
        comment.setIntIdEmpresa(cursor.getString(6));
        comment.setDtfecuser(cursor.getString(7));
        comment.setBoolCumple(Boolean.parseBoolean(cursor.getString(8)));

        return comment;
    }

    public List<Parametro> GetAll() {
        List<Parametro> lstResult = new ArrayList<Parametro>();
        Cursor cursor = database.query(Parametro.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Parametro newItem = cursorToParametro(cursor);
            lstResult.add(newItem);
            cursor.moveToNext();
        }
        cursor.close();
        return lstResult;
    }

    public int GetCount() {
        Cursor cursor = database.query(Parametro.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);

        return cursor.getCount();
    }

    public void truncateTable() {
        database.execSQL("delete from " + Parametro.NAME);
    }
}
