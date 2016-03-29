package co.com.appsource.outsafetyapp.custom_objects;

import java.util.HashSet;
import java.util.List;

import co.com.appsource.outsafetyapp.db_helper.Tables.Parametro;
import co.com.appsource.outsafetyapp.db_helper.Tables.Persona;

/**
 * Created by JANUS on 12/12/2015.
 */
public class InspeccionCompleta {

    String strUsuario;
    int intIdCentroTrabajo;
    int intIdRiesgo;
    int intIdInspeccion;
    String strObservacionGeneralInspeccion;
    int intIdArea;
    List<Persona> lstColaboradoresInspeccionados;
    HashSet<String> setEvidencias;
    List<Parametro> lstParametrosInspeccion;

    public String getStrUsuario() {
        return strUsuario;
    }

    public void setStrUsuario(String strUsuario) {
        this.strUsuario = strUsuario;
    }

    public int getIntIdCentroTrabajo() {
        return intIdCentroTrabajo;
    }

    public void setIntIdCentroTrabajo(int intIdCentroTrabajo) {
        this.intIdCentroTrabajo = intIdCentroTrabajo;
    }

    public int getIntIdRiesgo() {
        return intIdRiesgo;
    }

    public void setIntIdRiesgo(int intIdRiesgo) {
        this.intIdRiesgo = intIdRiesgo;
    }

    public int getIntIdInspeccion() {
        return intIdInspeccion;
    }

    public void setIntIdInspeccion(int intIdInspeccion) {
        this.intIdInspeccion = intIdInspeccion;
    }

    public String getStrObservacionGeneralInspeccion() {
        return strObservacionGeneralInspeccion;
    }

    public void setStrObservacionGeneralInspeccion(String strObservacionGeneralInspeccion) {
        this.strObservacionGeneralInspeccion = strObservacionGeneralInspeccion;
    }

    public int getIntIdArea() {
        return intIdArea;
    }

    public void setIntIdArea(int intIdArea) {
        this.intIdArea = intIdArea;
    }

    public List<Persona> getLstColaboradoresInspeccionados() {
        return lstColaboradoresInspeccionados;
    }

    public void setLstColaboradoresInspeccionados(List<Persona> lstColaboradoresInspeccionados) {
        this.lstColaboradoresInspeccionados = lstColaboradoresInspeccionados;
    }

    public HashSet<String> getSetEvidencias() {
        return setEvidencias;
    }

    public void setSetEvidencias(HashSet<String> setEvidencias) {
        this.setEvidencias = setEvidencias;
    }

    public List<Parametro> getLstParametrosInspeccion() {
        return lstParametrosInspeccion;
    }

    public void setLstParametrosInspeccion(List<Parametro> lstParametrosInspeccion) {
        this.lstParametrosInspeccion = lstParametrosInspeccion;
    }

}
