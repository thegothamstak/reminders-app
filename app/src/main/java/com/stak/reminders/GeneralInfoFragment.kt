package com.stak.reminders

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.stak.reminders.databinding.DialogEditReminderBinding
import com.stak.reminders.databinding.FragmentGeneralInfoBinding

class GeneralInfoFragment: Fragment() {

    private lateinit var binding: FragmentGeneralInfoBinding
    private val preferences by lazy { requireActivity().getSharedPreferences(PREF_FILENAME, Context.MODE_PRIVATE) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGeneralInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayValues()
        setOnClickListeners()
    }

    private fun displayValues() {
        binding.textViewBinDayValue.text = preferences.getString(PREF_BIN_DAY, null)
        binding.textViewSinNumberValue.text = preferences.getString(PREF_SIN_NUMBER, null)
        binding.textViewPostalAddressValue.text = preferences.getString(PREF_POSTAL_ADDRESS, null)
    }

    private fun setOnClickListeners() {
        binding.cardViewBinDay.setOnClickListener { showEditDialog(PREF_BIN_DAY) }
        binding.cardViewSinNumber.setOnClickListener { showEditDialog(PREF_SIN_NUMBER) }
        binding.cardViewPostalAddress.setOnClickListener { showEditDialog(PREF_POSTAL_ADDRESS) }
    }

    private fun showEditDialog(prefKey: String) {
        val dialogBinding = DialogEditReminderBinding.inflate(requireActivity().layoutInflater)
        dialogBinding.editTextValue.setText(preferences.getString(prefKey, null))
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Update value")
            .setView(dialogBinding.root)
            .setPositiveButton("Save") { _, _ ->
                preferences.edit { putString(prefKey, dialogBinding.editTextValue.text?.toString()) }
                displayValues()
            }
            .setNegativeButton("Cancel") { _, _ ->

            }
            .show()
    }

    companion object {
        const val PREF_FILENAME = "general_info_preferences"
        const val PREF_BIN_DAY = "pref_bin_day"
        const val PREF_SIN_NUMBER = "pref_sin_number"
        const val PREF_POSTAL_ADDRESS = "pref_postal_address"
    }
}