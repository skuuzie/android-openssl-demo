package com.skuuzie.openssldemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.skuuzie.openssldemo.corelib.Crypto
import com.skuuzie.openssldemo.corelib.CryptoHash
import com.skuuzie.openssldemo.corelib.OpensslHash
import com.skuuzie.openssldemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Update inset for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(binding.topAppBarHome) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = insets.top
            }
            WindowInsetsCompat.CONSUMED
        }

        binding.edtInput.addTextChangedListener(textWatcher)
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val md5 = CryptoHash().hash(OpensslHash(), Crypto.HashAlgorithm.MD5, s.toString())
            val sha1 = CryptoHash().hash(OpensslHash(), Crypto.HashAlgorithm.SHA1, s.toString())
            val sha256 = CryptoHash().hash(OpensslHash(), Crypto.HashAlgorithm.SHA256, s.toString())

            with (binding) {
                resultMd5.setText(md5 ?: "Some error happened in c++ side.")
                resultSha1.setText(sha1 ?: "Some error happened in c++ side.")
                resultSha256.setText(sha256 ?: "Some error happened in c++ side.")
            }
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }
}