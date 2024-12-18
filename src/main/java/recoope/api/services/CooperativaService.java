package recoope.api.services;

import org.springframework.stereotype.Service;
import recoope.api.domain.Mensagens;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.dtos.LeilaoPorCooperativa;
import recoope.api.domain.entities.Cooperativa;
import recoope.api.domain.entities.Leilao;
import recoope.api.repository.ICooperativaRepository;
import recoope.api.repository.ILeilaoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CooperativaService {
    private final ICooperativaRepository _cooperativaRepository;
    private final ILeilaoRepository _leilaoRepository;

    public CooperativaService(ICooperativaRepository cooperativaRepository, ILeilaoRepository leilaoRepository) {
        _cooperativaRepository = cooperativaRepository;
        _leilaoRepository = leilaoRepository;
    }

    public RespostaApi<Cooperativa> pegarPorId(String id) {

        Optional<Cooperativa> cooperativa = _cooperativaRepository.findById(id);

        return cooperativa.map(value -> new RespostaApi<>(Mensagens.COOPERATIVA_ENCONTRADA, value))
                .orElseGet(() -> new RespostaApi<>(404, Mensagens.COOPERATIVA_NAO_ENCONTRADA));
    }

    public RespostaApi<List<Cooperativa>> buscar(String nomeCooperativa){
        nomeCooperativa = nomeCooperativa.toLowerCase().trim();

        List<Cooperativa> cooperativas = _cooperativaRepository.pegarPorNome(nomeCooperativa);
        if (!cooperativas.isEmpty())
            return new RespostaApi<>(cooperativas);
        else return new RespostaApi<>(404, Mensagens.NENHUMA_COOPERATIVA_ENCONTRADA);
    }

    public RespostaApi<List<LeilaoPorCooperativa>> leiloes(String cnpjCooperativa){
        Cooperativa coop = pegarPorId(cnpjCooperativa).data;
        if (coop == null) return new RespostaApi<>(400, Mensagens.COOPERATIVA_NAO_EXISTE);

        List<Leilao> leiloesResult = _leilaoRepository.porCooperativa(cnpjCooperativa);
        if (!leiloesResult.isEmpty())
            return new RespostaApi<>(toDtoList(leiloesResult));
        else return new RespostaApi<>(404, Mensagens.NENHUM_LEILAO_ENCONTRADO);
    }

    public RespostaApi<List<LeilaoPorCooperativa>> leiloesPorMaterial(String cnpjCooperativa, String material) {
        Cooperativa coop = pegarPorId(cnpjCooperativa).data;
        if (coop == null) return new RespostaApi<>(400, Mensagens.COOPERATIVA_NAO_EXISTE);

        List<Leilao> leiloesResult = _leilaoRepository.porCooperativaEMaterial(cnpjCooperativa, material.toLowerCase());
        if (!leiloesResult.isEmpty())
            return new RespostaApi<>(toDtoList(leiloesResult));
        else return new RespostaApi<>(404, Mensagens.NENHUM_LEILAO_ENCONTRADO);
    }

    private List<LeilaoPorCooperativa> toDtoList(List<Leilao> leiloes) {

        List<LeilaoPorCooperativa> dtoList = new ArrayList<>();

        for (Leilao leilao: leiloes) {
            LeilaoPorCooperativa leilaoPorCooperativaDto =
                    new LeilaoPorCooperativa(leilao.getId(),
                            leilao.getDataInicio(),
                            leilao.getDataFim(),
                            leilao.getDetalhes(),
                            leilao.getHora(),
                            leilao.getEndereco(),
                            leilao.getProduto());

            dtoList.add(leilaoPorCooperativaDto);
        }

        return dtoList;
    }
}
