
-- ok

CREATE OR REPLACE FUNCTION USP_FE_GET_DOCUMENTO(
    pRuc_emisor       SOCIEDAD.ruc%TYPE,
    pTipo_documento   documento.tipo_documento%TYPE,
    pEstablecimiento   documento.establecimiento%TYPE,
    pPunto_emision     documento.punto_emision%TYPE,
    pNro_documento     documento.numero%TYPE
)
RETURNS TABLE (
    id_periodo  documento.id_periodo%type ,
    id_sociedad     documento.id_sociedad%type,
    secuencia       documento.secuencia%type ,
    fecha_emision   documento.fecha_emision%type,
    tipo_documento  documento.tipo_documento%type,
    establecimiento documento.establecimiento%type,
    punto_emision   documento.punto_emision%type,
    numero      documento.numero%type,
    estado      documento.estado%type,
    serie_correlativo documento.serie_correlativo%type,
    anulado 	documento.anulado%type,
    numero_sap 	documento.numero_sap%type
)

AS $$
BEGIN
RETURN QUERY
    SELECT doc.id_periodo,
        doc.id_sociedad,
        doc.secuencia,
        doc.fecha_emision,
        doc.tipo_documento,
        doc.establecimiento,
        doc.punto_emision,
        doc.numero,
        COALESCE(doc.estado_sri, doc.estado) estado,
        doc.serie_correlativo,
        doc.anulado,
        doc.numero_sap
    FROM DOCUMENTO doc
    WHERE doc.ultimo = TRUE
        AND doc.tipo_documento  = pTipo_documento
        AND doc.establecimiento = pEstablecimiento
        AND doc.punto_emision   = pPunto_emision
        AND doc.numero          = pNro_documento
        AND doc.id_sociedad     = ( SELECT s.id_sociedad FROM sociedad s WHERE ruc = pRuc_emisor);

END
$$
LANGUAGE plpgsql;