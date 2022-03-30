package sincronizacaoreceita.Controllers;

import sincronizacaoreceita.Models.ContaSicredi;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArquivoController {
    public static List<ContaSicredi> processaArquivo(String caminho){
        ArrayList<ContaSicredi> contas = new ArrayList<>();

        try {
            BufferedReader buffLeitura = new BufferedReader(new FileReader(caminho));
            String linha = buffLeitura.readLine();
            linha = buffLeitura.readLine();
            while (linha != null)
            {
                ContaSicredi conta = extraiContaSicredi(linha);
                contas.add(conta);
                linha = buffLeitura.readLine();
            }

            buffLeitura.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return contas;
    }

    public static FileWriter gravaArquivoRetorno(List<ContaSicredi> contasSincronizadas)
    {
        try {
            FileWriter arquivo = new FileWriter("src\\main\\resources\\arquivosSaida\\info_contas_sicredi_sincronizadas" + LocalDate.now() + ".csv");
            PrintWriter gravaArquivo = new PrintWriter(arquivo);
            gravaArquivo.append("agencia;conta;saldo;status;sincronizada\n");
            for(ContaSicredi contaSicredi : contasSincronizadas)
            {
                gravaArquivo.append(contaSicredi.getAgencia())
                        .append(contaSicredi.getConta()).append(";")
                        .append(contaSicredi.getSaldoFormatado()).append(";")
                        .append(contaSicredi.getStatus()).append(";")
                        .append(Boolean.toString(contaSicredi.isSincronizada())).append(";")
                        .append("\n");
                gravaArquivo.flush();
            }
            gravaArquivo.close();

            return arquivo;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static ContaSicredi extraiContaSicredi(String dadosArquivo)
    {
        String[] dadosDesformatados = dadosArquivo.split(";");
        String agencia = dadosDesformatados[0];
        String conta = dadosDesformatados[1];
        double saldo = Double.parseDouble(dadosDesformatados[2].replace(",","."));
        String status = dadosDesformatados[3];

        ContaSicredi contaSicredi = new ContaSicredi(agencia, conta, saldo, status);

        return contaSicredi;
    }
}
