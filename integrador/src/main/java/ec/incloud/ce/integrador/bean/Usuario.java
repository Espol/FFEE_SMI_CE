package ec.incloud.ce.integrador.bean;

public class Usuario {

	private int idUsuario;
	private int idPersona;
	private int idRol;
	private String username;
	private String clave;
	private String observacion;
	private int activo;
	private int eliminado;
	private String correo;
	private String claveInicial;
	private String usuarioSap;
	private String rucPersona;
	private int flagSap;
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
	public int getIdRol() {
		return idRol;
	}
	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}
	//	public Persona getPersona() {
//		return persona;
//	}
//	public void setPersona(Persona persona) {
//		this.persona = persona;
//	}
//	public Rol getRol() {
//		return rol;
//	}
//	public void setRol(Rol rol) {
//		this.rol = rol;
//	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public int getActivo() {
		return activo;
	}
	public void setActivo(int activo) {
		this.activo = activo;
	}
	public int getEliminado() {
		return eliminado;
	}
	public void setEliminado(int eliminado) {
		this.eliminado = eliminado;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getClaveInicial() {
		return claveInicial;
	}
	public void setClaveInicial(String claveInicial) {
		this.claveInicial = claveInicial;
	}
	public String getUsuarioSap() {
		return usuarioSap;
	}
	public void setUsuarioSap(String usuarioSap) {
		this.usuarioSap = usuarioSap;
	}
	public String getRucPersona() {
		return rucPersona;
	}
	public void setRucPersona(String rucPersona) {
		this.rucPersona = rucPersona;
	}
	public int getFlagSap() {
		return flagSap;
	}
	public void setFlagSap(int flagSap) {
		this.flagSap = flagSap;
	}
	
}
