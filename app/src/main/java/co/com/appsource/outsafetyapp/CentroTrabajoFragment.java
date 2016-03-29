package co.com.appsource.outsafetyapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import co.com.appsource.outsafetyapp.Async.AsyncExecuteGetCentroTrabajo;
import co.com.appsource.outsafetyapp.complete.CentroTrabajoComplete;
import co.com.appsource.outsafetyapp.db_helper.Tables.CentroTrabajo;
import co.com.appsource.outsafetyapp.model.CentroTrabajoAdapter;
import co.com.appsource.outsafetyapp.model.CentroTrabajoButtonAdapter;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CentroTrabajoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CentroTrabajoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CentroTrabajoFragment extends android.support.v4.app.Fragment implements CentroTrabajoComplete {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public String strUsuario;

    private OnFragmentInteractionListener mListener;

    public View objCentroTrabajoFragment = null;
    public LinearLayout linCts = null;

    public static CentroTrabajoFragment fragment;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CentroTrabajoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CentroTrabajoFragment newInstance(String param1, String param2) {
        fragment = new CentroTrabajoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CentroTrabajoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onTaskComplete(List<CentroTrabajo> result) {
        ListView objListView = (ListView) objCentroTrabajoFragment.findViewById(R.id.lstvCentrosTrabajo);
        List<CentroTrabajo> lstCentrosTrabajo = result;
        //CentroTrabajoAdapter adapterCentrosTrabajo = new CentroTrabajoAdapter(this.getActivity(), R.layout.item_centro_trabajo, lstCentrosTrabajo);
        CentroTrabajoButtonAdapter adapterCentrosTrabajo = new CentroTrabajoButtonAdapter(this.getActivity(), R.layout.item_centro_trabajo, lstCentrosTrabajo);
        objListView.setAdapter(adapterCentrosTrabajo);
        //setListViewHeightBasedOnChildren(objListView);
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

        new AsyncExecuteGetCentroTrabajo(this, getActivity()).execute(this.strUsuario, OutSafetyUtils.GetCurrentModoUso(getContext()));
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
        objCentroTrabajoFragment = inflater.inflate(R.layout.fragment_centro_trabajo, container, false);

        return objCentroTrabajoFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString(OutSafetyUtils.SHARED_PREFERENCE_USERNAME, this.strUsuario);
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


}


