package ch.awae.appremote.approxy.rabbitmq

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("rabbitmq")
@ConditionalOnProperty("rabbitmq.enabled", matchIfMissing = true)
data class RabbitMqProperties(
    /** default: true */
    val enabled: Boolean,
    /** RabbitMQ application ID */
    val appId: String,
)