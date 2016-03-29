package co.com.appsource.outsafetyapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPager;

import java.util.ArrayList;
import java.util.List;

import co.com.appsource.outsafetyapp.model.HazardIdPagerAdapter;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HazardId.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HazardId#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HazardId extends android.support.v4.app.Fragment {

    ArrayList<String> categories = new ArrayList<String>();
    View viewHazardId;
    private MaterialViewPager mViewPager;
    ViewPager viewPager;
    HazardIdPagerAdapter objHazardIdPagerAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HazardId() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HazardId.
     */
    // TODO: Rename and change types and number of parameters
    public static HazardId newInstance(String param1, String param2) {
        HazardId fragment = new HazardId();
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

    private List<Fragment> buildFragments() {
        List<android.support.v4.app.Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i < categories.size(); i++) {
            Bundle b = new Bundle();
            b.putInt("position", i);
            fragments.add(Fragment.instantiate(getContext(), HazardId.class.getName(), b));
        }

        return fragments;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        categories.add("1");

        viewHazardId = inflater.inflate(R.layout.fragment_hazard_id, container, false);
        List<Fragment> fragments = buildFragments();

        mViewPager = (MaterialViewPager) viewHazardId.findViewById(R.id.materialViewPager);
        viewPager = mViewPager.getViewPager();
        objHazardIdPagerAdapter = new HazardIdPagerAdapter(getContext()
                , getActivity().getSupportFragmentManager()
                , fragments
                , categories);
        viewPager.setAdapter(objHazardIdPagerAdapter);
        Bundle b = new Bundle();
        b.putInt("position", 0);
        String title = "asd";
        objHazardIdPagerAdapter.add(HazardIdPortadaFragment.class, "", b);
        objHazardIdPagerAdapter.notifyDataSetChanged();


        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        return viewHazardId;
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
