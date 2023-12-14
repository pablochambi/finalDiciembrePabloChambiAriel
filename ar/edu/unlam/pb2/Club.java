package ar.edu.unlam.pb2;

import java.util.HashMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import ar.edu.unlam.pb2.enums.Capacidad;
import ar.edu.unlam.pb2.enums.Deporte;
import ar.edu.unlam.pb2.enums.Horario;
import ar.edu.unlam.pb2.enums.Paredes;
import ar.edu.unlam.pb2.exception.*;
import ar.edu.unlam.pb2.interfaces.Afiliable;
import ar.edu.unlam.pb2.interfaces.Alquilable;

public class Club implements Afiliable{
	
	private String nombre;
	private Integer capacidad;
	private final Integer CAPACIDAD_MAX = 500;

	private Set<Alquilable> canchas;
	private Map<String, Persona> afiliados;

	public Club(String nombre) {
		this.nombre =nombre;
		this.capacidad = CAPACIDAD_MAX;
		this.canchas = new HashSet<>();
		this.afiliados = new HashMap<>();
	}

	public void agregarCancha(Alquilable cancha) throws NumeroDeCanchaDuplicadoException {
		if(!this.canchas.add(cancha))
			throw new NumeroDeCanchaDuplicadoException();
	}

	public Boolean existeCancha(Integer codigoCancha) {
		for(Alquilable alq : this.canchas) {
			if(((Cancha)alq).getNumero().equals(codigoCancha)) {
				return true;
			}
		}
		return false;
	}

	public Alquilable getCancha(Integer numeroCancha) throws CanchaInexistenteException {
		
		for(Alquilable alq : this.canchas) {
			if(alq instanceof Cancha && ((Cancha) alq).getNumero().equals(numeroCancha)) {
				return alq;
			}
		}
		throw new CanchaInexistenteException();
	}

	public Set<Alquilable> getCanchasDisponibles(Deporte deporte, Horario horario) {
		
		Set<Alquilable> canchas = new HashSet<>();
		for(Alquilable alq : this.canchas) {
			if(((Cancha)alq).getDeporte().equals(deporte) && alq.estaDisponible(horario)) {
				canchas.add(alq);
			}
		}
		return canchas;
	}
	
	public Map<String, Double> obtenerTotalesPorCancha(){
		// TODO: para la key utilizar el siguiente formato:
		// Futbol: FUTBOL-11-1 (Deporte-Capacidad-numero)
		// Padel: PADEL-VIDRIADA-1 (Deporte-Paredes-numero)
		Map<String, Double> totalRecaudadoPorCadaCancha = new HashMap<>();
		
		
		for(Alquilable cancha: this.canchas) {
			
			String canchaString = canchaAString(cancha);
			Double totalCancha = ((Cancha)cancha).getTotalRecaudado();
			
			totalRecaudadoPorCadaCancha.put(canchaString, totalCancha);
			
		}
		return totalRecaudadoPorCadaCancha;
	}

	

	private String canchaAString(Alquilable cancha) {
		String canchaString = "";
		
		if(cancha instanceof CanchaFutbol) {
			canchaString += "FUTBOL-";
			if(((CanchaFutbol) cancha).getCapacidad().equals(Capacidad.ONCE)) {
				canchaString += "11-";
			}
			if(((CanchaFutbol) cancha).getCapacidad().equals(Capacidad.CINCO)) {
				canchaString += "5-";
			}
			if(((CanchaFutbol) cancha).getCapacidad().equals(Capacidad.SIETE)) {
				canchaString += "7-";
			}
		}
		
		if(cancha instanceof CanchaPadel) {
			canchaString += "PADEL-";
			if(((CanchaPadel) cancha).getParedes().equals(Paredes.VIDRIADA)) {
				canchaString += "VIDRIADA-";
			}
			if(((CanchaPadel) cancha).getParedes().equals(Paredes.CEMENTO)) {
				canchaString += "CEMENTO-";
			}
			
		}
		
		String numeroString = ((Cancha)cancha).getNumero().toString();
		canchaString += numeroString;
		
		return canchaString;
	}

	
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public Set<Alquilable> getCanchas() {
		return canchas;
	}

	public void setCanchas(Set<Alquilable> canchas) {
		this.canchas = canchas;
	}

	public Map<String, Persona> getAfiliados() {
		return afiliados;
	}



	

	public void alquilarUnaCancha(Persona personaBus, Cancha canchaBus, Horario horario, Double senia) throws PersonaInexistenteException, CanchaInexistenteException, HorarioOcupadoException {
		// TODO Auto-generated method stub
//		if(!this.afiliados.containsValue(personaBus))
//			throw new PersonaInexistenteException();
//		
//		if(!this.canchas.contains(canchaBus))
//			throw new CanchaInexistenteException();
		
		Cancha cancha  = (Cancha) getCancha(canchaBus.getNumero());
		Persona persona = getAfiliado(personaBus.getDni());
		
		cancha.alquilar(horario, persona, senia);
		
	}

	@Override
	public void afiliar(String codigoAfiliado, Persona personanueva)
			throws DniExistenteException, CodigoAfiliadoDuplicadoException, CapacidadMaximaAlcanzadaException, PersonaInexistenteException {
		// TODO Auto-generated method stub
		
		if(this.afiliados.containsKey(codigoAfiliado))
			throw new CodigoAfiliadoDuplicadoException();
		
		if(this.afiliados.containsValue(personanueva))
			throw new DniExistenteException();
		
		this.afiliados.put(codigoAfiliado, personanueva);
		
	}

	@Override
	public Persona getAfiliado(Integer dni)  {
		// TODO Auto-generated method stub
		for (Map.Entry<String, Persona> entry : this.afiliados.entrySet()) {
			String cod = entry.getKey();
			Persona persona = entry.getValue();
			if(persona.getDni().equals(dni)) {
				return persona;
			}
			
		}
		
		return null;
	}

	@Override
	public Persona getAfiliado(String codigoAfiliado) {
		// TODO Auto-generated method stub
		for (Map.Entry<String, Persona> entry : this.afiliados.entrySet()) {
			String codigo = entry.getKey();
			Persona persona = entry.getValue();
			if(codigo.equals(codigoAfiliado)) {
				return persona;
			}
			
		}
		
		return null;
	}

	@Override
	public Boolean existeAfiliado(Integer dni) {
		return getAfiliado(dni)!=null ? true:false;
	}

	@Override
	public Boolean existeAfiliado(String codigoAfiliado) {
		return getAfiliado(codigoAfiliado)!=null ? true:false;
	}

	public Set<Persona> obtenerLasPersonasAfiliadasAlClubOrdenadasPorApellidoAsc() {
		// TODO Auto-generated method stub
		Set<Persona> personasOrd = new TreeSet<>(new OrdenPorApellidoAsc());
		
		for (Map.Entry<String, Persona> entry : this.afiliados.entrySet()) {
			
			Persona persona = entry.getValue();
			
			personasOrd.add(persona);
			
			
		}
		
		return personasOrd;
	}

	

	

}
