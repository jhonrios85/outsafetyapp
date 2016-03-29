package co.com.appsource.outsafetyapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import co.com.appsource.outsafetyapp.Async.AsyncExecuteGetConfigurarInspeccion;
import co.com.appsource.outsafetyapp.complete.ConfigurarInspeccionComplete;
import co.com.appsource.outsafetyapp.custom_objects.InputConfigurarInspeccion;
import co.com.appsource.outsafetyapp.db_helper.Tables.Area;
import co.com.appsource.outsafetyapp.db_helper.Tables.Inspeccion;
import co.com.appsource.outsafetyapp.db_helper.Tables.Parametro;
import co.com.appsource.outsafetyapp.db_helper.Tables.Riesgo;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfigurarInspeccionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConfigurarInspeccionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigurarInspeccionFragment extends android.support.v4.app.Fragment implements ConfigurarInspeccionComplete {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InputConfigurarInspeccion globalInputConfigurarInspeccion;
    public ParametroFragment objParametroFragment;

    public View objConfigurarInspeccionFragment = null;
    public String intIdEmpresa;
    public String strUsuario;

    Spinner spinnerArea = null;
    Spinner spinnerRiesgo = null;
    Spinner spinnerInspeccion = null;
    List<Parametro> lstParametrosInspeccionados = null;

    private OnFragmentInteractionListener mListener;

    public ConfigurarInspeccionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigurarInspeccionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfigurarInspeccionFragment newInstance(String param1, String param2) {
        ConfigurarInspeccionFragment fragment = new ConfigurarInspeccionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        SharedPreferences settings = getActivity().getSharedPreferences(((MainActivity) getActivity()).PREFS_NAME, 0);
        this.strUsuario = settings.getString(OutSafetyUtils.SHARED_PREFERENCE_USERNAME, "");

        new AsyncExecuteGetConfigurarInspeccion(this, getActivity()).execute(this.intIdEmpresa, this.strUsuario, OutSafetyUtils.GetCurrentModoUso(getContext()));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (objConfigurarInspeccionFragment == null) {
            objConfigurarInspeccionFragment = inflater.inflate(R.layout.fragment_configurar_inspeccion, container, false);
        }


        Button btnContinuarColab = (Button) objConfigurarInspeccionFragment.findViewById(R.id.btn_continuar_sel_colab);
        btnContinuarColab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickContinuar(v);
            }
        });

        spinnerArea = (Spinner) objConfigurarInspeccionFragment.findViewById(R.id.spinnerArea);
        spinnerRiesgo = (Spinner) objConfigurarInspeccionFragment.findViewById(R.id.spinnerRiesgo);
        spinnerInspeccion = (Spinner) objConfigurarInspeccionFragment.findViewById(R.id.spinnerInspeccion);

        if (savedInstanceState != null) {
            onTaskComplete(globalInputConfigurarInspeccion);
            SharedPreferences settings = getActivity().getSharedPreferences(((MainActivity) getActivity()).PREFS_NAME, 0);
            ArrayAdapter<Area> areaAdapter = (ArrayAdapter<Area>) spinnerArea.getAdapter();
            for (int i = 0; i < areaAdapter.getCount(); i++) {
                Area area = (Area) areaAdapter.getItem(i);
                if (area.getIntIdArea().equals(settings.getString(OutSafetyUtils.SHARED_PREFERENCE_ID_AREA, ""))) {
                    spinnerArea.setSelection(i, true);
                    break;
                }
            }
        }


        return objConfigurarInspeccionFragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        SharedPreferences settings = getActivity().getSharedPreferences(OutSafetyUtils.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        Area selectArea = (Area) spinnerArea.getSelectedItem();
        editor.putString(OutSafetyUtils.SHARED_PREFERENCE_ID_AREA, selectArea.getIntIdArea());

        Riesgo selectRiesgo = (Riesgo) spinnerRiesgo.getSelectedItem();
        editor.putString(OutSafetyUtils.SHARED_PREFERENCE_ID_RIESGO, selectRiesgo.getIntIdRiesgo());

        Inspeccion selectInspeccion = (Inspeccion) spinnerInspeccion.getSelectedItem();
        editor.putString(OutSafetyUtils.SHARED_PREFERENCE_ID_INSP, selectInspeccion.getIntIdInspeccion());

        editor.commit();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.action_evidencias);
        menu.removeItem(R.id.action_modo_off_on);
        MenuItem itemTomarFoto = menu.findItem(R.id.action_tomar_foto);
        if (itemTomarFoto != null) {
            itemTomarFoto.setVisible(true);
        }

        inflater.inflate(R.menu.main, menu);

        menu.findItem(R.id.action_modo_off_on).setTitle(((MainActivity) getActivity()).ConfigurarModoDeUso(OutSafetyUtils.GetCurrentModoUso(getContext())).equals(OutSafetyUtils.MODO_USO_ONLINE) ? OutSafetyUtils.MODO_USO_OFFLINE : OutSafetyUtils.MODO_USO_ONLINE);

    }

    @Override
    public void onTaskComplete(InputConfigurarInspeccion result) {
        globalInputConfigurarInspeccion = result;
        List<Area> lstAreas = result.getLstArea();
        List<Riesgo> lstRiesgos = result.getLstRiesgo();

        ArrayAdapter<Area> dataAdapter = new ArrayAdapter<Area>(getActivity(),
                android.R.layout.simple_spinner_item
                , lstAreas);

// Drop down layout style - list view
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spArea = null;
        spArea = (Spinner) objConfigurarInspeccionFragment.findViewById(R.id.spinnerArea);

// attaching data adapter to spinner
        spArea.setAdapter(dataAdapter);

        ArrayAdapter<Riesgo> dataAdapterRiesgo = new ArrayAdapter<Riesgo>(getActivity(),
                android.R.layout.simple_spinner_item
                , lstRiesgos);

// Drop down layout style - list view
        dataAdapterRiesgo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spRiesgo = null;
        spRiesgo = (Spinner) objConfigurarInspeccionFragment.findViewById(R.id.spinnerRiesgo);

// attaching data adapter to spinner
        spRiesgo.setAdapter(dataAdapterRiesgo);


        spRiesgo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                               @Override
                                               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                   Riesgo selectedRiesgo = (Riesgo) parent.getItemAtPosition(position);

                                                   ArrayAdapter<Inspeccion> dataAdapterInspeccion = new ArrayAdapter<Inspeccion>(getActivity(),
                                                           android.R.layout.simple_spinner_item
                                                           , FiltrarInspeccionPorRiesgo(selectedRiesgo.getIntIdRiesgo()));

// Drop down layout style - list view
                                                   dataAdapterInspeccion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                   Spinner spInspeccion = null;
                                                   spInspeccion = (Spinner) objConfigurarInspeccionFragment.findViewById(R.id.spinnerInspeccion);

// attaching data adapter to spinner
                                                   spInspeccion.setAdapter(dataAdapterInspeccion);

                                                   spInspeccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                                              @Override
                                                                                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                                                  Inspeccion selectedInspeccion = (Inspeccion) parent.getItemAtPosition(position);

                                                                                                  FragmentManager fm = getChildFragmentManager();

                                                                                                  objParametroFragment = (ParametroFragment)
                                                                                                          fm.findFragmentById(R.id.parametro_fragment);


                                                                                                  objParametroFragment.FiltrarParametros(selectedInspeccion.getIntIdInspeccion());

                                                                                              }

                                                                                              @Override
                                                                                              public void onNothingSelected(AdapterView<?> parent) {


                                                                                              }
                                                                                          }
                                                   );
                                               }

                                               @Override
                                               public void onNothingSelected(AdapterView<?> parent) {

                                               }
                                           }
        );


        SharedPreferences settings = getActivity().getSharedPreferences(((MainActivity) getActivity()).PREFS_NAME, 0);
        if (!settings.getString(OutSafetyUtils.SHARED_PREFERENCE_ID_AREA, "").equals("")) {
            ArrayAdapter<Area> areaAdapter = (ArrayAdapter<Area>) spinnerArea.getAdapter();
            for (int i = 0; i < areaAdapter.getCount(); i++) {
                Area area = (Area) areaAdapter.getItem(i);
                if (area.getIntIdArea().equals(settings.getString(OutSafetyUtils.SHARED_PREFERENCE_ID_AREA, ""))) {
                    spinnerArea.setSelection(i, true);
                    break;
                }
            }
        }

        if (!settings.getString(OutSafetyUtils.SHARED_PREFERENCE_ID_RIESGO, "").equals("")) {
            ArrayAdapter<Riesgo> riesgoAdapter = (ArrayAdapter<Riesgo>) spinnerRiesgo.getAdapter();
            for (int j = 0; j < riesgoAdapter.getCount(); j++) {
                Riesgo riesgo = (Riesgo) riesgoAdapter.getItem(j);
                if (riesgo.getIntIdRiesgo().equals(settings.getString(OutSafetyUtils.SHARED_PREFERENCE_ID_RIESGO, ""))) {
                    spinnerRiesgo.setSelection(j, true);

                    ArrayAdapter<Inspeccion> dataAdapterInspeccion = new ArrayAdapter<Inspeccion>(getActivity(),
                            android.R.layout.simple_spinner_item
                            , FiltrarInspeccionPorRiesgo(riesgo.getIntIdRiesgo()));

// Drop down layout style - list view
                    dataAdapterInspeccion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner spInspeccion = null;
                    spInspeccion = (Spinner) objConfigurarInspeccionFragment.findViewById(R.id.spinnerInspeccion);

// attaching data adapter to spinner
                    spInspeccion.setAdapter(dataAdapterInspeccion);

                    ArrayAdapter<Inspeccion> inspeccionAdapter = (ArrayAdapter<Inspeccion>) spinnerInspeccion.getAdapter();

                    for (int k = 0; k < inspeccionAdapter.getCount(); k++) {
                        Inspeccion inspeccion = (Inspeccion) inspeccionAdapter.getItem(k);
                        if (inspeccion.getIntIdInspeccion().equals(settings.getString(OutSafetyUtils.SHARED_PREFERENCE_ID_INSP, ""))) {
                            spinnerInspeccion.setSelection(k, true);
                            FragmentManager fm = getChildFragmentManager();
                            objParametroFragment = (ParametroFragment)
                                    fm.findFragmentById(R.id.parametro_fragment);
                            objParametroFragment.FiltrarParametros(inspeccion.getIntIdInspeccion());
                            break;
                        }
                    }

                    break;
                }
            }
        }


    }

    public boolean EsPositiva() {
        boolean esPositiva = true;
        View vwItemParametro;
        CheckBox chParametro;
        ListView lvParametrosInspeccion = (ListView) objParametroFragment.GetListViewParametros();
        lstParametrosInspeccionados = new ArrayList<Parametro>();
        for (int i = 0; i < lvParametrosInspeccion.getCount(); i++) {
            Parametro currentParametro = (Parametro) lvParametrosInspeccion.getAdapter().getItem(i);
            lstParametrosInspeccionados.add(currentParametro);
            if (!currentParametro.isBoolCumple()) {
                esPositiva = false;
            }

        }

        return esPositiva;
    }

    public List<Parametro> GetParametrosInspeccion() {
        return lstParametrosInspeccionados;
    }

    public boolean ExistenEvidencias() {
        boolean hayEvidencias = false;

        SharedPreferences settings = getActivity().getSharedPreferences(((MainActivity) getActivity()).PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        HashSet<String> objEvidencias = (HashSet<String>) settings.getStringSet("evidencias", new HashSet<String>());

        if (objEvidencias != null) {
            if (objEvidencias.size() > 0) {
                hayEvidencias = true;
            }
        }

        return hayEvidencias;
    }

    public void MostrarAlerta(String strMensaje) {
        new AlertDialog.Builder(getContext())
                .setTitle("Validación")
                .setMessage(strMensaje)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
//                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        //                  public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        //                }
                        //          })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void onClickContinuar(View v) {
        if (!ExistenEvidencias()) {
            this.MostrarAlerta("Debe tomar fotos de evidencia!!.");

            return;
        }

        SharedPreferences settings = getActivity().getSharedPreferences(OutSafetyUtils.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        Area selectArea = (Area) spinnerArea.getSelectedItem();
        editor.putString(OutSafetyUtils.SHARED_PREFERENCE_ID_AREA, selectArea.getIntIdArea());

        Riesgo selectRiesgo = (Riesgo) spinnerRiesgo.getSelectedItem();
        editor.putString(OutSafetyUtils.SHARED_PREFERENCE_ID_RIESGO, selectRiesgo.getIntIdRiesgo());

        Inspeccion selectInspeccion = (Inspeccion) spinnerInspeccion.getSelectedItem();
        editor.putString(OutSafetyUtils.SHARED_PREFERENCE_ID_INSP, selectInspeccion.getIntIdInspeccion());

        editor.commit();


        ((MainActivity) getActivity()).ContinuarSeleccionarColaboradores(EsPositiva(), this.intIdEmpresa);
    }

    public List<Inspeccion> FiltrarInspeccionPorRiesgo(String intIdRiesgo) {
        List<Inspeccion> lstInspeccionPorRiesgo = new ArrayList<Inspeccion>();
        lstInspeccionPorRiesgo = globalInputConfigurarInspeccion.getLstInspeccion();

        List<Inspeccion> lstInspeccionPorRiesgoFiltrada = new ArrayList<Inspeccion>();
        for (Inspeccion itemInspeccion : lstInspeccionPorRiesgo) {
            if (itemInspeccion.getIntIdRiesgo().equals(intIdRiesgo)) {
                lstInspeccionPorRiesgoFiltrada.add(itemInspeccion);
            }
        }

        if (lstInspeccionPorRiesgoFiltrada.size() == 0) {
            FragmentManager fm = getChildFragmentManager();
            objParametroFragment = (ParametroFragment)
                    fm.findFragmentById(R.id.parametro_fragment);
            objParametroFragment.ClearParametros();

        }

        return lstInspeccionPorRiesgoFiltrada;
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
}


