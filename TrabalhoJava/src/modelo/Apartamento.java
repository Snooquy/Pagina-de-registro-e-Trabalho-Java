package modelo;

import util.SeguroMaiorParcela;

import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Apartamento extends Financiamento implements Serializable {
    private int vagasGaragem;
    private int andar;

    public Apartamento(){}

    public Apartamento(double valorDesejado, int financiamentoAnos, double jurosAnual, int vagasGaragem, int andar) {
        super(valorDesejado, financiamentoAnos, jurosAnual);
        this.andar = andar;
        this.vagasGaragem = vagasGaragem;
    }

    public int getVagasGaragem() {
        return vagasGaragem;
    }

    public int getAndar() {
        return andar;
    }

    @Override
    public double pagamentoMensal(){
        double taxaJurosMensal = (getFees() / 100.0) / 12;
        int meses = getTerm() * 12;
        double numerador = getValueImovel() * taxaJurosMensal;
        double dominador = 1 - Math.pow(1 + taxaJurosMensal, -meses);

        return numerador / dominador;
    }

    public static double TotalFinanciamento(ArrayList<Apartamento> ap) throws SeguroMaiorParcela {
        double totalFinanciamentos = 0;

        for (Financiamento financiamento : ap) {
            totalFinanciamentos += financiamento.totalPagamento();
        }
        return totalFinanciamentos;

    }

    public static double TotalImoveis(ArrayList<Apartamento> ap) {
        double totalImoveis = 0;

        for (Financiamento financiamento : ap) {
            totalImoveis += financiamento.getValueImovel();
        }
        return totalImoveis;
    }

    public static String formatarTotais(ArrayList<Apartamento> ap) throws SeguroMaiorParcela {
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        double totalImoveis = TotalImoveis(ap);
        double totalFinanciamentos = TotalFinanciamento(ap);

        return String.format("Total de todos os imóveis: %s\nTotal de todos os financiamentos: %s",
                formatoMoeda.format(totalImoveis), formatoMoeda.format(totalFinanciamentos));

    }

    public static void salvarApartamento(List<Apartamento> listaDeFinanciamentos) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("financiamentos_ap.txt", true))) {
            for (Apartamento financiamento : listaDeFinanciamentos) {
                writer.println("Valor do imóvel: " + financiamento.getValueImovel());
                writer.println("Valor do financiamento: " + financiamento.totalPagamento());
                writer.println("Taxa de juros: " + financiamento.getFees());
                writer.println("Prazo de financiamento: " + financiamento.getTerm());
                writer.println("Andar do apartamento: " + financiamento.getAndar());
                writer.println("Quantidade de vagas na garagem: " + financiamento.getVagasGaragem());
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
                            -----Financiamento Apartamento-----
                            Valor do imóvel: %s
                            Valor da parcela: %s
                            Valor total: %s
                            Quantidade de vagas na garagem: %s
                            Andar: %s
                            """,
                    formatoMoeda.format(getValueImovel()), formatoMoeda.format(pagamentoMensal()), formatoMoeda.format(totalPagamento()), getVagasGaragem(), getAndar()
            );
        } catch (SeguroMaiorParcela e) {
            throw new RuntimeException(e);
        }
    }
}

