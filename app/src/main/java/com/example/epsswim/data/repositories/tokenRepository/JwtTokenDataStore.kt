package com.example.epsswim.data.repositories.tokenRepository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.epsswim.data.utils.Utils
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class JwtTokenDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) : JWTManager {
    companion object {
        val ACCESS_JWT_KEY = stringPreferencesKey("access_jwt")
        val ROLE = stringPreferencesKey("role")
        val REFRESH_JWT_KEY = stringPreferencesKey("refresh_jwt")
    }
    override suspend fun saveAccessJwt(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_JWT_KEY] = token
            preferences[ROLE] = Utils.getRoleFromToken(token)

        }
    }

    override suspend fun saveRefreshJwt(token: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAccessJwt(): String? {
        return dataStore.data.map { preferences ->
            preferences[ACCESS_JWT_KEY]
        }.first()
    }
    override suspend fun getRole(): String? {
        return dataStore.data.map { preferences ->
            preferences[ROLE]
        }.first()
    }

    override suspend fun getRefreshJwt(): String? {
        TODO("Not yet implemented")
    }

    override suspend fun clearAllTokens() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_JWT_KEY)
            preferences.remove(ROLE)
        }
    }

}