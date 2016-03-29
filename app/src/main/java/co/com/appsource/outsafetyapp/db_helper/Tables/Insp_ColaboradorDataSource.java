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
import java.util.ArrayList;
import java.util.List;

import co.com.appsource.outsafetyapp.db_helper.SQLOutsafetyHelper;

/**
 * Created by JANUS on 12/12/2015.
 */
public class Insp_ColaboradorDataSource {
    // Database fields
    private SQLiteDatabase database;
    private SQLOutsafetyHelper dbHelper;
    private String[] allColumns = {Insp_Colaborador.COLUMN_ID
            , Insp_Colaborador.COLUMN_ID_PERSONA
            , Insp_Colaborador.COLUMN_NOMBRE
            , Insp_Colaborador.COLUMN_APELLIDO
            , Insp_Colaborador.COLUMN_CEDULA
            , Insp_Colaborador.COLUMN_DIRECCION
            , Insp_Colaborador.COLUMN_TELEFONO
            , Insp_Colaborador.COLUMN_EMAIL
            , Insp_Colaborador.COLUMN_PASSWORD
            , Insp_Colaborador.COLUMN_ID_TIPO_PERSONA
            , Insp_Colaborador.COLUMN_BIT_ACTIVO
            , Insp_Colaborador.COLUMN_DT_FEC_USER
            , Insp_Colaborador.COLUMN_DT_ULTIMA_MODIFICACION
            , Insp_Colaborador.COLUMN_ID_EPS
            , Insp_Colaborador.COLUMN_RUTA_FOTO
            , Insp_Colaborador.COLUMN_ID_EMPRESA
            , Insp_Colaborador.COLUMN_RAZON_SOCIAL
            , Insp_Colaborador.COLUMN_FIRMA
            , Insp_Colaborador.COLUMN_ID_INSPECCION_FK
            , Insp_Colaborador.COLUMN_ID_INSPECCION_PARAM
            , Insp_Colaborador.COLUMN_USUARIO_CREA};

    public Insp_ColaboradorDataSource(Context context) {
        dbHelper = new SQLOutsafetyHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Insp_Colaborador CreateInsp_Colaborador(String bitActivo
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
            , String strRazonSocial
            , String strFirmaBase64
            , long intIdInspeccionFk
            , long intIdInspeccionParamFk
            , String strUsuario) {

        ContentValues values = new ContentValues();
        values.put(Insp_Colaborador.COLUMN_ID_PERSONA, intIdPersona);
        values.put(Insp_Colaborador.COLUMN_NOMBRE, strNombreProfesional);
        values.put(Insp_Colaborador.COLUMN_APELLIDO, strApellidoProfesional);
        values.put(Insp_Colaborador.COLUMN_CEDULA, strCedula);
        values.put(Insp_Colaborador.COLUMN_DIRECCION, strDireccion);
        values.put(Insp_Colaborador.COLUMN_TELEFONO, strTelefono);
        values.put(Insp_Colaborador.COLUMN_EMAIL, strEmail);
        values.put(Insp_Colaborador.COLUMN_PASSWORD, strPassword);
        values.put(Insp_Colaborador.COLUMN_ID_PERSONA, intIdPersona);
        values.put(Insp_Colaborador.COLUMN_BIT_ACTIVO, bitActivo);
        values.put(Insp_Colaborador.COLUMN_DT_FEC_USER, dtfecuser);
        values.put(Insp_Colaborador.COLUMN_DT_ULTIMA_MODIFICACION, dtUltimaModificacion);
        values.put(Insp_Colaborador.COLUMN_ID_EPS, intIdEps);
        values.put(Insp_Colaborador.COLUMN_RUTA_FOTO, strRutaFoto);
        values.put(Insp_Colaborador.COLUMN_ID_EMPRESA, intIdEmpresa);
        values.put(Insp_Colaborador.COLUMN_RAZON_SOCIAL, strRazonSocial);
        values.put(Insp_Colaborador.COLUMN_FIRMA, strFirmaBase64);
        values.put(Insp_Colaborador.COLUMN_ID_INSPECCION_FK, intIdInspeccionFk);
        values.put(Insp_Colaborador.COLUMN_ID_INSPECCION_PARAM, intIdInspeccionParamFk);
        values.put(Insp_Colaborador.COLUMN_USUARIO_CREA, strUsuario);

        long insertId = database.insert(Insp_Colaborador.NAME
                , null,
                values);
        Cursor cursor = database.query(Insp_Colaborador.NAME
                , allColumns
                , Insp_Colaborador.COLUMN_ID + " = " + insertId
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Insp_Colaborador newPersona = cursorToInsp_Colaborador(cursor);
        cursor.close();
        return newPersona;
    }

    public Insp_Colaborador GetInsp_Colaborador(String intIdInspeccionFk) {

        Cursor cursor = database.query(Insp_Colaborador.NAME
                , allColumns
                , Insp_Colaborador.COLUMN_ID_INSPECCION_FK + " = " + intIdInspeccionFk
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Insp_Colaborador newInsp_Colaborador = cursorToInsp_Colaborador(cursor);
        cursor.close();
        return newInsp_Colaborador;
    }

    public List<Insp_Colaborador> GetInsp_Colaborador(long intIdInspeccionFk) {

        List<Insp_Colaborador> lstColaboradores = new ArrayList<Insp_Colaborador>();
        Cursor cursor = database.query(Insp_Colaborador.NAME
                , allColumns
                , Insp_Colaborador.COLUMN_ID_INSPECCION_FK + " = " + intIdInspeccionFk
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Insp_Colaborador newInsp_Colaborador = cursorToInsp_Colaborador(cursor);
            lstColaboradores.add(newInsp_Colaborador);
            cursor.moveToNext();
        }
        cursor.close();
        return lstColaboradores;
    }

    private Insp_Colaborador cursorToInsp_Colaborador(Cursor cursor) {
        Insp_Colaborador comment = new Insp_Colaborador();

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
        comment.setStrFirmaBase64(cursor.getString(17));
        comment.setIntIdInspeccionFk(cursor.getLong(18));
        comment.setIntIdInspeccionParamFk(cursor.getLong(19));
        comment.setStrUsuario(cursor.getString(20));

        return comment;
    }

    public static String GenerarDataSet(List<Insp_Colaborador> lstColaboradores) {
        String serlializedDataSet = "<?xml version=\"1.0\" standalone=\"yes\"?><NewDataSet>";

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {

            serializer.setOutput(writer);
            for (Insp_Colaborador itemColaborador :
                    lstColaboradores) {

                serializer.startTag("", "Colaboradores");
                serializer.startTag("", "ConcInspeccion");
                serializer.text(Long.toString(itemColaborador.getIntIdInspeccionFk()));
                serializer.endTag("", "ConcInspeccion");
                serializer.startTag("", "idUsuario");
                serializer.text(itemColaborador.getStrUsuario());
                serializer.endTag("", "idUsuario");
                serializer.startTag("", "idInspeccion");
                serializer.text(Long.toString(itemColaborador.getIntIdInspeccionParamFk()));
                serializer.endTag("", "idInspeccion");
                serializer.startTag("", "idColaborador");
                serializer.text(itemColaborador.getStrCedula());
                serializer.endTag("", "idColaborador");
                serializer.startTag("", "Firma");
                serializer.text(itemColaborador.getStrFirmaBase64() == null ? "" : itemColaborador.getStrFirmaBase64());
                serializer.endTag("", "Firma");
                serializer.endTag("", "Colaboradores");

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

    public static String GetJson(Insp_Colaborador obj) {
        String strJson = "";
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        strJson = gson.toJson(obj);
        return strJson;
    }

    public List<Insp_Colaborador> GetAll() {
        List<Insp_Colaborador> lstResult = new ArrayList<Insp_Colaborador>();
        Cursor cursor = database.query(Insp_Colaborador.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Insp_Colaborador newItemResult = cursorToInsp_Colaborador(cursor);
            lstResult.add(newItemResult);
            cursor.moveToNext();
        }
        cursor.close();
        return lstResult;
    }

    public int GetCount() {
        Cursor cursor = database.query(Insp_Colaborador.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);

        return cursor.getCount();
    }

    public void DeleteInsp_ColaboradorByConsecutivoInsp(long idConsecutivoInsp) {
        database.execSQL("delete from " + Insp_Colaborador.NAME + " where " + Insp_Colaborador.COLUMN_ID_INSPECCION_FK + "=" + idConsecutivoInsp);
    }

    public void truncateTable() {
        database.execSQL("delete from " + Insp_Colaborador.NAME);
    }
}
