package com.alan.intermediatest.data.models

import java.util.*


//TODO: Make this much better with generics or something

data class CharactersResponse(
    val code: Int,
    val status: String,
    val data: CharactersData
)

data class CharactersData(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Character>
)

data class EventsResponse(
    val code: Int,
    val status: String,
    val data: EventsData
)

data class EventsData(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Event>
)

//
data class Character(
    val id: Int,
    val name: String,
    val description: String?,
    val modified: String?,//Date?,
    val resourceURI: String?,
    val urls: MutableList<Url>?,
    val thumbnail: Image?,
    val comics: ComicList?,
    val stories: StoryList?,
    val events: EventList?,
    val series: SeriesList?
)

data class Event(
    val id: Int?,
    val title: String?,
    val description: String,
    val resourceURI: String,
    val urls: MutableList<Url>?,
    val modified: String?,//Date?,
    val start: String?,//Date?,
    val end: String?,//Date?,
    val thumbnail: Image?,
    val comics: ComicList?,
    val stories: StoryList?,
    val series: SeriesList?,
    val characters: CharacterList?,
    val creators: CreatorList?,
    val next: EventSummary?,
    val previous: EventSummary?
)

data class Url(
    val type: String?,
    val url: String?
)

data class Image(
    val path: String?,
    val extension: String?
)

data class ComicList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<ComicSummary>
)

data class ComicSummary(
    val resourceURI: String?,
    val name: String?
)

data class StoryList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: MutableList<StorySummary>
)

data class StorySummary(
    val resourceURI: String?,
    val name: String?,
    val type: String?
)

data class EventList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<EventSummary>
)

data class EventSummary(
    val resourceURI: String?,
    val name: String?
)

data class SeriesList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: MutableList<SeriesSummary>?
)

data class SeriesSummary(
    val resourceURI: String?,
    val name: String?
)

data class CharacterList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: MutableList<CharacterSummary>?
)

data class CharacterSummary(
    val resourceURI: String?,
    val name: String?,
    val role: String?
)

data class CreatorList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: MutableList<CreatorSummary>
)

data class CreatorSummary(
    val resourceURI: String?,
    val name: String?,
    val role: String?
)