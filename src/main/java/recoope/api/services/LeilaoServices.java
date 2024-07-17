package recoope.api.services;

import org.springframework.stereotype.Service;
import recoope.api.domain.entities.Leilao;
import recoope.api.repository.ICooperativaRepository;
import recoope.api.repository.ILeilaoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LeilaoServices {
    private final ILeilaoRepository _leilaoRepository;

    public LeilaoServices(ILeilaoRepository leilaoRepository) {
        _leilaoRepository = leilaoRepository;
    }

    public Leilao pegarPorId(Long id) {
        Optional<Leilao> leilao = _leilaoRepository.findById(id);

        if (leilao.isPresent()) {
            return leilao.get();
        }

        return null;
    }

    public List<Leilao> pegarPorMaterial(String material) {
        List<Leilao> leiloes = _leilaoRepository.pegarPorMaterial(material.toLowerCase());
        return leiloes;
    }
}
