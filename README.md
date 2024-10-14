# Mutant Detector API

Este proyecto permite detectar si un humano es mutante basado en su secuencia de ADN. 
La API recibe una secuencia de ADN a través de un array de cadenas (strings) y determina si 
pertenece a un mutante. Además, se guardan los resultados en una base de datos y se exponen 
estadísticas de las verificaciones realizadas.

## Tabla de Contenido
- [Descripción del Proyecto](#Descripcion)
- [Tecnologías](#tecnologías)
- [Instalación](#instalación)
- [Uso](#uso)
- [Ejemplos](#ejemplos)
- [Pruebas](#pruebas)
- [Contribuciones](#contribuciones)

## Descripción
El proyecto consiste en una API REST que:
- Permite verificar si un humano es mutante o no mediante una secuencia de ADN.
- Utiliza una base de datos para almacenar los ADN verificados y expone un servicio de estadísticas.

## Tecnologías
- Java
- Spring Boot (Framework para la API)
- H2 Database (Base de datos en memoria para desarrollo)
- Gradle (Compilación y gestión de dependencias)
- JUnit (Pruebas automáticas)
- Docker (Contenedores y despliegue)
- Cloud Platform: (Render)

## Instalación y Despliegue

### Requisitos previos
- Java 17
- Gradle
- Docker
- Cuenta en Render o plataforma de despliegue.

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/Juliorivasz/ProgramacionIII-mutants.git
   cd ProgramacionIII-mutants
   
2. **Compilar el proyecto:**
    ```bash
   gradle build
   
3. **Ejecutar el proyecto:**
   ```bash
   gradle bootRun
   
4. **Ejecutar con Docker (Opcional):**
   ```bash
   docker build -t mutant-detector .
   docker run -p 8080:8080 mutant-detector

## Despliegue en la nube

1. **Despliegue en Render:**  
Puedes enlazar tu repositorio con Render y desplegar automáticamente tras cada commit.

## Uso

### La API está disponible en <a href=http://localhost:8080/api/mutant>Local</a> o <a href=https://programacioniii-mutants-1.onrender.com/api>Render</a>
1. **Detectar Mutantes:**  
Con la ruta **/mutant/**  
Puedes enviar una solicitud POST con el siguiente formato:
   ```json
       {
         "dna": [
           "ATGCGA",
           "CAGTGC",
           "TTATGT",
           "AGAAGG",
           "CCCCTA",
           "TCACTG"
         ]
       }

La API devolverá un código de estado **HTTP 200 (mutante) o 403 (no mutante)** 
dependiendo del ADN proporcionado.

2. **Estadisticas**:  
Con la ruta **/stats**  
Usando el metodo GET  
Obtienes las estadisticas de los ADN verificados.  
   ```json
   {
    "count_mutant_dna": 40,
    "count_human_dna": 100,
    "ratio": 0.4
   }
- **count_mutant_dna:** Número de ADN mutantes detectados. 
- **count_human_dna:** Número de ADN humanos detectados. 
- **ratio:** Proporción entre mutantes y humanos.

3. **Base de datos H2**
- Para ver la base de datos h2, la url es **/h2-console**

4. **Swagger OPEN API**
- La documentacion de la API se puede ver, la url es **/swagger-ui.html**


## Ejemplos

1. **Mutante**

    ```json
    {
      "dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACTG"
      ]
    }
La respuesta de la API sera el codigo de estado: **200-OK**

2. **No-Mutante**

    ```json
   {
    "dna": [
      "TTAAAA",
      "CAGTGC",
      "TTATTT",
      "AGACGG",
      "GCGTCA",
      "TCACTG"
      ]
   }
La respuesta de la API sera el codigo de estado: **403-Forbidden**

## Pruebas

1. Para ejecutar las pruebas unitarias, usa el siguiente comando:
    ````bash
    gradlew test

## Diagrama de Secuencia y Arquitectura

### Diagrama de Secuencia
- El siguiente diagrama muestra el flujo de interacción entre el cliente y la API:
   ```
   Cliente -> API: POST /mutant/
   API -> Detector: isMutant(dna)
   Detector -> BD: Guardar resultado
   BD -> API: Confirmación
   API -> Cliente: Resultado (200 OK o 403 Forbidden)

### Arquitectura del sistema
1. El sistema está compuesto por los siguientes componentes:

- Cliente: Envía solicitudes HTTP a la API. 
- API REST (Spring Boot): Procesa las solicitudes y coordina la verificación del ADN. 
- Base de Datos: Almacena los resultados de ADN verificados. 
- Servicio de Estadísticas: Proporciona estadísticas de las verificaciones.

## Contribuciones

**Las contribuciones son bienvenidas. Sigue estos pasos para contribuir:**
1. Haz un fork del repositorio.
2. Crea una nueva rama (git checkout -b feature/nueva-característica).
3. Realiza los cambios necesarios y realiza un commit (git commit -m 'Añadir nueva característica').
4. Envía un pull request.


