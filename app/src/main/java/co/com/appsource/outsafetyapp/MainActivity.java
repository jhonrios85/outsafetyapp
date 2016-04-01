package co.com.appsource.outsafetyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import co.com.appsource.outsafetyapp.complete.ConfigurarInspeccionComplete;
import co.com.appsource.outsafetyapp.db_helper.SQLOutsafetyHelper;
import co.com.appsource.outsafetyapp.db_helper.Tables.AreaDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.CentroTrabajoDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.InspeccionDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.LoginDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Parametro;
import co.com.appsource.outsafetyapp.db_helper.Tables.ParametroDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Persona;
import co.com.appsource.outsafetyapp.db_helper.Tables.PersonaDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.RiesgoDataSource;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks
        , Login.OnFragmentInteractionListener
        , SincronzarFragment.OnFragmentInteractionListener
        , CentroTrabajoFragment.OnFragmentInteractionListener
        , ConfigurarInspeccionFragment.OnFragmentInteractionListener
        , ParametroFragment.OnFragmentInteractionListener
        , EvidenciasFragment.OnFragmentInteractionListener
        , ColaboradoresInspeccionFragment.OnFragmentInteractionListener
        , FirmarFragment.OnFragmentInteractionListener
        , ObservacionInspeccionFragment.OnFragmentInteractionListener
        , HabilidadesFragment.OnFragmentInteractionListener
        , ControlIngresoFragment.OnFragmentInteractionListener
        , HazardId.OnFragmentInteractionListener
        , HazardIdPortadaFragment.OnFragmentInteractionListener {

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    public SQLiteDatabase dbOutSafety = null;

    public Menu objMainMenu = null;
    public Menu objGlobalMenu = null;

    public int idNuevaEvidencia = 0;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    public static final String PREFS_NAME = "OutsafetyPrefsFile";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == 0) {
            return;
        }

        if (requestCode == 49374) {
            if (data.getExtras() != null) {
                if (data.getExtras().get("SCAN_RESULT") != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    Fragment objFragmentControlAcceso = fragmentManager.findFragmentByTag(OutSafetyUtils.CONS_FRAG_CONTROL_ACCESO);
                    ControlIngresoFragment objControlAcceso = null;
                    if (objFragmentControlAcceso != null) {
                        objControlAcceso = (ControlIngresoFragment) objFragmentControlAcceso;
                        objControlAcceso.UpdateScannedDocumento((String) data.getExtras().get("SCAN_RESULT"));
                    }
                }
            }
        }

        if (requestCode == NavigationDrawerFragment.CAM_REQUEST_FULL_SIZE) {
            //Check that request code matches ours:

            //Get our saved file into a bitmap object:
            //File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            //File file = new File(Environment.getExternalStorageDirectory() + File.separator + settings.getString(OutSafetyUtils.SHARED_PREFERENCE_ID_FOTO, "") + ".jpg");
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + idNuevaEvidencia + ".jpg");
            //Bitmap evidenciaPhoto = decodeSampledBitmapFromFile(file.getAbsolutePath(), 800, 600);
            Bitmap evidenciaPhoto = OutSafetyUtils.decodeSampledBitmapFromFile(file.getAbsolutePath(), 600, 500);
            //file.delete();


            SharedPreferences.Editor editor = settings.edit();
            HashSet<String> objEvidencias = (HashSet<String>) settings.getStringSet(OutSafetyUtils.SHARED_PREFERENCE_EVIDENCIAS, new HashSet<String>());
            //objEvidencias.add(OutSafetyUtils.BitMapToString(evidenciaPhoto));
            objEvidencias.add(file.getAbsolutePath());
            editor.putStringSet(OutSafetyUtils.SHARED_PREFERENCE_EVIDENCIAS, objEvidencias);
            editor.commit();

            FragmentManager fm = getSupportFragmentManager();

            EvidenciasFragment evidenciasDialog = (EvidenciasFragment) fm.findFragmentByTag("fragment_evidencias");

            if (evidenciasDialog != null) {
                evidenciasDialog.UpdateEvidencias(evidenciaPhoto, file.getAbsolutePath());
            } else {
                evidenciasDialog = EvidenciasFragment.newInstance("fragment_evidencias");
                //evidenciasDialog.DisplayCurrentEvidencia(evidenciaPhoto);
                evidenciasDialog.bitEvidencia = evidenciaPhoto;
                evidenciasDialog.show(fm, "fragment_evidencias");
            }
        }

        if (requestCode == NavigationDrawerFragment.CAM_REQUEST) {
            if (data.getExtras() != null) {
                if (data.getExtras().get("data") != null) {
                    //Bitmap evidenciaPhoto = (Bitmap) data.getExtras().get("data");

                    Bitmap evidenciaPhoto = (Bitmap) data.getExtras().get("data");

                    evidenciaPhoto = Bitmap.createScaledBitmap(evidenciaPhoto, 600, 500, false);

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    HashSet<String> objEvidencias = (HashSet<String>) settings.getStringSet(OutSafetyUtils.SHARED_PREFERENCE_EVIDENCIAS, new HashSet<String>());
                    objEvidencias.add(OutSafetyUtils.BitMapToString(evidenciaPhoto));
                    editor.putStringSet(OutSafetyUtils.SHARED_PREFERENCE_EVIDENCIAS, objEvidencias);
                    editor.commit();

                    FragmentManager fm = getSupportFragmentManager();

                    EvidenciasFragment evidenciasDialog = (EvidenciasFragment) fm.findFragmentByTag("fragment_evidencias");

                    if (evidenciasDialog != null) {
                        evidenciasDialog.UpdateEvidencias(evidenciaPhoto, "");
                    } else {
                        evidenciasDialog = EvidenciasFragment.newInstance("fragment_evidencias");
                        //evidenciasDialog.DisplayCurrentEvidencia(evidenciaPhoto);
                        evidenciasDialog.bitEvidencia = evidenciaPhoto;
                        evidenciasDialog.show(fm, "fragment_evidencias");
                    }


                }
            }

            Toast.makeText(this, "Las fotos quedarán con baja resolución, debido a que no posee tarjeta SD!!", Toast.LENGTH_LONG).show();
        }

        if (requestCode == 327681) {
            Uri selectedImageUri = data.getData();
            UpdateCustomerLogoHazardID(selectedImageUri);
        }

        if (requestCode == 327691) {
            Uri selectedImageUri = data.getData();
            UpdateCustomerPortadaImageHazardID(selectedImageUri);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        if (OutSafetyUtils.GetCurrentFragmentBefore(this).equals(OutSafetyUtils.CONS_FRAG_CENTROS_TRABAJO)) {
            ShowCentrosTrabajo(OutSafetyUtils.GetCurrentUser(this));
            return;
        }

        if (OutSafetyUtils.GetCurrentFragmentBefore(this).equals(OutSafetyUtils.CONS_FRAG_CONFIG_INSP)) {
            ShowConfigurarInspeccion(OutSafetyUtils.GetCurrentCentroTrabajo(this));
            return;
        }

        if (OutSafetyUtils.GetCurrentFragmentBefore(this).equals(OutSafetyUtils.CONS_FRAG_COLABORADORES_INSP)) {
            ContinuarSeleccionarColaboradores(OutSafetyUtils.GetCurrentInspeccionEsPositiva(this)
                    , OutSafetyUtils.GetCurrentCentroTrabajo(this));
            return;
        }

        if (OutSafetyUtils.GetCurrentFragmentBefore(this).equals(OutSafetyUtils.CONS_FRAG_OBS_INSP)) {
            ContinuarHaciaObservacionesInspeccion(OutSafetyUtils.GetCurrentInspeccionEsPositiva(this)
                    , OutSafetyUtils.GetCurrentCentroTrabajo(this));
            return;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        if (savedInstanceState != null) {
            if (OutSafetyUtils.GetCurrentFragment(this).equals(OutSafetyUtils.CONS_FRAG_CENTROS_TRABAJO)) {
                ShowCentrosTrabajo(OutSafetyUtils.GetCurrentUser(this));
                return;
            }

            if (OutSafetyUtils.GetCurrentFragment(this).equals(OutSafetyUtils.CONS_FRAG_CONFIG_INSP)) {
                ShowConfigurarInspeccion(OutSafetyUtils.GetCurrentCentroTrabajo(this));
                return;
            }

            if (OutSafetyUtils.GetCurrentFragment(this).equals(OutSafetyUtils.CONS_FRAG_COLABORADORES_INSP)) {
                ContinuarSeleccionarColaboradores(OutSafetyUtils.GetCurrentInspeccionEsPositiva(this)
                        , OutSafetyUtils.GetCurrentCentroTrabajo(this));
                return;
            }

            if (OutSafetyUtils.GetCurrentFragment(this).equals(OutSafetyUtils.CONS_FRAG_OBS_INSP)) {
                ContinuarHaciaObservacionesInspeccion(OutSafetyUtils.GetCurrentInspeccionEsPositiva(this)
                        , OutSafetyUtils.GetCurrentCentroTrabajo(this));
                return;
            }
        }

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        HashSet<String> objEvidencias = new HashSet<String>();
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(OutSafetyUtils.SHARED_PREFERENCE_EVIDENCIAS, objEvidencias);
        editor.putString(OutSafetyUtils.SHARED_PREFERENCE_MODO_USO, OutSafetyUtils.MODO_USO_ONLINE);
        editor.commit();

        // if(!this.ValidateDatabaseExist())
        //{
        //this.CreateDataBaseOutSafety();
        //}
    }

    public void LimpiarSharedPrefereces() {
        //BEGIN Limpiar SHARED PREFRENCES
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        HashSet<String> objEvidencias = new HashSet<String>();
        editor.putStringSet(OutSafetyUtils.SHARED_PREFERENCE_EVIDENCIAS, objEvidencias);
        editor.putString(OutSafetyUtils.SHARED_PREFERENCE_CENTRO_TRABAJO, "");
        editor.putString(OutSafetyUtils.SHARED_PREFERENCE_ID_AREA, "");
        editor.putString(OutSafetyUtils.SHARED_PREFERENCE_ID_RIESGO, "");
        editor.putString(OutSafetyUtils.SHARED_PREFERENCE_ID_INSP, "");
        editor.putString(OutSafetyUtils.SHARED_PREFERENCE_ID_FOTO, "");

        editor.putString(OutSafetyUtils.CONS_COLABORADORES_SELECTED, "");
        editor.putBoolean(OutSafetyUtils.CONS_ES_INSP_POS, false);
        editor.putString(OutSafetyUtils.CONS_OBSERVACION_INSP, "");
        editor.putBoolean(OutSafetyUtils.CONS_ES_SIN_OBSERVACION_INSP, false);
        editor.commit();

        FragmentManager fm = getSupportFragmentManager();
        ColaboradoresInspeccionFragment fragment_colaboradores_inspeccion = (ColaboradoresInspeccionFragment) fm.findFragmentByTag("fragment_colaboradores_inspeccion");
        fragment_colaboradores_inspeccion.DeleteSelectedCols();

        //editor.apply();
        //END Limpiar SHARED PREFRENCES
    }


    public boolean ValidateDatabaseExist() {
        File dbFile = this.getDatabasePath(SQLOutsafetyHelper.DATABASE_NAME);
        return dbFile.exists();
    }

    public List<Persona> GetColaboradoresInspeccionados() {
        FragmentManager fm = getSupportFragmentManager();
        ColaboradoresInspeccionFragment fragment_colaboradores_inspeccion = (ColaboradoresInspeccionFragment) fm.findFragmentByTag("fragment_colaboradores_inspeccion");
        return fragment_colaboradores_inspeccion.GetColaboradoresInspeccionados();
    }

    public List<Parametro> GetParametrosInspeccionados() {
        FragmentManager fm = getSupportFragmentManager();
        ConfigurarInspeccionFragment fragment_configurar_inspeccion = (ConfigurarInspeccionFragment) fm.findFragmentByTag("fragment_configurar_inspeccion");
        return fragment_configurar_inspeccion.GetParametrosInspeccion();
    }

    public void CreateDataBaseOutSafety() {
        dbOutSafety = new SQLOutsafetyHelper(this).getWritableDatabase();
    }


    public void ShowHabilidades() {
        mTitle = getString(R.string.title_section_habilidades);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);


        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment objFragment = null;

        objFragment = fragmentManager.findFragmentByTag(OutSafetyUtils.CONS_FRAG_HABILIDADES);
        HabilidadesFragment objHabilidades = null;

        if (objFragment != null) {
            objHabilidades = (HabilidadesFragment) objFragment;
        } else {
            objHabilidades = new HabilidadesFragment();
        }

        objHabilidades.strUsuario = OutSafetyUtils.GetCurrentUser(this);

        Fragment fragment = objHabilidades;

        OutSafetyUtils.SetLastFragment(this, OutSafetyUtils.CONS_FRAG_HABILIDADES);
        //OutSafetyUtils.SetBeforeFragment(this, OutSafetyUtils.CONS_FRAG_LOGIN);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commit();
    }

    public void UpdateCustomerLogoHazardID(Uri uriImageLogo) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment objFragment = null;

        objFragment = fragmentManager.findFragmentByTag(OutSafetyUtils.CONS_FRAG_HAZARD_ID);
        HazardId objHazardId = null;

        if (objFragment != null) {
            objHazardId = (HazardId) objFragment;
            objHazardId.UpdateCustomerLogo(uriImageLogo);
        }
    }

    public void UpdateCustomerPortadaImageHazardID(Uri uriImageLogo) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment objFragment = null;

        objFragment = fragmentManager.findFragmentByTag(OutSafetyUtils.CONS_FRAG_HAZARD_ID);
        HazardId objHazardId = null;

        if (objFragment != null) {
            objHazardId = (HazardId) objFragment;
            objHazardId.UpdateCustomerPortadaImagae(uriImageLogo);
        }
    }

    public void ShowHazardID() {

        mTitle = getString(R.string.title_section_hazard_id);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);


        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment objFragment = null;

        objFragment = fragmentManager.findFragmentByTag(OutSafetyUtils.CONS_FRAG_HAZARD_ID);
        HazardId objHazardId = null;

        if (objFragment != null) {
            objHazardId = (HazardId) objFragment;
        } else {
            objHazardId = new HazardId();
        }

        Fragment fragment = objHazardId;

        OutSafetyUtils.SetLastFragment(this, OutSafetyUtils.CONS_FRAG_HAZARD_ID);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment, OutSafetyUtils.CONS_FRAG_HAZARD_ID);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commit();
    }

    public void ShowControlAcceso() {

        mTitle = getString(R.string.title_section_control_acceso);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);


        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment objFragment = null;

        objFragment = fragmentManager.findFragmentByTag(OutSafetyUtils.CONS_FRAG_CONTROL_ACCESO);
        ControlIngresoFragment objControlAcceso = null;

        if (objFragment != null) {
            objControlAcceso = (ControlIngresoFragment) objFragment;
        } else {
            objControlAcceso = new ControlIngresoFragment();
        }

        Fragment fragment = objControlAcceso;

        OutSafetyUtils.SetLastFragment(this, OutSafetyUtils.CONS_FRAG_CONTROL_ACCESO);
        //OutSafetyUtils.SetBeforeFragment(this, OutSafetyUtils.CONS_FRAG_LOGIN);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment, OutSafetyUtils.CONS_FRAG_CONTROL_ACCESO);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commit();
    }


    public void ShowCentrosTrabajo(String strUsuario) {
        mTitle = getString(R.string.title_section_centro_trabajo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);


        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment objFragment = null;

        objFragment = fragmentManager.findFragmentByTag(OutSafetyUtils.CONS_FRAG_CENTROS_TRABAJO);
        CentroTrabajoFragment objCT = null;

        if (objFragment != null) {
            objCT = (CentroTrabajoFragment) objFragment;
        } else {
            objCT = new CentroTrabajoFragment();
            objCT.strUsuario = strUsuario;
        }

        Fragment fragment = objCT;

        OutSafetyUtils.SetLastFragment(this, OutSafetyUtils.CONS_FRAG_CENTROS_TRABAJO);
        OutSafetyUtils.SetBeforeFragment(this, OutSafetyUtils.CONS_FRAG_LOGIN);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commit();
    }

    public void ContinuarHaciaObservacionesInspeccion(boolean esPositiva, String intIdEmpresa) {
        mTitle = getString(R.string.title_section_observacion);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);

        FragmentManager fragmentManager = getSupportFragmentManager();

        ObservacionInspeccionFragment objObservacionInspeccionFragment = null;
        Fragment fragment = null;
        fragment = fragmentManager.findFragmentByTag(OutSafetyUtils.CONS_FRAG_OBS_INSP);

        if (fragment == null) {
            objObservacionInspeccionFragment = new ObservacionInspeccionFragment();
        } else {
            if (OutSafetyUtils.GetCurrentFragment(this) == OutSafetyUtils.CONS_FRAG_COLABORADORES_INSP) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                objObservacionInspeccionFragment = new ObservacionInspeccionFragment();
            } else {
                objObservacionInspeccionFragment = (ObservacionInspeccionFragment) fragment;
            }
        }

        objObservacionInspeccionFragment.intIdEmpresa = intIdEmpresa;
        objObservacionInspeccionFragment.boolEsInspeccionPositiva = esPositiva;

        OutSafetyUtils.SetLastFragment(this, OutSafetyUtils.CONS_FRAG_OBS_INSP);
        OutSafetyUtils.SetBeforeFragment(this, OutSafetyUtils.CONS_FRAG_COLABORADORES_INSP);

        fragment = objObservacionInspeccionFragment;

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment, OutSafetyUtils.CONS_FRAG_OBS_INSP);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commit();
    }

    public void ContinuarSeleccionarColaboradores(boolean esPositiva, String intIdEmpresa) {
        mTitle = getString(R.string.title_section_colaboradores);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ColaboradoresInspeccionFragment objColaboradoresInspeccionFragment = null;
        Fragment fragment = null;
        fragment = fragmentManager.findFragmentByTag(OutSafetyUtils.CONS_FRAG_COLABORADORES_INSP);

        if (fragment == null) {
            objColaboradoresInspeccionFragment = new ColaboradoresInspeccionFragment();
        } else {
            if (OutSafetyUtils.GetCurrentFragment(this) == OutSafetyUtils.CONS_FRAG_CONFIG_INSP) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                objColaboradoresInspeccionFragment = new ColaboradoresInspeccionFragment();
            } else {
                objColaboradoresInspeccionFragment = (ColaboradoresInspeccionFragment) fragment;
            }
        }

        objColaboradoresInspeccionFragment.intIdEmpresa = intIdEmpresa;
        objColaboradoresInspeccionFragment.boolEsInspeccionPositiva = esPositiva;

        OutSafetyUtils.SetLastFragment(this, OutSafetyUtils.CONS_FRAG_COLABORADORES_INSP);
        OutSafetyUtils.SetBeforeFragment(this, OutSafetyUtils.CONS_FRAG_CONFIG_INSP);

        fragment = objColaboradoresInspeccionFragment;

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment, OutSafetyUtils.CONS_FRAG_COLABORADORES_INSP);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commit();
    }

    public void ShowConfigurarInspeccion(String intIdEmpresa) {
        mTitle = getString(R.string.title_section_configurar_inspeccion);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(OutSafetyUtils.SHARED_PREFERENCE_CENTRO_TRABAJO, intIdEmpresa);
        editor.commit();

        FragmentManager fragmentManager = getSupportFragmentManager();
        ConfigurarInspeccionFragment objConfigInsp = null;
        Fragment fragment = null;
        fragment = fragmentManager.findFragmentByTag(OutSafetyUtils.CONS_FRAG_CONFIG_INSP);

        if (fragment == null) {
            objConfigInsp = new ConfigurarInspeccionFragment();
        } else {
            if (OutSafetyUtils.GetCurrentFragment(this) == OutSafetyUtils.CONS_FRAG_CENTROS_TRABAJO) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                objConfigInsp = new ConfigurarInspeccionFragment();
            } else {
                objConfigInsp = (ConfigurarInspeccionFragment) fragment;
            }

        }

        objConfigInsp.intIdEmpresa = intIdEmpresa;

        fragment = objConfigInsp;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment, OutSafetyUtils.CONS_FRAG_CONFIG_INSP);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commit();
        OutSafetyUtils.SetLastFragment(this, OutSafetyUtils.CONS_FRAG_CONFIG_INSP);
        OutSafetyUtils.SetBeforeFragment(this, OutSafetyUtils.CONS_FRAG_CENTROS_TRABAJO);
    }

    public void ShowFirmar(int idPosicion, String strFirma) {
        FragmentManager fm = getSupportFragmentManager();
        FirmarFragment firmarDialog = FirmarFragment.newInstance("Firmar", "");
        firmarDialog.intPosicion = idPosicion;
        firmarDialog.strFirmaBase64Saved = strFirma;
        firmarDialog.show(fm, "fragment_firmar");
    }

    public void RemoverColaborador(String strCedula, int intPosicion) {

        FragmentManager fm = getSupportFragmentManager();

        ColaboradoresInspeccionFragment objColaboradoresInspeccionados = (ColaboradoresInspeccionFragment) fm.findFragmentByTag("fragment_colaboradores_inspeccion");

        if (objColaboradoresInspeccionados != null) {
            objColaboradoresInspeccionados.RemoverColaboradorDeInspeccion(intPosicion, strCedula);
        }
    }

    public void GuardarFirmaColaborador(int intPosicion, String strFirmaBase64) {

        FragmentManager fm = getSupportFragmentManager();
        ColaboradoresInspeccionFragment objColaboradoresInspeccionados = (ColaboradoresInspeccionFragment) fm.findFragmentByTag("fragment_colaboradores_inspeccion");
        if (objColaboradoresInspeccionados != null) {
            objColaboradoresInspeccionados.GuardarFirmaColaborador(intPosicion, strFirmaBase64);
        }
    }

    public void LimpiarFirma(int intPosicion) {

        FragmentManager fm = getSupportFragmentManager();
        ColaboradoresInspeccionFragment objColaboradoresInspeccionados = (ColaboradoresInspeccionFragment) fm.findFragmentByTag("fragment_colaboradores_inspeccion");
        if (objColaboradoresInspeccionados != null) {
            objColaboradoresInspeccionados.LimpiarFirma(intPosicion);
        }
    }

    public void AddColaboradorToInspeccion(String strCedula, int intPosicion) {

        FragmentManager fm = getSupportFragmentManager();

        ColaboradoresInspeccionFragment objColaboradoresInspeccionados = (ColaboradoresInspeccionFragment) fm.findFragmentByTag("fragment_colaboradores_inspeccion");

        if (objColaboradoresInspeccionados != null) {
            objColaboradoresInspeccionados.AddColaboradorToInspeccion(intPosicion);
        } else {
            //evidenciasDialog = EvidenciasFragment.newInstance("Evidencias");
            //evidenciasDialog.DisplayCurrentEvidencia(evidenciaPhoto);
            //evidenciasDialog.bitEvidencia = evidenciaPhoto;
            //evidenciasDialog.show(fm, "fragment_evidencias");
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    public void updateNavigationDrawer(String strTipoPersona) {
        mNavigationDrawerFragment.updateMenuItems(strTipoPersona);
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment fragment = PlaceholderFragment.newInstance(position + 1);
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (position) {
            default:
            case 0:
                mTitle = getString(R.string.title_section_login);
                fragment = new Login();

                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(null);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                transaction.commit();
                break;
            case 1:
                mTitle = getString(R.string.title_section_hacer_insp);
                ShowCentrosTrabajo(OutSafetyUtils.GetCurrentUser(this));
                break;
            case 2:
                mTitle = getString(R.string.title_section_sincronizar);

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

                SincronzarFragment objSincronzarFragment = new SincronzarFragment();
                objSincronzarFragment.strUsuario = settings.getString(OutSafetyUtils.SHARED_PREFERENCE_USERNAME, "");
                fragment = objSincronzarFragment;

                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(null);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                transaction.commit();
                break;
            case 3:
                mTitle = getString(R.string.title_section_habilidades);
                ShowHabilidades();
                break;
            case 4:
                mTitle = getString(R.string.title_section_control_acceso);
                ShowControlAcceso();
                break;
            case 5:
                mTitle = getString(R.string.title_section_hazard_id);
                ShowHazardID();
                break;
        }


    }


    public void onSectionAttached(int number) {
        /*switch (number) {
            case 1:
                mTitle = getString(R.string.title_section_login);
                break;
            case 2:
                mTitle = getString(R.string.title_section_sincronizar);
                break;
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            objMainMenu = menu;

            getMenuInflater().inflate(R.menu.global, menu);
            objGlobalMenu = menu;

            ConfigurarMenu();

            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_evidencias) {
            FragmentManager fm = getSupportFragmentManager();

            EvidenciasFragment evidenciasDialog = (EvidenciasFragment) fm.findFragmentByTag("fragment_evidencias");

            if (evidenciasDialog != null) {
                evidenciasDialog.show(fm, "fragment_evidencias");
            } else {
                evidenciasDialog = EvidenciasFragment.newInstance("fragment_evidencias");
                //evidenciasDialog.DisplayCurrentEvidencia(evidenciaPhoto);
                evidenciasDialog.show(fm, "fragment_evidencias");
            }

            return true;
        }

        if (id == R.id.action_modo_off_on) {
            String strModoNuevo = ConfigurarModoDeUso(item.getTitle().toString());
            item.setTitle(strModoNuevo.equals(OutSafetyUtils.MODO_USO_ONLINE) ? OutSafetyUtils.MODO_USO_OFFLINE : OutSafetyUtils.MODO_USO_ONLINE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void StartScan() {
        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
        integrator.initiateScan();
    }

    public void ConfigurarMenu() {
        String strModoNuevo = ConfigurarModoDeUso(OutSafetyUtils.GetCurrentModoUso(this));
        objMainMenu.findItem(R.id.action_modo_off_on).setTitle(ConfigurarModoDeUso(OutSafetyUtils.GetCurrentModoUso(this)).equals(OutSafetyUtils.MODO_USO_ONLINE) ? OutSafetyUtils.MODO_USO_OFFLINE : OutSafetyUtils.MODO_USO_ONLINE);
    }


    public String ConfigurarModoDeUso(String modoActual) {
        String strNuevoModo = modoActual;

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();


        if (modoActual.equals(OutSafetyUtils.MODO_USO_ONLINE)) {
            if (!OutSafetyUtils.ValidarConexion(this)) {
                strNuevoModo = OutSafetyUtils.MODO_USO_OFFLINE;
                objGlobalMenu.findItem(R.id.action_dummie_off).setVisible(true);
                objGlobalMenu.findItem(R.id.action_dummie_on).setVisible(false);
                editor.putString(OutSafetyUtils.SHARED_PREFERENCE_MODO_USO, OutSafetyUtils.MODO_USO_OFFLINE);
                editor.commit();
                strNuevoModo = OutSafetyUtils.MODO_USO_OFFLINE;
                OutSafetyUtils.MostrarAlerta("No posee conexion a la red para trabajar ONLINE!!", this);
                return strNuevoModo;
            }

            strNuevoModo = OutSafetyUtils.MODO_USO_ONLINE;
            objGlobalMenu.findItem(R.id.action_dummie_on).setVisible(true);
            objGlobalMenu.findItem(R.id.action_dummie_off).setVisible(false);
            editor.putString(OutSafetyUtils.SHARED_PREFERENCE_MODO_USO, OutSafetyUtils.MODO_USO_ONLINE);
        } else {
            if (!OutSafetyUtils.ValidarExisteInformacionSincronizada(this)) {
                objGlobalMenu.findItem(R.id.action_dummie_on).setVisible(true);
                objGlobalMenu.findItem(R.id.action_dummie_off).setVisible(false);
                editor.putString(OutSafetyUtils.SHARED_PREFERENCE_MODO_USO, OutSafetyUtils.MODO_USO_ONLINE);
                editor.commit();
                strNuevoModo = OutSafetyUtils.MODO_USO_ONLINE;
                OutSafetyUtils.MostrarAlerta("No tiene información local sincronizada para trabajar OFFLINE, primero sincronice los datos.!!", this);
                return strNuevoModo;
            }

            strNuevoModo = OutSafetyUtils.MODO_USO_OFFLINE;
            objGlobalMenu.findItem(R.id.action_dummie_on).setVisible(false);
            objGlobalMenu.findItem(R.id.action_dummie_off).setVisible(true);
            editor.putString(OutSafetyUtils.SHARED_PREFERENCE_MODO_USO, OutSafetyUtils.MODO_USO_OFFLINE);
        }

        editor.commit();

        return strNuevoModo;
    }

    public List<Persona> GetSelectedColaborador() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String strJsonInspeccionados = settings.getString(OutSafetyUtils.CONS_COLABORADORES_SELECTED, "");

        Gson gson = new Gson();

        Type listType = new TypeToken<ArrayList<Persona>>() {
        }.getType();
        List<Persona> lstSelected = new ArrayList<Persona>();
        lstSelected = gson.fromJson(strJsonInspeccionados, listType);

        if (lstSelected == null) {
            lstSelected = new ArrayList<Persona>();
        }

        return lstSelected;
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }


    }

}
