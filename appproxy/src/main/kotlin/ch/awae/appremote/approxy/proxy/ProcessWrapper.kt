package ch.awae.appremote.approxy.proxy

import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.TimeUnit

class ProcessWrapper(private val process: Process) {
    val outputStream: OutputStream
        get() = process.outputStream
    val inputStream: InputStream
        get() = process.inputStream
    val errorStream: InputStream
        get() = process.errorStream

    fun waitFor(): Int {
        return process.waitFor()
    }

    fun waitFor(timeout: Long, unit: TimeUnit): Boolean {
        process.destroy()
        return process.waitFor(timeout, unit)
    }

    fun destroyForcibly(): Process {
        return process.destroyForcibly()
    }

    val isAlive: Boolean
        get() = process.isAlive
}