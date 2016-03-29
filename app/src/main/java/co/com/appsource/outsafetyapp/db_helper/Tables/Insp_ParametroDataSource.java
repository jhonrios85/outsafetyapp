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
import java.util.Calendar;
import java.util.List;

import co.com.appsource.outsafetyapp.db_helper.SQLOutsafetyHelper;

/**
 * Created by JANUS on 12/12/2015.
 */
public class Insp_ParametroDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLOutsafetyHelper dbHelper;

    private String[] allColumns = {Insp_Parametro.COLUMN_ID
            , Insp_Parametro.COLUMN_ID_PARAMETRO
            , Insp_Parametro.COLUMN_ID_INSPECCION
            , Insp_Parametro.COLUMN_ID_RIESGO
            , Insp_Parametro.COLUMN_DESC_PARAMETRO
            , Insp_Parametro.COLUMN_RUTA_IMAGEN
            , Insp_Parametro.COLUMN_ID_EMPRESA
            , Insp_Parametro.COLUMN_FEC_USER
            , Insp_Parametro.COLUMN_CUMPLE
            , Insp_Parametro.COLUMN_ID_INSPECCION_FK};

    public Insp_ParametroDataSource(Context context) {
        dbHelper = new SQLOutsafetyHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Insp_Parametro CreateInsp_Parametro(String intIdParametro, String intIdInspeccion, String intIdRiesgo, String strDescripcionParametro, String strRutaImagen, String intIdEmpresa, String dtfecuser, boolean boolCumple, long intIdInspeccionFk) {

        ContentValues values = new ContentValues();
        values.put(Insp_Parametro.COLUMN_ID_PARAMETRO, intIdParametro);
        values.put(Insp_Parametro.COLUMN_ID_INSPECCION, intIdInspeccion);
        values.put(Insp_Parametro.COLUMN_ID_RIESGO, intIdRiesgo);
        values.put(Insp_Parametro.COLUMN_DESC_PARAMETRO, strDescripcionParametro);
        values.put(Insp_Parametro.COLUMN_RUTA_IMAGEN, strRutaImagen);
        values.put(Insp_Parametro.COLUMN_ID_EMPRESA, intIdEmpresa);
        values.put(Insp_Parametro.COLUMN_FEC_USER, dtfecuser);
        values.put(Insp_Parametro.COLUMN_CUMPLE, Boolean.toString(boolCumple));
        values.put(Insp_Parametro.COLUMN_ID_INSPECCION_FK, intIdInspeccionFk);

        long insertId = database.insert(Insp_Parametro.NAME
                , null,
                values);
        Cursor cursor = database.query(Insp_Parametro.NAME
                , allColumns
                , Insp_Parametro.COLUMN_ID + " = " + insertId
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Insp_Parametro newParametro = cursorToInsp_Parametro(cursor);
        cursor.close();
        return newParametro;
    }


    public List<Insp_Parametro> GetInsp_Parametro(long intIdInspeccionFk) {

        List<Insp_Parametro> lstParametros = new ArrayList<Insp_Parametro>();
        Cursor cursor = database.query(Insp_Parametro.NAME
                , allColumns
                , Insp_Parametro.COLUMN_ID_INSPECCION_FK + " = " + intIdInspeccionFk
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Insp_Parametro newInsp_Parametro = cursorToInsp_Parametro(cursor);
            lstParametros.add(newInsp_Parametro);
            cursor.moveToNext();
        }
        cursor.close();
        return lstParametros;
    }

    private Insp_Parametro cursorToInsp_Parametro(Cursor cursor) {
        Insp_Parametro comment = new Insp_Parametro();

        comment.setId(cursor.getLong(0));
        comment.setIntIdParametro(cursor.getString(1));
        comment.setIntIdInspeccion(cursor.getString(2));
        comment.setIntIdRiesgo(cursor.getString(3));
        comment.setStrDescripcionParametro(cursor.getString(4));
        comment.setStrRutaImagen(cursor.getString(5));
        comment.setIntIdEmpresa(cursor.getString(6));
        comment.setDtfecuser(cursor.getString(7));
        comment.setBoolCumple(Boolean.parseBoolean(cursor.getString(8)));
        comment.setIntIdInspeccionFk(cursor.getLong(9));

        return comment;
    }

    public static String GenerarDataSet(List<Insp_Parametro> lstResult) {
        String serlializedDataSet = "<?xml version=\"1.0\" standalone=\"yes\"?><NewDataSet>";

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        Calendar c = Calendar.getInstance();

        String anio = Integer.toString(c.get(Calendar.YEAR));

        String mes = Integer.toString(c.get(Calendar.MONTH) + 1);
        if ((c.get(Calendar.MONTH) + 1) < 10) {
            mes = "0" + mes;
        }

        String dia = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        if (c.get(Calendar.DAY_OF_MONTH) < 10) {
            dia = "0" + dia;
        }

        String currentDate = anio + "-" + mes + "-" + dia;

        try {

            serializer.setOutput(writer);
            for (Insp_Parametro itemResult :
                    lstResult) {

                serializer.startTag("", "inspeccion");
                serializer.startTag("", "ConcInspeccion");
                serializer.text(Long.toString(itemResult.getIntIdInspeccionFk()));
                serializer.endTag("", "ConcInspeccion");
                serializer.startTag("", "idInspeccion");
                serializer.text(itemResult.getIntIdInspeccion());
                serializer.endTag("", "idInspeccion");
                serializer.startTag("", "idParametro");
                serializer.text(itemResult.getIntIdParametro());
                serializer.endTag("", "idParametro");
                serializer.startTag("", "bitCumple");
                if (itemResult.isBoolCumple()) {
                    serializer.text("True");
                } else {
                    serializer.text("False");
                }

                serializer.endTag("", "bitCumple");
                serializer.startTag("", "dtFechaProgramacion");
                serializer.text(currentDate);
                serializer.endTag("", "dtFechaProgramacion");
                serializer.startTag("", "dtFechaInicioInspeccion");
                serializer.text(currentDate);
                serializer.endTag("", "dtFechaInicioInspeccion");
                serializer.startTag("", "dtFechaFinInspeccion");
                serializer.text(currentDate);
                serializer.endTag("", "dtFechaFinInspeccion");
                serializer.startTag("", "dtFechaRegistro");
                serializer.text(currentDate);
                serializer.endTag("", "dtFechaRegistro");
                serializer.endTag("", "inspeccion");

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

    public List<Insp_Parametro> GetAll() {
        List<Insp_Parametro> lstResult = new ArrayList<Insp_Parametro>();
        Cursor cursor = database.query(Insp_Parametro.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Insp_Parametro newItem = cursorToInsp_Parametro(cursor);
            lstResult.add(newItem);
            cursor.moveToNext();
        }
        cursor.close();
        return lstResult;
    }

    public int GetCount() {
        Cursor cursor = database.query(Insp_Parametro.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);

        return cursor.getCount();
    }

    public void DeleteInsp_ParametroByConsecutivoInsp(long idConsecutivoInsp) {
        database.execSQL("delete from " + Insp_Parametro.NAME + " where " + Insp_Parametro.COLUMN_ID_INSPECCION_FK + "=" + idConsecutivoInsp);
    }

    public void truncateTable() {
        database.execSQL("delete from " + Insp_Parametro.NAME);
    }
}
