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
 * Created by JANUS on 25/11/2015.
 */
public class CentroTrabajoDataSource {
    // Database fields
    private SQLiteDatabase database;
    private SQLOutsafetyHelper dbHelper;

    private String[] allColumns = {CentroTrabajo.COLUMN_ID
            , CentroTrabajo.COLUMN_ID_EMPRESA
            , CentroTrabajo.COLUMN_ID_PERSONA
            , CentroTrabajo.COLUMN_RAZON_SOCIAL};

    public CentroTrabajoDataSource(Context context) {
        dbHelper = new SQLOutsafetyHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public CentroTrabajo CreateCentroTrabajo(String intIdEmpresa, String intIdPersona, String strRazonSocial) {

        ContentValues values = new ContentValues();
        values.put(CentroTrabajo.COLUMN_ID_EMPRESA, intIdEmpresa);
        values.put(CentroTrabajo.COLUMN_ID_PERSONA, intIdPersona);
        values.put(CentroTrabajo.COLUMN_RAZON_SOCIAL, strRazonSocial);

        long insertId = database.insert(CentroTrabajo.NAME
                , null,
                values);
        Cursor cursor = database.query(CentroTrabajo.NAME
                , allColumns
                , CentroTrabajo.COLUMN_ID + " = " + insertId
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        CentroTrabajo newCentroTrabajo = cursorToCentroTrabajo(cursor);
        cursor.close();
        return newCentroTrabajo;
    }


    public CentroTrabajo GetCentroTrabajo(String intIdEmpresa, String intIdPersona) {

        boolean isUserLocal = false;

        Cursor cursor = database.query(CentroTrabajo.NAME
                , allColumns
                , CentroTrabajo.COLUMN_ID_EMPRESA + " = '" + intIdEmpresa + "' and " + CentroTrabajo.COLUMN_ID_PERSONA + " = '" + intIdPersona + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        CentroTrabajo newCentroTrabajo = cursorToCentroTrabajo(cursor);
        cursor.close();
        return newCentroTrabajo;
    }

    public List<CentroTrabajo> GetAllCentrosTrabajo() {
        List<CentroTrabajo> lstCentrosTrabajo = new ArrayList<CentroTrabajo>();
        Cursor cursor = database.query(CentroTrabajo.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CentroTrabajo newCentroTrabajo = cursorToCentroTrabajo(cursor);
            lstCentrosTrabajo.add(newCentroTrabajo);
            cursor.moveToNext();
        }
        cursor.close();
        return lstCentrosTrabajo;
    }

    public int GetCount() {
        Cursor cursor = database.query(CentroTrabajo.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);

        return cursor.getCount();
    }

    private CentroTrabajo cursorToCentroTrabajo(Cursor cursor) {
        CentroTrabajo comment = new CentroTrabajo();

        comment.setId(cursor.getLong(0));
        comment.setIntIdEmpresa(cursor.getString(1));
        comment.setIntIdPersona(cursor.getString(2));
        comment.setStrRazonSocial(cursor.getString(3));

        return comment;
    }

    public void truncateTable() {
        database.execSQL("delete from " + CentroTrabajo.NAME);
    }
}
