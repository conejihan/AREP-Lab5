# Taller de Modularización con Virtualización e Introducción a Docker y a AWS
## Descripción
En el Taller implemento una arquitectura la cual consiste en un balanceador de carga en donde se usa el método de RoundRobin. Este balanceador de carga se encarga de realizar las respectivas peticiones a los LogService, los cuales se encargan de realizar la conexión con las bases de datos montada en MongoDB, que almacena todos los mensajes entrantes. En la arquitectura del programa se encuentran en total cinco contenedores, uno para el RoundRobin, tres para el LogService y uno para las bases de datos en Mongo.

## Prerrequisitos
Para la realización y ejecución tanto del programa como de las pruebas de este, se requieren ser instalados los siguientes programas:
* Maven
* Git
* Docker

## Instalación
Para descargar el proyecto de GitHub, primero debemos clonar este repositorio.

```
git clone https://github.com/Skullzo/AREP-Lab5.git
```

## Ejecución
Para compilar el proyecto se utilizara el siguiente comando:

```
mvn package
```
## Pruebas
Para realizar las pruebas se ejecuta el siguiente:

```
mvn test
```


----------

### Localhost

Para probar ahora el correcto funcionamiento del Docker de manera local o localhost del programa RoundRobin, primero ejecutamos los siguientes comandos en orden.
```
docker build --tag lab5/roundrobin .
docker images
docker run -d -p 8000:6000 --name balanceadordecarga lab5/roundrobin
```

Ahora, se ejecuta el siguiente comando para comprobar que el Docker de RoundRobin se está ejecutando.
```
docker ps
```

Ahora, para probar de manera local o localhost el programa LogService con sus tres respectivos logs, ejecutamos los siguientes comandos en orden.
```
docker build --tag lab5/logservice .
docker images
```

Ahora, para correr los tres logs en puertos diferentes, se ejecutan de la siguiente manera.
```
docker run -d -p 8001:6000 --name logservice1 arep-lab5/logservice
docker run -d -p 8002:6000 --name logservice2 arep-lab5/logservice
docker run -d -p 8003:6000 --name logservice3 arep-lab5/logservice
```

Para comprobar que la página web ha sido desplegada con éxito, se ingresa en el navegador la siguiente URL: ```localhost:8000```.

----------

### AWS

Antes de iniciar a utilizar AWS, primero se debe subir cada uno de los contenedores creados a un repositorio. Para realizar esto, primero se creó el primer repositorio en Docker Hub, como se ve a continuación.

![img](https://github.com/conejihan/AREP-Lab5/blob/master/img/AWS1.PNG)

Luego, se ejecutaron los siguientes comandos en orden para poder subir los contenedores.
```
docker tag lab5/roundrobin conejihan7/arep-lab5
docker push conejihan7/arep-lab5:latest
```
Luego de ejecutar los comandos en orden, se observa que el contenedor ha sido subido satisfactoriamente al repositorio.

![img](https://github.com/conejihan/AREP-Lab5/blob/master/img/AWS2.PNG)

Para comprobar que se ha subido satisfactoriamente, se accede nuevamente al repositorio creado en Docker Hub, y se verifica que el contenedor se encuentre dentro de dicho repositorio.

Para iniciar a desplegar el contenedor en una máquina virtual alojada en AWS, primero se selecciona el tipo de máquina virtual que se utilizará, en este caso, se utilizará Amazon Linux 2 AMI (HVM), SSD Volume Type. Para utilizarla, se realiza clic en el botón Seleccionar.

![img](https://github.com/conejihan/AREP-Lab5/blob/master/img/AWS4.png)

Ahora se selecciona el tipo de instancia. Para esta máquina virtual, se selecciona **t2.micro**, la cual es apta para la capa gratuita. luego de seleccionarla, se realiza clic en **Revisar y lanzar**.

![img](https://github.com/conejihan/AREP-Lab5/blob/master/img/AWS5.png)

A continuación se muestra la instancia para verificar la máquina virtual que está a punto de ser lanzada. Para lanzarla, se realiza clic en el botón **Lanzar**.

![img](https://github.com/conejihan/AREP-Lab5/blob/master/img/AWS6.png)

Luego, se procede a crear un nuevo par de llaves para poder acceder a la máquina virtual desde el computador en cuestión. Para esto se selecciona la opción Crear un nuevo par de llaves y se escribe el nombre del par de claves, que en este caso es **AREP-Lab5KeyPair**. Para descargar la llave, se realiza clic en el botón Descargar par de llaves.

Después de descargar el par de llaves, ahora se procede a realizar clic en el botón **Lanzar instancias**.

Se mostrara que la instancia ha sido lanzada con éxito. Para verificar que esta ha sido lanzada, se realiza clic en el botón Ver instancias.

Para conectarse a la instancia, se realiza clic en el botón Acciones, para posteriormente realizar clic en el botón Conectar.

Para realizar la respectiva conexión con la instancia, se realiza clic en el botón Cliente SSH, que es el medio en el cual se realizará la conexión con la instancia.

Ahora, se ejecuta el SSH desde el computador con el cual se desea realizar la conexión con la instancia, y se ejecuta el siguiente comando.
```
ssh -i "AREP-Lab5KeyPair.pem" ec2-user@ec2-54-167-15-7.compute-1.amazonaws.com
```
Luego de ejecutar el comando, se muestra que la conexiLn con la instancia ha sido exitosa. Para comprobar que la instancia se encuentra corriendo en un entorno Linux, se ejecutan los comandos ```pwd``` y ```whoami``` para comprobarlo como se muestra a continuación.

Para iniciar con el procedimiento de descarga de Docker en la máquina virtual, se ejecuta el siguiente comando.
```
sudo yum update -y
```
Para iniciar la instalación de Docker, se ejecuta el siguiente comando.
```
sudo yum install docker
```
Luego de ejecutarlo, se inicia la descarga e instalación de Docker. Para poder iniciar con la descarga e instalación, se escribe yes después de haber sido ejecutado el comando.

Luego de finalizar la instalación de Docker, se ejecuta el siguiente comando para configurar el respectivo usuario en el grupo de docker para no tener que ingresar “sudo” cada vez que invoca un comando.
```
sudo usermod -a -G docker ec2-user
```
Para comprobar que el comando sirve, se sale y se ingresa nuevamente a la máquina virtual ejecutando los siguientes comandos en orden.
```
exit
ssh -i "AREP-Lab5KeyPair.pem" ec2-user@ec2-54-167-15-7.compute-1.amazonaws.com
```
Luego de haber ingresado a la máquina nuevamente, para iniciar el servicio de Docker en la máquina virtual, y para ver que Docker ya se encuentra en funcionamiento, se ejecutan los siguientes comandos en orden.
```
sudo service docker start
docker images
```


Para agregar reglas a los puertos para poder ejecutar el contenedor desde la máquina virtual, se procede a realizar clic en el botón Seguridad.

Luego, en Grupos de seguridad, se realiza clic en el hipervínculo para poder ingresar al mismo.

Para agregar reglas de entrada para poder tener acceso a los puertos de los contenedores, se realiza clic en el botón Editar reglas de entrada.

Para agregar una o varias reglas de entrada, se realiza clic en el botón de Agregar regla.

Luego de agregar todos los puertos de los contenedores, que son: 8000, 8001, 8002, 8003 y 27017, se realiza clic en el botón Guardar reglas.

Para correr el contenedor de Docker en la máquina virtual, se ejecuta el siguiente comando.
```
docker run -d -p 8000:6000 --name balanceadordecarga skullzo/arep-lab5
```
Luego de ejecutar el comando, la máquina virtual hace los respectivos pulls al repositorio de Docker ```arep-lab5```. Para comprobar que la máquina virtual ha realizado el pull de manera exitosa, se ejecuta el comando ```docker images``` para poder visualizar el contenedor.

Ahora, para comprobar que el contenedor se encuentra activo desde la máquina virtual, se ingresan tres mensajes para probar el funcionamiento del programa, que son Mensaje 1, Mensaje 2 y Mensaje 3 respectivamente.
