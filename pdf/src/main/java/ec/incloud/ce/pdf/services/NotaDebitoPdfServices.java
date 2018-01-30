/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.pdf.services;

import ec.incloud.ce.bean.common.CampoAdicional;
import ec.incloud.ce.bean.common.Impuesto;
import ec.incloud.ce.bean.debito.Motivo;
import ec.incloud.ce.bean.debito.NotaDebito;
import ec.incloud.ce.pdf.util.ConvertNumberToLetter;
import ec.incloud.ce.pdf.util.FormatNumberUtil;
import ec.incloud.ce.pdf.util.ResourceUtil;
import ec.incloud.ce.pdf.util.TipoDocumentoEnum;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class NotaDebitoPdfServices implements PdfServices<NotaDebito> {

    private static PdfServices instance;
    private final Logger log = Logger.getLogger(PdfServices.class);

    public static PdfServices create() {
        synchronized (NotaDebitoPdfServices.class) {
            if (instance == null) {
                instance = new NotaDebitoPdfServices();
            }
            return instance;
        }
    }

    @Override
    public void generarPdf(NotaDebito comprobante, String pathAbsolute, String numeroAutorizacion, String fechaAutorizacion, String sociedad[], String [] documento, String porcentajeIvaDinamico) {
    	
        JasperReport jasperReport=null;
        String TMP_IREPORT = "reporte/-ruc--notaDebito.jasper";
        String pathReporte = TMP_IREPORT.replaceFirst("-ruc-", sociedad[0]);
        try {
            InputStream is = ResourceUtil.getResourceStream(this.getClass(), pathReporte);
            jasperReport = (JasperReport) JRLoader.loadObject(is);
        } catch (FileNotFoundException | JRException ex) {
            log.error("Error al cargar jasper report ", ex);
        }
    	
        Map<String, Object> param = new HashMap<>();

        param.put("urlSociedad", sociedad[1]==null?"":sociedad[1] );
        param.put("USUARIO", sociedad[3]==null?"":sociedad[3] );
        param.put("PASSWORD", sociedad[4]==null?"":sociedad[4] );
        
        param.put("ambiente", (comprobante.getInfoTributaria().getAmbiente().equals("1")) ? "PRUEBAS" : "PRODUCCIÓN");
        param.put("tipoEmision", (comprobante.getInfoTributaria().getTipoEmision().equals("1")) ? "NORMAL" : "CONTINGENCIA");
        param.put("razonSocial", comprobante.getInfoTributaria().getRazonSocial());
        param.put("nombreComercial", comprobante.getInfoTributaria().getNombreComercial());
        param.put("ruc", comprobante.getInfoTributaria().getRuc());
        param.put("claveAcceso", comprobante.getInfoTributaria().getClaveAcceso());
        param.put("direccionMatriz", comprobante.getInfoTributaria().getDirMatriz());
        param.put("numeroAutorizacion", numeroAutorizacion);
        param.put("fechaAutorizacion", fechaAutorizacion);
        param.put("fechaEmision", comprobante.getInfoNotaDebito().getFechaEmision());
        param.put("contribuyenteEspecial", comprobante.getInfoNotaDebito().getContribuyenteEspecial());
        param.put("textoRide", sociedad[2]==null?"":sociedad[2]);
        param.put("llevaContabilidad", comprobante.getInfoNotaDebito().getObligadoContabilidad());
        param.put("nombreCliente", comprobante.getInfoNotaDebito().getRazonSocialComprador());
        param.put("identificacionCliente", comprobante.getInfoNotaDebito().getIdentificacionComprador());
        param.put("serieCorrelativo", MessageFormat.format("{0}-{1}-{2}", comprobante.getInfoTributaria().getEstab(), comprobante.getInfoTributaria().getPtoEmi(), comprobante.getInfoTributaria().getSecuencial()));
        param.put("fechaEmisionDocumentoModificado", comprobante.getInfoNotaDebito().getFechaEmisionDocSustento());
        param.put("documentoModificado", TipoDocumentoEnum.getTipo(comprobante.getInfoNotaDebito().getCodDocModificado()).getDescripcion());
        param.put("numeroDocumentoModificado", comprobante.getInfoNotaDebito().getNumDocModificado());

        List<CampoAdicional> lstInfoAdicional = comprobante.getInfoAdicional();
        
        String obsDocumento = ( documento ==null || documento.length < 1 || documento[0].isEmpty() ) ? null:documento[0];// obsComprobante es un info adicional si tiene más de 300 caracteres
        
        if( obsDocumento!=null ){
        	lstInfoAdicional = ( lstInfoAdicional==null ) ? new ArrayList<CampoAdicional>() : lstInfoAdicional;
        	CampoAdicional campoAdicional = new CampoAdicional();
        	campoAdicional.setNombre("Observación");
        	campoAdicional.setValue(obsDocumento);
        	lstInfoAdicional.add(campoAdicional);
        }
        
        if ( lstInfoAdicional != null) {
            List adicional = new ArrayList();
            Map<String, String> row;
            for (CampoAdicional ac : lstInfoAdicional ) {
                row = new HashMap<>();
                row.put("valor", ac.getValue());
                row.put("nombre", ac.getNombre());
                adicional.add(row);
            }
            param.put("campoAdicional", adicional);
        }

        List<Map<String, Object>> detalle = new ArrayList<>();
        BigDecimal[] impuesto = {new BigDecimal("0.00"), new BigDecimal("0.00"), new BigDecimal("0.00"), new BigDecimal("0.00")};
        Map<String, Object> row;

        if (comprobante.getMotivos() != null) {
            for (Motivo mo : comprobante.getMotivos()) {
                row = new HashMap();
                row.put("razonModificacion", mo.getRazon());
                row.put("valorModificacion", mo.getValor());
                detalle.add(row);
            }
        }

        if (comprobante.getInfoNotaDebito().getImpuestos() != null) {
            for (Impuesto x : comprobante.getInfoNotaDebito().getImpuestos()) {
                BigDecimal baseImponible = new BigDecimal(x.getBaseImponible());

                if (x.getCodigo().equalsIgnoreCase("2")) {
                    if (x.getCodigoPorcentaje().equalsIgnoreCase("0")) {
                        impuesto[0] = impuesto[0].add(baseImponible);
                    } else if (x.getCodigoPorcentaje().equalsIgnoreCase("2") || x.getCodigoPorcentaje().equalsIgnoreCase("3")) {
                        impuesto[1] = impuesto[1].add(baseImponible);
                    } else if (x.getCodigoPorcentaje().equalsIgnoreCase("6")) {
                        impuesto[2] = impuesto[2].add(baseImponible);
                    }
                } else if (x.getCodigo().equalsIgnoreCase("3")) {
                    impuesto[3] = impuesto[3].add(baseImponible.multiply(new BigDecimal(x.getTarifa())));
                }
            }
        }
        
        String valorIva =""+ Double.parseDouble(porcentajeIvaDinamico) / 100.00;

        param.put("iva0", FormatNumberUtil.formatMilDecimal(impuesto[0] + ""));
        param.put("iva12", FormatNumberUtil.formatMilDecimal(impuesto[1] + ""));
        param.put("labelIva", porcentajeIvaDinamico);
        param.put("noObjetoIva", FormatNumberUtil.formatMilDecimal(impuesto[2] + ""));
        param.put("ice", FormatNumberUtil.formatMilDecimal(impuesto[3].setScale(2, RoundingMode.HALF_UP) + ""));
        param.put("iva", FormatNumberUtil.formatMilDecimal(impuesto[1].add(impuesto[3]).multiply(new BigDecimal(valorIva)).setScale(2, RoundingMode.HALF_UP) + ""));
        param.put("valorTotal", FormatNumberUtil.formatMilDecimal(comprobante.getInfoNotaDebito().getValorTotal() + ""));
        param.put("subTotal", FormatNumberUtil.formatMilDecimal(comprobante.getInfoNotaDebito().getTotalSinImpuestos() + ""));
        param.put("montoLetras", (new ConvertNumberToLetter()).Convertir(comprobante.getInfoNotaDebito().getValorTotal(), true, "DÓLARES AMERICANOS"));

        JasperPrint jasperPrint;
        try {
            JRDataSource dataSource = new JRBeanCollectionDataSource(detalle);
            jasperPrint = JasperFillManager.fillReport(jasperReport, param, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, pathAbsolute);
        } catch (Exception ex) {
            log.error("Error al generar PDF", ex);
        }
    }
}
