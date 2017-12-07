package shop

import java.util.concurrent.ConcurrentHashMap

object StockRepository {
    private val items = ConcurrentHashMap<Item, Int>()

    init {
        resetStock(0)
    }

    fun stockOf(item: Item): Int {
        return items.getOrDefault(item, 0)
    }

    fun setStock(item: Item, qty: Int) {
        items.put(item, qty)
    }

    fun resetStock(initialStock: Int) {
        Item.values().forEach {
            setStock(it, initialStock)
        }
    }

    fun sell(item: Item, qty: Int) {
        items.merge(item, qty) { prev, new ->
            if (new <= prev) {
                prev - new
            } else {
                throw IllegalArgumentException("Not enough ${item.label} stock. Is: ${prev}, needed: ${new}.")
            }
        }
    }
}