package mx.com.aey.user.infrastructure.persistence.dao;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NamedNativeQuery;
import mx.com.aey.user.domain.entity.User;

public class UserQuery {

    static  final String PARAM_USER_LIMIT = "limit";
    static  final String PARAM_USER_OFFSET = "offset";
    static final String PARAM_USER_EMAIL = "userEmail";
    static final String USERS_PAGINATION =
            "select user_id, " +
            "u_first_name as first_name, " +
            "u_last_name as last_name, " +
            "u_nick_name as nick_name, " +
            "u_email as email, " +
            "u_backup_email as backup_email, " +
            "u_birthdate as birthdate, " +
            "u_phone_number as phone_number, " +
            "u_is_active as is_active " +
            "from usr01_users " +
            "where (u_is_active = true) " +
            "limit coalesce(:limit, 10) " +
            "offset coalesce(:offset, 0)";
    static final String FIND_USER_BY_EMAIL =
            "select " +
            "user_id, " +
            "u_first_name as first_name, " +
            "u_last_name as last_name, " +
            "u_nick_name as nick_name, " +
            "u_email as email, " +
            "u_backup_email as backup_email, " +
            "u_birthdate as birthdate, " +
            "u_phone_number as phone_number, " +
            "u_is_active as is_active " +
            "from usr01_users " +
            "where u_email = :userEmail " +
            "and u_is_active = true";

}
