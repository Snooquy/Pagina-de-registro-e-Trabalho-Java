package modelo;

import util.SeguroMaiorParcela;

import java.io.Serializable;

public abstract class Financiamento implements Serializable{
    protected double valorImovel;
    protected int prazoFinanciamento;
    protected double taxaJurosAnual;

    public Financiamento(){}

    public Financiamento(double valorImovel, int prazoFinanciamento, double taxaJurosAnual) {
        if (prazoFinanciamento <= 0) {
            throw new IllegalArgumentException("Prazo de financiamento deve ser maior que zero");
        }
        this.valorImovel = valorImovel;
        this.prazoFinanciamento = prazoFinanciamento;
        this.taxaJurosAnual = taxaJurosAnual;
    }

    public double getValueImovel(){
        return this.valorImovel;
    }

    public int getTerm(){
        return this.prazoFinanciamento;
    }


    public double getFees(){
        return this.taxaJurosAnual;
    }

    public abstract double pagamentoMensal() throws SeguroMaiorParcela;

    public double totalPagamento() throws SeguroMaiorParcela {
        if (prazoFinanciamento == 0) {
            throw new ArithmeticException("Cannot multiply by zero");
        }
        return pagamentoMensal() * (prazoFinanciamento * 12);
    }

    public boolean getDetalhesEspecificos() {
        return false;
    }
}
