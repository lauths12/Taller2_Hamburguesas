package Consola;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import Logica.Restaurante;


public class Aplicacion{
	
	private Restaurante archivoCargado;

	
	public void ejecutarAplicacion()
	{
		System.out.println("\n------------------------Hamburguesas Uniandes-----------------------\n");

		boolean continuar = true;
		while (continuar)
		{
			try
			{
				mostrarMenu();
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
				if (opcion_seleccionada == 1)
					ejecutarCargarMenu();
				else if (opcion_seleccionada == 2 && archivoCargado != null) 
					ejecutarIniciarPedido(archivoCargado, new Scanner(System.in));
				else if (opcion_seleccionada == 0)
				{
					System.out.println("Saliendo de la aplicación ...");
					continuar = false;
				}

				else
				{
					System.out.println("Por favor seleccione una opción válida.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println("Debe seleccionar uno de los números de las opciones.");
			}
		}
	}

	/**
	 * Muestra al usuario el menú con las opciones para que escoja la siguiente
	 * acción que quiere ejecutar.
	 */
	public void mostrarMenu()
	{
		System.out.println("\nOpciones de la aplicación\n");
		System.out.println("1. Cargar Menú");
		System.out.println("2. Iniciar Pedido");
		System.out.println("3. Agregar un elemento a un pedido");
		System.out.println("4. Cerrar un pedido y guardar la factura");
		System.out.println("5. Consultar la información de un pedido dado su id");
		System.out.println("0. Salir de la aplicación\n");
	}
	
	private void ejecutarIniciarPedido(Restaurante restaurante, Scanner scanner) {
	    System.out.println("Ingrese su nombre:  ");
	    String nombreCliente = scanner.nextLine();
	    System.out.println("Ingrese su dirección:  ");
	    String direccionCliente = scanner.nextLine();
	    System.out.println("Seleccione un pedido:  ");

	    HashMap<String, Double> menuBase = restaurante.getMenuBase();
	    HashMap<String, Double> bebidas = restaurante.getBebidas();
	    HashMap<String, ArrayList<String>> combos = restaurante.getCombos();

	    System.out.println("\nPLATOS PRINCIPALES:\n");
	    int contador = 1;
	    for (String producto : menuBase.keySet()) {
	        System.out.println(contador + ". " + producto + " - $" + menuBase.get(producto));
	        contador++;
	    }

	    System.out.println("\nBEBIDAS:\n");
	    for (String producto : bebidas.keySet()) {
	        System.out.println(contador + ". " + producto + " - $" + bebidas.get(producto));
	        contador++;
	    }

	    System.out.println("\nCOMBOS:\n");
	    int comboNum = contador;
	    for (String nombreCombo : combos.keySet()) {
	        ArrayList<String> listaProductos = combos.get(nombreCombo);
	        System.out.println(comboNum + ". " + nombreCombo + ":");
	        for (int i = 1; i < listaProductos.size(); i++) {
	            System.out.println("\t" + listaProductos.get(i));
	        }
	        comboNum++;
	    }

	    ArrayList<String> pedido = new ArrayList<>();
	    boolean seguirSeleccionando = true;
	    while (seguirSeleccionando) {
	        System.out.println("\nIngrese el número correspondiente a lo que desea ordenar (0 para finalizar):\n");
	        String opcion = scanner.nextLine();
	        if (opcion.equals("0")) {
	            seguirSeleccionando = false;
	        } else if (Integer.parseInt(opcion) > comboNum || Integer.parseInt(opcion) < 1) {
	            System.out.println("Opción no válida.");
	        } else {
	            String productoElegido;
	            if (Integer.parseInt(opcion) <= menuBase.size()) {
	                productoElegido = (String) menuBase.keySet().toArray()[Integer.parseInt(opcion) - 1];
	            } else if (Integer.parseInt(opcion) <= menuBase.size() + bebidas.size()) {
	                productoElegido = (String) bebidas.keySet().toArray()[Integer.parseInt(opcion) - menuBase.size() - 1];
	            } else {
	                productoElegido = (String) combos.keySet().toArray()[Integer.parseInt(opcion) - menuBase.size() - bebidas.size() - 1];
	            }
	            pedido.add(productoElegido);
	            String pedidoRegistrado = String.join(", ", pedido);
	            System.out.println("Pedido registrado: " + pedidoRegistrado);
	        }
	    }
	    
	    restaurante.iniciarPedido(nombreCliente, direccionCliente, pedido);
	    System.out.println("\nPedido registrado con éxito:");
	    for (String producto : pedido) {
	        System.out.println("- " + producto);
	    }
	}

	private void ejecutarCargarMenu() {
	    String rutaIngredientes = "./data/ingredientes.txt";
	    String rutaMenu = "./data/menu.txt";
	    String rutaCombos = "./data/combos.txt";

	    Restaurante restaurante = new Restaurante();
	    restaurante.cargarInformacionRestaurante(new File(rutaIngredientes), new File(rutaMenu), new File(rutaCombos));
	    

	    System.out.println("Menú cargado correctamente.");
	    
	    archivoCargado = restaurante;
	    System.out.println("\n----------------------MENU------------------------:");
	    System.out.println("\nBEBIDAS:\n");
	    HashMap<String, Double> bebidas = restaurante.getBebidas();
	    for (String producto : bebidas.keySet()) {
	        System.out.println(producto + ": $" + bebidas.get(producto));
	    }

	    System.out.println("\nMENU BASE:\n");
	    HashMap<String, Double> menuBase = restaurante.getMenuBase();
	    for (String producto : menuBase.keySet()) {
	        if (!restaurante.getBebidas().containsKey(producto)) {
	            System.out.println(producto + ": $" + menuBase.get(producto));
	        }
	    }
	    
	    System.out.println("\nCOMBOS:\n");
	    HashMap<String, ArrayList<String>> combos = restaurante.getCombos();
	    for (String nombreCombo : combos.keySet()) {
	        ArrayList<String> listaProductos = combos.get(nombreCombo);
	        System.out.println(nombreCombo + ":");
	        for (int i = 1; i < listaProductos.size(); i++) {
	            System.out.println("\t" + listaProductos.get(i));
	        }
	    }
	}

	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Este es el método principal de la aplicación, con el que inicia la ejecución
	 * de la aplicación
	 * 
	 * @param args Parámetros introducidos en la línea de comandos al invocar la
	 *             aplicación
	 */
	public static void main(String[] args)
	{
		Aplicacion consola = new Aplicacion();
		consola.ejecutarAplicacion();
	}

}

