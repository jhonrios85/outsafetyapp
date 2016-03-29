package co.com.appsource.outsafetyapp.complete;

import java.util.List;

import co.com.appsource.outsafetyapp.db_helper.Tables.CentroTrabajo;

/**
 * Created by JANUS on 25/11/2015.
 */
public interface CentroTrabajoComplete {
    public void onTaskComplete(List<CentroTrabajo> result);
}
