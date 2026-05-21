package jaycesar.dev.core.domain

import java.math.BigDecimal
import java.time.LocalDateTime

data class DeliveryReportSummary(
    val id: Long,
    val createdAt: LocalDateTime,
    val riskLevel: String,
    val failureRatePercent: BigDecimal?,
    val totalDeliveries: Int
)
