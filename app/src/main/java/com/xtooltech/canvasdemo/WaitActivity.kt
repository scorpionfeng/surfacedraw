package com.xtooltech.canvasdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_wait.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class WaitActivity : AppCompatActivity(),View.OnClickListener ,Runnable{

    private val obj=Object ()
    @Volatile
    private var working=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait)
        wait.setOnClickListener(this)
        notify.setOnClickListener(this)
        notifyone.setOnClickListener(this)
        Thread(this).start()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.notify->{
                working=true
                synchronized(obj){
                    obj.notifyAll()
                }


            }
            R.id.notifyone->{
                synchronized(obj){
                    obj.notifyAll()
                }
            }
            R.id.wait->{

                working=false



            }
        }
    }

    override fun run() {
        while(true){
            if(!working){
                synchronized(obj){
                    obj.wait()
                }
            }

            Log.i("ken","printing")
        }

    }

}
