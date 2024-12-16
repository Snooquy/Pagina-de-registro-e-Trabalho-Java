package util;

import modelo.Apartamento;
import modelo.Casa;
import modelo.Terreno;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class InterfaceUsuario {

    private Scanner sc;

    public InterfaceUsuario() {
        this.sc = new Scanner(System.in);
    }

    public void close() {
        if (sc != null) {
            sc.close();
        }
    }

    // Menu

    public int opcao(){
        System.out.println("""
                ----- Menu -----
                Qual financiamento deseja realizar?
                [1] - Apartamento
                [2] - Casa
                [3] - Terreno
                [4] - Ler dados serializados
                [5] - Mostrar ja instanciados
                """);
        int num = sc.nextInt();
        if (num <= 0 | num > 5){
            throw new IllegalArgumentException("Numero digitado inválido.");
        }
        return num;
    }

    public void cabecalho(int num) throws SeguroMaiorParcela, IOException {
        if (num == 1) {
            int finans = numFinans();
            ArrayList<Apartamento> ap = new ArrayList<>();
            for (int i = 0; i < finans; i++) {
                System.out.println("----- Apartamento -----");
                var aps = apartamento();
                ap.add(aps);
            }
            System.out.println(ap);
            System.out.println(Apartamento.formatarTotais(ap));
            Apartamento.salvarApartamento(ap);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("apartamentos.ser"))) {
                oos.writeObject(ap);
                System.out.println("Dados salvos com sucesso.");
            } catch (IOException e) {
                System.out.println("Erro ao salvar os dados: " + e.getMessage());
            }
        }
        if (num == 2){
            int finans = numFinans();
            ArrayList<Casa> casa = new ArrayList<>();
            for (int i = 0; i < finans; i++){
                System.out.println("----- Casa -----");
                var casas = casa();
                casa.add(casas);
            }
            System.out.println(casa);
            System.out.println(Casa.formatarTotais(casa));
            Casa.salvarCasa(casa);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("casa.ser"))) {
                oos.writeObject(casa);
                System.out.println("Dados salvos com sucesso.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (num == 3){
            int finans = numFinans();
            ArrayList<Terreno> terreno = new ArrayList<>();
            for (int i = 0; i < finans; i++){
                System.out.println("----- Terreno -----");
                var terrenos = terreno();
                terreno.add(terrenos);
            }
            System.out.println(terreno);
            System.out.println(Terreno.formatarTotais(terreno));
            Terreno.salvarTerreno(terreno);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("terreno.ser"))) {
                oos.writeObject(terreno);
                System.out.println("Dados salvos com sucesso.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (num == 4){
            lerSerializados();
        }
        if (num == 5){}
    }

    public void lerSerializados() throws SeguroMaiorParcela {
        System.out.println("""
                ----- Menu -----
                Qual financiamento deseja carregar?
                [1] - Apartamento
                [2] - Casa
                [3] - Terreno
                """);
        int num = sc.nextInt();
        if (num <= 0 || num > 3) {
            throw new IllegalArgumentException("Numero digitado inválido.");
        }
        if (num == 1) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("apartamentos.ser"))) {
                ArrayList<Apartamento> apartamentos = (ArrayList<Apartamento>) ois.readObject();
                System.out.println("Dados carregados com sucesso:");
                for (Apartamento apartamento : apartamentos) {
                    System.out.println(apartamento);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (num == 2) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("casa.ser"))) {
                ArrayList<Casa> casas = (ArrayList<Casa>) ois.readObject();
                System.out.println("Dados carregados com sucesso:");
                for (Casa casa : casas) {
                    System.out.println(casa);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (num == 3) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("terreno.ser"))) {
                ArrayList<Terreno> terrenos = (ArrayList<Terreno>) ois.readObject();
                System.out.println("Dados carregados com sucesso:");
                for (Terreno terreno : terrenos) {
                    System.out.println(terreno);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int numFinans(){
        System.out.println("Quantos financiamentos deseja realizar? ");
        int num = sc.nextInt();
        if (num <= 0){
            throw new IllegalArgumentException("Numero invalido.");
        }
        return num;
    }

    // Fim menu

    // Menu Ap

    public Apartamento apartamento(){
        double val = saberValor();
        int finan = tamFinanciamento();
        double juros = taxaJuros();
        int vagas = qntVagas();
        int andar = saberAndar();
        return new Apartamento(val, finan, juros, vagas, andar);
    }

    // Menu Casa

    public Casa casa(){
        double val = saberValor();
        int finan = tamFinanciamento();
        double juros = taxaJuros();
        int tamTerreno = tamTerreno();
        int areaConstruida = areaConstruida();
        return new Casa(val, finan, juros, tamTerreno, areaConstruida);
    }

    // Menu Terreno

    public Terreno terreno(){
        double val = saberValor();
        int finan = tamFinanciamento();
        double juros = taxaJuros();
        String zona = tipoZona();
        return new Terreno(val, finan, juros, zona);
    }

    // Geral

    public double saberValor(){
            System.out.println("Digite o valor do imovel: ");
            double num = sc.nextDouble();
            if (num < 0){
                throw new IllegalArgumentException("Numero invalido.");
            }
            return num;
    }
    public int tamFinanciamento(){
        System.out.println("Digite o prazo do seu financiamento (em anos): ");
        int num = sc.nextInt();
        if (num < 0){
            throw new IllegalArgumentException("Numero invalido.");
        }
        return num;
    }
    public double taxaJuros(){
        System.out.println("Digite a taxa de juros anual do seu financiamento: ");
        double num = sc.nextDouble();
        if (num < 0 | num > 100){
            throw new IllegalArgumentException("Numero invalido.");
        }
        return num;
    }


    // Apartamento

    public int qntVagas(){
        System.out.println("Digite a quantidade de vagas na garagem: ");
        int num = sc.nextInt();
        if (num < 0){
            throw new IllegalArgumentException("Numero invalido.");
        }
        return num;
    }
    public int saberAndar(){
        System.out.println("Digite o andar do seu apartamento: ");
        int num = sc.nextInt();
        if (num < 0){
            throw new IllegalArgumentException("Numero invalido.");
        }
        return num;
    }

    // Casa

    public int tamTerreno(){
        System.out.println("Digite o tamanho do terreno (em metros quadrados): ");
        int num = sc.nextInt();
        if (num < 0){
            throw new IllegalArgumentException("Numero invalido.");
        }
        return num;
    }
    public int areaConstruida(){
        System.out.println("Digite a area construida (em metros quadrados): ");
        int num = sc.nextInt();
        if (num < 0){
            throw new IllegalArgumentException("Numero invalido.");
        }
        return num;
    }

    // Terreno

    public String tipoZona(){
        System.out.println("Informe o tipo de zona (Ex: residencial ou comercial): ");
        return sc.next();
    }
}
