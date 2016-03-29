package co.com.appsource.outsafetyapp.model;

import android.app.Activity;
import android.content.Context;
import android.text.BoringLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import co.com.appsource.outsafetyapp.MainActivity;
import co.com.appsource.outsafetyapp.R;
import co.com.appsource.outsafetyapp.db_helper.Tables.Parametro;

/**
 * Created by JANUS on 29/11/2015.
 */
public class ParametroAdapter extends ArrayAdapter<Parametro> {

    Context context;
    int layoutId;
    private List<Parametro> mData;


    public ParametroAdapter(Context context, int resourceId,
                            List<Parametro> items) {
        super(context, resourceId, items);
        this.context = context;
        this.mData = items;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Parametro getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txv_lb_nom_parametro;
        TextView txv_lb_id_parametro;
        CheckBox checkBoxParametro;
        TextView txv_posicion_param;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Parametro rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_parametro, null);
            holder = new ViewHolder();
            holder.txv_lb_nom_parametro = (TextView) convertView.findViewById(R.id.txv_lb_nom_parametro);
            holder.checkBoxParametro = (CheckBox) convertView.findViewById(R.id.checkBoxParametro);
            holder.txv_lb_id_parametro = (TextView) convertView.findViewById(R.id.txv_lb_id_parametro);
            holder.txv_posicion_param = (TextView) convertView.findViewById(R.id.txv_posicion_param);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();


        holder.txv_lb_nom_parametro.setText(rowItem.getStrDescripcionParametro());
        holder.txv_lb_id_parametro.setText(rowItem.getIntIdParametro());
        holder.txv_posicion_param.setText(Integer.toString(position));

/*        holder.checkBoxParametro.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        CheckBox checkBoxParam = ((android.support.v7.widget.AppCompatCheckBox) ((android.widget.RelativeLayout) arg0.getParent()).getChildAt(0));
                        TextView txvPos = ((android.support.v7.widget.AppCompatTextView) ((android.widget.RelativeLayout) arg0.getParent()).getChildAt(3));
                        Parametro currentParametro = (Parametro) ((ListView) arg0.getParent().getParent()).getAdapter().getItem(Integer.parseInt(txvPos.getText().toString()));
                        currentParametro.setBoolCumple(Boolean.toString(checkBoxParam.isChecked()));
                    }
                }
        );*/

        holder.checkBoxParametro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = (int) buttonView.getTag();
                mData.get(pos).setBoolCumple(buttonView.isChecked());
            }
        });

        holder.checkBoxParametro.setTag(position);
        holder.checkBoxParametro.setChecked(rowItem.isBoolCumple());

        return convertView;
    }
}
