package com.yg.drawer.ui.gallery // This package matches the file location and error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yg.drawer.R // Make sure this imports R from your app's package
import com.google.firebase.firestore.FirebaseFirestore // Import Firebase Firestore
// import com.google.android.material.dialog.MaterialAlertDialogBuilder // Optional: uncomment if you use showDialog

// Data class to represent a user document in Firestore
// It's included here for simplicity as requested, but ideally
// would be in a separate data/model package in larger projects.
data class User(
    var firstName: String? = null, // Use nullable types for flexibility with Firestore
    var lastName: String? = null
)

// Change the class name from FirestoreSaverFragment to GalleryFragment
class GalleryFragment : Fragment() { // <-- Corrected Class Name

    // Declare UI elements
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var buttonSave: Button

    // Declare Firestore instance
    private lateinit var db: FirebaseFirestore

    // onCreateView is where you inflate the layout for this fragment.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize Firestore here or in onCreate()
        db = FirebaseFirestore.getInstance()

        // Inflate the layout for this fragment using the layout file associated with GalleryFragment
        // This should be fragment_gallery.xml based on your navigation graph
        return inflater.inflate(R.layout.fragment_gallery, container, false) // <-- Using fragment_gallery.xml
    }

    // onViewCreated is called after onCreateView and ensures the view is not null.
    // This is the best place to set up your UI elements and listeners.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get references to your UI elements from the inflated view
        // These IDs must match the ones in fragment_gallery.xml (or whichever layout you are using)
        // Based on your previous layout, these might be R.id.Marks, R.id.Category, R.id.button
        // If you are using the layout from the FirestoreSaverFragment example, use R.id.editTextFirstName, etc.
        // I will use the IDs from your original calculator app for now, assuming you put them in fragment_gallery.xml
        editTextFirstName = view.findViewById(R.id.Marks) // Assuming Marks is used for First Name input
        editTextLastName = view.findViewById(R.id.Category) // Assuming Category is used for Last Name input
        buttonSave = view.findViewById(R.id.button) // Assuming button is used for the Save button

        // Set the hint text to be more appropriate for First/Last Name if you haven't changed the layout
        if (editTextFirstName.hint.isNullOrEmpty()) {
            editTextFirstName.hint = "First Name"
        }
        if (editTextLastName.hint.isNullOrEmpty()) {
            editTextLastName.hint = "Last Name"
        }
        if (buttonSave.text.isNullOrEmpty() || buttonSave.text == "Calculate Result") {
            buttonSave.text = "Save User Data"
        }


        // Set click listener for the save button
        buttonSave.setOnClickListener {
            saveUserToFirestore()
        }
    }

    // Function to save user data to Firestore
    private fun saveUserToFirestore() {
        val firstName = editTextFirstName.text.toString().trim() // Get text and trim whitespace
        val lastName = editTextLastName.text.toString().trim()   // Get text and trim whitespace

        // Basic validation
        if (firstName.isEmpty() || lastName.isEmpty()) {
            // Use requireContext() to show the Toast message from the fragment
            Toast.makeText(requireContext(), "Please enter both first and last name", Toast.LENGTH_SHORT).show()
            return // Stop the function if validation fails
        }

        // Create a new User object
        val user = User(firstName, lastName)

        // Add a new document with a generated ID to the "users" collection
        db.collection("users") // Specify the collection name
            .add(user) // Add the user object
            .addOnSuccessListener { documentReference ->
                // Success: Data saved successfully
                // Use requireContext() for the Toast message
                Toast.makeText(requireContext(), "User saved with ID: ${documentReference.id}", Toast.LENGTH_SHORT).show()
                // Optionally clear the input fields after saving
                editTextFirstName.text.clear()
                editTextLastName.text.clear()
            }
            .addOnFailureListener { e ->
                // Failure: Handle the error
                // Use requireContext() for the Toast message
                Toast.makeText(requireContext(), "Error adding user: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Optional: Helper function to show a MaterialAlertDialog
    // Uncomment if you need this dialog functionality
    /*
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
    */

}
