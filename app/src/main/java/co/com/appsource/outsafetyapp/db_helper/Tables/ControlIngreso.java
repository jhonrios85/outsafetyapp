package co.com.appsource.outsafetyapp.db_helper.Tables;

/**
 * Created by JANUS on 18/01/2016.
 */
public class ControlIngreso {

    public static final String DATABASE_CREATE_TBL_CONTROL_INGRESO = "CREATE TABLE " + ControlIngreso.NAME + "(" +
            ControlIngreso.COLUMN_ID + " integer primary key autoincrement," +
            ControlIngreso.COLUMN_TIPO_ACCESO + " text null," +
            ControlIngreso.COLUMN_TIPO + " text null," +
            ControlIngreso.COLUMN_ID_USUARIO + " text null," +
            ControlIngreso.COLUMN_FECHA + " text null," +
            ControlIngreso.COLUMN_ID_CENTRO_TRABAJO + " text null," +
            ControlIngreso.COLUMN_ID_VIGILANTE + " text null," +
            ControlIngreso.COLUMN_ID_SALIDA + " integer null);";

    public static final String NAME = "aTblControlIngreso";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIPO_ACCESO = "strTipoAcceso";
    public static final String COLUMN_TIPO = "strTipo";
    public static final String COLUMN_ID_USUARIO = "strIdUsuario";
    public static final String COLUMN_FECHA = "strFecha";
    public static final String COLUMN_ID_CENTRO_TRABAJO = "intIdCentroTrabajo";
    public static final String COLUMN_ID_VIGILANTE = "strIdVigilante";
    public static final String COLUMN_ID_SALIDA = "intIdSalida";


    long id;
    String strTipoAcceso;
    String strTipo;
    String strIdUsuario;
    String strFecha;
    String intIdCentroTrabajo;
    String strIdVigilante;
    long intIdSalida;

    public long getIntIdSalida() {
        return intIdSalida;
    }

    public void setIntIdSalida(long intIdSalida) {
        this.intIdSalida = intIdSalida;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStrTipoAcceso() {
        return strTipoAcceso;
    }

    public void setStrTipoAcceso(String strTipoAcceso) {
        this.strTipoAcceso = strTipoAcceso;
    }

    public String getStrTipo() {
        return strTipo;
    }

    public void setStrTipo(String strTipo) {
        this.strTipo = strTipo;
    }

    public String getStrIdUsuario() {
        return strIdUsuario;
    }

    public void setStrIdUsuario(String strIdUsuario) {
        this.strIdUsuario = strIdUsuario;
    }

    public String getStrFecha() {
        return strFecha;
    }

    public void setStrFecha(String strFecha) {
        this.strFecha = strFecha;
    }

    public String getIntIdCentroTrabajo() {
        return intIdCentroTrabajo;
    }

    public void setIntIdCentroTrabajo(String intIdCentroTrabajo) {
        this.intIdCentroTrabajo = intIdCentroTrabajo;
    }

    public String getStrIdVigilante() {
        return strIdVigilante;
    }

    public void setStrIdVigilante(String strIdVigilante) {
        this.strIdVigilante = strIdVigilante;
    }

}
