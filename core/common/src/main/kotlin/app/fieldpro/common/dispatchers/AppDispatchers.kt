package app.fieldpro.common.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Coroutine dispatcher group injected wherever IO/CPU work is dispatched.
 * Production binds these to [Dispatchers.IO] / [Dispatchers.Default] / [Dispatchers.Main];
 * tests bind them to a `TestDispatcher` for deterministic execution.
 */
data class AppDispatchers(
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val main: CoroutineDispatcher,
) {
    companion object {
        fun production(): AppDispatchers = AppDispatchers(
            io = Dispatchers.IO,
            default = Dispatchers.Default,
            main = Dispatchers.Main,
        )
    }
}
