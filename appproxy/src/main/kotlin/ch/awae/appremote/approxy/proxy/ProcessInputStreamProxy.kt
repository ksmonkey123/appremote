package ch.awae.appremote.approxy.proxy

import ch.awae.appremote.approxy.api.InputStreamProxy
import ch.awae.appremote.approxy.api.ProxyLogService
import java.io.PrintWriter

class ProcessInputStreamProxy(
    private val logService: ProxyLogService,

    process: ProcessWrapper,
) : InputStreamProxy {

    private val writer = PrintWriter(process.outputStream)

    override fun relay(line: String) {
        logService.log("sending input: $line")
        writer.println(line)
        writer.flush()
    }
}