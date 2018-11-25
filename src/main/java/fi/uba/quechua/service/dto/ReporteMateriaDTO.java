package fi.uba.quechua.service.dto;

public class ReporteMateriaDTO {

    String id;

    String name;

    Float y;

    Integer inscriptos;

    Integer docentes;

    Integer cursos;

    public ReporteMateriaDTO(String id, String nombre, Float y, Integer inscriptos, Integer docentes, Integer cursos) {
        this.id = id;
        this.name = nombre;
        this.y = y;
        this.inscriptos = inscriptos;
        this.docentes = docentes;
        this.cursos = cursos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
