package fi.uba.quechua.service.dto;

public class ReporteCursoDTO {

    String name;

    String profesor;

    Float y;

    Integer inscriptos;

    public ReporteCursoDTO(String name, String profesor, Float y, Integer inscriptos) {
        this.name = name;
        this.profesor = profesor;
        this.y = y;
        this.inscriptos = inscriptos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
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
}
