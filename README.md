image-cache-server
==================

Instructivo de instalación:
---------------------------

* Generar el binario con maven  ejecutando el comando `mvn clean compile assembly:single`
* Copiar los archivos **image-cache-server.sh** e **image-cache-server.jar** al directorio donde se desea instalar y crear un directorio llamado **logs**
* Editar el archivo **image-cache-server.sh**  en la 2da linea donde dice  `CONFIG="server.port=8181 mongo.url=localhost mongo.dbname=storage"` y editar de ser necesario las properties que correspondan* (ver cuadro debajo para saber el significado de cada property).
* Para levantar el servidor ejecutar  `./image-cache-server.sh start` los logs se van a estar escribiendo en **logs/image-cache-server.log**
* Para bajar el servidor ejecutar el comando `./image-cache-server.sh stop`

Property     | Descripción                                          |Default                              
:-----------:|:----------------------------------------------------:|:------
 server.port |Puerto donde va a estar eschuchando el servidor http  |8181
 mongo.url   |url de MongoDB ej localohost:27017                    |localhost
 mongo.dbname|Nombre de la DB donde se va a persistir las imagenes  |storage

 Modo de uso:
 ------------
 * Para subir una nueva imagen invocar por http a la siguiente url `http://<image-cache-server-url>/upload?source=<image-source-url>` donde **image-cache-server-url** es la url del servidor Ej: **localhost:8181**, e **image-source-url** es la url de donde se quiere cachear la imagen Ej: **http://localhost/assets/image.jpeg**

 *Para acceder a una imagen cacheada invocar a la siguiente url `http://<image-cache-server-url>/static/<imageId> donde **imageId** conicide con el path relativo de la imagen subida, Ej: si se subio una imagen desde **http://localhost/assets/image.jpeg** entonecs el id será  **/assets/image.jpeg** y la url para obtener la imagen cacheada será **http://localhost:8181/static/assets/image.jpeg**
