package br.cabelin.easytodo.services

import br.cabelin.easytodo.models.Todo
import br.cabelin.easytodo.repositories.TodoRepository
import org.springframework.stereotype.Service

@Service
class TodoServiceImpl(val todoRepository: TodoRepository) : TodoService {
  override fun getAll(): List<Todo> {
    return todoRepository.findAll().toList()
  }

  override fun create(todo: Todo): Todo {
    return todoRepository.save(todo)
  }

  override fun update(todo: Todo): Todo {
    if (!todoRepository.existsById(todo.id)) return Todo()
    return todoRepository.save(todo)
  }

  override fun exists(id: Long): Boolean {
    return todoRepository.existsById(id)
  }

  override fun delete(id: Long) {
    todoRepository.deleteById(id)
  }
}
