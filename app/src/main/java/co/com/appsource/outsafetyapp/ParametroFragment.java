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
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import co.com.appsource.outsafetyapp.complete.ParametroComplete;
import co.com.appsource.outsafetyapp.custom_objects.RestfulResponse;
import co.com.appsource.outsafetyapp.db_helper.Tables.Parametro;
import co.com.appsource.outsafetyapp.db_helper.Tables.ParametroDataSource;
import co.com.appsource.outsafetyapp.model.ParametroAdapter;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;
import co.com.appsource.outsafetyapp.util.RequestMethod;
import co.com.appsource.outsafetyapp.util.RestClient;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ParametroFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ParametroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParametroFragment extends android.support.v4.app.Fragment implements ParametroComplete {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public View objParametroFragment = null;

    public ParametroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParametroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ParametroFragment newInstance(String param1, String param2) {
        ParametroFragment fragment = new ParametroFragment();
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

    public void FiltrarParametros(String intIdInspeccion) {
        new AsyncExecuteGetParametro(this, getActivity()).execute(intIdInspeccion, OutSafetyUtils.GetCurrentModoUso(getContext()));
    }

    public View GetListViewParametros() {
        return objParametroFragment.findViewById(R.id.lstvParametros);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        objParametroFragment = inflater.inflate(R.layout.fragment_parametro, container, false);
        return objParametroFragment;
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
    public void onTaskComplete(List<Parametro> result) {
        ListView objListView = (ListView) objParametroFragment.findViewById(R.id.lstvParametros);
//        View footerView =  ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_list_parametros, null, false);
        //      objListView.addFooterView(footerView);

        List<Parametro> lstParametros = result;
        ParametroAdapter adapterParametros = new ParametroAdapter(this.getActivity(), R.layout.item_parametro, lstParametros);
        objListView.setAdapter(adapterParametros);

        //setListViewHeightBasedOnChildren(objListView);

    }

    public void ClearParametros() {
        ListView objListView = (ListView) objParametroFragment.findViewById(R.id.lstvParametros);
        List<Parametro> lstParametro = new ArrayList<Parametro>();
        ParametroAdapter adapterParametros = new ParametroAdapter(this.getActivity(), R.layout.item_parametro, lstParametro);
        objListView.setAdapter(adapterParametros);

        //setListViewHeightBasedOnChildren(objListView);
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

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}

class AsyncExecuteGetParametro extends AsyncTask<String, Void, List<Parametro>> {

    RestClient objRestClient;
    Gson gson;
    RestfulResponse objRestfulResponse;

    String intIdInspeccion = "";
    String strModoUso = "";
    String intIdCentroTrabajo = "";
    public static final String OPERACION_POR_INSPECCION = "EX";

    private Context mContext;
    ProgressDialog mProgress;
    private ParametroComplete mCallback;


    public static final String SOAP_ACTION_POR_INSPECCION = "http://tempuri.org/cargarParametros";
    public static final String OPERATION_NAME_POR_INSPECCION = "cargarParametros";

    ///OJO: El name espace debe ser exactamente igual al namespace del WebService en el asmx.cs
    public static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    public static final String SOAP_ADDRESS = OutSafetyUtils.CONS_SOAP_ADDRESS;


    public AsyncExecuteGetParametro(android.support.v4.app.Fragment objFragment, Context context) {
        this.mContext = context;
        this.mCallback = (ParametroComplete) objFragment;
    }

    @Override
    protected List<Parametro> doInBackground(String... params) {

        intIdInspeccion = params[0];
        strModoUso = params[1];

        if (OutSafetyUtils.MODO_USO_OFFLINE.equals(strModoUso)) {
            ParametroDataSource objParametroDataSource = new ParametroDataSource(mContext);
            objParametroDataSource.open();
            List<Parametro> lstParametro = objParametroDataSource.GetParametrosByInspeccion(intIdInspeccion);
            objParametroDataSource.close();

            return lstParametro;
        }

        List<Parametro> lstParametro = new ArrayList<Parametro>();

        //Parametros
        objRestClient = new RestClient(String.format(OutSafetyUtils.CONS_URL_RESTFUL_JSON + OutSafetyUtils.CONS_JSON_GET_PARAMETROS, "null", intIdInspeccion));
        objRestfulResponse = null;

        try {
            objRestClient.Execute(RequestMethod.GET);
            gson = new Gson();
            objRestfulResponse = gson.fromJson(objRestClient.getResponse(), RestfulResponse.class);
            lstParametro= gson.fromJson(objRestfulResponse.value, new TypeToken<List<Parametro>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lstParametro;
    }

    @Override
    protected void onPreExecute() {
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage("Consultando Parametros!!...");
        mProgress.show();
    }

    @Override
    protected void onPostExecute(List<Parametro> result) {
        mProgress.dismiss();
        mCallback.onTaskComplete(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(List<Parametro> result) {
        // TODO Auto-generated method stub
        super.onCancelled(result);
    }

    @Override
    protected void onCancelled() {
        // TODO Auto-generated method stub
        super.onCancelled();
    }
}
