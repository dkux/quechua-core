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

    private String hora;
    private String fecha;

    private int numero;

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

    public String getHora() {
        return hora;
    }

    public void setId(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        if (this.fecha == null) {
            this.calcularFechayHora();
        }
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setNumero(int numero) {
        this.numero = numero;
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

    public int getNumero() {
        return numero;
    }


    public Prioridad periodo(Periodo periodo) {
        this.periodo = periodo;
        return this;
    }

    public void calcularFechayHora() {
        int año = 2019;
        int mes = 3;
        int dia = 1;
        int hora = 8;
        hora = this.numero % 10  + hora;
        dia = (this.numero / 10) + 1;
        String fecha = "0" + String.valueOf(dia) + "/0" + String.valueOf(mes) + "/" + String.valueOf(año);
        String horario = String.valueOf(hora)+":00";
        this.fecha = fecha;
        this.hora = horario;
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
