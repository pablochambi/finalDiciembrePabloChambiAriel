package ar.edu.unlam.pb2.test;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import ar.edu.unlam.pb2.Cancha;
import ar.edu.unlam.pb2.CanchaFutbol;
import ar.edu.unlam.pb2.CanchaPadel;
import ar.edu.unlam.pb2.Club;
import ar.edu.unlam.pb2.Persona;
import ar.edu.unlam.pb2.enums.Capacidad;
import ar.edu.unlam.pb2.enums.Deporte;
import ar.edu.unlam.pb2.enums.Horario;
import ar.edu.unlam.pb2.enums.Paredes;
import ar.edu.unlam.pb2.enums.Piso;
import ar.edu.unlam.pb2.exception.CanchaInexistenteException;
import ar.edu.unlam.pb2.exception.CapacidadMaximaAlcanzadaException;
import ar.edu.unlam.pb2.exception.CodigoAfiliadoDuplicadoException;
import ar.edu.unlam.pb2.exception.DniExistenteException;
import ar.edu.unlam.pb2.exception.HorarioOcupadoException;
import ar.edu.unlam.pb2.exception.NumeroDeCanchaDuplicadoException;
import ar.edu.unlam.pb2.exception.PersonaInexistenteException;
import ar.edu.unlam.pb2.interfaces.Alquilable;

public class TestCases {
	/*
	 * Se pide realizar un sistema para Club Deportivo UNLAM. El club cuenta con
	 * capacidad para 500 afiliados No pueden existir varios afiliados con el mismo
	 * codigo, o mismo DNI. Los afiliados pueden aprovechar las instalaciones para
	 * jugar Padel o Futbol. Para ello es necesario alquilar una cancha. Se debe
	 * abonar entre el 20 y el 80 % del costo del alquiler de la cancha, a modo de
	 * reserva. Solo un afiliado al club puede alquilarlas. Al finalizar el horario,
	 * el afiliado debe abonar el saldo restante y la cancha pasa a estar disponible
	 * en ese horario. Las canchas de Padel pueden ser de piso sintetico o cemento,
	 * y pueden tener paredes vidriadas o de cemento Las canchas de futbol pueden
	 * ser de piso sintetico o cemento, y su capacidad para 5, 7 u 11 jugadores por
	 * equipo. No se debe permitir alquilar una cancha en un horario no disponible.
	 * 
	 */

	@Test
	public void queSePuedaAgregarUnaCanchaDePadel() throws NumeroDeCanchaDuplicadoException {
		Club club = new Club("Boca");
		club.agregarCancha(new CanchaPadel(1, Paredes.CEMENTO, Piso.CEMENTO));
		
		assertTrue(club.existeCancha(1));
	}

	@Test(expected = NumeroDeCanchaDuplicadoException.class)
	public void queAlIntentarAgregarUnaCanchaConUnNumeroExistenteSeLanceUnaNumeroDeCanchaDuplicadoException() throws NumeroDeCanchaDuplicadoException {
		Club club = new Club("Boca");
		club.agregarCancha(new CanchaPadel(1, Paredes.CEMENTO, Piso.CEMENTO));
		club.agregarCancha(new CanchaFutbol(1, Capacidad.CINCO, Piso.CEMENTO));
		
	}

	@Test
	public void queSePuedaConsultarLasCanchasDePadelDisponibles() throws NumeroDeCanchaDuplicadoException {
		Club club = new Club("Boca");
		
		club.agregarCancha(new CanchaPadel(1, Paredes.CEMENTO, Piso.CEMENTO));
		club.agregarCancha(new CanchaFutbol(2, Capacidad.CINCO, Piso.CEMENTO));
		club.agregarCancha(new CanchaPadel(3, Paredes.CEMENTO, Piso.CEMENTO));
		club.agregarCancha(new CanchaFutbol(4, Capacidad.CINCO, Piso.CEMENTO));
		
		Set<Alquilable> canchasDisponibles = club.getCanchasDisponibles(Deporte.PADEL, Horario.BLOQUE1);
		
		
		Integer cantidadEsperada = 2;
		Integer cantidadObtenida = canchasDisponibles.size();
		
		assertEquals(cantidadEsperada,cantidadObtenida);
		
	}

	@Test
	public void queUnaPersonaSePuedaAfiliarAlClub() throws NumeroDeCanchaDuplicadoException, CodigoAfiliadoDuplicadoException, DniExistenteException, CapacidadMaximaAlcanzadaException, PersonaInexistenteException {
		
		Club club = new Club("Boca");
		Persona persona =  new Persona(111, "Yanina", "Torres");
		club.afiliar("A1", persona);
		
		assertTrue(club.getAfiliados().containsKey("A1"));
		assertTrue(club.getAfiliados().containsValue(new Persona(111, "Yanina", "Torres")));
		
		assertEquals(persona,club.getAfiliado(111));
		assertEquals(persona, club.getAfiliado("A1"));
	}

	@Test
	public void queUnAfiliadoPuedaAlquilarUnaCanchaDePadel() throws NumeroDeCanchaDuplicadoException, CodigoAfiliadoDuplicadoException, DniExistenteException, HorarioOcupadoException, CapacidadMaximaAlcanzadaException, PersonaInexistenteException, CanchaInexistenteException {
		Club club = new Club("Boca");
		
		CanchaPadel canchaPadel = new CanchaPadel(1, Paredes.CEMENTO, Piso.CEMENTO);
		club.agregarCancha(canchaPadel);
		
		
		Persona yanina =  new Persona(111, "Yanina", "Torres");
		String codigo  = "Y1";
		club.afiliar(codigo, yanina);
		
		Horario horario = Horario.BLOQUE1;
		Double senia = 200.0;
		club.alquilarUnaCancha(yanina, canchaPadel, horario, senia);
		

		assertTrue(club.getCanchas().contains(canchaPadel));
		
		Cancha cancha = (Cancha) club.getCancha(canchaPadel.getNumero());
		assertTrue(cancha.getAlquileres().size() == 1);
		
	}
	

	@Test
	public void queSePuedaAveriguarCuantoDebePagarPorLaCanchaDeFutbol11AlquiladaConsiderandoLaSenia() throws NumeroDeCanchaDuplicadoException, DniExistenteException, CodigoAfiliadoDuplicadoException, CapacidadMaximaAlcanzadaException, PersonaInexistenteException, CanchaInexistenteException, HorarioOcupadoException {

		Club club = new Club("Boca");
		
		Cancha canchaFutbol = new CanchaFutbol(1, Capacidad.ONCE, Piso.SINTETICO);
		canchaFutbol.setPrecio(100.0);
		club.agregarCancha(canchaFutbol);
		
		
		Persona yanina =  new Persona(111, "Yanina", "Torres");
		String codigo  = "Y1";
		club.afiliar(codigo, yanina);
		
		Horario horario = Horario.BLOQUE1;
		Double senia = 40.0;
		club.alquilarUnaCancha(yanina, canchaFutbol, horario, senia);
		
		
		assertEquals(60.0, canchaFutbol.getPrecioRestanteAPagar(yanina, horario),0.01);
		
	}

	@Test
	public void queSePuedaObtenerUnMapaConLosTotalesRecaudadosPorCadaCancha() throws NumeroDeCanchaDuplicadoException, DniExistenteException, CodigoAfiliadoDuplicadoException, CapacidadMaximaAlcanzadaException, PersonaInexistenteException, CanchaInexistenteException, HorarioOcupadoException {
		// TODO: para la key utilizar el siguiente formato:
		// Futbol: FUTBOL-11-1 (Deporte-Capacidad-numero)
		// Padel: PADEL-VIDRIADA-1 (Deporte-Paredes-numero)
		
		Club club = new Club("Boca");
		
		Cancha canchaFutbol = new CanchaFutbol(1, Capacidad.ONCE, Piso.SINTETICO);
		canchaFutbol.setPrecio(100.0);
		club.agregarCancha(canchaFutbol);
		
		Cancha canchaPadel = new CanchaPadel(2, Paredes.VIDRIADA, Piso.CEMENTO);
		canchaPadel.setPrecio(100.0);
		club.agregarCancha(canchaPadel);
		
		
		Persona yanina =  new Persona(111, "Yanina", "Torres");
		String codigo  = "Y1";
		club.afiliar(codigo, yanina);
		
		Horario horarioBloque1 = Horario.BLOQUE1;
		Horario horarioBloque2 = Horario.BLOQUE2;
		Horario horarioBloque3 = Horario.BLOQUE3;
		Double senia40 = 40.0;
		Double senia70 = 70.0;
		
		club.alquilarUnaCancha(yanina, canchaFutbol, horarioBloque1, senia40);
		club.alquilarUnaCancha(yanina, canchaPadel, horarioBloque2, senia70);
		club.alquilarUnaCancha(yanina, canchaPadel, horarioBloque3, senia70);
		
		Map<String, Double> totalRecudadoPorCancha = club.obtenerTotalesPorCancha();
		
		assertEquals(40.0,totalRecudadoPorCancha.get("FUTBOL-11-1"),0.01);
		assertEquals(140.0,totalRecudadoPorCancha.get("PADEL-VIDRIADA-2"),0.01);
		
	}

	@Test
	public void queSePuedanObtenerLasPersonasAfiliadasAlClubOrdenadasPorApellidoAscendente() throws NumeroDeCanchaDuplicadoException, DniExistenteException, CodigoAfiliadoDuplicadoException, CapacidadMaximaAlcanzadaException, PersonaInexistenteException {
		
		Club club = new Club("Boca");
		
		club.afiliar("Y1", new Persona(111, "Yanina", "bb"));
		club.afiliar("C1", new Persona(222, "Carlos", "zz"));
		club.afiliar("A1", new Persona(333, "Juan", "aa"));
		club.afiliar("N1", new Persona(444, "Natalia", "xx"));
		
		TreeSet<Persona> personas = (TreeSet<Persona>) club.obtenerLasPersonasAfiliadasAlClubOrdenadasPorApellidoAsc();
		
		assertEquals("aa", personas.first().getApellido());
		assertEquals("zz", personas.last().getApellido());
		
		
	}

}