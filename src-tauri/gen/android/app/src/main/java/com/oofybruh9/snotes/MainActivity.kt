package com.oofybruh9.snotes

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : TauriActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)

    // ---- Detect navigation mode ----
    val resourceId = resources.getIdentifier(
      "config_navBarInteractionMode",
      "integer",
      "android"
    )

    val navMode = if (resourceId > 0) {
      resources.getInteger(resourceId)
    } else {
      0
    }

    // 0 = 3-button
    // 1 = 2-button (legacy)
    // 2 = gesture
    val isGestureNav = navMode == 2

    // ---- Handle Insets ----
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

      view.setPadding(
        systemBars.left,
        systemBars.top,
        systemBars.right,
        if (isGestureNav) 0 else systemBars.bottom
      )

      insets
    }

    // ---- Initial System Bar Style ----
    window.statusBarColor = Color.TRANSPARENT

    if (isGestureNav) {
      window.navigationBarColor = Color.TRANSPARENT
    } else {
      window.navigationBarColor = Color.BLACK
    }

    val controller = WindowCompat.getInsetsController(window, window.decorView)
    controller.isAppearanceLightStatusBars = false
    controller.isAppearanceLightNavigationBars = false
  }

  // ---- Dynamic Sync Function (callable from JS later) ----
  fun updateSystemBars(colorHex: String, lightIcons: Boolean) {
    val parsedColor = Color.parseColor(colorHex)

    window.statusBarColor = parsedColor
    window.navigationBarColor = parsedColor

    val controller = WindowCompat.getInsetsController(window, window.decorView)
    controller.isAppearanceLightStatusBars = lightIcons
    controller.isAppearanceLightNavigationBars = lightIcons
  }
}
