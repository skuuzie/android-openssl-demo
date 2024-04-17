package com.skuuzie.openssldemo.corelib

import android.util.Log
import com.skuuzie.openssldemo.corelib.CryptoUtil.toHex

class OpensslHash : ICryptoHash {
    override fun hashString(algorithm: Crypto.HashAlgorithm, input: String): String? {
        return hash(algorithm, input.toByteArray())
    }

    fun hash(hashAlgorithm: Crypto.HashAlgorithm,
             input: ByteArray): String? {

        val coreLib = CoreLibrary.getInstance()
        val output: ByteArray
        val outputSize: Int

        when (hashAlgorithm) {
            Crypto.HashAlgorithm.MD5 -> {
                outputSize = 16
                output = ByteArray(outputSize)
                val res = coreLib.hash(Crypto.HashAlgorithm.MD5.ordinal, input, output)

                if (res != -1) {
                    return output.toHex()
                }
            }
            Crypto.HashAlgorithm.SHA1 -> {
                outputSize = 20
                output = ByteArray(outputSize)
                coreLib.hash(1, input, output)

                val res = coreLib.hash(Crypto.HashAlgorithm.SHA1.ordinal, input, output)

                if (res != -1) {
                    return output.toHex()
                }
            }
            Crypto.HashAlgorithm.SHA256 -> {
                outputSize = 32
                output = ByteArray(outputSize)
                coreLib.hash(2, input, output)

                val res = coreLib.hash(Crypto.HashAlgorithm.SHA256.ordinal, input, output)

                if (res != -1) {
                    return output.toHex()
                }
            }
            else -> {
                Log.e("CryptoHash::hash", "Algorithm not supported.")
                return null
            }
        }

        Log.e("CryptoHash::hash", "Something went wrong.")
        return null
    }
}