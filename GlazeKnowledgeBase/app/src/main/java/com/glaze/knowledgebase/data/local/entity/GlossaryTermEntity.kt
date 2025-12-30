package com.glaze.knowledgebase.data.local.entity
import androidx.room.*
import com.glaze.knowledgebase.data.local.Converters

@Entity(tableName = "glossary_terms")
@TypeConverters(Converters::class)
data class GlossaryTermEntity(
    @PrimaryKey val id: String, val term: String, val alternativeTerms: List<String>, val definition: String,
    val extendedDescription: String, val category: String, val relatedTermIds: List<String>,
    val relatedMaterialIds: List<String>, val relatedColorantIds: List<String>, val relatedGlazeTypeIds: List<String>,
    val wikipediaTitle: String?, val wikipediaUrl: String?, val additionalSources: List<String>
)
