package br.cabelin.easytodo.repositories

import br.cabelin.easytodo.models.Todo
import org.springframework.data.repository.CrudRepository

interface TodoRepository : CrudRepository<Todo, Long>
