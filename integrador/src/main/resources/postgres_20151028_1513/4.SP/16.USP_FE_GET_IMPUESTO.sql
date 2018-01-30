-- Function: usp_fe_get_impuesto(integer, integer)

-- DROP FUNCTION usp_fe_get_impuesto(integer, integer);

CREATE OR REPLACE FUNCTION usp_fe_get_impuesto(p_cod integer, p_cod_porce integer)
  RETURNS impuesto AS
$BODY$
declare
	IMPUESTO IMPUESTO%rowtype;
begin
	select i.* into impuesto from impuesto i where codigo         = p_cod
						   and cod_porcentaje = p_cod_porce;
	return impuesto;
end
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION usp_fe_get_impuesto(integer, integer)
  OWNER TO postgres;