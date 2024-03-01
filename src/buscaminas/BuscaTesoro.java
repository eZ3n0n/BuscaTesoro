
package buscaminas;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Aplicación para jugar a adivinar un tesoro en un array bidimensional
 * @author eZ3n0n
 * @version 1.0
 * @since 02.24
 */
public class BuscaTesoro {

    /**
     * Método de entrada a la aplicación
     * @param args No se pasan argumentos en este caso. Crea una partida y llama 
     *             métodos de la clase Juego.
     */
    public static void main(String[] args) {
        // Se piden los datos para elaborar el tablero: número de filas, número de columnas,
        // número de minas a colocar y número de tesoros. Se devuelven en un array:
        // datos[0] -> numFilas
        // datos[1] -> numColumnas
        // datos[2] -> numMinas
        // datos[3] -> numTesoros

        int[] datos = pideDatos();
        // Creo el juego
        Juego partida = new Juego(datos[0], datos[1], datos[2], datos[3]);                
        // Se iniciliza el tablero con todas las posiciones vacías.
        partida.inicializaTablero();
        // Coloca las minas
        partida.colocaMinas();
        // Coloca los tesoros
        partida.colocaTesoros();
        // Juego
        System.out.println("¡BUSCA EL TESORO!");
        boolean salir = false;
        String c = "";
        do {
            // Pinta el tablero
            partida.pintaTablero();
            // Pide las coordenadas
            int[] xy = partida.pideCoordenadas();
            //System.out.println("x:"+xy[0]+" y:"+xy[1]);
            // Tiro
            salir = partida.tirada(xy[0],xy[1]);

        } while (!salir);
        // Revelamos el tablero con las minas y el tesoro
        partida.revelaTablero();
        
    }
    /**
     * Método para solicitar datos. Solicita los datos que serán necesarios para 
     * la creación de una partida: número de filas y columnas del tablero, número de minas
     * a colocar y número de tesoros. Controla que los datos introducidos son correctos.
     * @return int[] Devuelve un array de enteros con 4 valores: número de filas, número de columnas, número de minas y número de tesoros.
     */
    private static int[] pideDatos(){
        Scanner sc = new Scanner(System.in);     
        boolean bandera;
        // res[0]->NUMFILAS
        // res[1]->NUMCOLUMNAS
        // res[2]->NUMMINAS
        // res[3]->NUMTESOROS

        int[] res = new int[4];
        do {
            bandera=false;
            try {
                System.out.println("Introduce número de filas del tablero: ");
                res[0] = sc.nextInt();
                if ((res[0] <= 1)) {
                    System.out.println("El número de filas debe ser un entero mayor de 1");
                    bandera = true;
                }
                System.out.println("Introduce número de columnas del tablero: ");
                res[1] = sc.nextInt();
                if ((res[1] <= 1)) {
                    System.out.println("El número de columnas debe ser un entero mayor de 1");
                    bandera = true;
                }
                System.out.println("Introduce número de minas a colocar: ");
                res[2] = sc.nextInt();
                if ((res[2] > res[0]*res[1]-1)) {
                    System.out.println("2El tablero tiene "+res[0]*res[1]+" posiciones. Como mucho, puedes encajar "+(res[0]*res[1]-1)+" minas.");
                    bandera = true;
                } else {
                    System.out.println("Introduce número de tesoros a colocar: ");
                    res[3] = sc.nextInt();
                    if ((res[3] > res[0] * res[1] - res[2])) {
                        //System.out.println("El tablero de "+NUMCOLUMNAS*NUMFILAS+" posiciones tiene ya colocadas "+NUMMINAS+" minas.\n Como mucho, podrás colocar "+(NUMCOLUMNAS * NUMFILAS - NUMMINAS)+" tesoros.");
                        System.out.println("El tablero de "+res[0]*res[1]+" posiciones tiene ya colocadas "+res[2]+" minas.\n Como mucho, podrás colocar "+(res[0] * res[1] - res[2])+((res[0] * res[1] - res[2])>1?" tesoros.":" tesoro."));
                        bandera = true;
                    }
                }                
            } catch (InputMismatchException e) {
                System.out.println("Error de tipo. Introduzca números enteros.");
                bandera = true;
            }
        } while (bandera);
        return res;       
    }
}