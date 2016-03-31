package co.com.appsource.outsafetyapp.db_helper.Tables;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 30/03/2016.
 */
public class Empresa {

    public static final String DATABASE_CREATE_TBL_EMPRESA = "CREATE TABLE " + Empresa.NAME + "(" +
            Empresa.COLUMN_ID + " integer primary key autoincrement," +
            Empresa.COLUMN_ID_EMPRESA + " text null," +

            Empresa.COLUMN_NIT + " text null," +
            Empresa.COLUMN_RAZON_SOCIAL + " text null," +
            Empresa.COLUMN_REPRESENTANTE_LEGAL + " text null," +
            Empresa.COLUMN_EMAIL + " text null," +
            Empresa.COLUMN_RESPONSABLE_SISO + " text null," +
            Empresa.COLUMN_EMAIL_RESPONSABLE_SISO + " text null," +
            Empresa.COLUMN_ESTADO + " text null," +
            Empresa.COLUMN_ID_TIPO_EMPRESA + " text null);";

    public static final String NAME = "aTblEmpresa";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ID_EMPRESA = "intIdEmpresa";
    public static final String COLUMN_NIT = "strNit";
    public static final String COLUMN_RAZON_SOCIAL = "strRazonSocial";
    public static final String COLUMN_REPRESENTANTE_LEGAL = "strRepresentanteLegal";
    public static final String COLUMN_EMAIL = "strEmail";
    public static final String COLUMN_RESPONSABLE_SISO = "strResponsableSiso";
    public static final String COLUMN_EMAIL_RESPONSABLE_SISO = "strEmailResponsableSiso";
    public static final String COLUMN_ESTADO = "boolEstado";
    public static final String COLUMN_ID_TIPO_EMPRESA = "intIdTipoEmpresa";

    private long id;

    @SerializedName("IdEmpresa")
    private String intIdEmpresa;

    @SerializedName("NIT")
    private String strNit;

    @SerializedName("RazonSocial")
    private String strRazonSocial;


    @SerializedName("RepresentanteLegal")
    private String strRepresentanteLegal;

    @SerializedName("Email")
    private String strEmail;

    @SerializedName("ResponsableSISO")
    private String strResponsableSiso;

    @SerializedName("EmailResponsableSISO")
    private String strEmailResponsableSiso;

    @SerializedName("Estado")
    private String boolEstado;

    @SerializedName("TipoEmpresa")
    private String intIdTipoEmpresa;


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

    public String getStrNit() {
        return strNit;
    }

    public void setStrNit(String strNit) {
        this.strNit = strNit;
    }

    public String getStrRazonSocial() {
        return strRazonSocial;
    }

    public void setStrRazonSocial(String strRazonSocial) {
        this.strRazonSocial = strRazonSocial;
    }

    public String getStrRepresentanteLegal() {
        return strRepresentanteLegal;
    }

    public void setStrRepresentanteLegal(String strRepresentanteLegal) {
        this.strRepresentanteLegal = strRepresentanteLegal;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public void setStrEmail(String strEmail) {
        this.strEmail = strEmail;
    }

    public String getStrResponsableSiso() {
        return strResponsableSiso;
    }

    public void setStrResponsableSiso(String strResponsableSiso) {
        this.strResponsableSiso = strResponsableSiso;
    }

    public String getStrEmailResponsableSiso() {
        return strEmailResponsableSiso;
    }

    public void setStrEmailResponsableSiso(String strEmailResponsableSiso) {
        this.strEmailResponsableSiso = strEmailResponsableSiso;
    }

    public String getBoolEstado() {
        return boolEstado;
    }

    public void setBoolEstado(String boolEstado) {
        this.boolEstado = boolEstado;
    }

    public String getIntIdTipoEmpresa() {
        return intIdTipoEmpresa;
    }

    public void setIntIdTipoEmpresa(String intIdTipoEmpresa) {
        this.intIdTipoEmpresa = intIdTipoEmpresa;
    }

    @Override
    public String toString() {
        return this.getStrRazonSocial();
    }

}
