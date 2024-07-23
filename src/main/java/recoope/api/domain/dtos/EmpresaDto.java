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

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public String getEmailEmpresa() {
        return emailEmpresa;
    }

    public String getTelefoneEmpresa() {
        return telefoneEmpresa;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public Date getRegistroEmpresa() {
        return registroEmpresa;
    }

    public String getTempoConosco() {
        return tempoConosco;
    }

    public String getLeiloesParticipados() {
        return leiloesParticipados;
    }

}
