package io.github.boogiemonster1o1.jetcon.client

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.Closeable
import java.net.InetSocketAddress
import java.nio.channels.SocketChannel
import java.util.concurrent.atomic.AtomicInteger

class RconClient (private val url: String, private val port: Int, private val password: String): Closeable {
    private val channel: SocketChannel = SocketChannel.open(InetSocketAddress.createUnresolved(url, port))
    private val requestId: AtomicInteger = AtomicInteger(0)

    override fun close() {
        channel.close()
    }

    suspend fun sendPacket(packet: Packet, responseConsumer: (String) -> Unit) {
        withContext(Dispatchers.IO) {
            val real: Packet = packet.withRequestId(requestId.getAndIncrement())
            channel.write(packet.getBytes())
        }
    }
}
