package fi.uba.quechua.service;

import fi.uba.quechua.repository.ReporteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReporteService {

    private final Logger log = LoggerFactory.getLogger(ReporteService.class);

    private final ReporteRepository reporteRepository;

    public ReporteService(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    public List<Object[]> reporteMaterias(Long departamentoId, Long periodoId) {
        return reporteRepository.reporteMaterias(departamentoId, periodoId);
    }
}
