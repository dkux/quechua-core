package fi.uba.quechua.service.dto;

import fi.uba.quechua.domain.*;
import fi.uba.quechua.domain.enumeration.CursoEstado;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CursoDTO {


    private Long id;

    private CursoEstado estado;

    private Integer vacantes;

    private Set<HorarioCursada> horarios;

    private Profesor profesor;

    private Materia materia;

    private Integer vacantesDisponibles;

    private Periodo periodo;

    private List<InscripcionCurso> inscripciones;

    private Integer numero;

    public CursoDTO() {
    }

    public CursoDTO(Curso curso, List<InscripcionCurso> inscripciones) {
        this.id = curso.getId();
        this.estado = curso.getEstado();
        this.vacantes = curso.getVacantes();
        this.horarios = curso.getHorarios();
        this.profesor = curso.getProfesor();
        this.materia = curso.getMateria();
        this.inscripciones = inscripciones;
        this.numero = curso.getNumero();
        this.vacantesDisponibles =  Math.max(0, curso.getVacantes() - inscripciones.size());

    }

    public CursoDTO(Curso curso, Integer vacantesDisponibles) {
        this.id = curso.getId();
        this.estado = curso.getEstado();
        this.vacantes = curso.getVacantes();
        this.horarios = curso.getHorarios();
        this.profesor = curso.getProfesor();
        this.materia = curso.getMateria();
        this.numero = curso.getNumero();
        this.vacantesDisponibles = vacantesDisponibles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CursoEstado getEstado() {
        return estado;
    }

    public void setEstado(CursoEstado estado) {
        this.estado = estado;
    }

    public Integer getVacantes() {
        return vacantes;
    }

    public void setVacantes(Integer vacantes) {
        this.vacantes = vacantes;
    }

    public Set<HorarioCursada> getHorarios() {
        return horarios;
    }

    public void setHorarios(Set<HorarioCursada> horarios) {
        this.horarios = horarios;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public List<InscripcionCurso> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<InscripcionCurso> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public Integer getVacantesDisponibles() {
        return vacantesDisponibles;
    }

    public void setVacantesDisponibles(Integer vacantesDisponibles) {
        this.vacantesDisponibles = vacantesDisponibles;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
}
