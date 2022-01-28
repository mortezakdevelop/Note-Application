package com.example.noteapplication.ui

import UPCommingAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.noteapplication.R
import com.example.noteapplication.adapter.CardClickListener
import com.example.noteapplication.adapter.PinnedAdapter
import com.example.noteapplication.databinding.FragmentHomeBinding
import com.example.noteapplication.room.entity.NoteEntity
import com.example.noteapplication.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment(),CardClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: AppViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        //fill fragmentHome with this fragment
        // فرگمنت داخل xml را با همان فرگمنت داخل این کلاس پر کردیم
        // name of variable databinding in xml
        //binding.fragmentHome = this

        setUpPinningRecyclerView()
        setUpUncommingRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // close app when we are in homeFragment
            val home = requireActivity().findViewById<View>(R.id.homeCon)

            requireActivity().onBackPressedDispatcher.addCallback(
                requireActivity(),
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (home.id == R.id.homeCon) {
                            requireActivity().finish()
                        } else {
                            isEnabled = false
                            requireActivity().onBackPressed()
                        }
                    }
                })
    }
    private fun setUpUncommingRecyclerView() {

        viewModel.liveData.observe(viewLifecycleOwner) { listData ->
            val data: ArrayList<NoteEntity> = ArrayList()
            listData.forEach {
                if (!it.noteModels.pinned) {
                    data.add(it)
                }
            }
            if (data.isEmpty()) {
                binding.textView3.visibility = View.VISIBLE
            } else {
                binding.textView3.visibility = View.GONE
            }

            binding.upcomingRv.adapter = UPCommingAdapter(data, this)
        }
//        val data: ArrayList<NoteModels> = ArrayList()
//        data.add(NoteModels("title1", "note 1","#FF03DAC5",false))
//        data.add(NoteModels("title2", "note 2","#FF6200EE",false))
//        data.add(NoteModels("title 3", "note 3","#FFEB3E",false))
//        data.add(NoteModels("title 4", "note 4","#FFBB86FC",false))
//        data.add(NoteModels("title 5", "note 5","#FFBB86FC",false))
//        data.add(NoteModels("title 6", "note 6","#FFBB86FC",false))
//        data.add(NoteModels("title 7", "note 7","#FFBB86FC",false))
//
//
//        binding.upcomingRv.adapter = UPCommingAdapter(data)
    }

    // add fake data for show
    private fun setUpPinningRecyclerView() {

        viewModel.liveData.observe(viewLifecycleOwner) { listData ->
            val data: ArrayList<NoteEntity> = ArrayList()
            listData.forEach {
                if (it.noteModels.pinned) {
                    data.add(it)
                }
            }
            if (data.isEmpty()) {
                binding.pinnedCon.visibility = View.GONE
            } else {
                binding.pinnedCon.visibility = View.VISIBLE
            }

            binding.pinnedRv.adapter = PinnedAdapter(data, this)

//        val data: ArrayList<NoteModels> = ArrayList()
//        data.add(NoteModels("title 1", "note 1","#FFBB86FC",false))
//        data.add(NoteModels("title 2", "note 2","#FFBB86FC",false))
//        data.add(NoteModels("title 3", "note 3","#FFBB86FC",false))
//
//        if (data.isEmpty())
//            binding.pinnedCon.visibility = View.GONE
//        else
//            binding.pinnedCon.visibility = View.VISIBLE
//
//        binding.pinnedRv.adapter = PinnedAdapter(data)
        }

        binding.addNoteFab.setOnClickListener {
            findNavController().navigate(R.id.navigateToHomeFragmentToSingleNoteFragment)
        }
    }

    override fun onItemRVClickListener(noteEntity: NoteEntity) {
        val bundle = bundleOf("datamodel" to noteEntity)
        Navigation.findNavController(binding.root)
            .navigate(R.id.navigateToHomeFragmentToSingleNoteFragment, bundle)
    }

    override fun onMenuItemRVClickListener(imageFilterButton: View, noteEntity: NoteEntity) {

        val popUpMenu = PopupMenu(requireContext(), imageFilterButton)

        // clickable item menu and delete from database
        popUpMenu.setOnMenuItemClickListener (PopupMenu.OnMenuItemClickListener{

            when(it.itemId) {
                R.id.delete ->{
                    deleteNoteFromDb(noteEntity)
                    true
                }
                else -> return@OnMenuItemClickListener false

            }
        })
        popUpMenu.inflate(R.menu.delete_menu)
        popUpMenu.show()

    }
    fun deleteNoteFromDb(noteEntity: NoteEntity){
        viewModel.deleteNote(noteEntity)
    }
}


