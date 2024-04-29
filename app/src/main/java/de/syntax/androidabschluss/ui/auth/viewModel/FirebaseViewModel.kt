package de.syntax.androidabschluss.ui.auth.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class FirebaseViewModel(application: Application) : AndroidViewModel(application) {

    // Firebase authentication email and password
    private val firebaseAuth = FirebaseAuth.getInstance()


    private val _currentParent = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentParent: LiveData<FirebaseUser?>
        get() = _currentParent

    private val _currentTeacher = MutableLiveData<FirebaseUser?>(null)
    val currentTeacher: LiveData<FirebaseUser?>
        get() = _currentTeacher


    //verify if current user is a teacher or not
    init {
        // Check if the current user is a teacher or a parent
        val currentUser = firebaseAuth.currentUser
        if (currentUser?.email?.endsWith("@abenteuer.com") == true) {
            _currentTeacher.value = currentUser
//            _currentTeacher.value?.displayName?.let { saveUsername(it) }
        } else if (currentUser?.email?.endsWith("@parents.com") == true) {
            _currentParent.value = currentUser
//            _currentParent.value?.displayName?.let { saveUsername(it) }
        }
    }

    fun signup(email: String, password: String, name: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { user ->
                if (user.isSuccessful) {
                    if (email.endsWith("@abenteuer.com")) {
                        _currentTeacher.value = firebaseAuth.currentUser
                        saveUsername(name)

                    }
                    _currentParent.value = firebaseAuth.currentUser
                    if (email.endsWith("@parents.com")) {
                        _currentParent.value = firebaseAuth.currentUser
                        saveUsername(name)
                    }
                } else {
                    Log.e("FirebaseViewModel", "Signup failed: ${user.exception?.message}")
                }
            }
    }

    private fun saveUsername(name: String) {
     // save the username to the firebase user
        val user = firebaseAuth.currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()

        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase View Model", "Username saved successfully")
                } else {
                    Log.e("Firebase View Model", "Failed to save username", task.exception)
                }
            }
    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { user ->
                if (user.isSuccessful) {
                    _currentParent.value = firebaseAuth.currentUser
                    if (email.endsWith("@abenteuer.com")) {
                        _currentTeacher.value = firebaseAuth.currentUser }
                } else {
                    Log.e("FirebaseViewModel", "login: ${user.exception?.message}") }
            }
    }

    fun logout() {
        firebaseAuth.signOut()
        _currentParent.value = firebaseAuth.currentUser
        _currentTeacher.value = null
    }

    fun isTeacherLoggedIn(): Boolean {
        val currentUser = currentTeacher.value
        return currentUser != null && currentUser.email!!.endsWith("@abenteuer.com")
    }
}