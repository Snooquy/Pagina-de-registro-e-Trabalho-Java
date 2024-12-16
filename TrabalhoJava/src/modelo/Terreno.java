package modelo;

import util.SeguroMaiorParcela;

import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Terreno extends Financiamento implements Serializable {
    private String tipoZona;

    public Terreno(){}

    public Terreno(double valorDesejado, int financiamentoAnos, double jurosAnual, String tipoZona) {
        super(valorDesejado, financiamentoAnos, jurosAnual);
        this.tipoZona = tipoZona;
    }

    public String getTipoZona() {
        return tipoZona;
    }

    public double pagamentoMensal(){
        double pag = (getValueImovel() / (getTerm()* 12)) * (1 + (getFees()/100 / 12));
        return pag + (2.0 / 100 * pag);
    }

    public static double TotalFinanciamento(ArrayList<Terreno> terreno) throws SeguroMaiorParcela {
        double totalFinanciamentos = 0;

        for (Financiamento financiamento : terreno) {
            totalFinanciamentos += financiamento.totalPagamento();
        }
        return totalFinanciamentos;

    }

    public static double TotalImoveis(ArrayList<Terreno> terreno) {
        double totalImoveis = 0;

        for (Financiamento financiamento : terreno) {
            totalImoveis += financiamento.getValueImovel();
        }
        return totalImoveis;
    }

    public static String formatarTotais(ArrayList<Terreno> terreno) throws SeguroMaiorParcela {
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        double totalImoveis = TotalImoveis(terreno);
        double totalFinanciamentos = TotalFinanciamento(terreno);

        return String.format("Total de todos os imóveis: %s\nTotal de todos os financiamentos: %s",
                formatoMoeda.format(totalImoveis), formatoMoeda.format(totalFinanciamentos));

    }

    public static void salvarTerreno(List<Terreno> listaDeFinanciamentos) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("financiamentos_terreno.txt", true))) {
            for (Terreno financiamento : listaDeFinanciamentos) {
                writer.println("Valor do imóvel: " + financiamento.getValueImovel());
                writer.println("Valor do financiamento: " + financiamento.totalPagamento());
                writer.println("Taxa de juros: " + financiamento.getFees());
                writer.println("Prazo de financiamento: " + financiamento.getTerm());
                writer.println("Tipo de zona: " + financiamento.getTipoZona());
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
                            -----Financiamento Terreno-----
                            Valor do imóvel: %s
                            Valor da parcela: %s
                            Valor total: %s
                            Tipo de zona: %s
                            """,
                    formatoMoeda.format(getValueImovel()), formatoMoeda.format(pagamentoMensal()), formatoMoeda.format(totalPagamento()), getTipoZona()
            );
        } catch (SeguroMaiorParcela e) {
            throw new RuntimeException(e);
        }
    }
}