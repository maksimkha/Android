package com.example.unitconverter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.math.pow


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ConvertFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConvertFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_convert, container, false)
        (root.findViewById(R.id.button17) as Button).setOnClickListener{
            val clipboardManager = this.activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val label = "Successfuly copied!"
            val textFrom: TextView? = view?.findViewById<TextView>(R.id.fromLabel)
            val text = textFrom?.text.toString()
            val clip = ClipData.newPlainText(label, text)
            clipboardManager.setPrimaryClip(clip)
        }
        (root.findViewById(R.id.button18) as Button).setOnClickListener{
            val clipboardManager = this.activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val label = "Successfuly copied!"
            val textFrom: TextView? = view?.findViewById<TextView>(R.id.toLabel)
            val text = textFrom?.text.toString()
            val clip = ClipData.newPlainText(label, text)
            clipboardManager.setPrimaryClip(clip)
        }
        return root
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        val textFrom: TextView? = view?.findViewById<TextView>(R.id.fromLabel)
        val textTo: TextView? = view?.findViewById<TextView>(R.id.toLabel)
        savedInstanceState.putString("textFrom", textFrom?.text.toString())
        savedInstanceState.putString("textTo", textTo?.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val textFrom: TextView? = view?.findViewById<TextView>(R.id.fromLabel)
        val textTo: TextView? = view?.findViewById<TextView>(R.id.toLabel)
        textFrom?.text = savedInstanceState?.getString("textFrom")
        textTo?.text = savedInstanceState?.getString("textTo")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ConvertFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConvertFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun updateText(text: String?) {
        val textFrom: TextView? = view?.findViewById<TextView>(R.id.fromLabel)
        var input: String? = textFrom?.text.toString()
        input += text
        textFrom?.text = input;
    }

    fun clearCharText() {
        val textFrom: TextView? = view?.findViewById<TextView>(R.id.fromLabel)
        val input: String? = textFrom?.text.toString()
        textFrom?.text = input?.dropLast(1);
    }

    fun clearText() {
        val textFrom: TextView? = view?.findViewById<TextView>(R.id.fromLabel)
        textFrom?.text = ""
    }

    fun convert(kef: Int) {
        val textFrom: TextView? = view?.findViewById<TextView>(R.id.fromLabel)
        val value = textFrom?.text?.toString()?.toInt()
        val fromSpinner: Spinner? = view?.findViewById<Spinner>(R.id.fromBox)
        val toSpinner: Spinner? = view?.findViewById<Spinner>(R.id.toBox)
        val difference = fromSpinner?.selectedItemId?.toInt()
            ?.minus(toSpinner?.selectedItemId?.toInt()!!)
        val result = when (kef) {
            0 -> value?.times((difference?.let { 10.0.pow(it) }!!))
            1 -> value?.times((difference?.let { 60.0.pow(it) }!!))
            else->{
                value?.times((difference?.let { 1000.0.pow(it) }!!))
            }
        }
        val textTo: TextView? = view?.findViewById<TextView>(R.id.toLabel)
        textTo?.text = result.toString()
    }

    fun swapText() {
        val textFrom: TextView? = view?.findViewById<TextView>(R.id.fromLabel)
        val textTo: TextView? = view?.findViewById<TextView>(R.id.toLabel)
        val temp = textFrom?.text
        textFrom?.text = textTo?.text
        textTo?.text = temp
        val fromSpinner: Spinner? = view?.findViewById<Spinner>(R.id.fromBox)
        val toSpinner: Spinner? = view?.findViewById<Spinner>(R.id.toBox)
        val fromId = fromSpinner?.selectedItemId?.toInt()
        val toId = toSpinner?.selectedItemId?.toInt()
        if (fromId != null) {
            toSpinner?.setSelection(fromId)
        }
        if (toId != null) {
            fromSpinner?.setSelection(toId)
        }
    }
}
