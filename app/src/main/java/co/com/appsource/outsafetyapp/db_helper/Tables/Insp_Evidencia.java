package co.com.appsource.outsafetyapp.db_helper.Tables;

/**
 * Created by JANUS on 12/12/2015.
 * Equivale al archivo EvidenciasGuardadas.xml
 */
/*<?xml version="1.0" standalone="yes"?>
<NewDataSet>
<Evidencias>
<ConcInspeccion>1</ConcInspeccion>
<idUsuario>1036615169</idUsuario>
<idInspeccion>1320</idInspeccion>
<Evidencia>/9j/4AAQSkZJRgABAQEAYABgAAD/</Evidencia>
</Evidencias>
<Evidencias>
<ConcInspeccion>1</ConcInspeccion>
<idUsuario>1036615169</idUsuario>
<idInspeccion>1320</idInspeccion>
<Evidencia>/9j/4AAQSkZJRgABAQEAYABgAAD/</Evidencia>
</Evidencias>
</NewDataSet>*/
public class Insp_Evidencia {

    public static final String DATABASE_CREATE_TBL_INSP_EVIDENCIA = "CREATE TABLE " + Insp_Evidencia.NAME + "(" +
            Insp_Evidencia.COLUMN_ID + " integer primary key autoincrement," +
            Insp_Evidencia.COLUMN_EVIDENCIA + " text null," +
            Insp_Evidencia.COLUMN_ID_INSPECCION_FK + " integer null," +
            Insp_Evidencia.COLUMN_CEDULA + " text null," +
            Insp_Evidencia.COLUMN_ID_INSPECCION_PARAM + " integer null," +
            Insp_Evidencia.COLUMN_USUARIO_CREA + " text null);";

    public static final String NAME = "tblInsp_Evidencia";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EVIDENCIA = "strEvidenciaBase64";
    public static final String COLUMN_ID_INSPECCION_FK = "intIdInspeccionFk";
    public static final String COLUMN_CEDULA = "strCedula";
    public static final String COLUMN_ID_INSPECCION_PARAM = "intIdInspeccionParamFk";
    public static final String COLUMN_USUARIO_CREA = "strUsuario";

    private long id;
    private String strEvidenciaBase64;
    private long intIdInspeccionFk;
    String strCedula;
    private long intIdInspeccionParamFk;
    String strUsuario;

    public String getStrUsuario() {
        return strUsuario;
    }

    public void setStrUsuario(String strUsuario) {
        this.strUsuario = strUsuario;
    }

    public long getIntIdInspeccionParamFk() {
        return intIdInspeccionParamFk;
    }

    public void setIntIdInspeccionParamFk(long intIdInspeccionParamFk) {
        this.intIdInspeccionParamFk = intIdInspeccionParamFk;
    }

    public String getStrCedula() {
        return strCedula;
    }

    public void setStrCedula(String strCedula) {
        this.strCedula = strCedula;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStrEvidenciaBase64() {
        return strEvidenciaBase64;
    }

    public void setStrEvidenciaBase64(String strEvidenciaBase64) {
        this.strEvidenciaBase64 = strEvidenciaBase64;
    }

    public long getIntIdInspeccionFk() {
        return intIdInspeccionFk;
    }

    public void setIntIdInspeccionFk(long intIdInspeccionFk) {
        this.intIdInspeccionFk = intIdInspeccionFk;
    }


}
