package com.glaze.knowledgebase.data.local.entity
import androidx.room.*
import com.glaze.knowledgebase.data.local.Converters

@Entity(tableName = "materials")
@TypeConverters(Converters::class)
data class MaterialEntity(
    @PrimaryKey val id: String, val name: String, val alternativeNames: List<String>, val category: String,
    val description: String, val characteristics: List<String>, val commonUses: List<String>,
    val safetyLevel: String, val safetyNotes: String, val temperatureRange: String,
    val relatedMaterialIds: List<String>, val relatedColorantIds: List<String>,
    val relatedGlazeTypeIds: List<String>, val wikipediaTitle: String?, val wikipediaUrl: String?, val additionalSources: List<String>
)
