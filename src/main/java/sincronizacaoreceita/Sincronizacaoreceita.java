package sincronizacaoreceita;

import receitaservice.ReceitaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sincronizacaoreceita.Controllers.ArquivoController;
import sincronizacaoreceita.Models.ContaSicredi;

import java.util.List;

@SpringBootApplication
public class Sincronizacaoreceita {

	public static void main(String[] args) {
		/*if(args.length != 1)
		{
			System.out.println("Ops! Não reconheci sua tentativa de inicialização, tente novamente da seguinte maneira:");
			System.out.println("java -jar SincronizacaoReceita <input-file>");
			return;
		}*/
		SpringApplication.run(Sincronizacaoreceita.class, args);

		List<ContaSicredi> contas = ArquivoController.processaArquivo("src\\main\\resources\\arquivosEntrada\\info_contas_sicredi.csv");

		try {
			ReceitaService receitaService = new ReceitaService();
			for(ContaSicredi contaSicredi : contas)
			{
				contaSicredi.setSincronizada(receitaService.atualizarConta(contaSicredi.getAgencia(),
																		   contaSicredi.getConta(),
																		   contaSicredi.getSaldo(),
																		   contaSicredi.getStatus()));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ArquivoController.gravaArquivoRetorno(contas);
	}
}
