package co.com.appsource.outsafetyapp.model;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import co.com.appsource.outsafetyapp.R;
import co.com.appsource.outsafetyapp.db_helper.Tables.Empresa;
import co.com.appsource.outsafetyapp.db_helper.Tables.EmpresaDataSource;

/**
 * Created by Administrador on 30/03/2016.
 */
public class EmpresaAdapter extends CursorAdapter implements android.widget.AdapterView.OnItemClickListener {

    private EmpresaDataSource mEmpresaDataSource;

    public EmpresaAdapter(EmpresaDataSource objEmpresaDataSource, Context currentContext) {
        // Call the CursorAdapter constructor with a null Cursor.
        super(currentContext, null);
        mEmpresaDataSource = objEmpresaDataSource;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.item_empresa, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final int itemColumnIndex = cursor.getColumnIndexOrThrow("intIdEmpresa");
        final int descColumnIndex = cursor.getColumnIndexOrThrow("strRazonSocial");

        String intIdEmpresa = cursor.getString(itemColumnIndex);
        String strRazonSocial = cursor.getString(descColumnIndex);

        TextView text1 = (TextView) view.findViewById(R.id.txtIdEmpresa);
        text1.setText(cursor.getString(itemColumnIndex));
        TextView text2 = (TextView) view.findViewById(R.id.txtRazonSocialEmpresa);
        text2.setText(cursor.getString(descColumnIndex));

    }

    @Override
    public CharSequence convertToString(Cursor cursor) {
        final int columnIndex = cursor.getColumnIndexOrThrow("strRazonSocial");
        final String str = cursor.getString(columnIndex);
        return str;
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {

        mEmpresaDataSource = new EmpresaDataSource(mContext);
        mEmpresaDataSource.open();

        Cursor objEmpresa = mEmpresaDataSource.GetEmpresaByNameCursor(
                (constraint != null ? constraint.toString() : "@@@@"));
        mEmpresaDataSource.close();
        return objEmpresa;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Get the cursor, positioned to the corresponding row in the result set
        Cursor cursor = (Cursor) parent.getItemAtPosition(position);

        // Get the Item Number from this row in the database.
        String itemNumber = cursor.getString(cursor.getColumnIndexOrThrow("intIdEmpresa"));

    }
}
