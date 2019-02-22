package com.dyedfox.weatherforecast.Internal

import kotlinx.coroutines.*

fun<T> lazyDeffender(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>>
{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY){
            block.invoke(this)
        }
    }
}