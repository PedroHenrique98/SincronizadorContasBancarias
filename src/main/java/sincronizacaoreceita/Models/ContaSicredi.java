package sincronizacaoreceita.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ContaSicredi {
    private String agencia;
    private String conta;
    private double saldo;
    private String status;
    private boolean sincronizada;

    public String getSaldoFormatoPtBr(){
        return Double.toString(this.saldo).replace(".",",");
    }
}
