package com.example.umrechnugsapp

import android.util.Log
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        val hel = "as"
        Log.v(hel,"halo test")
    }
    @Test
    fun currencyOutputTest(){
        val con : CurrencyOP = CurrencyOP()
        val out : String = con.convert(1.01,30.0,"$")
        if(out == "30.3$"){
            Log.v(out," Ist Richtig")
        }
        else{
            Log.v(out, "Ist Falsch")
        }

    }
}