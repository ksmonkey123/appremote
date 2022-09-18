package ch.awae.appremote.approxy.proxy

import ch.awae.appremote.approxy.api.ProxyLogLineProcessor
import ch.awae.appremote.approxy.api.ProxyLogService

class CompositeProxyLogService(
    private val processors: List<ProxyLogLineProcessor>,
) : ProxyLogService {

    override fun log(line: String) {
        processors.forEach {
            kotlin.runCatching {
                it.handle(line)
            }
        }
    }

}