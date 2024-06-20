package com.example.tugasakhir.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: UserModel){
        dataStore.edit { preference ->
            preference[USERNAME_KEY] = user.username
            preference[IS_LOGIN_KEY] = true
            preference[ID_KEY] = user.id
            preference[BENGKEL_KEY] = user.user_bengkel
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[USERNAME_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false,
                preferences[ID_KEY] ?: 0,
                preferences[BENGKEL_KEY] ?: ""
            )
        }
    }

    suspend fun logout(){
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val USERNAME_KEY = stringPreferencesKey("username")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
        private val ID_KEY = intPreferencesKey("id")
        private val BENGKEL_KEY = stringPreferencesKey("user_bengkel")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}