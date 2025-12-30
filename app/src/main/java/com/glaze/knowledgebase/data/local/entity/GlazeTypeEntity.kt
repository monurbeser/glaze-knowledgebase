package com.glaze.knowledgebase.data.local.entity
import androidx.room.*
import com.glaze.knowledgebase.data.local.Converters

@Entity(tableName = "glaze_types")
@TypeConverters(Converters::class)
data class GlazeTypeEntity(
    @PrimaryKey val id: String, val name: String, val category: String, val description: String,
    val visualCharacteristics: List<String>, val surfaceQuality: String, val lightInteraction: String,
    val typicalTemperatureRange: String, val historicalContext: String, val commonApplications: List<String>,
    val relatedGlazeTypeIds: List<String>, val relatedSurfaceEffectIds: List<String>,
    val relatedFiringTypeIds: List<String>, val wikipediaTitle: String?, val wikipediaUrl: String?, val additionalSources: List<String>
)
