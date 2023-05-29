package com.example.spycamsecurity.common

import kotlin.coroutines.CoroutineContext

/* coroutines != threads
    we can use dispatchers to tell our coroutines
    which threads to run on
 */
interface DispatcherProvider {
    fun provideUIContext(): CoroutineContext
    fun provideIOContext(): CoroutineContext
}