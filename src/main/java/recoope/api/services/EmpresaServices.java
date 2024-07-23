package recoope.api.services;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.dtos.EmpresaDto;
import recoope.api.domain.entities.Empresa;
import recoope.api.domain.inputs.EmpresaParams;
import recoope.api.repository.IEmpresaRepository;
import recoope.api.repository.ILanceRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class EmpresaServices {
    private final IEmpresaRepository empresaRepository;
    private final ILanceRepository lanceRepository;

    public EmpresaServices(IEmpresaRepository empresaRepository, ILanceRepository lanceRepository) {
        this.empresaRepository = empresaRepository;
        this.lanceRepository = lanceRepository;
    }

    public RespostaApi<EmpresaDto> pegarPorId(Long id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);

        if (empresa.isPresent()){
            Empresa empresaEnt = empresa.get();

            String tempoConosco = tempoConosco(empresaEnt.getRegistroEmpresa());
            String leiloesParticipados = leiloesParticipados(empresaEnt.getIdEmpresa());

            EmpresaDto empresaDto = new EmpresaDto(
                    empresaEnt.getIdEmpresa(),
                    empresaEnt.getNomeEmpresa(),
                    empresaEnt.getEmailEmpresa(),
                    empresaEnt.getTelefoneEmpresa(),
                    empresaEnt.getCnpjEmpresa(),
                    empresaEnt.getRegistroEmpresa(),
                    tempoConosco,
                    leiloesParticipados
            );

            return new RespostaApi<>("Empresa encontrada com sucesso!", empresaDto);
        }
        else return new RespostaApi<>(404, "Empresa não encontrada!");
    }

    public RespostaApi<Empresa> cadastrar(EmpresaParams params) {
        return validaEmpresa(params, false, null);
    }

    public RespostaApi<Empresa> alterar(Long id, EmpresaParams params) {
        Optional<Empresa> empresaOptional = empresaRepository.findById(id);

        if (empresaOptional.isPresent()) {
            Empresa empresa = empresaOptional.get();

            if (params.getNome() == null) params.setNome(empresa.getNomeEmpresa());
            if (params.getCnpj() == null) params.setCnpj(empresa.getCnpjEmpresa());
            if (params.getEmail() == null) params.setEmail(empresa.getEmailEmpresa());
            if (params.getTelefone() == null) params.setTelefone(empresa.getTelefoneEmpresa());
            if (params.getSenha() == null) params.setSenha(empresa.getSenhaEmpresa());

            return validaEmpresa(params, true, empresa);

        } else {
            return new RespostaApi<>(404, "Empresa não encontrada!");
        }
    }

    public RespostaApi<Empresa> remover(Long id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);

        if (empresa.isPresent()) {
            empresaRepository.delete(empresa.get());
            return new RespostaApi<>("Empresa removida com sucesso!", empresa.get());
        } else return new RespostaApi<>(404, "Empresa não encontrada!");
    }

    private RespostaApi<Empresa> validaEmpresa(EmpresaParams params, boolean alteracao, Empresa empresaAlterada) {

        String nome, cnpj, email, telefone, conf, senha;

        try {
            nome = params.getNome().trim();
            cnpj = params.getCnpj().replaceAll("[./-]", "").trim();
            email = params.getEmail().trim();
            telefone = params.getTelefone().replaceAll("[() -]", "").trim();
            conf = params.getConfirmacaoSenha();
            senha = params.getSenha();
        } catch (NullPointerException npe) {
            return new RespostaApi<>(500, "Não devem ser enviados parametros nulos.");
        }

        Empresa empresaValidada = new Empresa();

        if (nome.length() > 3) empresaValidada.setNomeEmpresa(nome);
        else return new RespostaApi<>(400, "O nome da empresa deve conter pelo menos 3 caracteres.");

        // Verificação CNPJ.
        if (validaCNPJ(cnpj)) empresaValidada.setCnpjEmpresa(cnpj);
        else return new RespostaApi<>(400, "CNPJ inválido.");

        // Verificação do email.
        String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

        if (email.matches(emailRegex)) empresaValidada.setEmailEmpresa(email);
        else return new RespostaApi<>(400, "Email inválido.");

        // Verificação do telefone.
        String telefoneRegex = "^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[0-9])[0-9]{3}-?[0-9]{4}$";

        if (telefone.matches(telefoneRegex))
            empresaValidada.setTelefoneEmpresa(telefone);
        else return new RespostaApi<>(400, "Telefone inválido.");

        // Verificação senha.
        if (senha.equals(conf))
            empresaValidada.setSenhaEmpresa(senha);
        else if (alteracao && (empresaAlterada.getSenhaEmpresa().equals(senha)))
            empresaValidada.setSenhaEmpresa(empresaAlterada.getSenhaEmpresa());
        else return new RespostaApi<>(400,"As senhas não correspondem.");

        if (!alteracao) {
            // Registrando data do cadastro
            empresaValidada.setRegistroEmpresa(new Date());

            empresaRepository.save(empresaValidada);
            return new RespostaApi<>(201, "Empresa cadastrada com sucesso!", empresaValidada);
        } else {
            empresaValidada.setIdEmpresa(empresaAlterada.getIdEmpresa());
            empresaValidada.setRegistroEmpresa(empresaAlterada.getRegistroEmpresa());

            empresaRepository.save(empresaValidada);
            return new RespostaApi<>(201, "Empresa atualizada com sucesso!", empresaValidada);
        }
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

    private String tempoConosco(Date dataRegistro) {
        Date dataAtual = new Date();

        int qtdMeses, qtdAnos;
        qtdAnos = dataAtual.getYear() - dataRegistro.getYear();

        int mesAtual = dataAtual.getMonth() + 1;
        int mesRegistro = dataRegistro.getMonth() + 1;

        if (mesAtual < mesRegistro) qtdMeses = (12 - mesRegistro) + mesAtual;
        else qtdMeses = mesAtual - mesRegistro;

        if (qtdMeses == 0) qtdAnos += 1;

        StringBuilder tempoConosco = new StringBuilder();
        if (qtdAnos != 0) {
            if (qtdAnos == 1) tempoConosco.append("1 ano");
            else tempoConosco.append(String.format("%d anos", qtdAnos));
        }
        if (qtdMeses != 0) {
            if (qtdAnos != 0) tempoConosco.append(" e ");

            if (qtdAnos == 1) tempoConosco.append("1 mês");
            else tempoConosco.append(String.format("%d meses", qtdMeses));
        }

        return tempoConosco.toString();
    }

    private String leiloesParticipados(Long id) {
        int lp = lanceRepository.empresaLeiloesParticipados(id);
        return lp == 1 ? lp + "leilão." : lp + " leilões.";
    }


}
