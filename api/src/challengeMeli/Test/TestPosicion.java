package challengeMeli.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import challengeMeli.Modelo.Planeta;
import challengeMeli.Modelo.Posicion;
import challengeMeli.Modelo.SentidoRotacion;

class TestPosicion {
	private static Planeta planetaTest;

	@BeforeEach
	public void InitTest() {
		planetaTest = new Planeta("planetaTest", new BigDecimal("4"), SentidoRotacion.ANTIHORARIO);
	}

	@Test
	public void testSetCoordenadas() {
		int diaActual = 200, diaOrigen = 1;

		// Resultado Esperado
		Posicion resultadoEsperado = new Posicion(planetaTest, diaActual, new BigDecimal("-969.600"),
				new BigDecimal("244.6000"));

		// Prueba
		/*
		 * Segun velocidad configurada, se mueve a 4 grados por dia, la posicion origen
		 * (0,1000), luego a partir de la diferencia de los dias origen y actual saco
		 * cuantos grados se movio, ejemplo: 200-1 = 199 dias * 3 grado/dia = 597 grados
		 */
		Posicion posicionDia200 = new Posicion(planetaTest, diaActual, new BigDecimal("0"), new BigDecimal("1000"),
				diaOrigen);

		assertTrue("La coordenada X para el dia " + diaActual + " esperada era" + new BigDecimal("-969.600"),
				resultadoEsperado.getCoordenadaX().compareTo(posicionDia200.getCoordenadaX()) == 0);
		assertEquals("La coordenada X para el dia " + diaActual + " esperada era" + new BigDecimal("-969.600"),
				resultadoEsperado.getCoordenadaY(), posicionDia200.getCoordenadaY());
	}

	@Test
	public void testOrdenarCoordenadas() {
		List<BigDecimal[]> casoDePrueba = new ArrayList<BigDecimal[]>();
		List<BigDecimal[]> resultadoObtenido = new ArrayList<BigDecimal[]>();
		List<BigDecimal[]> resultadoEsperado = new ArrayList<BigDecimal[]>();

		// Caso a Probar
		BigDecimal[] p1 = { new BigDecimal("1000"), new BigDecimal("-5.3") };
		BigDecimal[] p2 = { new BigDecimal("0"), new BigDecimal("10") };
		BigDecimal[] p3 = { new BigDecimal("0"), new BigDecimal("-2") };
		casoDePrueba.add(p1);
		casoDePrueba.add(p2);
		casoDePrueba.add(p3);

		// Resultado Esperado
		BigDecimal[] r1 = { new BigDecimal("0"), new BigDecimal("-2") };
		BigDecimal[] r2 = { new BigDecimal("0"), new BigDecimal("10") };
		BigDecimal[] r3 = { new BigDecimal("1000"), new BigDecimal("-5.3") };
		resultadoEsperado.add(r1);
		resultadoEsperado.add(r2);
		resultadoEsperado.add(r3);

		// Prueba
		resultadoObtenido = Posicion.ordenarCoordenadas(casoDePrueba);
		int indiceCoordenadas = 0;
		int indicePosiciones = 0;

		for (BigDecimal[] posicionesObtenidas : resultadoObtenido) {
			indiceCoordenadas = 0;
			for (BigDecimal coordenadasObtenidas : posicionesObtenidas) {
				assertEquals(resultadoEsperado.get(indicePosiciones)[indiceCoordenadas], coordenadasObtenidas);
				indiceCoordenadas++;
			}
			indicePosiciones++;
		}
	}

	@Test
	public void testDistancia() {
		BigDecimal resultadoObtenido;
		BigDecimal resultadoEsperado;

		// Caso a Probar
		BigDecimal x1 = new BigDecimal("0");
		BigDecimal y1 = new BigDecimal("-2");
		BigDecimal x2 = new BigDecimal("0");
		BigDecimal y2 = new BigDecimal("10");

		// sqrt( (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)) --> sqrt(0 + (12.0*12.0)) -->
		// sqrt(24.0) = 12.0
		resultadoEsperado = new BigDecimal("12.0");
		resultadoObtenido = Posicion.distancia(x1, y1, x2, y2);

		assertEquals("La distancia esperada era " + resultadoEsperado, resultadoEsperado, resultadoObtenido);
	}

	@Test
	public void testPertenece() {
		Boolean resultadoObtenido;
		Boolean resultadoEsperado;

		// Caso a Probar
		BigDecimal x1 = new BigDecimal("0.0");
		BigDecimal y1 = new BigDecimal("1.0");
		BigDecimal x2 = new BigDecimal("1.0");
		BigDecimal y2 = new BigDecimal("0.0");
		BigDecimal x3 = new BigDecimal("-1.0");
		BigDecimal y3 = new BigDecimal("-2.0");

		// Punto dentro
		// Punto a buscar
		BigDecimal pX = new BigDecimal("0.0");
		BigDecimal pY = new BigDecimal("0.0");
		resultadoEsperado = true; // El punto 0,0 se encuentra dentro del triangulo
		resultadoObtenido = Posicion.pertenece(pX, pY, x1, y1, x2, y2, x3, y3);
		assertEquals(resultadoEsperado, resultadoObtenido);

		// Punto fuera
		// Punto a buscar
		pX = new BigDecimal("10.0");
		pY = new BigDecimal("10.0");
		resultadoEsperado = false; // El punto 10,10 se encuentra fuera del triangulo
		resultadoObtenido = Posicion.pertenece(pX, pY, x1, y1, x2, y2, x3, y3);
		assertEquals(resultadoEsperado, resultadoObtenido);

	}

}
