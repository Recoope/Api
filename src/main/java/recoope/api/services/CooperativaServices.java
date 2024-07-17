package recoope.api.services;

import org.springframework.stereotype.Service;
import recoope.api.domain.entities.Leilao;
import recoope.api.repository.ICooperativaRepository;
import recoope.api.repository.ILeilaoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CooperativaServices {
    private final ICooperativaRepository _cooperativaRepository;
    private final ILeilaoRepository _leilaoRepository;

    public CooperativaServices(ICooperativaRepository cooperativaRepository, ILeilaoRepository leilaoRepository) {
        _cooperativaRepository = cooperativaRepository;
        _leilaoRepository = leilaoRepository;
    }

    public List<Leilao> leiloes(Long id){
        return _leilaoRepository.porCooperativa(id);
    }
}
