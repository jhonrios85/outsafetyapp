package co.com.appsource.outsafetyapp.db_helper.Tables;

/**
 * Created by JANUS on 25/11/2015.
 */
public class CentroTrabajo {

    public static final String DATABASE_CREATE_TBL_CENTRO_TRABAJO = "CREATE TABLE " + CentroTrabajo.NAME + "(" +
            CentroTrabajo.COLUMN_ID + " integer primary key autoincrement," +
            CentroTrabajo.COLUMN_ID_EMPRESA + " text null," +
            CentroTrabajo.COLUMN_ID_PERSONA + " text null," +
            CentroTrabajo.COLUMN_RAZON_SOCIAL + " text null);";

    public static final String NAME = "aTblCentroTrabajo";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ID_EMPRESA = "intIdEmpresa";
    public static final String COLUMN_ID_PERSONA = "intIdPersona";
    public static final String COLUMN_RAZON_SOCIAL = "strRazonSocial";

    private String intIdEmpresa;
    private String intIdPersona;
    private String strRazonSocial;
    private long id;
    private boolean boolSelectedCt;

    public boolean isBoolSelectedCt() {
        return boolSelectedCt;
    }

    public void setBoolSelectedCt(boolean boolSelectedCt) {
        this.boolSelectedCt = boolSelectedCt;
    }

    public String getIntIdEmpresa() {
        return intIdEmpresa;
    }

    public void setIntIdEmpresa(String intIdEmpresa) {
        this.intIdEmpresa = intIdEmpresa;
    }

    public String getIntIdPersona() {
        return intIdPersona;
    }

    public void setIntIdPersona(String intIdPersona) {
        this.intIdPersona = intIdPersona;
    }

    public String getStrRazonSocial() {
        return strRazonSocial;
    }

    public void setStrRazonSocial(String strRazonSocial) {
        this.strRazonSocial = strRazonSocial;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.getStrRazonSocial();
    }

}
