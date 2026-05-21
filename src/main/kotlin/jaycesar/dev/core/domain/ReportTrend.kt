package jaycesar.dev.core.domain

import java.math.BigDecimal
import java.time.LocalDateTime

data class ReportTrend(
    val createdAt: LocalDateTime,
    val failureRatePercent: BigDecimal?,
    val riskLevel: String
)
