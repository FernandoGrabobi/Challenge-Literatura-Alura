package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.service.LibroService;

import java.time.LocalDate;
import java.util.*;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private AutorService autorService;
    private LibroService libroService;

    public Principal(AutorService autorService, LibroService libroService) {
        this.autorService = autorService;
        this.libroService = libroService;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                     Elíje la opción a través de su número:
                     \s
                     1- Buscar libro por titulo
                     2- Listar libros registrados
                     3- Listar autores registrados
                     4- Listar autores vivos en un determinado año
                     5- Listar libros por idioma
                     6- Mostrar estadísticas de libros por idioma
                     7- Top 10 libros más descargados
                     8- Mostrar estadísticas de descargas por autor
                     \s
                     0 - Salir
                    \s""";
            System.out.println(menu);

            System.out.println("Ingrese una opción valida: ");
            String entrada = teclado.nextLine();

            try {
                opcion = Integer.parseInt(entrada);

                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo();
                        break;
                    case 2:
                        listarTodosLosLibros();
                        break;
                    case 3:
                        autoresRegistrados();
                        break;
                    case 4:
                        obtenerAutoresVivosEn();
                        break;
                    case 5:
                        buscarLibrosPorIdioma();
                        break;
                    case 6:
                        mostrarEstadisticasDeLibros();
                        break;
                    case 7:
                        buscarTop10librosDescargados();
                        break;
                    case 8:
                        mostrarEstadisticasDescargasPorAutor();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            }catch (NumberFormatException e){
                System.out.println("Error: Debe ingresar un número válido.\n");
            }
        }

    }

    private void mostrarEstadisticasDescargasPorAutor() {
        Map<Autor, DoubleSummaryStatistics> estadisticasPorAutor = libroService.obtenerEstadisticasDescargasPorAutor();

        if (estadisticasPorAutor == null || estadisticasPorAutor.isEmpty()) {
            System.out.println("No hay datos suficientes para mostrar estadísticas.\n");
            return;
        }

        System.out.println("Estadísticas de descargas por autor:\n");

        final int[] index = {1};

        estadisticasPorAutor.forEach((autor, stats) -> {
            System.out.println("--------AUTOR#" + index[0] + "--------");
            System.out.println("  " + (autor != null ? autor.getNombre() : "Sin autor registrado"));
            System.out.println("  Total de descargas: " + stats.getSum());
            System.out.println("  Promedio de descargas: " + stats.getAverage());
            System.out.println("  Máximo de descargas: " + stats.getMax());
            System.out.println("  Mínimo de descargas: " + stats.getMin());
            System.out.println("--------------------\n");

            index[0]++;
        });
    }

    private void buscarTop10librosDescargados() {
        List<Libro> topLibros = libroService.buscarTop10librosDescargados();

        final int[] index = {1};

        topLibros.forEach(libro -> {
            System.out.println("--------LIBRO#" + index[0] + "--------\n");
            System.out.println(libro);
            System.out.println("---------------------\n");

            index[0]++;
        });
    }

    private void mostrarEstadisticasDeLibros() {
        List<Object[]> estadisticas = libroService.obtenerEstadisticasPorIdioma();

        if(estadisticas.isEmpty()){
            System.out.println("No hay estadísticas disponibles sobre los libros por idioma T.T\n");
        }else {
            System.out.println("Estadísticas de libros por idioma en la base de datos: \n");

            estadisticas.stream()
                    .map(estadistica ->{
                        TipoIdioma idioma =(TipoIdioma) estadistica[0];
                        Long cantidad = (Long) estadistica[1];
                        return "Idioma: " + idioma.name() + " - Cantidad: " + cantidad;
                    })
                    .forEach(System.out::println);
        }
        System.out.println();

    }

    private void obtenerAutoresVivosEn() {
        System.out.println("Indica el año, para verificar los autores vivos: ");
        int anio = -1;
        int anioActual = LocalDate.now().getYear();

        while (anio < 0 || anio > anioActual){
            try {
                anio = Integer.parseInt(teclado.nextLine());
                if (anio < 0 || anio > anioActual) {
                    System.out.println("Error T.T El año debe estar entre 0 y " + anioActual + ". Inténtalo nuevamente.\n");
                }
            }catch (NumberFormatException e){
                System.out.println("Error T.T Debes ingresar un año válido :D\n");
            }
        }
    List<Autor> autoresVivos = autorService.obtenerAutoresVivosEn(anio);

        if(autoresVivos.isEmpty()){
            System.out.println("No hay autores vivos en el año: " + anio + "\n");
        }else {
            System.out.println("Autores vivos en el año " + anio + ":\n");

            final int[] index = {1};

            autoresVivos.forEach(autor -> {
                System.out.println("--------AUTOR#" + index[0] + "--------\n");
                System.out.println(autor);
                System.out.println("------------------------\n");

                index[0]++;
            });
        }
    }

    private void buscarLibrosPorIdioma() {
        System.out.println("Seleccione un idioma: ");

        for (int i = 0; i < TipoIdioma.values().length; i++) {
            System.out.println((i + 1) + " - " + TipoIdioma.values()[i].name());
        }
        System.out.println("0 - Volver al menú principal\n");

        int opcion = -1;

        while(opcion < 0 || opcion > TipoIdioma.values().length){
            try{
                opcion = Integer.parseInt(teclado.nextLine());

                if(opcion == 0){
                    return;
                }

                if(opcion < 0 || opcion > TipoIdioma.values().length){
                    System.out.println("Opción invalida. Por favor ingresa un número entre 0 y " + TipoIdioma.values().length);
                }
            }catch (NumberFormatException e){
                System.out.println("Entrada invalida T.T Por favor ingresa un número entre 0 y " + TipoIdioma.values().length);
            }
        }
        TipoIdioma idiomaSeleccionado = TipoIdioma.values()[opcion - 1];
        listarLibrosPorIdioma(idiomaSeleccionado);
    }

    private void listarLibrosPorIdioma(TipoIdioma idioma){
        List<Libro> libros = libroService.obtenerLibrosPorIdioma(idioma);

        if(libros.isEmpty()){
            System.out.println("No hay libros disponibles en el idioma: " + idioma.name() + " T.T\n");
        }else {
            System.out.println("Libros en " + idioma.name() + ":\n");

            final int[] index = {1};

            libros.forEach(libro -> {
                System.out.println("--------LIBRO#" + index[0] + "--------\n");
                System.out.println(libro);
                System.out.println("---------------------\n");

                index[0]++;
            });
        }
    }

    private void listarTodosLosLibros() {
        List<Libro> libros = libroService.obtenerTodosLosLibros();

        if(libros.isEmpty()){
            System.out.println("No hay libros registrados T.T\n");
        }else {
            System.out.println("Libros REGISTRADOS: \n");

            final int[] index = {1};

            libros.forEach(libro -> {
                System.out.println("--------LIBRO#" + index[0] + "--------\n");
                System.out.println(libro);
                System.out.println("---------------------\n");

                index[0]++;
            });
        }
    }

    private void autoresRegistrados() {
        List<Autor> autores = autorService.obtenerTodosLosAutores();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.\n");
        } else {
            System.out.println("Autores disponibles: \n");

            final int[] index = {1};

            autores.forEach(autor -> {
                System.out.println("--------AUTOR#" + index[0] + "--------\n");
                System.out.println(autor);
                System.out.println("------------------------\n");

                index[0]++;
            });
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el titulo de tu libro: ");
        var tituloLibro = teclado.nextLine();

        //Buscar primero en la base de datos
        Optional<Libro> libroExistente = libroService.buscarLibroPorTitulo(tituloLibro);

        if(libroExistente.isPresent()){
            // Mensaje cuando el libro ya está registrado
            System.out.println("El titulo del libro ya esta registrado :D\n" + libroExistente.get());
        }else {
            // Buscar en la API externa si no está en la base de datos
            Datos datos = getDatos(tituloLibro);

            if (!datos.resultados().isEmpty()) {
                DatosLibro datosLibro = datos.resultados().get(0);// Tomamos el primer libro
                DatosAutor datosAutor = datosLibro.autores().get(0);// Tomamos el primer autor

                // Validamos el primer idioma del libro
                String codigoIdioma = datosLibro.idioma().get(0);//// Solo tomamos el primer idioma
                TipoIdioma idiomaPrincipal;
                try{
                    idiomaPrincipal = TipoIdioma.fromString(codigoIdioma);// Validamos el idioma
                } catch (IllegalArgumentException e){
                    System.out.println("Idioma original del libro no permitido.\nIdiomas válidos: español(es), ingles(en), frances(fr), portuges(pt).");
                    System.out.println("Idioma ingresado: " + codigoIdioma + "\n");
                    return; // Salimos del metodo si el idioma no es valido
                }

            // Crear el libro con los datos obtenidos
            Libro libro = new Libro(datosLibro);
            libro.setIdioma(idiomaPrincipal);

            // Buscar o registrar autor
            Autor autor = obtenerORegistrarAutor(datosAutor);
            // Asignar el autor al libro
            libro.setAutor(autor);

            // Evitar duplicados en la lista del autor
            if (autor.getLibros() == null) {
                autor.setLibros(new ArrayList<>());
            }

            // Verificar si el libro ya está registrado para este autor
            boolean libroExiste = autor.getLibros().stream()
                    .anyMatch(l -> l.getTitulo().equalsIgnoreCase(libro.getTitulo()));

            if (libroExiste) {
                System.out.println("El titulo del libro ya esta registrado :D\n" + libro);
            } else {
                //si no esta registrado, agregar el libro
                autor.getLibros().add(libro);
                libroService.guardarLibro(libro);
                System.out.println("Libro guardado: " + "\n" + libro);
            }
        } else {
            System.out.println("No se encontraron libros T.T\n");
        }
    }
}

    private Autor obtenerORegistrarAutor(DatosAutor datosAutor) {
        return autorService.buscarAutorPorNombre(datosAutor.nombre())
                .orElseGet(() -> autorService.guardarAutor(new Autor(datosAutor)));
    }

    private Datos getDatos(String tituloLibro){
        try {
            var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
            return conversor.obtenerDatos(json, Datos.class);
        }catch (Exception e){
            System.out.println("Error al obtener datos de la API T.T " + e.getMessage());
            return new Datos(new ArrayList<>());
        }
    }

}












