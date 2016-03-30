package co.com.appsource.outsafetyapp.db_helper.Tables;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JHONRIOS on 04/11/2015.
 */
public class Login {
    public static final String DATABASE_CREATE_ATBL_LOGIN = "CREATE TABLE " + Login.NAME + "(" +
            Login.COLUMN_ID + " integer primary key autoincrement," +
            Login.COLUMN_PASSWORD + " text null," +
            Login.COLUMN_CEDULA + " text null," +
            Login.COLUMN_ID_TIPO_PERSONA + " text null);";

    public static final String NAME = "aTblLogin";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PASSWORD = "strPassword";
    public static final String COLUMN_CEDULA = "strCedula";
    public static final String COLUMN_ID_TIPO_PERSONA = "intTipoPersona";

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIntTipoPersona() {
        return intTipoPersona;
    }

    public void setIntTipoPersona(String intTipoPersona) {
        this.intTipoPersona = intTipoPersona;
    }

    public String getStrCedula() {
        return strCedula;
    }

    public void setStrCedula(String strCedula) {
        this.strCedula = strCedula;
    }

    public String getStrPassword() {
        return strPassword;
    }

    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
    }

    @SerializedName("strPassword")
    private String strPassword;

    @SerializedName("strCedula")
    private String strCedula;

    @SerializedName("intTipoPersona")
    private String intTipoPersona;

}
