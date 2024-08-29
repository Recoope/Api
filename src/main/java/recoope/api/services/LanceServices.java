package recoope.api.services;

import org.springframework.stereotype.Service;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.dtos.LanceDto;
import recoope.api.domain.entities.*;
import recoope.api.domain.inputs.LanceParams;
import recoope.api.repository.IEmpresaRepository;
import recoope.api.repository.ILanceRepository;
import recoope.api.repository.ILeilaoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LanceServices {
    private final ILeilaoRepository _leilaoRepository;
    private final ILanceRepository _lanceRepository;
    private final IEmpresaRepository _empresaRepository;

    public LanceServices(ILeilaoRepository leilaoRepository, ILanceRepository lanceRepository, IEmpresaRepository empresaRepository) {
        _leilaoRepository = leilaoRepository;
        _lanceRepository = lanceRepository;
        _empresaRepository = empresaRepository;
    }

    public RespostaApi<LanceDto> darLance(Long idLeilao, LanceParams params) {
        String cnpj = params.getCnpjEmpresa();
        Double valor = params.getValor();
        Empresa empresa;
        Leilao leilao;

        if (cnpj == null || valor == null) return new RespostaApi<>(400, "CNPJ e valor não podem ser informados.");

        Optional<Empresa> empresaOptional = _empresaRepository.findById(cnpj);

        if (empresaOptional.isPresent()){

            Optional<Leilao> leilaoOptional = _leilaoRepository.findById(idLeilao);
            if (leilaoOptional.isPresent()) {
                empresa = empresaOptional.get();
                leilao = leilaoOptional.get();
            } else return new RespostaApi<>(404, "Leilão não encontrado!");
        } else return new RespostaApi<>(404, "Empresa não encontrada!");

        Lance lance = new Lance();

        Lance maiorLanceReq = _lanceRepository.maiorLance(leilao);
        Double maiorLance = maiorLanceReq == null ? 0 : maiorLanceReq.getValor();

        lance.setEmpresa(empresa);
        lance.setLeilao(leilao);

        if (maiorLance < params.getValor()) lance.setValor(valor);
        else if (maiorLance.equals(params.getValor())) return new RespostaApi<>(400, "Esse lance é igual ao maior lance do leilão, ele deve ser maior.");
        else return new RespostaApi<>(400, "Esse lance é menor que o maior lance do leilão.");

        lance.setIdLance(_lanceRepository.lastId() + 1);

        _lanceRepository.save(lance);
        return new RespostaApi<>(201, "Lance atribuido com sucesso!", lance.toDto());
    }

    public RespostaApi<List<Lance>> cancelarLance(String cnpj, Long idLeilao) {
        Optional<Leilao> leilaoOptional = _leilaoRepository.findById(idLeilao);
        Empresa empresa;
        Leilao leilao;

        if (leilaoOptional.isPresent()) {
            Optional<Empresa> empresaOptional = _empresaRepository.findById(cnpj);
            if (empresaOptional.isPresent()) {
                empresa = empresaOptional.get();
                leilao = leilaoOptional.get();
            } else return new RespostaApi<>(404, "Empresa não encontrada!");
        } else return new RespostaApi<>(404, "Leilão não encontrado!");


        List<Lance> lances = _lanceRepository.pegarLances(empresa, leilao);

        if (!lances.isEmpty()) {
            _lanceRepository.deleteAllInBatch(lances);
            return new RespostaApi<>("Lance(s) cancelado com sucesso!", lances);
        } else return new RespostaApi<>(400, "O leilão não possui lances dessa empresa!");
    }

}
