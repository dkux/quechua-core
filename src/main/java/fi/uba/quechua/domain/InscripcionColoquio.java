package fi.uba.quechua.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import fi.uba.quechua.domain.enumeration.InscripcionColoquioEstado;

/**
 * A InscripcionColoquio.
 */
@Entity
@Table(name = "inscripcion_coloquio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InscripcionColoquio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private InscripcionColoquioEstado estado;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Coloquio coloquio;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Alumno alumno;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Cursada cursada;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InscripcionColoquioEstado getEstado() {
        return estado;
    }

    public InscripcionColoquio estado(InscripcionColoquioEstado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(InscripcionColoquioEstado estado) {
        this.estado = estado;
    }

    public Coloquio getColoquio() {
        return coloquio;
    }

    public InscripcionColoquio coloquio(Coloquio coloquio) {
        this.coloquio = coloquio;
        return this;
    }

    public void setColoquio(Coloquio coloquio) {
        this.coloquio = coloquio;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public InscripcionColoquio alumno(Alumno alumno) {
        this.alumno = alumno;
        return this;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Cursada getCursada() {
        return cursada;
    }

    public InscripcionColoquio cursada(Cursada cursada) {
        this.cursada = cursada;
        return this;
    }

    public void setCursada(Cursada cursada) {
        this.cursada = cursada;
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
        InscripcionColoquio inscripcionColoquio = (InscripcionColoquio) o;
        if (inscripcionColoquio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inscripcionColoquio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InscripcionColoquio{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
