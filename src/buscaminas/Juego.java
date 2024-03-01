
package buscaminas;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase Juego. Clase que permite la creación de una partida de Búsqueda de tesoro.
 * @author eZ3n0n
 * @version 1.0
 * @since 02.24
 */
public class Juego {
    // Se definen constantes para representar el contenido de las celdas
    final static int VACIO = 0;
    final static int MINA = 1;
    final static int TESORO = 2;
    final static int INTENTO = 3;  
    // Atributos del juego
    static int numFilas;    
    static int numColumnas;
    static int numMinas;
    static int numTesoros;
    // Tablero bidimensional de juego
    static int[][] tablero;  
    
    /**
     * Constructor Juego. Constructor parametrizado de la clase, para lanzar una partida.
     * @param numFilas Número de filas del tablero de juego.
     * @param numColumnas Número de columnas del tablero de juego.
     * @param numMinas Número de minas que se colocarán aleatoriamente en el tablero.
     * @param numTesoros Número de tesoros que se colocarán aleatoriamente en el tablero.
     */
    public Juego(int numFilas, int numColumnas, int numMinas, int numTesoros) {
        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.numMinas = numMinas;
        this.numTesoros = numTesoros;
        // Se reserva espacio para el tablero.
        tablero = new int[numFilas][numColumnas];        
    }
    
    /**
     * Método para inicializar el tablero de juego.  Pondrá todas las posiciones del tablero vacías.
     */
    public void inicializaTablero() {
        int x;
        int y;        
        // Inicializa el array
        for (x = 0; x < numFilas; x++) {
            for (y = 0; y < numColumnas; y++) {
                tablero[x][y] = VACIO;
            }
        }
    }

    /**
     * Método para colocar las minas en el tablero. Coloca el número de minas especificadas de forma aleatoria sobre el tablero de juego.
     */
    public void colocaMinas(){
        // Coloca las minas que se hayan especificado
        int minaX;
        int minaY;
        for (int i =0;i<numMinas;i++) {
            // Buscamos una localización de forma aleatoria para colocar
            // la mina, considerando que puede haber ya una en esa localización.
            do {
                minaX = (int) (Math.random() * numFilas);
                minaY = (int) (Math.random() * numColumnas);
            } while (tablero[minaX][minaY] == MINA);
            // Colocamos la mina.
            tablero[minaX][minaY] = MINA;        
        }
    }

    /**
     * Método para colocar los tesoros en el tablero. Coloca el número de tesoros especificados de forma aleatoria sobre el tablero de juego.
     */
    public void colocaTesoros(){
        int tesoroX;
        int tesoroY;
        // Coloca los tesoros que se hayan especificado
        for (int i =0;i<numTesoros;i++) {
            // Coloca el tesoro en una posición aleatoria que no coincida con una mina
            // Además, tenemos en cuenta que pudiera haber ya un tesoro en la posición indicada
            do {
                tesoroX = (int) (Math.random() * numFilas);
                tesoroY = (int) (Math.random() * numColumnas);
            } while ((tablero[tesoroX][tesoroY] == MINA) || (tablero[tesoroX][tesoroY] == TESORO));
            // Colocamos el tesoro
            tablero[tesoroX][tesoroY] = TESORO;
        }
    }

    /**
     * Método para realizar una tirada. Realiza una tirada sobre el tablero y devuelve true si termina la partida o false en caso contrario.
     *
     * @param x. Coordenada x de la tirada.
     * @param y. Coordenada y de la tirada.
     * @return boolean. Devuelve true si la partida se acaba, false si continúa.
     */
    public boolean tirada(int x, int y){
        boolean resultado=false;
        // Mira lo que hay en las coordenadas indicadas por el usuario
        switch (tablero[x][y]) {
            case VACIO:
                tablero[x][y] = INTENTO;
                break;
            case MINA:
                System.out.println("Lo siento, has perdido.");
                resultado = true;
                break;
            case TESORO:
                System.out.println("¡Enhorabuena! ¡Has encontrado el tesoro!");
                resultado = true;
                break;
            default:
        }
        return resultado;
        
    }

    /**
     * Método para pedir coordenadas. Solicita las coordenadas de una tirada, comprobando que están dentro de los márgenes del tablero.
     * @return int[]. Devuelve un array de dos posiciones. La primera es la coordenada x y la segunda la coordenada y.
     */
    public int[] pideCoordenadas(){
        int x,y;
        Scanner sc = new Scanner(System.in);        
        int[] coordenadas = new int[2];
        boolean bandera;
        do {
            bandera = false;
            System.out.print("Coordenada X: ");
            try {
                coordenadas[0] = sc.nextInt();
                if ((coordenadas[0]<0) || (coordenadas[0]>numFilas-1)) {
                    System.out.println("Coordenada X fuera de rango.");
                    bandera = true;                    
                }
            } catch (InputMismatchException e) {
                System.out.println("Error de tipo. Introduzca un entero.");
                bandera = true;
            }
        } while (bandera);
        do {
            bandera = false;
            System.out.print("Coordenada Y: ");
            try {
                coordenadas[1] = sc.nextInt();
                if ((coordenadas[1]<0) || (coordenadas[1]>numColumnas-1)) {
                    System.out.println("Coordenada Y fuera de rango.");
                    bandera = true;                    
                }
            } catch (InputMismatchException e) {
                System.out.println("Error de tipo. Introduzca un entero.");
                bandera = true;
            }
        } while (bandera);
        return coordenadas;        
    }

    /**
     * Método que dibuja el tablero. Dibuja el tablero con los intentos que se hayan realizado marcados con una X.
     */
    public void pintaTablero(){
        int x;
        int y;
        String ejex=" ";
        String separacion="";                
        
        // Línea de separación y leyenda del eje de las x        
        for (int i = 0; i < numFilas; i++) {
            ejex = ejex + " " + i;
        }
        for (int i = 0; i < ejex.length(); i++) {
            separacion = separacion + "-";
        }        
        for (y = numColumnas - 1; y >= 0; y--) {
            System.out.print(y + "|");
            for (x = 0; x < numFilas; x++) {
                if (tablero[x][y] == INTENTO) {
                    System.out.print("X ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        System.out.println(separacion);
        System.out.println(ejex);
        
    }

    /**
     * Método que revela el tablero. Dibuja el tablero con la posición de las minas (*) y los tesoros (€), además de los intentos realizados (X).
     */
    public void revelaTablero() {
        int x;
        int y;
        String c = "";
        String ejex=" ";
        String separacion="";                
        
        // Línea de separación y leyenda del eje de las x        
        for (int i = 0; i < numFilas; i++) {
            ejex = ejex + " " + i;
        }
        for (int i = 0; i < ejex.length(); i++) {
            separacion = separacion + "-";
        }        

        // Pinta el cuadrante
        for (y = numColumnas-1; y >= 0; y--) {
            System.out.print(y + " ");
            for (x = 0; x < numFilas; x++) {
                switch (tablero[x][y]) {
                    case VACIO:
                        c = "  ";
                        break;
                    case MINA:
                        c = "* ";
                        break;
                    case TESORO:
                        c = "€ ";
                        break;
                    case INTENTO:
                        c = "X ";
                        break;
                    default:
                }
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println(separacion);
        System.out.println(ejex);
    }       
}
