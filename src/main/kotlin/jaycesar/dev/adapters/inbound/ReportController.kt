package jaycesar.dev.adapters.inbound

import jaycesar.dev.core.ports.ReportService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reports")
class ReportController(private val service: ReportService) {

    @GetMapping
    fun listReports(
        @RequestParam(defaultValue = "100") limit: Int,
        @RequestParam(defaultValue = "0") offset: Int,
        @RequestParam(required = false) from: String?,
        @RequestParam(required = false) to: String?
    ) = service.listReports(limit, offset, from, to)

    @GetMapping("/trend")
    fun getTrend(
        @RequestParam(defaultValue = "50") limit: Int,
        @RequestParam(required = false) from: String?,
        @RequestParam(required = false) to: String?
    ) = service.getTrend(limit, from, to)

    @GetMapping("/{id}")
    fun getReport(@PathVariable id: Long): ResponseEntity<Any> = try {
        ResponseEntity.ok(service.getReport(id))
    } catch (e: NoSuchElementException) {
        ResponseEntity.notFound().build()
    }

    @GetMapping("/{id}/regions")
    fun getRegions(@PathVariable id: Long): ResponseEntity<Any> = try {
        ResponseEntity.ok(service.getRegions(id))
    } catch (e: NoSuchElementException) {
        ResponseEntity.notFound().build()
    }

    @GetMapping("/{id}/carriers")
    fun getCarriers(@PathVariable id: Long): ResponseEntity<Any> = try {
        ResponseEntity.ok(service.getCarriers(id))
    } catch (e: NoSuchElementException) {
        ResponseEntity.notFound().build()
    }

    @GetMapping("/{id}/reasons")
    fun getReasons(@PathVariable id: Long): ResponseEntity<Any> = try {
        ResponseEntity.ok(service.getReasons(id))
    } catch (e: NoSuchElementException) {
        ResponseEntity.notFound().build()
    }
}
