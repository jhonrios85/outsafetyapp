package co.com.appsource.outsafetyapp.custom_objects;

import java.util.List;

import co.com.appsource.outsafetyapp.db_helper.Tables.Area;
import co.com.appsource.outsafetyapp.db_helper.Tables.Habilidad;
import co.com.appsource.outsafetyapp.db_helper.Tables.Inspeccion;
import co.com.appsource.outsafetyapp.db_helper.Tables.Parametro;
import co.com.appsource.outsafetyapp.db_helper.Tables.Persona;
import co.com.appsource.outsafetyapp.db_helper.Tables.Riesgo;

/**
 * Created by JANUS on 26/11/2015.
 */
public class InputConfigurarInspeccion {

    List<Area> lstArea;
    List<Riesgo> lstRiesgo;
    List<Inspeccion> lstInspeccion;
    List<Persona> lstPersonas;
    List<Parametro> lstParametro;
    List<Habilidad> lstHabilidad;
    String intIdEmpresa;

    public List<Habilidad> getLstHabilidad() {
        return lstHabilidad;
    }

    public void setLstHabilidad(List<Habilidad> lstHabilidad) {
        this.lstHabilidad = lstHabilidad;
    }



    public List<Parametro> getLstParametro() {
        return lstParametro;
    }

    public void setLstParametro(List<Parametro> lstParametro) {
        this.lstParametro = lstParametro;
    }


    public String getIntIdEmpresa() {
        return intIdEmpresa;
    }

    public void setIntIdEmpresa(String intIdEmpresa) {
        this.intIdEmpresa = intIdEmpresa;
    }

    public List<Persona> getLstPersonas() {
        return lstPersonas;
    }

    public void setLstPersonas(List<Persona> lstPersonas) {
        this.lstPersonas = lstPersonas;
    }

    public List<Inspeccion> getLstInspeccion() {
        return lstInspeccion;
    }

    public void setLstInspeccion(List<Inspeccion> lstInspeccion) {
        this.lstInspeccion = lstInspeccion;
    }

    public List<Riesgo> getLstRiesgo() {
        return lstRiesgo;
    }

    public void setLstRiesgo(List<Riesgo> lstRiesgo) {
        this.lstRiesgo = lstRiesgo;
    }

    public List<Area> getLstArea() {
        return lstArea;
    }

    public void setLstArea(List<Area> lstArea) {
        this.lstArea = lstArea;
    }
}
