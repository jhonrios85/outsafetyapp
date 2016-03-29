package co.com.appsource.outsafetyapp.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import co.com.appsource.outsafetyapp.MainActivity;
import co.com.appsource.outsafetyapp.R;
import co.com.appsource.outsafetyapp.db_helper.Tables.CentroTrabajo;

/**
 * Created by JANUS on 15/01/2016.
 */
public class CentroTrabajoSyncAdapter extends ArrayAdapter<CentroTrabajo> {

    Context context;
    int layoutId;
    private List<CentroTrabajo> mData;


    public CentroTrabajoSyncAdapter(Context context, int resourceId,
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
        TextView txv_lb_nom_centro_trabajo_sync;
        TextView txv_lb_id_centro_trabajo_sync;
        CheckBox checkBoxCentroTrabajoSync;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        CentroTrabajo rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_centro_trabajo_sync, null);
            holder = new ViewHolder();
            holder.txv_lb_nom_centro_trabajo_sync = (TextView) convertView.findViewById(R.id.txv_lb_nom_centro_trabajo_sync);
            holder.checkBoxCentroTrabajoSync = (CheckBox) convertView.findViewById(R.id.checkBoxCentroTrabajoSync);
            holder.txv_lb_id_centro_trabajo_sync = (TextView) convertView.findViewById(R.id.txv_lb_id_centro_trabajo_sync);
            //holder.txv_lb_id_centro_trabajo = (TextView) convertView.findViewById(R.id.txv_lb_id_centro_trabajo);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();


        holder.txv_lb_nom_centro_trabajo_sync.setText(rowItem.getStrRazonSocial());
        holder.txv_lb_id_centro_trabajo_sync.setText(rowItem.getIntIdEmpresa());

        holder.checkBoxCentroTrabajoSync.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = (int) buttonView.getTag();
                mData.get(pos).setBoolSelectedCt(buttonView.isChecked());
            }
        });

        holder.checkBoxCentroTrabajoSync.setTag(position);
        holder.checkBoxCentroTrabajoSync.setChecked(rowItem.isBoolSelectedCt());

/*        holder.checkBoxCentroTrabajo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        String strIdEmpresa = ((android.support.v7.widget.AppCompatTextView) ((android.widget.LinearLayout) arg0.getParent()).getChildAt(2)).getText().toString();
                        MainActivity objMainActivity = (MainActivity) getContext();
                        objMainActivity.ShowConfigurarInspeccion(strIdEmpresa);
                    }
                }
        );*/

        return convertView;
    }
}
