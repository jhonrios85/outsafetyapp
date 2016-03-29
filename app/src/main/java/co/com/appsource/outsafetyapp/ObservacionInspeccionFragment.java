package co.com.appsource.outsafetyapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import co.com.appsource.outsafetyapp.Async.AsyncExecuteGuardarColaboradoresInspeccion;
import co.com.appsource.outsafetyapp.Async.AsyncExecuteGuardarEncabezadoInspeccion;
import co.com.appsource.outsafetyapp.Async.AsyncExecuteGuardarEvidenciasInspeccion;
import co.com.appsource.outsafetyapp.Async.AsyncExecuteGuardarInspeccion;
import co.com.appsource.outsafetyapp.complete.GuardarColaboradoresComplete;
import co.com.appsource.outsafetyapp.complete.GuardarEvidenciaComplete;
import co.com.appsource.outsafetyapp.complete.GuardarInspeccionComplete;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_Colaborador;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_ColaboradorDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_Evidencia;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_EvidenciaDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_Inspeccion;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_InspeccionDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_Parametro;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_ParametroDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Parametro;
import co.com.appsource.outsafetyapp.db_helper.Tables.Persona;
import co.com.appsource.outsafetyapp.util.GuardarColaboradoresResult;
import co.com.appsource.outsafetyapp.util.GuardarEvidenciasResult;
import co.com.appsource.outsafetyapp.util.GuardarInspeccionResult;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ObservacionInspeccionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ObservacionInspeccionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObservacionInspeccionFragment extends android.support.v4.app.Fragment implements GuardarInspeccionComplete, GuardarEvidenciaComplete, GuardarColaboradoresComplete {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public String intIdEmpresa;
    public boolean boolEsInspeccionPositiva;
    View viewObservacionInspeccionFragment;
    Button btn_finalizar_insp = null;
    EditText etxt_observacion_insp = null;
    CheckBox checkBoxSinObs = null;
    SharedPreferences settings = null;

    private OnFragmentInteractionListener mListener;

    Insp_Inspeccion newInspeccion = null;

    String strUsuario = "";
    Long longIdCentroTrabajo = null;
    Long longIdArea = null;
    Long longIdRiesgo = null;
    Long longIdInspeccion = null;
    String strObservacionGeneral = "";
    public HashSet<String> lstEvidencias = null;
    List<Persona> lstColaboradores = null;

    public int contadorEvidencias = 0;
    int contadorColaboradores = 0;


    List<Insp_Inspeccion> lstInsp_Inspeccion = null;
    List<Insp_Colaborador> lstInsp_Colaborador = null;
    List<Insp_Evidencia> lstInsp_Evidencia = null;
    List<Insp_Parametro> lstInsp_Parametro = null;
    public ProgressDialog mProgress;

    public String serialInsp_Parametro;
    public String serialInsp_Inspeccion;
    public String serialInsp_Colaborador;
    public String serialInsp_Evidencia;

    public ObservacionInspeccionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ObservacionInspeccionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ObservacionInspeccionFragment newInstance(String param1, String param2) {
        ObservacionInspeccionFragment fragment = new ObservacionInspeccionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        etxt_observacion_insp.setText(OutSafetyUtils.GetCurrentInspeccionObservacion(getActivity()));
        checkBoxSinObs.setChecked(OutSafetyUtils.GetCurrentStateSinObservacion(getActivity()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        SharedPreferences settings = getActivity().getSharedPreferences(OutSafetyUtils.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(OutSafetyUtils.CONS_OBSERVACION_INSP, etxt_observacion_insp.getText().toString());
        editor.putBoolean(OutSafetyUtils.CONS_ES_SIN_OBSERVACION_INSP, checkBoxSinObs.isChecked());
        editor.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);
        setRetainInstance(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.action_evidencias);
        menu.removeItem(R.id.action_modo_off_on);

        inflater.inflate(R.menu.main, menu);

        menu.findItem(R.id.action_modo_off_on).setTitle(((MainActivity) getActivity()).ConfigurarModoDeUso(OutSafetyUtils.GetCurrentModoUso(getContext())).equals(OutSafetyUtils.MODO_USO_ONLINE) ? OutSafetyUtils.MODO_USO_OFFLINE : OutSafetyUtils.MODO_USO_ONLINE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewObservacionInspeccionFragment = inflater.inflate(R.layout.fragment_observacion_inspeccion, container, false);
        btn_finalizar_insp = (Button) viewObservacionInspeccionFragment.findViewById(R.id.btn_finalizar_insp);
        btn_finalizar_insp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkBoxSinObs.isChecked()) {
                    Toast.makeText(getContext(), "Guardando inspección por favor espere NO INTERRUMPIR!!!", Toast.LENGTH_LONG).show();
                    GuardarInspeccionJSON();
                } else {
                    if (!etxt_observacion_insp.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Guardando inspección por favor espere NO INTERRUMPIR!!!", Toast.LENGTH_LONG).show();
                        GuardarInspeccionJSON();
                    } else {
                        if (mProgress != null) {
                            mProgress.dismiss();
                        }

                        OutSafetyUtils.MostrarAlerta("Debe escribir una observación o seleccionar 'Sin Observación'", getContext());
                    }
                }

            }
        });

        etxt_observacion_insp = (EditText) viewObservacionInspeccionFragment.findViewById(R.id.etxt_observacion_insp);

        checkBoxSinObs = (CheckBox) viewObservacionInspeccionFragment.findViewById(R.id.checkBoxSinObs);
        checkBoxSinObs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etxt_observacion_insp.setText("");
                }

                etxt_observacion_insp.setEnabled(!isChecked);
            }
        });


        return viewObservacionInspeccionFragment;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onTaskComplete(GuardarInspeccionResult result) {
        if (result == null) {
            if (mProgress != null) {
                LimpiarInfoInspeccion();
                mProgress.dismiss();
                OutSafetyUtils.MostrarAlerta("Inspección Guardada localmente,se enviará cuando sincronice!!!", getContext());
            }

            IrACentrosTrabajo();
        } else {
            Boolean boolTest = result.isExito();
            if (boolTest) {
                GuardarEvidencias(result.getIntIdInspeccion());
            } else {
                OutSafetyUtils.MostrarAlerta("No fue posible almacenar la inspección, intente de nuevo o consulte con el Administrador!!!", getContext());
            }
        }
    }

    @Override
    public void onTaskComplete(GuardarEvidenciasResult result) {
        if (result.isExito()) {
            if (contadorEvidencias < lstEvidencias.size()) {
                GuardarEvidencias(result.getIntIdInspeccion());
            } else {
                GuardarColaboradores(result.getIntIdInspeccion());
            }
        }
    }

    @Override
    public void onTaskComplete(GuardarColaboradoresResult result) {
        if (result.isExito()) {
            if (contadorColaboradores < lstColaboradores.size()) {
                GuardarColaboradores(result.getIntIdInspeccion());
            } else {
                LimpiarInfoInspeccion();
                if (mProgress != null) {
                    mProgress.dismiss();
                }
                OutSafetyUtils.MostrarAlerta("Se almacenó la inspección!!!", getContext());
                IrACentrosTrabajo();
            }
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void GuardarColaboradores(long idInspeccionCampo) {

        if (contadorColaboradores == 0) {
            lstColaboradores = ((MainActivity) getActivity()).GetColaboradoresInspeccionados();
        }

        Insp_ColaboradorDataSource objInsp_ColaboradorDataSource = new Insp_ColaboradorDataSource(getContext());
        objInsp_ColaboradorDataSource.open();

        Persona itemPersona = lstColaboradores.get(contadorColaboradores);

        Insp_Colaborador newColaborador = objInsp_ColaboradorDataSource.CreateInsp_Colaborador(itemPersona.getBitActivo()
                , itemPersona.getDtfecuser()
                , itemPersona.getDtUltimaModificacion()
                , itemPersona.getIntIdEps()
                , itemPersona.getIntIdPersona()
                , itemPersona.getIntTipoPersona()
                , itemPersona.getStrApellidoProfesional()
                , itemPersona.getStrCedula()
                , itemPersona.getStrDireccion()
                , itemPersona.getStrEmail()
                , itemPersona.getStrNombreProfesional()
                , itemPersona.getStrPassword()
                , itemPersona.getStrRutaFoto()
                , itemPersona.getStrTelefono()
                , itemPersona.getIntIdEmpresa()
                , itemPersona.getStrRazonSocial()
                , itemPersona.getStrFirmaBase64()
                , newInspeccion.getId()
                , newInspeccion.getIntIdInspeccion()
                , newInspeccion.getStrUsuario());
        objInsp_ColaboradorDataSource.close();

        contadorColaboradores++;

        new AsyncExecuteGuardarColaboradoresInspeccion(this, getActivity()).execute(Long.toString(idInspeccionCampo), itemPersona.getStrFirmaBase64(), itemPersona.getStrCedula(), OutSafetyUtils.GetCurrentModoUso(getContext()));
    }

    public void GuardarColaboradoresOffline() {


        lstColaboradores = ((MainActivity) getActivity()).GetColaboradoresInspeccionados();
        //Colaboradores
        Insp_ColaboradorDataSource objInsp_ColaboradorDataSource = new Insp_ColaboradorDataSource(getContext());
        objInsp_ColaboradorDataSource.open();
        for (Persona itemPersona :
                lstColaboradores) {
            Insp_Colaborador newColaborador = objInsp_ColaboradorDataSource.CreateInsp_Colaborador(itemPersona.getBitActivo()
                    , itemPersona.getDtfecuser()
                    , itemPersona.getDtUltimaModificacion()
                    , itemPersona.getIntIdEps()
                    , itemPersona.getIntIdPersona()
                    , itemPersona.getIntTipoPersona()
                    , itemPersona.getStrApellidoProfesional()
                    , itemPersona.getStrCedula()
                    , itemPersona.getStrDireccion()
                    , itemPersona.getStrEmail()
                    , itemPersona.getStrNombreProfesional()
                    , itemPersona.getStrPassword()
                    , itemPersona.getStrRutaFoto()
                    , itemPersona.getStrTelefono()
                    , itemPersona.getIntIdEmpresa()
                    , itemPersona.getStrRazonSocial()
                    , itemPersona.getStrFirmaBase64()
                    , newInspeccion.getId()
                    , newInspeccion.getIntIdInspeccion()
                    , newInspeccion.getStrUsuario());
        }
        objInsp_ColaboradorDataSource.close();
    }

    public void GuardarEvidencias(long idInspeccionCampo) {

        String evidenciaGuardar = "";
        int contadorEvidenciasIterar = 0;

        if (contadorEvidencias == 0) {
            lstEvidencias = (HashSet<String>) settings.getStringSet(OutSafetyUtils.SHARED_PREFERENCE_EVIDENCIAS, new HashSet<String>());
        }

        //Evidencias
        Insp_EvidenciaDataSource objInsp_EvidenciaDataSource = new Insp_EvidenciaDataSource(getContext());
        objInsp_EvidenciaDataSource.open();

        for (String itemEvidencia :
                lstEvidencias) {
            if (contadorEvidencias == contadorEvidenciasIterar) {
                evidenciaGuardar = itemEvidencia;
            }
            contadorEvidenciasIterar++;
        }

        Bitmap evidenciaPhoto = null;

        String strEvidencia = "";
        if (evidenciaGuardar.toUpperCase().contains(".JPG")) {
            evidenciaPhoto = OutSafetyUtils.decodeSampledBitmapFromFile(evidenciaGuardar, 600, 500);
            strEvidencia = OutSafetyUtils.BitMapToString(evidenciaPhoto);
        } else {
            strEvidencia = evidenciaGuardar;
        }

        Insp_Evidencia newInsp_Evidencia = objInsp_EvidenciaDataSource.CreateInsp_Evidencia(strEvidencia
                , newInspeccion.getId()
                , ""
                , newInspeccion.getIntIdInspeccion()
                , newInspeccion.getStrUsuario());

        File f = new File(evidenciaGuardar);
        if (f.exists()) {
            f.delete();
        }
        objInsp_EvidenciaDataSource.close();

        contadorEvidencias++;

        new AsyncExecuteGuardarEvidenciasInspeccion(this, getActivity()).execute(Long.toString(idInspeccionCampo), strEvidencia, OutSafetyUtils.GetCurrentModoUso(getContext()));

    }

    public void GuardarEvidenciasOffline() {

        Insp_EvidenciaDataSource objInsp_EvidenciaDataSource = new Insp_EvidenciaDataSource(getContext());
        objInsp_EvidenciaDataSource.open();
        lstEvidencias = (HashSet<String>) settings.getStringSet(OutSafetyUtils.SHARED_PREFERENCE_EVIDENCIAS, new HashSet<String>());
        for (String itemEvidencia :
                lstEvidencias) {

            Bitmap evidenciaPhoto = null;

            String strEvidencia = "";
            if (itemEvidencia.toUpperCase().contains(".JPG")) {
                //evidenciaPhoto = OutSafetyUtils.decodeSampledBitmapFromFile(itemEvidencia, 600, 500);
                evidenciaPhoto = OutSafetyUtils.decodeOriginalImage(itemEvidencia);
                strEvidencia = OutSafetyUtils.BitMapToString(evidenciaPhoto);
            } else {
                strEvidencia = itemEvidencia;
            }

            Insp_Evidencia newInsp_Evidencia = objInsp_EvidenciaDataSource.CreateInsp_Evidencia(strEvidencia
                    , newInspeccion.getId()
                    , ""
                    , newInspeccion.getIntIdInspeccion()
                    , newInspeccion.getStrUsuario());

            File f = new File(itemEvidencia);
            if (f.exists()) {
                f.delete();
            }
        }
        objInsp_EvidenciaDataSource.close();
    }

    public void GuardarInspeccion() {

        settings = getActivity().getSharedPreferences(OutSafetyUtils.PREFS_NAME, 0);
        strUsuario = settings.getString(OutSafetyUtils.SHARED_PREFERENCE_USERNAME, "");
        longIdCentroTrabajo = Long.parseLong(settings.getString(OutSafetyUtils.SHARED_PREFERENCE_CENTRO_TRABAJO, ""));
        longIdArea = Long.parseLong(settings.getString(OutSafetyUtils.SHARED_PREFERENCE_ID_AREA, ""));
        longIdRiesgo = Long.parseLong(settings.getString(OutSafetyUtils.SHARED_PREFERENCE_ID_RIESGO, ""));
        longIdInspeccion = Long.parseLong(settings.getString(OutSafetyUtils.SHARED_PREFERENCE_ID_INSP, ""));

        if (checkBoxSinObs.isChecked()) {
            strObservacionGeneral = checkBoxSinObs.getText().toString();
        } else {
            strObservacionGeneral = etxt_observacion_insp.getText().toString().equals("") ? checkBoxSinObs.getText().toString() : etxt_observacion_insp.getText().toString();
        }


        lstEvidencias = (HashSet<String>) settings.getStringSet(OutSafetyUtils.SHARED_PREFERENCE_EVIDENCIAS, new HashSet<String>());

        //Inspeccion Encabezado
        Insp_InspeccionDataSource objInsp_InspeccionDataSource = new Insp_InspeccionDataSource(getContext());
        objInsp_InspeccionDataSource.open();
        Insp_Inspeccion newInspeccion = objInsp_InspeccionDataSource.CreateInsp_Inspeccion(strUsuario
                , longIdCentroTrabajo
                , longIdArea
                , longIdRiesgo
                , longIdInspeccion
                , strObservacionGeneral);
        objInsp_InspeccionDataSource.close();
        //lstInsp_Inspeccion = new ArrayList<Insp_Inspeccion>();
        //lstInsp_Inspeccion.add(newInspeccion);
        //serialInsp_Inspeccion = objInsp_InspeccionDataSource.GenerarDataSet(lstInsp_Inspeccion);

        serialInsp_Inspeccion = Insp_InspeccionDataSource.GetJson(newInspeccion);

        //Colaboradores
        List<Persona> lstColaboradores = ((MainActivity) getActivity()).GetColaboradoresInspeccionados();
        Insp_ColaboradorDataSource objInsp_ColaboradorDataSource = new Insp_ColaboradorDataSource(getContext());
        objInsp_ColaboradorDataSource.open();
        lstInsp_Colaborador = new ArrayList<Insp_Colaborador>();
        for (Persona itemPersona :
                lstColaboradores) {
            Insp_Colaborador newColaborador = objInsp_ColaboradorDataSource.CreateInsp_Colaborador(itemPersona.getBitActivo()
                    , itemPersona.getDtfecuser()
                    , itemPersona.getDtUltimaModificacion()
                    , itemPersona.getIntIdEps()
                    , itemPersona.getIntIdPersona()
                    , itemPersona.getIntTipoPersona()
                    , itemPersona.getStrApellidoProfesional()
                    , itemPersona.getStrCedula()
                    , itemPersona.getStrDireccion()
                    , itemPersona.getStrEmail()
                    , itemPersona.getStrNombreProfesional()
                    , itemPersona.getStrPassword()
                    , itemPersona.getStrRutaFoto()
                    , itemPersona.getStrTelefono()
                    , itemPersona.getIntIdEmpresa()
                    , itemPersona.getStrRazonSocial()
                    , itemPersona.getStrFirmaBase64()
                    , newInspeccion.getId()
                    , newInspeccion.getIntIdInspeccion()
                    , newInspeccion.getStrUsuario());
            lstInsp_Colaborador.add(newColaborador);

        }
        objInsp_ColaboradorDataSource.close();
        serialInsp_Colaborador = objInsp_ColaboradorDataSource.GenerarDataSet(lstInsp_Colaborador);


        //Parametros
        List<Parametro> lstParametros = ((MainActivity) getActivity()).GetParametrosInspeccionados();
        Insp_ParametroDataSource objInsp_ParametroDataSource = new Insp_ParametroDataSource(getContext());
        objInsp_ParametroDataSource.open();
        lstInsp_Parametro = new ArrayList<Insp_Parametro>();
        for (Parametro itemParametro :
                lstParametros) {
            Insp_Parametro newInsp_Parametro = objInsp_ParametroDataSource.CreateInsp_Parametro(itemParametro.getIntIdParametro()
                    , Long.toString(newInspeccion.getIntIdInspeccion())
                    , Long.toString(newInspeccion.getIntIdRiesgo())
                    , itemParametro.getStrDescripcionParametro()
                    , itemParametro.getStrRutaImagen()
                    , itemParametro.getIntIdEmpresa()
                    , itemParametro.getDtfecuser()
                    , itemParametro.isBoolCumple()
                    , newInspeccion.getId());

            lstInsp_Parametro.add(newInsp_Parametro);
        }
        objInsp_ParametroDataSource.close();
        serialInsp_Parametro = objInsp_ParametroDataSource.GenerarDataSet(lstInsp_Parametro);
    }

    public void GuardarInspeccionJSON() {

        settings = getActivity().getSharedPreferences(OutSafetyUtils.PREFS_NAME, 0);
        strUsuario = settings.getString(OutSafetyUtils.SHARED_PREFERENCE_USERNAME, "");
        longIdCentroTrabajo = Long.parseLong(settings.getString(OutSafetyUtils.SHARED_PREFERENCE_CENTRO_TRABAJO, ""));
        longIdArea = Long.parseLong(settings.getString(OutSafetyUtils.SHARED_PREFERENCE_ID_AREA, ""));
        longIdRiesgo = Long.parseLong(settings.getString(OutSafetyUtils.SHARED_PREFERENCE_ID_RIESGO, ""));
        longIdInspeccion = Long.parseLong(settings.getString(OutSafetyUtils.SHARED_PREFERENCE_ID_INSP, ""));

        if (checkBoxSinObs.isChecked()) {
            strObservacionGeneral = checkBoxSinObs.getText().toString();
        } else {
            strObservacionGeneral = etxt_observacion_insp.getText().toString().equals("") ? checkBoxSinObs.getText().toString() : etxt_observacion_insp.getText().toString();
        }


        lstEvidencias = (HashSet<String>) settings.getStringSet(OutSafetyUtils.SHARED_PREFERENCE_EVIDENCIAS, new HashSet<String>());

        //Inspeccion Encabezado
        Insp_InspeccionDataSource objInsp_InspeccionDataSource = new Insp_InspeccionDataSource(getContext());
        objInsp_InspeccionDataSource.open();
        newInspeccion = objInsp_InspeccionDataSource.CreateInsp_Inspeccion(strUsuario
                , longIdCentroTrabajo
                , longIdArea
                , longIdRiesgo
                , longIdInspeccion
                , strObservacionGeneral);
        objInsp_InspeccionDataSource.close();


        //Parametros
        List<Parametro> lstParametros = ((MainActivity) getActivity()).GetParametrosInspeccionados();
        Insp_ParametroDataSource objInsp_ParametroDataSource = new Insp_ParametroDataSource(getContext());
        objInsp_ParametroDataSource.open();
        lstInsp_Parametro = new ArrayList<Insp_Parametro>();
        for (Parametro itemParametro :
                lstParametros) {
            Insp_Parametro newInsp_Parametro = objInsp_ParametroDataSource.CreateInsp_Parametro(itemParametro.getIntIdParametro()
                    , Long.toString(newInspeccion.getIntIdInspeccion())
                    , Long.toString(newInspeccion.getIntIdRiesgo())
                    , itemParametro.getStrDescripcionParametro()
                    , itemParametro.getStrRutaImagen()
                    , itemParametro.getIntIdEmpresa()
                    , itemParametro.getDtfecuser()
                    , itemParametro.isBoolCumple()
                    , newInspeccion.getId());

            //if (OutSafetyUtils.GetCurrentModoUso(getContext()) != OutSafetyUtils.MODO_USO_ONLINE) {
                lstInsp_Parametro.add(newInsp_Parametro);
            //}

        }
        objInsp_ParametroDataSource.close();

        if (OutSafetyUtils.GetCurrentModoUso(getContext()) == OutSafetyUtils.MODO_USO_OFFLINE) {
/*            GuardarEvidenciasOffline();
            GuardarColaboradoresOffline();
            LimpiarInfoInspeccion();
            UpdateInspeccionComoExitosa();
            IrACentrosTrabajo();*/

            new AsyncExecuteGuardarEncabezadoInspeccion(this, getActivity()).execute("", OutSafetyUtils.GetCurrentModoUso(getContext()));
        } else {
            serialInsp_Inspeccion = Insp_InspeccionDataSource.GetJson(newInspeccion);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            serialInsp_Parametro = gson.toJson(lstInsp_Parametro);
            String strGuardarInspeccionRequest = serialInsp_Inspeccion + "|" + serialInsp_Parametro;
            new AsyncExecuteGuardarEncabezadoInspeccion(this, getActivity()).execute(strGuardarInspeccionRequest, OutSafetyUtils.GetCurrentModoUso(getContext()));
        }
    }

    public void GuardarInspeccionOffline() {
        GuardarEvidenciasOffline();
        GuardarColaboradoresOffline();
        //LimpiarInfoInspeccion();
        UpdateInspeccionComoExitosa();
        //IrACentrosTrabajo();
    }


    public void LimpiarInfoInspeccion() {
        ((MainActivity) getActivity()).LimpiarSharedPrefereces();
        etxt_observacion_insp.setText("");
        checkBoxSinObs.setChecked(false);
        etxt_observacion_insp.setEnabled(true);
    }

    public void UpdateInspeccionComoExitosa() {
        Insp_InspeccionDataSource objInsp_InspeccionDataSource = new Insp_InspeccionDataSource(getContext());
        objInsp_InspeccionDataSource.open();

        objInsp_InspeccionDataSource.updateToComplete(newInspeccion.getId());

        objInsp_InspeccionDataSource.close();

        //OutSafetyUtils.MostrarAlerta("Inspección Guardada localmente,se enviará cuando sincronice!!!", getContext());
    }

    public void IrACentrosTrabajo() {
        ((MainActivity) getActivity()).ShowCentrosTrabajo(settings.getString(OutSafetyUtils.SHARED_PREFERENCE_USERNAME, ""));
    }
}
