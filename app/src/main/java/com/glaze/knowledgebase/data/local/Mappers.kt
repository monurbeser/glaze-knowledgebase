package com.glaze.knowledgebase.data.local
import com.glaze.knowledgebase.data.local.entity.*
import com.glaze.knowledgebase.domain.model.*

fun MaterialEntity.toDomain() = Material(id, name, alternativeNames,
    MaterialCategory.entries.find { it.name == category } ?: MaterialCategory.FLUX,
    description, characteristics, commonUses,
    SafetyLevel.entries.find { it.name == safetyLevel } ?: SafetyLevel.CAUTION,
    safetyNotes, temperatureRange, relatedMaterialIds, relatedColorantIds, relatedGlazeTypeIds,
    Attribution(wikipediaTitle, wikipediaUrl, additionalSources))

fun ColorantEntity.toDomain() = Colorant(id, name, chemicalName,
    ColorFamily.entries.find { it.name == colorFamily } ?: ColorFamily.BLUE,
    description, colorCharacteristics, AtmosphereEffects(oxidationEffect, reductionEffect),
    temperatureSensitivity, SafetyLevel.entries.find { it.name == safetyLevel } ?: SafetyLevel.CAUTION,
    safetyNotes, historicalBackground, relatedColorantIds, relatedMaterialIds, relatedSurfaceEffectIds,
    Attribution(wikipediaTitle, wikipediaUrl, additionalSources))

fun GlazeTypeEntity.toDomain() = GlazeType(id, name,
    GlazeCategory.entries.find { it.name == category } ?: GlazeCategory.GLOSSY,
    description, visualCharacteristics, surfaceQuality, lightInteraction,
    typicalTemperatureRange, historicalContext, commonApplications, relatedGlazeTypeIds,
    relatedSurfaceEffectIds, relatedFiringTypeIds, Attribution(wikipediaTitle, wikipediaUrl, additionalSources))

fun FiringTypeEntity.toDomain() = FiringType(id, name,
    FiringCategory.entries.find { it.name == firingCategory } ?: FiringCategory.MID_FIRE,
    description, temperatureRangeCelsius, temperatureRangeFahrenheit, coneRange,
    FiringAtmosphere.entries.find { it.name == atmosphere } ?: FiringAtmosphere.OXIDATION,
    characteristics, historicalBackground, commonCeramicTypes, relatedGlazeTypeIds, relatedFiringTypeIds,
    Attribution(wikipediaTitle, wikipediaUrl, additionalSources))

fun SurfaceEffectEntity.toDomain() = SurfaceEffect(id, name,
    EffectType.entries.find { it.name == effectType } ?: EffectType.DECORATIVE,
    description, visualAppearance, causes, associatedFactors, historicalContext, notableExamples,
    relatedEffectIds, relatedGlazeTypeIds, relatedColorantIds, Attribution(wikipediaTitle, wikipediaUrl, additionalSources))

fun SafetyInfoEntity.toDomain() = SafetyInfo(id, title,
    SafetyCategory.entries.find { it.name == category } ?: SafetyCategory.MATERIAL_SAFETY,
    description, hazardTypes.mapNotNull { h -> HazardType.entries.find { it.name == h } },
    protectiveMeasures, symptoms, firstAidInfo, storageGuidelines, disposalGuidelines, regulatoryInfo,
    relatedMaterialIds, relatedColorantIds, Attribution(wikipediaTitle, wikipediaUrl, additionalSources))

fun GlossaryTermEntity.toDomain() = GlossaryTerm(id, term, alternativeTerms, definition, extendedDescription,
    GlossaryCategory.entries.find { it.name == category } ?: GlossaryCategory.GENERAL,
    relatedTermIds, relatedMaterialIds, relatedColorantIds, relatedGlazeTypeIds,
    Attribution(wikipediaTitle, wikipediaUrl, additionalSources))
