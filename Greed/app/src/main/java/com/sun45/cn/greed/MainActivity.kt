package com.sun45.cn.greed

import android.app.ActivityManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


/**
 * 首页
 */
class MainActivity : AppCompatActivity() {
    private val mTriangleArray = floatArrayOf(0f, 1f, 0f, -1f, -1f, 0f, 1f, -1f, 0f)
    private lateinit var text: TextView
    private lateinit var surface: GLSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        text = findViewById(R.id.text)
        surface = findViewById(R.id.surface)

        var activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        var configurationInfo = activityManager.getDeviceConfigurationInfo()
        val supportsEs2 = configurationInfo.reqGlEsVersion >= 0x2000
        text.setText(supportsEs2.toString())
        surface.setRenderer(GLRenderer())
    }

    inner class GLRenderer : GLSurfaceView.Renderer {
        private lateinit var mTriangleBuffer: FloatBuffer

        fun GLRenderer() {
            //先初始化buffer，数组的长度*4，因为一个float占4个字节
            val bb = ByteBuffer.allocateDirect(mTriangleArray.size * 4)
            //以本机字节顺序来修改此缓冲区的字节顺序
            bb.order(ByteOrder.nativeOrder())
            mTriangleBuffer = bb.asFloatBuffer()
            //将给定float[]数据从当前位置开始，依次写入此缓冲区
            mTriangleBuffer.put(mTriangleArray)
            //设置此缓冲区的位置。如果标记已定义并且大于新的位置，则要丢弃该标记。
            mTriangleBuffer.position(0)
        }

        override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
            gl.glClearColor(1f, 0f, 0f, 0f)
        }

        override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
            gl.glViewport(0, 0, width, height)
        }

        override fun onDrawFrame(gl: GL10) {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT)
        }
    }

    override fun onPause() {
        super.onPause()
        if (surface != null) {
            surface.onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (surface != null) {
            surface.onResume()
        }
    }
}
