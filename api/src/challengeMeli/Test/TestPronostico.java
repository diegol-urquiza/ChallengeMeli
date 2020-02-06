package challengeMeli.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import challengeMeli.Modelo.EstadoClima;
import challengeMeli.Modelo.Pronostico;

class TestPronostico {

	@Test
	void testPronostico() {
		// El punto (0,0) con estos valores estan dentro del triangulo, la condicion
		// para que el clima sea lluvioso
		BigDecimal x1 = new BigDecimal("0.0");
		BigDecimal y1 = new BigDecimal("1.0");
		BigDecimal x2 = new BigDecimal("1.0");
		BigDecimal y2 = new BigDecimal("0.0");
		BigDecimal x3 = new BigDecimal("-1.0");
		BigDecimal y3 = new BigDecimal("-2.0");

		Pronostico pronostico = new Pronostico(1, x1, y1, x2, y2, x3, y3);

		// Prueba
		assertEquals(1, pronostico.getDia());
		assertEquals(EstadoClima.LLUVIA, pronostico.getClima());
		assertTrue(new BigDecimal("7.404").compareTo(pronostico.getIntensidadLluvia()) == 0);
		// La intensidad es el perimetro del triangulo(mientras el 0.0 esta dentro)
	}

	@Test
	void testSequia() {
		BigDecimal x1 = new BigDecimal("0.0");
		BigDecimal y1 = new BigDecimal("1.0");
		BigDecimal x2 = new BigDecimal("0.0");
		BigDecimal y2 = new BigDecimal("2.0");
		BigDecimal x3 = new BigDecimal("0.0");
		BigDecimal y3 = new BigDecimal("10.0");
		
		BigDecimal[] p1 = { x1,y1 };
		BigDecimal[] p2 = { x2,y2 };
		BigDecimal[] p3 = { x3,y3 };
		
		List<BigDecimal[]> coordenadas = new ArrayList<BigDecimal[]>();
		coordenadas.add(p1);
		coordenadas.add(p2);
		coordenadas.add(p3);

		/* Al setear el clima se llamara adentro al metodo privado que trae la condicion del clima
		 * a traves de los valores de prueba pasados, sabiendo las condiciones para que cada estado de clima
		 * ocurra, probaremos esos metodos, recordamos las condiciones:
		 * Coordenadas de los 3 planetas forman linea recta con el sol incluido	-->Sequia
		 * Coordenadas de los 3 planetas forman linea recta sin el sol			-->Optimo
		 * Coordenadas de los 3 planetas triangulo con sol dentro				-->Lluvia
		 * */
		Pronostico pronostico = new Pronostico(1);
		pronostico.setClima(x1, y1, x2, y2, x3, y3);
		assertEquals(EstadoClima.SEQUIA, pronostico.getClima());
	}

	@Test
	void testOptimo() {
		BigDecimal x1 = new BigDecimal("3.0");
		BigDecimal y1 = new BigDecimal("0.0");
		BigDecimal x2 = new BigDecimal("3.0");
		BigDecimal y2 = new BigDecimal("1.0");
		BigDecimal x3 = new BigDecimal("3.0");
		BigDecimal y3 = new BigDecimal("10.0");
		
		BigDecimal[] p1 = { x1,y1 };
		BigDecimal[] p2 = { x2,y2 };
		BigDecimal[] p3 = { x3,y3 };
		
		List<BigDecimal[]> coordenadas = new ArrayList<BigDecimal[]>();
		coordenadas.add(p1);
		coordenadas.add(p2);
		coordenadas.add(p3);

		/* Al setear el clima se llamara adentro al metodo privado que trae la condicion del clima
		 * a traves de los valores de prueba pasados, sabiendo las condiciones para que cada estado de clima
		 * ocurra, probaremos esos metodos, recordamos las condiciones:
		 * Coordenadas de los 3 planetas forman linea recta con el sol incluido	-->Sequia
		 * Coordenadas de los 3 planetas forman linea recta sin el sol			-->Optimo
		 * Coordenadas de los 3 planetas triangulo con sol dentro				-->Lluvia
		 * */
		Pronostico pronostico = new Pronostico(1);
		pronostico.setClima(x1, y1, x2, y2, x3, y3);
		assertEquals(EstadoClima.OPTIMO, pronostico.getClima());
	}

	@Test
	void testLluvia() {
		BigDecimal x1 = new BigDecimal("0.0");
		BigDecimal y1 = new BigDecimal("1.0");
		BigDecimal x2 = new BigDecimal("1.0");
		BigDecimal y2 = new BigDecimal("0.0");
		BigDecimal x3 = new BigDecimal("-1.0");
		BigDecimal y3 = new BigDecimal("-2.0");
		
		BigDecimal[] p1 = { x1,y1 };
		BigDecimal[] p2 = { x2,y2 };
		BigDecimal[] p3 = { x3,y3 };
		
		List<BigDecimal[]> coordenadas = new ArrayList<BigDecimal[]>();
		coordenadas.add(p1);
		coordenadas.add(p2);
		coordenadas.add(p3);

		/* Al setear el clima se llamara adentro al metodo privado que trae la condicion del clima
		 * a traves de los valores de prueba pasados, sabiendo las condiciones para que cada estado de clima
		 * ocurra, probaremos esos metodos, recordamos las condiciones:
		 * Coordenadas de los 3 planetas forman linea recta con el sol incluido	-->Sequia
		 * Coordenadas de los 3 planetas forman linea recta sin el sol			-->Optimo
		 * Coordenadas de los 3 planetas triangulo con sol dentro				-->Lluvia
		 * */
		Pronostico pronostico = new Pronostico(1);
		pronostico.setClima(x1, y1, x2, y2, x3, y3);
		assertEquals(EstadoClima.LLUVIA, pronostico.getClima());
		assertTrue(new BigDecimal("7.404").compareTo(pronostico.getIntensidadLluvia()) == 0);
	}

}
