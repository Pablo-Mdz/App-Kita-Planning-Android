package de.syntax.androidabschluss.ui.home
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentCardsBinding

class CardsFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val window = dialog?.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setDimAmount(0.8f)
    }

    // create a new instance of the fragment
    companion object {
        fun newInstance(image: Int, title: String, text: String, backgroundColor : Int): CardsFragment {
            val fragment = CardsFragment()
            val args = Bundle()
            args.putString("image", image.toString())
            args.putString("title", title)
            args.putString("text", text)
            args.putInt("backgroundColor", backgroundColor)
            fragment.arguments = args
            return fragment
        }
    }

    // create the dialog (cards)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        val dialogBinding = FragmentCardsBinding.inflate(inflater)
        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogBinding.root)

        val image = requireArguments().getString("image", "")
        val title = requireArguments().getString("title", "")
        val text = requireArguments().getString("text", "")
        val backgroundColor = requireArguments().getInt("backgroundColor", R.color.md_theme_light_errorContainer)
        val color = ContextCompat.getColor(requireContext(), backgroundColor)

        dialogBinding.cardView.setBackgroundColor(color)
        dialogBinding.ivCard.setImageResource(image.toInt())
        dialogBinding.tvTitle.text = title
        dialogBinding.tvDescription.text = text
        // width and height of the dialog
        val width =
            resources.displayMetrics.widthPixels * 0.8 // Zum Beispiel 90% der Bildschirmbreite
        val height =
            resources.displayMetrics.heightPixels * 0.36 // Zum Beispiel 80% der Bildschirmhöhe
        dialog.window?.setLayout(width.toInt(), height.toInt())

        // radius for the corners
        val drawable = GradientDrawable()
        drawable.cornerRadius = resources.displayMetrics.density * 20 // Radius in dp umwandeln
        dialog.window?.setBackgroundDrawable(drawable)

        return dialog
    }

}


