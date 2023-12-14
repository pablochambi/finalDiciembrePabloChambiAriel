package ar.edu.unlam.pb2;

import ar.edu.unlam.pb2.enums.Deporte;
import ar.edu.unlam.pb2.enums.Paredes;
import ar.edu.unlam.pb2.enums.Piso;
 
public class CanchaPadel extends Cancha {
	
	private Paredes paredes;

	public CanchaPadel(Integer numero, Paredes paredes, Piso piso) {
		super(numero, Deporte.PADEL);
		this.setParedes(paredes);
		this.piso=piso;
	}

	public Paredes getParedes() {
		return paredes;
	}

	public void setParedes(Paredes paredes) {
		this.paredes = paredes;
	}
}
