package com.syesoftware.wheaterapp.utils

class NoConnectivityException : Throwable() {

    override val message: String?
        get() = "No connectivity exception"

}
