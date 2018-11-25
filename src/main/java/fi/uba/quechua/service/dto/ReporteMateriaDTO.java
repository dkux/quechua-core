package fi.uba.quechua.service.dto;

public class ReporteMateriaDTO {

    Long id;

    String nombre;

    Float y;

    Integer inscriptos;

    Integer docentes;

    Integer cursos;

    public ReporteMateriaDTO(Long id, String nombre, Float y, Integer inscriptos, Integer docentes, Integer cursos) {
        this.id = id;
        this.nombre = nombre;
        this.y = y;
        this.inscriptos = inscriptos;
        this.docentes = docentes;
        this.cursos = cursos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public Integer getInscriptos() {
        return inscriptos;
    }

    public void setInscriptos(Integer inscriptos) {
        this.inscriptos = inscriptos;
    }

    public Integer getDocentes() {
        return docentes;
    }

    public void setDocentes(Integer docentes) {
        this.docentes = docentes;
    }

    public Integer getCursos() {
        return cursos;
    }

    public void setCursos(Integer cursos) {
        this.cursos = cursos;
    }
}
