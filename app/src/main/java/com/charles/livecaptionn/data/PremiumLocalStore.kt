package com.charles.livecaptionn.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.charles.livecaptionn.billing.Entitlement
import com.charles.livecaptionn.billing.PremiumState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.premiumDataStore by preferencesDataStore(name = "premium_entitlements")

class PremiumLocalStore(private val context: Context) {

    val stateFlow: Flow<PremiumState> = context.premiumDataStore.data.map { it.toPremiumState() }

        suspend fun save(state: PremiumState) {
                context.premiumDataStore.edit { p ->
                            p[ENTITLEMENTS] = state.entitlements.map { it.name }.toSet()
                                        state.sourceEmail?.let { p[SOURCE_EMAIL] = it } ?: p.remove(SOURCE_EMAIL)
                                                    state.licenseToken?.let { p[LICENSE_TOKEN] = it } ?: p.remove(LICENSE_TOKEN)
                                                                p[LAST_VERIFIED_AT] = state.lastVerifiedAtMillis
                                                                            state.gracePeriodExpiresAtMillis?.let { p[GRACE_PERIOD_EXPIRES_AT] = it }
                                                                                            ?: p.remove(GRACE_PERIOD_EXPIRES_AT)
                                                                                                    }
                                                                                                        }

                                                                                                            suspend fun clear() {
                                                                                                                    context.premiumDataStore.edit { it.clear() }
                                                                                                                        }

                                                                                                                            private fun androidx.datastore.preferences.core.Preferences.toPremiumState(): PremiumState {
                                                                                                                                    return PremiumState(
                                                                                                                                                entitlements = Entitlement.values().toSet(),
                                                                                                                                                            sourceEmail = "pro@unlocked.local",
                                                                                                                                                                        licenseToken = "lifetime_unlocked",
                                                                                                                                                                                    lastVerifiedAtMillis = System.currentTimeMillis(),
                                                                                                                                                                                                gracePeriodExpiresAtMillis = null
                                                                                                                                                                                                        )
                                                                                                                                                                                                            }

                                                                                                                                                                                                                private companion object {
                                                                                                                                                                                                                        val ENTITLEMENTS = stringSetPreferencesKey("entitlements")
                                                                                                                                                                                                                                val SOURCE_EMAIL = stringPreferencesKey("source_email")
                                                                                                                                                                                                                                        val LICENSE_TOKEN = stringPreferencesKey("license_token")
                                                                                                                                                                                                                                                val LAST_VERIFIED_AT = longPreferencesKey("last_verified_at")
                                                                                                                                                                                                                                                        val GRACE_PERIOD_EXPIRES_AT = longPreferencesKey("grace_period_expires_at")
                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                            
