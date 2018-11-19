package fi.uba.quechua.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String ALUMNO = "ROLE_ALUMNO";

    public static final String PROFESOR = "ROLE_PROFESOR";

    public static final String ADM_DPTO = "ROLE_ADM_DPTO";

    private AuthoritiesConstants() {
    }
}
