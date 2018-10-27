package fi.uba.quechua.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import fi.uba.quechua.domain.enumeration.CursoEstado;

/**
 * A Curso.
 */
@Entity
@Table(name = "curso")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private CursoEstado estado;

    @NotNull
    @Column(name = "vacantes", nullable = false)
    private Integer vacantes;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @OneToMany(mappedBy = "curso")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<HorarioCursada> horarios = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private Profesor profesor;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Periodo periodo;

    @ManyToOne
    @JsonIgnoreProperties("cursos")
    private Materia materia;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CursoEstado getEstado() {
        return estado;
    }

    public Curso estado(CursoEstado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(CursoEstado estado) {
        this.estado = estado;
    }

    public Integer getVacantes() {
        return vacantes;
    }

    public Curso vacantes(Integer vacantes) {
        this.vacantes = vacantes;
        return this;
    }

    public void setVacantes(Integer vacantes) {
        this.vacantes = vacantes;
    }

    public Integer getNumero() {
        return numero;
    }

    public Curso numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Set<HorarioCursada> getHorarios() {
        return horarios;
    }

    public Curso horarios(Set<HorarioCursada> horarioCursadas) {
        this.horarios = horarioCursadas;
        return this;
    }

    public Curso addHorario(HorarioCursada horarioCursada) {
        this.horarios.add(horarioCursada);
        horarioCursada.setCurso(this);
        return this;
    }

    public Curso removeHorario(HorarioCursada horarioCursada) {
        this.horarios.remove(horarioCursada);
        horarioCursada.setCurso(null);
        return this;
    }

    public void setHorarios(Set<HorarioCursada> horarioCursadas) {
        this.horarios = horarioCursadas;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public Curso profesor(Profesor profesor) {
        this.profesor = profesor;
        return this;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public Curso periodo(Periodo periodo) {
        this.periodo = periodo;
        return this;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Materia getMateria() {
        return materia;
    }

    public Curso materia(Materia materia) {
        this.materia = materia;
        return this;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Curso curso = (Curso) o;
        if (curso.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), curso.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Curso{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", vacantes=" + getVacantes() +
            ", numero=" + getNumero() +
            "}";
    }
}
