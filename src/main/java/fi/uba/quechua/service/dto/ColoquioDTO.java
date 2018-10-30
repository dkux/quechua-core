package fi.uba.quechua.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fi.uba.quechua.domain.Coloquio;
import fi.uba.quechua.domain.Curso;
import fi.uba.quechua.domain.Periodo;
import fi.uba.quechua.domain.enumeration.Sede;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ColoquioDTO {

    private Long id;

    private String aula;

    private String horaInicio;

    private String horaFin;

    private Sede sede;

    private LocalDate fecha;

    private String libro;

    private String folio;

    private Curso curso;

    private Periodo periodo;

    private Integer inscripcionesCantidad;

    public ColoquioDTO(Coloquio coloquio, Integer inscripcionesCantidad) {
        this.id = coloquio.getId();
        this.aula = coloquio.getAula();
        this.horaInicio = coloquio.getHoraInicio();
        this.horaFin = coloquio.getHoraFin();
        this.sede = coloquio.getSede();
        this.fecha = coloquio.getFecha();
        this.libro = coloquio.getLibro();
        this.folio = coloquio.getFolio();
        this.curso = coloquio.getCurso();
        this.periodo = coloquio.getPeriodo();
        this.inscripcionesCantidad = inscripcionesCantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getLibro() {
        return libro;
    }

    public void setLibro(String libro) {
        this.libro = libro;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Integer getInscripcionesCantidad() {
        return inscripcionesCantidad;
    }

    public void setInscripcionesCantidad(Integer inscripcionesCantidad) {
        this.inscripcionesCantidad = inscripcionesCantidad;
    }
}
