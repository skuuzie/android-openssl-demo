# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

project("core")

# OPENSSL
include_directories(openssl_include)

add_library(crypto STATIC IMPORTED)
set_target_properties(crypto
        PROPERTIES
        IMPORTED_LOCATION
        ${PROJECT_SOURCE_DIR}/prebuilt/${ANDROID_ABI}/libcrypto.a)

add_library(ssl STATIC IMPORTED)
set_target_properties(ssl
        PROPERTIES
        IMPORTED_LOCATION
        ${PROJECT_SOURCE_DIR}/prebuilt/${ANDROID_ABI}/libssl.a)
# OPENSSL

add_library(${CMAKE_PROJECT_NAME} SHARED
        # List C/C++ source files with relative paths to this CMakeLists.txt.
        openssldemo.cpp)

target_link_libraries(${CMAKE_PROJECT_NAME}
        # List libraries link to the target library
        android
        crypto ssl
        log)
