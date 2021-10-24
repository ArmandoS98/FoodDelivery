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
    val precioEnQuetzales = (precio / 81.23)
    this.text = context.getString(R.string.lblamount, precioEnQuetzales.format())
}

 fun Double.format(): String {
    val df = DecimalFormat("#.00")
    return df.format(this)
}

fun TextView.amountConverter(precios: String, cantidad: String) {
    val precio: Double = precios.toDouble()
    val precioEnQuetzalesTemp = (precio / 81.23)
    val cant = cantidad.toInt()
    val precioEnQuetzales = precioEnQuetzalesTemp * cant
    this.text = context.getString(R.string.lblprecio, precioEnQuetzales.format())
}

fun amountConverter(precios: String, cantidad: String): String {
    val precio: Double = precios.toDouble()
    val precioEnQuetzalesTemp = (precio / 81.23)
    val cant = cantidad.toInt()
    val precioEnQuetzales = precioEnQuetzalesTemp * cant
    return precioEnQuetzales.format()
}

fun TextView.amountConverterString(precios: String, cantidad: String) {
    val precio: Double = precios.toDouble()
    val precioEnQuetzalesTemp = (precio / 81.23)
    val cant = cantidad.toInt()
    val precioEnQuetzales = precioEnQuetzalesTemp * cant
    this.text = context.getString(R.string.lblamount, precioEnQuetzales.format())
}

fun MaterialCardView.backgrounCustom(nombre: String) {
    when (nombre) {
        "Desayunos" -> {
            this.setCardBackgroundColor(resources.getColor(R.color.purple_200))
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
    .placeholder(R.drawable.icon)
    .error(R.drawable.icon)
    .fallback(R.drawable.icon)
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
