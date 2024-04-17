ROOT_DIR=`pwd`

export ANDROID_NDK_ROOT=/home/usr/some_path/android-ndk-r26d-linux/android-ndk-r26d
export OPENSSL_FOLDER=/home/usr/some_path/openssl-3.3.0 # download from https://www.openssl.org/source/

PATH=$ANDROID_NDK_ROOT/toolchains/llvm/prebuilt/linux-x86_64/bin:$PATH
CC=clang

build() {
	cd $ROOT_DIR

	ARCH=$1
	ANDROID_API=$2
	OUTPUT_DIR="openssl_android/libs/${ARCH}"

	mkdir -p $OUTPUT_DIR

	export CXXFLAGS="-fPIC -DANDROID"
	export CPPFLAGS="-fPIC -DANDROID"
	export CFLAGS="-fPIC -DANDROID"

	cd $OPENSSL_FOLDER

	make clean
	./Configure $ARCH -D__ANDROID_API__=$ANDROID_API -static no-asm no-shared no-tests
	make

	mv libcrypto.a $ROOT_DIR/$OUTPUT_DIR
	mv libssl.a $ROOT_DIR/$OUTPUT_DIR
}

build "android-x86" "24"
build "android-x86_64" "24"
build "android-arm" "24"
build "android-arm64" "24"