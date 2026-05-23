package jaycesar.dev.adapters.outbound

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jaycesar.dev.core.domain.DeliveryReport
import jaycesar.dev.core.domain.DeliveryReportSummary
import jaycesar.dev.core.domain.ReportTrend
import jaycesar.dev.core.ports.ReportRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ReportRepositoryImpl(
    private val jpa: SpringDataReportRepository
) : ReportRepository {

    private val mapper = jacksonObjectMapper()

    override fun findSummaries(limit: Int, offset: Int): List<DeliveryReportSummary> {
        val pageNumber = if (limit > 0) offset / limit else 0
        return jpa.findSummaries(PageRequest.of(pageNumber, limit)).map { it.toSummary() }
    }

    override fun findTrend(limit: Int): List<ReportTrend> =
        jpa.findTrend(PageRequest.of(0, limit))
            .map { ReportTrend(it.deliveryDateFrom ?: it.createdAt, it.failureRatePercent, it.riskLevel, it.deliveryDateFrom) }

    override fun findById(id: Long): DeliveryReport? =
        jpa.findById(id).orElse(null)?.toDomain()

    override fun findSummariesByDateRange(from: LocalDateTime, to: LocalDateTime): List<DeliveryReportSummary> =
        jpa.findByDeliveryDateRange(from, to).map { it.toSummary() }

    override fun findTrendByDateRange(from: LocalDateTime, to: LocalDateTime): List<ReportTrend> =
        jpa.findByDeliveryDateRangeAsc(from, to)
            .map { ReportTrend(it.deliveryDateFrom ?: it.createdAt, it.failureRatePercent, it.riskLevel, it.deliveryDateFrom) }

    private fun DeliveryReportEntity.toSummary() = DeliveryReportSummary(
        id = id!!,
        createdAt = createdAt,
        riskLevel = riskLevel,
        failureRatePercent = failureRatePercent,
        totalDeliveries = totalDeliveries,
        deliveryDateFrom = deliveryDateFrom,
        deliveryDateTo = deliveryDateTo
    )

    private fun DeliveryReportEntity.toDomain() = DeliveryReport(
        id = id!!,
        sourceFile = sourceFile,
        analysisFile = analysisFile,
        riskLevel = riskLevel,
        executiveSummary = executiveSummary,
        totalDeliveries = totalDeliveries,
        totalFailures = totalFailures,
        totalSuccesses = totalSuccesses,
        failureRatePercent = failureRatePercent,
        successRatePercent = successRatePercent,
        averageDeliveryAttempts = averageDeliveryAttempts,
        criticalRegions = parseJson(criticalRegions),
        criticalCarriers = parseJson(criticalCarriers),
        topFailureReasons = parseJson(topFailureReasons),
        operationalPatterns = parseJson(operationalPatterns),
        customerRisks = parseJson(customerRisks),
        fraudIndicators = parseJson(fraudIndicators),
        recommendations = parseJson(recommendations),
        suggestedActions = parseJson(suggestedActions),
        deliveryDateFrom = deliveryDateFrom,
        deliveryDateTo = deliveryDateTo,
        createdAt = createdAt
    )

    private fun parseJson(raw: String?): Any? {
        if (raw.isNullOrBlank()) return null
        return try {
            mapper.readValue(raw, Any::class.java)
        } catch (e: Exception) {
            null
        }
    }
}
