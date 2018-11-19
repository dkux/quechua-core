INSERT INTO `jhi_authority` VALUE('ROLE_ALUMNO');
INSERT INTO `jhi_authority` VALUE('ROLE_PROFESOR');
INSERT INTO `jhi_authority` VALUE('ROLE_ADM_DPTO');

ALTER TABLE alumno ADD COLUMN user_id BIGINT(20);
ALTER TABLE profesor ADD COLUMN user_id BIGINT(20);

INSERT INTO `carrera` (`id`, `nombre`)
VALUES
  (1, 'Ingeniería Civil'),
  (2, 'Ingeniería de Alimentos'),
  (3, 'Ingeniería Electricista'),
  (4, 'Ingeniería Electrónica'),
  (5, 'Ingeniería en Agrimensura'),
  (6, 'Ingeniería en Informática'),
  (7, 'Ingeniería en Petróleo'),
  (8, 'Ingeniería Industrial'),
  (9, 'Ingeniería Mecánica'),
  (10, 'Ingeniería Naval y Mecánica'),
  (11, 'Ingeniería Química'),
  (12, 'Licenciatura en Análisis de Sistemas');

INSERT INTO `departamento` (`id`, `nombre`, `codigo`)
VALUES
  (1, 'Agrimensura', 1),
  (2, 'Computación', 75),
  (3, 'Construcciones y Estructuras', 2),
  (4, 'Electrónica', 66),
  (5, 'Energía', 4),
  (6, 'Estabilidad', 6),
  (7, 'Física', 62),
  (8, 'Gestión', 71),
  (9, 'Hidráulica', 9),
  (10, 'Idiomas', 10),
  (11, 'Ingeniería Mecánica', 11),
  (12, 'Ingeniería Naval', 12),
  (13, 'Ingeniería Química', 13),
  (14, 'Matemática', 61),
  (15, 'Química', 63),
  (16, 'Seguridad del Trabajo y Ambiente', 16),
  (17, 'Tecnología Industrial', 17),
  (18, 'Transporte', 18);


INSERT INTO `jhi_user` (`id`, `login`, `password_hash`, `first_name`, `last_name`, `email`, `image_url`, `activated`, `lang_key`, `activation_key`, `reset_key`, `created_by`, `created_date`, `reset_date`, `last_modified_by`, `last_modified_date`)
VALUES
  (5, 'msosa@fiuba.com', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'Mercedes', 'Sosa', 'msosa@fiuba.com', '', 1, 'en', NULL, NULL, 'system', NOW(), NULL, 'system', NULL),
  (6, 'gcerati@fiuba.com', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'Gustavo', 'Cerati', 'gcerati@fiuba.com', '', 1, 'en', NULL, NULL, 'system', NOW(), NULL, 'system', NULL),
  (7, 'lspinetta@fiuba.com', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'Luis Alberto', 'Spinetta', 'lspinetta@fiuba.com', '', 1, 'en', NULL, NULL, 'system', NOW(), NULL, 'system', NULL),
  (8, 'fpaez@fiuba.com', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'Fito', 'Paez', 'fpaez@fiuba.com', '', 1, 'en', NULL, NULL, 'system', NOW(), NULL, 'system', NULL),
  (9, 'lgieco@fiuba.com', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'Leon', 'Gieco', 'lgieco@fiuba.com', '', 1, 'en', NULL, NULL, 'system', NOW(), NULL, 'system', NULL),
  (10, 'cgarcia@fiuba.com', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'Charly', 'Garcia', 'cgarcia@fiuba.com', '', 1, 'en', NULL, NULL, 'system', NOW(), NULL, 'system', NULL),
  (11, 'gcordera@fiuba.com', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'Gustavo', 'Cordera', 'gcordera@fiuba.com', '', 1, 'en', NULL, NULL, 'system', NOW(), NULL, 'system', NULL),
  (12, 'fcantilo@fiuba.com', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'Faviana', 'Cantilo', 'fcantilo@fiuba.com', '', 1, 'en', NULL, NULL, 'system', NOW(), NULL, 'system', NULL),
  (13, 'hlizarazu@fiuba.com', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'Hilda', 'Lizarazu', 'hlizarazu@fiuba.com', '', 1, 'en', NULL, NULL, 'system', NOW(), NULL, 'system', NULL),
  (14, 'epresley@fiuba.com', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'Elvis', 'Presley', 'epresley@fiuba.com', '', 1, 'en', NULL, NULL, 'system', NOW(), NULL, 'system', NULL),
  (15, 'admin@fiuba.com', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Juan', 'Admin', 'admin@fiuba.com', '', 1, 'en', NULL, NULL, 'system', NOW(), NULL, 'system', NULL),
  (16, 'computacion@fiuba.com', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Computación', 'Departamento', 'computacion@fiuba.com', '', 1, 'en', NULL, NULL, 'system', NOW(), NULL, 'system', NULL);



INSERT INTO `jhi_user_authority` (`user_id`, `authority_name`)
VALUES
  (5, 'ROLE_PROFESOR'),
  (6, 'ROLE_PROFESOR'),
  (7, 'ROLE_PROFESOR'),
  (8, 'ROLE_ALUMNO'),
  (9, 'ROLE_ALUMNO'),
  (10, 'ROLE_ALUMNO'),
  (11, 'ROLE_ALUMNO'),
  (12, 'ROLE_ALUMNO'),
  (13, 'ROLE_ALUMNO'),
  (14, 'ROLE_ALUMNO'),
  (14, 'ROLE_PROFESOR'),
  (15, 'ROLE_ADMIN'),
  (16, 'ROLE_ADM_DPTO');

INSERT INTO `periodo` (`id`, `cuatrimestre`, `anio`)
VALUES
  (1, 'SEGUNDO', '2018');

INSERT INTO `profesor` (`id`, `nombre`, `apellido`, `user_id`)
VALUES
  (1, 'Mercedes', 'Sosa', 5),
  (2, 'Gustavo', 'Cerati', 6),
  (3, 'Luis Alberto', 'Spinetta', 7),
  (4, 'Elvis', 'Presley', 14);


INSERT INTO `alumno` (`id`, `nombre`, `apellido`, `padron`, `prioridad`, `user_id`)
VALUES
  (1, 'Fito', 'Paez', '1000', 10, 8),
  (2, 'Leon', 'Gieco', '2000', 10, 9),
  (3, 'Charly', 'Garcia', '3000', 20, 10),
  (4, 'Gustavo', 'Cordera', '4000', 30, 11),
  (5, 'Fabiana', 'Cantilo', '5000', 60, 12),
  (6, 'Hilda', 'Lizarazu', '6000', 32, 13),
  (7, 'Elvis', 'Presley', '6000', 12, 14);

INSERT INTO `alumno_carrera` (`id`, `alumno_id`, `carrera_id`)
VALUES
  (1, 1, 6),
  (2, 1, 12),
  (3, 2, 1),
  (4, 3, 6),
  (5, 4, 6),
  (6, 5, 6),
  (7, 6, 6);

INSERT INTO `materia` (`id`, `nombre`, `codigo`, `creditos`, `departamento_id`, `carrera_id`)
VALUES
  (1, 'Tesis', '75.00', 20, 2, 6),
  (2, 'Computación', '75.01', 6, 2, 6),
  (3, 'Algoritmos y Programación I', '75.02', 6, 2, 6),
  (4, 'Organización del Computador', '75.03', 6, 2, 6),
  (5, 'Legal', '71.03', 6, 8, 6);

INSERT INTO `curso` (`id`, `estado`, `vacantes`, `numero`, `profesor_id`, `periodo_id`, `materia_id`)
VALUES
  (1, 'ACTIVO', 6, 1, 1, 1, 1),
  (2, 'ACTIVO', 2, 1, 1, 1, 2);

INSERT INTO `horario_cursada` (`id`, `dia`, `sede`, `aula`, `hora_inicio`, `hora_fin`, `curso_id`)
VALUES
  (1, 'LUNES', 'PC', '203', '19:00', '23:00', 1),
  (2, 'MIERCOLES', 'PC', '102', '19:00', '23:00', 1),
  (3, 'VIERNES', 'LH', '201', '15:00', '18:00', 2);

INSERT INTO `coloquio` (`id`, `aula`, `hora_inicio`, `hora_fin`, `sede`, `fecha`, `curso_id`, `periodo_id`, `estado`)
VALUES
  (1, '201', '19:00', '22:00', 'PC', '2018-12-23', 1, 1, 'ACTIVO'),
  (2, 'E9', '9:00', '12:00', 'PC', '2018-12-18', 2, 1, 'ACTIVO');

INSERT INTO `prioridad` (`fecha_habilitacion`, `periodo_id`)
VALUES
  ("2018-07-23 09:00:00",1),
  ("2018-07-23 09:30:00",1),
  ("2018-07-23 10:00:00",1),
  ("2018-07-23 10:30:00",1),
  ("2018-07-23 11:00:00",1),
  ("2018-07-23 11:30:00",1),
  ("2018-07-23 12:00:00",1),
  ("2018-07-23 12:30:00",1),
  ("2018-07-23 13:00:00",1),
  ("2018-07-23 13:30:00",1),
  ("2018-07-23 14:00:00",1),
  ("2018-07-23 14:30:00",1),
  ("2018-07-23 15:00:00",1),
  ("2018-07-23 15:30:00",1),
  ("2018-07-23 16:00:00",1),
  ("2018-07-23 16:30:00",1),
  ("2018-07-23 17:00:00",1),
  ("2018-07-23 17:30:00",1),
  ("2018-07-23 18:00:00",1),
  ("2018-07-24 09:00:00",1),
  ("2018-07-24 09:30:00",1),
  ("2018-07-24 10:00:00",1),
  ("2018-07-24 10:30:00",1),
  ("2018-07-24 11:00:00",1),
  ("2018-07-24 11:30:00",1),
  ("2018-07-24 12:00:00",1),
  ("2018-07-24 12:30:00",1),
  ("2018-07-24 13:00:00",1),
  ("2018-07-24 13:30:00",1),
  ("2018-07-24 14:00:00",1),
  ("2018-07-24 14:30:00",1),
  ("2018-07-24 15:00:00",1),
  ("2018-07-24 15:30:00",1),
  ("2018-07-24 16:00:00",1),
  ("2018-07-24 16:30:00",1),
  ("2018-07-24 17:00:00",1),
  ("2018-07-24 17:30:00",1),
  ("2018-07-24 18:00:00",1),
  ("2018-07-25 09:00:00",1),
  ("2018-07-25 09:30:00",1),
  ("2018-07-25 10:00:00",1),
  ("2018-07-25 10:30:00",1),
  ("2018-07-25 11:00:00",1),
  ("2018-07-25 11:30:00",1),
  ("2018-07-25 12:00:00",1),
  ("2018-07-25 12:30:00",1),
  ("2018-07-25 13:00:00",1),
  ("2018-07-25 13:30:00",1),
  ("2018-07-25 14:00:00",1),
  ("2018-07-25 14:30:00",1),
  ("2018-07-25 15:00:00",1),
  ("2018-07-25 15:30:00",1),
  ("2018-07-25 16:00:00",1),
  ("2018-07-25 16:30:00",1),
  ("2018-07-25 17:00:00",1),
  ("2018-07-25 17:30:00",1),
  ("2018-07-25 18:00:00",1),
  ("2018-07-26 09:00:00",1),
  ("2018-07-26 09:30:00",1),
  ("2018-07-26 10:00:00",1),
  ("2018-07-26 10:30:00",1),
  ("2018-07-26 11:00:00",1),
  ("2018-07-26 11:30:00",1),
  ("2018-07-26 12:00:00",1),
  ("2018-07-26 12:30:00",1),
  ("2018-07-26 13:00:00",1),
  ("2018-07-26 13:30:00",1),
  ("2018-07-26 14:00:00",1),
  ("2018-07-26 14:30:00",1),
  ("2018-07-26 15:00:00",1),
  ("2018-07-26 15:30:00",1),
  ("2018-07-26 16:00:00",1),
  ("2018-07-26 16:30:00",1),
  ("2018-07-26 17:00:00",1),
  ("2018-07-26 17:30:00",1),
  ("2018-07-26 18:00:00",1),
  ("2018-07-27 09:00:00",1),
  ("2018-07-27 09:30:00",1),
  ("2018-07-27 10:00:00",1),
  ("2018-07-27 10:30:00",1),
  ("2018-07-27 11:00:00",1),
  ("2018-07-27 11:30:00",1),
  ("2018-07-27 12:00:00",1),
  ("2018-07-27 12:30:00",1),
  ("2018-07-27 13:00:00",1),
  ("2018-07-27 13:30:00",1),
  ("2018-07-27 14:00:00",1),
  ("2018-07-27 14:30:00",1),
  ("2018-07-27 15:00:00",1),
  ("2018-07-27 15:30:00",1),
  ("2018-07-27 16:00:00",1),
  ("2018-07-27 16:30:00",1),
  ("2018-07-27 17:00:00",1),
  ("2018-07-27 17:30:00",1),
  ("2018-07-27 18:00:00",1);

INSERT INTO `periodo_administrativo` (`id`, `fecha_inicio`, `fecha_fin`, `actividad`)
VALUES
  (1, '2018-07-01', '2018-12-31', 'INSCRIPCION_CURSADA'),
  (2, '2018-07-01', '2018-12-31', 'CONSULTAR_PRIORIDAD'),
  (3, '2018-07-01', '2018-12-31', 'INSCRIPCION_COLOQUIO');

INSERT INTO `administrador_departamento` (`id`, `user_id`, `departamento_id`)
VALUES
  (1, 16, 2);

