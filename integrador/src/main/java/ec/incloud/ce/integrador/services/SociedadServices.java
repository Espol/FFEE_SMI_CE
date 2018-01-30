/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.services;

import java.util.List;

import ec.incloud.ce.integrador.bean.Sociedad;
import ec.incloud.ce.integrador.exception.IntegradorException;

/**
 *
 * @author Incloud Services S.A.C.
 */
public interface SociedadServices {
	
    public void updateEstadoProcesoFirma(Sociedad sociedad, int estadoProcesoCaducidad );
    public boolean notificaFirmaCaducada(Sociedad sociedad, String fechaCaducidadFirma);
	
    public List<Sociedad> getSociedadesByFirmaCaducada();
    public Sociedad getSociedadByFirmaCaducada(Sociedad sociedad);
    public Sociedad getSociedad(String ruc) throws IntegradorException;
    
}
