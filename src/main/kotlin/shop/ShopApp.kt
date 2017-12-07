package shop

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.html.*
import io.ktor.request.receive
import io.ktor.request.receiveParameters
import io.ktor.request.receiveText
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
                                li {+"${it.label}: ${StockRepository.stockOf(it)} left"}
                            }
                        }
                    }

                    form (action = "/stock", method = FormMethod.post) {
                        label {+"Item A:"}
                        input (name = "item_a")
                        br
                        label {+"Item B:"}
                        input (name = "item_b")
                        br
                        input (type = InputType.submit)
                    }
                }
            }
        }

        post("/stock") {
            var response = ""
            call.receiveText().parseUrlEncodedParameters().forEach() { paramName, values ->
                val item = Item.values().find { it.paramName == paramName } ?: throw IllegalArgumentException("Item not found")
                val qty = values.first().toInt()
                response += "- ${item.label}: ${qty} <br>"
                StockRepository.sell(item, qty)
            }
            call.respondText("Items bought: <br> ${response}", ContentType.Text.Html, HttpStatusCode.Created)
        }
    }
}