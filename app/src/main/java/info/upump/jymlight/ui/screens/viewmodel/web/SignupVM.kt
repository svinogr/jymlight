package info.upump.jymlight.ui.screens.viewmodel.web

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignupVM : ViewModel() {
    private val _mail = MutableStateFlow("")
    val mail = _mail.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()


    private val _passwordRepeat = MutableStateFlow<String>("")
    val passwordRepeat = _passwordRepeat.asStateFlow()

    fun updateMail(mail: String) {
        _mail.update { mail }
    }

    fun updatePasswordRepeat(passwordRepeat: String) {
        _passwordRepeat.update { passwordRepeat }
    }

    fun updatePassword(password: String) {
        _password.update { password }
    }


}
