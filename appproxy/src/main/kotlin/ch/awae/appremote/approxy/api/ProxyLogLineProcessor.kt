package ch.awae.appremote.approxy.api

interface ProxyLogLineProcessor {

    fun handle(line: String)

}