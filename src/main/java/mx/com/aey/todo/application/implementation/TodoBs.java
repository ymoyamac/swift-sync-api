package mx.com.aey.todo.application.implementation;

import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import mx.com.aey.todo.domain.entity.Todo;
import mx.com.aey.todo.domain.entity.TodoInteractionRole;
import mx.com.aey.todo.domain.entity.TodoStatusRole;
import mx.com.aey.todo.domain.repository.TodoRepository;
import mx.com.aey.todo.domain.service.TodoService;
import mx.com.aey.user.domain.service.UserService;
import mx.com.aey.util.error.ErrorCode;
import mx.com.aey.util.schema.ResponseCode;

import java.util.*;

@ApplicationScoped
public class TodoBs implements TodoService {

    @Inject
    UserService userService;
    @Inject
    TodoRepository todoRepository;

    @Override
    public Either<ErrorCode, Set<Todo>> getAllTodosByUserId(UUID userId) {
        var userFound = userService.getUserById(userId);
        if (userFound.isLeft()) {
            return Either.left(ErrorCode.NOT_FOUND);
        }
        return todoRepository.findAllByUserId(userFound.get().getUserId())
                .<Either<ErrorCode, Set<Todo>>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.ERROR));
    }

    @Override
    public Either<ErrorCode, Todo> getTodoById(UUID todoId) {
        var todoFound = todoRepository.findOneById(todoId);
        if (todoFound.isEmpty()) {
            return Either.left(ErrorCode.NOT_FOUND);
        }
        if (todoFound.get().getIsActive().equals(Boolean.FALSE)) {
            return Either.left(ErrorCode.RESOURCE_NOT_AVAILABLE);
        }
        return Either.right(todoFound.get());
    }

    @Override
    @Transactional
    public Either<ErrorCode, Todo> create(UUID userId, Todo todo) {
        var userFound = userService.getUserById(userId);
        if (userFound.isLeft()) {
            return Either.left(ErrorCode.NOT_FOUND);
        }
        todo.setIsCompleted(Boolean.FALSE);
        todo.setCreationAt(new Date());
        todo.setUpdateAt(new Date());
        todo.setIsActive(Boolean.TRUE);
        todo.setUserId(userFound.get().getUserId());
        todo.setTodoInteractionRoleId(1);
        todo.setTodoStatusRoleId(1);
        return todoRepository.create(todo)
                .<Either<ErrorCode, Todo>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.ERROR_TO_CREATE));
    }

    @Override
    public Either<ErrorCode, Todo> update(UUID todoId, Todo todoTo) {
        return getTodoById(todoId)
                .map(todo -> {
                    todo.setTitle(todoTo.getTitle() != null ? todoTo.getTitle() : todo.getTitle());
                    todo.setDescription(todoTo.getDescription() != null ? todoTo.getDescription() : todo.getDescription());
                    todo.setIsCompleted(todoTo.getIsCompleted() != null ? todoTo.getIsCompleted() : todo.getIsCompleted());
                    todo.setCreationAt(todo.getCreationAt());
                    todo.setUpdateAt(new Date());
                    todo.setIsActive(todo.getIsActive());
                    todo.setUserId(todo.getUserId());
                    Optional<Todo> todoUpdated = todoRepository.update(todo);
                    return todoUpdated.orElse(null);
                });
    }

    @Override
    public Either<ErrorCode, ResponseCode> deleteTodoById(UUID todoId) {
        return getTodoById(todoId)
                .map(todo -> {
                    todoRepository.deleteTodo(todoId);
                    return ResponseCode.DELETED;
                });
    }

    @Override
    public Either<ErrorCode, TodoInteractionRole> getTodoInteractionRole(Integer id) {
        return todoRepository.getTodoInteractionRole(id)
                .<Either<ErrorCode, TodoInteractionRole>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.ERROR));
    }

    @Override
    public Either<ErrorCode, TodoStatusRole> getTodoStatusRole(Integer id) {
        return todoRepository.getTodoStatusRole(id)
                .<Either<ErrorCode, TodoStatusRole>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.ERROR));
    }
}
