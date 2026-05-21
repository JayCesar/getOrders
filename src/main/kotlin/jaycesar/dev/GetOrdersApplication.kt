package jaycesar.dev

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GetOrdersApplication

fun main(args: Array<String>) {
    runApplication<GetOrdersApplication>(*args)
}
