package ch.awae.appremote.approxy.proxy

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("proxy")
data class ProxyProperties(
    /** the command executed to start the application */
    val command: String,
    /** the shutdown grace period (seconds). default: -1. timeout disabled if negative. */
    val shutdownGracePeriod: Long?,
    /** input file path for file based input */
    val inputFile: String?,
)