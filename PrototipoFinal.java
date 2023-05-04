/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package modelotransporte;

import java.util.Scanner;

public class PrototipoFinal {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Pedir al usuario la cantidad de orígenes y destinos
        System.out.print("Ingrese la cantidad de orígenes: ");
        int numOrigenes = input.nextInt();
        System.out.print("Ingrese la cantidad de destinos: ");
        int numDestinos = input.nextInt();

        // Crear las matrices de costos, demandas y ofertas
        int[][] costos = new int[numOrigenes][numDestinos];
        int[] demandas = new int[numDestinos];
        int[] ofertas = new int[numOrigenes];

        // Pedir al usuario los costos
        for (int i = 0; i < numOrigenes; i++) {
            for (int j = 0; j < numDestinos; j++) {
                System.out.print("Ingrese el costo de enviar desde el origen " + (i + 1) + " al destino " + (j + 1) + ": ");
                costos[i][j] = input.nextInt();
            }
        }

        System.out.println("\n-----------------------------DIGITA LAS OFERTAS-----------------------------\n");
        // Pedir al usuario las ofertas
        for (int j = 0; j < numDestinos; j++) {
            System.out.print("Ingrese la oferta del origen" + (j + 1) + ": ");
            demandas[j] = input.nextInt();
        }
        System.out.println("\n-----------------------------DIGITA LAS DEMANDAS-----------------------------\n");
        // Pedir al usuario las demandas
        for (int i = 0; i < numOrigenes; i++) {
            System.out.print("Ingrese la demanda del destino " + (i + 1) + ": ");
            ofertas[i] = input.nextInt();
        }

        // Aplicar el método de esquina noroeste para encontrar una solución inicial
        int[][] solucion = esquinaNoroeste(costos, demandas, ofertas);

        // Calcular el costo total de la solución
        int costoTotal = 0;
        for (int i = 0; i < numOrigenes; i++) {
            for (int j = 0; j < numDestinos; j++) {
                costoTotal += solucion[i][j] * costos[i][j];
            }
        }

        // Imprimir la solución encontrada
        System.out.println("\n-----------------------------TABLA DE TRANSPORTE-----------------------------\n");
        // Imprimir la tabla de transporte
        System.out.print("\t\t");
        for (int j = 0; j < numDestinos; j++) {
            System.out.print("Destino " + (j + 1) + "\t");
        }
        System.out.println("Oferta");

        for (int i = 0; i < numOrigenes; i++) {
            System.out.print("Origen " + (i + 1) + "\t");
            for (int j = 0; j < numDestinos; j++) {
                System.out.print(solucion[i][j] + "\t\t");
            }
            System.out.println(ofertas[i]);
        }
        System.out.print("Demanda\t\t");
        for (int j = 0; j < numDestinos; j++) {
            System.out.print(demandas[j] + "\t\t");
        }

        // Imprimir el costo total de la solución
        System.out.println("a\n");

    }

    public static int[][] esquinaNoroeste(int[][] costos, int[] oferta, int[] demanda) {
        int numOrigenes = costos.length;
        int numDestinos = costos[0].length;

        int[][] asignaciones = new int[numOrigenes][numDestinos];

        int ofertaActual = 0;
        int demandaActual = 0;

        while (ofertaActual < numOrigenes && demandaActual < numDestinos) {
            int ofertaDisponible = oferta[ofertaActual];
            int demandaDisponible = demanda[demandaActual];

            if (ofertaDisponible == 0) {
                ofertaActual++;
                continue;
            }

            if (demandaDisponible == 0) {
                demandaActual++;
                continue;
            }

            int asignacion = Math.min(ofertaDisponible, demandaDisponible);

            asignaciones[ofertaActual][demandaActual] = asignacion;

            oferta[ofertaActual] -= asignacion;
            demanda[demandaActual] -= asignacion;

            if (oferta[ofertaActual] == 0) {
                ofertaActual++;
            }

            if (demanda[demandaActual] == 0) {
                demandaActual++;
            }
        }

        int ofertaFaltante = 0;
        for (int i = 0; i < numOrigenes; i++) {
            ofertaFaltante += oferta[i];
        }

        int demandaFaltante = 0;
        for (int i = 0; i < numDestinos; i++) {
            demandaFaltante += demanda[i];
        }

        if (ofertaFaltante == 0 && demandaFaltante == 0) {
            return asignaciones;
        } else {
            return null;
        }
    }

}
