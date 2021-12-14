package hu.bme.aut.quicklog.logs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.quicklog.databinding.FragmentAddLogLayoutBinding
import java.lang.ClassCastException
import java.lang.RuntimeException

class AddLogFragment : DialogFragment() {
    private lateinit var binding: FragmentAddLogLayoutBinding
    lateinit var listener: AddLogListener

    interface AddLogListener{
        fun onLogAdded(title: String, score: Int,description:String,year: Int, month: Int, day: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            listener = parentFragment as AddLogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Calling fragment must implement AddLogListener interface")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = FragmentAddLogLayoutBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("New log")
            .setView(binding.root)
            .setPositiveButton("Log it") { _, _ ->
                listener.onLogAdded(
                    binding.tvAddTitle.text.toString(),
                    binding.sliderScore.value.toInt(),
                    binding.tvDescription.text.toString(),
                    binding.dpTimestamp.year,
                    binding.dpTimestamp.month,
                    binding.dpTimestamp.dayOfMonth
                )
            }
            .setNegativeButton("Cancel", null)
            .create()
    }
}
