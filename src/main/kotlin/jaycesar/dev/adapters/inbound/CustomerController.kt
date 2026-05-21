package jaycesar.dev.adapters.inbound

import jaycesar.dev.core.ports.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController(private val service: CustomerService) {

    @GetMapping("/reports/{id}/customers")
    fun listCustomers(
        @PathVariable id: Long,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(name = "per_page", defaultValue = "20") perPage: Int,
        @RequestParam(required = false) search: String?,
        @RequestParam(required = false) region: String?,
        @RequestParam(name = "whatsapp_sent", required = false) whatsappSent: Boolean?
    ): ResponseEntity<Any> = try {
        ResponseEntity.ok(service.listCustomers(id, page, perPage, search, region, whatsappSent))
    } catch (e: NoSuchElementException) {
        ResponseEntity.notFound().build()
    }

    @PatchMapping("/customers/{id}/whatsapp-sent")
    fun markWhatsappSent(@PathVariable id: Long): ResponseEntity<Any> = try {
        val (success, sentAt) = service.markWhatsappSent(id)
        ResponseEntity.ok(mapOf("success" to success, "sent_at" to sentAt))
    } catch (e: NoSuchElementException) {
        ResponseEntity.notFound().build()
    }
}
