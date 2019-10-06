package com.example.kotlinbaseboilerplate.internal

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

/**
 * Function to receive a Deferred from a Task
 * Takes in a T parameter with a result T, and will return a Deferred<T>
 */
fun <T> Task<T>.asDeferred(): Deferred<T> {
    //Is up to us if the deferred is completed (hence the "completable")
    val deferred = CompletableDeferred<T>()

    //We add a listener to get the deferred completed
    this.addOnSuccessListener { result ->
        deferred.complete(result)
    }

    //If the deferred fails to complete and an exception is thrown,
    //we'll ask to complete it exceptionally and pass the exception
    //"async() and await() propagate exception to their callers" so the exception can propagate
    //This way we can wrap the caller in a try-catch block
    this.addOnFailureListener { exception ->
        deferred.completeExceptionally(exception)
    }

    return deferred
}