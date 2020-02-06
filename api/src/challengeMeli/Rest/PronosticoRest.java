package challengeMeli.Rest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import challengeMeli.Main;
import challengeMeli.Modelo.Pronostico;

@Path("clima")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class PronosticoRest {

	@GET
	public Response getPronostico(@QueryParam("dia") Integer dia) {
		// ejemplo ---> http://localhost:8080/api-0.0.1-SNAPSHOT/clima?dia=1
		try {
			if (dia == null) {
				throw new Exception("Porfavor ingrese el dia que desea consultar");
			}
			if (dia.compareTo(0) <= 0) {
				throw new Exception("Porfavor ingrese un dia valido");
			}

			JsonParser parser = new JsonParser();
			FileReader fr = null;
			try {
				String fileName = (System.getProperty("user.home") + "\\pronostico.json");
				fr = new FileReader(fileName);
			} catch (FileNotFoundException e) {
				System.err.println("Se produjo al intentar leer el archivo de pronosticos");
				e.printStackTrace();
			}
			JsonElement clima = parser.parse(fr);

			JsonArray jsonArray = clima.getAsJsonArray();
			JsonElement elm = jsonArray.get(dia - 1);
			JsonObject obj = elm.getAsJsonObject();
			JsonElement diaResul = obj.get("dia");
			JsonElement climaResul = obj.get("clima");

			Map<String, String> pronostico = new HashMap<String, String>();
			pronostico.put("dia", diaResul.toString());
			pronostico.put("clima", climaResul.toString());

			
			FileReader frResumen = null;
			try {
				String fileName = (System.getProperty("user.home") + "\\resumen.txt");
				frResumen = new FileReader(fileName);
			} catch (FileNotFoundException e) {
				System.err.println("Se produjo al intentar leer el archivo de pronosticos");
				e.printStackTrace();
			}


			System.out.println("-----------------------------------------------------------------------\n");
			System.out.println("Resumen: \n");
		       BufferedReader b = new BufferedReader(frResumen);
		       String cadena;
		        while((cadena = b.readLine()) != null) {
		            System.out.println(cadena);
		        }
            frResumen.close();

			
			
			return Response.ok(pronostico, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {

			return Response.status(404).entity(e.getMessage()).build();
		}

	}

}
