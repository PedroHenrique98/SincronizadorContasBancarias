package sincronizacaoreceita.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import receitaservice.ReceitaService;
import sincronizacaoreceita.Models.ContaSicredi;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArquivoController {
    public void processaArquivo(String caminho){
        try {
            List<ContaSicredi> contasSicredi = extraiContasSicrediDoArquivo(caminho);
            List<ContaSicredi> contasSicrediAtualizadas = atualizaSincronizacaoDeContas(contasSicredi);
            gravaArquivoRetorno(contasSicrediAtualizadas);
        } catch (IOException e) {
            System.out.println("Houve uma falha ao tentar ler o arquivo csv! Favor, verificar o caminho fornecido.");
            System.out.println("DICA: Deixe o arquivo .csv na mesma pasta onde está executando a aplicação ou passe o caminho completo para chegar até ele!");
        } catch (InterruptedException e) {
            System.out.println("Houve uma falha na comunicação ao serviço de atualizações de contas, favor tentar novamente mais tarde.");
        }
    }

    private List<ContaSicredi> extraiContasSicrediDoArquivo(String caminho) throws IOException {
        List<ContaSicredi> contas = new ArrayList<>();
        BufferedReader buffLeitura = new BufferedReader(new FileReader(caminho));
        buffLeitura.readLine();
        String linha = buffLeitura.readLine();
        while (linha != null) {
            ContaSicredi conta = extraiContaSicrediDaLinha(linha);
            contas.add(conta);
            linha = buffLeitura.readLine();
        }
        buffLeitura.close();
        return contas;
    }

    private ContaSicredi extraiContaSicrediDaLinha(String dadosConta) {
        String[] dadosDesformatados = dadosConta.split(";");
        String agencia = dadosDesformatados[0];
        String conta = dadosDesformatados[1];
        double saldo = Double.parseDouble(dadosDesformatados[2].replace(",","."));
        String status = dadosDesformatados[3];
        boolean sincronizada = false;

        return new ContaSicredi(agencia, conta, saldo, status, sincronizada);
    }

    private List<ContaSicredi> atualizaSincronizacaoDeContas(List<ContaSicredi> contas) throws InterruptedException{
        ReceitaService receitaService = new ReceitaService();
        for (ContaSicredi contaSicredi : contas) {
            contaSicredi.setSincronizada(receitaService.atualizarConta(contaSicredi.getAgencia(),
                                                                       contaSicredi.getConta().replace("-", ""),
                                                                       contaSicredi.getSaldo(),
                                                                       contaSicredi.getStatus()));
        }
        return contas;
    }

    private void gravaArquivoRetorno(List<ContaSicredi> contasSincronizadas) throws IOException {
        String nomeDoArquivo = "info_contas_sicredi_sincronizadas" + LocalDate.now() + ".csv";
        PrintWriter gravaArquivo = new PrintWriter(new FileWriter(nomeDoArquivo));
        gravaArquivo.append("agencia;conta;saldo;status;sincronizada\n");
        System.out.println("Aguarde! Enviando contas para o service do Banco Central!");
        for(ContaSicredi contaSicredi : contasSincronizadas) {
            gravaArquivo.append(contaSicredi.getAgencia())
                    .append(contaSicredi.getConta()).append(";")
                    .append(contaSicredi.getSaldoFormatoPtBr()).append(";")
                    .append(contaSicredi.getStatus()).append(";")
                    .append(Boolean.toString(contaSicredi.isSincronizada()))
                    .append("\n");
            gravaArquivo.flush();
            System.out.println(".");
        }
        gravaArquivo.close();
        System.out.println("Contas atualizadas com sucesso!! Você pode verificar o resultado no arquivo que gravamos para você na pasta onde a aplicação foi executada!");
    }
}