package com.skuuzie.openssldemo.corelib

class CryptoHash {
    fun hash(hasher: ICryptoHash, algorithm: Crypto.HashAlgorithm, input: String): String? {
        return hasher.hashString(algorithm, input)
    }
}