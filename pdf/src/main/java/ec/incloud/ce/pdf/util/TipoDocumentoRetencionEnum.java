/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.pdf.util;

/**
 *
 * @author Incloud Services S.A.C.
 */
public enum TipoDocumentoRetencionEnum {

    FACTURA("01", "factura", "Factura"),
    LIQUIDACION("03", "liquidacion", "Liq. Compras"),
    DEBITO("05", "nota_debito", "Nota Débito");
	
    private final String codigo;
    private final String directorio;
    private final String descripcion;
    
    private TipoDocumentoRetencionEnum(String codigo, String directorio, String descripcion) {
        this.codigo = codigo;
        this.directorio = directorio;
        this.descripcion = descripcion;
    }

    public static TipoDocumentoRetencionEnum getTipo(String codigo) {
        switch (codigo) {
            case "01":
                return TipoDocumentoRetencionEnum.FACTURA;
            case "03":
                return TipoDocumentoRetencionEnum.LIQUIDACION;
            case "05":
                return TipoDocumentoRetencionEnum.DEBITO;
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

}
