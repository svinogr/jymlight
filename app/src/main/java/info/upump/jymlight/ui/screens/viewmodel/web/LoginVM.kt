package info.upump.jymlight.ui.screens.viewmodel.web

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginVM : ViewModel() {
    private val _login = MutableStateFlow("")
    val login = _login.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()


    fun getLoginAndPassword() {

    }

    fun updateLogin(login: String) {
        _login.update { login }
    }

    fun updatePassword(password: String) {
        _password.update { password }
    }
}
