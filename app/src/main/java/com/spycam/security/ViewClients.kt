package com.spycam.security

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spycam.security.databinding.ClientsViewBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ViewClients : Fragment() {

    private var _binding: ClientsViewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ClientsViewBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_ViewClients_to_EditClients)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}