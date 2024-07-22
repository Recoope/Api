package recoope.api.domain.dtos;


import java.util.Date;

public class EmpresaDto {
    private Long idEmpresa;

    private String nomeEmpresa;

    private String emailEmpresa;

    private String telefoneEmpresa;

    private String cnpjEmpresa;

    private Date registroEmpresa;

    private String tempoConosco;

    private String leiloesParticipados;

    public EmpresaDto(Long idEmpresa, String nomeEmpresa, String emailEmpresa, String telefoneEmpresa, String cnpjEmpresa, Date registroEmpresa, String tempoConosco, String leiloesParticipados) {
        this.idEmpresa = idEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.emailEmpresa = emailEmpresa;
        this.telefoneEmpresa = telefoneEmpresa;
        this.cnpjEmpresa = cnpjEmpresa;
        this.registroEmpresa = registroEmpresa;
        this.tempoConosco = tempoConosco;
        this.leiloesParticipados = leiloesParticipados;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getEmailEmpresa() {
        return emailEmpresa;
    }

    public void setEmailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
    }

    public String getTelefoneEmpresa() {
        return telefoneEmpresa;
    }

    public void setTelefoneEmpresa(String telefoneEmpresa) {
        this.telefoneEmpresa = telefoneEmpresa;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public void setCnpjEmpresa(String cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public Date getRegistroEmpresa() {
        return registroEmpresa;
    }

    public void setRegistroEmpresa(Date registroEmpresa) {
        this.registroEmpresa = registroEmpresa;
    }

    public String getTempoConosco() {
        return tempoConosco;
    }

    public void setTempoConosco(String tempoConosco) {
        this.tempoConosco = tempoConosco;
    }

    public String getLeiloesParticipados() {
        return leiloesParticipados;
    }

    public void setLeiloesParticipados(String leiloesParticipados) {
        this.leiloesParticipados = leiloesParticipados;
    }
}
