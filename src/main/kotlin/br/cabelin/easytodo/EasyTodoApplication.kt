package br.cabelin.easytodo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EasyTodoApplication

fun main(args: Array<String>) {
  runApplication<EasyTodoApplication>(*args)
}
