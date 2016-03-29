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
 * Created by JANUS on 31/12/2015.
 */
public class HabilidadDataSource {
    // Database fields
    private SQLiteDatabase database;
    private SQLOutsafetyHelper dbHelper;

    private String[] allColumns = {Habilidad.COLUMN_ID,
            Habilidad.COLUMN_ID_CENTRO_TRABAJO,
            Habilidad.COLUMN_ID_EMPRESA,
            Habilidad.COLUMN_RAZON_SOCIAL,
            Habilidad.COLUMN_NOMBRE,
            Habilidad.COLUMN_APELLIDO,
            Habilidad.COLUMN_CEDULA,
            Habilidad.COLUMN_ID_HABILIDAD,
            Habilidad.COLUMN_DESCR_HABILIDAD};

    public HabilidadDataSource(Context context) {
        dbHelper = new SQLOutsafetyHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Habilidad CreateHabilidad(String intIdCentroTrabajo
            , String intIdEmpresa
            , String strRazonSocial
            , String strNombreProfesional
            , String strApellidoProfesional
            , String strCedula
            , String intIdHabilidad
            , String strDescripcionHabilidad) {

        ContentValues values = new ContentValues();
        values.put(Habilidad.COLUMN_ID_CENTRO_TRABAJO, intIdCentroTrabajo);
        values.put(Habilidad.COLUMN_ID_EMPRESA, intIdEmpresa);
        values.put(Habilidad.COLUMN_RAZON_SOCIAL, strRazonSocial);
        values.put(Habilidad.COLUMN_NOMBRE, strNombreProfesional);
        values.put(Habilidad.COLUMN_APELLIDO, strApellidoProfesional);
        values.put(Habilidad.COLUMN_CEDULA, strCedula);
        values.put(Habilidad.COLUMN_ID_HABILIDAD, intIdHabilidad);
        values.put(Habilidad.COLUMN_DESCR_HABILIDAD, strDescripcionHabilidad);

        long insertId = database.insert(Habilidad.NAME
                , null,
                values);
        Cursor cursor = database.query(Habilidad.NAME
                , allColumns
                , Habilidad.COLUMN_ID + " = " + insertId
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Habilidad newHabilidad = cursorToHabilidad(cursor);
        cursor.close();
        return newHabilidad;
    }


    public Habilidad GetHabilidad(String strCedula) {

        Cursor cursor = database.query(Habilidad.NAME
                , allColumns
                , Habilidad.COLUMN_CEDULA + " = '" + strCedula + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Habilidad newHabilidad = cursorToHabilidad(cursor);
        cursor.close();
        return newHabilidad;
    }


    public List<Habilidad> GetHabilidadesByCedula(String strCedula) {
        List<Habilidad> lstHabilidad = new ArrayList<Habilidad>();
        Cursor cursor = database.query(Habilidad.NAME
                , allColumns
                , Habilidad.COLUMN_CEDULA + " = '" + strCedula + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Habilidad newHabilidad = cursorToHabilidad(cursor);
            lstHabilidad.add(newHabilidad);
            cursor.moveToNext();
        }
        cursor.close();
        return lstHabilidad;
    }

    public List<Habilidad> GetHabilidadesByCedulaAndCt(String strCedula, String idCt) {
        List<Habilidad> lstHabilidad = new ArrayList<Habilidad>();
        Cursor cursor = database.query(Habilidad.NAME
                , allColumns
                , Habilidad.COLUMN_CEDULA + " = '" + strCedula + "' AND " + Habilidad.COLUMN_ID_EMPRESA + "=" + idCt
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Habilidad newHabilidad = cursorToHabilidad(cursor);
            lstHabilidad.add(newHabilidad);
            cursor.moveToNext();
        }
        cursor.close();
        return lstHabilidad;
    }

    public List<Habilidad> GetAllHabilidades() {
        List<Habilidad> lstHabilidad = new ArrayList<Habilidad>();
        Cursor cursor = database.query(Habilidad.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Habilidad newHabilidad = cursorToHabilidad(cursor);
            lstHabilidad.add(newHabilidad);
            cursor.moveToNext();
        }
        cursor.close();
        return lstHabilidad;
    }

    public int GetCount() {
        Cursor cursor = database.query(Habilidad.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);

        return cursor.getCount();
    }

    private Habilidad cursorToHabilidad(Cursor cursor) {
        Habilidad comment = new Habilidad();

        comment.setId(cursor.getLong(0));
        comment.setIntIdCentroTrabajo(cursor.getString(1));
        comment.setIntIdEmpresa(cursor.getString(2));
        comment.setStrRazonSocial(cursor.getString(3));
        comment.setStrNombreProfesional(cursor.getString(4));
        comment.setStrApellidoProfesional(cursor.getString(5));
        comment.setStrCedula(cursor.getString(6));
        comment.setIntIdHabilidad(cursor.getString(7));
        comment.setStrDescripcionHabilidad(cursor.getString(8));

        return comment;
    }

    public void truncateTable() {
        database.execSQL("delete from " + Habilidad.NAME);
    }
}
