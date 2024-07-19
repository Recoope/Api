package recoope.api.services;


import org.springframework.stereotype.Service;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.entities.Empresa;
import recoope.api.domain.inputs.EmpresaRegistroParams;
import recoope.api.repository.IEmpresaRepository;

@Service
public class EmpresaServices {
    private final IEmpresaRepository empresaRepository;

    public EmpresaServices(IEmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public RespostaApi<Empresa> cadastrar(EmpresaRegistroParams params) {

        Empresa novaEmpresa = new Empresa();

        novaEmpresa.setNomeEmpresa(params.getNome());

        // Verificação CNPJ.
        String cnpj = params.getCnpj().replaceAll("[./-]", "").trim();

        if (validaCNPJ(cnpj)) novaEmpresa.setCnpjEmpresa(cnpj);
        else return new RespostaApi<>(400, "CNPJ inválido.");

        // Verificação do email.
        String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        String email = params.getEmail().trim();

        if (email.matches(emailRegex)) novaEmpresa.setEmailEmpresa(params.getEmail());
        else return new RespostaApi<>(400, "Email inválido.");

        // Verificação do telefone.
        String telefoneRegex = "^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[0-9])[0-9]{3}-?[0-9]{4}$";
        String telefone = params.getTelefone().replaceAll("[() -]", "").trim();

        if (telefone.matches(telefoneRegex))
            novaEmpresa.setTelefoneEmpresa(params.getTelefone());
        else return new RespostaApi<>(400, "Telefone inválido.");

        // Verificação senha.
        String conf = params.getConfirmacaoSenha();
        String senha = params.getSenha();

        if (conf.equals(senha))
            novaEmpresa.setSenhaEmpresa(params.getSenha());
        else return new RespostaApi<>(400,"As senhas não correspondem.");

        empresaRepository.save(novaEmpresa);
        return new RespostaApi<>("Empresa cadastrada com sucesso!", novaEmpresa);
    }

    private boolean validaCNPJ (String CNPJ) {

        if (CNPJ.length() != 14 || CNPJ.matches("(\\d)\\1{13}")) {
            return false;
        }

        char dig13, dig14;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 2;
            for (i=11; i>=0; i--) {
                num = (int)(CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char)((11-r) + 48);

            sm = 0;
            peso = 2;
            for (i=12; i>=0; i--) {
                num = (int)(CNPJ.charAt(i)- 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char)((11-r) + 48);

            return (dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13));
        } catch (Exception erro) {
            return(false);
        }
    }
}
