package com.skuuzie.openssldemo.corelib

import android.util.Log

class CoreLibrary {

    companion object {
        private var isLoaded = false

        @JvmStatic
        fun getInstance(): CoreLibrary {
            if (!isLoaded) {
                System.loadLibrary("core")
                isLoaded = true
                Log.d("CoreLibrary", "libcore.so loaded!")
            }
            return CoreLibrary()
        }
    }

    external fun hash(algorithm: Int,
                      input: ByteArray,
                      output: ByteArray) : Int
}