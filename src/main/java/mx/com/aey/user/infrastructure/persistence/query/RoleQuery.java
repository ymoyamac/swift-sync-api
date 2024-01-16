package mx.com.aey.user.infrastructure.persistence.query;

public class RoleQuery {

    public static final String PARAM_USER_ID = "userId";

    public static final String FIND_USER_ROLES_BY_USER_ID =
            "select usr02_roles.* from usr02_roles " +
            "inner join usr03_roles_users " +
            "on usr02_roles.role_id = usr03_roles_users.role_id " +
            "inner join usr01_users " +
            "on usr03_roles_users.user_id = usr01_users.user_id " +
            "where usr01_users.user_id = :userId";
}
