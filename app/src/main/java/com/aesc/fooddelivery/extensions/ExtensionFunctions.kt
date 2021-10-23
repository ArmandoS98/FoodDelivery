package com.aesc.fooddelivery.extensions

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.aesc.fooddelivery.R
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import java.text.DecimalFormat

fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, duration).show()

fun Activity.toast(resourceId: Int, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, resourceId, duration).show()

fun ViewGroup.inflate(layoutId: Int) = LayoutInflater.from(context).inflate(layoutId, this, false)!!

fun TextView.amountConverter(precio: Long) {
    val df = DecimalFormat("#.00")
    val precioEnQuetzales = (precio / 81.23)
    this.text = "Q${df.format(precioEnQuetzales)}"
}

fun MaterialCardView.backgrounCustom(nombre: String) {
    when (nombre) {
        "Desayunos" -> {
            this.setCardBackgroundColor(resources.getColor(R.color.purple_500))
        }
        "Almuerzos" -> {
            this.setCardBackgroundColor(resources.getColor(R.color.colorAccent))
        }
        "Cena" -> {
            this.setCardBackgroundColor(resources.getColor(R.color.success_green))
        }
        "Bebidas" -> {
            this.setCardBackgroundColor(resources.getColor(R.color.error_red))
        }
    }
}

fun ImageView.loadByURL(url: String) = Glide.with(this)
    .load(url)
    .placeholder(R.drawable.side_nav_bar)
    .error(R.drawable.side_nav_bar)
    .fallback(R.drawable.side_nav_bar)
    .into(this)

fun ImageView.loadByResource(resource: Int) = Glide.with(this).load(resource).into(this)

inline fun <reified T : Activity> Activity.goToActivityF(noinline init: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
    finish()
}

inline fun <reified T : Activity> Activity.goToActivity(noinline init: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
}
