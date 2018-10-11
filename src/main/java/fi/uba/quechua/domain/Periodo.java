package fi.uba.quechua.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import fi.uba.quechua.domain.enumeration.Cuatrimestre;

/**
 * A Periodo.
 */
@Entity
@Table(name = "periodo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Periodo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cuatrimestre", nullable = false)
    private Cuatrimestre cuatrimestre;

    @NotNull
    @Column(name = "anio", nullable = false)
    private String anio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cuatrimestre getCuatrimestre() {
        return cuatrimestre;
    }

    public Periodo cuatrimestre(Cuatrimestre cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
        return this;
    }

    public void setCuatrimestre(Cuatrimestre cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public String getAnio() {
        return anio;
    }

    public Periodo ano(String ano) {
        this.anio = ano;
        return this;
    }

    public void setAnio(String anio) {
        this.anio = anio;
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
        Periodo periodo = (Periodo) o;
        if (periodo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), periodo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Periodo{" +
            "id=" + getId() +
            ", cuatrimestre='" + getCuatrimestre() + "'" +
            ", anio='" + getAnio() + "'" +
            "}";
    }
}
