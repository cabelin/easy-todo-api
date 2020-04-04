package br.cabelin.easytodo.controllers

import br.cabelin.easytodo.models.Todo
import br.cabelin.easytodo.services.TodoService
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher.matchAll
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest
class TodoControllerUnitTest(@Autowired val mockMvc: MockMvc, @Autowired val objectMapper: ObjectMapper) {

  private val baseUrl = "/todos"

  @MockBean
  private lateinit var todoService: TodoService

  @Test
  fun `When request all todo's and exists then return status 200 and a list of todo's`() {
    val todos = listOf(
      Todo(id = 1, title = "Task title 01", description = "Task description 01"))

    `when`(todoService.getAll()).thenReturn(todos);

    mockMvc.perform(get(baseUrl).contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk)
      .andExpect(jsonPath("$", hasSize<Any>(1)))
      .andExpect(matchAll(
        jsonPath("$[0].id", equalTo(1)),
        jsonPath("$[0].title", equalTo("Task title 01")),
        jsonPath("$[0].description", equalTo("Task description 01"))
      ))
  }

  @Test
  fun `When request all todo's and not exists then return status 200 and empty list`() {
    `when`(todoService.getAll()).thenReturn(listOf());

    mockMvc.perform(get(baseUrl).contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk)
      .andExpect(jsonPath("$", hasSize<Any>(0)))
  }

  @Test
  fun `When request create todo then return status 200 and todo`() {
    val todo = Todo(id = 1, title = "Task title 01", description = "Task description 01")
    `when`(todoService.create(todo)).thenReturn(todo);

    mockMvc.perform(post(baseUrl)
      .content(objectMapper.writeValueAsString(todo))
      .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk)
      .andExpect(matchAll(
        jsonPath("$.id", equalTo(1)),
        jsonPath("$.title", equalTo("Task title 01")),
        jsonPath("$.description", equalTo("Task description 01"))
      ))
  }

  @Test
  fun `When request update existing todo then return status 200 and todo`() {
    val id = 1L;
    val todo = Todo(title = "Task title 00", description = "Task description 00")

    `when`(todoService.exists(id)).thenReturn(true)

    val safeTodo = todo.copy(id);
    `when`(todoService.update(safeTodo)).thenReturn(safeTodo);

    mockMvc.perform(put("$baseUrl/{id}", id)
      .content(objectMapper.writeValueAsString(todo))
      .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk)
      .andExpect(matchAll(
        jsonPath("$.id", equalTo(1)),
        jsonPath("$.title", equalTo("Task title 00")),
        jsonPath("$.description", equalTo("Task description 00"))
      ))
  }

  @Test
  fun `When request update not existing todo then return status 404`() {
    val id = 1L;
    val todo = Todo(title = "Task title 00", description = "Task description 00")

    `when`(todoService.exists(id)).thenReturn(false)

    val safeTodo = todo.copy(id);
    `when`(todoService.update(safeTodo)).thenReturn(safeTodo);

    mockMvc.perform(put("$baseUrl/{id}", id)
      .content(objectMapper.writeValueAsString(todo))
      .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNotFound)
  }

  @Test
  fun `When request delete existing todo then return status 204`() {
    val id = 1L;

    `when`(todoService.exists(id)).thenReturn(true)
    doNothing().`when`(todoService).delete(id)

    mockMvc.perform(delete("$baseUrl/{id}", id)
      .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNoContent)
  }

  @Test
  fun `When request delete not existing todo then return status 404`() {
    val id = 1L;

    `when`(todoService.exists(id)).thenReturn(false)
    doNothing().`when`(todoService).delete(id)

    mockMvc.perform(delete("$baseUrl/{id}", id)
      .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNotFound)
  }
}
