package de.syntax.androidabschluss.ui.home

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentHomeBinding
import de.syntax.androidabschluss.ui.auth.viewModel.FirebaseViewModel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class HomeFragment : Fragment() {
    private val firebaseViewModel: FirebaseViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var userName: String

    // list of images for the carousel
    private val list = mutableListOf<CarouselItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addNewUserItem = binding.sidebarMenu.menu.findItem(R.id.add_new_user)
        //side menu signup - welcome user
        firebaseViewModel.currentTeacher.observe(viewLifecycleOwner) { teacher ->
            addNewUserItem.isVisible = teacher != null
            val welcomeText = binding.sidebarMenu.getHeaderView(0)
                .findViewById<TextView>(R.id.username)
            if (teacher != null) {
                val userName = teacher.displayName ?: teacher.email
                Log.e("HomeFragment", "Teacher: $userName")
                welcomeText.text = "Welcome Teacher $userName"
            }
        }

        firebaseViewModel.currentParent.observe(viewLifecycleOwner) { parent ->
            val welcomeText = binding.sidebarMenu.getHeaderView(0)
                .findViewById<TextView>(R.id.username)
            if (parent != null && firebaseViewModel.currentTeacher.value == null) {
                val userName = parent.displayName ?: parent.email
                welcomeText.text = "Welcome Parent $userName"
            }
        }
        // icon for the chatbot and navigate to chat
        val gifImage = binding.ivIaChat
        Glide.with(this)
            .load(R.drawable.gif_bot)
            .into(gifImage)
        binding.ivIaChat.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToIAChatFragment())
        }

        // List of images for carousel
        val carousel = binding.carousel
        list.add(CarouselItem("https://images.unsplash.com/photo-1503454537195-1dcabb73ffb9?q=80&w=2848&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"))
        list.add(CarouselItem("https://images.unsplash.com/photo-1551966775-a4ddc8df052b?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"))
        list.add(CarouselItem("https://images.unsplash.com/photo-1516627145497-ae6968895b74?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"))
        list.add(CarouselItem("https://images.unsplash.com/photo-1502781252888-9143ba7f074e?q=80&w=2942&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"))
        list.add(CarouselItem("https://img.freepik.com/foto-gratis/colegiales-leyendo-biblioteca_1098-4048.jpg?t=st=1710403073~exp=1710403673~hmac=aa824a133d5648b419be7a39c259a14359d9bcacd1878d71c681f45e7d0051d6"))
        list.add(CarouselItem("https://img.freepik.com/foto-gratis/profesora-ensenando-leer_1098-823.jpg"))
        list.add(CarouselItem("https://img.freepik.com/fotos-premium/ninos-jugando-habitacion_52137-5228.jpg?w=1480"))
        carousel.addData(list)


        //sidebar navigation
        binding.apply {
            toolbar.setNavigationOnClickListener {
                drawerLayout.open()
            }
            sidebarMenu.setNavigationItemSelectedListener { menuItem ->
                menuItem.isChecked = true
                when (menuItem.itemId) {
                    R.id.item_home -> {
                        drawerLayout.close()
                    }
                    R.id.item_bot_ia -> {
                        findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToIAChatFragment())
                    }
                    R.id.add_new_user -> {
                        findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToSignUpFragment2())
                    }
                    R.id.item_logout -> {
                        firebaseViewModel.logout()
                        findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToWelcomeActivity())
                    }
                    R.id.item_info -> {
                        findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToInfoFragment())
                    }
                }
                return@setNavigationItemSelectedListener true
            }
        }

        // card click listener and open dialogFragment
        binding.cardArt.setOnClickListener {
            openDialogFragment(
                R.drawable.img_art,
                "Art",
                "Cultivates creativity and individual expression in every child.",
                R.color.md_theme_light_primaryContainer
            )
        }
        binding.cardHumanity.setOnClickListener {
            openDialogFragment(
                R.drawable.img_heart,
                "Humanity",
                "Nurtures respect, empathy, and compassion among children.",
                R.color.md_theme_light_errorContainer
            )
        }
        binding.cardMusic.setOnClickListener {
            openDialogFragment(
                R.drawable.img_music,
                "Music",
                "Inspires harmony and rhythm in daily learning.",
                R.color.md_theme_light_errorContainer
            )
        }
        binding.cardDance.setOnClickListener {
            openDialogFragment(
                R.drawable.img_dance,
                "Dance",
                "Celebrates body expression and free movement.",
                R.color.md_theme_light_primaryContainer
            )
        }
        binding.cardEducation.setOnClickListener {
            openDialogFragment(
                R.drawable.img_education,
                "Education",
                "Guides intellectual and emotional growth in every child.",
                R.color.md_theme_light_primaryContainer
            )
        }
        binding.cardInclusivity.setOnClickListener {
            openDialogFragment(
                R.drawable.img_humanity,
                "Inclusivity",
                "Promotes diversity and equality in our community.",
                R.color.md_theme_light_errorContainer
            )
        }

        // animation for the cards and logo
        animationCards(binding.cardInclusivity)
        animationCards(binding.cardEducation)
        animationCards(binding.cardDance)
        animationCards(binding.cardMusic)
        animationCards(binding.cardHumanity)
        animationCards(binding.cardArt)
        moveLogo()
    }

    private fun moveLogo() {
        val rotationAnimator = ObjectAnimator.ofFloat(binding.imgLogo, View.ROTATION, -3f, 3f)
        rotationAnimator.duration = 3000
        rotationAnimator.repeatCount = ObjectAnimator.INFINITE
        rotationAnimator.repeatMode = ObjectAnimator.REVERSE
        rotationAnimator.interpolator = AccelerateDecelerateInterpolator()
        rotationAnimator.start()
    }
    private fun animationCards(cardView: View) {
        val scaleAnim = ObjectAnimator.ofPropertyValuesHolder(
            cardView,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 0.95f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.95f))
        scaleAnim.duration = 1000
        scaleAnim.repeatCount = ObjectAnimator.INFINITE
        scaleAnim.repeatMode = ObjectAnimator.REVERSE
        scaleAnim.start()
    }

    private fun openDialogFragment(image: Int, title: String, text: String, backgroundColor: Int) {
        val dialogFragment = CardsFragment.newInstance(image, title, text, backgroundColor)
        dialogFragment.show(childFragmentManager, "CardsDialogFragment")
    }
}