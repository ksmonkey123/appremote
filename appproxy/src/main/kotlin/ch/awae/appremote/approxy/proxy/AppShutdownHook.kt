package ch.awae.appremote.approxy.proxy

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

class AppShutdownHook(
    private val processWrapper: ProcessWrapper,
    private val gracePeriod: Long,
) : ApplicationRunner {

    private val logger = Logger.getLogger(AppShutdownHook::class.java.name)

    override fun run(args: ApplicationArguments?) {
        Runtime.getRuntime().addShutdownHook(Thread(this::runHook).also { it.name = "app-shutdown" })
        logger.info("registered shutdown hook " + (if (gracePeriod < 0) "without time limit" else "with time limit $gracePeriod seconds"))
    }

    private fun runHook() {
        if (!processWrapper.isAlive) return
        logger.info("shutdown hook triggered")

        when {
            gracePeriod == 0L -> {
                logger.info("no grace period. killing app.")
                processWrapper.destroyForcibly()
            }

            gracePeriod < 0 -> {
                logger.info("waiting for the app to terminate. (no time limit)")
                processWrapper.waitFor()
            }

            else -> {
                logger.info("waiting for the app to terminate. (time limit: $gracePeriod seconds)")
                processWrapper.waitFor(gracePeriod, TimeUnit.SECONDS)
                if (processWrapper.isAlive) {
                    logger.info("shutdown time limit exceeded. killing app.")
                    processWrapper.destroyForcibly()
                }
            }
        }

        logger.info("shutdown hook finished")
    }

}
