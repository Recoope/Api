package recoope.api.services;


import org.springframework.stereotype.Service;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.Validacoes;
import recoope.api.domain.dtos.EmpresaDto;
import recoope.api.domain.entities.Empresa;
import recoope.api.domain.inputs.AlterarEmpresaParams;
import recoope.api.domain.inputs.EmpresaParams;
import recoope.api.domain.inputs.LoginParams;
import recoope.api.repository.IEmpresaRepository;
import recoope.api.repository.ILanceRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class EmpresaServices {
    private final IEmpresaRepository _empresaRepository;
    private final ILanceRepository _lanceRepository;

    public EmpresaServices(IEmpresaRepository empresaRepository, ILanceRepository lanceRepository) {
        this._empresaRepository = empresaRepository;
        this._lanceRepository = lanceRepository;
    }

    public RespostaApi<Empresa> login(LoginParams params) {
        boolean cnpj = Validacoes.CNPJ(params.getCnpjOuEmail());
        boolean email = Validacoes.EMAIL(params.getCnpjOuEmail());

        if (cnpj || email) {
            Optional<Empresa> empresa = _empresaRepository.login(params.getCnpjOuEmail(), params.getSenha());

            if (empresa.isPresent())
                return new RespostaApi<>("Login feito com sucesso!", empresa.get());
            else return cnpj ?
                new RespostaApi<>(404, "O CNPJ fornecido não possui uma correspondência ou a senha está incorreta.") :
                new RespostaApi<>(404, "O E-mail fornecido não possui uma correspondência ou a senha está incorreta.");
        } else return new RespostaApi<>(400, "Parâmetro fornecido não é um E-mail ou CNPJ.");
    }

    public RespostaApi<EmpresaDto> pegarPorId(String cnpj) {
        Optional<Empresa> empresa = _empresaRepository.findById(cnpj);

        if (empresa.isPresent()){
            Empresa empresaEnt = empresa.get();

            String tempoConosco = tempoConosco(empresaEnt.getRegistroEmpresa());
            String leiloesParticipados = leiloesParticipados(empresaEnt.getCnpjEmpresa());

            EmpresaDto empresaDto = new EmpresaDto(
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
        String nome, cnpj, email, telefone, conf, senha;
        Empresa novaEmpresa = new Empresa();

        try {
            nome = params.getNome().trim();
            cnpj = params.getCnpj().replaceAll("[./-]", "").trim();
            email = params.getEmail().trim();
            telefone = params.getTelefone().replaceAll("[() -]", "").trim();
            conf = params.getConfirmacaoSenha();
            senha = params.getSenha();
        } catch (NullPointerException npe) {
            return new RespostaApi<>(400, "Não devem ser enviados parametros nulos.");
        }

        // Verificação nome.
        if (Validacoes.NOME(nome)) novaEmpresa.setNomeEmpresa(nome);
        else return new RespostaApi<>(400, "O nome da empresa deve conter pelo menos 3 caracteres.");

        // Verificação CNPJ.
        if (Validacoes.CNPJ(cnpj)) {
            if (_empresaRepository.findById(cnpj).isEmpty()) novaEmpresa.setCnpjEmpresa(cnpj);
            else return new RespostaApi<>(400, "CNPJ já existente.");
        } else return new RespostaApi<>(400, "CNPJ inválido.");

        // Verificação do email.
        if (Validacoes.EMAIL(email)) {
            if (_empresaRepository.findByTelefoneOuEmail(email).isEmpty()) novaEmpresa.setEmailEmpresa(email);
            else return new RespostaApi<>(400, "Email já existente.");
        } else return new RespostaApi<>(400, "Email inválido.");

        // Verificação do telefone.
        if (Validacoes.TEL(telefone)) {
            if (_empresaRepository.findByTelefoneOuEmail(telefone).isEmpty()) novaEmpresa.setTelefoneEmpresa(telefone);
            else return new RespostaApi<>(400, "Telefone já existente.");
        } else return new RespostaApi<>(400, "Telefone inválido.");

        // Verificação senha.
        if (senha.equals(conf)) novaEmpresa.setSenhaEmpresa(senha);
        else return new RespostaApi<>(400,"As senhas não correspondem.");

        // Registrando data do cadastro
        novaEmpresa.setRegistroEmpresa(new Date());

        _empresaRepository.save(novaEmpresa);
        return new RespostaApi<>(201, "Empresa cadastrada com sucesso!", novaEmpresa);
    }

    public RespostaApi<Empresa> alterar(String cnpj, AlterarEmpresaParams params) {
        Optional<Empresa> empresaOptional = _empresaRepository.findById(cnpj);
        String nome, email, telefone;
        boolean emailAlterado = false, telefoneAlterado = false;

        Empresa empresaAlterada = new Empresa();

        if (empresaOptional.isPresent()) {
            Empresa empresa = empresaOptional.get();

            if (params.getNome() == null) nome = empresa.getNomeEmpresa();
            else nome = params.getNome().trim();
            if (params.getEmail() == null) email = empresa.getEmailEmpresa();
            else {
                email = params.getEmail().trim();
                emailAlterado = true;
            }
            if (params.getTelefone() == null) telefone = empresa.getTelefoneEmpresa();
            else {
                telefone = params.getTelefone().replaceAll("[() -]", "").trim();
                telefoneAlterado = true;
            }

        } else {
            return new RespostaApi<>(404, "Empresa não encontrada!");
        }

        // Verificação nome.
        if (Validacoes.NOME(nome)) empresaAlterada.setNomeEmpresa(nome);
        else return new RespostaApi<>(400, "O nome da empresa deve conter pelo menos 3 caracteres.");

        // Verificação do email.
        if (Validacoes.EMAIL(email)) {
            if (!emailAlterado || _empresaRepository.findByTelefoneOuEmail(email).isEmpty()) empresaAlterada.setEmailEmpresa(email);
            else return new RespostaApi<>(400, "Email já existente.");
        } else return new RespostaApi<>(400, "Email inválido.");

        // Verificação do telefone.
        if (Validacoes.TEL(telefone)) {
            if (!telefoneAlterado || _empresaRepository.findByTelefoneOuEmail(telefone).isEmpty()) empresaAlterada.setTelefoneEmpresa(telefone);
            else return new RespostaApi<>(400, "Telefone já existente.");
        } else return new RespostaApi<>(400, "Telefone inválido.");


        empresaAlterada.setCnpjEmpresa(empresaAlterada.getCnpjEmpresa());
        empresaAlterada.setRegistroEmpresa(empresaAlterada.getRegistroEmpresa());

        _empresaRepository.save(empresaAlterada);
        return new RespostaApi<>(201, "Empresa atualizada com sucesso!", empresaAlterada);
    }

    public RespostaApi<Empresa> remover(String cnpj) {
        Optional<Empresa> empresa = _empresaRepository.findById(cnpj);

        if (empresa.isPresent()) {
            _empresaRepository.delete(empresa.get());
            return new RespostaApi<>("Empresa removida com sucesso!", empresa.get());
        } else return new RespostaApi<>(404, "Empresa não encontrada!");
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

    private String leiloesParticipados(String id) {
        int lp = _lanceRepository.empresaLeiloesParticipados(id);
        return lp == 1 ? lp + "leilão." : lp + " leilões.";
    }
}
