package recoope.api.services;

import org.springframework.stereotype.Service;
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
public class CooperativaServices {
    private final ICooperativaRepository _cooperativaRepository;
    private final ILeilaoRepository _leilaoRepository;

    public CooperativaServices(ICooperativaRepository cooperativaRepository, ILeilaoRepository leilaoRepository) {
        _cooperativaRepository = cooperativaRepository;
        _leilaoRepository = leilaoRepository;
    }

    public RespostaApi<Cooperativa> pegarPorId(Long id) {

        Optional<Cooperativa> cooperativa = _cooperativaRepository.findById(id);

        if (cooperativa.isPresent())
            return new RespostaApi<>("Cooperativa encontrada com sucesso!", cooperativa.get());
        else return new RespostaApi<>(404, "Cooperativa não encontrada!");
    }

    public RespostaApi<List<Cooperativa>> buscar(String nomeCooperativa){
        nomeCooperativa = nomeCooperativa.toLowerCase().trim();

        List<Cooperativa> cooperativas = _cooperativaRepository.pegarPorNome(nomeCooperativa);
        if (!cooperativas.isEmpty())
            return new RespostaApi<>(cooperativas);
        else return new RespostaApi<>(404, "Nenhuma cooperativa encontrada.");
    }

    public RespostaApi<List<LeilaoPorCooperativa>> leiloes(Long idCooperativa){
        Cooperativa coop = pegarPorId(idCooperativa).data;
        if (coop == null) return new RespostaApi<>(400, "Cooperativa não existe.");

        List<Leilao> leiloesResult = _leilaoRepository.porCooperativa(idCooperativa);
        if (!leiloesResult.isEmpty())
            return new RespostaApi<>(toDtoList(leiloesResult));
        else return new RespostaApi<>(404, "Não foi encontrado nenhum leilão.");
    }

    public RespostaApi<List<LeilaoPorCooperativa>> leiloesPorMaterial(Long idCooperativa, String material) {
        Cooperativa coop = pegarPorId(idCooperativa).data;
        if (coop == null) return new RespostaApi<>(400, "Cooperativa não existe.");

        List<Leilao> leiloesResult = _leilaoRepository.porCooperativaEMaterial(idCooperativa, material.toLowerCase());;
        if (!leiloesResult.isEmpty())
            return new RespostaApi<>(toDtoList(leiloesResult));
        else return new RespostaApi<>(404, "Não foi encontrado nenhum leilão.");
    }

    private List<LeilaoPorCooperativa> toDtoList(List<Leilao> leiloes) {

        List<LeilaoPorCooperativa> dtoList = new ArrayList<>();

        for (Leilao leilao: leiloes) {
            LeilaoPorCooperativa leilaoPorCooperativaDto =
                    new LeilaoPorCooperativa(leilao.getIdLeilao(),
                            leilao.getDataInicioLeilao(),
                            leilao.getDataFimLeilao(),
                            leilao.getDetalhesLeilao(),
                            leilao.getHoraLeilao(),
                            leilao.getEndereco(),
                            leilao.getProduto());

            dtoList.add(leilaoPorCooperativaDto);
        }

        return dtoList;
    }
}
