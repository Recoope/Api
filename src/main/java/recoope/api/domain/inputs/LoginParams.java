package recoope.api.domain.inputs;

public class LoginParams {
    private String cnpjOuEmail;
    private String senha;

    public LoginParams(String cnpjOuEmail, String senha) {
        this.cnpjOuEmail = cnpjOuEmail;
        this.senha = senha;
    }

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
