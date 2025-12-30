package com.glaze.knowledgebase.data.local.entity
import androidx.room.*
import com.glaze.knowledgebase.data.local.Converters

@Entity(tableName = "safety_info")
@TypeConverters(Converters::class)
data class SafetyInfoEntity(
    @PrimaryKey val id: String, val title: String, val category: String, val description: String,
    val hazardTypes: List<String>, val protectiveMeasures: List<String>, val symptoms: List<String>,
    val firstAidInfo: String, val storageGuidelines: String, val disposalGuidelines: String,
    val regulatoryInfo: String, val relatedMaterialIds: List<String>, val relatedColorantIds: List<String>,
    val wikipediaTitle: String?, val wikipediaUrl: String?, val additionalSources: List<String>
)
