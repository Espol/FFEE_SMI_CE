/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.pdf.services;

import ec.incloud.ce.bean.common.CampoAdicional;
import ec.incloud.ce.bean.common.Impuesto;
import ec.incloud.ce.bean.factura.Factura;
import ec.incloud.ce.bean.factura.FacturaDetalle;
import ec.incloud.ce.pdf.util.ConvertNumberToLetter;
import ec.incloud.ce.pdf.util.FormatNumberUtil;
import ec.incloud.ce.pdf.util.ResourceUtil;
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
class FacturaPdfServices implements PdfServices<Factura> {

    private static PdfServices instance;
    private final Logger log = Logger.getLogger(PdfServices.class);

    public static PdfServices create() {
        synchronized (FacturaPdfServices.class) {
            if (instance == null) {
                instance = new FacturaPdfServices();
            }
            return instance;
        }
    }

    @Override
    public void generarPdf(Factura comprobante, String pathAbsolute, String numeroAutorizacion, String fechaAutorizacion, String [] sociedad, String [] documento, String porcentajeIvaDinamico) {
    	
        JasperReport jasperReport=null;
        String TMP_IREPORT = "reporte/-ruc--factura.jasper";
        try {
            String pathReporte = TMP_IREPORT.replaceFirst("-ruc-", sociedad[0]);
            
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
        param.put("fechaEmision", comprobante.getInfoFactura().getFechaEmision());
        param.put("contribuyenteEspecial", comprobante.getInfoFactura().getContribuyenteEspecial());
        param.put("textoRide", sociedad[2]==null?"":sociedad[2]);
        param.put("llevaContabilidad", comprobante.getInfoFactura().getObligadoContabilidad());
        param.put("guiaRemision", comprobante.getInfoFactura().getGuiaRemision());
        param.put("nombreCliente", comprobante.getInfoFactura().getRazonSocialComprador());
        param.put("identificacionCliente", comprobante.getInfoFactura().getIdentificacionComprador());
        param.put("totalDescuento", comprobante.getInfoFactura().getTotalDescuento());
        param.put("serieCorrelativo", MessageFormat.format("{0}-{1}-{2}", comprobante.getInfoTributaria().getEstab(), comprobante.getInfoTributaria().getPtoEmi(), comprobante.getInfoTributaria().getSecuencial()));
        
        param.put("direccionComprador", comprobante.getInfoFactura().getDireccionComprador() );
        List<CampoAdicional> lstInfoAdicional = comprobante.getInfoAdicional();
        
        String obsDocumento = ( documento ==null || documento.length < 1 || documento[0].isEmpty() ) ? null:documento[0];// obsComprobante es un info adicional si tiene más de 300 caracteres
        
        if( obsDocumento!=null ){
        	lstInfoAdicional = ( lstInfoAdicional==null ) ? new ArrayList<CampoAdicional>() : lstInfoAdicional;
        	CampoAdicional campoAdicional = new CampoAdicional();
        	campoAdicional.setNombre("Observación");
        	campoAdicional.setValue(obsDocumento);
        	lstInfoAdicional.add(campoAdicional);
        }
        
        if ( lstInfoAdicional != null ) {
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
        BigDecimal[] impuesto = {new BigDecimal("0.00"), new BigDecimal("0.00"), new BigDecimal("0.00"), new BigDecimal("0.00")};
        Map<String, Object> row;

        for (FacturaDetalle fd : comprobante.getDetalles()) {
            row = new HashMap();
            row.put("codigoPrincipal", fd.getCodigoPrincipal());
            row.put("codigoAuxiliar", fd.getCodigoAuxiliar());
            row.put("cantidad", FormatNumberUtil.formatMilDecimal(fd.getCantidad()));
            row.put("descripcion", fd.getDescripcion());
            row.put("precioUnitario", FormatNumberUtil.formatMilDecimal(fd.getPrecioUnitario()));
            row.put("precioTotalSinImpuesto", FormatNumberUtil.formatMilDecimal(fd.getPrecioTotalSinImpuesto()));
            
            if (fd.getDetallesAdicionales() != null) {
                for (int i = 0; i < fd.getDetallesAdicionales().size(); i++) {
                    switch (i) {
                        case 0:
                            row.put("detalle1", fd.getDetallesAdicionales().get(i).getValor());
                            break;
                        case 1:
                            row.put("detalle2", fd.getDetallesAdicionales().get(i).getValor());
                            break;
                        case 2:
                            row.put("detalle3", fd.getDetallesAdicionales().get(i).getValor());
                            break;
                    }
                }
            }

            row.put("descuento", FormatNumberUtil.formatMilDecimal(fd.getDescuento()));
            row.put("subtotal", FormatNumberUtil.formatMilDecimal(new BigDecimal(fd.getPrecioUnitario()).multiply(new BigDecimal(fd.getCantidad())).setScale(2, RoundingMode.HALF_UP) + ""));
            
            detalle.add(row);
            for (Impuesto i : fd.getImpuestos()) {
                if (i.getCodigo().equals("2")) {
                    switch (i.getCodigoPorcentaje()) {
                        case "0":
                            impuesto[0] = impuesto[0].add(new BigDecimal(fd.getPrecioTotalSinImpuesto()));
                            break;
                        case "2":
                            impuesto[1] = impuesto[1].add(new BigDecimal(fd.getPrecioTotalSinImpuesto()));
                            break;
                        case "3":
                            impuesto[1] = impuesto[1].add(new BigDecimal(fd.getPrecioTotalSinImpuesto()));
                            break;
                        case "6":
                            impuesto[2] = impuesto[2].add(new BigDecimal(fd.getPrecioTotalSinImpuesto()));
                            break;
                    }
                }
                if (i.getCodigo().equalsIgnoreCase("3")) {
                    impuesto[3] = impuesto[3].add(new BigDecimal(fd.getPrecioTotalSinImpuesto()))
                            .multiply(new BigDecimal(i.getTarifa()));
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
        param.put("valorTotal", FormatNumberUtil.formatMilDecimal(comprobante.getInfoFactura().getImporteTotal() + ""));
        param.put("subTotal", FormatNumberUtil.formatMilDecimal(comprobante.getInfoFactura().getTotalSinImpuestos() + ""));
        param.put("propina", FormatNumberUtil.formatMilDecimal(comprobante.getInfoFactura().getPropina() + ""));
        param.put("marcaAgua", null);
        param.put("montoLetras", (new ConvertNumberToLetter()).Convertir(comprobante.getInfoFactura().getImporteTotal(), true, "DÓLARES AMERICANOS"));
        
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
