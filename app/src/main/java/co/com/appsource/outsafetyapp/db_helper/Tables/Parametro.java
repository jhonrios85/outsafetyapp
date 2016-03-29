package co.com.appsource.outsafetyapp.db_helper.Tables;

/**
 * Created by JANUS on 26/11/2015.
 */
public class Parametro {

    public static final String DATABASE_CREATE_TBL_PARAMETRO = "CREATE TABLE " + Parametro.NAME + "(" +
            Parametro.COLUMN_ID + " integer primary key autoincrement," +
            Parametro.COLUMN_ID_PARAMETRO + " text null," +
            Parametro.COLUMN_ID_INSPECCION + " text null," +
            Parametro.COLUMN_ID_RIESGO + " text null," +
            Parametro.COLUMN_DESC_PARAMETRO + " text null," +
            Parametro.COLUMN_RUTA_IMAGEN + " text null," +
            Parametro.COLUMN_ID_EMPRESA + " text null," +
            Parametro.COLUMN_FEC_USER + " text null," +
            Parametro.COLUMN_CUMPLE + " text null);";

    public static final String NAME = "aTblParametro";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ID_PARAMETRO = "intIdParametro";
    public static final String COLUMN_ID_INSPECCION = "intIdInspeccion";
    public static final String COLUMN_ID_RIESGO = "intIdRiesgo";
    public static final String COLUMN_DESC_PARAMETRO = "strDescripcionParametro";
    public static final String COLUMN_RUTA_IMAGEN = "strRutaImagen";
    public static final String COLUMN_ID_EMPRESA = "intIdEmpresa";
    public static final String COLUMN_FEC_USER = "dtfecuser";
    public static final String COLUMN_CUMPLE = "boolCumple";

    private long id;
    private String intIdParametro;
    private String intIdInspeccion;
    private String intIdRiesgo;
    private String strDescripcionParametro;
    private String strRutaImagen;
    private String intIdEmpresa;
    private String dtfecuser;
    private boolean boolCumple = true;

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
