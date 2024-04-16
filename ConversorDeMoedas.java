package org.example;

// Importando bibliotecas para o projeto
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ConversorDeMoedas {

    private static final String API_KEY = "YOUR_API_KEY"; // Após logar no site ExchangeRate-API, basta copiar a sua API-KEY pelo dashboard ou acessar este link: https://app.exchangerate-api.com/dashboard
    private static final String BASE_CODE = "BRL";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + BASE_CODE + "";

    public static void main(String[] args) {
        try {
            boolean exit = false;
            while (!exit) {
                // Obtem as taxas de conversão através da API
                JSONObject rates = getExchangeRates();

                // Menu de opções para seleção das moedas
                System.out.println("\nSelecione uma opção:");
                System.out.println("1. BRL para USD(Dólar Americano)");
                System.out.println("2. BRL para EUR(Euro)");
                System.out.println("3. BRL para GBP(Libra Esterlina)");
                System.out.println("4. BRL para JPY(Iene japonês)");
                System.out.println("5. BRL para CHF(Franco Suíço)");
                System.out.println("6. BRL para CNY(Yuan Chinês)");
                System.out.println("7. Sair");

                // Lê a opção selecionada pelo usuário
                int option = readOption();

                // Realiza a conversão com base na opção selecionada pelo usuário
                double amountInBRL;
                switch (option) {
                    case 1:
                        amountInBRL = getAmount("BRL");
                        convertAndPrint(amountInBRL, "USD", rates);
                        break;
                    case 2:
                        amountInBRL = getAmount("BRL");
                        convertAndPrint(amountInBRL, "EUR", rates);
                        break;
                    case 3:
                        amountInBRL = getAmount("BRL");
                        convertAndPrint(amountInBRL, "GBP", rates);
                        break;
                    case 4:
                        amountInBRL = getAmount("BRL");
                        convertAndPrint(amountInBRL, "JPY", rates);
                        break;
                    case 5:
                        amountInBRL = getAmount("BRL");
                        convertAndPrint(amountInBRL, "CHF", rates);
                        break;
                    case 6:
                        amountInBRL = getAmount("BRL");
                        convertAndPrint(amountInBRL, "CNY", rates);
                        break;
                    case 7:
                        exit = true;
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            }
            System.out.println("Obrigado por usar o Conversor de Moedas!");
        } catch (IOException e) {
            System.out.println("Erro ao ler entrada do usuário: " + e.getMessage());
        }
    }

    // Função para obter as taxas de conversão através da API
    private static JSONObject getExchangeRates() throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse.getJSONObject("conversion_rates");
    }

    // Função para ler a opção selecionada pelo usuário
    private static int readOption() {
        int option = 0;
        boolean validInput = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!validInput) {
            try {
                option = Integer.parseInt(reader.readLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, insira um número válido.");
            } catch (IOException e) {
                System.out.println("Erro ao ler entrada do usuário: " + e.getMessage());
            }
        }
        return option;
    }

    // Função para solicitar e obter o valor da moeda de origem
    private static double getAmount(String currency) throws IOException {
        System.out.println("Digite o valor em " + currency + ":");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return Double.parseDouble(reader.readLine());
    }

    // Função para converter e imprimir o resultado da conversão
    private static void convertAndPrint(double amountInBRL, String targetCurrency, JSONObject rates) {
        double exchangeRate = rates.getDouble(targetCurrency);
        double convertedAmount = amountInBRL * exchangeRate;
        System.out.println("O valor em " + targetCurrency + " é: " + convertedAmount);
    }
}
