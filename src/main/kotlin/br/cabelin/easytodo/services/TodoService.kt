package br.cabelin.easytodo.services

import br.cabelin.easytodo.models.Todo

interface TodoService {
  fun getAll(): List<Todo>
  fun create(todo: Todo): Todo
  fun update(todo: Todo): Todo
  fun exists(id: Long): Boolean
  fun delete(id: Long)
}
