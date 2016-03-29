package co.com.appsource.outsafetyapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import co.com.appsource.outsafetyapp.util.OutSafetyUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EvidenciasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EvidenciasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EvidenciasFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final int CAM_REQUEST = 1313;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static final String PREFS_NAME = "OutsafetyPrefsFile";
    private OnFragmentInteractionListener mListener;
    public int indexFotoBorrar = -1;

    public Bitmap bitEvidencia;
    View objEvidenciaFragment;
    private android.net.Uri mImageUri;

    public EvidenciasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EvidenciasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EvidenciasFragment newInstance(String param1) {
        EvidenciasFragment fragment = new EvidenciasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
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

        ViewParent parent = objEvidenciaFragment.getParent();
        View current = objEvidenciaFragment;
        if (parent != null) {
            current = (View) parent;

            Window window = getDialog().getWindow();
            Point size = new Point();
            Display display = window.getWindowManager().getDefaultDisplay();
            display.getSize(size);

            current.getLayoutParams().height = (int) (size.y * 0.90);

        }

        current.requestLayout();
    }

    public void OpenCam(View v) {
        Toast.makeText(getActivity(), "Tomar Foto.", Toast.LENGTH_SHORT).show();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityForResult(cameraIntent, CAM_REQUEST);

    }

    public void OpenCamFull(View v) {

        Random r = new Random();
        int i1 = r.nextInt(1000 - 1) + 1;

        if (!OutSafetyUtils.CheckExternalStorage()) {
            Toast.makeText(getActivity(), "Tomar Foto.", Toast.LENGTH_SHORT).show();
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            getActivity().startActivityForResult(cameraIntent, CAM_REQUEST);
        } else {
            Toast.makeText(getActivity(), "Tomar Foto.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + i1 + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(OutSafetyUtils.SHARED_PREFERENCE_ID_FOTO, Integer.toString(i1));
            editor.commit();

            ((MainActivity) getActivity()).idNuevaEvidencia = i1;
            getActivity().startActivityForResult(intent, NavigationDrawerFragment.CAM_REQUEST_FULL_SIZE);
        }
    }

    private File createTemporaryFile(String part, String ext) throws Exception {
        File tempDir = Environment.getExternalStorageDirectory();
        tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
        return File.createTempFile(part, ext, tempDir);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Window window = getDialog().getWindow();
/*        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        window.setLayout((int) (width * 0.90), (int) (height * 0.75));*/

        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER_VERTICAL;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);


        objEvidenciaFragment = inflater.inflate(R.layout.fragment_evidencias, container, false);

        if (bitEvidencia != null) {
            ImageView objEvidenciaImg = (ImageView) objEvidenciaFragment.findViewById(R.id.imageViewEvidencia);
            objEvidenciaImg.setImageBitmap(bitEvidencia);
            objEvidenciaImg.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        HashSet<String> objEvidencias = (HashSet<String>) settings.getStringSet("evidencias", new HashSet<String>());

        for (String itemEvidencia :
                objEvidencias) {

            Bitmap bitEvidenciaItem = null;
            if (!itemEvidencia.toUpperCase().contains(".JPG")) {
                byte[] bloc = Base64.decode(itemEvidencia, Base64.DEFAULT);
                bitEvidenciaItem = BitmapFactory.decodeByteArray(bloc, 0, bloc.length);
            } else {
                bitEvidenciaItem = OutSafetyUtils.decodeSampledBitmapFromFile(itemEvidencia, 600, 500);
            }


            LinearLayout linGallery = (LinearLayout) objEvidenciaFragment.findViewById(R.id.gallery);
            //ImageView newImage = new ImageView(getActivity());
            ImageView newImage = (ImageView) inflater.inflate(R.layout.item_foto_evidencia, null);
            newImage.setImageBitmap(bitEvidenciaItem);
            newImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    biggerView(v);
                }
            });
            registerForContextMenu(newImage);
            linGallery.addView(newImage);

            TextView title_dialog_evidencias = (TextView) objEvidenciaFragment.findViewById(R.id.title_dialog_evidencias);
            if (linGallery.getChildCount() > 0) {
                title_dialog_evidencias.setText(String.format("Evidencias (%s)", linGallery.getChildCount()));
            } else {
                title_dialog_evidencias.setText("Evidencias");
            }
        }

        ImageButton buttonTakePic = (ImageButton) objEvidenciaFragment.findViewById(R.id.buttonTakePic);
        buttonTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCamFull(v);
            }
        });

        ImageView btnClose = (ImageView) objEvidenciaFragment.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().popBackStack();
                dismiss();
            }
        });

        return objEvidenciaFragment;
    }

    public void UpdateEvidencias(Bitmap newEvidencia, String strPath) {
        if (newEvidencia != null) {
            ImageView objEvidenciaImg = (ImageView) objEvidenciaFragment.findViewById(R.id.imageViewEvidencia);
            objEvidenciaImg.setImageBitmap(newEvidencia);
            objEvidenciaImg.setScaleType(ImageView.ScaleType.FIT_XY);


            LinearLayout linGallery = (LinearLayout) objEvidenciaFragment.findViewById(R.id.gallery);
            //ImageView newImage = new ImageView(getActivity());
            LayoutInflater lInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ImageView newImage = (ImageView) lInflater.inflate(R.layout.item_foto_evidencia, null);

            newImage.setTag(strPath);

            newImage.setImageBitmap(newEvidencia);
            newImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    biggerView(v);
                }
            });
            registerForContextMenu(newImage);
            linGallery.addView(newImage);

            TextView title_dialog_evidencias = (TextView) objEvidenciaFragment.findViewById(R.id.title_dialog_evidencias);
            if (linGallery.getChildCount() > 0) {
                title_dialog_evidencias.setText(String.format("Evidencias (%s)", linGallery.getChildCount()));
            } else {
                title_dialog_evidencias.setText("Evidencias");
            }

        }
    }

    public void biggerView(View v) {
        ImageView im = (ImageView) objEvidenciaFragment.findViewById(R.id.imageViewEvidencia);
        ImageView selectedImage = (ImageView) v;
        im.setImageBitmap(((BitmapDrawable) selectedImage.getDrawable()).getBitmap());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get field from view
        //mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        // Fetch arguments from bundle and set title
        //String title = getArguments().getString("title", "Evidencias");
        // Show soft keyboard automatically and request focus to field
        //mEditText.requestFocus();
        Window wind = getDialog().getWindow();
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

    public int BorrarFotoPorString(String strBitmap) {
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        HashSet<String> objEvidencias = (HashSet<String>) settings.getStringSet("evidencias", new HashSet<String>());

        String borrar = "";
        if (objEvidencias.size() > 0) {
            for (String itemEvidencia :
                    objEvidencias) {
                if (itemEvidencia.equals(strBitmap)) {
                    borrar = itemEvidencia;
                }
            }
        }

        if (borrar != "") {
            objEvidencias.remove(borrar);
        }

        editor.putStringSet("evidencias", objEvidencias);
        editor.commit();

        for (String itemEvidencia :
                objEvidencias) {
            Bitmap bitEvidenciaItem = null;
            byte[] bloc = Base64.decode(itemEvidencia, Base64.DEFAULT);
            bitEvidenciaItem = BitmapFactory.decodeByteArray(bloc, 0, bloc.length);

            LinearLayout linGallery = (LinearLayout) objEvidenciaFragment.findViewById(R.id.gallery);
            LayoutInflater lInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ImageView newImage = (ImageView) lInflater.inflate(R.layout.item_foto_evidencia, null);
            newImage.setImageBitmap(bitEvidenciaItem);
            newImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    biggerView(v);
                }
            });
            registerForContextMenu(newImage);
            linGallery.addView(newImage);
        }

        return objEvidencias.size();
    }

    public int BorrarFoto(int posicion) {
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        HashSet<String> objEvidencias = new HashSet<String>();

        ViewGroup viewGroup = ((ViewGroup) objEvidenciaFragment.findViewById(R.id.gallery));
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            ImageView childImage = (ImageView) viewGroup.getChildAt(i);
            if (childImage.getTag() != null) {
                if (childImage.getTag().toString() != "") {
                    File f = new File(childImage.getTag().toString());
                    if (f.exists()) {
                        objEvidencias.add(childImage.getTag().toString());
                    }
                } else {
                    objEvidencias.add(OutSafetyUtils.BitMapToString(((BitmapDrawable) childImage.getDrawable()).getBitmap()));
                }
            } else {
                objEvidencias.add(OutSafetyUtils.BitMapToString(((BitmapDrawable) childImage.getDrawable()).getBitmap()));
            }
        }

        editor.putStringSet("evidencias", objEvidencias);
        editor.commit();

        return objEvidencias.size();
    }

    public int BorrarFotoNoSD(int posicion) {
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        HashSet<String> objEvidencias = new HashSet<String>();

        ViewGroup viewGroup = ((ViewGroup) objEvidenciaFragment.findViewById(R.id.gallery));
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            ImageView childImage = (ImageView) viewGroup.getChildAt(i);
            objEvidencias.add(OutSafetyUtils.BitMapToString(((BitmapDrawable) childImage.getDrawable()).getBitmap()));
        }

        editor.putStringSet("evidencias", objEvidencias);
        editor.commit();

        return objEvidencias.size();
    }

    public void PopulateFotos() {
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        HashSet<String> objEvidencias = (HashSet<String>) settings.getStringSet("evidencias", new HashSet<String>());

        for (String itemEvidencia :
                objEvidencias) {

            Bitmap bitEvidenciaItem = OutSafetyUtils.decodeSampledBitmapFromFile(itemEvidencia, 1000, 700);
            //byte[] bloc = Base64.decode(itemEvidencia, Base64.DEFAULT);
            //bitEvidenciaItem = BitmapFactory.decodeByteArray(bloc, 0, bloc.length);

            LinearLayout linGallery = (LinearLayout) objEvidenciaFragment.findViewById(R.id.gallery);
            LayoutInflater lInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ImageView newImage = (ImageView) lInflater.inflate(R.layout.item_foto_evidencia, null);
            newImage.setImageBitmap(bitEvidenciaItem);
            newImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    biggerView(v);
                }
            });
            registerForContextMenu(newImage);
            linGallery.addView(newImage);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        indexFotoBorrar = ((ViewGroup) v.getParent()).indexOfChild(v);

        MenuInflater mInflater = getActivity().getMenuInflater();
        mInflater.inflate(R.menu.borrar_foto, menu);
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                ImageView imgBorrar = (ImageView) ((ViewGroup) objEvidenciaFragment.findViewById(R.id.gallery)).getChildAt(indexFotoBorrar);

                int cantidadRestante = 0;

                if (imgBorrar.getTag() != null) {
                    if (imgBorrar.getTag().toString() != "") {
                        File fBorrar = new File(imgBorrar.getTag().toString());
                        if (fBorrar.exists()) {
                            fBorrar.delete();
                        }
                    }
                }

                ((ViewGroup) objEvidenciaFragment.findViewById(R.id.gallery)).removeViewAt(indexFotoBorrar);
                cantidadRestante = BorrarFoto(indexFotoBorrar);

                ImageView imgSelected = (ImageView) objEvidenciaFragment.findViewById(R.id.imageViewEvidencia);
                TextView title_dialog_evidencias = (TextView) objEvidenciaFragment.findViewById(R.id.title_dialog_evidencias);

                if (cantidadRestante > 0) {
                    ImageView imgMostrar = (ImageView) ((ViewGroup) objEvidenciaFragment.findViewById(R.id.gallery)).getChildAt(cantidadRestante - 1);
                    imgSelected.setImageBitmap(((BitmapDrawable) imgMostrar.getDrawable()).getBitmap());
                    title_dialog_evidencias.setText(String.format("Evidencias (%s)", cantidadRestante));
                } else {
                    imgSelected.setImageResource(android.R.drawable.ic_menu_camera);
                    title_dialog_evidencias.setText("Evidencias");
                }

                Toast.makeText(getView().getContext(), "Evidencia Borrada", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
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
