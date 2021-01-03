package com.example.unitconverter

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ButtonsFragment : Fragment(), View.OnClickListener  {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var mCallback: ButtonClicked? = null

    interface ButtonClicked {
        fun sendChar(text: String?)
        fun clearChar()
        fun clearAll()
        fun convert()
        fun swap()
    }

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
        val view: View =  inflater.inflate(R.layout.fragment_buttons, container, false)
        (view.findViewById(R.id.button2) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button3) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button4) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button5) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button6) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button7) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button8) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button9) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button10) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button11) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button12) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button13) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button14) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button15) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button16) as Button).setOnClickListener(this)
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ButtonsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ButtonsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View?) {
        val textF: TextView? = view?.findViewById(R.id.fromLabel)
        when (v?.id) {
            R.id.button2 -> mCallback?.sendChar("1")
            R.id.button3 -> mCallback?.sendChar("2")
            R.id.button4 -> mCallback?.sendChar("3")
            R.id.button5 -> mCallback?.sendChar("4")
            R.id.button6 -> mCallback?.sendChar("5")
            R.id.button7 -> mCallback?.sendChar("6")
            R.id.button8 -> mCallback?.sendChar("7")
            R.id.button9 -> mCallback?.sendChar("8")
            R.id.button10 -> mCallback?.sendChar("9")
            R.id.button15 -> mCallback?.sendChar("0")
            R.id.button14 -> mCallback?.sendChar(".")
            R.id.button11 -> mCallback?.clearChar()
            R.id.button12 -> mCallback?.clearAll()
            R.id.button16 -> mCallback?.convert()
            R.id.button13 -> mCallback?.swap()
            else -> {
                Log.println(Log.INFO, "qwe", "dunno what is happening")
            }
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mCallback = try {
            activity as ButtonClicked
        } catch (e: ClassCastException) {
            throw ClassCastException(
                activity.toString()
                        + " must implement TextClicked"
            )
        }
    }

    override fun onDetach() {
        mCallback = null
        super.onDetach()
    }

}