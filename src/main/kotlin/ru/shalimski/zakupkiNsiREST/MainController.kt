package ru.shalimski.zakupkiNsiREST

import com.zaxxer.hikari.HikariDataSource
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.sql.SQLException
import javax.annotation.PostConstruct

@RestController
@RequestMapping(value = ["/organizations"], produces = [MediaType.APPLICATION_JSON_VALUE])
class MainController {

    lateinit var hikariDataSource: HikariDataSource

    @PostConstruct
    fun createPool() {

        hikariDataSource = HikariDataSource()
        hikariDataSource.jdbcUrl = ConfigProrerties.url
        hikariDataSource.username = ConfigProrerties.username
        hikariDataSource.password = ConfigProrerties.password
        hikariDataSource.maximumPoolSize = 3
        hikariDataSource.isReadOnly = true

    }

    @GetMapping("/{regNumber}")
    fun getOrganizationByRegNumber(@PathVariable regNumber: String): String {

        val conn = hikariDataSource.connection

        return try {

            val st = conn.prepareStatement("SELECT id, data FROM organizations WHERE id = '$regNumber';")
            val rs = st.executeQuery()

            if (rs.next()) {
                var res = rs.getString(2)
                conn.close()
                res
            } else {
                conn.close()
                "{\"error\":\"not found\"}"
            }

        } catch (e: SQLException) {
            e.printStackTrace()
            conn.close()
            return "{\"error\":\"failed\"}"
        }

    }

    @GetMapping("/inn/{inn}&{kpp}")
    fun getOrganizationByINNKPP(@PathVariable inn: String, @PathVariable kpp: String): String {

        val conn = hikariDataSource.connection

        return try {

            val st = conn.prepareStatement("SELECT id, data FROM organizations WHERE data @> '{\"INN\":\"$inn\", \"KPP\": \"$kpp\"}';")
            val rs = st.executeQuery()

            if (rs.next()) {
                var res = rs.getString(2)
                conn.close()
                res
            } else {
                conn.close()
                "{\"error\":\"not found\"}"
            }

        } catch (e: SQLException) {
            e.printStackTrace()
            conn.close()
            return "{\"error\":\"failed\"}"
        }
    }

}