package com.example.perpustakaan.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateSerializer : KSerializer<Date> {
    // Format hanya untuk tanggal: yyyy-MM-dd
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date) {
        // Format tanggal tanpa waktu (hanya tahun, bulan, dan hari)
        encoder.encodeString(dateFormat.format(value))
    }

    override fun deserialize(decoder: Decoder): Date {
        // Parse string menjadi objek Date dengan format yang sesuai
        return dateFormat.parse(decoder.decodeString()) ?: throw IllegalArgumentException("Invalid date format")
    }
}
