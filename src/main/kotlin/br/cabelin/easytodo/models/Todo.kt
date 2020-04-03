package br.cabelin.easytodo.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Todo(
  @Id
  @GeneratedValue
  val id: Long = 0L,
  val title: String = "",
  val description: String = "")
