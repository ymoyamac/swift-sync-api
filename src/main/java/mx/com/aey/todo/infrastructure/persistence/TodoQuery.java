package mx.com.aey.todo.infrastructure.persistence;

public class TodoQuery {

    public static final String PARAM_USER_ID = "userId";

    public static final String FIND_TODOS_BY_USER_ID =
            "select " +
            "todo_id," +
            "t_title as title, " +
            "t_description as description, " +
            "t_is_completed as isCompleted, " +
            "t_creation_at as creationAt, " +
            "t_update_at as updateAt, " +
            "t_is_active as isActive, " +
            "t_user_id as userId, " +
            "t_interaction_role_id as todoInteractionRoleId, " +
            "t_status_role_id as todoStatusRoleId " +
            "from tdo01_todos " +
            "inner join usr01_users " +
            "on tdo01_todos.t_user_id = usr01_users.user_id " +
            "where usr01_users.user_id = :userId";
}
