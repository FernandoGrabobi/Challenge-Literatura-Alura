# LiterAlura: Catálogo de Libros Interactivo

## Descripción del Proyecto

**LiterAlura** es una aplicación de consola en Java diseñada para gestionar un catálogo de libros. Este proyecto combina el consumo de una API, la manipulación de datos JSON, la persistencia en una base de datos y la interacción directa con los usuarios. Es una solución integral para explorar libros y autores de interés de manera estructurada y dinámica.

## Objetivo

El objetivo principal de LiterAlura es proporcionar una herramienta interactiva que permita a los usuarios:
- Buscar libros y autores en una API de libros.
- Guardar los datos obtenidos en una base de datos.
- Consultar y filtrar la información almacenada.
- Visualizar los resultados directamente en la consola.
- Realizar al menos cinco acciones diferentes relacionadas con los libros y autores.

## Uso
1. Al iniciar, la aplicación mostrará un menú con las opciones disponibles.
2. Elige una opción y sigue las instrucciones que aparecen en pantalla.
3. Los resultados se mostrarán directamente en la consola.

## Funcionalidades Principales

### 1. **Buscar Libro por Título:**
   - Permite buscar libros en base a un título proporcionado por el usuario. Esta búsqueda consume la API [Gutenberg API](https://gutendex.com/), la cual devuelve un JSON con información de libros relacionados con el título ingresado. Los datos obtenidos de la API son serializados y almacenados en la base de datos para futuras consultas.

### 2. **Buscar Libro Registrado por Nombre de Autor:**
   - Permite buscar libros registrados en la base de datos utilizando el nombre de un autor.

### 3. **Listar Libros Registrados:**
   - Muestra todos los libros almacenados en la base de datos.

### 4. **Listar Autores Registrados:**
   - Muestra todos los autores almacenados en la base de datos.

### 5. **Listar Autores Vivos en un Año Determinado:**
   - Permite filtrar y mostrar los autores que estaban vivos en un año específico.

### 6. **Listar Libros por Idioma:**
   - Permite listar los libros registrados que estén disponibles en un idioma específico.
   - 
## Instalación

### Requisitos Previos

- Java 17 o superior.
- Maven 3.8.1 o superior.
- Base de datos PostgreSQL configurada.
- Acceso a la API de libros (proporcionar URL y credenciales si aplica).

### Pasos de Instalación

1. Clona este repositorio:
   ```bash
   git clone https://github.com/tu-usuario/literalura.git
   cd literalura

### Creditos
Proyecto desarrollado como parte del desafío II de programación de Alura. ¡Gracias por esta oportunidad para aprender y crecer!
