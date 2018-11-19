package fi.uba.quechua.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(fi.uba.quechua.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(fi.uba.quechua.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.Carrera.class.getName(), jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.Carrera.class.getName() + ".materias", jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.Departamento.class.getName(), jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.Departamento.class.getName() + ".materias", jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.Materia.class.getName(), jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.Materia.class.getName() + ".cursos", jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.HorarioCursada.class.getName(), jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.Curso.class.getName(), jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.Curso.class.getName() + ".horarios", jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.Profesor.class.getName(), jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.Alumno.class.getName(), jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.AlumnoCarrera.class.getName(), jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.InscripcionCurso.class.getName(), jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.Coloquio.class.getName(), jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.Periodo.class.getName(), jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.InscripcionColoquio.class.getName(), jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.PeriodoAdministrativo.class.getName(), jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.Cursada.class.getName(), jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.Prioridad.class.getName(), jcacheConfiguration);
            cm.createCache(fi.uba.quechua.domain.AdministradorDepartamento.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
