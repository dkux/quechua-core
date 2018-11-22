package fi.uba.quechua.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Alumno.
 */
@Entity
@Table(name = "alumno")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Alumno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @NotNull
    @Column(name = "padron", nullable = false)
    private String padron;

    @NotNull
    @Column(name = "prioridad", nullable = false)
    private Integer prioridad;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "firebase_token")
    private String firebaseToken;

    public Long getUserId() {
        return userId;
    }

    public Alumno userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Alumno nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Alumno apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPadron() {
        return padron;
    }

    public Alumno padron(String padron) {
        this.padron = padron;
        return this;
    }

    public void setPadron(String padron) {
        this.padron = padron;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public Alumno prioridad(Integer prioridad) {
        this.prioridad = prioridad;
        return this;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public Alumno firebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
        return this;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
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
        Alumno alumno = (Alumno) o;
        if (alumno.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alumno.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Alumno{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", padron='" + getPadron() + "'" +
            ", prioridad=" + getPrioridad() +
            ", firebaseToken='" + getFirebaseToken() + "'" +
            "}";
    }
}
