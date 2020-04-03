package br.cabelin.easytodo.controllers

import br.cabelin.easytodo.models.Todo
import br.cabelin.easytodo.services.TodoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("todos")
class TodoController(val todoService: TodoService) {
  @GetMapping
  fun list(): ResponseEntity<List<Todo>> {
    return ResponseEntity.ok(todoService.getAll())
  }

  @PostMapping
  fun add(@RequestBody todo: Todo): ResponseEntity<Todo> {
    return ResponseEntity.ok(todoService.create(todo))
  }

  @PutMapping("{id}")
  fun edit(@PathVariable id: Long, @RequestBody todo: Todo): ResponseEntity<Todo> {
    if (!todoService.exists(id)) {
      return ResponseEntity.notFound().build()
    }
    val safeTodo = todo.copy(id = id)
    return ResponseEntity.ok(todoService.update(safeTodo))
  }

  @DeleteMapping("{id}")
  fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
    if (!todoService.exists(id)) {
      return ResponseEntity.notFound().build()
    }
    todoService.delete(id)
    return ResponseEntity.noContent().build()
  }
}
