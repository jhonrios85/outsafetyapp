package co.com.appsource.outsafetyapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import co.com.appsource.outsafetyapp.Async.AsyncExecuteEnviarCorreoInspeccion;
import co.com.appsource.outsafetyapp.Async.AsyncExecuteGetCentroTrabajo;
import co.com.appsource.outsafetyapp.Async.AsyncExecuteGetConfigurarInspeccion;
import co.com.appsource.outsafetyapp.Async.AsyncExecuteGuardarColaboradoresInspeccion;
import co.com.appsource.outsafetyapp.Async.AsyncExecuteGuardarControlAcceso;
import co.com.appsource.outsafetyapp.Async.AsyncExecuteGuardarEncabezadoInspeccion;
import co.com.appsource.outsafetyapp.Async.AsyncExecuteGuardarEvidenciasInspeccion;
import co.com.appsource.outsafetyapp.Async.AsyncExecuteGuardarInspeccion;
import co.com.appsource.outsafetyapp.complete.CentroTrabajoComplete;
import co.com.appsource.outsafetyapp.complete.ConfigurarInspeccionComplete;
import co.com.appsource.outsafetyapp.complete.EnviarMailComplete;
import co.com.appsource.outsafetyapp.complete.GuardarColaboradoresComplete;
import co.com.appsource.outsafetyapp.complete.GuardarControlAccesoComplete;
import co.com.appsource.outsafetyapp.complete.GuardarEvidenciaComplete;
import co.com.appsource.outsafetyapp.complete.GuardarInspeccionComplete;
import co.com.appsource.outsafetyapp.custom_objects.InputConfigurarInspeccion;
import co.com.appsource.outsafetyapp.db_helper.SQLOutsafetyHelper;
import co.com.appsource.outsafetyapp.db_helper.Tables.Area;
import co.com.appsource.outsafetyapp.db_helper.Tables.AreaDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.CentroTrabajo;
import co.com.appsource.outsafetyapp.db_helper.Tables.CentroTrabajoDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.ControlIngreso;
import co.com.appsource.outsafetyapp.db_helper.Tables.ControlIngresoDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Habilidad;
import co.com.appsource.outsafetyapp.db_helper.Tables.HabilidadDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_Colaborador;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_ColaboradorDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_Evidencia;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_EvidenciaDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_Inspeccion;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_InspeccionDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_Parametro;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_ParametroDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Inspeccion;
import co.com.appsource.outsafetyapp.db_helper.Tables.InspeccionDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Parametro;
import co.com.appsource.outsafetyapp.db_helper.Tables.ParametroDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Persona;
import co.com.appsource.outsafetyapp.db_helper.Tables.PersonaDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Riesgo;
import co.com.appsource.outsafetyapp.db_helper.Tables.RiesgoDataSource;
import co.com.appsource.outsafetyapp.model.CentroTrabajoAdapter;
import co.com.appsource.outsafetyapp.model.CentroTrabajoSyncAdapter;
import co.com.appsource.outsafetyapp.util.GuardarColaboradoresResult;
import co.com.appsource.outsafetyapp.util.GuardarControlAccesoResult;
import co.com.appsource.outsafetyapp.util.GuardarEvidenciasResult;
import co.com.appsource.outsafetyapp.util.GuardarInspeccionResult;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SincronzarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SincronzarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SincronzarFragment extends android.support.v4.app.Fragment implements ConfigurarInspeccionComplete, CentroTrabajoComplete, GuardarInspeccionComplete, GuardarControlAccesoComplete, GuardarEvidenciaComplete, GuardarColaboradoresComplete, EnviarMailComplete {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public String intIdEmpresa;
    public String strUsuario;
    SharedPreferences settings = null;
    Insp_Inspeccion newInspeccion = null;
    List<Insp_Evidencia> lstInsp_EvidenciaGuardar = null;
    List<Insp_Colaborador> lstInsp_ColaboradorGuardar = null;

    public long cantidadInspecciones = 0;
    public int contadorInspeccioes = 0;
    public int contadorEvidencias = 0;
    public int contadorColaboradores = 0;

    public InputConfigurarInspeccion globalInputConfigurarInspeccion;
    public List<CentroTrabajo> globalListCentroTrabajo;
    public List<CentroTrabajo> globalListCentroTrabajoSelected;
    public List<ControlIngreso> globalLstControlIngresoGuardar;
    public List<Insp_Inspeccion> lstInsp_InspeccionGuardar = new ArrayList<Insp_Inspeccion>();
    public int ctIndex = 0;
    public int controlIngIndex = 0;
    public boolean boolEsInspeccionPositiva = true;
    Long longIdInspeccionGuardada = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ImageButton img_dw_database = null;
    private ImageButton img_up_database = null;
    private ImageButton img_delete = null;
    ListView objListView;
    public SQLiteDatabase dbOutSafety = null;

    public String strMensajeDeCarga = "";

    public View objSincronzarFragment = null;

    public SincronzarFragment objSincronzarFragmentInstance = null;

    Dialog dwDialog;
    public ProgressDialog mProgress;
    public ProgressDialog mProgressUploadInspecciones;

    boolean descargando = true;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SincronzarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SincronzarFragment newInstance(String param1, String param2) {
        SincronzarFragment fragment = new SincronzarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SincronzarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);
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
        objSincronzarFragment = inflater.inflate(R.layout.fragment_sincronzar, container, false);

        objSincronzarFragmentInstance = this;

        img_dw_database = (ImageButton) objSincronzarFragment.findViewById(R.id.img_dw_database);
        img_dw_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dwDialog = new Dialog(getActivity());
                dwDialog.setTitle("Sincronizar");
                dwDialog.setContentView(R.layout.sync_ct_dialog);
                Button btnCustomOk = (Button) dwDialog.findViewById(R.id.btnCustomOk);

                btnCustomOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ValidateSelectedCt()) {
                            mProgress = new ProgressDialog(getContext());
                            mProgress.setMessage("Sincronizando,por favor espera, no interrumpir el proceso!!!");
                            mProgress.show();
                            descargando = true;
                            ctIndex = 0;
                            controlIngIndex = 0;
                            GuardarInspeccionesUnaPorUna();
                            GuardarControlAccesoUnoPorUno();
                            ClearCurrentTables();

                            CentroTrabajoDataSource objCentroTrabajoDataSource;
                            objCentroTrabajoDataSource = new CentroTrabajoDataSource(getContext());
                            objCentroTrabajoDataSource.open();
                            for (CentroTrabajo i :
                                    globalListCentroTrabajoSelected) {
                                objCentroTrabajoDataSource.CreateCentroTrabajo(i.getIntIdEmpresa()
                                        , i.getIntIdPersona()
                                        , i.getStrRazonSocial()
                                );

                                //Toast.makeText(getActivity(), "....Sincronizando " + i.getStrRazonSocial().toUpperCase(), Toast.LENGTH_LONG).show();
                            }
                            objCentroTrabajoDataSource.close();

                            if (globalListCentroTrabajoSelected.size() > 0) {
                                new AsyncExecuteGetConfigurarInspeccion(objSincronzarFragmentInstance, getActivity(), "Consultando Areas,Riesgos,Inspecciones,Personas y Habilidades de " + globalListCentroTrabajoSelected.get(0).getStrRazonSocial().toUpperCase() + "!!...Puede tardar varios minutos!!").execute(globalListCentroTrabajoSelected.get(0).getIntIdEmpresa(), objSincronzarFragmentInstance.strUsuario, OutSafetyUtils.GetCurrentModoUso(getContext()), OutSafetyUtils.CONS_SINCRONIZANDO);
                            }


                            dwDialog.cancel();

                        } else {
                            Toast.makeText(getActivity(), "Debe seleccionar algun Centro de Trabajo!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                Button btnCustomCancel = (Button) dwDialog.findViewById(R.id.btnCustomCancel);
                btnCustomCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dwDialog.cancel();
                    }
                });
                dwDialog.show();

                if (OutSafetyUtils.GetCurrentModoUso(getContext()).equals(OutSafetyUtils.MODO_USO_ONLINE)) {
                    DownloadDataBaseSelectCt();
                } else {
                    OutSafetyUtils.MostrarAlerta("No puede ejecutar esta acción en modo OFFLINE!!", getContext());
                    dwDialog.cancel();
                }


            }
        });

        img_up_database = (ImageButton) objSincronzarFragment.findViewById(R.id.img_up_database);
        img_up_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getContext())
                        .setTitle("Validación")
                        .setMessage("Este proceso envia al servidor las inspecciones realizadas en este equipo,¿Desea Continuar?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                descargando = false;
                                UploadInspecciones();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        img_delete = (ImageButton) objSincronzarFragment.findViewById(R.id.img_delete);
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Insp_InspeccionDataSource objInsp_InspeccionDataSource = new Insp_InspeccionDataSource(getContext());
                objInsp_InspeccionDataSource.open();
                int cantidadInspeccionesBorrar = objInsp_InspeccionDataSource.GetCount();
                String strMessageBorrar = "Este proceso ELIMINA las inspecciones realizadas en este equipo, en total son " + cantidadInspeccionesBorrar + " inspecciones,¿Desea Continuar?";
                objInsp_InspeccionDataSource.close();

                if (cantidadInspeccionesBorrar > 0) {

                    new AlertDialog.Builder(getContext())
                            .setTitle("Validación")
                            .setMessage(strMessageBorrar)
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    BorrarInspeccionesLocales();
                                    Toast.makeText(getActivity(), "Inspecciones Eliminadas!!", Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                } else {
                    Toast.makeText(getActivity(), "No hay inspecciones para eliminar del dispositivo!!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return objSincronzarFragment;
    }

    public void DownloadDataBase() {
        if (OutSafetyUtils.ValidarConexion(getContext())) {
            if (OutSafetyUtils.GetCurrentModoUso(getContext()).equals(OutSafetyUtils.MODO_USO_ONLINE)) {
                //GuardarInspecciones();
                GuardarInspeccionesUnaPorUna();
                ClearCurrentTables();
                new AsyncExecuteGetCentroTrabajo(this, getActivity()).execute(this.strUsuario, OutSafetyUtils.GetCurrentModoUso(getContext()));
            } else {
                OutSafetyUtils.MostrarAlerta("No puede ejecutar esta acción en modo OFFLINE!!", getContext());
            }

        } else {
            OutSafetyUtils.MostrarAlerta("No cuenta con una red de datos para ejecutar esta acción!!", getContext());
        }
    }

    public void DownloadDataBaseSelectCt() {
        if (OutSafetyUtils.ValidarConexion(getContext())) {
            if (OutSafetyUtils.GetCurrentModoUso(getContext()).equals(OutSafetyUtils.MODO_USO_ONLINE)) {
                new AsyncExecuteGetCentroTrabajo(this, getActivity()).execute(this.strUsuario, OutSafetyUtils.GetCurrentModoUso(getContext()));
            } else {
                OutSafetyUtils.MostrarAlerta("No puede ejecutar esta acción en modo OFFLINE!!", getContext());
            }

        } else {
            OutSafetyUtils.MostrarAlerta("No cuenta con una red de datos para ejecutar esta acción!!", getContext());
        }
    }

    public void UploadInspecciones() {
        if (OutSafetyUtils.ValidarConexion(getContext())) {
            if (OutSafetyUtils.GetCurrentModoUso(getContext()).equals(OutSafetyUtils.MODO_USO_ONLINE)) {

                mProgress = new ProgressDialog(getContext());
                mProgress.setMessage("Sincronizando,por favor espera, no interrumpir el proceso!!!");
                mProgress.show();

                GuardarControlAccesoUnoPorUno();
                GuardarInspeccionesUnaPorUna();

                if (mProgress != null) {
                    mProgress.dismiss();
                }
            } else {
                OutSafetyUtils.MostrarAlerta("No puede ejecutar esta acción en modo OFFLINE!!", getContext());
            }
        } else {
            OutSafetyUtils.MostrarAlerta("No cuenta con una red de datos para ejecutar esta acción!!", getContext());
        }
    }

    public void GuardarInspecciones() {
        settings = getActivity().getSharedPreferences(OutSafetyUtils.PREFS_NAME, 0);
        strUsuario = settings.getString(OutSafetyUtils.SHARED_PREFERENCE_USERNAME, "");
        List<Insp_Inspeccion> lstInsp_Inspeccion = new ArrayList<Insp_Inspeccion>();
        List<Insp_Colaborador> lstInsp_Colaborador = null;
        List<Insp_Evidencia> lstInsp_Evidencia = null;
        List<Insp_Parametro> lstInsp_Parametro = null;

        //Inspecciones
        Insp_InspeccionDataSource objInsp_InspeccionDataSource = new Insp_InspeccionDataSource(getContext());
        objInsp_InspeccionDataSource.open();
        lstInsp_Inspeccion = objInsp_InspeccionDataSource.GetAll();
        objInsp_InspeccionDataSource.close();
        String serialInsp_Inspeccion = objInsp_InspeccionDataSource.GenerarDataSet(lstInsp_Inspeccion);

        cantidadInspecciones = lstInsp_Inspeccion.size();
        if (cantidadInspecciones > 0) {

//Colaboradores
            Insp_ColaboradorDataSource objInsp_ColaboradorDataSource = new Insp_ColaboradorDataSource(getContext());
            objInsp_ColaboradorDataSource.open();
            lstInsp_Colaborador = objInsp_ColaboradorDataSource.GetAll();
            objInsp_ColaboradorDataSource.close();
            String serialInsp_Colaborador = objInsp_ColaboradorDataSource.GenerarDataSet(lstInsp_Colaborador);

//Evidencias
            Insp_EvidenciaDataSource objInsp_EvidenciaDataSource = new Insp_EvidenciaDataSource(getContext());
            objInsp_EvidenciaDataSource.open();
            lstInsp_Evidencia = objInsp_EvidenciaDataSource.GetAll();
            objInsp_EvidenciaDataSource.close();
            String serialInsp_Evidencia = objInsp_EvidenciaDataSource.GenerarDataSet(lstInsp_Evidencia);

            //Parametros
            Insp_ParametroDataSource objInsp_ParametroDataSource = new Insp_ParametroDataSource(getContext());
            objInsp_ParametroDataSource.open();
            lstInsp_Parametro = objInsp_ParametroDataSource.GetAll();
            objInsp_ParametroDataSource.close();
            String serialInsp_Parametro = objInsp_ParametroDataSource.GenerarDataSet(lstInsp_Parametro);

            new AsyncExecuteGuardarInspeccion(this, getActivity()).execute(serialInsp_Inspeccion, serialInsp_Parametro, serialInsp_Colaborador, serialInsp_Evidencia
                    , OutSafetyUtils.GetCurrentModoUso(getContext()), "Subiendo Inspecciones al servidor!!!, puede tardar varios minutos!!");
        } else {
            if (mProgress != null) {
                mProgress.dismiss();
            }
            OutSafetyUtils.MostrarAlerta("No hay inspecciones para guardar!!", getContext());
        }
    }

    public void GuardarControlAccesoUnoPorUno() {
        settings = getActivity().getSharedPreferences(OutSafetyUtils.PREFS_NAME, 0);
        strUsuario = settings.getString(OutSafetyUtils.SHARED_PREFERENCE_USERNAME, "");
        globalLstControlIngresoGuardar = new ArrayList<ControlIngreso>();

        ControlIngresoDataSource objControlIngresoDataSource = new ControlIngresoDataSource(getContext());
        objControlIngresoDataSource.open();

        globalLstControlIngresoGuardar = objControlIngresoDataSource.GetAllControlIngreso();
        objControlIngresoDataSource.close();

        if (globalLstControlIngresoGuardar != null) {
            if (globalLstControlIngresoGuardar.size() > 0) {
                List<ControlIngreso> lstControlIngresoTmp = new ArrayList<ControlIngreso>();
                lstControlIngresoTmp.add(globalLstControlIngresoGuardar.get(0));
                String strDsControlAcceso = objControlIngresoDataSource.GenerarDataSet(lstControlIngresoTmp);
                controlIngIndex++;
                new AsyncExecuteGuardarControlAcceso(this, getActivity()).execute(strDsControlAcceso);
            } else {
                Toast.makeText(getActivity(), "No hay controles de acceso para guardar!!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(), "No hay controles de acceso para guardar!!", Toast.LENGTH_LONG).show();
        }
    }

    public void BorrarInspeccionesLocales() {
        settings = getActivity().getSharedPreferences(OutSafetyUtils.PREFS_NAME, 0);
        strUsuario = settings.getString(OutSafetyUtils.SHARED_PREFERENCE_USERNAME, "");

        //Inspecciones
        Insp_InspeccionDataSource objInsp_InspeccionDataSource = new Insp_InspeccionDataSource(getContext());
        objInsp_InspeccionDataSource.open();
        objInsp_InspeccionDataSource.truncateTable();
        objInsp_InspeccionDataSource.close();

        //Parametros
        Insp_ParametroDataSource objInsp_ParametroDataSource = new Insp_ParametroDataSource(getContext());
        objInsp_ParametroDataSource.open();
        objInsp_ParametroDataSource.truncateTable();
        objInsp_ParametroDataSource.close();

        //Colaboradores
        Insp_ColaboradorDataSource objInsp_ColaboradorDataSource = new Insp_ColaboradorDataSource(getContext());
        objInsp_ColaboradorDataSource.open();
        objInsp_ColaboradorDataSource.truncateTable();
        objInsp_ColaboradorDataSource.close();

        //Evidencias
        Insp_EvidenciaDataSource objInsp_EvidenciaDataSource = new Insp_EvidenciaDataSource(getContext());
        objInsp_EvidenciaDataSource.open();
        objInsp_EvidenciaDataSource.truncateTable();
        objInsp_EvidenciaDataSource.close();

        //Control de Acceso
        ControlIngresoDataSource objControlIngresoDataSource = new ControlIngresoDataSource(getContext());
        objControlIngresoDataSource.open();
        objControlIngresoDataSource.truncateTable();
        objControlIngresoDataSource.close();
    }

    public void GuardarInspeccionesUnaPorUna() {
        boolEsInspeccionPositiva = true;
        longIdInspeccionGuardada = null;

        settings = getActivity().getSharedPreferences(OutSafetyUtils.PREFS_NAME, 0);
        strUsuario = settings.getString(OutSafetyUtils.SHARED_PREFERENCE_USERNAME, "");

        if (contadorInspeccioes == 0) {
            //Inspecciones
            Insp_InspeccionDataSource objInsp_InspeccionDataSource = new Insp_InspeccionDataSource(getContext());
            objInsp_InspeccionDataSource.open();
            lstInsp_InspeccionGuardar = objInsp_InspeccionDataSource.GetAllComplete();
            objInsp_InspeccionDataSource.close();
        }

        if (lstInsp_InspeccionGuardar.size() == 0) {
            if (!descargando) {
                Toast.makeText(getActivity(), "Sincronización Finalizada!!", Toast.LENGTH_LONG).show();
                OutSafetyUtils.MostrarAlerta("Sincronización Terminada, no hay inspecciones para cargar!!!", getContext());
            } else {
                Toast.makeText(getActivity(), "No hay inspecciones por cargar!!", Toast.LENGTH_LONG).show();
            }

            return;
        }

        newInspeccion = lstInsp_InspeccionGuardar.get(contadorInspeccioes);
        String serialInsp_Inspeccion = Insp_InspeccionDataSource.GetJson(newInspeccion);

        //Parametros
        List<Insp_Parametro> lstInsp_Parametro = new ArrayList<Insp_Parametro>();
        Insp_ParametroDataSource objInsp_ParametroDataSource = new Insp_ParametroDataSource(getContext());
        objInsp_ParametroDataSource.open();
        lstInsp_Parametro = objInsp_ParametroDataSource.GetInsp_Parametro(newInspeccion.getId());
        objInsp_ParametroDataSource.close();

        for (Insp_Parametro itemParam :
                lstInsp_Parametro) {
            if (!itemParam.isBoolCumple()) {
                boolEsInspeccionPositiva = false;
                break;
            }
        }

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String serialInsp_Parametro = gson.toJson(lstInsp_Parametro);

        String strGuardarInspeccionRequest = serialInsp_Inspeccion + "|" + serialInsp_Parametro;

        contadorInspeccioes++;
        Toast.makeText(getActivity(), "Enviando inspección " + contadorInspeccioes + " de " + lstInsp_InspeccionGuardar.size() + "!!!", Toast.LENGTH_LONG).show();

        new AsyncExecuteGuardarEncabezadoInspeccion(this, getActivity()).execute(strGuardarInspeccionRequest, OutSafetyUtils.GetCurrentModoUso(getContext()), Long.toString(newInspeccion.getId()), Long.toString(contadorInspeccioes));
    }


    public void ClearCurrentTables() {
        CentroTrabajoDataSource objCentroTrabajoDataSource;
        objCentroTrabajoDataSource = new CentroTrabajoDataSource(getContext());
        objCentroTrabajoDataSource.open();
        int cantidadCts = objCentroTrabajoDataSource.GetAllCentrosTrabajo().size();
        objCentroTrabajoDataSource.truncateTable();
        objCentroTrabajoDataSource.close();

        AreaDataSource objAreaDataSource;
        objAreaDataSource = new AreaDataSource(getContext());
        objAreaDataSource.open();
        objAreaDataSource.truncateTable();
        objAreaDataSource.close();

        RiesgoDataSource objRiesgoDataSource;
        objRiesgoDataSource = new RiesgoDataSource(getContext());
        objRiesgoDataSource.open();
        objRiesgoDataSource.truncateTable();
        objRiesgoDataSource.close();

        InspeccionDataSource objInspeccionDataSource;
        objInspeccionDataSource = new InspeccionDataSource(getContext());
        objInspeccionDataSource.open();
        objInspeccionDataSource.truncateTable();
        objInspeccionDataSource.close();

        PersonaDataSource objPersonaDataSource;
        objPersonaDataSource = new PersonaDataSource(getContext());
        objPersonaDataSource.open();
        objPersonaDataSource.truncateTable();
        objPersonaDataSource.close();

        ParametroDataSource objParametroDataSource;
        objParametroDataSource = new ParametroDataSource(getContext());
        objParametroDataSource.open();
        objParametroDataSource.truncateTable();
        objParametroDataSource.close();

        HabilidadDataSource objHabilidadDataSource;
        objHabilidadDataSource = new HabilidadDataSource(getContext());
        objHabilidadDataSource.open();
        objHabilidadDataSource.truncateTable();
        objHabilidadDataSource.close();

        Toast.makeText(getActivity(), "Se limpiaron las tablas!!", Toast.LENGTH_LONG).show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onTaskComplete(GuardarColaboradoresResult result) {
        if (result.isExito()) {
            if (contadorColaboradores < lstInsp_ColaboradorGuardar.size()) {
                GuardarColaboradores(result.getIntIdInspeccion(), result.getIntIdInspeccionLocal());
            } else {
                contadorColaboradores = 0;
                Insp_ColaboradorDataSource objInsp_ColaboradorDataSource = new Insp_ColaboradorDataSource(getContext());
                objInsp_ColaboradorDataSource.open();
                objInsp_ColaboradorDataSource.DeleteInsp_ColaboradorByConsecutivoInsp(result.getIntIdInspeccionLocal());
                objInsp_ColaboradorDataSource.close();

                Insp_EvidenciaDataSource objInsp_EvidenciaDataSource = new Insp_EvidenciaDataSource(getContext());
                objInsp_EvidenciaDataSource.open();
                objInsp_EvidenciaDataSource.DeleteInsp_EvidenciaByConsecutivoInsp(result.getIntIdInspeccionLocal());
                objInsp_EvidenciaDataSource.close();

                Insp_ParametroDataSource objInsp_ParametroDataSource = new Insp_ParametroDataSource(getContext());
                objInsp_ParametroDataSource.open();
                objInsp_ParametroDataSource.DeleteInsp_ParametroByConsecutivoInsp(result.getIntIdInspeccionLocal());
                objInsp_ParametroDataSource.close();

                Insp_InspeccionDataSource objInsp_InspeccionDataSource = new Insp_InspeccionDataSource(getContext());
                objInsp_InspeccionDataSource.open();
                objInsp_InspeccionDataSource.DeleteInsp_InspeccionByConsecutivoInsp(result.getIntIdInspeccionLocal());
                objInsp_InspeccionDataSource.close();

                if (!boolEsInspeccionPositiva) {
                    if (longIdInspeccionGuardada > 0) {
                        new AsyncExecuteEnviarCorreoInspeccion(this, getActivity()).execute(Long.toString(longIdInspeccionGuardada), OutSafetyUtils.GetCurrentModoUso(getContext()));
                    }
                }


                if (contadorInspeccioes < lstInsp_InspeccionGuardar.size()) {
                    GuardarInspeccionesUnaPorUna();
                } else {
                    contadorInspeccioes = 0;
                    objInsp_InspeccionDataSource = new Insp_InspeccionDataSource(getContext());
                    objInsp_InspeccionDataSource.open();
                    objInsp_InspeccionDataSource.truncateTable();
                    objInsp_InspeccionDataSource.close();

                    if (mProgress != null) {
                        mProgress.dismiss();
                    }

                    if (!descargando) {
                        Toast.makeText(getActivity(), "Inspecciones Cargadas!!", Toast.LENGTH_LONG).show();
                        OutSafetyUtils.MostrarAlerta("Sincronización Terminada!!!", getContext());
                    }

                }
            }
        }
    }

    @Override
    public void onTaskComplete(InputConfigurarInspeccion result) {
        if (ctIndex < globalListCentroTrabajoSelected.size()) {

            try {

                Toast.makeText(getActivity(), "...No interrumpir el proceso, por favor espera!!", Toast.LENGTH_LONG).show();

                globalInputConfigurarInspeccion = result;

                AreaDataSource objAreaDataSource;
                objAreaDataSource = new AreaDataSource(getContext());
                objAreaDataSource.open();
                for (Area itemArea : result.getLstArea()
                        ) {
                    objAreaDataSource.CreateArea(itemArea.getIntIdEmpresa()
                            , itemArea.getIntIdArea()
                            , itemArea.getStrDescripcion()
                    );
                }
                objAreaDataSource.close();

                RiesgoDataSource objRiesgoDataSource;
                objRiesgoDataSource = new RiesgoDataSource(getContext());
                objRiesgoDataSource.open();
                for (Riesgo itemRiesgo : result.getLstRiesgo()) {
                    objRiesgoDataSource.CreateRiesgo(itemRiesgo.getIntIdRiesgo()
                            , itemRiesgo.getStrDescripcionRiesgo()
                            , globalInputConfigurarInspeccion.getIntIdEmpresa());
                }
                objRiesgoDataSource.close();

                InspeccionDataSource objInspeccionDataSource;
                objInspeccionDataSource = new InspeccionDataSource(getContext());
                objInspeccionDataSource.open();
                for (Inspeccion itemInspec : result.getLstInspeccion()
                        ) {
                    objInspeccionDataSource.CreateInspeccion(itemInspec.getIntIdEmpresa()
                            , itemInspec.getIntIdInspeccion()
                            , itemInspec.getStrDescripcionInspeccion()
                            , itemInspec.getIntIdRiesgo());
                }
                objInspeccionDataSource.close();

                PersonaDataSource objPersonaDataSource;
                objPersonaDataSource = new PersonaDataSource(getContext());
                objPersonaDataSource.open();

                List<Persona> lstPersonas = result.getLstPersonas();

                for (Persona itemPersona : lstPersonas
                        ) {
                    objPersonaDataSource.CreatePesona(itemPersona.getBitActivo()
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
                            , itemPersona.getStrRazonSocial());
                }
                objPersonaDataSource.close();

                ParametroDataSource objParametroDataSource;
                objParametroDataSource = new ParametroDataSource(getContext());
                objParametroDataSource.open();
                for (Parametro itemParam : result.getLstParametro()
                        ) {
                    objParametroDataSource.CreateParametro(itemParam.getIntIdParametro()
                            , itemParam.getIntIdInspeccion()
                            , itemParam.getIntIdRiesgo()
                            , itemParam.getStrDescripcionParametro()
                            , itemParam.getStrRutaImagen()
                            , itemParam.getIntIdEmpresa()
                            , itemParam.getDtfecuser()
                            , Boolean.toString(itemParam.isBoolCumple()));
                }
                objParametroDataSource.close();

                HabilidadDataSource objHabilidadDataSource;
                objHabilidadDataSource = new HabilidadDataSource(getContext());
                objHabilidadDataSource.open();
                for (Habilidad itemHabilidad : result.getLstHabilidad()
                        ) {
                    objHabilidadDataSource.CreateHabilidad(itemHabilidad.getIntIdCentroTrabajo()
                            , itemHabilidad.getIntIdEmpresa()
                            , itemHabilidad.getStrRazonSocial()
                            , itemHabilidad.getStrNombreProfesional()
                            , itemHabilidad.getStrApellidoProfesional()
                            , itemHabilidad.getStrCedula()
                            , itemHabilidad.getIntIdHabilidad()
                            , itemHabilidad.getStrDescripcionHabilidad());
                }
                objHabilidadDataSource.close();

                //Toast.makeText(getActivity(), globalListCentroTrabajoSelected.get(ctIndex).getStrRazonSocial().toUpperCase() + " Sincronizado!!", Toast.LENGTH_SHORT).show();

            } catch (Exception exception) {

                OutSafetyUtils.MostrarAlerta("ERROR al sincronizar, consulte el Administrador!!", getContext());
            }
        }

        ctIndex++;

        if (ctIndex < globalListCentroTrabajoSelected.size()) {
            new AsyncExecuteGetConfigurarInspeccion(this, getActivity(), "Consultando Areas,Riesgos,Inspecciones,Personas y Habilidades de " + globalListCentroTrabajoSelected.get(ctIndex).getStrRazonSocial().toUpperCase() + "!!...Puede tardar varios minutos!!").execute(globalListCentroTrabajoSelected.get(ctIndex).getIntIdEmpresa(), this.strUsuario, OutSafetyUtils.GetCurrentModoUso(getContext()), OutSafetyUtils.CONS_SINCRONIZANDO, "SI");
        } else {
            mProgress.dismiss();
            Toast.makeText(getActivity(), "Sincronización Finalizada!!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onTaskComplete(List<CentroTrabajo> result) {

        Toast.makeText(getActivity(), "....Consultando centros de trabajo,favor espera!!", Toast.LENGTH_LONG).show();

        globalListCentroTrabajo = result;

        objListView = (ListView) dwDialog.findViewById(R.id.lstvCentrosTrabajoSync);
        List<CentroTrabajo> lstCentrosTrabajo = result;
        CentroTrabajoSyncAdapter adapterCentrosTrabajo = new CentroTrabajoSyncAdapter(this.getActivity(), R.layout.item_centro_trabajo_sync, lstCentrosTrabajo);
        objListView.setAdapter(adapterCentrosTrabajo);

    }

    public boolean ValidateSelectedCt() {
        globalListCentroTrabajoSelected = new ArrayList<CentroTrabajo>();
        boolean selectedCt = false;
        for (int i = 0; i < objListView.getCount(); i++) {
            CentroTrabajo currentCentroTrabajo = (CentroTrabajo) objListView.getAdapter().getItem(i);
            if (currentCentroTrabajo.isBoolSelectedCt() == true) {
                globalListCentroTrabajoSelected.add(currentCentroTrabajo);
                selectedCt = true;
            }
        }

        return selectedCt;
    }


    @Override
    public void onTaskComplete(GuardarInspeccionResult result) {
        if (result.isExito()) {
            longIdInspeccionGuardada = result.getIntIdInspeccion();
            GuardarEvidencias(result.getIntIdInspeccion(), result.getIntIdInspeccionLocal());
        } else {
            OutSafetyUtils.MostrarAlerta("No fue posible guardar las inspecciones, consulte con el administrador!!", getContext());
        }
    }

    @Override
    public void onTaskComplete(GuardarControlAccesoResult result) {
        if (controlIngIndex < globalLstControlIngresoGuardar.size()) {
            ControlIngresoDataSource objControlIngresoDataSource = new ControlIngresoDataSource(getContext());
            List<ControlIngreso> lstControlIngresoTmp = new ArrayList<ControlIngreso>();
            lstControlIngresoTmp.add(globalLstControlIngresoGuardar.get(controlIngIndex));
            String strDsControlAcceso = objControlIngresoDataSource.GenerarDataSet(lstControlIngresoTmp);
            controlIngIndex++;
            new AsyncExecuteGuardarControlAcceso(this, getActivity()).execute(strDsControlAcceso);
        } else {
            ControlIngresoDataSource objControlIngresoDataSource = new ControlIngresoDataSource(getContext());
            objControlIngresoDataSource.open();
            objControlIngresoDataSource.truncateTable();
            objControlIngresoDataSource.close();
            Toast.makeText(getActivity(), "Control de acceso sincronizadó!!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onTaskComplete(GuardarEvidenciasResult result) {
        if (result.isExito()) {
            if (contadorEvidencias < lstInsp_EvidenciaGuardar.size()) {
                GuardarEvidencias(result.getIntIdInspeccion(), result.getIntIdInspeccionLocal());
            } else {
                contadorEvidencias = 0;
                GuardarColaboradores(result.getIntIdInspeccion(), result.getIntIdInspeccionLocal());
            }
        }
    }

    public void GuardarColaboradores(long idInspeccionCampo, long idInspeccionLocal) {

        if (contadorColaboradores == 0) {
            Insp_ColaboradorDataSource objInsp_ColaboradorDataSource = new Insp_ColaboradorDataSource(getContext());
            objInsp_ColaboradorDataSource.open();
            lstInsp_ColaboradorGuardar = new ArrayList<Insp_Colaborador>();
            lstInsp_ColaboradorGuardar = objInsp_ColaboradorDataSource.GetInsp_Colaborador(idInspeccionLocal);
            objInsp_ColaboradorDataSource.close();
        }

        Insp_Colaborador itemPersona = lstInsp_ColaboradorGuardar.get(contadorColaboradores);

        contadorColaboradores++;

        new AsyncExecuteGuardarColaboradoresInspeccion(this, getActivity()).execute(Long.toString(idInspeccionCampo), itemPersona.getStrFirmaBase64(), itemPersona.getStrCedula(), OutSafetyUtils.GetCurrentModoUso(getContext()), Long.toString(idInspeccionLocal));
    }

    @Override
    public void onTaskComplete(Boolean result) {

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
        public void onFragmentInteraction(Uri uri);

    }

    public void OnClickSynDownload() {

    }

    public void GuardarEvidencias(long idInspeccionCampo, long idInspeccionLocal) {

        String evidenciaGuardar = "";
        int contadorEvidenciasIterar = 0;

        if (contadorEvidencias == 0) {
            //Evidencias
            Insp_EvidenciaDataSource objInsp_EvidenciaDataSource = new Insp_EvidenciaDataSource(getContext());
            objInsp_EvidenciaDataSource.open();
            lstInsp_EvidenciaGuardar = new ArrayList<Insp_Evidencia>();
            lstInsp_EvidenciaGuardar = objInsp_EvidenciaDataSource.GetInsp_Evidencia(idInspeccionLocal);
            objInsp_EvidenciaDataSource.close();
        }

        //Evidencias
        Insp_Evidencia itemEvidencia = lstInsp_EvidenciaGuardar.get(contadorEvidencias);

        contadorEvidencias++;

        new AsyncExecuteGuardarEvidenciasInspeccion(this, getActivity()).execute(Long.toString(idInspeccionCampo), itemEvidencia.getStrEvidenciaBase64(), OutSafetyUtils.GetCurrentModoUso(getContext()), Long.toString(idInspeccionLocal));

    }

}
