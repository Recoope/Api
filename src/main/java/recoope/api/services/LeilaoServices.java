package recoope.api.services;

import org.springframework.stereotype.Service;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.entities.Leilao;
import recoope.api.repository.ILeilaoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LeilaoServices {
    private final ILeilaoRepository _leilaoRepository;

    public LeilaoServices(ILeilaoRepository leilaoRepository) {
        _leilaoRepository = leilaoRepository;
    }

    public RespostaApi<Leilao> pegarPorDataFim(Date data) {
        List<Leilao> leiloes = _leilaoRepository.porDataDeFim(data);

        if (!leiloes.isEmpty())
            return new RespostaApi<>(leiloes);
        else return new RespostaApi<>(404, "Nenhum leilão encontrado nesta data!");
    }

    public RespostaApi<Leilao> pegarPorId(Long id) {
        Optional<Leilao> leilao = _leilaoRepository.findById(id);

        if (leilao.isPresent())
            return new RespostaApi<>("Leilão encontrado com sucesso!", leilao.get());
        else return new RespostaApi<>(404, "Leilão não encontrado!");
    }

    public RespostaApi<List<Leilao>> todos() {
        List<Leilao> leiloes = _leilaoRepository.findAll();

        if (!leiloes.isEmpty())
            return new RespostaApi<>(leiloes);
        else return new RespostaApi<>(404, "Nenhum leilão encontrado!");
    }

    public RespostaApi<List<Leilao>> pegarPorMaterial(String material) {
        List<Leilao> leiloes = _leilaoRepository.pegarPorMaterial(material.toLowerCase());

        if (!leiloes.isEmpty()) return new RespostaApi<>(leiloes);
        else return new RespostaApi<>(404, "Nenhum leilão encontrado!");
    }
}
