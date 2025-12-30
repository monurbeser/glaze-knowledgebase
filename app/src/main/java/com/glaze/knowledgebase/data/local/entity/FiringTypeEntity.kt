package com.glaze.knowledgebase.data.local.entity
import androidx.room.*
import com.glaze.knowledgebase.data.local.Converters

@Entity(tableName = "firing_types")
@TypeConverters(Converters::class)
data class FiringTypeEntity(
    @PrimaryKey val id: String, val name: String, val firingCategory: String, val description: String,
    val temperatureRangeCelsius: String, val temperatureRangeFahrenheit: String, val coneRange: String,
    val atmosphere: String, val characteristics: List<String>, val historicalBackground: String,
    val commonCeramicTypes: List<String>, val relatedGlazeTypeIds: List<String>,
    val relatedFiringTypeIds: List<String>, val wikipediaTitle: String?, val wikipediaUrl: String?, val additionalSources: List<String>
)
