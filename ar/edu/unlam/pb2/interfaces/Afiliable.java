package ar.edu.unlam.pb2.interfaces;

import ar.edu.unlam.pb2.Persona;
import ar.edu.unlam.pb2.exception.CapacidadMaximaAlcanzadaException;
import ar.edu.unlam.pb2.exception.CodigoAfiliadoDuplicadoException;
import ar.edu.unlam.pb2.exception.DniExistenteException;
import ar.edu.unlam.pb2.exception.PersonaInexistenteException;

public interface Afiliable {

	public void afiliar(String codigoAfiliado, Persona persona) throws DniExistenteException, CodigoAfiliadoDuplicadoException, CapacidadMaximaAlcanzadaException, PersonaInexistenteException;
	public Persona getAfiliado(Integer dni) throws PersonaInexistenteException;
	public Persona getAfiliado(String codigoAfiliado);
	public Boolean existeAfiliado(Integer dni);
	public Boolean existeAfiliado(String codigoAfiliado);
	
	
}
