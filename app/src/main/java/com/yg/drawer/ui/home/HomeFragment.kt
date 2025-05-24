package com.yg.drawer.ui.home // Make sure this package matches your project structure

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.yg.drawer.R // Make sure this imports R from your app's package
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Locale

class HomeFragment : Fragment() {

    // onCreateView is where you inflate the layout for this fragment.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using your fragment_home.xml
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    // onViewCreated is called after onCreateView and ensures the view is not null.
    // This is the best place to set up your UI elements and listeners.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get references to your UI elements from the inflated view
        val marksEditText: EditText = view.findViewById(R.id.Marks)
        val categoryEditText: EditText = view.findViewById(R.id.Category)
        val resultsButton: Button = view.findViewById(R.id.button)

        // Set the click listener for the button
        resultsButton.setOnClickListener {
            val marksInput = marksEditText.text.toString()
            val categoryInput = categoryEditText.text.toString().uppercase(Locale.getDefault())

            // Validate Marks
            val marks: Float? = try {
                marksInput.toFloat()
            } catch (e: NumberFormatException) {
                null
            }

            if (marks == null || marks < 0 || marks > 100) {
                showDialog("Invalid Input", "Please enter valid marks within the 0-100 range")
                // Use return@setOnClickListener to exit the lambda correctly
                return@setOnClickListener
            }

            // Validate Category
            if (categoryInput.length != 1 || !listOf("A", "B", "C").contains(categoryInput)) {
                showDialog("Invalid Input", "Category can only be A/B/C")
                // Use return@setOnClickListener to exit the lambda correctly
                return@setOnClickListener
            }

            // Calculate Result using the helper function
            val result = calculateResult(marks, categoryInput)

            // Show Result using the helper function
            showDialog("Result", result)
        }
    }

    // Helper function to calculate the result based on marks and category
    private fun calculateResult(marks: Float, category: String): String {
        return when (category) {
            "A" -> {
                when {
                    marks < 35 -> "Fail"
                    marks < 60 -> "Pass"
                    marks < 75 -> "First Class"
                    else -> "Distinction"
                }
            }
            "B" -> {
                when {
                    marks < 30 -> "Fail"
                    marks < 50 -> "Pass"
                    marks < 70 -> "First Class"
                    else -> "Distinction"
                }
            }
            "C" -> {
                when {
                    marks < 25 -> "Fail"
                    marks < 45 -> "Pass"
                    marks < 65 -> "First Class"
                    else -> "Distinction"
                }
            }
            else -> "" // Should not happen due to validation, but good to have a default
        }
    }

    // Helper function to show a MaterialAlertDialog
    private fun showDialog(title: String, message: String) {
        // Use requireContext() to get the context from the fragment's attached activity
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    // onDestroyView is called when the fragment's view is being destroyed.
    // Clean up any references to views here if needed (though not strictly
    // necessary for this simple case without View Binding).
    override fun onDestroyView() {
        super.onDestroyView()
        // If you were using View Binding, you would set the binding variable to null here.
        // _binding = null
    }
}
