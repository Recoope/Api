package recoope.api.services;

import org.springframework.stereotype.Service;
import recoope.api.domain.Mensagens;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.dtos.LeilaoDto;
import recoope.api.domain.entities.*;
import recoope.api.repository.ILanceRepository;
import recoope.api.repository.ILeilaoRepository;

import java.util.*;

@Service
public class LeilaoServices {
    private final ILeilaoRepository _leilaoRepository;
    private final ILanceRepository _lanceRepository;

    public LeilaoServices(ILeilaoRepository leilaoRepository, ILanceRepository lanceRepository) {
        _leilaoRepository = leilaoRepository;
        _lanceRepository = lanceRepository;
    }

    public RespostaApi<Leilao> pegarParticipados(String cnpj, Date fim) {
        List<Leilao> leiloes;

        if (fim == null) leiloes = _leilaoRepository.participados(cnpj);
        else leiloes = _leilaoRepository.participadosPorDataFim(cnpj, fim);

        if (!leiloes.isEmpty())
            return new RespostaApi<>(leiloes);
        else return new RespostaApi<>(404, Mensagens.NENHUM_LEILAO_ENCONTRADO);
    }

    public RespostaApi<LeilaoDto> pegarPorId(Long id) {
        Optional<Leilao> leilaoOptional = _leilaoRepository.findById(id);

        if (leilaoOptional.isPresent()) {

            Leilao leilao = leilaoOptional.get();

            Lance maiorLance = _lanceRepository.maiorLance(leilao);

            LeilaoDto leilaoDto = new LeilaoDto(
                leilao.getId(),
                leilao.getDataInicio(),
                leilao.getDataFim(),
                leilao.getDetalhes(),
                leilao.getHora(),
                maiorLance,
                leilao.getEndereco(),
                leilao.getProduto(),
                leilao.getCooperativa()
            );

            return new RespostaApi<>(Mensagens.LEILAO_ENCONTRADO, leilaoDto);
        }
        else return new RespostaApi<>(404, Mensagens.LEILAO_NAO_ENCONTRADO);
    }

    public RespostaApi<List<Leilao>> todos(List<String> materiais, Date ate, Double pesoMin, Double pesoMax) {
        List<Leilao> leiloesSemfiltro = _leilaoRepository.pegarAtivos();
        List<Leilao> leiloes = new ArrayList<>();

        for (Leilao leilao : leiloesSemfiltro)
            if (materiais == null || materiais.contains(leilao.getProduto().getTipo().toUpperCase()))
                if (ate == null || !leilao.getDataFim().after(ate))
                    if (pesoMin == null || leilao.getProduto().getPeso() >= pesoMin)
                        if (pesoMax == null || leilao.getProduto().getPeso() <= pesoMax)
                            leiloes.add(leilao);

        if (!leiloes.isEmpty())
            return new RespostaApi<>(leiloes);
        else return new RespostaApi<>(404, Mensagens.NENHUM_LEILAO_ENCONTRADO);
    }

    public RespostaApi<Set<Date>> vencimentos(String cnpjCooperativa) {

        Set<Date> datasFim = _leilaoRepository.pegarVencimentos(cnpjCooperativa);

        if (!datasFim.isEmpty()) return new RespostaApi<>(datasFim);
        else return new RespostaApi<>(404, Mensagens.NENHUM_LEILAO_ENCONTRADO);
    }
}
