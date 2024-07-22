package recoope.api.services;


import org.springframework.stereotype.Service;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.dtos.EmpresaDto;
import recoope.api.domain.entities.Empresa;
import recoope.api.domain.inputs.EmpresaRegistroParams;
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

        // Registrando data do cadastro
        novaEmpresa.setRegistroEmpresa(new Date());

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
