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
 * Created by JHONRIOS on 04/11/2015.
 */
public class LoginDataSource {
    // Database fields
    private SQLiteDatabase database;
    private SQLOutsafetyHelper dbHelper;

    private String[] allColumns = {Login.COLUMN_ID
            , Login.COLUMN_CEDULA
            , Login.COLUMN_PASSWORD
            , Login.COLUMN_ID_TIPO_PERSONA};

    public LoginDataSource(Context context) {
        dbHelper = new SQLOutsafetyHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Login CreateLogin(String intTipoPersona, String strCedula, String strPassword) {

        ContentValues values = new ContentValues();
        values.put(Login.COLUMN_PASSWORD, strPassword);
        values.put(Login.COLUMN_CEDULA, strCedula);
        values.put(Login.COLUMN_ID_TIPO_PERSONA, intTipoPersona);

        long insertId = database.insert(Login.NAME
                , null,
                values);
        Cursor cursor = database.query(Login.NAME
                , allColumns
                , Login.COLUMN_ID + " = " + insertId
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Login newLogin = cursorToLogin(cursor);
        cursor.close();
        return newLogin;
    }


    public Login GetLoginByCredenciales(String strCedula, String strPassword) {


        Cursor cursor = database.query(Login.NAME
                , allColumns
                , Login.COLUMN_CEDULA + " = '" + strCedula + "' and " + Login.COLUMN_PASSWORD + " = '" + strPassword + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Login newLogin = null;

        if (cursor.getCount() > 0) {
            newLogin = cursorToLogin(cursor);
        }
        cursor.close();

        return newLogin;
    }

    private Login cursorToLogin(Cursor cursor) {
        Login comment = new Login();

        comment.setId(cursor.getLong(0));
        comment.setStrCedula(cursor.getString(1));
        comment.setStrPassword(cursor.getString(2));
        comment.setIntTipoPersona(cursor.getString(3));

        return comment;
    }

    public void truncateTable() {
        database.execSQL("delete from " + Login.NAME);
    }

    public List<Login> GetAll() {
        List<Login> lstResult = new ArrayList<Login>();
        Cursor cursor = database.query(Login.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Login newItem = cursorToLogin(cursor);
            lstResult.add(newItem);
            cursor.moveToNext();
        }
        cursor.close();
        return lstResult;
    }

    public int GetCount() {
        Cursor cursor = database.query(Login.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);

        return cursor.getCount();
    }
}
