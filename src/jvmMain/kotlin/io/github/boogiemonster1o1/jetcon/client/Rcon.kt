package io.github.boogiemonster1o1.jetcon.client

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

object Rcon {
    suspend fun open(url: String, port: Int, password: String = ""): Deferred<RconClient> = coroutineScope {
        async {
            val a = RconClient(url, port, password)
            println("bruh")
            a
        }
    }
}
