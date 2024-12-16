package modelo;

import util.SeguroMaiorParcela;

import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Casa extends Financiamento implements Serializable {
    private int areaConstruida;
    private int tamTerreno;

    public Casa() {}

    public Casa(double valorDesejado, int financiamentoAnos, double jurosAnual, int tamTerreno, int areaConstruida) {
        super(valorDesejado, financiamentoAnos, jurosAnual);
        this.areaConstruida = areaConstruida;
        this.tamTerreno = tamTerreno;
    }

    public int getAreaConstruida() {
        return areaConstruida;
    }

    public int getTamTerreno() {
        return tamTerreno;
    }

    public double pagamentoMensal() throws SeguroMaiorParcela {
        double parcela = (getValueImovel() / (getTerm() * 12)) * (1 + (getFees() / 100 / 12)) + 80;
        if (parcela < 80) {
            throw new SeguroMaiorParcela("Seguro maior do que o valor da parcela.");
        }
        return parcela;
    }

    public static double TotalFinanciamento(ArrayList<Casa> casa) throws SeguroMaiorParcela {
        double totalFinanciamentos = 0;

        for (Financiamento financiamento : casa) {
            totalFinanciamentos += financiamento.totalPagamento();
        }
        return totalFinanciamentos;

    }

    public static double TotalImoveis(ArrayList<Casa> casa) {
        double totalImoveis = 0;

        for (Financiamento financiamento : casa) {
            totalImoveis += financiamento.getValueImovel();
        }
        return totalImoveis;
    }

    public static String formatarTotais(ArrayList<Casa> casa) throws SeguroMaiorParcela {
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        double totalImoveis = TotalImoveis(casa);
        double totalFinanciamentos = TotalFinanciamento(casa);

        return String.format("Total de todos os imóveis: %s\nTotal de todos os financiamentos: %s",
                formatoMoeda.format(totalImoveis), formatoMoeda.format(totalFinanciamentos));

    }

    public static void salvarCasa(List<Casa> listaDeFinanciamentos) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("financiamentos_casa.txt",true))) {
            for (Casa financiamento : listaDeFinanciamentos) {
                writer.println("Valor do imóvel: " + financiamento.getValueImovel());
                writer.println("Valor do financiamento: " + financiamento.totalPagamento());
                writer.println("Taxa de juros: " + financiamento.getFees());
                writer.println("Prazo de financiamento: " + financiamento.getTerm());
                writer.println("Area Construida: " + financiamento.getAreaConstruida());
                writer.println("Tamanho do terreno: " + financiamento.getTamTerreno());
                writer.println();
            }
        } catch (SeguroMaiorParcela e) {
            throw new RuntimeException(e);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


    @Override
    public String toString() {
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        try {
            return String.format(
                    """
                            -----Financiamento Casa-----
                            Valor do imóvel: %s
                            Valor da parcela: %s
                            Valor total: %s
                            Area construida: %sm²
                            Tamanho do terreno: %sm²
                            """,
                    formatoMoeda.format(getValueImovel()), formatoMoeda.format(pagamentoMensal()), formatoMoeda.format(totalPagamento()), getAreaConstruida(), getTamTerreno()
            );
        } catch (SeguroMaiorParcela e) {
            throw new RuntimeException(e);
        }
    }
}
