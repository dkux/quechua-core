package fi.uba.quechua.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PeriodoAdministrativo.
 */
@Entity
@Table(name = "periodo_administrativo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PeriodoAdministrativo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "consultar_pioridad")
    private Boolean consultarPioridad;

    @Column(name = "inscribir_cursada")
    private Boolean inscribirCursada;

    @Column(name = "inscribir_coloquio")
    private Boolean inscribirColoquio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public PeriodoAdministrativo fechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public PeriodoAdministrativo fechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean isConsultarPioridad() {
        return consultarPioridad;
    }

    public PeriodoAdministrativo consultarPioridad(Boolean consultarPioridad) {
        this.consultarPioridad = consultarPioridad;
        return this;
    }

    public void setConsultarPioridad(Boolean consultarPioridad) {
        this.consultarPioridad = consultarPioridad;
    }

    public Boolean isInscribirCursada() {
        return inscribirCursada;
    }

    public PeriodoAdministrativo inscribirCursada(Boolean inscribirCursada) {
        this.inscribirCursada = inscribirCursada;
        return this;
    }

    public void setInscribirCursada(Boolean inscribirCursada) {
        this.inscribirCursada = inscribirCursada;
    }

    public Boolean isInscribirColoquio() {
        return inscribirColoquio;
    }

    public PeriodoAdministrativo inscribirColoquio(Boolean inscribirColoquio) {
        this.inscribirColoquio = inscribirColoquio;
        return this;
    }

    public void setInscribirColoquio(Boolean inscribirColoquio) {
        this.inscribirColoquio = inscribirColoquio;
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
        PeriodoAdministrativo periodoAdministrativo = (PeriodoAdministrativo) o;
        if (periodoAdministrativo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), periodoAdministrativo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PeriodoAdministrativo{" +
            "id=" + getId() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", consultarPioridad='" + isConsultarPioridad() + "'" +
            ", inscribirCursada='" + isInscribirCursada() + "'" +
            ", inscribirColoquio='" + isInscribirColoquio() + "'" +
            "}";
    }
}
