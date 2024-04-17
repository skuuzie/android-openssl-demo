package com.skuuzie.openssldemo.corelib

interface ICryptoHash { // not necessary. just a DI demo, in case we also want to use java's own crypto
    fun hashString(algorithm: Crypto.HashAlgorithm, input: String): String?
    // hmac
    // ...
}