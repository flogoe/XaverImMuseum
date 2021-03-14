/*
 * Copyright (C) 2020 - museum x, Karlsruhe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package x.museum.chase.config.security

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod.DELETE
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.OPTIONS
import org.springframework.http.HttpMethod.PATCH
import org.springframework.http.HttpMethod.POST
import org.springframework.http.HttpMethod.PUT
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import x.museum.chase.Router.Companion.apiPath
import x.museum.chase.Router.Companion.authPath
import java.util.*

//import x.museum.chase.config.security.dev.quest

interface SecurityConfig {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity, context: ApplicationContext) : SecurityWebFilterChain = http
            .authorizeExchange { exchanges ->
                val chasePath = "/chase$apiPath"
                val chasePathId = "$chasePath/*"

                exchanges
                        // Chase
                        .pathMatchers(GET, "/chase/api").permitAll()
                        .pathMatchers(GET, "/api/*").permitAll()
                        .pathMatchers(GET, "/*").permitAll()
                        .pathMatchers(GET, chasePath).permitAll()
                        .pathMatchers(GET, chasePathId).permitAll()
                        .pathMatchers(POST, chasePath).permitAll()
                        .pathMatchers(PUT, chasePathId).permitAll()

                        .pathMatchers(DELETE).permitAll()
            }
            .cors{ CorsConfiguration() }
            .httpBasic{}
            .formLogin{ form -> form.disable() }
            .headers { headers -> headers.contentSecurityPolicy("default-src 'self'") }
            // Cross-Site-Request-Forgery (TODO: Check if we need to enable it)
            .csrf { csrf -> csrf.disable() }
            .build()


    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        print("--------------- cors settings by gflow ---------------------")
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST")
        configuration.exposedHeaders = listOf("Location,ETag,Access-Control-Allow-Origin,Access-Control-Allow-Headers")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }


    companion object {

    }
}
