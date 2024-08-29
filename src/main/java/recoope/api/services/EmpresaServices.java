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

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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
        boolean isViaCnpj = Validacoes.CNPJ(params.getCnpjOuEmail());
        boolean isViaEmail = Validacoes.EMAIL(params.getCnpjOuEmail());

        if (isViaCnpj || isViaEmail) {
            Optional<Empresa> empresa = _empresaRepository.login(params.getCnpjOuEmail(), params.getSenha());

            if (empresa.isPresent())
                return new RespostaApi<>("Login feito com sucesso!", empresa.get());
            else return isViaCnpj ?
                new RespostaApi<>(401, "O CNPJ fornecido não possui uma correspondência ou a senha está incorreta.") :
                new RespostaApi<>(401, "O E-mail fornecido não possui uma correspondência ou a senha está incorreta.");
        } else return new RespostaApi<>(400, "Parâmetro fornecido não é um E-mail ou CNPJ.");
    }

    public RespostaApi<EmpresaDto> pegarPorId(String cnpj) {
        Optional<Empresa> empresa = _empresaRepository.findById(cnpj);

        if (empresa.isPresent()){
            Empresa empresaEnt = empresa.get();

            String tempoConosco = tempoConosco(empresaEnt.getRegistro());
            String leiloesParticipados = leiloesParticipados(empresaEnt.getCnpj());

            EmpresaDto empresaDto = new EmpresaDto(
                    empresaEnt.getNome(),
                    empresaEnt.getEmail(),
                    empresaEnt.getTelefone(),
                    empresaEnt.getCnpj(),
                    empresaEnt.getRegistro(),
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
        if (Validacoes.NOME(nome)) novaEmpresa.setNome(nome);
        else return new RespostaApi<>(400, "O nome da empresa deve conter pelo menos 3 caracteres.");

        // Verificação CNPJ.
        if (Validacoes.CNPJ(cnpj)) {
            if (_empresaRepository.findById(cnpj).isEmpty()) novaEmpresa.setCnpj(cnpj);
            else return new RespostaApi<>(400, "CNPJ já existente.");
        } else return new RespostaApi<>(400, "CNPJ inválido.");

        // Verificação do email.
        if (Validacoes.EMAIL(email)) {
            if (_empresaRepository.findByTelefoneOuEmail(email).isEmpty()) novaEmpresa.setEmail(email);
            else return new RespostaApi<>(400, "Email já existente.");
        } else return new RespostaApi<>(400, "Email inválido.");

        // Verificação do telefone.
        if (Validacoes.TEL(telefone)) {
            if (_empresaRepository.findByTelefoneOuEmail(telefone).isEmpty()) novaEmpresa.setTelefone(telefone);
            else return new RespostaApi<>(400, "Telefone já existente.");
        } else return new RespostaApi<>(400, "Telefone inválido.");

        // Verificação senha.
        if (senha.equals(conf)) novaEmpresa.setSenha(senha);
        else return new RespostaApi<>(400,"As senhas não correspondem.");

        // Registrando data do cadastro
        novaEmpresa.setRegistro(new Date());

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

            if (params.getNome() == null) nome = empresa.getNome();
            else nome = params.getNome().trim();
            if (params.getEmail() == null) email = empresa.getEmail();
            else {
                email = params.getEmail().trim();
                emailAlterado = true;
            }
            if (params.getTelefone() == null) telefone = empresa.getTelefone();
            else {
                telefone = params.getTelefone().replaceAll("[() -]", "").trim();
                telefoneAlterado = true;
            }

        } else {
            return new RespostaApi<>(404, "Empresa não encontrada!");
        }

        // Verificação nome.
        if (Validacoes.NOME(nome)) empresaAlterada.setNome(nome);
        else return new RespostaApi<>(400, "O nome da empresa deve conter pelo menos 3 caracteres.");

        // Verificação do email.
        if (Validacoes.EMAIL(email)) {
            if (!emailAlterado || _empresaRepository.findByTelefoneOuEmail(email).isEmpty()) empresaAlterada.setEmail(email);
            else return new RespostaApi<>(400, "Email já existente.");
        } else return new RespostaApi<>(400, "Email inválido.");

        // Verificação do telefone.
        if (Validacoes.TEL(telefone)) {
            if (!telefoneAlterado || _empresaRepository.findByTelefoneOuEmail(telefone).isEmpty()) empresaAlterada.setTelefone(telefone);
            else return new RespostaApi<>(400, "Telefone já existente.");
        } else return new RespostaApi<>(400, "Telefone inválido.");


        empresaAlterada.setCnpj(empresaAlterada.getCnpj());
        empresaAlterada.setRegistro(empresaAlterada.getRegistro());

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
        LocalDate dataRegistroLocalDate = dataRegistro.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dataAtual = LocalDate.now();

        Period periodo = Period.between(dataRegistroLocalDate, dataAtual);

        int qtdMeses = periodo.getMonths();
        int qtdAnos = periodo.getYears();

        return switch (qtdAnos) {
            case 0 -> qtdMeses <= 1 ? "1 mês." : String.format("%d meses.", qtdMeses);
            case 1 -> "1 ano.";
            default -> String.format("%d anos.", qtdAnos);
        };

    }

    private String leiloesParticipados(String id) {
        int lp = _lanceRepository.empresaLeiloesParticipados(id);
        return lp == 1 ? lp + "leilão." : lp + " leilões.";
    }
}
