package ec.incloud.ce.integrador.envio;

import java.util.Date;

import ec.incloud.ce.integrador.bean.Documento;
import ec.incloud.ce.integrador.bean.Sociedad;
import ec.incloud.ce.integrador.exception.IntegradorException;
import ec.incloud.ce.integrador.services.DocumentoServices;
import ec.incloud.ce.integrador.services.ServicesFactory;

public class HiloCorreoByMinutes implements Runnable{
	
	private DocumentoServices documentoServices;
	private Documento documento;
	private Date timeAvailableCorrecion;//fecha de no autorizado o no recepcionado. Esta fecha es fija para este hilo.
	
	public HiloCorreoByMinutes(DocumentoServices documentoServices, Documento documento, Date timeAvailableCorrecion){
		this.documentoServices = documentoServices;
		this.documento = documento;
		this.timeAvailableCorrecion = timeAvailableCorrecion;
	}
	
	@Override
	public void run() {
		String timeAvailableCorrecionTxt=null;
		String mailNotificacion = this.documento.getSociedad().getMailNotificacion();
		int iniTimeAvailableCorrecion = this.documento.getSociedad().getIniTimeAvailableCorrecion() ;//fijo durante el proceso del hilo.
		
		try {
			while(true){
				
				if( this.documento.getSociedad().getIntervalNotifDocRechazado() < 1 ){
                	Thread.interrupted();
    				return;
				}
				
				Thread.sleep(this.documento.getSociedad().getIntervalNotifDocRechazado() * 1000*60);//minutos * minutos_frecuencia_envio_correo				
				
				long secs = (new Date().getTime() - this.timeAvailableCorrecion.getTime()) / 1000;					
				int hours = (int) (secs / 3600);
				secs = secs % 3600;
				int mins = (int) (secs / 60);
				secs = secs % 60;
				
				if( ( iniTimeAvailableCorrecion - hours ) < 0 || ( ( iniTimeAvailableCorrecion - hours ) == 0 && ( 59 - mins ) < 0 ) ){
                	Thread.interrupted();
    				return;
				}
				
				timeAvailableCorrecionTxt = ( iniTimeAvailableCorrecion - hours ) +" horas y "+ ( 59-mins ) +" minutos";
				
				//obtener el último doc, por el nro sri indicado y secuencia indicado
                Documento documentoActualizado = documentoServices.getDocumentoById(
                    this.documento.getSociedad().getRuc(),
                    this.documento.getTipoDocumento(),
                    this.documento.getEstablecimiento(),
                    this.documento.getPuntoEmision(),
                    this.documento.getNumero(),
                    this.documento.getSecuencia()
                );
                
                if(	 	documentoActualizado == null 
            		|| 	documentoActualizado.getEstadoNotifRechazado() == 1
                ){
                	Thread.interrupted();
    				return;
                }
                this.documento.getSociedad().setMailNotificacion(mailNotificacion);
				documentoServices.renotificaRechazoAdministrador(this.documento,timeAvailableCorrecionTxt);
				
				Sociedad sociedadActualizada = ServicesFactory.getFactory().createSociedadServices().getSociedad( this.documento.getSociedad().getRuc()  );				
				this.documento.setSociedad(sociedadActualizada);
			}
			
		} catch (IntegradorException integradorException) {
			integradorException.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
