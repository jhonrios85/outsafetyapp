package co.com.appsource.outsafetyapp.util;

/**
 * Created by JANUS on 04/03/2016.
 */
public class GuardarColaboradoresResult {
    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public long getIntIdInspeccion() {
        return intIdInspeccion;
    }

    public void setIntIdInspeccion(long intIdInspeccion) {
        this.intIdInspeccion = intIdInspeccion;
    }

    long intIdInspeccion;

    public long getIntIdInspeccionLocal() {
        return intIdInspeccionLocal;
    }

    public void setIntIdInspeccionLocal(long intIdInspeccionLocal) {
        this.intIdInspeccionLocal = intIdInspeccionLocal;
    }

    long intIdInspeccionLocal;

    boolean exito;
}
