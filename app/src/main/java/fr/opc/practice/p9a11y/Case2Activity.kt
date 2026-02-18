package fr.opc.practice.p9a11y

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import fr.opc.practice.p9a11y.databinding.ActivityCase2Binding

class Case2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityCase2Binding
    private var isFavourite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCase2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setFavouriteButtonIcon(isFavourite)

        binding.favouriteButton.setOnClickListener {
            isFavourite = !isFavourite
            setFavouriteButtonIcon(isFavourite)
        }

        binding.addRecipeToBasket.setOnClickListener {
            Toast.makeText(this, getString(R.string.recette_ajout_au_panier), Toast.LENGTH_SHORT)
                .show()
        }

        binding.recipeCard.setOnClickListener {
            // TODO navigate to recipe screen
        }

        setupCardAccessibility()

    }

    private fun setupCardAccessibility() {
        // no focus for internes objects
        binding.productTitle.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
        binding.favouriteButton.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
        binding.addRecipeToBasket.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO

        // only focus on card and talk
        binding.recipeCard.contentDescription = getString(R.string.demo_cookie_text)

        // add basket action - label fixe
        ViewCompat.addAccessibilityAction(
            binding.recipeCard,
            getString(R.string.ajouter)
        ) { _, _ ->
            binding.addRecipeToBasket.performClick()
            true
        }

        updateFavouriteAccessibilityAction()
    }

    private fun updateFavouriteAccessibilityAction() {
        val label = if (isFavourite) getString(R.string.cas_supprimer_des_favoris)
        else getString(R.string.cd_ajouter_aux_favoris)


        ViewCompat.replaceAccessibilityAction(
            binding.recipeCard,
            AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                R.id.favourite_accessibility_action,
                null
            ),
            label,
        ) { _, _ ->
            binding.favouriteButton.performClick()
            true
        }
    }


    private fun setFavouriteButtonIcon(isFavourite: Boolean) {
        if (isFavourite) {
            binding.favouriteButton.setImageResource(R.drawable.ic_favourite_on)
            binding.favouriteButton.contentDescription =
                getString(R.string.cas_supprimer_des_favoris)
        } else {
            binding.favouriteButton.setImageResource(R.drawable.ic_favourite_off)
            binding.favouriteButton.contentDescription = getString(R.string.cd_ajouter_aux_favoris)
        }
        updateFavouriteAccessibilityAction()
    }

}
