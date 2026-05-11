import kotlin.math.max

fun main() {
val result = commission("Visa", 200,0)

    when (result) {
        is CommissionResult.Success -> println("Commission: ${result.commission}")
        is CommissionResult.Error -> println("Error: ${result.message}")
    }
}

sealed class CommissionResult {
    data class Success(val commission: Int) : CommissionResult()
    data class Error(val message: String) : CommissionResult()
}

fun commission(type: String, amount: Int, previous: Int): CommissionResult {
    if (amount > 150_000 || amount + previous > 600_000) { return CommissionResult.Error("Limit exceeded") }
    return when (type) {
        "Mastercard", "Maestro" -> { if (amount !in 300..75_000) CommissionResult.Success((amount * 0.006).toInt()) else CommissionResult.Success(0)
        }
        "Visa", "Мир" -> { CommissionResult.Success(max(35, (amount * 0.0075).toInt()))
        }
        "VK Pay" -> { if (amount > 15_000 || amount + previous > 40_000) CommissionResult.Error("Limit transfer") else CommissionResult.Success(0)
        }
        else -> {CommissionResult.Error("Unknown Type Card")}
    }
}