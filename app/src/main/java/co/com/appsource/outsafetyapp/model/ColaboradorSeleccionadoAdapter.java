package co.com.appsource.outsafetyapp.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.com.appsource.outsafetyapp.MainActivity;
import co.com.appsource.outsafetyapp.R;
import co.com.appsource.outsafetyapp.db_helper.Tables.Persona;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;

/**
 * Created by JANUS on 08/12/2015.
 */
public class ColaboradorSeleccionadoAdapter extends ArrayAdapter<Persona> {

    Context context;
    int layoutId;
    public List<Persona> mData;


    public ColaboradorSeleccionadoAdapter(Context context, int resourceId,
                                          List<Persona> items) {
        super(context, resourceId, items);
        this.context = context;
        this.mData = items;
    }

    @Override
    public void remove(Persona object) {
        super.remove(object);
        this.mData.remove(object);
    }

    public void replaceItem(int position, Persona object) {
        this.mData.set(position, object);
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Persona getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txv_num_documento_selected;
        TextView txv_nombre_selected;
        ImageButton imbtn_firmar;
        TextView txv_hidden_posicion_select;
        ImageButton imbtn_remove_sel;
        ImageView img_view_firmado;
        TextView txv_hidden_firma;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Persona rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_colaborador_seleccionado, null);
            holder = new ViewHolder();
            holder.txv_num_documento_selected = (TextView) convertView.findViewById(R.id.txv_num_documento_selected);
            holder.txv_nombre_selected = (TextView) convertView.findViewById(R.id.txv_nombre_selected);
            holder.imbtn_firmar = (ImageButton) convertView.findViewById(R.id.imbtn_firmar);
            holder.txv_hidden_posicion_select = (TextView) convertView.findViewById(R.id.txv_hidden_posicion_select);
            holder.imbtn_remove_sel = (ImageButton) convertView.findViewById(R.id.imbtn_remove_sel);
            holder.img_view_firmado = (ImageView) convertView.findViewById(R.id.img_view_firmado);
            holder.txv_hidden_firma = (TextView) convertView.findViewById(R.id.txv_hidden_firma);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();


        holder.txv_num_documento_selected.setText(rowItem.getStrCedula());
        holder.txv_nombre_selected.setText(rowItem.getStrNombreProfesional() + " " + rowItem.getStrApellidoProfesional());
        holder.txv_hidden_posicion_select.setText(Integer.toString(position));

        holder.imbtn_firmar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        String strNumDoc = ((android.support.v7.widget.AppCompatTextView) ((android.widget.LinearLayout) arg0.getParent()).getChildAt(0)).getText().toString();
                        int intPosicion = Integer.parseInt(((android.support.v7.widget.AppCompatTextView) ((android.widget.LinearLayout) arg0.getParent()).getChildAt(4)).getText().toString());
                        String strFirma = ((android.support.v7.widget.AppCompatTextView) ((android.widget.LinearLayout) arg0.getParent()).getChildAt(6)).getText().toString();
                        MainActivity objMainActivity = (MainActivity) getContext();
                        objMainActivity.ShowFirmar(intPosicion, strFirma);
                    }
                }
        );

        if (rowItem.getStrFirmaBase64() != null) {
            if (rowItem.getStrFirmaBase64() != "") {
                if (!(rowItem.getStrFirmaBase64().length() == OutSafetyUtils.SIZE_EMPTY_SIGNATURE)) {
                    holder.img_view_firmado.setVisibility(View.VISIBLE);
                }
            } else {
                holder.img_view_firmado.setVisibility(View.GONE);
            }
        } else {
            holder.img_view_firmado.setVisibility(View.GONE);
        }

        holder.txv_hidden_firma.setText(rowItem.getStrFirmaBase64());

        holder.imbtn_remove_sel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        String strNumDoc = ((android.support.v7.widget.AppCompatTextView) ((android.widget.LinearLayout) arg0.getParent()).getChildAt(0)).getText().toString();
                        int intPosicion = Integer.parseInt(((android.support.v7.widget.AppCompatTextView) ((android.widget.LinearLayout) arg0.getParent()).getChildAt(4)).getText().toString());
                        MainActivity objMainActivity = (MainActivity) getContext();
                        objMainActivity.RemoverColaborador(strNumDoc, intPosicion);
                    }
                }
        );


        if (rowItem.isEsInspeccionPositiva()) {
            holder.imbtn_firmar.setVisibility(View.GONE);
        }

        return convertView;
    }
}
