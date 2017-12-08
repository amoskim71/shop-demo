package shop.test

import io.ktor.server.testing.it
import io.ktor.server.testing.on
import org.junit.After
import org.junit.Test
import shop.Item
import shop.StockRepository
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class StockRepositoryTest {
    @After
    fun clear() = StockRepository.resetStock(0)

    @Test
    fun `initial stock`() {
        assertEquals(0, StockRepository.stockOf(Item.A))
        assertEquals(0, StockRepository.stockOf(Item.B))

        on ("setting initial stock") {
            StockRepository.setStock(Item.A, 30)
            StockRepository.setStock(Item.B, 90)

            it ("should return set stock") {
                assertEquals(30, StockRepository.stockOf(Item.A))
                assertEquals(90, StockRepository.stockOf(Item.B))
            }
        }
    }

    @Test
    fun `selling stock`() {
        StockRepository.setStock(Item.A, 30)
        StockRepository.setStock(Item.B, 90)
        assertEquals(30, StockRepository.stockOf(Item.A))
        assertEquals(90, StockRepository.stockOf(Item.B))

        on ("setting selling stock") {
            StockRepository.sell(Item.A, 10)
            StockRepository.sell(Item.B, 15)

            it ("should decrease stock by sold amount") {
                assertEquals(20, StockRepository.stockOf(Item.A))
                assertEquals(75, StockRepository.stockOf(Item.B))
            }
        }
    }

    @Test
    fun `not enough stock`() {
        StockRepository.setStock(Item.A, 0)
        StockRepository.setStock(Item.B, 10)
        assertEquals(0, StockRepository.stockOf(Item.A))
        assertEquals(10, StockRepository.stockOf(Item.B))

        on ("selling if the stock is empty") {
            it ("should fail with IllegalArgumentException") {
                assertFailsWith(IllegalArgumentException::class) {
                    StockRepository.sell(Item.A, 1)
                }
            }
            it ("should remain empty stock") {
                assertEquals(0, StockRepository.stockOf(Item.A))
            }
        }

        on ("selling if there is not enough stock") {
            it ("should fail with IllegalArgumentException") {
                assertFailsWith(IllegalArgumentException::class) {
                    StockRepository.sell(Item.B, 31)
                }
            }
            it ("should remain previous stock") {
                assertEquals(10, StockRepository.stockOf(Item.B))
            }

        }
    }

}
