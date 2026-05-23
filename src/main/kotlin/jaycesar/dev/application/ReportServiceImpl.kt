package jaycesar.dev.application

import jaycesar.dev.core.domain.CarrierStat
import jaycesar.dev.core.domain.DeliveryReport
import jaycesar.dev.core.domain.DeliveryReportSummary
import jaycesar.dev.core.domain.RegionStat
import jaycesar.dev.core.domain.ReportTrend
import jaycesar.dev.core.ports.CustomerRepository
import jaycesar.dev.core.ports.ReportRepository
import jaycesar.dev.core.ports.ReportService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.OffsetDateTime

@Service
class ReportServiceImpl(
    private val repository: ReportRepository,
    private val customerRepository: CustomerRepository
) : ReportService {

    override fun listReports(limit: Int, offset: Int, from: String?, to: String?): List<DeliveryReportSummary> =
        if (from != null && to != null)
            repository.findSummariesByDateRange(parseDateTime(from), parseDateTime(to))
        else
            repository.findSummaries(limit, offset)

    override fun getTrend(limit: Int, from: String?, to: String?): List<ReportTrend> =
        if (from != null && to != null)
            repository.findTrendByDateRange(parseDateTime(from), parseDateTime(to))
        else
            repository.findTrend(limit)

    override fun getReport(id: Long): DeliveryReport =
        repository.findById(id) ?: throw NoSuchElementException("Report $id not found")

    override fun getRegions(id: Long): Any? {
        val total = getReport(id).totalDeliveries.takeIf { it > 0 } ?: return emptyList<Any>()
        return customerRepository.countByRegion(id).map { (region, count) ->
            RegionStat(region, count, (count.toDouble() / total) * 100.0)
        }
    }

    override fun getCarriers(id: Long): Any? {
        val total = getReport(id).totalDeliveries.takeIf { it > 0 } ?: return emptyList<Any>()
        return customerRepository.countByCarrier(id).map { (carrier, count) ->
            CarrierStat(carrier, count, (count.toDouble() / total) * 100.0)
        }
    }

    override fun getReasons(id: Long): Any? = getReport(id).topFailureReasons

    private fun parseDateTime(s: String): LocalDateTime =
        try { OffsetDateTime.parse(s).toLocalDateTime() } catch (_: Exception) { LocalDateTime.parse(s) }
}
