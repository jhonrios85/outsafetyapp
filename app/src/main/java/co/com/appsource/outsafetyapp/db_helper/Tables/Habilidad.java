package co.com.appsource.outsafetyapp.db_helper.Tables;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JANUS on 31/12/2015.
 */
public class Habilidad {

    public static final String DATABASE_CREATE_TBL_HABILIDAD = "CREATE TABLE " + Habilidad.NAME + "(" +
            Habilidad.COLUMN_ID + " integer primary key autoincrement," +
            Habilidad.COLUMN_ID_CENTRO_TRABAJO + " text null," +
            Habilidad.COLUMN_ID_EMPRESA + " text null," +
            Habilidad.COLUMN_RAZON_SOCIAL + " text null," +
            Habilidad.COLUMN_NOMBRE + " text null," +
            Habilidad.COLUMN_APELLIDO + " text null," +
            Habilidad.COLUMN_CEDULA + " text null," +
            Habilidad.COLUMN_ID_HABILIDAD + " text null," +
            Habilidad.COLUMN_DESCR_HABILIDAD + " text null);";

    public static final String NAME = "aTblHabilidad";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ID_CENTRO_TRABAJO = "intIdCentroTrabajo";
    public static final String COLUMN_ID_EMPRESA = "intIdEmpresa";
    public static final String COLUMN_RAZON_SOCIAL = "strRazonSocial";
    public static final String COLUMN_NOMBRE = "strNombreProfesional";
    public static final String COLUMN_APELLIDO = "strApellidoProfesional";
    public static final String COLUMN_CEDULA = "strCedula";
    public static final String COLUMN_ID_HABILIDAD = "intIdHabilidad";
    public static final String COLUMN_DESCR_HABILIDAD = "strDescripcionHabilidad";

    long id;

    @SerializedName("intIdCentroTrabajo")
    String intIdCentroTrabajo;

    @SerializedName("intIdEmpresa")
    String intIdEmpresa;

    @SerializedName("strRazonSocial")
    String strRazonSocial;

    @SerializedName("strNombreProfesional")
    String strNombreProfesional;

    @SerializedName("strApellidoProfesional")
    String strApellidoProfesional;

    @SerializedName("strCedula")
    String strCedula;

    @SerializedName("intIdHabilidad")
    String intIdHabilidad;

    @SerializedName("strDescripcionHabilidad")
    String strDescripcionHabilidad;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIntIdCentroTrabajo() {
        return intIdCentroTrabajo;
    }

    public void setIntIdCentroTrabajo(String intIdCentroTrabajo) {
        this.intIdCentroTrabajo = intIdCentroTrabajo;
    }

    public String getIntIdEmpresa() {
        return intIdEmpresa;
    }

    public void setIntIdEmpresa(String intIdEmpresa) {
        this.intIdEmpresa = intIdEmpresa;
    }

    public String getStrRazonSocial() {
        return strRazonSocial;
    }

    public void setStrRazonSocial(String strRazonSocial) {
        this.strRazonSocial = strRazonSocial;
    }

    public String getStrNombreProfesional() {
        return strNombreProfesional;
    }

    public void setStrNombreProfesional(String strNombreProfesional) {
        this.strNombreProfesional = strNombreProfesional;
    }

    public String getStrApellidoProfesional() {
        return strApellidoProfesional;
    }

    public void setStrApellidoProfesional(String strApellidoProfesional) {
        this.strApellidoProfesional = strApellidoProfesional;
    }

    public String getStrCedula() {
        return strCedula;
    }

    public void setStrCedula(String strCedula) {
        this.strCedula = strCedula;
    }

    public String getIntIdHabilidad() {
        return intIdHabilidad;
    }

    public void setIntIdHabilidad(String intIdHabilidad) {
        this.intIdHabilidad = intIdHabilidad;
    }

    public String getStrDescripcionHabilidad() {
        return strDescripcionHabilidad;
    }

    public void setStrDescripcionHabilidad(String strDescripcionHabilidad) {
        this.strDescripcionHabilidad = strDescripcionHabilidad;
    }

    @Override
    public boolean equals(Object obj) {
        Habilidad other = (Habilidad) obj;
        return this.getIntIdHabilidad() == other.getIntIdHabilidad();
    }

    @Override
    public int hashCode() {
        return this.getIntIdHabilidad().hashCode();
    }

    @Override
    public String toString() {
        return this.strDescripcionHabilidad;
    }
}
