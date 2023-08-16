package me.darthwithap.kmm.multilingo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform