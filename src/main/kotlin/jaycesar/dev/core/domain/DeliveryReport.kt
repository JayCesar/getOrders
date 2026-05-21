package jaycesar.dev.core.domain

import java.math.BigDecimal
import java.time.LocalDateTime

data class DeliveryReport(
    val id: Long,
    val sourceFile: String,
    val analysisFile: String,
    val riskLevel: String,
    val executiveSummary: String?,
    val totalDeliveries: Int,
    val totalFailures: Int,
    val totalSuccesses: Int,
    val failureRatePercent: BigDecimal?,
    val successRatePercent: BigDecimal?,
    val averageDeliveryAttempts: BigDecimal?,
    val criticalRegions: Any?,
    val criticalCarriers: Any?,
    val topFailureReasons: Any?,
    val operationalPatterns: Any?,
    val customerRisks: Any?,
    val fraudIndicators: Any?,
    val recommendations: Any?,
    val suggestedActions: Any?,
    val createdAt: LocalDateTime
)
