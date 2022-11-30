package com.example.umrechnugsapp

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


public class MyDialogFragment() : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.CurrError))
            .setPositiveButton(getString(R.string.CurrErrorOK)) { _,_  -> }
            .create()

    companion object {
        const val TAG = "EQUAL CURRENCIES"
    }
}