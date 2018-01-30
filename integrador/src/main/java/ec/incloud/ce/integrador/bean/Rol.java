package ec.incloud.ce.integrador.bean;

import java.util.Date;

public class Rol {
	
	private int idRol;
	private String rol;
	private String descripcion;
	private String menu;
	private int idUsuarioRegistrado;
	private Date fechaRegistro;
	private int idUsuarioModificado;
	private Date fechaModifica;
	
	public int getIdRol() {
		return idRol;
	}
	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public int getIdUsuarioRegistrado() {
		return idUsuarioRegistrado;
	}
	public void setIdUsuarioRegistrado(int idUsuarioRegistrado) {
		this.idUsuarioRegistrado = idUsuarioRegistrado;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public int getIdUsuarioModificado() {
		return idUsuarioModificado;
	}
	public void setIdUsuarioModificado(int idUsuarioModificado) {
		this.idUsuarioModificado = idUsuarioModificado;
	}
	public Date getFechaModifica() {
		return fechaModifica;
	}
	public void setFechaModifica(Date fechaModifica) {
		this.fechaModifica = fechaModifica;
	}
}
