package recoope.api.services;


import org.springframework.stereotype.Service;
import recoope.api.domain.Mensagens;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.Validacoes;
import recoope.api.domain.dtos.EmpresaDto;
import recoope.api.domain.entities.Empresa;
import recoope.api.domain.inputs.AlterarEmpresaParams;
import recoope.api.domain.inputs.EmpresaParams;
import recoope.api.repository.IEmpresaRepository;
import recoope.api.repository.ILanceRepository;

import java.util.Optional;

@Service
public class EmpresaServices {
    private final IEmpresaRepository _empresaRepository;
    private final ILanceRepository _lanceRepository;

    public EmpresaServices(IEmpresaRepository empresaRepository, ILanceRepository lanceRepository) {
        this._empresaRepository = empresaRepository;
        this._lanceRepository = lanceRepository;
    }

    public RespostaApi<EmpresaDto> pegarPorId(String cnpj) {
        Optional<Empresa> empresa = _empresaRepository.findById(cnpj);

        if (empresa.isPresent()){
            Empresa empresaEnt = empresa.get();

            String leiloesParticipados = leiloesParticipados(empresaEnt.getCnpj());

            EmpresaDto empresaDto = new EmpresaDto(
                    empresaEnt.getNome(),
                    empresaEnt.getEmail(),
                    empresaEnt.getTelefone(),
                    empresaEnt.getCnpj(),
                    leiloesParticipados
            );

            return new RespostaApi<>(Mensagens.EMPRESA_ENCONTRADA, empresaDto);
        }
        else return new RespostaApi<>(404, Mensagens.EMPRESA_NAO_ENCONTRADA);
    }

    public RespostaApi<Empresa> cadastrar(EmpresaParams params) {
        String nome, cnpj, email, telefone, conf, senha;

        try {
            nome = params.getNome().trim();
            cnpj = params.getCnpj().replaceAll("[./-]", "").trim();
            email = params.getEmail().trim();
            telefone = params.getTelefone().replaceAll("[() -]", "").trim();
            conf = params.getConfirmacaoSenha();
            senha = params.getSenha();

            if (nome.isEmpty() || cnpj.isEmpty() || email.isEmpty() ||
                    telefone.isEmpty() || conf.isEmpty() || senha.isEmpty())
                throw new NullPointerException();

        } catch (NullPointerException npe) {
            return new RespostaApi<>(400, Mensagens.PARAMETROS_VAZIOS);
        }

        Empresa emp = new Empresa(cnpj, nome, email, senha, telefone);

        // Verificação nome.
        if (!Validacoes.NOME(nome)) return new RespostaApi<>(400, Mensagens.NOME_INVALIDO);
        // Verificação CNPJ.
        if (!Validacoes.CNPJ(cnpj)) return new RespostaApi<>(400, Mensagens.CNPJ_INVALIDO);
        if (_empresaRepository.findById(cnpj).isPresent()) return new RespostaApi<>(400, Mensagens.CNPJ_EXISTENTE);
        // Verificação do email.
        if (!Validacoes.EMAIL(email)) return new RespostaApi<>(400, Mensagens.EMAIL_INVALIDO);
        if (!_empresaRepository.findByTelefoneOuEmail(email).isEmpty()) return new RespostaApi<>(400, Mensagens.EMAIL_EXISTENTE);
        // Verificação do telefone.
        if (!Validacoes.TEL(telefone)) return new RespostaApi<>(400, Mensagens.TELEFONE_INVALIDO);
        if (!_empresaRepository.findByTelefoneOuEmail(telefone).isEmpty()) return new RespostaApi<>(400, Mensagens.TELEFONE_EXISTENTE);
        // Verificação senha.
        if (!Validacoes.SENHA(senha)) return new RespostaApi<>(400, Mensagens.SENHA_INVALIDA);
        if (!senha.equals(conf)) return new RespostaApi<>(400, Mensagens.SENHA_NAO_CORREPONDENTE);

        _empresaRepository.inserir(cnpj, nome, email, senha, telefone);
        return new RespostaApi<>(201, Mensagens.EMPRESA_CADASTRADA, emp);
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
            return new RespostaApi<>(404, Mensagens.EMPRESA_NAO_ENCONTRADA);
        }

        // Verificação nome.
        if (Validacoes.NOME(nome)) empresaAlterada.setNome(nome);
        else return new RespostaApi<>(400, Mensagens.NOME_INVALIDO);

        // Verificação do email.
        if (Validacoes.EMAIL(email)) {
            if (!emailAlterado || _empresaRepository.findByTelefoneOuEmail(email).isEmpty()) empresaAlterada.setEmail(email);
            else return new RespostaApi<>(400, Mensagens.EMAIL_EXISTENTE);
        } else return new RespostaApi<>(400, Mensagens.EMAIL_INVALIDO);

        // Verificação do telefone.
        if (Validacoes.TEL(telefone)) {
            if (!telefoneAlterado || _empresaRepository.findByTelefoneOuEmail(telefone).isEmpty()) empresaAlterada.setTelefone(telefone);
            else return new RespostaApi<>(400, Mensagens.TELEFONE_EXISTENTE);
        } else return new RespostaApi<>(400, Mensagens.TELEFONE_INVALIDO);


        empresaAlterada.setCnpj(empresaAlterada.getCnpj());

        _empresaRepository.save(empresaAlterada);
        return new RespostaApi<>(201, Mensagens.EMPRESA_ATUALIZADA, empresaAlterada);
    }

    public RespostaApi<Empresa> remover(String cnpj) {
        Optional<Empresa> empresa = _empresaRepository.findById(cnpj);

        if (empresa.isPresent()) {
            _empresaRepository.delete(empresa.get());
            return new RespostaApi<>(Mensagens.EMPRESA_REMOVIDA, empresa.get());
        } else return new RespostaApi<>(404, Mensagens.EMPRESA_NAO_ENCONTRADA);
    }

    private String leiloesParticipados(String id) {
        int lp = _lanceRepository.empresaLeiloesParticipados(id);
        return lp == 1 ? lp + "leilão." : lp + " leilões.";
    }
}
