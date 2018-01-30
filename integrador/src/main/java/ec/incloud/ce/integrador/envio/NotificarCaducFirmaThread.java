package ec.incloud.ce.integrador.envio;

import java.util.Calendar;
import java.util.Date;

import ec.incloud.ce.integrador.bean.Sociedad;
import ec.incloud.ce.integrador.services.SociedadServices;
import ec.incloud.ce.integrador.util.Util;

public class NotificarCaducFirmaThread implements Runnable{
	
	private SociedadServices sociedadServices;
	private Sociedad sociedad;
	private int idSociedad;
	
	public NotificarCaducFirmaThread(SociedadServices sociedadServices, Sociedad sociedad){
		this.sociedadServices = sociedadServices;
		this.sociedad = sociedad;
		this.idSociedad = sociedad.getIdSociedad();
	}
	
	@Override
	public void run() {
		try {
			while(true){
				
				Calendar date = Calendar.getInstance();
				long t= date.getTimeInMillis();
				// 60000-> 1 minuto en milisegundos
				
				if( this.sociedad == null || this.sociedad.getIntervalNotifFirma() < 1  
						|| ( new Date(t + (this.sociedad.getIniNotifFirma() * 60000) ) ).compareTo( this.sociedad.getVencFirma() ) < 0						
				){
					//la fecha actual + los días de inicio de la notificación < fecha de vencimiento ?
					
					this.sociedad = new Sociedad() ;
					this.sociedad.setIdSociedad(idSociedad);
					
					sociedadServices.updateEstadoProcesoFirma( this.sociedad, Util.PROCESO_CADUCIDAD_FIRMA_OFF );
                	Thread.interrupted();
    				return;
				}
				
				String fechaCaducFirma = Util.INSTANCE.getStringFromDate( this.sociedad.getVencFirma() );
				
				sociedadServices.notificaFirmaCaducada( this.sociedad ,fechaCaducFirma);
				Thread.sleep( this.sociedad.getIntervalNotifFirma() * 1000*60 );// minutos_frecuencia_envio_correo * 1 minuto
				
				this.sociedad = sociedadServices.getSociedadByFirmaCaducada(this.sociedad);
			}
			
		} catch (Exception e) {
			sociedadServices.updateEstadoProcesoFirma(this.sociedad, Util.PROCESO_CADUCIDAD_FIRMA_OFF );
			e.printStackTrace();
		}
		
	}

}
