package recoope.api.services;

import org.springframework.stereotype.Service;
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

    public Cooperativa pegarPorId(Long id) {
        Optional<Cooperativa> cooperativa = _cooperativaRepository.findById(id);

        if (cooperativa.isPresent()) {
            return cooperativa.get();
        }

        return null;
    }

    public List<Cooperativa> buscar(String nomeCooperativa){
        List<Cooperativa> cooperativas = _cooperativaRepository.pegarPorNome(nomeCooperativa.toLowerCase());

        return cooperativas;
    }

    public List<LeilaoPorCooperativa> leiloes(Long idCooperativa){

        List<Leilao> leiloesResult = _leilaoRepository.porCooperativa(idCooperativa);
        return toDtoList(leiloesResult);
    }

    public List<LeilaoPorCooperativa> leiloesPorMaterial(Long idCooperativa, String material) {
        List<Leilao> leiloesResult = _leilaoRepository.porCooperativaEMaterial(idCooperativa, material.toLowerCase());
        return toDtoList(leiloesResult);
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
