package fi.uba.quechua.web.rest;

import com.codahale.metrics.annotation.Timed;
import fi.uba.quechua.service.ReporteService;
import fi.uba.quechua.service.dto.ReporteMateriaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ReporteResource {

    private final Logger log = LoggerFactory.getLogger(ReporteResource.class);

    private final ReporteService reporteService;

    public ReporteResource(ReporteService reporteService) {
        this.reporteService = reporteService;
    }


    @GetMapping("/reportes/materias")
    @Timed
    public List<ReporteMateriaDTO> reporteMaterias(@RequestParam(name="departamentoId") Long departamentoId,
                                                   @RequestParam(name="periodoId") Long periodoId) {
        List<Object[]> reporte = reporteService.reporteMaterias(departamentoId, periodoId);
        List<ReporteMateriaDTO> result = new LinkedList<>();
        Integer total = 0;
        for (Object[] row: reporte) {
            total += Integer.valueOf(row[2].toString());
        }
        for (Object[] row: reporte) {
            Long id = Long.valueOf(row[0].toString());
            String materia = row[1].toString();
            Integer inscriptos = Integer.valueOf(row[2].toString());
            Float y = (float)inscriptos/total;
            Integer docentes = Integer.valueOf(row[3].toString());
            Integer cursos = Integer.valueOf(row[4].toString());
            result.add(new ReporteMateriaDTO(id, materia, y, inscriptos, docentes, cursos));
        }
        return result;
    }
}
