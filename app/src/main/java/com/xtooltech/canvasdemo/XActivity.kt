package com.xtooltech.canvasdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class XActivity : AppCompatActivity() {

   private lateinit var xview:Xsv

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        xview=Xsv(this)
        setContentView(xview)
    }

}
