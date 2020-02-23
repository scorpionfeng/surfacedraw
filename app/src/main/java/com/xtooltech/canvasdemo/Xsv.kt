package com.xtooltech.canvasdemo

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Toast

class Xsv constructor (context:Context) : SurfaceView(context), SurfaceHolder.Callback,Runnable,View.OnClickListener {

    init{
        Toast.makeText(context,"init",Toast.LENGTH_SHORT).show()
        holder.addCallback(this)
        setOnClickListener(this)
    }
    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        Thread(this).start()
    }


    private val arcRecf= RectF()
    private lateinit var  canvas: Canvas

    private val arcPaint=Paint().also {
        it.color=Color.GREEN
        it.isAntiAlias=true
        it.isDither=true
        it.style=Paint.Style.STROKE
        it.strokeWidth=40f
    }
    private var paintRed:Paint=Paint().also {
        it.color=Color.RED
        it.strokeWidth=20f
        it.textSize=70f
        it.style=Paint.Style.STROKE
    }

    private var sweepAngle=130f

    override fun run() {
        /** 圆弧 */

        while (true){
            drawing(holder.lockCanvas())
             Thread.sleep(100)
        }


    }

    private fun drawing(canvas:Canvas) {

        canvas.drawRGB(36, 68, 125);

        arcRecf.left=300f
        arcRecf.top=200f
        arcRecf.right=900f
        arcRecf.bottom=800f

        val mSweepGradient: SweepGradient = SweepGradient(
            (canvas.width / 2).toFloat(),
            (canvas.height / 2).toFloat(),  //以圆弧中心作为扫描渲染的中心以便实现需要的效果
            Color.YELLOW,
            Color.BLUE
        )
        arcPaint.shader=mSweepGradient

        canvas.drawArc(arcRecf,180f,sweepAngle,false,arcPaint)
        canvas.drawRect(arcRecf, paintRed)
        canvas.drawPoint(600f,500f,paintRed)

        holder.unlockCanvasAndPost(canvas)
    }

    override fun onClick(v: View?) {
        Toast.makeText(context,"anmi start",Toast.LENGTH_SHORT).show()
        val anim = ValueAnimator.ofFloat(30f, 180f,45f)


        anim.duration = 1000
        anim.addUpdateListener {
            sweepAngle= it?.animatedValue as Float
        }

        anim.start()

    }


}