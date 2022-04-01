package sincronizacaoreceita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sincronizacaoreceita.Controllers.ArquivoController;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

/**
 * @author pedro_henrique_medeiros<pedro.medeiros.001@acad.pucrs.br>
 */

@SpringBootApplication
@RequiredArgsConstructor
public class SincronizacaoReceita implements CommandLineRunner {

	private final ArquivoController arquivoController;
	public static void main(String[] args) {
		if(args.length != 1)
		{
			System.out.println("Ops! Não reconheci sua tentativa de inicialização, tente novamente da seguinte maneira:");
			System.out.println("java -jar sincronizacaoreceita.jar <input-file>");
			return;
		}
		SpringApplication.run(SincronizacaoReceita.class, args);
	}

	public void run(String... args) {
		arquivoController.processaArquivo(args[0]);
	}
}