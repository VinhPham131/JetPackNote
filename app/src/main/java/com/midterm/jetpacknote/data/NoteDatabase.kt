package com.midterm.jetpacknote.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.midterm.jetpacknote.model.Note
import com.midterm.jetpacknote.ultils.Converters

@Database(entities = [Note::class], version = 1, exportSchema = false)

@TypeConverters(Converters :: class)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun noteDao(): NoteDatabaseDao
}