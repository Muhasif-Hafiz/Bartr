package com.tech.cursor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform