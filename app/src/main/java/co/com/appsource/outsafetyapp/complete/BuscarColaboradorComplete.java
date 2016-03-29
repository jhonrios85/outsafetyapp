package co.com.appsource.outsafetyapp.complete;

import java.util.List;

import co.com.appsource.outsafetyapp.db_helper.Tables.Persona;

/**
 * Created by JANUS on 07/12/2015.
 */
public interface BuscarColaboradorComplete {
    public void onTaskComplete(List<Persona> result);
}
