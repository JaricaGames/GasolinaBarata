package com.jarica.preciogasolina.core

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import com.google.android.play.core.ktx.launchReview
import com.google.android.play.core.ktx.requestReview
import com.google.android.play.core.review.ReviewManagerFactory

//DIALOGO NATIVO DE VALORACION DE GOOGLE PLAY. PLAY DECIDE SI LO MUESTRA DE VERDAD
//(CUOTAS, APP YA VALORADA...); CUALQUIER FALLO SE IGNORA PORQUE VALORAR ES OPCIONAL
//Y NUNCA DEBE ROMPER LA APP.
suspend fun launchInAppReview(activity: Activity) {
    try {
        val manager = ReviewManagerFactory.create(activity)
        val reviewInfo = manager.requestReview()
        manager.launchReview(activity, reviewInfo)
    } catch (_: Exception) {
    }
}

//COMPOSE ENTREGA UN ContextWrapper EN LocalContext; SE DESENVUELVE HASTA LA ACTIVITY
tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
