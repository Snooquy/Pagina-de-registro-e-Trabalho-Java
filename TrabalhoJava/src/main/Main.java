package main;

import modelo.Apartamento;
import modelo.Casa;
import modelo.Financiamento;
import modelo.Terreno;
import util.InterfaceUsuario;
import util.SeguroMaiorParcela;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SeguroMaiorParcela, IOException {
        InterfaceUsuario mostrarMenu = new InterfaceUsuario();
        ArrayList<Financiamento> financiamentos = new ArrayList<>();

        //Financiamentos ja instanciados.

        Financiamento casa = new Casa(200000, 15, 10.0, 200, 300);
        Financiamento ap = new Apartamento(500000, 20, 10, 15, 12);
        Financiamento terreno = new Terreno(235000, 20, 10, "rural");

        financiamentos.add(casa);
        financiamentos.add(ap);
        financiamentos.add(terreno);

        // Salvando txt

        try (PrintWriter writer = new PrintWriter(new FileWriter("financiamentos_instanciados.txt", true))) {
            for (Financiamento financiamento : financiamentos) {
                writer.println("Valor do imóvel: " + financiamento.getValueImovel());
                writer.println("Valor do financiamento: " + financiamento.totalPagamento());
                writer.println("Taxa de juros: " + financiamento.getFees());
                writer.println("Prazo de financiamento: " + financiamento.getTerm());
                writer.println(financiamento.getDetalhesEspecificos());
                writer.println();
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

        // Salvando serializados

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("financiamentos.ser"))) {
            oos.writeObject(financiamentos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Menu

        int op = mostrarMenu.opcao();
        mostrarMenu.cabecalho(op);
        mostrarMenu.close();

        // Mostrando serializados

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("financiamentos.ser"))) {
            financiamentos = (ArrayList<Financiamento>) ois.readObject();
            System.out.println("Serializados ja instanciados:");
            for (Financiamento finans : financiamentos) {
                System.out.println(finans);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
