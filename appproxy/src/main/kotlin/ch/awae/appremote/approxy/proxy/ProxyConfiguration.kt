package ch.awae.appremote.approxy.proxy

import ch.awae.appremote.approxy.api.AppLogLineProcessor
import ch.awae.appremote.approxy.api.InputStreamProxy
import ch.awae.appremote.approxy.api.ProxyLogLineProcessor
import ch.awae.appremote.approxy.api.ProxyLogService
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.logging.Logger
import kotlin.math.max

@Configuration
class ProxyConfiguration(private val proxyProperties: ProxyProperties) {

    private val logger = Logger.getLogger(ProxyConfiguration::class.java.name)

    init {
        logger.info("configuring proxy with properties $proxyProperties")
    }

    @Bean
    fun compositeLogLineProcessor(processors: List<AppLogLineProcessor>) = CompositeLogLineProcessor(processors)

    @Bean
    fun proxyLogService(processors: List<ProxyLogLineProcessor>) = CompositeProxyLogService(processors)

    @Bean
    fun processWrapper(): ProcessWrapper {
        logger.info("starting app with command: '${proxyProperties.command}'")
        return ProcessWrapper(Runtime.getRuntime().exec(proxyProperties.command))
    }

    @Bean
    fun inputStreamProxy(processWrapper: ProcessWrapper, logService: ProxyLogService) =
        ProcessInputStreamProxy(logService, processWrapper)

    @Bean("outputThread")
    fun createOutputThread(processWrapper: ProcessWrapper, processors: CompositeLogLineProcessor) =
        OutputThread(processors, false, processWrapper.inputStream)

    @Bean("errorThread")
    fun createErrorThread(processWrapper: ProcessWrapper, processors: CompositeLogLineProcessor) =
        OutputThread(processors, true, processWrapper.errorStream)

    @Bean
    fun appShutdownHook(processWrapper: ProcessWrapper) =
        AppShutdownHook(processWrapper, max(proxyProperties.shutdownGracePeriod ?: -1, -1))

    @Bean
    fun fileInputRunner(inputStreamProxy: InputStreamProxy) = ApplicationRunner {
        val filepath = proxyProperties.inputFile
        if (filepath != null) {
            val thread = FileInputThread(inputStreamProxy, filepath)
            thread.isDaemon = true
            thread.start()
        } else {
            logger.info("no file-input configured, not starting file input thread")
        }
    }

}
