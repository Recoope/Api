package recoope.api.services;

import org.springframework.stereotype.Service;
import recoope.api.domain.Mensagens;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.dtos.LeilaoDto;
import recoope.api.domain.dtos.LeilaoParticipadoDto;
import recoope.api.domain.entities.*;
import recoope.api.repository.IEmpresaRepository;
import recoope.api.repository.ILanceRepository;
import recoope.api.repository.ILeilaoRepository;

import java.util.*;

@Service
public class LeilaoService {
    private final ILeilaoRepository _leilaoRepository;
    private final ILanceRepository _lanceRepository;
    private final IEmpresaRepository _empresaRepository;

    public LeilaoService(ILeilaoRepository leilaoRepository, ILanceRepository lanceRepository, IEmpresaRepository empresaRepository) {
        _leilaoRepository = leilaoRepository;
        _lanceRepository = lanceRepository;
        _empresaRepository = empresaRepository;
    }

    public RespostaApi<LeilaoParticipadoDto> pegarParticipados(String cnpj, Date fim) {
        Optional<Empresa> empresa = _empresaRepository.findById(cnpj);

        if (empresa.isPresent()){
            List<LeilaoParticipadoDto> leiloes = new ArrayList<>();
            List<Lance> lancesSobresalentes = _lanceRepository.lancesSobressalentesPorEmpresa(cnpj);
            List<Lance> lances = fim == null ?
                    _lanceRepository.pegarLances(empresa.get()) :
                    _lanceRepository.pegarLances(empresa.get(), fim);

            for (Lance lance: lances) {
                Leilao leilao = lance.getLeilao();
                Integer status = getStatus(lance, leilao, lancesSobresalentes);

                LeilaoParticipadoDto leilaoParticipado = new LeilaoParticipadoDto(
                    leilao.getId(),
                    leilao.getDataInicio(),
                    leilao.getDataFim(),
                    leilao.getDetalhes(),
                    leilao.getHora(),
                    leilao.getEndereco(),
                    leilao.getProduto(),
                    leilao.getCooperativa(),
                    status
                );

                leiloes.add(leilaoParticipado);
            }

            if (!leiloes.isEmpty())
                return new RespostaApi<>(leiloes);
            else return new RespostaApi<>(404, Mensagens.NENHUM_LEILAO_ENCONTRADO);
        } else return new RespostaApi<>(404, Mensagens.EMPRESA_NAO_ENCONTRADA);
    }

    private Integer getStatus(Lance lance, Leilao leilao, List<Lance> lancesSobresalentes) {
        Date now = new Date();
        boolean leilaoVencido = now.after(leilao.getDataFim()) ||
                now.after(leilao.getDataFim()) && now.getTime() > leilao.getHora().getTime();
        int status;

        if (lancesSobresalentes.contains(lance)) {
            if (leilaoVencido) status = 0; // Ganhou um leilão.
            else status = 1; // Está ganhando um leilão.
        } else {
            if (leilaoVencido) status = 2; // Perdeu um leilão.
            else status = 3; // Lance foi superado.
        }
        return status;
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
