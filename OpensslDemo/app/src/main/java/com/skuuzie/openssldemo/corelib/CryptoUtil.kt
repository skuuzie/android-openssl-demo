package com.skuuzie.openssldemo.corelib

object CryptoUtil {
    fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }
}