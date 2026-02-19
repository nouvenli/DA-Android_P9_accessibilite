package fr.opc.practice.p9a11y

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.opc.practice.p9a11y.databinding.ActivityCase1Binding

/**
 * Activity demonstrating basic interactive controls.
 * This activity allows users to increment or decrement a quantity and handles
 * accessibility by updating the content description dynamically
 * with argumented string
 */
class Case1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityCase1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCase1Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var quantity = 0
        updateQuantityText(quantity)

        binding.quantityText.text = "$quantity"
        binding.addButton.setOnClickListener {
            quantity++
            updateQuantityText(quantity)

        }

        binding.removeButton.setOnClickListener {
            if (quantity > 0) {
                quantity--
                updateQuantityText(quantity)
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.impossible_d_avoir_une_quantit_n_gative),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun updateQuantityText(quantity: Int) {
        binding.quantityText.text = "$quantity"
        binding.quantityText.contentDescription =  getString(R.string.cas_1_lire_quantité, quantity)
    }
}



