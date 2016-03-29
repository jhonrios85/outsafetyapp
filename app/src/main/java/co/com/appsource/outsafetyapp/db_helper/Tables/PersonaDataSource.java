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
 * Created by JHONRIOS on 27/10/2015.
 */
public class PersonaDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLOutsafetyHelper dbHelper;
    private String[] allColumns = {Persona.COLUMN_ID
            , Persona.COLUMN_ID_PERSONA
            , Persona.COLUMN_NOMBRE
            , Persona.COLUMN_APELLIDO
            , Persona.COLUMN_CEDULA
            , Persona.COLUMN_DIRECCION
            , Persona.COLUMN_TELEFONO
            , Persona.COLUMN_EMAIL
            , Persona.COLUMN_PASSWORD
            , Persona.COLUMN_ID_TIPO_PERSONA
            , Persona.COLUMN_BIT_ACTIVO
            , Persona.COLUMN_DT_FEC_USER
            , Persona.COLUMN_DT_ULTIMA_MODIFICACION
            , Persona.COLUMN_ID_EPS
            , Persona.COLUMN_RUTA_FOTO
            , Persona.COLUMN_ID_EMPRESA
            , Persona.COLUMN_RAZON_SOCIAL};

    public PersonaDataSource(Context context) {
        dbHelper = new SQLOutsafetyHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Persona CreatePesona(String bitActivo
            , String dtfecuser
            , String dtUltimaModificacion
            , String intIdEps
            , String intIdPersona
            , String intTipoPersona
            , String strApellidoProfesional
            , String strCedula
            , String strDireccion
            , String strEmail
            , String strNombreProfesional
            , String strPassword
            , String strRutaFoto
            , String strTelefono
            , String intIdEmpresa
            , String strRazonSocial) {

        ContentValues values = new ContentValues();
        values.put(Persona.COLUMN_ID_PERSONA, intIdPersona);
        values.put(Persona.COLUMN_NOMBRE, strNombreProfesional);
        values.put(Persona.COLUMN_APELLIDO, strApellidoProfesional);
        values.put(Persona.COLUMN_CEDULA, strCedula);
        values.put(Persona.COLUMN_DIRECCION, strDireccion);
        values.put(Persona.COLUMN_TELEFONO, strTelefono);
        values.put(Persona.COLUMN_EMAIL, strEmail);
        values.put(Persona.COLUMN_PASSWORD, strPassword);
        values.put(Persona.COLUMN_ID_PERSONA, intIdPersona);
        values.put(Persona.COLUMN_BIT_ACTIVO, bitActivo);
        values.put(Persona.COLUMN_DT_FEC_USER, dtfecuser);
        values.put(Persona.COLUMN_DT_ULTIMA_MODIFICACION, dtUltimaModificacion);
        values.put(Persona.COLUMN_ID_EPS, intIdEps);
        values.put(Persona.COLUMN_RUTA_FOTO, strRutaFoto);
        values.put(Persona.COLUMN_ID_EMPRESA, intIdEmpresa);
        values.put(Persona.COLUMN_RAZON_SOCIAL, strRazonSocial);

        long insertId = database.insert(Persona.NAME
                , null,
                values);
        Cursor cursor = database.query(Persona.NAME
                , allColumns
                , Persona.COLUMN_ID + " = " + insertId
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Persona newPersona = cursorToPersona(cursor);
        cursor.close();
        return newPersona;
    }

    private Persona cursorToPersona(Cursor cursor) {
        Persona comment = new Persona();

        comment.setId(cursor.getLong(0));
        comment.setIntIdPersona(cursor.getString(1));
        comment.setStrNombreProfesional(cursor.getString(2));
        comment.setStrApellidoProfesional(cursor.getString(3));
        comment.setStrCedula(cursor.getString(4));
        comment.setStrDireccion(cursor.getString(5));
        comment.setStrTelefono(cursor.getString(6));
        comment.setStrEmail(cursor.getString(7));
        comment.setStrPassword(cursor.getString(8));
        comment.setIntTipoPersona(cursor.getString(9));
        comment.setBitActivo(cursor.getString(10));
        comment.setDtfecuser(cursor.getString(11));
        comment.setDtUltimaModificacion(cursor.getString(12));
        comment.setIntIdEps(cursor.getString(13));
        comment.setStrRutaFoto(cursor.getString(14));
        comment.setIntIdEmpresa(cursor.getString(15));
        comment.setStrRazonSocial(cursor.getString(16));

        return comment;
    }

    public void truncateTable() {
        database.execSQL("delete from " + Persona.NAME);
    }

    public List<Persona> GetPersonasByDocumento(String strNumDocumento) {
        List<Persona> lstResult = new ArrayList<Persona>();
        Cursor cursor = database.query(Persona.NAME
                , allColumns
                , Persona.COLUMN_CEDULA + " = '" + strNumDocumento + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Persona newItem = cursorToPersona(cursor);
            lstResult.add(newItem);
            cursor.moveToNext();
        }
        cursor.close();
        return lstResult;
    }

    public List<Persona> GetPersonasByCentroTrabajo(String strIdCentroTrabajo) {
        List<Persona> lstResult = new ArrayList<Persona>();
        Cursor cursor = database.query(Persona.NAME
                , allColumns
                , Persona.COLUMN_ID_EMPRESA + " = '" + strIdCentroTrabajo + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Persona newItem = cursorToPersona(cursor);
            lstResult.add(newItem);
            cursor.moveToNext();
        }
        cursor.close();
        return lstResult;
    }

    public List<Persona> GetAll() {
        List<Persona> lstResult = new ArrayList<Persona>();
        Cursor cursor = database.query(Persona.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Persona newItem = cursorToPersona(cursor);
            lstResult.add(newItem);
            cursor.moveToNext();
        }
        cursor.close();
        return lstResult;
    }

    public int GetCount() {
        Cursor cursor = database.query(Persona.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);

        return cursor.getCount();
    }
}
