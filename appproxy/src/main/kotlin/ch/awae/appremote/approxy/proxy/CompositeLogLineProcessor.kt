package ch.awae.appremote.approxy.proxy

import ch.awae.appremote.approxy.api.AppLogLineProcessor

class CompositeLogLineProcessor(
    private val processors: List<AppLogLineProcessor>,
) {

    fun handle(line: String, error: Boolean) {
        processors.forEach {
            kotlin.runCatching {
                it.handle(line, error)
            }
        }
    }

}