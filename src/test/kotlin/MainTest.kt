import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class MainTest {
    @Test
    fun mastercardWithCommission() {
        val type = "Mastercard"
        val amount = 76_000
        val previous = 0
        val result = commission(type = type, amount = amount, previous = previous)
        when (result) {
            is CommissionResult.Success -> {
                assertEquals(456, result.commission)
            }

            is CommissionResult.Error -> {
                fail("Expected Success")
            }
        }
    }

    @Test
    fun maestroLowNonCommissionLevel() {
        val type = "Maestro"
        val amount = 300
        val previous = 0
        val result = commission(type = type, amount = amount, previous = previous)
        when (result) {
            is CommissionResult.Success -> {
                assertEquals(0, result.commission)
            }
            is CommissionResult.Error -> {
                fail("Expected Success")
            }
        }
    }

    @Test
    fun maestroHighNonCommissionLevel() {
        val type = "Maestro"
        val amount = 75_000
        val previous = 0
        val result = commission(type = type, amount = amount, previous = previous)
        when (result) {
            is CommissionResult.Success -> {
                assertEquals(0, result.commission)
            }
            is CommissionResult.Error -> {
                fail("Expected Success")
            }
        }
    }

    @Test
    fun mastercardWithoutCommission() {
        val type = "Mastercard"
        val amount = 200
        val previous = 0
        val result = commission(type = type, amount = amount, previous = previous)
        when (result) {
            is CommissionResult.Success -> {
                assertEquals(1, result.commission)
            }
            is CommissionResult.Error -> {
                fail("Expected Success")
            }
        }
    }

    @Test
    fun visaCommissionSuccess() {
        val type = "Visa"
        val amount = 100
        val previous = 0
        val result = commission(type = type, amount = amount, previous = previous)
        when (result) {
            is CommissionResult.Success -> {
                assertEquals(35, result.commission)
            }
            is CommissionResult.Error -> {
                fail("Expected Success")
            }
        }
    }

    @Test
    fun mirCommissionSuccess() {
        val type = "Мир"
        val amount = 10_000
        val previous = 0
        val result = commission(type = type, amount = amount, previous = previous)
        when (result) {
            is CommissionResult.Success -> {
                assertEquals(75, result.commission)
            }
            is CommissionResult.Error -> {
                fail("Expected Success")
            }
        }
    }

    @Test
    fun vkPayLowLimitTransferSuccess() {
        val type = "VK Pay"
        val amount = 15_000
        val previous = 0
        val result = commission(type = type, amount = amount, previous = previous)
        when (result) {
            is CommissionResult.Success -> {
                assertEquals(0, result.commission)
            }
            is CommissionResult.Error -> {
                fail("Expected Success")
            }
        }
    }

    @Test
    fun vkPayHighLimitTransferSuccess() {
        val type = "VK Pay"
        val amount = 1_000
        val previous = 39_000
        val result = commission(type = type, amount = amount, previous = previous)
        when (result) {
            is CommissionResult.Success -> {
                assertEquals(0, result.commission)
            }
            is CommissionResult.Error -> {
                fail("Expected Success")
            }
        }
    }

    @Test
    fun errorDailyLimitExceed() {
        val type = "Visa"
        val amount = 200_000
        val previous = 0
        val result = commission(type = type, amount = amount, previous = previous)
        when (result) {
            is CommissionResult.Error -> {
                assertEquals("Limit exceeded", result.message)
            }
            is CommissionResult.Success -> {
                fail("Expected Error")
            }
        }
    }

    @Test
    fun errorMonthlyLimitExceed() {
        val type = "Visa"
        val amount = 1_000
        val previous = 600_000
        val result = commission(type = type, amount = amount, previous = previous)
        when (result) {
            is CommissionResult.Error -> {
                assertEquals("Limit exceeded", result.message)
            }

            is CommissionResult.Success -> {
                fail("Expected Error")
            }
        }
    }

    @Test
    fun errorVkPayDaylyLimitTransfer() {
        val type = "VK Pay"
        val amount = 16_000
        val previous = 0
        val result = commission(type = type, amount = amount, previous = previous)
        when (result) {
            is CommissionResult.Error -> {
                assertEquals("Limit transfer", result.message)
            }
            is CommissionResult.Success -> {
                fail("Expected Error")
            }
        }
    }

    @Test
    fun errorVkPayMonthlyLimitTransfer() {
        val type = "VK Pay"
        val amount = 1_000
        val previous = 40_000
        val result = commission(type = type, amount = amount, previous = previous)
        when (result) {
            is CommissionResult.Error -> {
                assertEquals("Limit transfer", result.message)
            }
            is CommissionResult.Success -> {
                fail("Expected Error")
            }
        }
    }

    @Test
    fun errorUnknownTypeCard() {
        val type = "Salut"
        val amount = 10_000
        val previous = 0
        val result = commission(type = type, amount = amount, previous = previous)
        when (result) {
            is CommissionResult.Error -> {
                assertEquals("Unknown Type Card", result.message)
            }
            is CommissionResult.Success -> {
                fail("Expected Error")
            }
        }
    }
}