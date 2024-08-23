package recoope.api.domain.inputs;

import jakarta.validation.constraints.NotNull;

public class LanceParams {
    @NotNull
    private String cnpjEmpresa;

    private Double valor;

    public LanceParams(String cnpjEmpresa, Double valor) {
        this.cnpjEmpresa = cnpjEmpresa;
        this.valor = valor;
    }

    public String getCnpj() {
        return cnpjEmpresa;
    }

    public void setCnpj(String empresaCnpj) {
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
