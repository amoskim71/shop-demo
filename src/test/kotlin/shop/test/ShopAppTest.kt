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
    fun `should support two kind of items with initial stock of 20 and 10`() = withTestApplication(Application::main) {
        // when:
        with (handleRequest(HttpMethod.Get, "/")) {
            // then:
            assertEquals(HttpStatusCode.OK, response.status())
            assertTrue(response.content?.contains("Item A: 20 left")!!)
            assertTrue(response.content?.contains("Item B: 10 left")!!)
        }
    }

    @Test
    fun `should allow buying stock of both items`() = withTestApplication(Application::main) {
        // given:
        StockRepository.resetStock(30)

        // when:
        with (handleRequest {
            uri = "/stock"
            method = HttpMethod.Post
            body = "item_a=10&item_b=17"
        }) {
            // then:
            assertEquals(HttpStatusCode.Created, response.status())
            assertEquals(StockRepository.stockOf(Item.A), 20)
            assertEquals(StockRepository.stockOf(Item.B), 13)
        }
    }
}