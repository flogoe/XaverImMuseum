package x.museum.chase.config.security


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.config.WebFluxConfigurerComposite


@Configuration
interface WebFluxConfig {
    @Bean
    fun corsConfigurer(): WebFluxConfigurer {
        return object : WebFluxConfigurerComposite() {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedMethods("*")
            }
        }
    }
}