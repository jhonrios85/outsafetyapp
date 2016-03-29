package co.com.appsource.outsafetyapp.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import co.com.appsource.outsafetyapp.MainActivity;
import co.com.appsource.outsafetyapp.R;
import co.com.appsource.outsafetyapp.db_helper.Tables.CentroTrabajo;

/**
 * Created by JANUS on 05/03/2016.
 */
public class CentroTrabajoButtonAdapter extends ArrayAdapter<CentroTrabajo> {

    Context context;
    int layoutId;
    private List<CentroTrabajo> mData;


    public CentroTrabajoButtonAdapter(Context context, int resourceId,
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
        Button btnCentroTrabajo;
        TextView txv_lb_id_centro_trabajo;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        CentroTrabajo rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_centro_trabajo_button, null);
            holder = new ViewHolder();
            holder.btnCentroTrabajo = (Button) convertView.findViewById(R.id.btnCentroTrabajo);
            holder.txv_lb_id_centro_trabajo = (TextView) convertView.findViewById(R.id.txv_lb_id_centro_trabajo);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();


        holder.btnCentroTrabajo.setText(rowItem.getStrRazonSocial());
        holder.txv_lb_id_centro_trabajo.setText(rowItem.getIntIdEmpresa());

        holder.btnCentroTrabajo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        String strIdEmpresa = ((android.support.v7.widget.AppCompatTextView) ((android.widget.LinearLayout) arg0.getParent()).getChildAt(1)).getText().toString();
                        MainActivity objMainActivity = (MainActivity) getContext();
                        objMainActivity.ShowConfigurarInspeccion(strIdEmpresa);
                    }
                }
        );

        return convertView;
    }
}
