package ru.shalimski.zakupkiNsiREST

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "spring.datasource")
object ConfigProrerties {
    var url: String = "jdbc:postgresql://localhost:5432/testbase"
    var username: String = "postgres1"
    var password: String = ""
}