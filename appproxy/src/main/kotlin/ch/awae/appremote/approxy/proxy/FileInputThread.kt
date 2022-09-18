package ch.awae.appremote.approxy.proxy

import ch.awae.appremote.approxy.api.InputStreamProxy
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.util.logging.Logger

class FileInputThread(
    private val inputStreamProxy: InputStreamProxy,
    private val path: String,
) : Thread() {

    private val logger = Logger.getLogger(FileInputThread::class.java.name)

    init {
        name = "file-input"
    }

    override fun run() {
        logger.info("watching for input file $path")
        while (!interrupted()) {
            val f = File(path)
            if (f.exists() && f.canRead() && f.isFile) {
                try {
                    val lines: List<String> = Files.readAllLines(f.toPath())
                    for (line in lines) {
                        inputStreamProxy.relay(line)
                    }
                    f.delete()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                try {
                    sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                    break
                }
            }
        }
    }

}