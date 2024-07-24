package recoope.api.services;

import org.springframework.stereotype.Service;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.dtos.LeilaoDto;
import recoope.api.domain.entities.*;
import recoope.api.repository.ILanceRepository;
import recoope.api.repository.ILeilaoRepository;

import java.sql.Time;
import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LeilaoServices {
    private final ILeilaoRepository _leilaoRepository;
    private final ILanceRepository _lanceRepository;

    public LeilaoServices(ILeilaoRepository leilaoRepository, ILanceRepository lanceRepository) {
        _leilaoRepository = leilaoRepository;
        _lanceRepository = lanceRepository;
    }

    public RespostaApi<Leilao> pegarPorDataFim(Date data) {
        List<Leilao> leiloes = _leilaoRepository.porDataDeFim(data);

        if (!leiloes.isEmpty())
            return new RespostaApi<>(leiloes);
        else return new RespostaApi<>(404, "Nenhum leilão encontrado nesta data!");
    }

    public RespostaApi<LeilaoDto> pegarPorId(Long id) {
        Optional<Leilao> leilaoOptional = _leilaoRepository.findById(id);

        if (leilaoOptional.isPresent()) {

            Leilao leilao = leilaoOptional.get();

            String tempoRestante = tempoRestante(leilao);
            Lance maiorLance = _lanceRepository.maiorLance(leilao);

            LeilaoDto leilaoDto = new LeilaoDto(
                leilao.getIdLeilao(),
                leilao.getDataInicioLeilao(),
                leilao.getDataFimLeilao(),
                leilao.getDetalhesLeilao(),
                leilao.getHoraLeilao(),
                tempoRestante,
                maiorLance,
                leilao.getEndereco(),
                leilao.getProduto(),
                leilao.getCooperativa()
            );

            return new RespostaApi<>("Leilão encontrado com sucesso!", leilaoDto);
        }
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

    private String tempoRestante(Leilao leilao) {
        Date dataFim = leilao.getDataFimLeilao();
        Time horaLeilao = leilao.getHoraLeilao();

        Instant instantDataFim = dataFim.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instantDataFim, ZoneId.systemDefault());
        LocalTime localTime = horaLeilao.toLocalTime();

        LocalDateTime dataHoraLeilao = localDateTime.toLocalDate().atTime(localTime);

        Date data = Date.from(dataHoraLeilao.atZone(ZoneId.systemDefault()).toInstant());

        Date agora = new Date();
        long diferencaMillis = data.getTime() - agora.getTime();

        if (diferencaMillis <= 0) {
            return "0";
        }

        long segundos = diferencaMillis / 1000;
        long minutos = segundos / 60;
        long horas = minutos / 60;
        long dias = horas / 24;

        if (dias > 0) {
            return dias + " dias.";
        } else {
            long horasRestantes = horas % 24;
            long minutosRestantes = minutos % 60;
            long segundosRestantes = segundos % 60;

            return horasRestantes + "h" + minutosRestantes + "m" + segundosRestantes + "s.";
        }
    }
}
