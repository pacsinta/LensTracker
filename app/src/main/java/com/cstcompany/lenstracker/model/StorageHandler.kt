package com.cstcompany.lenstracker.model

import android.app.Activity
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
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
    suspend fun resetCounter(){
        if(user == null){
            context.dataStore.edit { prefs ->
                prefs[longPreferencesKey("changedDay")] = LocalDate.now().toEpochDay()
            }
        }
    }

    fun getDayCount(): Flow<Int> {
        if(user == null){
            return context.dataStore.data.map { prefs ->
                val changedDay = prefs[longPreferencesKey("changedDay")] ?: LocalDate.now().toEpochDay()
                val days = LocalDate.now().toEpochDay() - changedDay
                return@map days.toInt()
            }
        }

        return flow { emit(0) }
    }
}