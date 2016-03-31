package co.com.appsource.outsafetyapp.db_helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import co.com.appsource.outsafetyapp.db_helper.Tables.Area;
import co.com.appsource.outsafetyapp.db_helper.Tables.CentroTrabajo;
import co.com.appsource.outsafetyapp.db_helper.Tables.ControlIngreso;
import co.com.appsource.outsafetyapp.db_helper.Tables.Empresa;
import co.com.appsource.outsafetyapp.db_helper.Tables.Habilidad;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_Colaborador;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_Evidencia;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_Inspeccion;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_Parametro;
import co.com.appsource.outsafetyapp.db_helper.Tables.Inspeccion;
import co.com.appsource.outsafetyapp.db_helper.Tables.Login;
import co.com.appsource.outsafetyapp.db_helper.Tables.Parametro;
import co.com.appsource.outsafetyapp.db_helper.Tables.Persona;
import co.com.appsource.outsafetyapp.db_helper.Tables.Riesgo;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;

/**
 * Created by JHONRIOS on 27/10/2015.
 */
public class SQLOutsafetyHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = OutSafetyUtils.CONS_SOAP_DATABASE_NAME;
    private static final int DATABASE_VERSION = 12;


    public SQLOutsafetyHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Login.DATABASE_CREATE_ATBL_LOGIN);
        db.execSQL(Persona.DATABASE_CREATE_TBL_PERSONA);
        db.execSQL(CentroTrabajo.DATABASE_CREATE_TBL_CENTRO_TRABAJO);
        db.execSQL(Area.DATABASE_CREATE_TBL_AREA);
        db.execSQL(Riesgo.DATABASE_CREATE_TBL_RIESGO);
        db.execSQL(Inspeccion.DATABASE_CREATE_TBL_INSPECCION);
        db.execSQL(Parametro.DATABASE_CREATE_TBL_PARAMETRO);
        db.execSQL(Habilidad.DATABASE_CREATE_TBL_HABILIDAD);
        db.execSQL(ControlIngreso.DATABASE_CREATE_TBL_CONTROL_INGRESO);
        db.execSQL(Empresa.DATABASE_CREATE_TBL_EMPRESA);

        db.execSQL(Insp_Colaborador.DATABASE_CREATE_TBL_INSP_COLABORADOR);
        db.execSQL(Insp_Evidencia.DATABASE_CREATE_TBL_INSP_EVIDENCIA);
        db.execSQL(Insp_Inspeccion.DATABASE_CREATE_TBL_INSP_INSPECCION);
        db.execSQL(Insp_Parametro.DATABASE_CREATE_TBL_INSP_PARAMETRO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLOutsafetyHelper.class.getName(),
                "Actualizando database de la versión " + oldVersion + " a la "
                        + newVersion + ", lo cual destruira los datos de la base de datos vieja.");

        db.execSQL("DROP TABLE IF EXISTS " + Login.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Persona.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CentroTrabajo.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Area.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Riesgo.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Inspeccion.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Parametro.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Habilidad.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ControlIngreso.NAME);

        db.execSQL("DROP TABLE IF EXISTS " + Insp_Colaborador.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Insp_Evidencia.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Insp_Inspeccion.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Insp_Parametro.NAME);

        onCreate(db);
    }
}
