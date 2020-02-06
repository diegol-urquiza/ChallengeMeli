package challengeMeli.Servlet;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.swing.JOptionPane;

import com.google.gson.Gson;

import challengeMeli.Modelo.EstadoClima;
import challengeMeli.Modelo.Planeta;
import challengeMeli.Modelo.Posicion;
import challengeMeli.Modelo.Pronostico;
import challengeMeli.Modelo.SentidoRotacion;

public class MyContextListener 
implements ServletContextListener{

@Override
public void contextDestroyed(ServletContextEvent arg0) {
//do stuff
}

@Override
public void contextInitialized(ServletContextEvent arg0) {
	/* Instancio Planetas */
	Planeta vulcano = new Planeta("Vulcano", new BigDecimal("5"), SentidoRotacion.ANTIHORARIO);
	Planeta ferengi = new Planeta("Ferengi", new BigDecimal("1"), SentidoRotacion.HORARIO);
	Planeta betasoide = new Planeta("Betasoide", new BigDecimal("3"), SentidoRotacion.HORARIO);
	
	Properties config = new Properties();
	InputStream configInput = null;
	Integer diasPorAnio = null;
 
	 try{
         configInput = getClass().getClassLoader().getResourceAsStream("/config.properties");
         config.load(configInput);
         diasPorAnio = Integer.parseInt(config.getProperty("cantidad_dias_anio"));
     } catch(Exception e){
         JOptionPane.showMessageDialog(null, "Error cargando configuracion\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
     }

	/*
	 * Instancio Posiciones 
	 * A partir del dia 1 donde asumimos una configuracion base a
	 * partir de su distancia al sol ya que el ejercicio no lo menciona
	 */
	Posicion vulcanoPosInit = new Posicion(vulcano, 1, new BigDecimal("0"), new BigDecimal("1000"));
	Posicion ferengiPosInit = new Posicion(ferengi, 1, new BigDecimal("0"), new BigDecimal("500"));
	Posicion betasoidePosInit = new Posicion(betasoide, 1, new BigDecimal("0"), new BigDecimal("2000"));

	List<Posicion[]> posiciones = new ArrayList<Posicion[]>();
	Posicion[] posicionDiaUno = { vulcanoPosInit, ferengiPosInit, betasoidePosInit };
	posiciones.add(posicionDiaUno);

	for (Integer j = 2; j <= (10 * diasPorAnio); j++) {
		/*Utilizo constructor para los dias diferentes al primero, 
		 * aqui le envio las posiciones iniciales con el dia correspondiente,
		 * y ademas el dia buscado actual*/
		Posicion vulcanoPos = new Posicion(vulcano, j, posiciones.get(j - 2)[0].getCoordenadaX(),
				posiciones.get(j - 2)[0].getCoordenadaY(),j-1);
		Posicion ferengiPos = new Posicion(ferengi, j, posiciones.get(j - 2)[1].getCoordenadaX(),
				posiciones.get(j - 2)[1].getCoordenadaY(),j-1);
		Posicion betasoidePos = new Posicion(betasoide, j, posiciones.get(j - 2)[2].getCoordenadaX(),
				posiciones.get(j - 2)[2].getCoordenadaY(),j-1);

		Posicion[] posicionDia = { vulcanoPos, ferengiPos, betasoidePos };
		posiciones.add(posicionDia);
	}

	// Imprimo por pantalla json
	// System.out.println("Json Posiciones: ");
	// System.out.println(new Gson().toJson(posiciones));

	// Grabo archivo Json
	try {
	    String fileName = (System.getProperty("user.home") + "\\posiciones.json");
		FileWriter file = new FileWriter(fileName);
		file.write(new Gson().toJson(posiciones));
		file.flush();
		file.close();

	} catch (IOException e) {
		System.err.println("Se produjo al intentar grabar el archivo de posiciones");
	}

	/* Instancio Pronosticos */
	List<Pronostico> pronosticos = new ArrayList<Pronostico>();
	for (Integer i = 1; i <= (10 * diasPorAnio); i++) {
		BigDecimal xP1 = posiciones.get(i - 1)[0].getCoordenadaX();
		BigDecimal yP1 = posiciones.get(i - 1)[0].getCoordenadaY();
		BigDecimal xP2 = posiciones.get(i - 1)[1].getCoordenadaX();
		BigDecimal yP2 = posiciones.get(i - 1)[1].getCoordenadaY();
		BigDecimal xP3 = posiciones.get(i - 1)[2].getCoordenadaX();
		BigDecimal yP3 = posiciones.get(i - 1)[2].getCoordenadaY();
		Pronostico p = new Pronostico(i, xP1, yP1, xP2, yP2, xP3, yP3);// Para saber un pronostico es necesario
																		// conocer la posicion de cada planeta en un
																		// dia especifico
		pronosticos.add(p);
	}

	// Imprimo por pantalla json
	// System.out.println("Json Pronosticos");
	// System.out.println(new Gson().toJson(pronosticos));

	// Grabo archivo Json
	try {
	    String fileName = (System.getProperty("user.home") + "\\pronostico.json");
		FileWriter file = new FileWriter(fileName);
		file.write(new Gson().toJson(pronosticos));
		file.flush();
		file.close();

	} catch (IOException e) {
		System.err.println("Se produjo al intentar grabar el archivo de pronosticos");
	}

	// Corte de control para Periodos
	int periodosSequia = 0;
	int periodosOptimo = 0;
	int periodosLluvia = 0;
	int periodosIndefinido = 0;
	BigDecimal mayorLluvia = new BigDecimal("0");
	int mayorLluviaDia = 0;
	int contador = 0;

	while (contador < pronosticos.size()) {
		EstadoClima estadoActual = pronosticos.get(contador).getClima();
		while (contador < pronosticos.size() && pronosticos.get(contador).getClima() == estadoActual) {
			contador++;
		}
		switch (estadoActual) {
		case SEQUIA:
			periodosSequia++;
			break;
		case OPTIMO:
			periodosOptimo++;
			break;
		case LLUVIA:
			periodosLluvia++;
			if (mayorLluvia.compareTo(pronosticos.get(contador - 1).getIntensidadLluvia()) < 0) {
				mayorLluvia = pronosticos.get(contador - 1).getIntensidadLluvia();
				mayorLluviaDia = contador;
			}
			break;
		case INDEFINIDO:
			periodosIndefinido++;
			break;
		}
	}

	// Imprimir lo solicitado por ejercicio
	// Grabo archivo Json
	try {
	    String fileName = (System.getProperty("user.home") + "\\resumen.txt");
		FileWriter writer = new FileWriter(fileName);
 
		writer.write("Periodos de sequia: " + periodosSequia);
		writer.write("\r\n"); 
		writer.write("Periodos de lluvia: " + periodosLluvia);
		writer.write("\r\n"); 
		writer.write("Dia de mayor lluvia: " + pronosticos.get(mayorLluviaDia).getDia());
		writer.write("\r\n"); 
		writer.write("Periodos de condiciones optimas de presion y temperatura: " + periodosOptimo );
		writer.write("\r\n"); 
		writer.close();

	} catch (IOException e) {
		System.err.println("Se produjo al intentar grabar el archivo de Resumen");
	}
}
}