package fi.uba.quechua.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import fi.uba.quechua.domain.enumeration.Dia;

import fi.uba.quechua.domain.enumeration.Sede;

/**
 * A Coloquio.
 */
@Entity
@Table(name = "coloquio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Coloquio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "dia", nullable = false)
    private Dia dia;

    @NotNull
    @Column(name = "aula", nullable = false)
    private String aula;

    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private String horaInicio;

    @NotNull
    @Column(name = "hora_fin", nullable = false)
    private String horaFin;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sede", nullable = false)
    private Sede sede;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Curso curso;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Periodo periodo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dia getDia() {
        return dia;
    }

    public Coloquio dia(Dia dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public String getAula() {
        return aula;
    }

    public Coloquio aula(String aula) {
        this.aula = aula;
        return this;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public Coloquio horaInicio(String horaInicio) {
        this.horaInicio = horaInicio;
        return this;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public Coloquio horaFin(String horaFin) {
        this.horaFin = horaFin;
        return this;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Sede getSede() {
        return sede;
    }

    public Coloquio sede(Sede sede) {
        this.sede = sede;
        return this;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Curso getCurso() {
        return curso;
    }

    public Coloquio curso(Curso curso) {
        this.curso = curso;
        return this;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public Coloquio periodo(Periodo periodo) {
        this.periodo = periodo;
        return this;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
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
        Coloquio coloquio = (Coloquio) o;
        if (coloquio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coloquio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Coloquio{" +
            "id=" + getId() +
            ", dia='" + getDia() + "'" +
            ", aula='" + getAula() + "'" +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horaFin='" + getHoraFin() + "'" +
            ", sede='" + getSede() + "'" +
            "}";
    }
}
