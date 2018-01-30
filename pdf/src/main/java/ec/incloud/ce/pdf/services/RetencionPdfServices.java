/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.pdf.services;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ec.incloud.ce.bean.common.CampoAdicional;
import ec.incloud.ce.bean.retencion.ComprobanteRetencion;
import ec.incloud.ce.bean.retencion.ImpuestoRetenido;
import ec.incloud.ce.pdf.util.ConvertNumberToLetter;
import ec.incloud.ce.pdf.util.FormatNumberUtil;
import ec.incloud.ce.pdf.util.ResourceUtil;
import ec.incloud.ce.pdf.util.TipoDocumentoRetencionEnum;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class RetencionPdfServices implements PdfServices<ComprobanteRetencion> {

    private static PdfServices instance;
    private final Logger log = Logger.getLogger(PdfServices.class);
    
    public static PdfServices create() {
        synchronized (RetencionPdfServices.class) {
            if (instance == null) {
                instance = new RetencionPdfServices();
            }
            return instance;
        }
    }

    @Override
    public void generarPdf(ComprobanteRetencion comprobante, String pathAbsolute, String numeroAutorizacion, String fechaAutorizacion, String [] sociedad, String [] documento, String porcentajeIvaDinamico) {
        
        JasperReport jasperReport=null;
    	String TMP_IREPORT = "reporte/-ruc--comprobanteRetencion.jasper";
    	String ruc = sociedad[0];
        try {
            String pathReporte = TMP_IREPORT.replaceFirst("-ruc-", ruc);
            InputStream is = ResourceUtil.getResourceStream(this.getClass(), pathReporte);
            jasperReport = (JasperReport) JRLoader.loadObject(is);
        } catch (FileNotFoundException | JRException ex) {
            log.error("Error al cargar jasper report ", ex);
        }
    	
    	Map<String, Object> param = new HashMap();
    	
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
        param.put("fechaEmision", comprobante.getInfoCompRetencion().getFechaEmision());
        param.put("contribuyenteEspecial", comprobante.getInfoCompRetencion().getContribuyenteEspecial());
        param.put("textoRide", sociedad[2]==null?"":sociedad[2]);
        param.put("llevaContabilidad", comprobante.getInfoCompRetencion().getObligadoContabilidad());
        param.put("nombreCliente", comprobante.getInfoCompRetencion().getRazonSocialSujetoRetenido());
        param.put("identificacionCliente", comprobante.getInfoCompRetencion().getIdentificacionSujetoRetenido());
        param.put("serieCorrelativo", MessageFormat.format("{0}-{1}-{2}", comprobante.getInfoTributaria().getEstab(), comprobante.getInfoTributaria().getPtoEmi(), comprobante.getInfoTributaria().getSecuencial()));

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
            for (CampoAdicional ac : lstInfoAdicional) {
                row = new HashMap<>();
                row.put("valor", ac.getValue());
                row.put("nombre", ac.getNombre());
                adicional.add(row);
            }
            param.put("campoAdicional", adicional);
        }

        List<Map<String, Object>> detalle = new ArrayList<>();
        BigDecimal valorTotal = new BigDecimal("0.00");
        Map<String, Object> row;

        if (comprobante.getImpuestos() != null) {
            String periodo = comprobante.getInfoCompRetencion().getPeriodoFiscal();

            for (ImpuestoRetenido ir : comprobante.getImpuestos()) {
            	
            	if(ir.getCodDocSustento()==null || ir.getCodDocSustento().isEmpty() )
            		log.error("error_usuario_1:campo:codDocSustento, del impuesto retenido es nulo");
            	
                row = new HashMap();
                //TipoDocumentoRetencionEnum sólo tiene tipo doc 01, 03 y 05. A pedido de la gente SAP, y han asegurado q no existe otro escenario
                row.put("nombreComprobante", TipoDocumentoRetencionEnum.getTipo(ir.getCodDocSustento()).getDescripcion());
                row.put("baseImponible", ir.getBaseImponible());
                row.put("porcentajeRetener", ir.getPorcentajeRetener() + "%");
                row.put("numeroComprobante", ir.getNumDocSustento());
                row.put("fechaEmisionCcompModificado", ir.getFechaEmisionDocSustento());
                row.put("valorRetenido", FormatNumberUtil.formatMilDecimal(ir.getValorRetenido()));
                row.put("codigoRetencion", ir.getCodigoRetencion() );
                
                String nomImpuesto;
                if(ruc.equals("0990007241001")){//si el ruc es de ecuasal, aplica el sgte cálculo
                	if( ir.getCodigo().equals("1") ){
                		nomImpuesto = "RENTA";
                	}else if( ir.getCodigo().equals("2") ){
                		nomImpuesto = "IVA";
                	}else if( ir.getCodigo().equals("6") ){
                		nomImpuesto = "ISD";
                	}else{
                		nomImpuesto = "Sin código";
                	}
                }else{
                	nomImpuesto = "Impuesto código: " + ir.getCodigo();
                }
                
                row.put("nombreImpuesto", nomImpuesto );
                row.put("ejercicioFiscal", periodo);
                valorTotal = valorTotal.add(new BigDecimal(ir.getValorRetenido()));
                detalle.add(row);
            }
        }
        
        param.put("ejercicioFiscal", comprobante.getInfoCompRetencion().getPeriodoFiscal());
        param.put("valorTotal", FormatNumberUtil.formatMilDecimal(valorTotal + ""));
        param.put("montoLetras", (new ConvertNumberToLetter()).Convertir(valorTotal + "", true, "DÓLARES AMERICANOS"));

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
