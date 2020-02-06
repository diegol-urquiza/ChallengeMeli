package challengeMeli.Modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Pronostico {
	private int dia;
	private EstadoClima clima;
	private BigDecimal intensidadLluvia;

	// Constructor
	public Pronostico(int dia) {
		this.dia = dia;
	}
	public Pronostico(int dia, BigDecimal xP1, BigDecimal yP1, BigDecimal xP2, BigDecimal yP2, BigDecimal xP3,
			BigDecimal yP3) {
		this.dia = dia;
		this.clima = this.getEstadoDia(xP1, yP1, xP2, yP2, xP3, yP3);
	}

	// Getters y Setters
	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public EstadoClima getClima() {
		return clima;
	}

	public void setClima(EstadoClima clima) {
		this.clima = clima;
	}
	public void setClima(BigDecimal xP1, BigDecimal yP1, BigDecimal xP2, BigDecimal yP2, BigDecimal xP3,
			BigDecimal yP3) {
		this.clima = this.getEstadoDia(xP1, yP1, xP2, yP2, xP3, yP3);
	}
	public BigDecimal getIntensidadLluvia() {
		return intensidadLluvia;
	}

	public void setIntensidadLluvia(BigDecimal intensidadLluvia) {
		this.intensidadLluvia = intensidadLluvia;
	}

	// Metodos
	private EstadoClima getEstadoDia(BigDecimal xP1, BigDecimal yP1, BigDecimal xP2, BigDecimal yP2, BigDecimal xP3,
			BigDecimal yP3) {
		List<BigDecimal[]> coordenadas = new ArrayList<BigDecimal[]>();
		List<BigDecimal[]> coordenadasOrdenadas = new ArrayList<BigDecimal[]>();

		BigDecimal[] p1 = { xP1, yP1 };
		BigDecimal[] p2 = { xP2, yP2 };
		BigDecimal[] p3 = { xP3, yP3 };

		coordenadas.add(p1);
		coordenadas.add(p2);
		coordenadas.add(p3);

		coordenadasOrdenadas = Posicion.ordenarCoordenadas(coordenadas);

		if (this.sequia(coordenadasOrdenadas))
			return EstadoClima.SEQUIA;

		if (this.optimo(coordenadasOrdenadas))
			return EstadoClima.OPTIMO;

		if (this.lluvia(coordenadasOrdenadas))
			return EstadoClima.LLUVIA;

		return EstadoClima.INDEFINIDO;
	}

	private Boolean sequia(List<BigDecimal[]> coordenadasOrdenadas) {
		BigDecimal minX = coordenadasOrdenadas.get(0)[0];
		BigDecimal minY = coordenadasOrdenadas.get(0)[1];
		BigDecimal mediaX = coordenadasOrdenadas.get(1)[0];
		BigDecimal mediaY = coordenadasOrdenadas.get(1)[1];
		BigDecimal maxX = coordenadasOrdenadas.get(2)[0];
		BigDecimal maxY = coordenadasOrdenadas.get(2)[1];

		BigDecimal p1p2 = Posicion.distancia(minX, minY, mediaX, mediaY);
		BigDecimal p2p3 = Posicion.distancia(mediaX, mediaY, maxX, maxY);
		BigDecimal p1p3 = Posicion.distancia(minX, minY, maxX, maxY);

		if (p1p2.add(p2p3).compareTo(p1p3) == 0) {
			List<BigDecimal[]> coordenadasSol = new ArrayList<BigDecimal[]>();
			List<BigDecimal[]> coordenadasOrdenadasSol = new ArrayList<BigDecimal[]>();

			BigDecimal[] sol = { new BigDecimal("0"), new BigDecimal("0") };
			coordenadasSol.add(sol);
			coordenadasSol.add(coordenadasOrdenadas.get(0));// Minimo
			coordenadasSol.add(coordenadasOrdenadas.get(2));// Maximo

			coordenadasOrdenadasSol = Posicion.ordenarCoordenadas(coordenadasSol);
			BigDecimal minXSol = coordenadasOrdenadasSol.get(0)[0];
			BigDecimal minYSol = coordenadasOrdenadasSol.get(0)[1];
			BigDecimal mediaXSol = coordenadasOrdenadasSol.get(1)[0];
			BigDecimal mediaYSol = coordenadasOrdenadasSol.get(1)[1];
			BigDecimal maxXSol = coordenadasOrdenadasSol.get(2)[0];
			BigDecimal maxYSol = coordenadasOrdenadasSol.get(2)[1];

			BigDecimal p1p2Sol = Posicion.distancia(minXSol, minYSol, mediaXSol, mediaYSol);
			BigDecimal p2p3Sol = Posicion.distancia(mediaXSol, mediaYSol, maxXSol, maxYSol);
			BigDecimal p1p3Sol = Posicion.distancia(minXSol, minYSol, maxXSol, maxYSol);

			if (p1p2Sol.add(p2p3Sol).compareTo(p1p3Sol) == 0)
				return true;//Deben estar alineados con el sol para que haya sequia
		}

		return false;
	}

	private Boolean optimo(List<BigDecimal[]> coordenadasOrdenadas) {
		BigDecimal minX = coordenadasOrdenadas.get(0)[0];
		BigDecimal minY = coordenadasOrdenadas.get(0)[1];
		BigDecimal mediaX = coordenadasOrdenadas.get(1)[0];
		BigDecimal mediaY = coordenadasOrdenadas.get(1)[1];
		BigDecimal maxX = coordenadasOrdenadas.get(2)[0];
		BigDecimal maxY = coordenadasOrdenadas.get(2)[1];

		BigDecimal p1p2 = Posicion.distancia(minX, minY, mediaX, mediaY);
		BigDecimal p2p3 = Posicion.distancia(mediaX, mediaY, maxX, maxY);
		BigDecimal p1p3 = Posicion.distancia(minX, minY, maxX, maxY);

		if (p1p2.add(p2p3).compareTo(p1p3) == 0) {
			List<BigDecimal[]> coordenadasSol = new ArrayList<BigDecimal[]>();
			List<BigDecimal[]> coordenadasOrdenadasSol = new ArrayList<BigDecimal[]>();

			BigDecimal[] sol = { new BigDecimal("0"), new BigDecimal("0") };
			coordenadasSol.add(sol);
			coordenadasSol.add(coordenadasOrdenadas.get(0));// Minimo
			coordenadasSol.add(coordenadasOrdenadas.get(2));// Maximo

			coordenadasOrdenadasSol = Posicion.ordenarCoordenadas(coordenadasSol);
			BigDecimal minXSol = coordenadasOrdenadasSol.get(0)[0];
			BigDecimal minYSol = coordenadasOrdenadasSol.get(0)[1];
			BigDecimal mediaXSol = coordenadasOrdenadasSol.get(1)[0];
			BigDecimal mediaYSol = coordenadasOrdenadasSol.get(1)[1];
			BigDecimal maxXSol = coordenadasOrdenadasSol.get(2)[0];
			BigDecimal maxYSol = coordenadasOrdenadasSol.get(2)[1];

			BigDecimal p1p2Sol = Posicion.distancia(minXSol, minYSol, mediaXSol, mediaYSol);
			BigDecimal p2p3Sol = Posicion.distancia(mediaXSol, mediaYSol, maxXSol, maxYSol);
			BigDecimal p1p3Sol = Posicion.distancia(minXSol, minYSol, maxXSol, maxYSol);

			if (p1p2Sol.add(p2p3Sol).compareTo(p1p3Sol) == 0)
				return false;//No deben estar alineados con el sol para que tenga un clima optimo
			return true;
		}

		return false;
	}

	private Boolean lluvia(List<BigDecimal[]> coordenadasOrdenadas) {
		BigDecimal minX = coordenadasOrdenadas.get(0)[0];
		BigDecimal minY = coordenadasOrdenadas.get(0)[1];
		BigDecimal mediaX = coordenadasOrdenadas.get(1)[0];
		BigDecimal mediaY = coordenadasOrdenadas.get(1)[1];
		BigDecimal maxX = coordenadasOrdenadas.get(2)[0];
		BigDecimal maxY = coordenadasOrdenadas.get(2)[1];

		BigDecimal p1p2 = Posicion.distancia(minX, minY, mediaX, mediaY);
		BigDecimal p2p3 = Posicion.distancia(mediaX, mediaY, maxX, maxY);
		BigDecimal p1p3 = Posicion.distancia(minX, minY, maxX, maxY);

		if (p1p2.add(p2p3).compareTo(p1p3) == 0)
			return false;// No debe ser colineal para ser un triangulo

		// El sol deberia estar dentro del triangulo para la condicion de lluvia
		BigDecimal pX = new BigDecimal("0");
		BigDecimal pY = new BigDecimal("0");
		Boolean pertenece = Posicion.pertenece(pX, pY, minX, minY, mediaX, mediaY, maxX, maxY);
		if (pertenece) {
			this.setIntensidadLluvia(p1p2.add(p2p3).add(p1p3));// Si llueve guardo intensidad que se relaciona al
																// perimetro segun enunciado
			return true;
		}

		return false;
	}
}
