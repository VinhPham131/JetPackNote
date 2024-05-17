package com.midterm.jetpacknote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "note")
data class Note(

    @PrimaryKey
    val id: UUID = UUID.randomUUID(),

    @ColumnInfo
    val title: String,

    @ColumnInfo
    val description: String,

    @ColumnInfo
    val entryDate: Date,
)
