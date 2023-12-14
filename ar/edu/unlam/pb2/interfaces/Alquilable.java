package ar.edu.unlam.pb2.interfaces;

import ar.edu.unlam.pb2.Persona;
import ar.edu.unlam.pb2.enums.Horario;
import ar.edu.unlam.pb2.exception.HorarioOcupadoException;

public interface Alquilable {

	public void alquilar(Horario horario, Persona afiliado, Double senia) throws HorarioOcupadoException;
	
	public Boolean estaDisponible(Horario horario);
	
	public void setPrecio(Double precio);
}
