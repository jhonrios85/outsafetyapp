package co.com.appsource.outsafetyapp.db_helper.Tables;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JHONRIOS on 27/10/2015.
 */
public class Persona {

    public static final String DATABASE_CREATE_TBL_PERSONA = "CREATE TABLE " + Persona.NAME + "(" +
            Persona.COLUMN_ID + " integer primary key autoincrement," +
            Persona.COLUMN_ID_PERSONA + " text null," +
            Persona.COLUMN_NOMBRE + " text null," +
            Persona.COLUMN_APELLIDO + " text null," +
            Persona.COLUMN_CEDULA + " text null," +
            Persona.COLUMN_DIRECCION + " text null," +
            Persona.COLUMN_TELEFONO + " text null," +
            Persona.COLUMN_EMAIL + " text null," +
            Persona.COLUMN_PASSWORD + " text null," +
            Persona.COLUMN_ID_TIPO_PERSONA + " text null," +
            Persona.COLUMN_BIT_ACTIVO + " text null," +
            Persona.COLUMN_DT_FEC_USER + " text null," +
            Persona.COLUMN_DT_ULTIMA_MODIFICACION + " text null," +
            Persona.COLUMN_ID_EPS + " text null," +
            Persona.COLUMN_RUTA_FOTO + " text null," +
            Persona.COLUMN_RAZON_SOCIAL + " text null," +
            Persona.COLUMN_ID_EMPRESA + " text null);";


    public static final String NAME = "tblPersona";
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

    private long id;

    @SerializedName("intIdPersona")
    private String intIdPersona;

    @SerializedName("strNombreProfesional")
    private String strNombreProfesional;

    @SerializedName("strApellidoProfesional")
    private String strApellidoProfesional;

    @SerializedName("strCedula")
    private String strCedula;

    private String strDireccion;

    private String strTelefono;

    private String strEmail;

    private String strPassword;

    @SerializedName("intTipoPersona")
    private String intTipoPersona;

    private String bitActivo;

    private String dtfecuser;

    private String dtUltimaModificacion;

    private String intIdEps;

    private String strRutaFoto;

    private boolean esInspeccionPositiva;

    private String strFirmaBase64;

    @SerializedName("strRazonSocial")
    private String strRazonSocial;

    @SerializedName("intIdEmpresa")
    private String intIdEmpresa;

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

/*    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getStrNombreProfesional());
        dest.writeString(getStrApellidoProfesional());
        dest.writeString(getStrCedula());
        dest.writeString(getStrDireccion());
        dest.writeString(getStrTelefono());
        dest.writeString(getStrEmail());
        dest.writeString(getStrPassword());
        dest.writeString(getIntTipoPersona());
        dest.writeString(getBitActivo());
        dest.writeString(getDtfecuser());
        dest.writeString(getDtUltimaModificacion());
        dest.writeString(getIntIdEps());
        dest.writeString(getStrRutaFoto());
        dest.writeBooleanArray(new boolean[]{isEsInspeccionPositiva()});
        dest.writeString(getStrFirmaBase64());
        dest.writeString(getStrRazonSocial());
        dest.writeString(getIntIdEmpresa());
    }

    public Persona(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        setIntIdPersona(in.readString());
        setStrNombreProfesional(in.readString());
        setStrApellidoProfesional(in.readString());
        setStrCedula(in.readString());
        setStrDireccion(in.readString());
        setStrTelefono(in.readString());
        setStrEmail(in.readString());
        setStrPassword(in.readString());
        setIntTipoPersona(in.readString());
        setBitActivo(in.readString());
        setDtfecuser(in.readString());
        setDtUltimaModificacion(in.readString());
        setIntIdEps(in.readString());
        setStrRutaFoto(in.readString());

        boolean[] temp = new boolean[1];
        in.readBooleanArray(temp);
        setEsInspeccionPositiva(temp[0]);

        setStrFirmaBase64(in.readString());
        setStrRazonSocial(in.readString());
        setIntIdEmpresa(in.readString());
    }

    public static final Parcelable.Creator<Persona> CREATOR
            = new Parcelable.Creator<Persona>() {
        public Persona createFromParcel(Parcel in) {
            return new Persona(in);
        }

        public Persona[] newArray(int size) {
            return new Persona[size];
        }
    };*/

}


