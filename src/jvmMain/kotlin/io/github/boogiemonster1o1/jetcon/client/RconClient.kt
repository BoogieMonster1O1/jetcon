package io.github.boogiemonster1o1.jetcon.client

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.Closeable
import java.net.InetSocketAddress
import java.nio.channels.SocketChannel

class RconClient (private val url: String, private val port: Int, private val password: String): Closeable {
    private val channel: SocketChannel = SocketChannel.open(InetSocketAddress.createUnresolved(url, port))

    override fun close() {
        channel.close()
    }

    suspend fun sendPacket(packet: Packet) {
        withContext(Dispatchers.IO) {
            channel.write(packet.getBytes())
        }
    }
}
