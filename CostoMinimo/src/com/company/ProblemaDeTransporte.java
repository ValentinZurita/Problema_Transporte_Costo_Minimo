package com.company;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class ProblemaDeTransporte {

    double[] demanda;
    double[] oferta;
    double[][] costo;
    LinkedList<Variable> factible = new LinkedList<Variable>();
    int ofertaTamanio;
    int demandaTamanio;

    public ProblemaDeTransporte(int ofertaTamanio, int demandaTamanio) {

        //Inicializamos variables
        this.ofertaTamanio = ofertaTamanio;
        this.demandaTamanio = demandaTamanio;
        oferta = new double[ofertaTamanio];
        demanda = new double[demandaTamanio];
        costo = new double[ofertaTamanio][demandaTamanio];

        //Inicializamos la lista factible
        for (int i = 0; i < (demandaTamanio + ofertaTamanio - 1); i++)
            factible.add(new Variable());

    }

    //Getter y Setters
    public void setOferta(double valor, int indice) {
        oferta[indice] = valor;
    }

    public void setDemanda(double valor, int indice) {
        demanda[indice] = valor;
    }

    public void setOferta(double valor, int oferta, int demanda) {
        costo[oferta][demanda] = valor;
    }


    //Metodo del costo mínimo
    //Inicializa la lista de soluciones factibles usando la regla de menor costo.
    public double cosotMinimo() {

        long start = System.nanoTime();

        double min;
        int k = 0; //Contador de soluciones factibles

        //isSet es responsable de anotar las celdas que se han asignado.
        boolean[][] isSet = new boolean[ofertaTamanio][demandaTamanio];
        for (int j = 0; j < demandaTamanio; j++)
            for (int i = 0; i < ofertaTamanio; i++)
                isSet[i][j] = false;

        int i = 0, j = 0;
        Variable costoMin = new Variable();

        //este bucle es responsable de las organizar las celdas por su menor costo
        while (k < (ofertaTamanio + demandaTamanio - 1)) {
            costoMin.setValor(Double.MAX_VALUE);

            //Obteniendo la celda de menor costo
            for (int m = 0; m < ofertaTamanio; m++)
                for (int n = 0; n < demandaTamanio; n++)
                    if (!isSet[m][n])
                        if (costo[m][n] < costoMin.getValor()) {
                            costoMin.setOferta(m);
                            costoMin.setDemanda(n);
                            costoMin.setValor(costo[m][n]);
                        }

            i = costoMin.getOferta();
            j = costoMin.getDemanda();

            //Asignación de la oferta de la manera adecuada.
            min = Math.min(demanda[j], oferta[i]);

            factible.get(k).setDemanda(j);
            factible.get(k).setOferta(i);
            factible.get(k).setValor(min);
            k++;

            demanda[j] -= min;
            oferta[i] -= min;

            //Asignar valores nulos en la fila / columna eliminada.
            if (oferta[i] == 0)
                for (int l = 0; l < demandaTamanio; l++)
                    isSet[i][l] = true;
            else
                for (int l = 0; l < ofertaTamanio; l++)
                    isSet[l][j] = true;

        }

        return (System.nanoTime() - start) * 1.0e-9;

    }

    //Getter
    public double getSolucion() {
        double resultado = 0;
        for (Variable x : factible) {
            resultado += x.getValor() * costo[x.getOferta()][x.getDemanda()];
        }

        return resultado;
    }


    //Método Main
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("+-------------------------------------------------------------+\n" +
                           "|                                                             |\n" +
                           "|                            UNADM                            |\n" +
                           "|                  INVESTIGACIÓN DE OPERACIONES               |\n" +
                           "|                                                             |\n" +
                           "|                    Actividad Complementaria                 |\n" +
                           "|                Valentin Alejandro Perez Zurita              |\n" +
                           "|                                                             |\n" +
                           "+-------------------------------------------------------------+\n"
        );

        System.out.println("Introduce el no. de centros de distribución y no. de estados:");
        int s = scanner.nextInt();
        int r = scanner.nextInt();
        double x;
        ProblemaDeTransporte prueba = new ProblemaDeTransporte(s, r);

        System.out.println("Introduce los datos de la oferta:");
        for (int i = 0; i < prueba.ofertaTamanio; i++) {
            x = scanner.nextDouble();
            prueba.setOferta(x, i);
        }

        System.out.println("Introduce los datos de la demanda:");
        for (int i = 0; i < prueba.demandaTamanio; i++) {
            x = scanner.nextDouble();
            prueba.setDemanda(x, i);
        }

        System.out.println("Introduce los costos de transporte:");
        for (int i = 0; i < prueba.ofertaTamanio; i++)
            for (int j = 0; j < prueba.demandaTamanio; j++) {
                x = scanner.nextDouble();
                prueba.setOferta(x, i, j);
            }

        //Llamado al método de costo mínimo
        prueba.cosotMinimo();

        //Iteramos entre los valores de la lista factible y los imprimimos
        for (Variable t : prueba.factible) {
            System.out.println(t);
        }

        //Imprimimos el resultado - el costo mínimo.
        System.out.println("Costo mínimo: " + prueba.getSolucion());

    }

}