package fr.opc.practice.p9a11y

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import fr.opc.practice.p9a11y.databinding.ActivityCase3Binding


/**
 * Activity demonstrates handling form validation
 * and error reporting with a focus on accessibility.
 *
 * This activity validates a pseudonym input field, displaying visual error messages
 * and providing feedback to screen readers using accessibility announcements.
 */
class Case3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityCase3Binding

    // --- OnCreate ---
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCase3Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //--- error message with validation
        binding.validateButton.setOnClickListener {
            val textLength = binding.pseudoEdit.text?.length ?: 0
            if (textLength < 3) {
                val message = getString(R.string.cas_3_pseudo_court, textLength)
                // for user
                binding.errorText.text = message
                binding.errorText.visibility = View.VISIBLE
                // for accessibility talkback
                binding.validateButton.announceForAccessibility(message)
            }
        }
        // --- validation on text change
        binding.pseudoEdit.doOnTextChanged { text, _, _, _ ->
            val textLength = text?.length ?: 0
            binding.errorText.visibility = View.INVISIBLE
            if (textLength < 3) {
                binding.pseudoEdit.backgroundTintList = if (textLength > 2) {
                    ColorStateList.valueOf(resources.getColor(R.color.green400, theme))
                } else {
                    ColorStateList.valueOf(resources.getColor(R.color.red400, theme))
                }
            }

        }
    }
}

