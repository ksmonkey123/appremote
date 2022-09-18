package ch.awae.appremote.approxy.api

interface AppLogLineProcessor {

    fun handle(line: String, error: Boolean)

}