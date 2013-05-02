image-cache-server
==================

English Version
===============

Install instructions:
---------------------

* Generate the binary file with maven executing the command `mvn clean compile assembly:single`
* Copy the files **image-cache-server.sh or image-cache-server.cmd** and **image-cache-server.jar** to the directory where you want to run it and create the **logs** folder
* Edit the file **image-cache-server.sh** in the second line where it says `CONFIG="server.port=8181 mongo.url=localhost mongo.dbname=storage"` and modify, if needed, the properties (see table below to learn the properties meaning).
* To run the server execute `./image-cache-server.sh start`, the log will be written in **logs/image-cache-server.log**
* To stop the server execute the command `./image-cache-server.sh stop`

Property     | Description                                          |Default                              
:-----------:|:----------------------------------------------------:|:------
 server.port |Port where the HTTP server will be listening          |8181
 mongo.url   |URL of MongoDB eg localhost:27017                     |localhost
 mongo.dbname|Name of the DB where the images are persisted         |storage

 Mode of use:
 ------------
* To upload a new image invoke, through HTTP, the following URL `http://<image-cache-server-url>/upload?source=<image-source-url>` where **image-cache-server-url** is the URL of the server, eg: **localhost:8181**, and **image-source-url** is the URL where the image to be cached is found, eg: **http://localhost/assets/image.jpeg**
* To retrieve a cached image invoke the following URL `http://<image-cache-server-url>/static/<imageId> where **imageId** matches the relative path of the uploaded image, eg: if the image is uploaded from **http://localhost/assets/image.jpeg**, then the ID will be **/assets/image.jpeg** and the URL to get the image will be **http://localhost:8181/static/assets/image.jpeg**

Spanish Version
===============

 Instructivo de instalación:
---------------------------

* Generar el binario con maven  ejecutando el comando `mvn clean compile assembly:single`
* Copiar los archivos **image-cache-server.sh o image-cache-server.cmd** e **image-cache-server.jar** al directorio donde se desea instalar y crear un directorio llamado **logs**
* Editar el archivo **image-cache-server.sh**  en la 2da linea donde dice  `CONFIG="server.port=8181 mongo.url=localhost mongo.dbname=storage"` y editar de ser necesario las properties que correspondan* (ver cuadro debajo para saber el significado de cada property).
* Para levantar el servidor ejecutar  `./image-cache-server.sh start` los logs se van a estar escribiendo en **logs/image-cache-server.log**
* Para bajar el servidor ejecutar el comando `./image-cache-server.sh stop`

Property     | Descripción                                          |Default                              
:-----------:|:----------------------------------------------------:|:------
 server.port |Puerto donde va a estar eschuchando el servidor http  |8181
 mongo.url   |url de MongoDB ej localhost:27017                     |localhost
 mongo.dbname|Nombre de la DB donde se va a persistir las imagenes  |storage

 Modo de uso:
 ------------
* Para subir una nueva imagen invocar por http a la siguiente url `http://<image-cache-server-url>/upload?source=<image-source-url>` donde **image-cache-server-url** es la url del servidor Ej: **localhost:8181**, e **image-source-url** es la url de donde se quiere cachear la imagen Ej: **http://localhost/assets/image.jpeg**

* Para acceder a una imagen cacheada invocar a la siguiente url `http://<image-cache-server-url>/static/<imageId> donde **imageId** coincide con el path relativo de la imagen subida, Ej: si se subio una imagen desde **http://localhost/assets/image.jpeg** entonces el id será  **/assets/image.jpeg** y la url para obtener la imagen cacheada será **http://localhost:8181/static/assets/image.jpeg**
