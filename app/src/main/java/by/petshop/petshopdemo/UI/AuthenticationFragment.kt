package by.petshop.petshopdemo.UI

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import by.petshop.petshopdemo.R
import by.petshop.petshopdemo.ViewModel.AuthenticationViewModel
import by.petshop.petshopdemo.databinding.FragmentAuthenticationBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class AuthenticationFragment : Fragment() {
    private var _binding: FragmentAuthenticationBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val viewModel by viewModels<AuthenticationViewModel>()

    companion object {
        const val TAG = "AuthenticationFragment"
        const val SIGN_IN_RESULT_CODE = 10
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentAuthenticationBinding.inflate(inflater, container, false)
        (activity as MainActivity).binding.appbar.visibility = View.GONE
        (activity as MainActivity).binding.navViewBottom.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        viewModel.authenticationState.observe(viewLifecycleOwner, { authenticationState ->
            when (authenticationState) {
                AuthenticationViewModel.AuthenticationState.AUTHENTICATED -> {
                    navController.navigate(R.id.shopFragment)
                    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                        navController.popBackStack(R.id.shopFragment, false)
                    }
                }
                AuthenticationViewModel.AuthenticationState.INVALID_AUTHENTICATION ->
                    Snackbar.make(requireView(), requireActivity().getString(R.string.login_unsuccessful_msg),
                            Snackbar.LENGTH_LONG).show()
                else -> {
                    Log.d(TAG, "Состояние аутентификации, не требующее изменения " +
                            "пользовательского интерфейса $authenticationState")
                }
            }
        })
        binding.login.setOnClickListener {
            val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),
                SIGN_IN_RESULT_CODE)
        }
        binding.noLogin.setOnClickListener {
            navController.navigate(R.id.shopFragment)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                Log.d(TAG, "Пользователь ${FirebaseAuth.getInstance().currentUser?.displayName} успешно вошёл в систему.")
            } else {
                Log.d(TAG, "Войти не удалось ${response?.error?.errorCode}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}