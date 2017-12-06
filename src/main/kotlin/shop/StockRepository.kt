package shop

import io.ktor.html.each
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

data class Stock (val item: Item) {
    var qty: Int = 0
}

object StockRepository {
    private val items = ConcurrentHashMap<Item, Stock>()

    init {
        Item.values().forEach {
            items[it] = Stock(it)
        }
    }

    fun stockOf(item: Item): Stock {
        return items[item]!!
    }

    fun setStock(item: Item, initialStock: Int) {
        items[item]!!.qty = initialStock
    }

    fun resetStock(initialStock: Int) {
        Item.values().forEach {
            setStock(it, initialStock)
        }
    }
}