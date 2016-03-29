package co.com.appsource.outsafetyapp.db_helper.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import co.com.appsource.outsafetyapp.db_helper.SQLOutsafetyHelper;

/**
 * Created by JANUS on 12/12/2015.
 */
public class Insp_EvidenciaDataSource {
    // Database fields
    private SQLiteDatabase database;
    private SQLOutsafetyHelper dbHelper;
    private String[] allColumns = {Insp_Evidencia.COLUMN_ID
            , Insp_Evidencia.COLUMN_EVIDENCIA
            , Insp_Evidencia.COLUMN_ID_INSPECCION_FK
            , Insp_Evidencia.COLUMN_CEDULA
            , Insp_Evidencia.COLUMN_ID_INSPECCION_PARAM
            , Insp_Evidencia.COLUMN_USUARIO_CREA};

    public Insp_EvidenciaDataSource(Context context) {
        dbHelper = new SQLOutsafetyHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Insp_Evidencia CreateInsp_Evidencia(String strEvidenciaBase64, long intIdInspeccionFk, String strCedula, long intIdInspeccionParamFk, String strUsuario) {

        ContentValues values = new ContentValues();
        values.put(Insp_Evidencia.COLUMN_EVIDENCIA, strEvidenciaBase64);
        values.put(Insp_Evidencia.COLUMN_ID_INSPECCION_FK, intIdInspeccionFk);
        values.put(Insp_Evidencia.COLUMN_CEDULA, strCedula);
        values.put(Insp_Evidencia.COLUMN_ID_INSPECCION_PARAM, intIdInspeccionParamFk);
        values.put(Insp_Evidencia.COLUMN_USUARIO_CREA, strUsuario);

        long insertId = database.insert(Insp_Evidencia.NAME
                , null,
                values);
        Cursor cursor = database.query(Insp_Evidencia.NAME
                , allColumns
                , Insp_Evidencia.COLUMN_ID + " = " + insertId
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Insp_Evidencia newEvidencia = cursorToInsp_Evidencia(cursor);
        cursor.close();
        return newEvidencia;
    }

    public Insp_Evidencia GetInsp_Evidencia(String intIdInspeccionFk) {

        Cursor cursor = database.query(Insp_Evidencia.NAME
                , allColumns
                , Insp_Evidencia.COLUMN_ID_INSPECCION_FK + " = " + intIdInspeccionFk
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Insp_Evidencia newInsp_Evidencia = cursorToInsp_Evidencia(cursor);
        cursor.close();
        return newInsp_Evidencia;
    }

    public List<Insp_Evidencia> GetInsp_Evidencia(long intIdInspeccionFk) {

        List<Insp_Evidencia> lstEvidencias = new ArrayList<Insp_Evidencia>();
        Cursor cursor = database.query(Insp_Evidencia.NAME
                , allColumns
                , Insp_Evidencia.COLUMN_ID_INSPECCION_FK + " = " + intIdInspeccionFk
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Insp_Evidencia newInsp_Evidencia = cursorToInsp_Evidencia(cursor);
            lstEvidencias.add(newInsp_Evidencia);
            cursor.moveToNext();
        }
        cursor.close();
        return lstEvidencias;
    }

    private Insp_Evidencia cursorToInsp_Evidencia(Cursor cursor) {
        Insp_Evidencia comment = new Insp_Evidencia();

        comment.setId(cursor.getLong(0));
        comment.setStrEvidenciaBase64(cursor.getString(1));
        comment.setIntIdInspeccionFk(cursor.getLong(2));
        comment.setStrCedula(cursor.getString(3));
        comment.setIntIdInspeccionParamFk(cursor.getLong(4));
        comment.setStrUsuario(cursor.getString(5));

        return comment;
    }

    public static String GenerarDataSet(List<Insp_Evidencia> lstResult) {
        String serlializedDataSet = "<?xml version=\"1.0\" standalone=\"yes\"?><NewDataSet>";

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {

            serializer.setOutput(writer);
            for (Insp_Evidencia itemResult :
                    lstResult) {

                serializer.startTag("", "Evidencias");
                serializer.startTag("", "ConcInspeccion");
                serializer.text(Long.toString(itemResult.getIntIdInspeccionFk()));
                serializer.endTag("", "ConcInspeccion");
                serializer.startTag("", "idUsuario");
                serializer.text(itemResult.getStrUsuario());
                serializer.endTag("", "idUsuario");
                serializer.startTag("", "idInspeccion");
                serializer.text(Long.toString(itemResult.getIntIdInspeccionParamFk()));
                serializer.endTag("", "idInspeccion");
                serializer.startTag("", "Evidencia");
                serializer.text(itemResult.getStrEvidenciaBase64());
                serializer.endTag("", "Evidencia");
                serializer.endTag("", "Evidencias");

            }
            serializer.endDocument();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        serlializedDataSet = serlializedDataSet + writer.toString() + "</NewDataSet>";
        return serlializedDataSet;

    }

    public List<Insp_Evidencia> GetAll() {
        List<Insp_Evidencia> lstResult = new ArrayList<Insp_Evidencia>();
        Cursor cursor = database.query(Insp_Evidencia.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Insp_Evidencia newItemResult = cursorToInsp_Evidencia(cursor);
            lstResult.add(newItemResult);
            cursor.moveToNext();
        }
        cursor.close();
        return lstResult;
    }

    public int GetCount() {
        Cursor cursor = database.query(Insp_Evidencia.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);

        return cursor.getCount();
    }

    public void DeleteInsp_EvidenciaByConsecutivoInsp(long idConsecutivoInsp) {
        database.execSQL("delete from " + Insp_Evidencia.NAME + " where " + Insp_Evidencia.COLUMN_ID_INSPECCION_FK + "=" + idConsecutivoInsp);
    }

    public void truncateTable() {
        database.execSQL("delete from " + Insp_Evidencia.NAME);
    }
}
