package co.com.appsource.outsafetyapp.db_helper.Tables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by JANUS on 12/12/2015.
 * Equivale al archivo FirmasGuardadas.xml
 */
/*<?xml version="1.0" standalone="yes"?>
<NewDataSet>
<Colaboradores>
<ConcInspeccion>1</ConcInspeccion>
<idUsuario>1036615169</idUsuario>
<idInspeccion>1320</idInspeccion>
<idColaborador>1036615169</idColaborador>
<Firma />
</Colaboradores>
</NewDataSet>*/
public class Insp_Colaborador {

    public static final String DATABASE_CREATE_TBL_INSP_COLABORADOR = "CREATE TABLE " + Insp_Colaborador.NAME + "(" +
            Insp_Colaborador.COLUMN_ID + " integer primary key autoincrement," +
            Insp_Colaborador.COLUMN_ID_PERSONA + " text null," +
            Insp_Colaborador.COLUMN_NOMBRE + " text null," +
            Insp_Colaborador.COLUMN_APELLIDO + " text null," +
            Insp_Colaborador.COLUMN_CEDULA + " text null," +
            Insp_Colaborador.COLUMN_DIRECCION + " text null," +
            Insp_Colaborador.COLUMN_TELEFONO + " text null," +
            Insp_Colaborador.COLUMN_EMAIL + " text null," +
            Insp_Colaborador.COLUMN_PASSWORD + " text null," +
            Insp_Colaborador.COLUMN_ID_TIPO_PERSONA + " text null," +
            Insp_Colaborador.COLUMN_BIT_ACTIVO + " text null," +
            Insp_Colaborador.COLUMN_DT_FEC_USER + " text null," +
            Insp_Colaborador.COLUMN_DT_ULTIMA_MODIFICACION + " text null," +
            Insp_Colaborador.COLUMN_ID_EPS + " text null," +
            Insp_Colaborador.COLUMN_RUTA_FOTO + " text null," +
            Insp_Colaborador.COLUMN_RAZON_SOCIAL + " text null," +
            Insp_Colaborador.COLUMN_ID_EMPRESA + " text null," +
            Insp_Colaborador.COLUMN_FIRMA + " text null," +
            Insp_Colaborador.COLUMN_ID_INSPECCION_FK + " integer null," +
            Insp_Colaborador.COLUMN_ID_INSPECCION_PARAM + " integer null," +
            Insp_Colaborador.COLUMN_USUARIO_CREA + " text null);";

    public static final String NAME = "tblInsp_Colaborador";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ID_PERSONA = "intIdPersona";
    public static final String COLUMN_NOMBRE = "strNombreProfesional";
    public static final String COLUMN_APELLIDO = "strApellidoProfesional";
    public static final String COLUMN_CEDULA = "strCedula";
    public static final String COLUMN_DIRECCION = "strDireccion";
    public static final String COLUMN_TELEFONO = "strTelefono";
    public static final String COLUMN_EMAIL = "strEmail";
    public static final String COLUMN_PASSWORD = "strPassword";
    public static final String COLUMN_ID_TIPO_PERSONA = "intTipoPersona";
    public static final String COLUMN_BIT_ACTIVO = "bitActivo";
    public static final String COLUMN_DT_FEC_USER = "dtfecuser";
    public static final String COLUMN_DT_ULTIMA_MODIFICACION = "dtUltimaModificacion";
    public static final String COLUMN_ID_EPS = "intIdEps";
    public static final String COLUMN_RUTA_FOTO = "strRutaFoto";
    public static final String COLUMN_RAZON_SOCIAL = "strRazonSocial";
    public static final String COLUMN_ID_EMPRESA = "intIdEmpresa";
    public static final String COLUMN_FIRMA = "strFirmaBase64";
    public static final String COLUMN_ID_INSPECCION_FK = "intIdInspeccionFk";
    public static final String COLUMN_ID_INSPECCION_PARAM = "intIdInspeccionParamFk";
    public static final String COLUMN_USUARIO_CREA = "strUsuario";

    private long id;
    private String intIdPersona;
    private String strNombreProfesional;
    private String strApellidoProfesional;
    private String strCedula;
    private String strDireccion;
    private String strTelefono;
    private String strEmail;
    private String strPassword;
    private String intTipoPersona;
    private String bitActivo;
    private String dtfecuser;
    private String dtUltimaModificacion;
    private String intIdEps;
    private String strRutaFoto;
    private boolean esInspeccionPositiva;
    private String strFirmaBase64;
    private long intIdInspeccionFk;
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

    public long getIntIdInspeccionFk() {
        return intIdInspeccionFk;
    }

    public void setIntIdInspeccionFk(long intIdInspeccionFk) {
        this.intIdInspeccionFk = intIdInspeccionFk;
    }

    public String getStrFirmaBase64() {
        return strFirmaBase64;
    }

    public void setStrFirmaBase64(String strFirmaBase64) {
        this.strFirmaBase64 = strFirmaBase64;
    }


    public boolean isEsInspeccionPositiva() {
        return esInspeccionPositiva;
    }

    public void setEsInspeccionPositiva(boolean esInspeccionPositiva) {
        this.esInspeccionPositiva = esInspeccionPositiva;
    }

    public String getStrRazonSocial() {
        return strRazonSocial;
    }

    public void setStrRazonSocial(String strRazonSocial) {
        this.strRazonSocial = strRazonSocial;
    }

    public String getIntIdEmpresa() {
        return intIdEmpresa;
    }

    public void setIntIdEmpresa(String intIdEmpresa) {
        this.intIdEmpresa = intIdEmpresa;
    }

    private String strRazonSocial;
    private String intIdEmpresa;

    public String getBitActivo() {
        return bitActivo;
    }

    public void setBitActivo(String bitActivo) {
        this.bitActivo = bitActivo;
    }

    public String getDtfecuser() {
        return dtfecuser;
    }

    public void setDtfecuser(String dtfecuser) {
        this.dtfecuser = dtfecuser;
    }

    public String getDtUltimaModificacion() {
        return dtUltimaModificacion;
    }

    public void setDtUltimaModificacion(String dtUltimaModificacion) {
        this.dtUltimaModificacion = dtUltimaModificacion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIntIdEps() {
        return intIdEps;
    }

    public void setIntIdEps(String intIdEps) {
        this.intIdEps = intIdEps;
    }

    public String getIntIdPersona() {
        return intIdPersona;
    }

    public void setIntIdPersona(String intIdPersona) {
        this.intIdPersona = intIdPersona;
    }

    public String getIntTipoPersona() {
        return intTipoPersona;
    }

    public void setIntTipoPersona(String intTipoPersona) {
        this.intTipoPersona = intTipoPersona;
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

    public String getStrDireccion() {
        return strDireccion;
    }

    public void setStrDireccion(String strDireccion) {
        this.strDireccion = strDireccion;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public void setStrEmail(String strEmail) {
        this.strEmail = strEmail;
    }

    public String getStrNombreProfesional() {
        return strNombreProfesional;
    }

    public void setStrNombreProfesional(String strNombreProfesional) {
        this.strNombreProfesional = strNombreProfesional;
    }

    public String getStrPassword() {
        return strPassword;
    }

    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
    }

    public String getStrRutaFoto() {
        return strRutaFoto;
    }

    public void setStrRutaFoto(String strRutaFoto) {
        this.strRutaFoto = strRutaFoto;
    }

    public String getStrTelefono() {
        return strTelefono;
    }

    public void setStrTelefono(String strTelefono) {
        this.strTelefono = strTelefono;
    }
}
