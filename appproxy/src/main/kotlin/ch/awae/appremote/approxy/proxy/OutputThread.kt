package ch.awae.appremote.approxy.proxy

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.logging.Logger

class OutputThread(
    private val processor: CompositeLogLineProcessor,
    private val isError: Boolean,

    stream: InputStream,
) : Thread() {

    private val logger = Logger.getLogger(this::class.java.name)
    private val reader = BufferedReader(InputStreamReader(stream))

    init {
        name = if (isError) "stderr" else "stdout"
    }

    override fun run() {
        try {
            reader.lines().forEach {
                logger.info(it)
                processor.handle(it, isError)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}