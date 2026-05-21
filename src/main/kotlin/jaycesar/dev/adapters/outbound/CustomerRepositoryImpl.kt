package jaycesar.dev.adapters.outbound

import jaycesar.dev.core.domain.AffectedCustomer
import jaycesar.dev.core.ports.CustomerRepository
import jakarta.persistence.criteria.Predicate
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class CustomerRepositoryImpl(
    private val jpa: SpringDataCustomerRepository
) : CustomerRepository {

    override fun findByReportId(
        reportId: Long,
        page: Int,
        perPage: Int,
        search: String?,
        region: String?,
        whatsappSent: Boolean?
    ): Pair<List<AffectedCustomer>, Long> {
        val spec = Specification<AffectedCustomerEntity> { root, _, cb ->
            val predicates = mutableListOf<Predicate>()

            predicates += cb.equal(root.get<Long>("reportId"), reportId)

            if (!search.isNullOrBlank()) {
                val pattern = "%${search.lowercase()}%"
                predicates += cb.or(
                    cb.like(cb.lower(root.get("name")), pattern),
                    cb.like(cb.lower(root.get("whatsapp")), pattern),
                    cb.like(cb.lower(root.get("failureReason")), pattern)
                )
            }

            if (!region.isNullOrBlank()) {
                predicates += cb.equal(root.get<String>("region"), region)
            }

            if (whatsappSent != null) {
                predicates += cb.equal(root.get<Boolean>("whatsappSent"), whatsappSent)
            }

            cb.and(*predicates.toTypedArray())
        }

        val result = jpa.findAll(spec, PageRequest.of(page - 1, perPage))
        return Pair(result.content.map { it.toDomain() }, result.totalElements)
    }

    @Transactional
    override fun markWhatsappSent(id: Long): LocalDateTime? {
        if (!jpa.existsById(id)) return null
        val sentAt = LocalDateTime.now()
        jpa.markSent(id, sentAt)
        return sentAt
    }

    private fun AffectedCustomerEntity.toDomain() = AffectedCustomer(
        id = id!!,
        reportId = reportId,
        name = name,
        whatsapp = whatsapp,
        failureReason = failureReason,
        region = region,
        whatsappSent = whatsappSent,
        whatsappSentAt = whatsappSentAt,
        createdAt = createdAt
    )
}
