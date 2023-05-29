package com.example.spycamsecurity.common

import android.app.Activity
import android.widget.Toast
import com.example.spycamsecurity.R
import com.example.spycamsecurity.domain.UserType

/* allows us to employ the OC (open-close) principle:
    OC: software entities should be open for extension
        but closed for modification
        (add new functionality to existing source code
        without having to modify the original source code)
 */

internal fun Activity.makeToast(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_LONG
    ).show()
}

internal val UserType.toLocalizedResource: Int
    get() {
        return when (this) {
            UserType.SERVER -> R.string.server
            UserType.CLIENT -> R.string.client
        }
    }
