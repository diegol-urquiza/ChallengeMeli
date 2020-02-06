package challengeMeli.Modelo;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import ch.obermuhlner.math.big.BigDecimalMath;

public class Posicion {
	private int dia;
	private Planeta planeta;
	private BigDecimal coordenadaX;
	private BigDecimal coordenadaY;

	// Constructor
	public Posicion(Planeta planeta) {
		this.setPlaneta(planeta);
	}
	
	public Posicion(Planeta planeta, int dia, BigDecimal x, BigDecimal y) {
		this.setPlaneta(planeta);
		this.dia = dia;
		this.coordenadaX = x;
		this.coordenadaY = y;
	}

	public Posicion(Planeta planeta, int diaActual, BigDecimal xOrigen, BigDecimal yOrigen, int diaOrigen) {
		this.setPlaneta(planeta);
		this.dia = diaActual;
		this.setCoordenadas(xOrigen, yOrigen, diaOrigen, diaActual);
	}

	// Getters y Setters
	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public Planeta getPlaneta() {
		return planeta;
	}

	public void setPlaneta(Planeta planeta) {
		this.planeta = planeta;
	}

	public BigDecimal getCoordenadaY() {
		return coordenadaY;
	}

	public void setCoordenadaY(BigDecimal coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	public BigDecimal getCoordenadaX() {
		return coordenadaX;
	}

	public void setCoordenadaX(BigDecimal coordenadaX) {
		this.coordenadaX = coordenadaX;
	}

	// Metodos
	/**
	 * A partir de un punto (x,y) de origen, el dia en que el planeta se encontraba
	 * en ese punto, puedo calcular las posiciones del dia actual pasado
	 */
	private void setCoordenadas(BigDecimal xOrigen, BigDecimal yOrigen, int diaOrigen, int diaActual) {
		BigDecimal gradoDesplazado, gradoRadian;
		MathContext mathContext = new MathContext(4, RoundingMode.DOWN);

		if (this.getPlaneta().getSentido() == null) {
			// TODO: Excepcion
		}

		BigDecimal diasTranscurridos = new BigDecimal(String.valueOf(diaActual))
				.subtract(new BigDecimal(String.valueOf(diaOrigen)));

		if (this.getPlaneta().getSentido() == SentidoRotacion.ANTIHORARIO)
			gradoDesplazado = this.getPlaneta().getVelocidad()
					.multiply((diasTranscurridos.multiply(new BigDecimal("1"))), mathContext);
		else
			gradoDesplazado = this.getPlaneta().getVelocidad()
					.multiply((diasTranscurridos.multiply(new BigDecimal("-1"))), mathContext);

		BigDecimal toRadian = BigDecimalMath.pi(mathContext).divide(new BigDecimal("180"), mathContext);

		gradoRadian = gradoDesplazado.multiply(toRadian, mathContext);

		BigDecimal cosBd = BigDecimalMath.cos(gradoRadian, mathContext);

		BigDecimal sinBd = BigDecimalMath.sin(gradoRadian, mathContext);

		this.coordenadaX = xOrigen.multiply(cosBd, mathContext).subtract(yOrigen.multiply(sinBd, mathContext));

		this.coordenadaY = xOrigen.multiply(sinBd, mathContext).add(yOrigen.multiply(cosBd, mathContext));
	}

	/**
	 * Ordenamos los puntos de los ejes de coordenadas para saber el inicial, el
	 * medio y el final
	 */
	public static List<BigDecimal[]> ordenarCoordenadas(List<BigDecimal[]> coordenadas) {
		BigDecimal[] max = new BigDecimal[2], min = new BigDecimal[2], media = new BigDecimal[2];
		List<BigDecimal[]> coordenadasOrdenadas = new ArrayList<BigDecimal[]>();

		// Minimo
		int iMin = 0;
		min[0] = coordenadas.get(0)[0];
		min[1] = coordenadas.get(0)[1];
		for (int i = 1; i < coordenadas.size(); i++) {
			if (min[0].compareTo(coordenadas.get(i)[0]) > 0) {
				min[0] = coordenadas.get(i)[0];
				min[1] = coordenadas.get(i)[1];
				iMin = i;
			} else if (min[0].compareTo(coordenadas.get(i)[0]) == 0) {
				if (min[1].compareTo(coordenadas.get(i)[1]) > 0) {
					min[0] = coordenadas.get(i)[0];
					min[1] = coordenadas.get(i)[1];
					iMin = i;
				}
			}
		}
		coordenadas.remove(iMin);

		// Maximo
		int iMax = 0;
		max[0] = coordenadas.get(0)[0];
		max[1] = coordenadas.get(0)[1];
		for (int i = 1; i < coordenadas.size(); i++) {
			if (max[0].compareTo(coordenadas.get(i)[0]) < 0) {
				max[0] = coordenadas.get(i)[0];
				max[1] = coordenadas.get(i)[1];
				iMax = i;
			} else if (max[0].compareTo(coordenadas.get(i)[0]) == 0) {
				if (max[1].compareTo(coordenadas.get(i)[1]) < 0) {
					max[0] = coordenadas.get(i)[0];
					max[1] = coordenadas.get(i)[1];
					iMax = i;
				}
			}
		}
		coordenadas.remove(iMax);

		media[0] = coordenadas.get(0)[0];
		media[1] = coordenadas.get(0)[1];

		coordenadasOrdenadas.add(min);
		coordenadasOrdenadas.add(media);
		coordenadasOrdenadas.add(max);

		return coordenadasOrdenadas;
	}

	/**
	 * Calcula la distancia entre dos puntos (x1,y1) y (x2,y2)
	 */
	public static BigDecimal distancia(BigDecimal x1, BigDecimal y1, BigDecimal x2, BigDecimal y2) {
		BigDecimal base = x2.subtract(x1);
		BigDecimal height = y2.subtract(y1);
		MathContext mathContext = new MathContext(4, RoundingMode.DOWN);

		BigDecimal baseBase = base.multiply(base, mathContext);

		BigDecimal heightHeight = height.multiply(height, mathContext);

		BigDecimal suma = baseBase.add(heightHeight);

		BigDecimal hypotenuse = BigDecimalMath.sqrt(suma, mathContext);

		return hypotenuse;
	}

	/**
	 * Determina si un punto (pX,pY) pertenece o no al triangulo que forman los
	 * vertices x1,y1,x2,y2,x3,y3 Utilizamos barycentric coordinate method
	 * https://en.wikipedia.org/wiki/Barycentric_coordinate_system
	 */
	public static boolean pertenece(BigDecimal pX, BigDecimal pY, BigDecimal x1, BigDecimal y1, BigDecimal x2,
			BigDecimal y2, BigDecimal x3, BigDecimal y3) {
		MathContext mathContext = new MathContext(4, RoundingMode.DOWN);

		BigDecimal y2y3 = y2.subtract(y3);
		BigDecimal x1x3 = x1.subtract(x3);
		BigDecimal x3x2 = x3.subtract(x2);
		BigDecimal y1y3 = y1.subtract(y3);
		BigDecimal y3y1 = y3.subtract(y1);

		BigDecimal y2y3x1x3 = y2y3.multiply(x1x3, mathContext);
		BigDecimal x3x2y1y3 = x3x2.multiply(y1y3, mathContext);

		BigDecimal det = y2y3x1x3.add(x3x2y1y3);

		BigDecimal pxx3 = pX.subtract(x3);
		BigDecimal pyy3 = pY.subtract(y3);

		BigDecimal y2y3pxx3 = y2y3.multiply(pxx3, mathContext);
		BigDecimal x3x2pyy3 = x3x2.multiply(pyy3, mathContext);

		BigDecimal lambda1 = (y2y3pxx3.add(x3x2pyy3)).divide(det, mathContext);

		BigDecimal y3y1pxx3 = y3y1.multiply(pxx3, mathContext);
		BigDecimal x1x3pyy3 = x1x3.multiply(pyy3, mathContext);

		BigDecimal lambda2 = (y3y1pxx3.add(x1x3pyy3, mathContext)).divide(det, mathContext);

		BigDecimal lambda3 = new BigDecimal("1").subtract(lambda1).subtract(lambda2);
		return (lambda1.compareTo(new BigDecimal("0")) >= 0) && (lambda2.compareTo(new BigDecimal("0")) >= 0)
				&& (lambda3.compareTo(new BigDecimal("0")) >= 0);
	}

}
