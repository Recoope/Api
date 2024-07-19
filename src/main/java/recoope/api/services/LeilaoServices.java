package recoope.api.services;

import org.springframework.stereotype.Service;
import recoope.api.domain.ApiResponse;
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

    public ApiResponse<Leilao> pegarPorId(Long id) {
        Optional<Leilao> leilao = _leilaoRepository.findById(id);

        if (leilao.isPresent())
            return new ApiResponse<>("Leilão encontrado com sucesso!", leilao.get());
        else return new ApiResponse<>("Leilão não encontrado!");

    }

    public ApiResponse<List<Leilao>> todos() {
        List<Leilao> leiloes = _leilaoRepository.findAll();

        if (!leiloes.isEmpty())
            return new ApiResponse<>(leiloes);
        else return new ApiResponse<>("Nenhum leilão encontrado!");
    }

    public ApiResponse<List<Leilao>> pegarPorMaterial(String material) {
        List<Leilao> leiloes = _leilaoRepository.pegarPorMaterial(material.toLowerCase());

        if (!leiloes.isEmpty()) return new ApiResponse<>(leiloes);
        else return new ApiResponse<>("Nenhum leilão encontrado!");
    }
}
