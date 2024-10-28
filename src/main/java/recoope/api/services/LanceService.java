package recoope.api.services;

import org.springframework.stereotype.Service;
import recoope.api.domain.Mensagens;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.dtos.LanceDto;
import recoope.api.domain.entities.Empresa;
import recoope.api.domain.entities.Lance;
import recoope.api.domain.entities.Leilao;
import recoope.api.domain.inputs.LanceParams;
import recoope.api.repository.IEmpresaRepository;
import recoope.api.repository.ILanceRepository;
import recoope.api.repository.ILeilaoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LanceService {
    private final ILeilaoRepository _leilaoRepository;
    private final ILanceRepository _lanceRepository;
    private final IEmpresaRepository _empresaRepository;

    public LanceService(ILeilaoRepository leilaoRepository, ILanceRepository lanceRepository, IEmpresaRepository empresaRepository) {
        _leilaoRepository = leilaoRepository;
        _lanceRepository = lanceRepository;
        _empresaRepository = empresaRepository;
    }

    public RespostaApi<LanceDto> darLance(Long idLeilao, LanceParams params) {
        String cnpj = params.getCnpjEmpresa();
        Double valor = params.getValor();
        Empresa empresa;
        Leilao leilao;

        if (cnpj == null || valor == null) return new RespostaApi<>(400, Mensagens.LANCE_PARAM_INVALIDOS);

        Optional<Leilao> leilaoOptional = _leilaoRepository.findById(idLeilao);

        if (leilaoOptional.isPresent()){

            Optional<Empresa> empresaOptional = _empresaRepository.findById(cnpj);
            if (empresaOptional.isPresent()) {
                empresa = empresaOptional.get();
                leilao = leilaoOptional.get();
            } else return new RespostaApi<>(404, Mensagens.EMPRESA_NAO_ENCONTRADA);
        } else return new RespostaApi<>(404, Mensagens.LEILAO_NAO_ENCONTRADO);

        Lance lance = new Lance();

        Lance maiorLanceReq = _lanceRepository.maiorLance(leilao);
        Double maiorLance = maiorLanceReq == null ? 0 : maiorLanceReq.getValor();

        lance.setEmpresa(empresa);
        lance.setLeilao(leilao);

        if (maiorLance < params.getValor()) lance.setValor(valor);
        else if (maiorLance.equals(params.getValor())) return new RespostaApi<>(400, Mensagens.LANCE_IGUAL);
        else return new RespostaApi<>(400, Mensagens.LANCE_MENOR);

        lance.setId(_lanceRepository.lastId() + 1);
        lance.setData(new Date());

        _lanceRepository.save(lance);
        return new RespostaApi<>(201, Mensagens.LANCE_ATRIBUIDO, lance.toDto());
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
            } else return new RespostaApi<>(404, Mensagens.EMPRESA_NAO_ENCONTRADA);
        } else return new RespostaApi<>(404, Mensagens.LEILAO_NAO_ENCONTRADO);


        List<Lance> lances = _lanceRepository.pegarLancesPorLeilao(empresa, leilao);

        if (!lances.isEmpty()) {
            _lanceRepository.deleteAllInBatch(lances);
            return new RespostaApi<>(Mensagens.LANCE_CANCELADO, lances);
        } else return new RespostaApi<>(400, Mensagens.LANCE_NAO_EXISTE);
    }

}
