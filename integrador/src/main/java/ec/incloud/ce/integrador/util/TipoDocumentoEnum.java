/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.util;

/**
 *
 * @author Incloud Services S.A.C.
 */
public enum TipoDocumentoEnum {

    FACTURA("01", "factura", "FACTURA", "factura"),
    LIQUIDACIONCOMPRA("03", "liquidacionCompra", "LIQUIDACIONCOMPRA", "liquidacionCompra"),
    CREDITO("04", "nota_credito", "NOTA DE CRÉDITO", "notaCredito"),
    DEBITO("05", "nota_debito", "NOTA DE DÉBITO", "notaDebito"),
    GUIA("06", "guia_remision", "GUIA DE REMISIÓN", "guiaRemision"),
    RETENCION("07", "retencion", "COMPROBANTE RETENCIÓN", "comprobanteRetencion");

    private final String codigo;
    private final String directorio;
    private final String descripcion;
    private final String prefijoXml;

    private TipoDocumentoEnum(String codigo, String directorio, String descripcion, String prefijo) {
        this.codigo = codigo;
        this.directorio = directorio;
        this.descripcion = descripcion;
        this.prefijoXml = prefijo;
    }
    
     public static TipoDocumentoEnum getTipo(String codigo) {
        switch (codigo) {
            case "01":
                return TipoDocumentoEnum.FACTURA;
            case "03":
                return TipoDocumentoEnum.LIQUIDACIONCOMPRA;
            case "04":
                return TipoDocumentoEnum.CREDITO;
            case "05":
                return TipoDocumentoEnum.DEBITO;
            case "06":
                return TipoDocumentoEnum.GUIA;
            case "07":
                return TipoDocumentoEnum.RETENCION;
            default:
                throw new IllegalArgumentException(codigo);
        }
    }

    public String getCodigo() {
        return this.codigo;
    }

    public String getDirectorio() {
        return this.directorio;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public String getPrefijoXml() {
        return prefijoXml;
    }
}
