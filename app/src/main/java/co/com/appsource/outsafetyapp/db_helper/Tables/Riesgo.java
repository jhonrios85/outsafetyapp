package co.com.appsource.outsafetyapp.db_helper.Tables;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JANUS on 27/11/2015.
 */
public class Riesgo {

    public static final String DATABASE_CREATE_TBL_RIESGO = "CREATE TABLE " + Riesgo.NAME + "(" +
            Riesgo.COLUMN_ID + " integer primary key autoincrement," +
            Riesgo.COLUMN_ID_RIESGO + " text null," +
            Riesgo.COLUMN_DESCRIPCION_RIESGO + " text null," +
            Riesgo.COLUMN_ID_CENTRO_TRABAJO + " text null);";

    public static final String NAME = "aTblRiesgo";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ID_RIESGO = "intIdRiesgo";
    public static final String COLUMN_DESCRIPCION_RIESGO = "strDescripcionRiesgo";
    public static final String COLUMN_ID_CENTRO_TRABAJO = "strIdCentroTrabajo";

    private long id;

    @SerializedName("intIdRiesgo")
    private String intIdRiesgo;

    @SerializedName("strDescripcionRiesgo")
    private String strDescripcionRiesgo;

    @SerializedName("strIdCentroTrabajo")
    private String strIdCentroTrabajo;

    public String getStrIdCentroTrabajo() {
        return strIdCentroTrabajo;
    }

    public void setStrIdCentroTrabajo(String strIdCentroTrabajo) {
        this.strIdCentroTrabajo = strIdCentroTrabajo;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIntIdRiesgo() {
        return intIdRiesgo;
    }

    public void setIntIdRiesgo(String intIdRiesgo) {
        this.intIdRiesgo = intIdRiesgo;
    }

    public String getStrDescripcionRiesgo() {
        return strDescripcionRiesgo;
    }

    public void setStrDescripcionRiesgo(String strDescripcionRiesgo) {
        this.strDescripcionRiesgo = strDescripcionRiesgo;
    }

    @Override
    public String toString() {
        return this.strDescripcionRiesgo;
    }

}
