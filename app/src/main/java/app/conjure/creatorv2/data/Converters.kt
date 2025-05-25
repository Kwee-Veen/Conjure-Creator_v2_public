package app.conjure.creatorv2.data

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromDamageModifierList(value: List<DamageModifierModel>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toDamageModifierList(json: String): List<DamageModifierModel> {
        return Gson().fromJson(json, Array<DamageModifierModel>::class.java).toList()
    }

    @TypeConverter
    fun fromMoveList(value: List<MoveModel>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toMoveList(json: String): List<MoveModel> {
        return Gson().fromJson(json, Array<MoveModel>::class.java).toList()
    }
}