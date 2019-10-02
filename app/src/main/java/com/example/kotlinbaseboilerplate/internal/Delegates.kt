package com.example.kotlinbaseboilerplate.internal

import kotlinx.coroutines.*

//This function allows a lazy function to be called/executed from a CoroutineScope
//It will take in a generic type, which will receive a block of code embedding a suspended
//CoroutineScope that will return T
fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    //This will return a lazy execution
    return lazy {
        //Async will be started as lazy, meaning that it will start when the lazyDeferred fun is called
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this) //"this" is the CoroutineScope
        }
    }
}