package jaycesar.dev.core.domain

import java.time.LocalDateTime

data class AffectedCustomer(
    val id: Long,
    val reportId: Long,
    val name: String?,
    val whatsapp: String?,
    val failureReason: String?,
    val region: String?,
    val carrier: String?,
    val deliveryAttemptedAt: LocalDateTime?,
    val whatsappSent: Boolean,
    val whatsappSentAt: LocalDateTime?,
    val createdAt: LocalDateTime
)
