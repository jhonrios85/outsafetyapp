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
 * Created by Administrador on 30/03/2016.
 */
public class EmpresaDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLOutsafetyHelper dbHelper;

    private String[] allColumns = {Empresa.COLUMN_ID,
            Empresa.COLUMN_ID_EMPRESA,
            Empresa.COLUMN_NIT,
            Empresa.COLUMN_RAZON_SOCIAL,
            Empresa.COLUMN_REPRESENTANTE_LEGAL,
            Empresa.COLUMN_EMAIL,
            Empresa.COLUMN_RESPONSABLE_SISO,
            Empresa.COLUMN_EMAIL_RESPONSABLE_SISO,
            Empresa.COLUMN_ESTADO,
            Empresa.COLUMN_ID_TIPO_EMPRESA};

    public EmpresaDataSource(Context context) {
        dbHelper = new SQLOutsafetyHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Empresa CreateEmpresa(String intIdEmpresa, String strNit, String strRazonSocial, String strRepresentanteLegal, String strEmail, String strResponsableSiso, String strEmailResponsableSiso, String boolEstado, String intIdTipoEmpresa) {

        ContentValues values = new ContentValues();

        values.put(Empresa.COLUMN_ID_EMPRESA, intIdEmpresa);
        values.put(Empresa.COLUMN_NIT, strNit);
        values.put(Empresa.COLUMN_RAZON_SOCIAL, strRazonSocial);
        values.put(Empresa.COLUMN_REPRESENTANTE_LEGAL, strRepresentanteLegal);
        values.put(Empresa.COLUMN_EMAIL, strEmail);
        values.put(Empresa.COLUMN_RESPONSABLE_SISO, strEmailResponsableSiso);
        values.put(Empresa.COLUMN_EMAIL_RESPONSABLE_SISO, strEmailResponsableSiso);
        values.put(Empresa.COLUMN_ESTADO, boolEstado);
        values.put(Empresa.COLUMN_ID_TIPO_EMPRESA, intIdTipoEmpresa);

        long insertId = database.insert(Empresa.NAME
                , null,
                values);
        Cursor cursor = database.query(Empresa.NAME
                , allColumns
                , Empresa.COLUMN_ID + " = " + insertId
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Empresa newEmpresa = cursorToEmpresa(cursor);
        cursor.close();
        return newEmpresa;
    }


    public Empresa GetEmpresa(String intIdEmpresa) {

        boolean isUserLocal = false;

        Cursor cursor = database.query(Empresa.NAME
                , allColumns
                , Empresa.COLUMN_ID_EMPRESA + " = '" + intIdEmpresa + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Empresa newEmpresa = cursorToEmpresa(cursor);
        cursor.close();
        return newEmpresa;
    }

    public Empresa GetEmpresaByName(String strRazonSocial) {

        boolean isUserLocal = false;

        Cursor cursor = database.query(Empresa.NAME
                , allColumns
                , Empresa.COLUMN_RAZON_SOCIAL + " like '%" + strRazonSocial + "%'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Empresa newEmpresa = cursorToEmpresa(cursor);
        cursor.close();
        return newEmpresa;
    }

    public Cursor GetEmpresaByNameCursor(String strRazonSocial) {

        Cursor cursor = database.query(Empresa.NAME
                , allColumns
                , Empresa.COLUMN_RAZON_SOCIAL + " like '%" + strRazonSocial + "%'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        return cursor;
    }

    public List<Empresa> GetAllEmpresas() {
        List<Empresa> lstEmpresas = new ArrayList<Empresa>();
        Cursor cursor = database.query(Empresa.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Empresa newEmpresa = cursorToEmpresa(cursor);
            lstEmpresas.add(newEmpresa);
            cursor.moveToNext();
        }
        cursor.close();
        return lstEmpresas;
    }

    public int GetCount() {
        Cursor cursor = database.query(Empresa.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);

        return cursor.getCount();
    }

    private Empresa cursorToEmpresa(Cursor cursor) {
        Empresa comment = new Empresa();

        comment.setId(cursor.getLong(0));
        comment.setIntIdEmpresa(cursor.getString(1));
        comment.setStrNit(cursor.getString(2));
        comment.setStrRazonSocial(cursor.getString(3));
        comment.setStrRepresentanteLegal(cursor.getString(4));
        comment.setStrEmail(cursor.getString(5));
        comment.setStrResponsableSiso(cursor.getString(6));
        comment.setStrEmailResponsableSiso(cursor.getString(7));
        comment.setBoolEstado(cursor.getString(8));
        comment.setIntIdTipoEmpresa(cursor.getString(9));

        return comment;
    }

    public void truncateTable() {
        database.execSQL("delete from " + Empresa.NAME);
    }
}
