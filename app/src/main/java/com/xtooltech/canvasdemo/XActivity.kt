package com.xtooltech.canvasdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class XActivity : AppCompatActivity(), View.OnLongClickListener {

   private lateinit var xview:Xsv

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        xview=Xsv(this)
        xview.setOnLongClickListener(this)
        setContentView(xview)
    }

    override fun onLongClick(v: View?): Boolean {
        startActivity(Intent(this,WaitActivity::class.java))
        return false
    }

}
