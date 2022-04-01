# SincronizadorContasBancarias
Autor: Pedro Henrique Eugenio de Medeiros <br />
Contato: pedro.medeiros.001@acad.pucrs.br

Software desenvolvido com o intuito de testar habilidades provenientes de Java e de mais ferramentas como Spring Boot e Lombok.<br />
O objetivo do teste era simular uma aplicação StandAlone em Spring Boot que se comunicava com um service do Banco Central afim de atualizar dados de contas bancárias do Sicredi, para isto, foi fornecida a classe 'ReceitaService' que cumpria a emulação deste acontecimento.<br />
Como solução, foi implementada em uma arquitetura baseada em MVC, onde por falta de telas, abstriu-se os conceitos de View, permanecendo só a classe controller 'ArquivoController' e a Model 'ContaSicredi', além da classe 'SincronizacaoReceita' que foi encarregada de gerenciar a aplicação ao conter o método inicial (main).<br />

***@Models***<br />
ContaSicredi<br />
A única Model modelada nesta aplicação definia os dados de uma conta no Sicredi, baseando-se no arquivo .csv fornecido como exemplo, definiu-se seus atributos como:
- agencia [String]
- conta [String]
- saldo [double]
- status [String]
- sincronizada [boolean]
<br />Este último adicionado por conta própria afim de deixar armazenado na conta um controle de sincronismo com o Banco Central, possuindo o valor 'true' caso ja tenha sido feita sua atualização com o Service mencionado.<br />

***@Controllers***<br />
ArquivoController<br />
O controller responsável por toda gerência de arquivo, possui apenas um método de acesso publico, o 'processaArquovo', neste, fornecendo o caminho do arquivo .csv a ser processado, realiza chamadas para métodos auxiliares que irão respectivamente:
- Ler o arquivo .csv, gerando instâncias de ContaSicredi para cada linha de conta fornecida
- Enviar dados das contas recebidas ao Service do Banco Central, armazenando se a atualização foi bem sucedida ou não
- Gravar em um novo arquivo os dados das contas em conjunto com a resposta de atualização do Service.

<br />**Entradas Esperadas**<br />
Para execução esperada da aplicação, espera-se um arquivo .csv contendo a seguinte configuração:<br />

agencia;conta;saldo;status<br />
0101;12225-6;100,00;A<br />
...<br />
0101;12226-8;3200,50;A<br />
3202;40011-1;-35,12;I<br />
3202;54001-2;0,00;P<br />
3202;00321-2;34500,00;B<br />
<br />
**Saída Fornecida**<br />
Ao finalizar a aplicação, será criado no diretório onde o projeto foi executado um arquivo .csv similar a seguinte configuração:<br />

agencia;conta;saldo;status;sincronizado<br />
0101;12225-6;100,00;A;true<br />
...<br />
0101;12226-8;3200,50;A;true<br />
3202;40011-1;-35,12;I;true<br />
3202;54001-2;0,00;P;true<br />
3202;00321-2;34500,00;B;true<br />
<br />
***Step by Step***<br />
Para execução da aplicação, primeiramente armazene um arquivo .csv conforme o exemplo contido neste documento em um local conhecido, na sequência, utilize o arquivo .jar 'sincronizacaoreceita.jar' fornecido na raiz deste projeto e em um prompt de comando com privilégios de administrador digite a seguinte linha:<br />
'java -jar sincronizacaoreceita.jar <input-file>'<br />
 onde <input-file> deverá ser substituido pelo caminho completo do arquivo .csv armazenado.<br />
 Caso opte por deixar o arquivo em conjunto com o .jar, basta fornecer o nome do mesmo.
