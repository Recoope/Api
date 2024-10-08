package recoope.api.services;

import org.springframework.stereotype.Service;
import recoope.api.domain.Mensagens;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.dtos.LeilaoDto;
import recoope.api.domain.entities.*;
import recoope.api.repository.ILanceRepository;
import recoope.api.repository.ILeilaoRepository;

import java.sql.Time;
import java.time.*;
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

            String tempoRestante = tempoRestante(leilao);
            Lance maiorLance = _lanceRepository.maiorLance(leilao);

            LeilaoDto leilaoDto = new LeilaoDto(
                leilao.getId(),
                leilao.getDataInicio(),
                leilao.getDataFim(),
                leilao.getDetalhes(),
                leilao.getHora(),
                tempoRestante,
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

    private String tempoRestante(Leilao leilao) {
        Date dataFim = leilao.getDataFim();
        Time horaLeilao = leilao.getHora();

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
