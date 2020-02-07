# ChallengeMeli

Es una manera rapida y sencilla de obtener por pantalla como estara el clima en un dia determinado.

## Comenzando 

Estas instrucciones te permitirán obtener una copia del proyecto en funcionamiento en tu máquina local para propósitos de desarrollo y pruebas.
Mira Despliegue para conocer como desplegar el proyecto.

### Requisitos
Para obtener el clima de un dia especifico, debemos en primer lugar descargar la aplicacion y seleccionar Run -> Run on Server

#### Ultimos cambios

git clone https://github.com/diegol-urquiza/ChallengeMeli

##### Configuracion y acceso al servicio Restful
Para obtener el clima de un dia especifico, solo nos basta con ejecutar el servicio restful que provee la aplicacion, accediendo al mismo desde el siguiente enlance.

http://localhost:8080/api-0.0.1-SNAPSHOT/clima?dia=<dia_buscado>

Donde el <dia_buscado> debe ser como condicion para un correcto funcionamiento, mayor a 1 y menor a 3650 (dia maximo que fue pronosticado).
Esto ultimo puede modificarse si se cambian las propiedades configuradas en el archivo de propiedades :
- Cantidad de dias por año
- Cantidad de años a pronosticar
https://raw.githubusercontent.com/diegol-urquiza/ChallengeMeli/master/api/src/challengeMeli/propiedades.png


##### Obteniendo el pronostico
Como se vio antes, si quisieramos entonces obtener entonces el clima para el dia 100:
(http://localhost:8080/api-0.0.1-SNAPSHOT/clima?dia=100)

y su respuesta seria:
(https://raw.githubusercontent.com/diegol-urquiza/ChallengeMeli/master/api/src/challengeMeli/dia100.png)




## Desarrollado con

* [Eclipse](https://www.eclipse.org/downloads/) - Plataforma utilizada
* [Maven](https://maven.apache.org/) - Gestion de dependencias

 
 
## Autor

* **Diego Urquiza**

 