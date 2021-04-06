package by.petshop.petshopdemo.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import by.petshop.petshopdemo.UI.FirebaseUserLiveData

class AuthenticationViewModel : ViewModel() {
    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }
    val authenticationState = FirebaseUserLiveData().map { user ->
       if (user != null) {
           AuthenticationState.AUTHENTICATED
       } else {
           AuthenticationState.UNAUTHENTICATED
       }
   }
}