package com.example.noteapplication.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.customview.widget.Openable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.noteapplication.MainActivity
import com.example.noteapplication.MainActivity_GeneratedInjector
import com.example.noteapplication.R
import com.example.noteapplication.databinding.FragmentHomeBinding
import com.example.noteapplication.databinding.FragmentSingleNoteBinding
import com.example.noteapplication.model.NoteModels
import com.example.noteapplication.room.entity.NoteEntity
import com.example.noteapplication.viewmodel.AppViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.ui.NavigationUI.setupWithNavController as setupWithNavController1
import androidx.navigation.ui.NavigationUI.setupWithNavController as setupWithNavController2


@AndroidEntryPoint
class SingleNoteFragment : Fragment() {

    private lateinit var binding: FragmentSingleNoteBinding
    private var checkColor = "#64C8FD"

    private val viewModel: AppViewModel by viewModels()
    lateinit var mainActivity: MainActivity
    lateinit var navController: NavController

    lateinit var noteEntity:NoteEntity

    var pinned = false

    var isUpdating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_single_note, container, false)

        binding.singleNoteFragment = this

        getData()
        return binding.root

    }

    private fun getData(){
        if (arguments != null){
            noteEntity = arguments?.getParcelable("datamodel")!!

            binding.titleEdtx.setText(noteEntity.noteModels.title)
            binding.noteEdtx.setText(noteEntity.noteModels.note)
            pinned = noteEntity.noteModels.pinned
            checkColor = noteEntity.noteModels.color

            isUpdating = true

            hideAllChecks()
            colorCheckToVisible(noteEntity.noteModels.color)
        }
    }

    private fun colorCheckToVisible(color:String){
        binding.apply {
            when(color){
                "#64C8FD" -> this.check1.visibility = View.VISIBLE
                "#8069FF" -> this.check2.visibility = View.VISIBLE
                "#FFCC36" -> this.check3.visibility = View.VISIBLE
                "#D77FFD" -> this.check4.visibility = View.VISIBLE
                "#FF419A" -> this.check5.visibility = View.VISIBLE
                "#7FFB76" -> this.check6.visibility = View.VISIBLE

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolBar(view)
    }

    private fun setupToolBar(view: View) {

        navController = Navigation.findNavController(view)
        val appBarConfiguration = AppBarConfiguration.Builder(R.id.singleNoteFragment).build()
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)

        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navController.addOnDestinationChangedListener { controller,destination,argument ->

            if (destination.id == R.id.singleNoteFragment){
                toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        val item = menu.findItem(R.id.pinitem)
        if (pinned)
            item.icon = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_push_pin_24)
        else
            item.icon = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_outline_push_pin_24)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.singlenote_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.pinitem ->
                if (!pinned) {
                    item.icon = ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.ic_baseline_push_pin_24
                    )
                    pinned = !pinned
                    true
                } else {
                    item.icon = ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.ic_outline_push_pin_24
                    )
                    pinned = !pinned
                    true
                }
            android.R.id.home -> {
                mainActivity.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onAddNoteClick(view: View) {

        if (isUpdating){

            binding.apply {
                if (this.titleEdtx.text.isNullOrBlank()) {
                    Snackbar.make(this.mainCoord, "please enter title ...", Snackbar.LENGTH_LONG).show()
                } else {
                    if (this.noteEdtx.text.isNullOrBlank()) {
                        Snackbar.make(this.mainCoord, "please enter note ...", Snackbar.LENGTH_LONG)
                            .show()
                    } else {
                        noteEntity.noteModels.title = this.titleEdtx.text.toString()
                        noteEntity.noteModels.note = this.noteEdtx.text.toString()
                        noteEntity.noteModels.color = checkColor
                        noteEntity.noteModels.pinned = pinned

                        viewModel.updateNoteDatabase(noteEntity)
                        findNavController().navigate(R.id.action_singleNoteFragment_to_homeFragment)

                    }
                }
            }
        }else{
            binding.apply {
                if (this.titleEdtx.text.isNullOrBlank()) {
                    Snackbar.make(this.mainCoord, "please enter title ...", Snackbar.LENGTH_LONG).show()
                } else {
                    if (this.noteEdtx.text.isNullOrBlank()) {
                        Snackbar.make(this.mainCoord, "please enter note ...", Snackbar.LENGTH_LONG)
                            .show()
                    } else {
                        val title = this.titleEdtx.text.toString()
                        val note = this.noteEdtx.text.toString()
                        var color = checkColor

                        val noteModel = NoteModels(title, note, color, pinned)
                        viewModel.insertToNoteModel(noteModel)
                        findNavController().navigate(R.id.action_singleNoteFragment_to_homeFragment)

                    }
                }
            }
        }
    }

    fun onColorViewCheck(check: View) {
        hideAllChecks()

        //visible check that view selected
        check.visibility = View.VISIBLE

        binding.apply {
            when (check.id) {

                // this code for change note color(use in database)
                //we can use this.check1.id
                R.id.check1 -> checkColor = "#64C8FD"
                R.id.check2 -> checkColor = "#8069FF"
                R.id.check3 -> checkColor = "#FFCC36"
                R.id.check4 -> checkColor = "#D77FFD"
                R.id.check5 -> checkColor = "#FF419A"
                R.id.check6 -> checkColor = "#7FFB76"
            }
        }
    }

    // gone all checks
    fun hideAllChecks() {
        binding.apply {
            this.check1.visibility = View.GONE
            this.check2.visibility = View.GONE
            this.check3.visibility = View.GONE
            this.check4.visibility = View.GONE
            this.check5.visibility = View.GONE
            this.check6.visibility = View.GONE


        }
    }

    // get object of MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
}
