package com.cstcompany.lenstracker.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class StorageHandler(private val user: FirebaseUser?, private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("LensData")
    }
    suspend fun resetCounter(side: EyeSide){
        if(user == null){
            context.dataStore.edit { prefs ->
                prefs[longPreferencesKey(if(side == EyeSide.LEFT) "changedDayLeft" else "changedDayRight")] = LocalDate.now().toEpochDay()
            }
        }
    }

    fun getDayCount(): Flow<Array<Int>> {
        if(user == null){
            return context.dataStore.data.map { prefs ->
                val changedDayRight = prefs[longPreferencesKey("changedDayRight")] ?: -1
                val changedDayLeft = prefs[longPreferencesKey("changedDayLeft")] ?: -1
                val daysRight = if(changedDayRight != -1L) LocalDate.now().toEpochDay() - changedDayRight else -1
                val daysLeft = if(changedDayLeft != -1L) LocalDate.now().toEpochDay() - changedDayLeft else -1
                return@map arrayOf(daysLeft.toInt(), daysRight.toInt())
            }
        }

        return flow { emit(arrayOf(0, 0)) }
    }
}