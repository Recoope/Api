package recoope.api.domain.inputs;

public class LoginParams {
    private String cnpjOuEmail;
    private String senha;

    public String getCnpjOuEmail() {
        return cnpjOuEmail;
    }

    public void setCnpjOuEmail(String cnpjOuEmail) {
        this.cnpjOuEmail = cnpjOuEmail;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
