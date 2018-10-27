package fi.uba.quechua.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Prioridad.
 */
@Entity
@Table(name = "prioridad")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Prioridad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_habilitacion", nullable = false)
    private ZonedDateTime fecha_habilitacion;

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

    public ZonedDateTime getFecha_habilitacion() {
        return fecha_habilitacion;
    }

    public Prioridad fecha_habilitacion(ZonedDateTime fecha_habilitacion) {
        this.fecha_habilitacion = fecha_habilitacion;
        return this;
    }

    public void setFecha_habilitacion(ZonedDateTime fecha_habilitacion) {
        this.fecha_habilitacion = fecha_habilitacion;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public Prioridad periodo(Periodo periodo) {
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
        Prioridad prioridad = (Prioridad) o;
        if (prioridad.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prioridad.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Prioridad{" +
            "id=" + getId() +
            ", fecha_habilitacion='" + getFecha_habilitacion() + "'" +
            "}";
    }
}
