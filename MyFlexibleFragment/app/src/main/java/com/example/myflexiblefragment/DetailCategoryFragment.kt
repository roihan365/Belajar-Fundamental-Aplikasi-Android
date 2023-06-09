package com.example.myflexiblefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.security.cert.PKIXRevocationChecker.Option

class DetailCategoryFragment : Fragment() {

    lateinit var tvCategoryName: TextView
    lateinit var tvCategoryDescription: TextView
    lateinit var btnProfile: Button
    lateinit var btnShowDialog: Button

    internal var optionDialogListener: OptionDialogFragment.OnOptionDialogListener = object : OptionDialogFragment.OnOptionDialogListener {
        override fun onOptionChoosen(text: String?) {
            Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
        }
    }

    var description: String? = null
    companion object {
        var EXTRA_NAME = "extra-namw"
        var EXTRA_DESC = "extre_description"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvCategoryName = view.findViewById(R.id.tv_category_name)
        tvCategoryDescription = view.findViewById(R.id.tv_category_description)
        btnProfile = view.findViewById(R.id.btn_profile)
        btnShowDialog = view.findViewById(R.id.btn_show_dialog)

        if (arguments != null) {
            val categoryName = arguments?.getString(EXTRA_NAME)
            val description = arguments?.getString(EXTRA_DESC)
            tvCategoryName.text = categoryName
            tvCategoryDescription.text = description
        }

        btnShowDialog.setOnClickListener{
            val mOptionDialogFragment = OptionDialogFragment()
            val mFragmentManager = childFragmentManager
            mOptionDialogFragment.show(mFragmentManager, Option::class.java.simpleName)
        }
    }
}