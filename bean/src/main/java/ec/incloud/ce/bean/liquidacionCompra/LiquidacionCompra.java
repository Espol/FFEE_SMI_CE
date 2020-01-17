package ec.incloud.ce.bean.liquidacionCompra;

import java.util.List;

import ec.incloud.ce.bean.common.CampoAdicional;
import ec.incloud.ce.bean.common.InfoTributaria;
import ec.incloud.ce.bean.facturaReembolso.ReembolsoDetalle;

public class LiquidacionCompra {
	
	private String id;
    private String version;
    private InfoTributaria infoTributaria;
    private InfoLiquidacionCompra infoLiquidacionCompra;
    private List<LiquidacionCompraDetalle> detalles;
    private List<ReembolsoDetalle> reembolsos;//Reembolso
    private MaquinaFiscal maquinaFiscal;
    private List<CampoAdicional> infoAdicional;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public InfoTributaria getInfoTributaria() {
		return infoTributaria;
	}
	public void setInfoTributaria(InfoTributaria infoTributaria) {
		this.infoTributaria = infoTributaria;
	}
	public InfoLiquidacionCompra getInfoLiquidacionCompra() {
		return infoLiquidacionCompra;
	}
	public void setInfoLiquidacionCompra(InfoLiquidacionCompra infoLiquidacionCompra) {
		this.infoLiquidacionCompra = infoLiquidacionCompra;
	}
	public List<LiquidacionCompraDetalle> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<LiquidacionCompraDetalle> detalles) {
		this.detalles = detalles;
	}
	public List<ReembolsoDetalle> getReembolsos() {
		return reembolsos;
	}
	public void setReembolsos(List<ReembolsoDetalle> reembolsos) {
		this.reembolsos = reembolsos;
	}
	public MaquinaFiscal getMaquinaFiscal() {
		return maquinaFiscal;
	}
	public void setMaquinaFiscal(MaquinaFiscal maquinaFiscal) {
		this.maquinaFiscal = maquinaFiscal;
	}
	public List<CampoAdicional> getInfoAdicional() {
		return infoAdicional;
	}
	public void setInfoAdicional(List<CampoAdicional> infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

}
