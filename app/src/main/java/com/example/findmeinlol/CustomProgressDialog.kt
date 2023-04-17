package com.example.findmeinlol

import android.app.Dialog
import android.content.Context
import android.view.Window

class CustomProgressDialog(context: Context): Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_progress)
    }
}