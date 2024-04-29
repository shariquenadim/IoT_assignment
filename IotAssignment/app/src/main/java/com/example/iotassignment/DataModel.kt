package com.example.iotassignment

import com.google.gson.annotations.SerializedName

data class Channel(
    val id: Int,
    val name: String,
    val description: String,
    val latitude: String,
    val longitude: String,
    @SerializedName("field1")
    val moistureLevel: String, // Assuming field1 represents moisture level
    @SerializedName("field2")
    val temperature: String, // Assuming field2 represents temperature
    @SerializedName("field3")
    val warnings: String?, // Assuming field3 represents warnings
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("last_entry_id")
    val lastEntryId: Int
)

data class Feed(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("entry_id")
    val entryId: Int,
    @SerializedName("field1")
    val field1: String,
    @SerializedName("field2")
    val field2: String,
    @SerializedName("field3")
    val field3: String?
)

data class ApiResponse(
    val channel: Channel,
    val feeds: List<Feed>
)

