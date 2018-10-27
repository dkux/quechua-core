package fi.uba.quechua.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;



import io.github.jhipster.service.filter.ZonedDateTimeFilter;


/**
 * Criteria class for the Prioridad entity. This class is used in PrioridadResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /prioridads?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PrioridadCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private ZonedDateTimeFilter fecha_habilitacion;

    private LongFilter periodoId;

    public PrioridadCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getFecha_habilitacion() {
        return fecha_habilitacion;
    }

    public void setFecha_habilitacion(ZonedDateTimeFilter fecha_habilitacion) {
        this.fecha_habilitacion = fecha_habilitacion;
    }

    public LongFilter getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(LongFilter periodoId) {
        this.periodoId = periodoId;
    }

    @Override
    public String toString() {
        return "PrioridadCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fecha_habilitacion != null ? "fecha_habilitacion=" + fecha_habilitacion + ", " : "") +
                (periodoId != null ? "periodoId=" + periodoId + ", " : "") +
            "}";
    }

}
