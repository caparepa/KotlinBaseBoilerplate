package com.example.kotlinbaseboilerplate.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * We create a base fragment with a CoroutineScope
 */
abstract class ScopedFragment: Fragment(), CoroutineScope {

    //We create a Job property
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main //This job will run on the main dispatcher (Main thread)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job() //when the fragment is instantiate it, crete a new job
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel() //when the fargment is destroyed, cancel the job
    }
}