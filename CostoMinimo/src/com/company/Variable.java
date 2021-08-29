package com.company;
import java.util.Formatter;

public class Variable {

    private int oferta;
    private int demanda;
    private double valor;

    public Variable(){
        this.oferta = 0;
        this.demanda = 0;
    }

    //Constructor
    public Variable(int oferta, int demanda){
        this.oferta = oferta;
        this.demanda = demanda;
    }

    //Getters y Setters
    public int getOferta() {
        return oferta;
    }

    public void setOferta(int oferta) {
        this.oferta = oferta;
    }

    public int getDemanda() {
        return demanda;
    }

    public void setDemanda(int demanda) {
        this.demanda = demanda;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        Formatter f = new Formatter();
        f.format("x[%d,%d]=%f", this.oferta +1, this.demanda +1, this.valor);
        return f.toString();
    }

}