package com.example.unitconverter

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

/**
 * A simple [Fragment] subclass.
 * Use the [ButtonsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ButtonsFragment : Fragment(), View.OnClickListener  {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val mListener: OnFragmentInteractionListener? = null

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(link: String?)
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
        val btn: Button = view.findViewById(R.id.button2)
        btn.setOnClickListener(this)
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
            R.id.button2 -> {
                mListener?.onFragmentInteraction("1");
            }
            else -> {
                Log.println(Log.INFO, "qwe", "else")
            }
        }
    }

}