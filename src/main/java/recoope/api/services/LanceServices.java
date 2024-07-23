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
public class LanceServices {
    private final ILeilaoRepository _leilaoRepository;
    private final ILanceRepository _lanceRepository;

    public LanceServices(ILeilaoRepository leilaoRepository, ILanceRepository lanceRepository) {
        _leilaoRepository = leilaoRepository;
        _lanceRepository = lanceRepository;
    }

    public RespostaApi<>

}
