package com.sun45.cn.greed

import android.app.ActivityManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceView
import android.widget.TextView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


/**
 * 首页
 */
class MainActivity : AppCompatActivity() {
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

        //        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
//        }
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
