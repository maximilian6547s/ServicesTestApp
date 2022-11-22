package com.example.servicestestapp

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*


class MyJobService : JobService() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartCommand")
        coroutineScope.launch {
            for (i in 0 until 5) {
                delay(1000)
                log("Timer $i")
            }
            jobFinished(params, true)
        }
        return true //if asynchronous operations are performed = true and false if we perform synchronous operations
    }


    //calls only if system killed our service
    override fun onStopJob(params: JobParameters?): Boolean {
        log("onStopJob")
        return true //true if we want reschedule service
    }

    override fun onDestroy() {
        log("onDestroy")
        coroutineScope.cancel()
        super.onDestroy()
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyJobService: $message")
    }

    companion object{

        const val JOB_ID = 111
    }

}