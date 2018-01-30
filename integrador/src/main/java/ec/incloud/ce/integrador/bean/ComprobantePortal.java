package ec.incloud.ce.integrador.bean;

import java.io.Serializable;

public class ComprobantePortal implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String rucCliente;
	private String tipoDocCliente;
	private String razonSocialsCliente;
	
	public String getRucCliente() {
		return rucCliente;
	}
	public void setRucCliente(String rucCliente) {
		this.rucCliente = rucCliente;
	}
	public String getTipoDocCliente() {
		return tipoDocCliente;
	}
	public void setTipoDocCliente(String tipoDocCliente) {
		this.tipoDocCliente = tipoDocCliente;
	}
	public String getRazonSocialsCliente() {
		return razonSocialsCliente;
	}
	public void setRazonSocialsCliente(String razonSocialsCliente) {
		this.razonSocialsCliente = razonSocialsCliente;
	}
	@Override
	public String toString() {
		return "ComprobantePortal [rucCliente=" + rucCliente + ", tipoDocCliente=" + tipoDocCliente
				+ ", razonSocialsCliente=" + razonSocialsCliente + "]";
	}

}
