package co.com.appsource.outsafetyapp.db_helper.Tables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by JANUS on 12/12/2015.
 * Equivale al archivo InspeccionesGuardadas.xml
 */
/*<?xml version="1.0" standalone="yes"?>
<NewDataSet>
<inspeccion>
<ConcInspeccion>1</ConcInspeccion>
<idInspeccion>1320</idInspeccion>
<idParametro>18330</idParametro>
<bitCumple>True</bitCumple>
</inspeccion>
<inspeccion>
<ConcInspeccion>1</ConcInspeccion>
<idInspeccion>1320</idInspeccion>
<idParametro>18331</idParametro>
<bitCumple>True</bitCumple>
</inspeccion>
<inspeccion>
<ConcInspeccion>1</ConcInspeccion>
<idInspeccion>1320</idInspeccion>
<idParametro>18332</idParametro>
<bitCumple>True</bitCumple>
</inspeccion>
<inspeccion>
<ConcInspeccion>1</ConcInspeccion>
<idInspeccion>1320</idInspeccion>
<idParametro>18333</idParametro>
<bitCumple>True</bitCumple>
</inspeccion>
<inspeccion>
<ConcInspeccion>1</ConcInspeccion>
<idInspeccion>1320</idInspeccion>
<idParametro>18334</idParametro>
<bitCumple>True</bitCumple>
</inspeccion>
<inspeccion>
<ConcInspeccion>1</ConcInspeccion>
<idInspeccion>1320</idInspeccion>
<idParametro>18335</idParametro>
<bitCumple>True</bitCumple>
</inspeccion>
<inspeccion>
<ConcInspeccion>1</ConcInspeccion>
<idInspeccion>1320</idInspeccion>
<idParametro>18336</idParametro>
<bitCumple>True</bitCumple>
</inspeccion>
<inspeccion>
<ConcInspeccion>1</ConcInspeccion>
<idInspeccion>1320</idInspeccion>
<idParametro>18337</idParametro>
<bitCumple>True</bitCumple>
</inspeccion>
<inspeccion>
<ConcInspeccion>1</ConcInspeccion>
<idInspeccion>1320</idInspeccion>
<idParametro>18338</idParametro>
<bitCumple>True</bitCumple>
</inspeccion>
<inspeccion>
<ConcInspeccion>1</ConcInspeccion>
<idInspeccion>1320</idInspeccion>
<idParametro>18339</idParametro>
<bitCumple>True</bitCumple>
</inspeccion>
<inspeccion>
<ConcInspeccion>1</ConcInspeccion>
<idInspeccion>1320</idInspeccion>
<idParametro>18348</idParametro>
<bitCumple>True</bitCumple>
</inspeccion>
</NewDataSet>*/
public class Insp_Parametro {

    public static final String DATABASE_CREATE_TBL_INSP_PARAMETRO = "CREATE TABLE " + Insp_Parametro.NAME + "(" +
            Insp_Parametro.COLUMN_ID + " integer primary key autoincrement," +
            Insp_Parametro.COLUMN_ID_PARAMETRO + " text null," +
            Insp_Parametro.COLUMN_ID_INSPECCION + " text null," +
            Insp_Parametro.COLUMN_ID_RIESGO + " text null," +
            Insp_Parametro.COLUMN_DESC_PARAMETRO + " text null," +
            Insp_Parametro.COLUMN_RUTA_IMAGEN + " text null," +
            Insp_Parametro.COLUMN_ID_EMPRESA + " text null," +
            Insp_Parametro.COLUMN_FEC_USER + " text null," +
            Insp_Parametro.COLUMN_CUMPLE + " text null," +
            Insp_Parametro.COLUMN_ID_INSPECCION_FK + " integer null);";

    public static final String NAME = "tblInsp_Parametro";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ID_PARAMETRO = "intIdParametro";
    public static final String COLUMN_ID_INSPECCION = "intIdInspeccion";
    public static final String COLUMN_ID_RIESGO = "intIdRiesgo";
    public static final String COLUMN_DESC_PARAMETRO = "strDescripcionParametro";
    public static final String COLUMN_RUTA_IMAGEN = "strRutaImagen";
    public static final String COLUMN_ID_EMPRESA = "intIdEmpresa";
    public static final String COLUMN_FEC_USER = "dtfecuser";
    public static final String COLUMN_CUMPLE = "boolCumple";
    public static final String COLUMN_ID_INSPECCION_FK = "intIdInspeccionFk";

    private long id;

    @Expose
    @SerializedName("idParametro")
    private String intIdParametro;


    private String intIdInspeccion;
    private String intIdRiesgo;
    private String strDescripcionParametro;
    private String strRutaImagen;
    private String intIdEmpresa;
    private String dtfecuser;

    @Expose
    @SerializedName("bitCumple")
    private boolean boolCumple = true;


    private long intIdInspeccionFk;



    public long getIntIdInspeccionFk() {
        return intIdInspeccionFk;
    }

    public void setIntIdInspeccionFk(long intIdInspeccionFk) {
        this.intIdInspeccionFk = intIdInspeccionFk;
    }

    public boolean isBoolCumple() {
        return boolCumple;
    }

    public void setBoolCumple(boolean boolCumple) {
        this.boolCumple = boolCumple;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIntIdParametro() {
        return intIdParametro;
    }

    public void setIntIdParametro(String intIdParametro) {
        this.intIdParametro = intIdParametro;
    }

    public String getIntIdInspeccion() {
        return intIdInspeccion;
    }

    public void setIntIdInspeccion(String intIdInspeccion) {
        this.intIdInspeccion = intIdInspeccion;
    }

    public String getIntIdRiesgo() {
        return intIdRiesgo;
    }

    public void setIntIdRiesgo(String intIdRiesgo) {
        this.intIdRiesgo = intIdRiesgo;
    }

    public String getStrDescripcionParametro() {
        return strDescripcionParametro;
    }

    public void setStrDescripcionParametro(String strDescripcionParametro) {
        this.strDescripcionParametro = strDescripcionParametro;
    }

    public String getStrRutaImagen() {
        return strRutaImagen;
    }

    public void setStrRutaImagen(String strRutaImagen) {
        this.strRutaImagen = strRutaImagen;
    }

    public String getIntIdEmpresa() {
        return intIdEmpresa;
    }

    public void setIntIdEmpresa(String intIdEmpresa) {
        this.intIdEmpresa = intIdEmpresa;
    }

    public String getDtfecuser() {
        return dtfecuser;
    }

    public void setDtfecuser(String dtfecuser) {
        this.dtfecuser = dtfecuser;
    }
}
