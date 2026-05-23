package jaycesar.dev.adapters.outbound

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "affected_customers")
class AffectedCustomerEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "report_id")
    val reportId: Long = 0,

    @Column(name = "name", length = 255)
    val name: String? = null,

    @Column(name = "whatsapp", length = 20)
    val whatsapp: String? = null,

    @Column(name = "failure_reason", columnDefinition = "TEXT")
    val failureReason: String? = null,

    @Column(name = "region", length = 100)
    val region: String? = null,

    @Column(name = "carrier", length = 100)
    val carrier: String? = null,

    @Column(name = "delivery_attempted_at")
    val deliveryAttemptedAt: LocalDateTime? = null,

    @Column(name = "whatsapp_sent")
    val whatsappSent: Boolean = false,

    @Column(name = "whatsapp_sent_at")
    val whatsappSentAt: LocalDateTime? = null,

    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
