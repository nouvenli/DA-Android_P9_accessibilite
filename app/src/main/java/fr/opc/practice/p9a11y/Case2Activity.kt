package fr.opc.practice.p9a11y

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import fr.opc.practice.p9a11y.databinding.ActivityCase2Binding

/**
 * This activity demonstrates how to manage accessibility for a complex card component
 * *
 * by grouping information and providing custom accessibility actions. It handles
 * the state of a favourite button and a basket addition within a single focusable
 * recipe card to improve the experience for screen reader users.
 */
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

    /**
     * Configures accessibility for the recipe card for screen reader .
     * - Disables accessibility focus for child elements (title, favorite button, and basket button)
     * - Sets a global content description for the entire recipe card.
     * - Adds a custom accessibility action for adding the recipe to the basket.
     * - Initializes the custom accessibility action for toggling the favorite status.
     */
    private fun setupCardAccessibility() {
        // no focus for internes objects
        binding.productTitle.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
        binding.favouriteButton.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
        binding.addRecipeToBasket.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO

        // only focus on card and talk
        binding.recipeCard.contentDescription = getString(R.string.demo_cookie_text)

        // add basket action and simulate clic on button - label fixe
        ViewCompat.addAccessibilityAction(
            binding.recipeCard,
            getString(R.string.ajouter)
        ) { _, _ ->
            addNewRecipe()
            true
        }

        updateFavouriteAccessibilityAction()
    }

    private fun addNewRecipe() {
        //TODO add recipe
    }

    private fun toggleFavourite(){
        isFavourite = !isFavourite
        setFavouriteButtonIcon(isFavourite)
    }

    /**
     * Update the favourite accessibility action.
     * when FavouriteButtonIcon change, the Label change
     * and clic is simulate
     */
    private fun updateFavouriteAccessibilityAction() {
        val label = if (isFavourite) getString(R.string.cas_2_supprimer_des_favoris)
        else getString(R.string.cd_ajouter_aux_favoris)


        ViewCompat.replaceAccessibilityAction(
            binding.recipeCard,
            AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                R.id.favourite_accessibility_action,
                null
            ),
            label,
        ) { _, _ ->
            toggleFavourite()
            true
        }
    }


    /**
     * Updates the favourite button's visual icon
     * and its description based
     * and triggers update of the accessibility action.
     *
     * @param isFavourite boolean indicating if item is currently marked as a favourite.
     */
    private fun setFavouriteButtonIcon(isFavourite: Boolean) {
        if (isFavourite) {
            binding.favouriteButton.setImageResource(R.drawable.ic_favourite_on)
            binding.favouriteButton.contentDescription =
                getString(R.string.cas_2_supprimer_des_favoris)
        } else {
            binding.favouriteButton.setImageResource(R.drawable.ic_favourite_off)
            binding.favouriteButton.contentDescription = getString(R.string.cd_ajouter_aux_favoris)
        }
        updateFavouriteAccessibilityAction()
    }
}
