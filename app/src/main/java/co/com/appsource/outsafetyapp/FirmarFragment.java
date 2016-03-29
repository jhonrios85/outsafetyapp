package co.com.appsource.outsafetyapp;

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import co.com.appsource.outsafetyapp.util.OutSafetyUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FirmarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FirmarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirmarFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public int intPosicion;
    public String strFirmaBase64Saved = "";

    private OnFragmentInteractionListener mListener;

    View objFirmaFragment;

    public FirmarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirmarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirmarFragment newInstance(String param1, String param2) {
        FirmarFragment fragment = new FirmarFragment();
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
    public void onStart() {
        super.onStart();

        ViewParent parent = objFirmaFragment.getParent();
        View current = objFirmaFragment;
        if (parent != null) {
            current = (View) parent;

            Window window = getDialog().getWindow();
            Point size = new Point();
            Display display = window.getWindowManager().getDefaultDisplay();
            display.getSize(size);

            current.getLayoutParams().height = (int) (size.y * 0.80);
            current.getLayoutParams().width = (int) (size.x * 1);
        }

        current.requestLayout();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        objFirmaFragment = inflater.inflate(R.layout.fragment_firmar, container, false);

        ImageView btnClose = (ImageView) objFirmaFragment.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button btnAceptar_firma = (Button) objFirmaFragment.findViewById(R.id.btnAceptar_firma);
        btnAceptar_firma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarFirma();
                dismiss();
            }
        });

        ImageButton imbtn_limpiar_firm = (ImageButton) objFirmaFragment.findViewById(R.id.imbtn_limpiar_firm);
        imbtn_limpiar_firm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LimpiarFirma();
            }
        });

        ImageView imgViewFirma = (ImageView) objFirmaFragment.findViewById(R.id.imgViewFirma);

        imgViewFirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddeImage(v);
            }
        });

        if (strFirmaBase64Saved != null) {
            if (strFirmaBase64Saved != "") {
                if (strFirmaBase64Saved.length() != OutSafetyUtils.SIZE_EMPTY_SIGNATURE) {
                    GestureOverlayView gestureView = (GestureOverlayView) objFirmaFragment.findViewById(R.id.signaturePad);
                    gestureView.setVisibility(View.GONE);
                    imgViewFirma.setVisibility(View.VISIBLE);
                    Bitmap bitFirma = null;
                    byte[] bloc = Base64.decode(strFirmaBase64Saved, Base64.DEFAULT);
                    bitFirma = BitmapFactory.decodeByteArray(bloc, 0, bloc.length);
                    imgViewFirma.setImageBitmap(bitFirma);
                }

            }
        }


        return objFirmaFragment;
    }

    public void hiddeImage(View v) {
        GestureOverlayView gestureView = (GestureOverlayView) objFirmaFragment.findViewById(R.id.signaturePad);
        gestureView.setVisibility(View.VISIBLE);
        v.setVisibility(View.GONE);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Window wind = getDialog().getWindow();
        wind.requestFeature(Window.FEATURE_NO_TITLE);
        //wind.setSoftInputMode(
        //      WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
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

    public void GuardarFirma() {
        GestureOverlayView gestureView = (GestureOverlayView) objFirmaFragment.findViewById(R.id.signaturePad);
        gestureView.setDrawingCacheEnabled(true);

        if (gestureView.getGesture() != null) {
            Bitmap bm = Bitmap.createBitmap(gestureView.getDrawingCache());
            ((MainActivity) getActivity()).GuardarFirmaColaborador(intPosicion, OutSafetyUtils.BitMapToString(bm));
        }

    }

    public void LimpiarFirma() {
        GestureOverlayView gestureView = (GestureOverlayView) objFirmaFragment.findViewById(R.id.signaturePad);
        gestureView.cancelClearAnimation();
        gestureView.clear(true);

        View objImageViewFirma = objFirmaFragment.findViewById(R.id.imgViewFirma);

        if (objImageViewFirma.getVisibility() == View.VISIBLE) {
            objImageViewFirma.setVisibility(View.GONE);
            gestureView.setVisibility(View.VISIBLE);
        }
    }
}
