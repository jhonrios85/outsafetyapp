package co.com.appsource.outsafetyapp.db_helper.Tables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by JANUS on 12/12/2015.
 * Equivale al archivo InspecGuardadas.xml
 */
/*<?xml version="1.0" standalone="yes"?>
<NewDataSet>
<inspeccion>
<ConcInspeccion>1</ConcInspeccion>
<idUsuario>1036615169</idUsuario>
<idCentroTrabajo>141</idCentroTrabajo>
<idRiesgo>72</idRiesgo>
<idInspeccion>1320</idInspeccion>
<Observacion>Prueba Android</Observacion>
<intIdArea>68</intIdArea>
</inspeccion>
</NewDataSet>*/
public class Insp_Inspeccion {

    public static final String DATABASE_CREATE_TBL_INSP_INSPECCION = "CREATE TABLE " + Insp_Inspeccion.NAME + "(" +
            Insp_Inspeccion.COLUMN_ID + " integer primary key autoincrement," +
            Insp_Inspeccion.COLUMN_USUARIO_CREA + " text null," +
            Insp_Inspeccion.COLUMN_ID_CENTRO_TRABAJO + " integer null," +
            Insp_Inspeccion.COLUMN_ID_AREA + " integer null," +
            Insp_Inspeccion.COLUMN_ID_RIESGO + " integer null," +
            Insp_Inspeccion.COLUMN_ID_INSPECCION + " integer null," +
            Insp_Inspeccion.COLUMN_OBSERVACION_GENERAL + " text null," +
            Insp_Inspeccion.COLUMN_GUARDADA_CON_EXITO + " text null" + ");";

    public static final String NAME = "tblInsp_Inspeccion";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USUARIO_CREA = "strUsuario";
    public static final String COLUMN_ID_CENTRO_TRABAJO = "intIdCentroTrabajo";
    public static final String COLUMN_ID_AREA = "intIdArea";
    public static final String COLUMN_ID_RIESGO = "intIdRiesgo";
    public static final String COLUMN_ID_INSPECCION = "intIdInspeccion";
    public static final String COLUMN_OBSERVACION_GENERAL = "strObservacionGeneral";
    public static final String COLUMN_GUARDADA_CON_EXITO = "strGuardadaConExito";

    private long id;

    @Expose
    @SerializedName("idUsuario")
    private String strUsuario;

    @Expose
    @SerializedName("idCentroTrabajo")
    private long intIdCentroTrabajo;

    @Expose
    @SerializedName("intIdArea")
    private long intIdArea;

    @Expose
    @SerializedName("idRiesgo")
    private long intIdRiesgo;

    @Expose
    @SerializedName("idInspeccion")
    private long intIdInspeccion;

    @Expose
    @SerializedName("Observacion")
    private String strObservacionGeneral;

    public String getStrGuardadaConExito() {
        return strGuardadaConExito;
    }

    public void setStrGuardadaConExito(String strGuardadaConExito) {
        this.strGuardadaConExito = strGuardadaConExito;
    }

    private String strGuardadaConExito;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStrUsuario() {
        return strUsuario;
    }

    public void setStrUsuario(String strUsuario) {
        this.strUsuario = strUsuario;
    }

    public long getIntIdCentroTrabajo() {
        return intIdCentroTrabajo;
    }

    public void setIntIdCentroTrabajo(long intIdCentroTrabajo) {
        this.intIdCentroTrabajo = intIdCentroTrabajo;
    }

    public long getIntIdArea() {
        return intIdArea;
    }

    public void setIntIdArea(long intIdArea) {
        this.intIdArea = intIdArea;
    }

    public long getIntIdRiesgo() {
        return intIdRiesgo;
    }

    public void setIntIdRiesgo(long intIdRiesgo) {
        this.intIdRiesgo = intIdRiesgo;
    }

    public long getIntIdInspeccion() {
        return intIdInspeccion;
    }

    public void setIntIdInspeccion(long intIdInspeccion) {
        this.intIdInspeccion = intIdInspeccion;
    }

    public String getStrObservacionGeneral() {
        return strObservacionGeneral;
    }

    public void setStrObservacionGeneral(String strObservacionGeneral) {
        this.strObservacionGeneral = strObservacionGeneral;
    }


}
