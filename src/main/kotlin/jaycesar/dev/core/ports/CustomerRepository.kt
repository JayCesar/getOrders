package jaycesar.dev.core.ports

import jaycesar.dev.core.domain.AffectedCustomer
import java.time.LocalDateTime

interface CustomerRepository {
    fun findByReportId(
        reportId: Long,
        page: Int,
        perPage: Int,
        search: String?,
        region: String?,
        whatsappSent: Boolean?
    ): Pair<List<AffectedCustomer>, Long>

    fun markWhatsappSent(id: Long): LocalDateTime?
}
