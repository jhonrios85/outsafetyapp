package co.com.appsource.outsafetyapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import java.util.List;

import co.com.appsource.outsafetyapp.Async.AsyncExecuteGetEmpresa;
import co.com.appsource.outsafetyapp.complete.EmpresaComplete;
import co.com.appsource.outsafetyapp.db_helper.Tables.Empresa;
import co.com.appsource.outsafetyapp.db_helper.Tables.EmpresaDataSource;
import co.com.appsource.outsafetyapp.model.EmpresaAdapter;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HazardIdPortadaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HazardIdPortadaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HazardIdPortadaFragment extends android.support.v4.app.Fragment implements EmpresaComplete {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    EmpresaAdapter objEmpresaAdapter;
    AutoCompleteTextView autoCompleteEmpresa;
    public View objHazardIdPortadaFragment = null;

    public HazardIdPortadaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HazardIdPortadaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HazardIdPortadaFragment newInstance(String param1, String param2) {
        HazardIdPortadaFragment fragment = new HazardIdPortadaFragment();
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

        objHazardIdPortadaFragment = inflater.inflate(R.layout.fragment_hazard_id_portada, container, false);

        new AsyncExecuteGetEmpresa(this, getActivity()).execute(OutSafetyUtils.GetCurrentModoUso(getContext()));


        // Inflate the layout for this fragment
        return objHazardIdPortadaFragment;
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
    public void onTaskComplete(List<Empresa> result) {
        if (result != null) {
            EmpresaDataSource objEmpresaDataSource = new EmpresaDataSource(getContext());
            objEmpresaDataSource.open();
            for (Empresa itemEmpresa :
                    result) {
                objEmpresaDataSource.CreateEmpresa(itemEmpresa.getIntIdEmpresa()
                        , itemEmpresa.getStrNit()
                        , itemEmpresa.getStrRazonSocial()
                        , itemEmpresa.getStrRepresentanteLegal()
                        , itemEmpresa.getStrEmail()
                        , itemEmpresa.getStrResponsableSiso()
                        , itemEmpresa.getStrEmailResponsableSiso()
                        , itemEmpresa.getBoolEstado()
                        , itemEmpresa.getIntIdTipoEmpresa());
            }

            objEmpresaDataSource.close();

            autoCompleteEmpresa = (AutoCompleteTextView) objHazardIdPortadaFragment.findViewById(R.id.autoCompleteEmpresa);

            EmpresaDataSource objEmpresaDataSourceAdapter = new EmpresaDataSource(getContext());
            objEmpresaAdapter = new EmpresaAdapter(objEmpresaDataSourceAdapter, getContext());

            autoCompleteEmpresa.setAdapter(objEmpresaAdapter);
            autoCompleteEmpresa.setOnItemClickListener(objEmpresaAdapter);
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
