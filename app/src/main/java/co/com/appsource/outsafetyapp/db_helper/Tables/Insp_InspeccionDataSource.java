package co.com.appsource.outsafetyapp.db_helper.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Xml;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import co.com.appsource.outsafetyapp.db_helper.SQLOutsafetyHelper;

/**
 * Created by JANUS on 12/12/2015.
 */
public class Insp_InspeccionDataSource {
    // Database fields
    private SQLiteDatabase database;
    private SQLOutsafetyHelper dbHelper;

    private String[] allColumns = {Insp_Inspeccion.COLUMN_ID
            , Insp_Inspeccion.COLUMN_USUARIO_CREA
            , Insp_Inspeccion.COLUMN_ID_CENTRO_TRABAJO
            , Insp_Inspeccion.COLUMN_ID_AREA
            , Insp_Inspeccion.COLUMN_ID_RIESGO
            , Insp_Inspeccion.COLUMN_ID_INSPECCION
            , Insp_Inspeccion.COLUMN_OBSERVACION_GENERAL
            , Insp_Inspeccion.COLUMN_GUARDADA_CON_EXITO};

    public Insp_InspeccionDataSource(Context context) {
        dbHelper = new SQLOutsafetyHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Insp_Inspeccion CreateInsp_Inspeccion(String strUsuario, long intIdCentroTrabajo, long intIdArea, long intIdRiesgo, long intIdInspeccion, String strObservacionGeneral) {

        ContentValues values = new ContentValues();
        values.put(Insp_Inspeccion.COLUMN_USUARIO_CREA, strUsuario);
        values.put(Insp_Inspeccion.COLUMN_ID_CENTRO_TRABAJO, intIdCentroTrabajo);
        values.put(Insp_Inspeccion.COLUMN_ID_AREA, intIdArea);
        values.put(Insp_Inspeccion.COLUMN_ID_RIESGO, intIdRiesgo);
        values.put(Insp_Inspeccion.COLUMN_ID_INSPECCION, intIdInspeccion);
        values.put(Insp_Inspeccion.COLUMN_OBSERVACION_GENERAL, strObservacionGeneral);
        values.put(Insp_Inspeccion.COLUMN_GUARDADA_CON_EXITO, "0");

        long insertId = database.insert(Insp_Inspeccion.NAME
                , null,
                values);
        Cursor cursor = database.query(Insp_Inspeccion.NAME
                , allColumns
                , Insp_Inspeccion.COLUMN_ID + " = " + insertId
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Insp_Inspeccion newInsp_Inspeccion = cursorToInsp_Inspeccion(cursor);
        cursor.close();
        return newInsp_Inspeccion;
    }


    public Insp_Inspeccion GetInsp_Inspeccion(String intId) {

        Cursor cursor = database.query(Insp_Inspeccion.NAME
                , allColumns
                , Insp_Inspeccion.COLUMN_ID + " = " + intId
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Insp_Inspeccion newInsp_Inspeccion = cursorToInsp_Inspeccion(cursor);
        cursor.close();
        return newInsp_Inspeccion;
    }

    private Insp_Inspeccion cursorToInsp_Inspeccion(Cursor cursor) {
        Insp_Inspeccion comment = new Insp_Inspeccion();

        comment.setId(cursor.getLong(0));
        comment.setStrUsuario(cursor.getString(1));
        comment.setIntIdCentroTrabajo(cursor.getLong(2));
        comment.setIntIdArea(cursor.getLong(3));
        comment.setIntIdRiesgo(cursor.getLong(4));
        comment.setIntIdInspeccion(cursor.getLong(5));
        comment.setStrObservacionGeneral(cursor.getString(6));
        comment.setStrGuardadaConExito(cursor.getString(7));

        return comment;
    }

    public static String GetJson(Insp_Inspeccion obj) {
        String strJson = "";
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        strJson = gson.toJson(obj);
        return strJson;
    }

    public static String GenerarDataSet(List<Insp_Inspeccion> lstResult) {
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
            for (Insp_Inspeccion itemResult :
                    lstResult) {

                serializer.startTag("", "inspeccion");
                serializer.startTag("", "ConcInspeccion");
                serializer.text(Long.toString(itemResult.getId()));
                serializer.endTag("", "ConcInspeccion");
                serializer.startTag("", "idUsuario");
                serializer.text(itemResult.getStrUsuario());
                serializer.endTag("", "idUsuario");
                serializer.startTag("", "idCentroTrabajo");
                serializer.text(Long.toString(itemResult.getIntIdCentroTrabajo()));
                serializer.endTag("", "idCentroTrabajo");
                serializer.startTag("", "idRiesgo");
                serializer.text(Long.toString(itemResult.getIntIdRiesgo()));
                serializer.endTag("", "idRiesgo");
                serializer.startTag("", "idInspeccion");
                serializer.text(Long.toString(itemResult.getIntIdInspeccion()));
                serializer.endTag("", "idInspeccion");
                serializer.startTag("", "Observacion");
                serializer.text(itemResult.getStrObservacionGeneral());
                serializer.endTag("", "Observacion");
                serializer.startTag("", "intIdArea");
                serializer.text(Long.toString(itemResult.getIntIdArea()));
                serializer.endTag("", "intIdArea");
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

    public List<Insp_Inspeccion> GetAll() {
        List<Insp_Inspeccion> lstResult = new ArrayList<Insp_Inspeccion>();
        Cursor cursor = database.query(Insp_Inspeccion.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Insp_Inspeccion newItem = cursorToInsp_Inspeccion(cursor);
            lstResult.add(newItem);
            cursor.moveToNext();
        }
        cursor.close();
        return lstResult;
    }

    public List<Insp_Inspeccion> GetAllComplete() {
        List<Insp_Inspeccion> lstResult = new ArrayList<Insp_Inspeccion>();
        Cursor cursor = database.query(Insp_Inspeccion.NAME
                , allColumns
                , Insp_Inspeccion.COLUMN_GUARDADA_CON_EXITO + " = '1'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Insp_Inspeccion newItem = cursorToInsp_Inspeccion(cursor);
            lstResult.add(newItem);
            cursor.moveToNext();
        }
        cursor.close();
        return lstResult;
    }

    public int GetCount() {
        Cursor cursor = database.query(Insp_Inspeccion.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);

        return cursor.getCount();
    }

    public void DeleteInsp_InspeccionByConsecutivoInsp(long idConsecutivoInsp) {
        database.execSQL("delete from " + Insp_Inspeccion.NAME + " where " + Insp_Inspeccion.COLUMN_ID + "=" + idConsecutivoInsp);
    }

    public void truncateTable() {
        database.execSQL("delete from " + Insp_Inspeccion.NAME);
    }

    public void removeIncomplete() {
        database.execSQL("delete from " + Insp_Inspeccion.NAME + " where " + Insp_Inspeccion.COLUMN_GUARDADA_CON_EXITO + "='0'");
    }

    public void updateToComplete(Long intId) {
        database.execSQL("UPDATE " + Insp_Inspeccion.NAME + " SET " + Insp_Inspeccion.COLUMN_GUARDADA_CON_EXITO + "='1' where " + Insp_Inspeccion.COLUMN_ID + "=" + intId);
    }
}
