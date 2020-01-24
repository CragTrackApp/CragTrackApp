package com.blockbasti.cragtrack.db.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(
    entity = Location::class,
    childColumns = ["placeId"],
    parentColumns = ["locationId"],
    onDelete = ForeignKey.NO_ACTION,
    onUpdate = ForeignKey.CASCADE)])
data class Session(
    @PrimaryKey(autoGenerate = true) val sId : Int,
    val locationId: String
)