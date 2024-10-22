package recoope.api.services;

import org.springframework.stereotype.Service;
import recoope.api.domain.Mensagens;
import recoope.api.domain.RespostaApi;
import recoope.api.domain.dtos.ReciboDTO;
import recoope.api.domain.entities.Lance;
import recoope.api.repository.ILanceRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReciboServices {
    private final ILanceRepository _lanceRepository;

    public ReciboServices(ILanceRepository lanceRepository) {

        _lanceRepository = lanceRepository;
    }

    public RespostaApi<List<ReciboDTO>> pegarRecibos(String cnpj) {
        List<Lance> vencidosComMaiorLance = _lanceRepository.pegarVencidosComMaiorLance(cnpj);
        List<ReciboDTO> reciboDTOS = new ArrayList<>();

        for (Lance vencido: vencidosComMaiorLance)
            reciboDTOS.add(new ReciboDTO(
                    vencido.getLeilao().getId().intValue(),
                    vencido.getData(),
                    vencido.getLeilao().getHora(),
                    vencido.getLeilao().getCooperativa().getNome(),
                    vencido.getLeilao().getCooperativa().getCnpj(),
                    vencido.getEmpresa().getNome(),
                    vencido.getEmpresa().getCnpj(),
                    vencido.getValor()
            ));

        return !reciboDTOS.isEmpty() ? new RespostaApi<>(reciboDTOS) :
                new RespostaApi<>(204, Mensagens.NENHUM_RECIBO_ENCONTRADO);
    }
}
