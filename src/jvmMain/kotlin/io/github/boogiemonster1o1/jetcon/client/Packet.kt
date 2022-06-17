package io.github.boogiemonster1o1.jetcon.client

import java.nio.ByteBuffer
import java.nio.ByteOrder

class Packet(private val requestId: Int, private val type: Int, private val body: String) {
    companion object {
        @JvmStatic
        val LOGIN_PACKET_ID = 3

        @JvmStatic
        val COMMAND_PACKET_ID = 2

        @JvmStatic
        val OUTPUT_PACKET_ID = 0

        fun read(bytes: ByteBuffer): Packet {
            bytes.order(ByteOrder.LITTLE_ENDIAN)
            val size: Int = bytes.int
            val requestId: Int = bytes.int
            val type: Int = bytes.int
            val body: String = String(bytes.array())
            return Packet(requestId, type, body)
        }
    }

    fun getBytes(): ByteBuffer {
        val size: Int = getSize()
        val bytes: ByteBuffer = ByteBuffer.allocate(size + 4)
        bytes.order(ByteOrder.LITTLE_ENDIAN)
        bytes.putInt(size)
        bytes.putInt(this.requestId)
        bytes.putInt(this.type)
        bytes.put(this.body.encodeToByteArray())
        bytes.put(byteArrayOf(0, 0))
        bytes.flip()
        return bytes
    }

    private fun getSize(): Int {
        return 4 + 4 + this.body.length
    }
}
