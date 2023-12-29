package mx.com.aey.user.infrastructure.persistence.dao;


import jakarta.enterprise.context.ApplicationScoped;

public class UserQuery {

    static  final String PARAM_USER_LIMIT = "limit";
    static  final String PARAM_USER_OFFSET = "offset";
    static final String USERS_PAGINATION =
            "select user_id, " +
            "u_first_name as first_name, " +
            "u_last_name as last_name, " +
            "u_email as email, " +
            "u_backup_email as backup_email, " +
            "u_birthdate as birthdate, " +
            "u_phone_number as phone_number, " +
            "u_is_active as is_active " +
            "from usr01_users " +
            "where (u_is_active = true) " +
            "limit :limit offset :offset";
}
