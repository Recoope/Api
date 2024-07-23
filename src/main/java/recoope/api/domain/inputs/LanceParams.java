package recoope.api.domain.inputs;

public class LanceParams {
    private Long idEmpresa;

    private Double valor;

    public LanceParams(Long idEmpresa, Double valor) {
        this.idEmpresa = idEmpresa;
        this.valor = valor;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long empresaId) {
        this.idEmpresa = empresaId;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
