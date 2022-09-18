package ch.awae.appremote.approxy.rabbitmq

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Configuration
import java.util.logging.Logger

@Configuration
@ConditionalOnProperty("rabbitmq.enabled", matchIfMissing = true)
class RabbitMqConfiguration(val rabbitMqProperties: RabbitMqProperties) {

    private val logger = Logger.getLogger(RabbitMqConfiguration::class.java.name)

    init {
        logger.info("configuring RabbitMQ with properties $rabbitMqProperties")
    }

}