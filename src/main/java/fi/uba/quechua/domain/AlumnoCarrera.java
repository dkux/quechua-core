package fi.uba.quechua.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AlumnoCarrera.
 */
@Entity
@Table(name = "alumno_carrera")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AlumnoCarrera implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Alumno alumno;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Carrera carrera;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public AlumnoCarrera alumno(Alumno alumno) {
        this.alumno = alumno;
        return this;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public AlumnoCarrera carrera(Carrera carrera) {
        this.carrera = carrera;
        return this;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
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
        AlumnoCarrera alumnoCarrera = (AlumnoCarrera) o;
        if (alumnoCarrera.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alumnoCarrera.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlumnoCarrera{" +
            "id=" + getId() +
            "}";
    }
}
