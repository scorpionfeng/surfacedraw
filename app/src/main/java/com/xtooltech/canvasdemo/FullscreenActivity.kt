package com.xtooltech.canvasdemo

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */


class FullscreenActivity : AppCompatActivity(), SurfaceHolder.Callback,Runnable,View.OnClickListener,View.OnLongClickListener, CoroutineScope by MainScope(){
    private val data_channel_two: FloatArray = arrayOf(50f,150f,250f,350f,450f,550f,650f,750f,850f,950f).toFloatArray()

    @Volatile
    private var multiple =1f


    private val offset_channel_two = 120

    private val distance = 180

    private var sweepAngle=130f

    private lateinit var surfaceView:SurfaceView
    private lateinit var holder:SurfaceHolder
    private lateinit var  canvas:Canvas
    private var paintPoint:Paint = Paint()
    private var count=1
    private var paintRed:Paint=Paint().also {
        it.color=Color.RED
        it.strokeWidth=20f
        it.textSize=70f
        it.style=Paint.Style.STROKE
    }

    private var paint_ch2:Paint=Paint().also {
        it.setAntiAlias(true)
        it.setDither(true)
        it.setColor(Color.YELLOW)
        it.setStrokeWidth(5f)
        it.setStyle(Paint.Style.STROKE)
    }

    private val paint_text:Paint=Paint().also {
        it.color=Color.GREEN
        it.strokeWidth=10f
    }


    private val mPaint= Paint().also{
        it.setColor(Color.BLACK)
        it.setStrokeWidth(8f)
        it.setStyle(Paint.Style.STROKE)
        it.setTextSize(60f)
    }

    private val arcPaint=Paint().also {
        it.color=Color.GREEN
        it.isAntiAlias=true
        it.isDither=true
        it.style=Paint.Style.STROKE
        it.strokeWidth=40f
    }
    private val arcRecf=RectF()

    private val start: PointF= PointF(200f,200f)
    private  var end:PointF = PointF(400f,200f)
    private  var control:PointF= PointF(300f,100f)




    private val channel= Channel<FloatArray>()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)
         surfaceView = findViewById(R.id.view)
         holder = surfaceView.holder
        holder.addCallback(this)
        surfaceView.setOnClickListener(this)
        surfaceView.setOnLongClickListener(this)
        paintPoint.run {
            color=Color.RED
            textSize=60f
            strokeWidth=10f
            isAntiAlias=true
            style=Paint.Style.FILL_AND_STROKE

        }
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        Thread(this).start()
    }

    override  fun run() {

        val path = Path()
        canvas = holder.lockCanvas()


        /** 画背景色 */
        canvas.drawRGB(36, 68, 125);


        /** 画点 */
        path.quadTo(130f,130f,260f,260f)
        path.quadTo(260f,260f,390f,390f)
        path.quadTo(390f,390f,500f,500f)
        path.setLastPoint(600f,600f)
        canvas.drawPath(path, paintRed)
        canvas.drawLine(130f,130f,260f,260f,paintRed)

        /** 画中间的字 */
        canvas.drawText("I'm here",260f,260f,paintRed)



/** 贝塞尔曲线 */
        // 绘制数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20f)
        canvas.drawPoint(start.x,start.y,mPaint);
        canvas.drawPoint(end.x,end.y,mPaint);
        canvas.drawPoint(control.x,control.y,mPaint);
        // 绘制辅助线
        mPaint.setStrokeWidth(4f)
        canvas.drawLine(start.x,start.y,control.x,control.y,mPaint);
        canvas.drawLine(end.x,end.y,control.x,control.y,mPaint);

        // 绘制贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8f);

       val pathQual = Path()

        pathQual.moveTo(start.x,start.y);
        pathQual.quadTo(control.x,control.y,end.x,end.y);

        canvas.drawPath(pathQual, mPaint)


        /** 圆弧 */

        arcRecf.left=300f
        arcRecf.top=200f
        arcRecf.right=900f
        arcRecf.bottom=800f

        var mSweepGradient: SweepGradient = SweepGradient(
            (canvas.width / 2).toFloat(),
            (canvas.height / 2).toFloat(),  //以圆弧中心作为扫描渲染的中心以便实现需要的效果
            Color.YELLOW,
            Color.BLUE
        )
        arcPaint.shader=mSweepGradient

        canvas.drawArc(arcRecf,180f,sweepAngle,false,arcPaint)
        canvas.drawRect(arcRecf, paintRed)
        canvas.drawPoint(600f,500f,paintRed)


        launch {
            while (true) {
                var receive = channel.receive()
                canvas = holder.lockCanvas()
//                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                canvas.drawColor(Color.WHITE)
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.SRC)
                canvas.drawColor(Color.BLUE)
                var conPath=Path()
                conPath.moveTo(0f, receive.get(0))
//                for (i in 1 until receive.size) {
//                    conPath.quadTo(
//                        (i - 1) * distance.toFloat()+account*20,
//                        data_channel_two.get(i - 1) * multiple + offset_channel_two,
//                        i * distance.toFloat()+account*20,
//                        data_channel_two.get(i) * multiple + offset_channel_two
//                    )
//                }

                for (i in receive.indices){
                    when (i) {
                        0 -> {
                            conPath.moveTo(0f,receive[0]+offset_channel_two)
                            canvas.drawPoint(0f,receive[0]+offset_channel_two,paintPoint)
//                            canvas.drawLine(0f,0f,0f,receive[0]+offset_channel_two,paint_ch2)
                        }
                        else -> {
                            conPath.quadTo(((i-1)*distance+0.5*distance).toFloat(),receive[i-1]+2*distance,i*distance.toFloat(),receive[i]+offset_channel_two)
                            canvas.drawPoint(i*distance.toFloat(),receive[i]+offset_channel_two,paintPoint)
                            canvas.drawLine((i-1)*distance.toFloat(),receive[i-1]+offset_channel_two,i*distance.toFloat(),receive[i]+offset_channel_two,paintPoint)
                        }
                    }
                }

                canvas.drawPath(conPath, paint_ch2)
                holder.unlockCanvasAndPost(canvas)
            }

        }


        holder.unlockCanvasAndPost(canvas)

    }


    override fun onClick(v: View?) {
        /** 更新点 */
        Toast.makeText(this,"click me",Toast.LENGTH_SHORT).show()
        count++
        channel.offer(arrayOf(50f,30f,70f,20f,100f,130f,10f).toFloatArray())

    }

    override fun onLongClick(v: View?): Boolean {
        startActivity(Intent(this,XActivity::class.java))
        return false
    }


}

