package shop.test

import org.junit.After
import org.junit.Test
import shop.Item
import shop.StockRepository
import kotlin.test.assertEquals

class StockRepositoryTest {
    @After
    fun clear() = StockRepository.resetStock(0)

    @Test
    fun `should support setting initial stock`() {
        // given:
        assertEquals(0, StockRepository.stockOf(Item.A).qty)
        assertEquals(0, StockRepository.stockOf(Item.B).qty)

        // when:
        StockRepository.setStock(Item.A, 30)
        StockRepository.setStock(Item.B, 90)

        // then:
        assertEquals(30, StockRepository.stockOf(Item.A).qty)
        assertEquals(90, StockRepository.stockOf(Item.B).qty)
    }
}
