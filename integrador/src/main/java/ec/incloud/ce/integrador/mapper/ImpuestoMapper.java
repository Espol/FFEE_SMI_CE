package ec.incloud.ce.integrador.mapper;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ec.incloud.ce.integrador.bean.Impuesto;



public interface ImpuestoMapper {
	
	@Results({ 
		@Result(property = "codigo", column = "codigo"),
		@Result(property = "codigoPorcentaje", column = "cod_porcentaje"),
		@Result(property = "porcentaje", column = "porcentaje") 
	})
	@Select("SELECT * FROM usp_fe_get_impuesto(#{codigo},#{codProcentaje})")
	@Options(statementType = StatementType.CALLABLE)
	public Impuesto getImpuesto(@Param("codigo") int codigo, @Param("codProcentaje") int codProcentaje);

}
