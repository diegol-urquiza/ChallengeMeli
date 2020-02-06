package challengeMeli.Modelo;

import java.math.BigDecimal;

public class Planeta {
	private String nombre;
	private BigDecimal velocidad;
	private SentidoRotacion sentido;

	// Constructor
	public Planeta(String nombre, BigDecimal velocidad, SentidoRotacion sentido) {
		this.nombre = nombre;
		this.velocidad = velocidad;
		this.sentido = sentido;
	}

	// Getters y Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(BigDecimal velocidad) {
		this.velocidad = velocidad;
	}

	public SentidoRotacion getSentido() {
		return sentido;
	}

	public void setSentido(SentidoRotacion sentido) {
		this.sentido = sentido;
	}

}
