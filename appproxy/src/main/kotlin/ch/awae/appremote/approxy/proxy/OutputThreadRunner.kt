package ch.awae.appremote.approxy.proxy

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import kotlin.system.exitProcess

@Component
class OutputThreadRunner(
    val processWrapper: ProcessWrapper,
    val outputThreads: List<OutputThread>,
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        outputThreads.forEach {
            it.isDaemon = true
            it.start()
        }
        Thread { exitProcess(processWrapper.waitFor()) }.start()
    }

}