package ar.edu.unlam.pb2;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ar.edu.unlam.pb2.enums.Deporte;
import ar.edu.unlam.pb2.enums.Horario;
import ar.edu.unlam.pb2.enums.Piso;
import ar.edu.unlam.pb2.exception.HorarioOcupadoException;
import ar.edu.unlam.pb2.interfaces.Alquilable;

public abstract class Cancha implements Alquilable {

	private Integer numero;
	private Set<Alquiler> alquileres;
	private Deporte deporte;
	private Double precio;
	protected Piso piso;

	public Cancha(Integer numero, Deporte deporte) {
		this.numero = numero;
		this.deporte = deporte;
		this.alquileres = new HashSet<>();
		this.precio = 0.0;
	}

	public void alquilar(Horario horario, Persona afiliado, Double senia) throws HorarioOcupadoException {
		
		if(!this.estaDisponible(horario))
			throw new HorarioOcupadoException();
		
		Double veintePorciento = getPrecio()*0.2;
		Double ochentaPorciento = getPrecio()*0.8;
		
		if(senia >= veintePorciento && senia <= ochentaPorciento) {
			Alquiler alq = new Alquiler(horario, afiliado, senia);
			this.alquileres.add(alq);
		}
		
		
	}

	public Boolean estaDisponible(Horario horario) {
		
		for(Alquiler alq: this.alquileres) {
			if(alq.getHorario().equals(horario)) {
				return false;
			}
		}
		
		return true;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Set<Alquiler> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(Set<Alquiler> alquileres) {
		this.alquileres = alquileres;
	}

	public Deporte getDeporte() {
		return deporte;
	}

	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Piso getPiso() {
		return piso;
	}

	public void setPiso(Piso piso) {
		this.piso = piso;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
//		if (getClass() != obj.getClass())
//			return false;
		Cancha other = (Cancha) obj;
		return Objects.equals(numero, other.numero);
	}

	public Double getPrecioRestanteAPagar(Persona persona, Horario horario) {
		// TODO Auto-generated method stub
		Double restante = 0.0;
		
		for(Alquiler alq : this.alquileres) {
			if(alq.getResponsable().equals(persona) && alq.getHorario().equals(horario)) {
				restante = getPrecio() - alq.getSenia();
			}
		}
		return restante;
		
	}

	public Double getTotalRecaudado() {
		// TODO Auto-generated method stub
		Double total = 0.0;
		for(Alquiler alq :this.alquileres) {
			total += alq.getSenia();
		}
		return total;
	}

	
	

}
