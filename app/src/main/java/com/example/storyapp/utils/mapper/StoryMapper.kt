package com.example.storyapp.utils.mapper

import com.example.storyapp.model.StoryModel
import com.example.storyapp.source.local.StoryEntity
import com.example.storyapp.source.remote.StoryResponse

object StoryMapper {
    fun mapResponseToDomain(input: StoryResponse): StoryModel {
        return StoryModel(
            id = input.id,
            name = input.name,
            description = input.description,
            photoUrl = input.photoUrl,
            created = input.createdAt,
            lat = input.lat,
            lon = input.lon
        )
    }
    fun mapResponsesToDomains(input: List<StoryResponse>): List<StoryModel> {
        return input.map {
            StoryModel(
                id = it.id,
                name = it.name,
                description = it.description,
                photoUrl = it.photoUrl,
                created = it.createdAt,
                lat = it.lat,
                lon = it.lon
            )
        }
    }
    fun mapResponsesToEntities(input: List<StoryResponse>): List<StoryEntity> {
        return input.map {
            StoryEntity(
                id = it.id,
                name = it.name,
                description = it.description,
                photoUrl = it.photoUrl,
                created = it.createdAt,
                lat = it.lat,
                lon = it.lon
            )
        }
    }
}