package fi.uba.quechua.repository;

import fi.uba.quechua.domain.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface ReporteRepository extends JpaRepository<Materia, Long> {

    @Query(nativeQuery = true, value =
        "SELECT materia.codigo AS id, materia.nombre AS nombre, COUNT(inscripcion.id) AS inscriptos, " +
        "COUNT(DISTINCT(profesor.id)) as docentes, COUNT(DISTINCT(curso.profesor_id)) AS cursos "+
        "FROM materia AS materia " +
        "JOIN curso AS curso ON curso.materia_id = materia.id " +
        "LEFT JOIN inscripcion_curso AS inscripcion ON inscripcion.curso_id = curso.id "+
        "JOIN departamento AS departamento ON materia.departamento_id = departamento.id "+
        "JOIN profesor AS profesor ON curso.profesor_id = profesor.id "+
        "JOIN periodo AS periodo ON curso.periodo_id = periodo.id "+
        "WHERE departamento.id = ?1 AND periodo.id = ?2 " +
        "GROUP BY materia.codigo")
    List<Object[]> reporteMaterias(Long departamentoId, Long periodoId);


    @Query(nativeQuery = true, value =
        "SELECT curso.id AS id, curso.numero AS numero, COUNT(inscripcion.id) AS inscriptos, " +
            "profesor.nombre, profesor.apellido "+
            "FROM curso AS curso " +
            "LEFT JOIN inscripcion_curso AS inscripcion ON inscripcion.curso_id = curso.id "+
            "JOIN materia AS materia ON curso.materia_id = materia.id "+
            "JOIN profesor AS profesor ON curso.profesor_id = profesor.id "+
            "JOIN periodo AS periodo ON curso.periodo_id = periodo.id "+
            "WHERE materia.codigo = ?1 AND periodo.id = ?2 " +
            "GROUP BY curso.profesor_id")
    List<Object[]> reporteCursos(String codigoMateria, Long periodoId);
}
