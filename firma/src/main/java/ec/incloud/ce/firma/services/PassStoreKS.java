package ec.incloud.ce.firma.services;

import java.security.cert.X509Certificate;

import es.mityc.javasign.pkstore.IPassStoreKS;

class PassStoreKS implements IPassStoreKS {
	
	/** Contrase�a de acceso al almac�n. */
	private transient String password;
	
	/**
	 * <p>Crea una instancia con la contrase�a que se utilizar� con el almac�n relacionado.</p>
	 * @param pass Contrase�a del almac�n
	 */
	public PassStoreKS(final String pass) {
		this.password = new String(pass);
	}

	/**
	 * <p>Devuelve la contrase�a configurada para este almac�n.</p>
	 * @param certificate No se utiliza
	 * @param alias no se utiliza
	 * @return contrase�a configurada para este almac�n
	 * @see es.mityc.javasign.pkstore.IPassStoreKS#getPassword(java.security.cert.X509Certificate, java.lang.String)
	 */
	public char[] getPassword(final X509Certificate certificate, final String alias) {
		return password.toCharArray();
	}

}
