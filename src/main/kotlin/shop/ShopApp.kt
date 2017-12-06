package shop

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.html.*
import kotlinx.html.*
import kotlinx.html.dom.*

fun Application.main() {
    StockRepository.setStock(Item.A, 20)
    StockRepository.setStock(Item.B, 10)
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/") {
            call.respondHtml {
                head {
                    title("Shop demo application")
                }
                body {
                    h1 {+"Items"}
                    p {
                        ul {
                            Item.values().map {
                                li {+"${it.label}: ${StockRepository.stockOf(it).qty} left"}
                            }
                        }
                    }
                }
            }
        }
    }
}