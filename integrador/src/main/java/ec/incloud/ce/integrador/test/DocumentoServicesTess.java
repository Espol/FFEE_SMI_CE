/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.test;

import java.math.BigDecimal;
import java.util.Date;

import ec.incloud.ce.integrador.bean.Documento;
import ec.incloud.ce.integrador.bean.Sociedad;
import ec.incloud.ce.integrador.exception.IntegradorException;
import ec.incloud.ce.integrador.services.ServicesFactory;
import ec.incloud.ce.integrador.util.Util;

/**
 *
 * @author Ivan
 */
public class DocumentoServicesTess {

    public static void main(String[] args) {
        try {
            Sociedad sociedad = ServicesFactory.getFactory().createSociedadServices().getSociedad("0990129185001");
            System.out.println(sociedad.getRazonSocial());
        } catch (IntegradorException e) {
            System.out.println("" + e);
        }
        System.out.println("" + Util.INSTANCE.getDateFromString("13/07/2015"));
    }

    public static void guardar() {
        Sociedad soc = new Sociedad();
        soc.setRuc("0990129185001");
        soc.setIdSociedad(1);

        Documento doc = new Documento();
        doc.setSociedad(soc);
        doc.setTipoDocumento("01");
        doc.setEstablecimiento("123");
        doc.setPuntoEmision("788");
        doc.setNumero("F00157129");
        doc.setFechaEmision(new Date());
        doc.setFechaReferencia("2015-09-02");
        doc.setCodigoCliente("9878974987");
        doc.setClaveAcceso("465465654465465654465465654465465654AAAAAAAAAAAAA");
        doc.setXml("C/");
        doc.setPdf("C/");
        doc.setNumeroSap("98798");
        doc.setUsuarioSap("44");
        doc.setTerminal("5kj4");
        doc.setMailDestino("admin@dominio.com");
        doc.setImporteTotal(new BigDecimal("654654.522"));

        try {
            ServicesFactory.getFactory().createDocumentoServices().guardar(doc);
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }

    public static Documento get(String ruc, String tipo, String establecimiento, String punto, String numero) {
        return ServicesFactory.getFactory().createDocumentoServices().getDocumento(ruc, tipo, establecimiento, punto, numero);
    }

}
