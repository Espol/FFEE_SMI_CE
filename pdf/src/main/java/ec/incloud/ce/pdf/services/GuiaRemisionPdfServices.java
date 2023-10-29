/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.pdf.services;

import ec.incloud.ce.bean.common.CampoAdicional;
import ec.incloud.ce.bean.common.DetAdicional;
import ec.incloud.ce.bean.guia.Destinatario;
import ec.incloud.ce.bean.guia.GuiaRemision;
import ec.incloud.ce.bean.guia.GuiaRemisionDetalle;
import ec.incloud.ce.pdf.util.FormatNumberUtil;
import ec.incloud.ce.pdf.util.ResourceUtil;
import ec.incloud.ce.pdf.util.TipoDocumentoEnum;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
public class GuiaRemisionPdfServices implements PdfServices<GuiaRemision> {

    private static PdfServices instance;
    private final Logger log = Logger.getLogger(PdfServices.class);

    public static PdfServices create() {
        synchronized (GuiaRemisionPdfServices.class) {
            if (instance == null) {
                instance = new GuiaRemisionPdfServices();
            }
            return instance;
        }
    }
    
    @Override
    public void generarPdf(GuiaRemision comprobante, String pathAbsolute, String numeroAutorizacion, String fechaAutorizacion, String [] sociedad, String [] documento, String porcentajeIvaDinamico) {
    	
        JasperReport jasperReport=null;
        String TMP_IREPORT = "reporte/-ruc--guiaRemision.jasper";
        try {
            String pathReporte = TMP_IREPORT.replaceFirst("-ruc-", sociedad[0]);
            InputStream is = ResourceUtil.getResourceStream(this.getClass(), pathReporte);
            jasperReport = (JasperReport) JRLoader.loadObject(is);
        } catch (FileNotFoundException | JRException ex) {
            log.error("Error al cargar jasper report ", ex);
        }
        
        String texto_ride = "";
        List<CampoAdicional> lstInfoAdicional = comprobante.getInfoAdicional();
        /************ TEXTO RIDE DESDE SAP *******************/
        if ( lstInfoAdicional != null ) {
            for (CampoAdicional ac : lstInfoAdicional) {
            	if(ac.getNombre().equalsIgnoreCase("TEXTO_RIDE_SAP")) {
            		texto_ride = ac.getValue();
            	}
            }
        }
        /************ TEXTO RIDE DESDE SAP *******************/
        
        Map<String, Object> param = new HashMap<String, Object>();
        
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
        param.put("fechaEmision", comprobante.getInfoGuiaRemision().getFechaIniTransporte());
        param.put("contribuyenteEspecial", comprobante.getInfoGuiaRemision().getContribuyenteEspecial());
        param.put("textoRide", texto_ride.equals("") ? (sociedad[2]==null?"":sociedad[2]) : texto_ride);
        param.put("llevaContabilidad", comprobante.getInfoGuiaRemision().getObligadoContabilidad());
        param.put("serieCorrelativo", MessageFormat.format("{0}-{1}-{2}", comprobante.getInfoTributaria().getEstab(), comprobante.getInfoTributaria().getPtoEmi(), comprobante.getInfoTributaria().getSecuencial()));
        param.put("rucTransportista", comprobante.getInfoGuiaRemision().getRucTransportista());
        param.put("razonSocialTransportista", comprobante.getInfoGuiaRemision().getRazonSocialTransportista());
        param.put("puntoPartida", comprobante.getInfoGuiaRemision().getDirPartida());
        param.put("fechaInicioTransporte", comprobante.getInfoGuiaRemision().getFechaIniTransporte());
        param.put("placa", comprobante.getInfoGuiaRemision().getPlaca());
        param.put("fechaFinTransporte", comprobante.getInfoGuiaRemision().getFechaFinTransporte());
        
        String obsDocumento = ( documento == null || documento[0].isEmpty() ) ? null:documento[0];// obsComprobante es un info adicional si tiene más de 300 caracteres
        
        if( obsDocumento!=null ){
        	lstInfoAdicional = ( lstInfoAdicional==null ) ? new ArrayList<CampoAdicional>() : lstInfoAdicional;
        	CampoAdicional campoAdicional = new CampoAdicional();
        	campoAdicional.setNombre("Observación");
        	campoAdicional.setValue(obsDocumento);
        	lstInfoAdicional.add(campoAdicional);
        }
        
        if ( lstInfoAdicional != null) {
            List<Map> adicional = new ArrayList<Map>();
            Map<String, String> row;
            for (CampoAdicional ac : lstInfoAdicional) {
                row = new HashMap<>();
                if( !ac.getNombre().equalsIgnoreCase("TEXTO_RIDE_SAP") ) {
                	row.put("valor", ac.getValue());
                    row.put("nombre", ac.getNombre());
                    adicional.add(row);
                }
            }
            param.put("campoAdicional", adicional);
        }

        List<Map<String, Object>> detalle = new ArrayList<>();
        Map<String, Object> row_;

        if (comprobante.getDestinatarios() != null) {
            for (Destinatario des : comprobante.getDestinatarios()) {
                row_ = new HashMap<String, Object>();
                row_.put("nombreComprobante", des.getCodDocSustento() == null ? "" : TipoDocumentoEnum.getTipo(des.getCodDocSustento()).getDescripcion());
                row_.put("numDocSustento", des.getNumDocSustento());
                row_.put("numeroAutorizacion", des.getNumAutDocSustento());
                row_.put("fechaEmisionSustento", des.getFechaEmisionDocSustento());
                row_.put("motivoTraslado", des.getMotivoTraslado());
                row_.put("docAduanero", des.getDocAduaneroUnico());
                row_.put("ruta", des.getRuta());
                row_.put("destino", des.getDirDestinatario());
                row_.put("rucDestinatario", des.getIdentificacionDestinatario());
                row_.put("razonSocial", des.getRazonSocialDestinatario());
                row_.put("docAduanero", des.getDocAduaneroUnico());
                row_.put("codigoEstab", des.getCodEstabDestino());
                row_.put("ruta", des.getRuta());

                List<Map<String, String>> detalles = new ArrayList<>();
                Map<String, String> row;
                for (GuiaRemisionDetalle det : des.getDetalles()) {
                    row = new HashMap<String, String>();
                    row.put("cantidad", FormatNumberUtil.formatMilDecimal(det.getCantidad()));
                    row.put("codigoPrincipal", det.getCodigoInterno());
                    row.put("codigoAuxiliar", det.getCodigoAdicional());
                    row.put("descripcion", det.getDescripcion());
                    for (int i = 0; i < det.getDetallesAdicionales().size(); i++) {
                        DetAdicional item = det.getDetallesAdicionales().get(i);
                        if (i == 0) {
                            row.put("detalle1", item.getValor());
                        } else if (i == 1) {
                            row.put("detalle2", item.getValor());
                        } else if (i == 2) {
                            row.put("detalle3", item.getValor());
                        }
                    }
                    detalles.add(row);
                }
                row_.put("detalles", detalles);
                detalle.add(row_);
            }
        }
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
