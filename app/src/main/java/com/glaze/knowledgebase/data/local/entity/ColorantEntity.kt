package com.glaze.knowledgebase.data.local.entity
import androidx.room.*
import com.glaze.knowledgebase.data.local.Converters

@Entity(tableName = "colorants")
@TypeConverters(Converters::class)
data class ColorantEntity(
    @PrimaryKey val id: String, val name: String, val chemicalName: String, val colorFamily: String,
    val description: String, val colorCharacteristics: List<String>, val oxidationEffect: String,
    val reductionEffect: String, val temperatureSensitivity: String, val safetyLevel: String,
    val safetyNotes: String, val historicalBackground: String, val relatedColorantIds: List<String>,
    val relatedMaterialIds: List<String>, val relatedSurfaceEffectIds: List<String>,
    val wikipediaTitle: String?, val wikipediaUrl: String?, val additionalSources: List<String>
)
