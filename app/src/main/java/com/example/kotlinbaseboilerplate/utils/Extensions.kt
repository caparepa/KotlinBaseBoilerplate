package com.example.kotlinbaseboilerplate.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.kotlinbaseboilerplate.BuildConfig
import com.google.gson.Gson

/**
 * Context Extensions
 */
fun Context.toastLong(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun Context.toastShort(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

/**
 * General extensions
 */

inline fun <T> justTry(block: () -> T) = try { block() } catch (e: Throwable) {}

inline fun debugMode(block : () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}


/**
 * Logger extension
 */
fun logger(mode: Int, tag: String, message: String, t: Throwable? = null) {

    if (t == null) {
        when (mode) {
            Log.DEBUG -> Log.d(tag, message)
            Log.ERROR -> Log.e(tag, message)
            Log.INFO -> Log.i(tag, message)
            Log.VERBOSE -> Log.v(tag, message)
            Log.WARN -> Log.w(tag, message)
            else -> Log.wtf(tag, message)
        }
    } else {
        when (mode) {
            Log.DEBUG -> Log.d(tag, message, t)
            Log.ERROR -> Log.e(tag, message, t)
            Log.INFO -> Log.i(tag, message, t)
            Log.VERBOSE -> Log.v(tag, message, t)
            Log.WARN -> Log.w(tag, message, t)
            else -> Log.wtf(tag, message, t)
        }
    }
}

/**
 * Parser extensions
 */
fun Any.toJsonString(): String? {
    justTry {
        val gson = Gson()
        return gson.toJson(this)
    }
    return null
}

fun <T> String.objectFromJson(classOfT: Class<T>): Any? {
    val gson = Gson()
    return gson.fromJson<Any>(this, classOfT as Class<*>)
}

inline fun <reified T: Any> String.toKotlinObject() : T {
    val gson = Gson()
    return gson.fromJson(this, T::class.java)
}

/**
 * View extensions
 */
fun View.isVisible(): Boolean = this.visibility == View.VISIBLE

fun View.makeVisible() { this.visibility = View.VISIBLE }

fun View.makeGone() { this.visibility = View.GONE }

fun View.makeInvisible() { this.visibility = View.INVISIBLE }