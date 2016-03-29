package co.com.appsource.outsafetyapp.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.com.appsource.outsafetyapp.MainActivity;
import co.com.appsource.outsafetyapp.R;
import co.com.appsource.outsafetyapp.db_helper.Tables.CentroTrabajo;

/**
 * Created by JANUS on 25/11/2015.
 */
public class CentroTrabajoAdapter extends ArrayAdapter<CentroTrabajo> {

    Context context;
    int layoutId;
    private List<CentroTrabajo> mData;


    public CentroTrabajoAdapter(Context context, int resourceId,
                                List<CentroTrabajo> items) {
        super(context, resourceId, items);
        this.context = context;
        this.mData = items;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CentroTrabajo getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txv_lb_nom_centro_trabajo;
        TextView  txv_lb_id_centro_trabajo;
        CheckBox checkBoxCentroTrabajo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        CentroTrabajo rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_centro_trabajo, null);
            holder = new ViewHolder();
            holder.txv_lb_nom_centro_trabajo = (TextView) convertView.findViewById(R.id.txv_lb_nom_centro_trabajo);
            holder.checkBoxCentroTrabajo = (CheckBox) convertView.findViewById(R.id.checkBoxCentroTrabajo);
            holder.txv_lb_id_centro_trabajo= (TextView) convertView.findViewById(R.id.txv_lb_id_centro_trabajo);
            //holder.txv_lb_id_centro_trabajo = (TextView) convertView.findViewById(R.id.txv_lb_id_centro_trabajo);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();


        holder.txv_lb_nom_centro_trabajo.setText(rowItem.getStrRazonSocial());
        holder.txv_lb_id_centro_trabajo.setText(rowItem.getIntIdEmpresa());

        holder.checkBoxCentroTrabajo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        String strIdEmpresa= ((android.support.v7.widget.AppCompatTextView)((android.widget.LinearLayout)arg0.getParent()).getChildAt(2)).getText().toString();
                        MainActivity objMainActivity=(MainActivity)getContext();
                        objMainActivity.ShowConfigurarInspeccion(strIdEmpresa);
                    }
                }
        );

        return convertView;
    }
}
