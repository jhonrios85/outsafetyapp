package co.com.appsource.outsafetyapp.db_helper.Tables;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JANUS on 26/11/2015.
 */
public class Area {

    public static final String DATABASE_CREATE_TBL_AREA = "CREATE TABLE " + Area.NAME + "(" +
            Area.COLUMN_ID + " integer primary key autoincrement," +
            Area.COLUMN_ID_EMPRESA + " text null," +
            Area.COLUMN_ID_AREA + " text null," +
            Area.COLUMN_DESCRIPCION + " text null);";

    public static final String NAME = "aTblArea";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ID_EMPRESA = "intIdEmpresa";
    public static final String COLUMN_ID_AREA = "intIdArea";
    public static final String COLUMN_DESCRIPCION = "strDescripcion";

    private long id;

    @SerializedName("intIdEmpresa")
    private String intIdEmpresa;

    @SerializedName("intIdArea")
    private String intIdArea;

    @SerializedName("strDescripcion")
    private String strDescripcion;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIntIdEmpresa() {
        return intIdEmpresa;
    }

    public void setIntIdEmpresa(String intIdEmpresa) {
        this.intIdEmpresa = intIdEmpresa;
    }

    public String getIntIdArea() {
        return intIdArea;
    }

    public void setIntIdArea(String intIdArea) {
        this.intIdArea = intIdArea;
    }

    public String getStrDescripcion() {
        return strDescripcion;
    }

    public void setStrDescripcion(String strDescripcion) {
        this.strDescripcion = strDescripcion;
    }

    @Override
    public String toString() {
        return this.strDescripcion;
    }
}
