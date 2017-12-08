package shop.test

import io.ktor.application.Application
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.After
import kotlin.test.*
import org.junit.Test
import shop.Item
import shop.StockRepository
import shop.main

class ShopAppTest {

    @After
    fun reset() = StockRepository.resetStock(0)

    @Test
    fun `testing simple get`() = withTestApplication(Application::main) {
        on ("first request to /") {
            val result = handleRequest(HttpMethod.Get, "/")

            it ("should return initial stock of 20 and 10") {
                with (result) {
                    assertEquals(HttpStatusCode.OK, response.status())
                    assertTrue(response.content?.contains("Item A: 20 left")!!)
                    assertTrue(response.content?.contains("Item B: 10 left")!!)
                }
            }
        }
    }

    @Test
    fun `testing post`() = withTestApplication(Application::main) {
        StockRepository.resetStock(30)

        on("post to /stock") {
            val result = handleRequest {
                uri = "/stock"
                method = HttpMethod.Post
                body = "item_a=10&item_b=17"
            }

            it("should allow buying stock of both items") {
                with(result) {
                    assertEquals(HttpStatusCode.Created, response.status())
                    assertEquals(StockRepository.stockOf(Item.A), 20)
                    assertEquals(StockRepository.stockOf(Item.B), 13)
                }
            }
        }
    }
}