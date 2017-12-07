package shop.test

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
    fun `should support setting initial stock`() {
        // given:
        assertEquals(0, StockRepository.stockOf(Item.A))
        assertEquals(0, StockRepository.stockOf(Item.B))

        // when:
        StockRepository.setStock(Item.A, 30)
        StockRepository.setStock(Item.B, 90)

        // then:
        assertEquals(30, StockRepository.stockOf(Item.A))
        assertEquals(90, StockRepository.stockOf(Item.B))
    }

    @Test
    fun `should support selling stock`() {
        // given:
        StockRepository.setStock(Item.A, 30)
        StockRepository.setStock(Item.B, 90)
        assertEquals(30, StockRepository.stockOf(Item.A))
        assertEquals(90, StockRepository.stockOf(Item.B))

        // when:
        StockRepository.sell(Item.A, 10)
        StockRepository.sell(Item.B, 15)

        // then:
        assertEquals(20, StockRepository.stockOf(Item.A))
        assertEquals(75, StockRepository.stockOf(Item.B))
    }

    @Test
    fun `should fail if there is not enough stock`() {
        // given:
        StockRepository.setStock(Item.A, 0)
        StockRepository.setStock(Item.B, 10)
        assertEquals(0, StockRepository.stockOf(Item.A))
        assertEquals(10, StockRepository.stockOf(Item.B))

        // when:
        assertFailsWith(IllegalArgumentException::class) {
            StockRepository.sell(Item.A, 1)
        }

        assertFailsWith(IllegalArgumentException::class) {
            StockRepository.sell(Item.B, 31)
        }

        // then:
        assertEquals(0, StockRepository.stockOf(Item.A))
        assertEquals(10, StockRepository.stockOf(Item.B))
    }

}
