package co.com.appsource.outsafetyapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.com.appsource.outsafetyapp.Async.AsyncExecuteGetCentroTrabajo;
import co.com.appsource.outsafetyapp.Async.AsyncExecuteGetHabilidades;
import co.com.appsource.outsafetyapp.complete.CentroTrabajoComplete;
import co.com.appsource.outsafetyapp.complete.ConfigurarInspeccionComplete;
import co.com.appsource.outsafetyapp.custom_objects.InputConfigurarInspeccion;
import co.com.appsource.outsafetyapp.db_helper.Tables.CentroTrabajo;
import co.com.appsource.outsafetyapp.db_helper.Tables.CentroTrabajoDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Habilidad;
import co.com.appsource.outsafetyapp.model.HabilidadesAdapter;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HabilidadesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HabilidadesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HabilidadesFragment extends android.support.v4.app.Fragment implements ConfigurarInspeccionComplete, CentroTrabajoComplete {

    HabilidadesAdapter listAdapter;
    ExpandableListView expListViewHabilidades;
    Button btnBuscarHabilidades;
    EditText etxt_num_doc_hab;
    List<String> listDataHeaderHabilidades = null;
    HashMap<String, List<String>> listDataChildHabilidad = null;
    View viewHabilidadesFragment;
    public String intIdEmpresa;
    public String strUsuario;
    TextView txv_nombre_encontrado_val;
    TextView txv_num_documento_hab_encontrado_val;
    List<CentroTrabajo> lstCentrosTrabajo = null;
    int ctIndex = 0;

    String strCedulaColab = "";
    String strNombreCompletoColab = "";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HabilidadesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HabilidadesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HabilidadesFragment newInstance(String param1, String param2) {
        HabilidadesFragment fragment = new HabilidadesFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewHabilidadesFragment = inflater.inflate(R.layout.fragment_habilidades, container, false);

        // get the editText
        etxt_num_doc_hab = (EditText) viewHabilidadesFragment.findViewById(R.id.etxt_num_doc_hab);

        // get the listview
        expListViewHabilidades = (ExpandableListView) viewHabilidadesFragment.findViewById(R.id.lvExpHabilidades);
        // get button
        btnBuscarHabilidades = (Button) viewHabilidadesFragment.findViewById(R.id.btnBuscarHabilidades);
        btnBuscarHabilidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarHabilidades();
            }
        });

        txv_nombre_encontrado_val = (TextView) viewHabilidadesFragment.findViewById(R.id.txv_nombre_encontrado_val);
        txv_num_documento_hab_encontrado_val = (TextView) viewHabilidadesFragment.findViewById(R.id.txv_num_documento_hab_encontrado_val);

        return viewHabilidadesFragment;
    }

    public void BuscarHabilidades() {

        ctIndex = 0;
        listDataHeaderHabilidades = null;
        listDataChildHabilidad = null;

        txv_num_documento_hab_encontrado_val.setText("");
        txv_nombre_encontrado_val.setText("");

        if (OutSafetyUtils.GetCurrentModoUso(getContext()).equals(OutSafetyUtils.MODO_USO_OFFLINE)) {

            CentroTrabajoDataSource objCentroTrabajoDataSource = new CentroTrabajoDataSource(getContext());
            objCentroTrabajoDataSource.open();
            lstCentrosTrabajo = objCentroTrabajoDataSource.GetAllCentrosTrabajo();
            objCentroTrabajoDataSource.close();

            //new AsyncExecuteGetHabilidades(this, getActivity()).execute(this.intIdEmpresa, etxt_num_doc_hab.getText().toString(), OutSafetyUtils.GetCurrentModoUso(getContext()));

            if (lstCentrosTrabajo != null) {
                if (lstCentrosTrabajo.size() > 0) {
                    new AsyncExecuteGetHabilidades(this, getActivity()).execute(lstCentrosTrabajo.get(ctIndex).getIntIdEmpresa(), etxt_num_doc_hab.getText().toString(), OutSafetyUtils.GetCurrentModoUso(getContext()));
                    ctIndex++;
                }
            }
        } else {
            new AsyncExecuteGetCentroTrabajo(this, getActivity(), "Consultando Habilidades!!").execute(this.strUsuario, OutSafetyUtils.GetCurrentModoUso(getContext()));
        }

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
    public void onTaskComplete(InputConfigurarInspeccion result) {

        if (listDataHeaderHabilidades == null) {
            listDataHeaderHabilidades = new ArrayList<String>();
        }

        if (listDataChildHabilidad == null) {
            listDataChildHabilidad = new HashMap<String, List<String>>();
        }

        if (lstCentrosTrabajo == null) {
            lstCentrosTrabajo = new ArrayList<CentroTrabajo>();
        }

        if (result != null) {
            if (result.getLstHabilidad() != null) {
                if (result.getLstHabilidad().size() > 0) {
                    if (OutSafetyUtils.GetCurrentModoUso(getContext()).equals(OutSafetyUtils.MODO_USO_OFFLINE)) {

                        for (Habilidad itemHab :
                                result.getLstHabilidad()) {
                            listDataHeaderHabilidades.add(itemHab.toString());
                        }

                        for (Habilidad itemHabilidad : result.getLstHabilidad()
                                ) {
                            List<String> lstPropiedadesHabilidad = new ArrayList<>();
                            lstPropiedadesHabilidad.add(itemHabilidad.getStrRazonSocial());
                            listDataChildHabilidad.put(itemHabilidad.toString(), lstPropiedadesHabilidad);
                        }

                        listAdapter = new HabilidadesAdapter(this.getActivity(), listDataHeaderHabilidades, listDataChildHabilidad);
                        expListViewHabilidades.setAdapter(listAdapter);
                    } else {
                        if (listDataHeaderHabilidades != null) {
                            for (Habilidad itemHab :
                                    result.getLstHabilidad()) {
                                listDataHeaderHabilidades.add(itemHab.toString());
                            }
                        } else {
                            listDataHeaderHabilidades = new ArrayList<String>();
                            for (Habilidad itemHab :
                                    result.getLstHabilidad()) {
                                listDataHeaderHabilidades.add(itemHab.toString());
                            }
                        }

                        if (listDataChildHabilidad == null) {
                            listDataChildHabilidad = new HashMap<String, List<String>>();
                        }


                        for (Habilidad itemHabilidad : result.getLstHabilidad()
                                ) {
                            List<String> lstPropiedadesHabilidad = new ArrayList<>();
                            lstPropiedadesHabilidad.add(itemHabilidad.getStrRazonSocial());
                            listDataChildHabilidad.put(itemHabilidad.toString(), lstPropiedadesHabilidad);
                        }

                    }


                    txv_num_documento_hab_encontrado_val.setText(result.getLstHabilidad().get(0).getStrCedula());
                    txv_nombre_encontrado_val.setText(result.getLstHabilidad().get(0).getStrNombreProfesional() + " " + result.getLstHabilidad().get(0).getStrApellidoProfesional());
                } else {
                    //OutSafetyUtils.MostrarAlerta("No posee habilidades activas Registradas!!", getContext());

                    // listAdapter = new HabilidadesAdapter(this.getActivity(), listDataHeaderHabilidades, listDataChildHabilidad);
                    // expListViewHabilidades.setAdapter(listAdapter);
                }
            } else {
                //OutSafetyUtils.MostrarAlerta("No posee habilidades activas Registradas!!", getContext());

                //listAdapter = new HabilidadesAdapter(this.getActivity(), listDataHeaderHabilidades, listDataChildHabilidad);
                //expListViewHabilidades.setAdapter(listAdapter);
            }
        } else {
            //OutSafetyUtils.MostrarAlerta("No posee habilidades activas Registradas!!", getContext());

            //listAdapter = new HabilidadesAdapter(this.getActivity(), listDataHeaderHabilidades, listDataChildHabilidad);
            //expListViewHabilidades.setAdapter(listAdapter);
        }

        if (ctIndex < lstCentrosTrabajo.size()) {
            new AsyncExecuteGetHabilidades(this, getActivity()).execute(lstCentrosTrabajo.get(ctIndex).getIntIdEmpresa(), etxt_num_doc_hab.getText().toString(), OutSafetyUtils.GetCurrentModoUso(getContext()));
            ctIndex++;
        } else {
            if (OutSafetyUtils.GetCurrentModoUso(getContext()).equals(OutSafetyUtils.MODO_USO_ONLINE)) {
                listAdapter = new HabilidadesAdapter(this.getActivity(), listDataHeaderHabilidades, listDataChildHabilidad);
                expListViewHabilidades.setAdapter(listAdapter);

                if (!(listDataHeaderHabilidades != null)) {

                    txv_num_documento_hab_encontrado_val.setText("");
                    txv_nombre_encontrado_val.setText("");

                    ctIndex = 0;
                    listDataHeaderHabilidades = null;
                    listDataChildHabilidad = null;

                    OutSafetyUtils.MostrarAlerta("No posee habilidades activas Registradas!!", getContext());

                    return;
                } else {
                    if (!(listDataHeaderHabilidades.size() > 0)) {

                        txv_num_documento_hab_encontrado_val.setText("");
                        txv_nombre_encontrado_val.setText("");

                        ctIndex = 0;
                        listDataHeaderHabilidades = null;
                        listDataChildHabilidad = null;

                        OutSafetyUtils.MostrarAlerta("No posee habilidades activas Registradas!!", getContext());

                        return;
                    }
                }
            } else {
                if (listDataHeaderHabilidades == null) {
                    OutSafetyUtils.MostrarAlerta("No posee habilidades activas Registradas!!", getContext());
                } else {
                    if (listDataHeaderHabilidades.size() <= 0) {
                        OutSafetyUtils.MostrarAlerta("No posee habilidades activas Registradas!!", getContext());
                    }
                }

            }

            ctIndex = 0;
            listDataHeaderHabilidades = null;
            listDataChildHabilidad = null;
        }

    }

    @Override
    public void onTaskComplete(List<CentroTrabajo> result) {

        lstCentrosTrabajo = result;

        if (lstCentrosTrabajo != null) {
            if (lstCentrosTrabajo.size() > 0) {
                new AsyncExecuteGetHabilidades(this, getActivity()).execute(lstCentrosTrabajo.get(ctIndex).getIntIdEmpresa(), etxt_num_doc_hab.getText().toString(), OutSafetyUtils.GetCurrentModoUso(getContext()));
                ctIndex++;
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
}
