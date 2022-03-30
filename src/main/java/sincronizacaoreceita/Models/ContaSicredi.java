package sincronizacaoreceita.Models;

public class ContaSicredi {
    private String Agencia;
    private String Conta;
    private double Saldo;
    private String Status;
    private boolean Sincronizada;

    public ContaSicredi(String agencia, String conta, double saldo, String status)
    {
        this.Agencia = agencia;
        this.Conta = conta;
        this.Saldo = saldo;
        this.Status = status;
        this.Sincronizada = false;
    }

    public String getAgencia() {
        return Agencia;
    }

    public void setAgencia(String agencia) {
        Agencia = agencia;
    }

    public String getConta() {
        return Conta;
    }

    public void setConta(String conta) {
        Conta = conta;
    }

    public double getSaldo() {
        return Saldo;
    }

    public String getSaldoFormatado(){
        return Double.toString(Saldo).replace(".",",");
    }

    public void setSaldo(double saldo) {
        Saldo = saldo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public boolean isSincronizada() {
        return Sincronizada;
    }

    public void setSincronizada(boolean sincronizada) {
        Sincronizada = sincronizada;
    }
}
