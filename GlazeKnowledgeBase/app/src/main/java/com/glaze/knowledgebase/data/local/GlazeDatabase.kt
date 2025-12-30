package com.glaze.knowledgebase.data.local
import androidx.room.*
import com.glaze.knowledgebase.data.local.dao.*
import com.glaze.knowledgebase.data.local.entity.*

@Database(entities = [MaterialEntity::class, ColorantEntity::class, GlazeTypeEntity::class, FiringTypeEntity::class, SurfaceEffectEntity::class, SafetyInfoEntity::class, GlossaryTermEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GlazeDatabase : RoomDatabase() {
    abstract fun materialDao(): MaterialDao
    abstract fun colorantDao(): ColorantDao
    abstract fun glazeTypeDao(): GlazeTypeDao
    abstract fun firingTypeDao(): FiringTypeDao
    abstract fun surfaceEffectDao(): SurfaceEffectDao
    abstract fun safetyInfoDao(): SafetyInfoDao
    abstract fun glossaryTermDao(): GlossaryTermDao
}
