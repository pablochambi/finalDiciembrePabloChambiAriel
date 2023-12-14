package ar.edu.unlam.pb2;

import java.util.Objects;

import ar.edu.unlam.pb2.enums.Horario;

public class Alquiler {

	private Horario horario;
	private Persona responsable;
	private Double senia;

	public Alquiler(Horario horario, Persona responsable, Double senia) {
		this.horario = horario;
		this.responsable  = responsable;
		this.senia = senia;
	}

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	public Persona getResponsable() {
		return responsable;
	}

	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}

	public Double getSenia() {
		return senia;
	}

	public void setSenia(Double senia) {
		this.senia = senia;
	}

	@Override
	public int hashCode() {
		return Objects.hash(horario, responsable);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alquiler other = (Alquiler) obj;
		return horario == other.horario && Objects.equals(responsable, other.responsable);
	}
	
	
	
	
	
}
