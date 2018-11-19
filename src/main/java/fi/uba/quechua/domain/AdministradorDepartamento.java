package fi.uba.quechua.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AdministradorDepartamento.
 */
@Entity
@Table(name = "administrador_departamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdministradorDepartamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "departamento_id", nullable = false)
    private Long departamentoId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public AdministradorDepartamento userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDepartamentoId() {
        return departamentoId;
    }

    public AdministradorDepartamento departamentoId(Long departamentoId) {
        this.departamentoId = departamentoId;
        return this;
    }

    public void setDepartamentoId(Long departamentoId) {
        this.departamentoId = departamentoId;
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
        AdministradorDepartamento administradorDepartamento = (AdministradorDepartamento) o;
        if (administradorDepartamento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), administradorDepartamento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdministradorDepartamento{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", departamentoId=" + getDepartamentoId() +
            "}";
    }
}
