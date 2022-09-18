package ch.awae.appremote.approxy.proxy

import ch.awae.appremote.approxy.api.ProxyLogLineProcessor
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class ProxyLogger : ProxyLogLineProcessor {

    private val logger = Logger.getLogger(ProxyLogger::class.java.name)

    override fun handle(line: String) {
        logger.info(line)
    }

}