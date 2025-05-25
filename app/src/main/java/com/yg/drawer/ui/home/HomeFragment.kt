package com.yg.drawer.ui.home 

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

    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        
        val marksEditText: EditText = view.findViewById(R.id.Marks)
        val categoryEditText: EditText = view.findViewById(R.id.Category)
        val resultsButton: Button = view.findViewById(R.id.button)

     
        resultsButton.setOnClickListener {
            val marksInput = marksEditText.text.toString()
            val categoryInput = categoryEditText.text.toString().uppercase(Locale.getDefault())

            
            val marks: Float? = try {
                marksInput.toFloat()
            } catch (e: NumberFormatException) {
                null
            }

            if (marks == null || marks < 0 || marks > 100) {
                showDialog("Invalid Input", "Please enter valid marks within the 0-100 range")
                
                return@setOnClickListener
            }

            
            if (categoryInput.length != 1 || !listOf("A", "B", "C").contains(categoryInput)) {
                showDialog("Invalid Input", "Category can only be A/B/C")
                
                return@setOnClickListener
            }

            
            val result = calculateResult(marks, categoryInput)

            
            showDialog("Result", result)
        }
    }

    
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
            else -> "" 
        }
    }

    
    private fun showDialog(title: String, message: String) {
        
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    
    override fun onDestroyView() {
        super.onDestroyView()
        
        // _binding = null
    }
}
