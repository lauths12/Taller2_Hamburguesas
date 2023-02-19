package Logica;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Restaurante {
    private HashMap<String, Double> menuBase;
    private HashMap<String, Double> bebidas;
    private HashMap<String, Double> ingredientes;
    private HashMap<String, ArrayList<String>> combos;
    public HashMap<Integer, HashMap<String, Object>> pedidos;
    private int idPedidoActual;
    
    public Restaurante() {
        this.menuBase = new HashMap<>();
        this.bebidas = new HashMap<>();
        this.ingredientes = new HashMap<>();
        this.combos = new HashMap<>();
        this.pedidos = new HashMap<>();
        this.idPedidoActual = 0;
    
    }

    
    public void iniciarPedido(String nombreCliente, String direccionCliente, ArrayList<String> pedido) {
        HashMap<String, Object> infoPedido = new HashMap<>();
        infoPedido.put("nombreCliente", nombreCliente);
        infoPedido.put("direccion", direccionCliente);
        infoPedido.put("Pedido", pedido);
        this.idPedidoActual++;
        this.pedidos.put(this.idPedidoActual, infoPedido);

    }
    
    public void cerrarYGuardarPedido() {
        // Creamos un nuevo HashMap para guardar los pedidos con un ID
        HashMap<Integer, HashMap<String, Object>> pedidosGuardados = new HashMap<>();
        
        // Iteramos por los pedidos y los agregamos al nuevo HashMap con su ID
        for (int i = 1; i <= this.pedidos.size(); i++) {
            pedidosGuardados.put(i, this.pedidos.get(i));
        }
        
        // Actualizamos el HashMap de pedidos con el nuevo HashMap
        this.pedidos = pedidosGuardados;
 
    }

    
    public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos) {
        cargarIngredientes(archivoIngredientes);
        cargarMenu(archivoMenu);
        cargarCombos(archivoCombos);
    }
    
    private void cargarIngredientes(File archivoIngredientes) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                String nombre = datos[0];
                Double precio = Double.parseDouble(datos[1]);
                this.ingredientes.put(nombre, precio);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void cargarMenu(File archivoMenu) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivoMenu));
            String linea;
            int contador = 0;
            int totalLineas = 0;
            while ((linea = br.readLine()) != null) {
                totalLineas++;
            }
            br.close();

            br = new BufferedReader(new FileReader(archivoMenu));
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                String nombre = datos[0];
                Double precio = Double.parseDouble(datos[1]);
                if (contador < totalLineas - 3) {
                    this.menuBase.put(nombre, precio);
                } else {
                    this.bebidas.put(nombre, precio);
                }
                contador++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarCombos(File archivoCombos) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivoCombos));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                String nombreCombo = datos[0];
                int descuento = Integer.parseInt(datos[1].replace("%", ""));
                ArrayList<String> listaProductos = new ArrayList<>();
                listaProductos.add(String.format("%d%%", descuento));
                for (int i = 2; i < datos.length; i++) {
                    listaProductos.add(datos[i]);
                }
                this.combos.put(nombreCombo, listaProductos);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public HashMap<String, Double> getMenuBase() {
        return this.menuBase;
    }
    
    public HashMap<String, Double> getBebidas() {
        return this.bebidas;
    }
    

    public HashMap<String, Double> getIngredientes() {
        return this.ingredientes;
    }

    public HashMap<String, ArrayList<String>> getCombos() {
        return this.combos;
    }
    
    public HashMap<Integer, HashMap<String, Object>> getPedidoEnCurso(){
        return this.pedidos;
    }
   
}

