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
 * Created by JANUS on 18/01/2016.
 */
public class ControlIngresoDataSource {
    // Database fields
    private SQLiteDatabase database;
    private SQLOutsafetyHelper dbHelper;

    private String[] allColumns = {ControlIngreso.COLUMN_ID,
            ControlIngreso.COLUMN_TIPO_ACCESO,
            ControlIngreso.COLUMN_TIPO,
            ControlIngreso.COLUMN_ID_USUARIO,
            ControlIngreso.COLUMN_FECHA,
            ControlIngreso.COLUMN_ID_CENTRO_TRABAJO,
            ControlIngreso.COLUMN_ID_VIGILANTE,
            ControlIngreso.COLUMN_ID_SALIDA};

    public ControlIngresoDataSource(Context context) {
        dbHelper = new SQLOutsafetyHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ControlIngreso CreateControlIngreso(String strTipoAcceso
            , String strTipo
            , String strIdUsuario
            , String strFecha
            , String intIdCentroTrabajo
            , String strIdVigilante
            , long intIdSalida) {

        ContentValues values = new ContentValues();
        values.put(ControlIngreso.COLUMN_TIPO_ACCESO, strTipoAcceso);
        values.put(ControlIngreso.COLUMN_TIPO, strTipo);
        values.put(ControlIngreso.COLUMN_ID_USUARIO, strIdUsuario);
        values.put(ControlIngreso.COLUMN_FECHA, strFecha);
        values.put(ControlIngreso.COLUMN_ID_CENTRO_TRABAJO, intIdCentroTrabajo);
        values.put(ControlIngreso.COLUMN_ID_VIGILANTE, strIdVigilante);
        values.put(ControlIngreso.COLUMN_ID_SALIDA, intIdSalida);


        long insertId = database.insert(ControlIngreso.NAME
                , null,
                values);

        Cursor cursor = database.query(ControlIngreso.NAME
                , allColumns
                , ControlIngreso.COLUMN_ID + " = " + insertId
                , null
                , null
                , null
                , null);

        cursor.moveToFirst();
        ControlIngreso newControlIngreso = cursorToControlIngreso(cursor);
        cursor.close();
        return newControlIngreso;
    }


    public ControlIngreso GetControlIngreso(String strCedula) {

        Cursor cursor = database.query(ControlIngreso.NAME
                , allColumns
                , ControlIngreso.COLUMN_ID_USUARIO + " = '" + strCedula + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        ControlIngreso newControlIngreso = cursorToControlIngreso(cursor);
        cursor.close();
        return newControlIngreso;
    }

    public List<ControlIngreso> GetControleIngresoByCedulaAndType(String strCedula, String tipo) {
        List<ControlIngreso> lstControlIngreso = new ArrayList<ControlIngreso>();
        Cursor cursor = database.query(ControlIngreso.NAME
                , allColumns
                , ControlIngreso.COLUMN_ID_USUARIO + " = '" + strCedula + "' AND " + ControlIngreso.COLUMN_TIPO + " = '" + tipo + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ControlIngreso newControlIngreso = cursorToControlIngreso(cursor);
            lstControlIngreso.add(newControlIngreso);
            cursor.moveToNext();
        }
        cursor.close();
        return lstControlIngreso;
    }

    public List<ControlIngreso> GetControlesIngresoByCedula(String strCedula) {
        List<ControlIngreso> lstControlIngreso = new ArrayList<ControlIngreso>();
        Cursor cursor = database.query(ControlIngreso.NAME
                , allColumns
                , ControlIngreso.COLUMN_ID_USUARIO + " = '" + strCedula + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ControlIngreso newControlIngreso = cursorToControlIngreso(cursor);
            lstControlIngreso.add(newControlIngreso);
            cursor.moveToNext();
        }
        cursor.close();
        return lstControlIngreso;
    }

    public List<ControlIngreso> GetAllControlIngreso() {
        List<ControlIngreso> lstControlIngreso = new ArrayList<ControlIngreso>();
        Cursor cursor = database.query(ControlIngreso.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ControlIngreso newControlIngreso = cursorToControlIngreso(cursor);
            lstControlIngreso.add(newControlIngreso);
            cursor.moveToNext();
        }
        cursor.close();
        return lstControlIngreso;
    }

    public int GetCount() {
        Cursor cursor = database.query(ControlIngreso.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);

        return cursor.getCount();
    }

    private ControlIngreso cursorToControlIngreso(Cursor cursor) {
        ControlIngreso comment = new ControlIngreso();

        comment.setId(cursor.getLong(0));
        comment.setStrTipoAcceso(cursor.getString(1));
        comment.setStrTipo(cursor.getString(2));
        comment.setStrIdUsuario(cursor.getString(3));
        comment.setStrFecha(cursor.getString(4));
        comment.setIntIdCentroTrabajo(cursor.getString(5));
        comment.setStrIdVigilante(cursor.getString(6));
        comment.setIntIdSalida(cursor.getLong(7));

        return comment;
    }

    public static String GenerarDataSet(List<ControlIngreso> lstResult) {
        String serlializedDataSet = "<?xml version=\"1.0\" standalone=\"yes\"?><NewDataSet>";

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {

            serializer.setOutput(writer);
            for (ControlIngreso itemResult :
                    lstResult) {

                serializer.startTag("", "accesos");
                serializer.startTag("", "tipoAcceso");
                serializer.text(itemResult.getStrTipoAcceso());
                serializer.endTag("", "tipoAcceso");
                serializer.startTag("", "tipo");
                serializer.text(itemResult.getStrTipo());
                serializer.endTag("", "tipo");
                serializer.startTag("", "idUsuario");
                serializer.text(itemResult.getStrIdUsuario());
                serializer.endTag("", "idUsuario");
                serializer.startTag("", "Fecha");
                serializer.text(itemResult.getStrFecha());
                serializer.endTag("", "Fecha");
                serializer.startTag("", "idCentroTrabajo");
                serializer.text(itemResult.getIntIdCentroTrabajo());
                serializer.endTag("", "idCentroTrabajo");
                serializer.startTag("", "idVigilante");
                serializer.text(itemResult.getStrIdVigilante());
                serializer.endTag("", "idVigilante");
                serializer.endTag("", "accesos");

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

    public void truncateTable() {
        database.execSQL("delete from " + ControlIngreso.NAME);
    }
}
