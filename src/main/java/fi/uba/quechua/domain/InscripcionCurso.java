package fi.uba.quechua.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import fi.uba.quechua.domain.enumeration.InscripcionCursoEstado;

import fi.uba.quechua.domain.enumeration.CursadaEstado;

/**
 * A InscripcionCurso.
 */
@Entity
@Table(name = "inscripcion_curso")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InscripcionCurso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private InscripcionCursoEstado estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "cursada_estado")
    private CursadaEstado cursadaEstado;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Alumno alumno;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Curso curso;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InscripcionCursoEstado getEstado() {
        return estado;
    }

    public InscripcionCurso estado(InscripcionCursoEstado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(InscripcionCursoEstado estado) {
        this.estado = estado;
    }

    public CursadaEstado getCursadaEstado() {
        return cursadaEstado;
    }

    public InscripcionCurso cursadaEstado(CursadaEstado cursadaEstado) {
        this.cursadaEstado = cursadaEstado;
        return this;
    }

    public void setCursadaEstado(CursadaEstado cursadaEstado) {
        this.cursadaEstado = cursadaEstado;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public InscripcionCurso alumno(Alumno alumno) {
        this.alumno = alumno;
        return this;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Curso getCurso() {
        return curso;
    }

    public InscripcionCurso curso(Curso curso) {
        this.curso = curso;
        return this;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
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
        InscripcionCurso inscripcionCurso = (InscripcionCurso) o;
        if (inscripcionCurso.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inscripcionCurso.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InscripcionCurso{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", cursadaEstado='" + getCursadaEstado() + "'" +
            "}";
    }
}
