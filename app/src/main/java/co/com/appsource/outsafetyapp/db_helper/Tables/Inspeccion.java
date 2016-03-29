package co.com.appsource.outsafetyapp.db_helper.Tables;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JANUS on 27/11/2015.
 */
public class Inspeccion {

    public static final String DATABASE_CREATE_TBL_INSPECCION = "CREATE TABLE " + Inspeccion.NAME + "(" +
            Inspeccion.COLUMN_ID + " integer primary key autoincrement," +
            Inspeccion.COLUMN_ID_EMPRESA + " text null," +
            Inspeccion.COLUMN_ID_INSPECCION + " text null," +
            Inspeccion.COLUMN_DESC_INSPECCION + " text null," +
            Inspeccion.COLUMN_ID_RIESGO + " text null);";

    public static final String NAME = "aTblInspeccion";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ID_EMPRESA = "intIdEmpresa";
    public static final String COLUMN_ID_INSPECCION = "intIdInspeccion";
    public static final String COLUMN_DESC_INSPECCION = "strDescripcionInspeccion";
    public static final String COLUMN_ID_RIESGO = "intIdRiesgo";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;

    @SerializedName("intIdEmpresa")
    private String intIdEmpresa;

    @SerializedName("intIdInspeccion")
    private String intIdInspeccion;

    @SerializedName("strDescripcionInspeccion")
    private String strDescripcionInspeccion;

    @SerializedName("intIdRiesgo")
    private String intIdRiesgo;

    public String getIntIdEmpresa() {
        return intIdEmpresa;
    }

    public void setIntIdEmpresa(String intIdEmpresa) {
        this.intIdEmpresa = intIdEmpresa;
    }

    public String getIntIdInspeccion() {
        return intIdInspeccion;
    }

    public void setIntIdInspeccion(String intIdInspeccion) {
        this.intIdInspeccion = intIdInspeccion;
    }

    public String getStrDescripcionInspeccion() {
        return strDescripcionInspeccion;
    }

    public void setStrDescripcionInspeccion(String strDescripcionInspeccion) {
        this.strDescripcionInspeccion = strDescripcionInspeccion;
    }

    public String getIntIdRiesgo() {
        return intIdRiesgo;
    }

    public void setIntIdRiesgo(String intIdRiesgo) {
        this.intIdRiesgo = intIdRiesgo;
    }

    @Override
    public String toString() {
        return this.strDescripcionInspeccion;
    }

}
