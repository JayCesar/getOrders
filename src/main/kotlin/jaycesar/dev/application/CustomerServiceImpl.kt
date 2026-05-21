package jaycesar.dev.application

import jaycesar.dev.core.ports.CustomerPage
import jaycesar.dev.core.ports.CustomerRepository
import jaycesar.dev.core.ports.CustomerService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.math.ceil

@Service
class CustomerServiceImpl(private val repository: CustomerRepository) : CustomerService {

    override fun listCustomers(
        reportId: Long,
        page: Int,
        perPage: Int,
        search: String?,
        region: String?,
        whatsappSent: Boolean?
    ): CustomerPage {
        val (customers, total) = repository.findByReportId(reportId, page, perPage, search, region, whatsappSent)
        val pages = if (total == 0L) 1 else ceil(total.toDouble() / perPage).toInt()
        return CustomerPage(customers, total, page, perPage, pages)
    }

    override fun markWhatsappSent(id: Long): Pair<Boolean, LocalDateTime> {
        val sentAt = repository.markWhatsappSent(id)
            ?: throw NoSuchElementException("Customer $id not found")
        return Pair(true, sentAt)
    }
}
