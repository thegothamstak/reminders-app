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
import com.stak.reminders.databinding.FragmentPasswordsBinding

class PasswordsFragment: Fragment() {

    private lateinit var binding: FragmentPasswordsBinding
    private val preferences by lazy { requireActivity().getSharedPreferences(PREF_FILENAME, Context.MODE_PRIVATE) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayValues()
        setOnClickListeners()
    }

    private fun displayValues() {
        binding.textViewWifiValue.text = preferences.getString(PREF_WIFI, null)
        binding.textViewTobuTabletValue.text = preferences.getString(PREF_TOBU_TABLET, null)
        binding.textViewFrontDoorValue.text = preferences.getString(PREF_FRONT_DOOR, null)
    }

    private fun setOnClickListeners() {
        binding.cardViewWifi.setOnClickListener { showEditDialog(PREF_WIFI) }
        binding.cardViewTobuTablet.setOnClickListener { showEditDialog(PREF_TOBU_TABLET) }
        binding.cardViewFrontDoor.setOnClickListener { showEditDialog(PREF_FRONT_DOOR) }
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
        const val PREF_FILENAME = "passwords_preferences"
        const val PREF_WIFI = "pref_wifi"
        const val PREF_TOBU_TABLET = "pref_tobu_tablet"
        const val PREF_FRONT_DOOR = "pref_front_door"
    }
}