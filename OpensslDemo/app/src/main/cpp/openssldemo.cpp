#include <jni.h>
#include <string>
#include "openssl_include/openssl/evp.h"

extern "C"
JNIEXPORT int JNICALL
Java_com_skuuzie_openssldemo_corelib_CoreLibrary_hash(JNIEnv *env, jobject thiz, jint algorithm,
                                                      jbyteArray input, jbyteArray output) {
    // Mimics the Crypto.HashAlgorithm enum
    const int MD5 = 0;
    const int SHA1 = 1;
    const int SHA256 = 2;

    // Input length
    long input_size = env->GetArrayLength(input);
    if (input_size < 0) return -1;

    // Copy input to a buffer
    unsigned char *_input = new unsigned char[input_size];
    env->GetByteArrayRegion(input, 0, input_size, reinterpret_cast<jbyte *>(_input));

    // Openssl ops
    EVP_MD_CTX *mdctx;
    unsigned char digest[EVP_MAX_MD_SIZE];
    unsigned int digest_len;

    if ((mdctx = EVP_MD_CTX_new()) == NULL) {
        return -1;
    }

    switch (algorithm) {
        case MD5:
            if (EVP_DigestInit_ex(mdctx, EVP_md5(), NULL) != 1) {
                return -1;
            }
            break;
        case SHA1:
            if (EVP_DigestInit_ex(mdctx, EVP_sha1(), NULL) != 1) {
                return -1;
            }
            break;
        case SHA256:
            if (EVP_DigestInit_ex(mdctx, EVP_sha256(), NULL) != 1) {
                return -1;
            }
            break;
        default:
            return -1;
    }

    if (EVP_DigestUpdate(mdctx, _input, input_size) != 1) {
        return -1;
    }

    if (EVP_DigestFinal_ex(mdctx, digest, &digest_len) != 1) {
        return -1;
    }

    // Transfer digest to output
    env->SetByteArrayRegion(output, 0, digest_len, reinterpret_cast<const jbyte *>(digest));

    // Cleanup
    EVP_MD_CTX_free(mdctx);
    delete[] _input;

    return 0;
}