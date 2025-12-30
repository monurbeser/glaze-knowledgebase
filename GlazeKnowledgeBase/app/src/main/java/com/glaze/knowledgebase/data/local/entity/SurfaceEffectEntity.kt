package com.glaze.knowledgebase.data.local.entity
import androidx.room.*
import com.glaze.knowledgebase.data.local.Converters

@Entity(tableName = "surface_effects")
@TypeConverters(Converters::class)
data class SurfaceEffectEntity(
    @PrimaryKey val id: String, val name: String, val effectType: String, val description: String,
    val visualAppearance: String, val causes: List<String>, val associatedFactors: List<String>,
    val historicalContext: String, val notableExamples: List<String>, val relatedEffectIds: List<String>,
    val relatedGlazeTypeIds: List<String>, val relatedColorantIds: List<String>,
    val wikipediaTitle: String?, val wikipediaUrl: String?, val additionalSources: List<String>
)
