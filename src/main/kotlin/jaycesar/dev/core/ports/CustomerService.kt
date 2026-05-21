package jaycesar.dev.core.ports

import jaycesar.dev.core.domain.AffectedCustomer
import java.time.LocalDateTime

interface CustomerService {
    fun listCustomers(
        reportId: Long,
        page: Int,
        perPage: Int,
        search: String?,
        region: String?,
        whatsappSent: Boolean?
    ): CustomerPage

    fun markWhatsappSent(id: Long): Pair<Boolean, LocalDateTime>
}

data class CustomerPage(
    val data: List<AffectedCustomer>,
    val total: Long,
    val page: Int,
    val perPage: Int,
    val pages: Int
)
