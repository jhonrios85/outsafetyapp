package co.com.appsource.outsafetyapp.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import co.com.appsource.outsafetyapp.MainActivity;
import co.com.appsource.outsafetyapp.R;
import co.com.appsource.outsafetyapp.db_helper.Tables.Persona;

public class ColaboradorEncontradoAdapter extends ArrayAdapter<Persona> {

    Context context;
    int layoutId;
    private List<Persona> mData;


    public ColaboradorEncontradoAdapter(Context context, int resourceId,
                                        List<Persona> items) {
        super(context, resourceId, items);
        this.context = context;
        this.mData = items;
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
        TextView txv_lb_num_documento;
        TextView txv_nombre;
        ImageButton imbtn_adicionar;
        TextView txv_hidden_posicion;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Persona rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_colaborador_encontrado, null);
            holder = new ViewHolder();
            holder.txv_lb_num_documento = (TextView) convertView.findViewById(R.id.txv_lb_num_documento);
            holder.txv_nombre = (TextView) convertView.findViewById(R.id.txv_nombre);
            holder.imbtn_adicionar = (ImageButton) convertView.findViewById(R.id.imbtn_adicionar);
            holder.txv_hidden_posicion = (TextView) convertView.findViewById(R.id.txv_hidden_posicion);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();


        holder.txv_lb_num_documento.setText(rowItem.getStrCedula());
        holder.txv_nombre.setText(rowItem.getStrNombreProfesional() + " " + rowItem.getStrApellidoProfesional());
        holder.txv_hidden_posicion.setText(Integer.toString(position));

        holder.imbtn_adicionar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        String strNumDoc = ((android.support.v7.widget.AppCompatTextView) ((android.widget.LinearLayout) arg0.getParent()).getChildAt(0)).getText().toString();
                        int intItemPosition = Integer.parseInt(((android.support.v7.widget.AppCompatTextView) ((android.widget.LinearLayout) arg0.getParent()).getChildAt(3)).getText().toString());
                        MainActivity objMainActivity = (MainActivity) getContext();
                        objMainActivity.AddColaboradorToInspeccion(strNumDoc, intItemPosition);
                    }
                }
        );

        return convertView;
    }
}