package com.example.alcoolougasolina

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    var percentual: Double = 0.7
    private val f = 0.7f
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("PDM23", "No onCreate, $percentual")


        sharedPref = getPreferences(Context.MODE_PRIVATE)
        percentual = sharedPref.getFloat("percentual", f).toDouble()
        percentual = BigDecimal(percentual).setScale(2, RoundingMode.HALF_UP).toDouble()

        val btCalc: Button = findViewById(R.id.btCalcular)
        val switch = findViewById<Switch>(R.id.swPercentual)

        btCalc.setOnClickListener(View.OnClickListener {
            resultadoCombustivel(percentual)
            Log.d("PDM23", "No onCreate, $percentual")
        })

        switch.setOnCheckedChangeListener { _, isChecked ->
            Log.d("PDM23", "estado switch: $isChecked")
            if (isChecked) {
                switch.text = "70%"
                percentual = 0.7
            } else {
                switch.text = "75%"
                percentual = 0.75
            }
            sharedPref.edit().putFloat("percentual", percentual.toFloat()).apply()
        }
        switch.isChecked = (percentual == 0.7)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        percentual = savedInstanceState.getDouble("percentual")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("percentual", percentual)
    }

    override fun onResume() {
        super.onResume()
        Log.d("PDM23", "No onResume, $percentual")
    }

    override fun onStart() {
        super.onStart()
        Log.d("PDM23", "No onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("PDM23", "No onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("PDM23", "No onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("PDM23", "No onResume")
    }

    fun resultadoCombustivel(percentual: Double) {

        val gasolina: EditText = findViewById(R.id.precoGasolina)
        val alcool: EditText = findViewById(R.id.precoAlcool)
        val resultado = findViewById<EditText>(R.id.result)

        try {
            val gasolinaPreco: Double = gasolina.text.toString().toDouble()
            val alcoolPreco: Double = alcool.text.toString().toDouble()
            val calculo = gasolinaPreco * percentual

            if (alcoolPreco <= calculo) {
                resultado.setText("Álcool é mais vantajoso")
                resultado.visibility = View.VISIBLE
                resultado.keyListener = null
            } else {
                resultado.visibility = View.VISIBLE
                resultado.keyListener = null
                resultado.setText("Gasolina é mais vantajoso")
            }

        } catch (e: java.lang.NumberFormatException) {
            // handler
            Log.e("PDM23", "Valor invalido");
        }
        Log.d("PDM23", "No btCalcular, $percentual")
    }


}