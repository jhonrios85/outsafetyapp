package co.com.appsource.outsafetyapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import co.com.appsource.outsafetyapp.Async.AsyncExecuteGetCentroTrabajo;
import co.com.appsource.outsafetyapp.Async.AsyncExecuteGetPersonaControlAcceso;
import co.com.appsource.outsafetyapp.Async.AsyncExecuteGuardarControlAcceso;
import co.com.appsource.outsafetyapp.complete.BuscarColaboradorComplete;
import co.com.appsource.outsafetyapp.complete.BuscarPersonaIngresoComplete;
import co.com.appsource.outsafetyapp.complete.CentroTrabajoComplete;
import co.com.appsource.outsafetyapp.complete.GuardarControlAccesoComplete;
import co.com.appsource.outsafetyapp.db_helper.Tables.CentroTrabajo;
import co.com.appsource.outsafetyapp.db_helper.Tables.ControlIngreso;
import co.com.appsource.outsafetyapp.db_helper.Tables.ControlIngresoDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Persona;
import co.com.appsource.outsafetyapp.db_helper.Tables.PersonaDataSource;
import co.com.appsource.outsafetyapp.util.GuardarControlAccesoResult;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ControlIngresoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ControlIngresoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ControlIngresoFragment extends android.support.v4.app.Fragment implements BuscarPersonaIngresoComplete, CentroTrabajoComplete, GuardarControlAccesoComplete {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    View viewControlIngresoFragment;
    ControlIngresoFragment currentControlIngresoFragment;
    Button btnScan;
    ImageButton imbtnScanCedula;
    Button btnRegistrar;
    Button btnIngresar;
    Button btnSalir;
    TextView txv_nombre_encontrado_val;
    TextView txv_num_documento_hab_encontrado_val;
    TextView txv_area_contratista_val;
    EditText etxt_num_doc_reg;
    RadioButton rbtnEntrada;
    RadioButton rbtnSalida;
    private RadioGroup radioGroupAccion;
    Spinner spinnerCentroTrabajoAcc = null;
    String strOperacion = "";


    public ControlIngresoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ControlIngresoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ControlIngresoFragment newInstance(String param1, String param2) {
        ControlIngresoFragment fragment = new ControlIngresoFragment();
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

        viewControlIngresoFragment = inflater.inflate(R.layout.fragment_control_ingreso, container, false);
        currentControlIngresoFragment = this;
        btnScan = (Button) viewControlIngresoFragment.findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).StartScan();
            }
        });

        imbtnScanCedula = (ImageButton) viewControlIngresoFragment.findViewById(R.id.imbtnScanCedula);
        imbtnScanCedula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnIngresar.setEnabled(false);
                btnSalir.setEnabled(false);

                txv_num_documento_hab_encontrado_val.setText("");
                txv_nombre_encontrado_val.setText("");
                txv_area_contratista_val.setText("");
                etxt_num_doc_reg.setText("");

                ((MainActivity) getActivity()).StartScan();
            }
        });

        btnRegistrar = (Button) viewControlIngresoFragment.findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnIngresar.setEnabled(false);
                btnSalir.setEnabled(false);

                txv_num_documento_hab_encontrado_val.setText("");
                txv_nombre_encontrado_val.setText("");
                txv_area_contratista_val.setText("");

                if (etxt_num_doc_reg.getText().toString() != "") {
                    if (OutSafetyUtils.GetCurrentModoUso(getContext()).equals(OutSafetyUtils.MODO_USO_OFFLINE)) {
                        BuscarPersonaOffLine();
                    } else {
                        new AsyncExecuteGetPersonaControlAcceso(currentControlIngresoFragment, getActivity()).execute(etxt_num_doc_reg.getText().toString(), ((CentroTrabajo) spinnerCentroTrabajoAcc.getSelectedItem()).getIntIdEmpresa(), OutSafetyUtils.GetCurrentModoUso(getContext()));
                    }

                }
            }
        });

        btnIngresar = (Button) viewControlIngresoFragment.findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OutSafetyUtils.GetCurrentModoUso(getContext()).equals(OutSafetyUtils.MODO_USO_OFFLINE)) {
                    RegistrarEntradaOffLine();
                } else {
                    strOperacion = "Entrada";
                    RegistrarOnline("False"
                            , "True"
                            , txv_num_documento_hab_encontrado_val.getText().toString()
                            , GetCurrentDateTime()
                            , ((CentroTrabajo) spinnerCentroTrabajoAcc.getSelectedItem()).getIntIdEmpresa()
                            , OutSafetyUtils.GetCurrentUser(getContext())
                            , 0);

                }

            }
        });

        btnSalir = (Button) viewControlIngresoFragment.findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OutSafetyUtils.GetCurrentModoUso(getContext()).equals(OutSafetyUtils.MODO_USO_OFFLINE)) {
                    RegistrarSalidaOffLine(txv_num_documento_hab_encontrado_val.getText().toString());
                } else {
                    strOperacion = "Salida";
                    RegistrarOnline("False"
                            , "False"
                            , txv_num_documento_hab_encontrado_val.getText().toString()
                            , GetCurrentDateTime()
                            , ((CentroTrabajo) spinnerCentroTrabajoAcc.getSelectedItem()).getIntIdEmpresa()
                            , OutSafetyUtils.GetCurrentUser(getContext())
                            , 0);
                }

            }
        });

        etxt_num_doc_reg = (EditText) viewControlIngresoFragment.findViewById(R.id.etxt_num_doc_reg);

        txv_num_documento_hab_encontrado_val = (TextView) viewControlIngresoFragment.findViewById(R.id.txv_num_documento_hab_encontrado_val);
        txv_nombre_encontrado_val = (TextView) viewControlIngresoFragment.findViewById(R.id.txv_nombre_encontrado_val);
        txv_area_contratista_val = (TextView) viewControlIngresoFragment.findViewById(R.id.txv_area_contratista_val);

        radioGroupAccion = (RadioGroup) viewControlIngresoFragment.findViewById(R.id.radioGroupAccion);
        radioGroupAccion.check(R.id.rbtnEntrada);

        new AsyncExecuteGetCentroTrabajo(this, getActivity()).execute(OutSafetyUtils.GetCurrentUser(getContext()), OutSafetyUtils.GetCurrentModoUso(getContext()));

        return viewControlIngresoFragment;
    }

    public void ExecuteRegistroPersona() {
        if (etxt_num_doc_reg.getText().toString() != "") {
            new AsyncExecuteGetPersonaControlAcceso(currentControlIngresoFragment, getActivity()).execute(etxt_num_doc_reg.getText().toString(), ((CentroTrabajo) spinnerCentroTrabajoAcc.getSelectedItem()).getIntIdEmpresa(), OutSafetyUtils.GetCurrentModoUso(getContext()));
        }
    }

    public void UpdateScannedDocumento(String strNumDocumento) {
        btnIngresar.setEnabled(false);
        btnSalir.setEnabled(false);

        txv_num_documento_hab_encontrado_val.setText("");
        txv_nombre_encontrado_val.setText("");
        txv_area_contratista_val.setText("");

        if (etxt_num_doc_reg.getText().toString() != "") {
            if (OutSafetyUtils.GetCurrentModoUso(getContext()).equals(OutSafetyUtils.MODO_USO_OFFLINE)) {
                BuscarPersonaOffLine();
            } else {
                new AsyncExecuteGetPersonaControlAcceso(currentControlIngresoFragment, getActivity()).execute(etxt_num_doc_reg.getText().toString(), ((CentroTrabajo) spinnerCentroTrabajoAcc.getSelectedItem()).getIntIdEmpresa(), OutSafetyUtils.GetCurrentModoUso(getContext()));
            }

        }
    }

    public void RegistrarOnline(String strTipoAcceso
            , String strTipo
            , String strIdUsuario
            , String strFecha
            , String intIdCentroTrabajo
            , String strIdVigilante
            , long intIdSalida) {

        List<ControlIngreso> lstRegistrar = new ArrayList<ControlIngreso>();

        ControlIngreso objControlIngreso = new ControlIngreso();
        objControlIngreso.setStrTipoAcceso(strTipoAcceso);
        objControlIngreso.setStrTipo(strTipo);
        objControlIngreso.setStrIdUsuario(strIdUsuario);
        objControlIngreso.setStrFecha(strFecha);
        objControlIngreso.setIntIdCentroTrabajo(intIdCentroTrabajo);
        objControlIngreso.setStrIdVigilante(strIdVigilante);
        objControlIngreso.setIntIdSalida(intIdSalida);

        lstRegistrar.add(objControlIngreso);


        ControlIngresoDataSource objControlIngresoDataSource = new ControlIngresoDataSource(getContext());
        String strDsControlAcceso = objControlIngresoDataSource.GenerarDataSet(lstRegistrar);

        new AsyncExecuteGuardarControlAcceso(currentControlIngresoFragment, getActivity()).execute(strDsControlAcceso);
    }

    public void RegistrarEntradaOffLine() {
        ControlIngresoDataSource objControlIngresoDataSource = new ControlIngresoDataSource(getContext());
        objControlIngresoDataSource.open();
        objControlIngresoDataSource.CreateControlIngreso("False"
                , "True"
                , txv_num_documento_hab_encontrado_val.getText().toString()
                , GetCurrentDateTime()
                , ((CentroTrabajo) spinnerCentroTrabajoAcc.getSelectedItem()).getIntIdEmpresa()
                , OutSafetyUtils.GetCurrentUser(getContext())
                , 0);
        objControlIngresoDataSource.close();

        OutSafetyUtils.MostrarAlerta("Entrada registrada con Exito!!", getContext());

        btnIngresar.setEnabled(false);
        btnSalir.setEnabled(false);

        txv_num_documento_hab_encontrado_val.setText("");
        txv_nombre_encontrado_val.setText("");
        txv_area_contratista_val.setText("");
    }

    public void RegistrarSalidaOffLine(String strCedula) {
        ControlIngresoDataSource objControlIngresoDataSource = new ControlIngresoDataSource(getContext());
        objControlIngresoDataSource.open();
        List<ControlIngreso> lstControlIngresoEntradas = objControlIngresoDataSource.GetControleIngresoByCedulaAndType(strCedula, "True");

        ControlIngreso entradaAbierta = null;

        for (ControlIngreso itemControlIngreso :
                lstControlIngresoEntradas) {
            if (!(itemControlIngreso.getIntIdSalida() > 0)) {
                entradaAbierta = itemControlIngreso;
                break;
            }
        }

        if (entradaAbierta != null) {
            objControlIngresoDataSource.CreateControlIngreso("False"
                    , "False"
                    , strCedula
                    , GetCurrentDateTime()
                    , ((CentroTrabajo) spinnerCentroTrabajoAcc.getSelectedItem()).getIntIdEmpresa()
                    , OutSafetyUtils.GetCurrentUser(getContext())
                    , entradaAbierta.getId());

            OutSafetyUtils.MostrarAlerta("Salida registrada con Exito!!", getContext());

            btnIngresar.setEnabled(false);
            btnSalir.setEnabled(false);

            txv_num_documento_hab_encontrado_val.setText("");
            txv_nombre_encontrado_val.setText("");
            txv_area_contratista_val.setText("");
        }

        objControlIngresoDataSource.close();


    }

    public static String GetCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        String anio = Integer.toString(c.get(Calendar.YEAR));

        String mes = Integer.toString(c.get(Calendar.MONTH) + 1);
        if ((c.get(Calendar.MONTH) + 1) < 10) {
            mes = "0" + mes;
        }

        String dia = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        if (c.get(Calendar.DAY_OF_MONTH) < 10) {
            dia = "0" + dia;
        }

        String hora = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
        if (c.get(Calendar.HOUR_OF_DAY) < 10) {
            hora = "0" + hora;
        }

        String minuto = Integer.toString(c.get(Calendar.MINUTE));
        if (c.get(Calendar.MINUTE) < 10) {
            minuto = "0" + minuto;
        }

        String segundo = Integer.toString(c.get(Calendar.SECOND));
        if (c.get(Calendar.SECOND) < 10) {
            segundo = "0" + segundo;
        }

        String currentDate = anio + "/" + mes + "/" + dia + " " + hora + ":" + minuto + ":" + segundo;

        return currentDate;
    }

    public void ValidarIngresos(String strCedula) {
        btnIngresar.setEnabled(false);
        btnSalir.setEnabled(false);

        ControlIngresoDataSource objControlIngresoDataSource = new ControlIngresoDataSource(getContext());
        objControlIngresoDataSource.open();
        List<ControlIngreso> lstControlIngresoEntradas = objControlIngresoDataSource.GetControleIngresoByCedulaAndType(strCedula, "True");
        objControlIngresoDataSource.close();

        boolean salidasPendientes = false;

        for (ControlIngreso itemControlIngreso :
                lstControlIngresoEntradas) {
            if (!(itemControlIngreso.getIntIdSalida() > 0)) {
                salidasPendientes = true;
                break;
            }
        }

        if (salidasPendientes) {
            btnIngresar.setEnabled(false);
            btnSalir.setEnabled(true);
        } else {
            btnIngresar.setEnabled(true);
            btnSalir.setEnabled(false);
        }
    }

    public void BuscarPersonaOffLine() {
        PersonaDataSource objPersonaDataSource = new PersonaDataSource(getContext());
        objPersonaDataSource.open();

        List<Persona> lstPersona = objPersonaDataSource.GetPersonasByDocumento(etxt_num_doc_reg.getText().toString());

        txv_num_documento_hab_encontrado_val.setText("");
        txv_nombre_encontrado_val.setText("");
        txv_area_contratista_val.setText("");

        if (lstPersona != null) {
            if (lstPersona.size() > 0) {
                txv_num_documento_hab_encontrado_val.setText(lstPersona.get(0).getStrCedula());
                txv_nombre_encontrado_val.setText(lstPersona.get(0).getStrNombreProfesional() + " " + lstPersona.get(0).getStrApellidoProfesional());
                txv_area_contratista_val.setText(lstPersona.get(0).getStrRazonSocial());

                ValidarIngresos(lstPersona.get(0).getStrCedula());

            } else {
                OutSafetyUtils.MostrarAlerta("No se encontró ninguna persona!!", getContext());
            }
        } else {
            OutSafetyUtils.MostrarAlerta("No se encontró ninguna persona!!", getContext());
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
    public void onTaskComplete(List<CentroTrabajo> result) {
        ArrayAdapter<CentroTrabajo> dataAdapter = new ArrayAdapter<CentroTrabajo>(getActivity(),
                android.R.layout.simple_spinner_item
                , result);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCentroTrabajoAcc = (Spinner) viewControlIngresoFragment.findViewById(R.id.spinnerCentroTrabajoAcc);

        spinnerCentroTrabajoAcc.setAdapter(dataAdapter);
    }

    @Override
    public void onTaskComplete(Persona result) {

        if (result != null) {
            txv_num_documento_hab_encontrado_val.setText(result.getStrCedula());
            txv_nombre_encontrado_val.setText(result.getStrNombreProfesional() + " " + result.getStrApellidoProfesional());
            txv_area_contratista_val.setText(result.getStrRazonSocial());

            btnIngresar.setEnabled(true);
            btnSalir.setEnabled(true);

        } else {
            OutSafetyUtils.MostrarAlerta("No existe esta persona en el centro de trabajo seleccionado!!", getContext());
        }
    }

    @Override
    public void onTaskComplete(GuardarControlAccesoResult result) {
        if (result != null) {
            if (result.isExito()) {
                OutSafetyUtils.MostrarAlerta(strOperacion + " registrada con Exito!!", getContext());
                btnIngresar.setEnabled(false);
                btnSalir.setEnabled(false);

                txv_num_documento_hab_encontrado_val.setText("");
                txv_nombre_encontrado_val.setText("");
                txv_area_contratista_val.setText("");
            } else {
                OutSafetyUtils.MostrarAlerta("No fue posible almacenar el registro, consulte con el Administrador!!", getContext());
            }
        } else {
            OutSafetyUtils.MostrarAlerta("No fue posible almacenar el registro, consulte con el Administrador!!", getContext());
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

