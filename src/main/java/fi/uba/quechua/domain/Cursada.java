package fi.uba.quechua.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import fi.uba.quechua.domain.enumeration.EstadoCursada;

/**
 * A Cursada.
 */
@Entity
@Table(name = "cursada")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cursada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nota_cursada")
    private Float notaCursada;

    @Column(name = "not_final")
    private Float notFinal;

    @Column(name = "libro")
    private String libro;

    @Column(name = "folio")
    private String folio;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoCursada estado;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Curso curso;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Alumno alumno;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getNotaCursada() {
        return notaCursada;
    }

    public Cursada notaCursada(Float notaCursada) {
        this.notaCursada = notaCursada;
        return this;
    }

    public void setNotaCursada(Float notaCursada) {
        this.notaCursada = notaCursada;
    }

    public Float getNotFinal() {
        return notFinal;
    }

    public Cursada notFinal(Float notFinal) {
        this.notFinal = notFinal;
        return this;
    }

    public void setNotFinal(Float notFinal) {
        this.notFinal = notFinal;
    }

    public String getLibro() {
        return libro;
    }

    public Cursada libro(String libro) {
        this.libro = libro;
        return this;
    }

    public void setLibro(String libro) {
        this.libro = libro;
    }

    public String getFolio() {
        return folio;
    }

    public Cursada folio(String folio) {
        this.folio = folio;
        return this;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public EstadoCursada getEstado() {
        return estado;
    }

    public Cursada estado(EstadoCursada estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoCursada estado) {
        this.estado = estado;
    }

    public Curso getCurso() {
        return curso;
    }

    public Cursada curso(Curso curso) {
        this.curso = curso;
        return this;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Cursada alumno(Alumno alumno) {
        this.alumno = alumno;
        return this;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
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
        Cursada cursada = (Cursada) o;
        if (cursada.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cursada.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cursada{" +
            "id=" + getId() +
            ", notaCursada=" + getNotaCursada() +
            ", notFinal=" + getNotFinal() +
            ", libro='" + getLibro() + "'" +
            ", folio='" + getFolio() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
