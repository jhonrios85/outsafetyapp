package co.com.appsource.outsafetyapp.complete;

import java.util.List;

import co.com.appsource.outsafetyapp.db_helper.Tables.Empresa;

/**
 * Created by Administrador on 30/03/2016.
 */
public interface EmpresaComplete {
    public void onTaskComplete(List<Empresa> result);
}
