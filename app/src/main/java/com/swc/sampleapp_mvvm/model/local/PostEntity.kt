package com.swc.sampleapp_mvvm.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.swc.sampleapp_mvvm.model.remote.PostResponse


@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

fun PostResponse.toEntity(): PostEntity {
    return PostEntity(
        id = this.id,
        userId = this.useId,
        title = this.title,
        body = this.body
    )
}

fun List<PostResponse>.toEntityList(): List<PostEntity> {
    return this.map { it.toEntity() }
}