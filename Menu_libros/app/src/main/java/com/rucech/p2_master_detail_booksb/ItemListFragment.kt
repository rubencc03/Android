package com.rucech.p2_master_detail_booksb

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.rucech.p2_master_detail_booksb.placeholder.PlaceholderContent;
import com.rucech.p2_master_detail_booksb.databinding.FragmentItemListBinding
import com.rucech.p2_master_detail_booksb.databinding.ItemListContentBinding

import com.rucech.p2_master_detail_booksb.BookItem
import com.rucech.p2_master_detail_booksb.databinding.ActivityItemDetailBinding
import org.w3c.dom.Text


class ItemListFragment : Fragment() {
    private var item: BookItem? = null
    var aver : String? = null
    //We create a button variable to use a button in the future
    private var btn2: FloatingActionButton? = null
    private var txt2 : TextView? = null
    private val unhandledKeyEventListenerCompat =
        ViewCompat.OnUnhandledKeyEventListenerCompat { v, event ->
            if (event.keyCode == KeyEvent.KEYCODE_Z && event.isCtrlPressed) {
                Toast.makeText(
                    v.context,
                    "Undo (Ctrl + Z) shortcut triggered",
                    Toast.LENGTH_LONG
                ).show()
                true
            } else if (event.keyCode == KeyEvent.KEYCODE_F && event.isCtrlPressed) {
                Toast.makeText(
                    v.context,
                    "Find (Ctrl + F) shortcut triggered",
                    Toast.LENGTH_LONG
                ).show()
                true
            }
            false
        }

    private var _binding: FragmentItemListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    //we change the title of the action bar to Book list
        (activity as AppCompatActivity).supportActionBar?.title="Book List"
        //We create binding to access the elements
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        //We assign fab2 (button that loads the json) to the previously created variable btn2
        btn2=binding.fab2
        txt2=binding.tvmsg
        return binding.root




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // (activity as AppCompatActivity).supportActionBar?.title ="Book List"
        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat)

        val recyclerView: RecyclerView = binding.itemList

        // layout configuration (layout, layout-sw600dp)
        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_container)

        /** Click Listener to trigger navigation based on if you have
         * a single pane layout or two pane layout
         */
        val onClickListener = View.OnClickListener { itemView ->
            val item = itemView.tag as BookItem
            val bundle = Bundle()
            bundle.putString(
                ItemDetailFragment.ARG_ITEM_ID,
                item.id
            )
            if (itemDetailFragmentContainer != null) {
                itemDetailFragmentContainer.findNavController()
                    .navigate(R.id.fragment_item_detail, bundle)
            } else {
                itemView.findNavController().navigate(R.id.show_item_detail, bundle)
            }
        }
        //Listener to load Json
        btn2?.setOnClickListener{

            //We create the snack that Carlos asks us with the text Load
            val snack = Snackbar.make(it, "Do you want to load data?", Snackbar.LENGTH_LONG).setAction("LOAD"){
                //We execute the Setup function, which itself is in charge of executing everything
                setupRecyclerView(recyclerView, onClickListener)
                //This line is placed here so that when we activate the button's listener the image of the action bar is loaded
                (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(getResources().getDrawable(R.drawable.banlibros))
                //We hide the button
                   btn2?.setVisibility(View.GONE)
                txt2?.setVisibility(View.GONE)
                binding.tvmsg2?.setVisibility(View.GONE)
            }.show()
        }

    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        onClickListener: View.OnClickListener,
    ) {

        recyclerView.adapter = SimpleItemRecyclerViewAdapter(
            BookItem.loadJson(requireContext()),
            onClickListener,
        )
    }

    class SimpleItemRecyclerViewAdapter(
        private val bookItemList: MutableList<BookItem>?,
        private val onClickListener: View.OnClickListener,
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val binding =
                ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            //We create a variable to store all the books and thus be able to access their data
            val bookItem = bookItemList?.get(position)!!
            //we access the textview that we have in the xml and assign it the title of a book
            holder.idView.text = bookItem.title
            //we access the textview that we have in the xml and we assign the author of a book
            holder.contentView.text = bookItem.author


            with(holder.itemView) {
                tag = bookItem
                setOnClickListener(onClickListener)


            }
            //We create a variable in which we save the image of each book
            val imageName = bookItem.url_image
            //We take the context of iv1
            val context: Context = holder.iv1.getContext()
            //We create the id of each image
            val id = context.resources.getIdentifier(
                imageName,
                "drawable",
                context.packageName
            )
            //we load the image on the listItem screen
            holder.iv1.setImageResource(id)
        }

        override fun getItemCount() = bookItemList?.size ?: 0

        //We create the viewHolder class to have our ItemList binding (with two textview and the imageview)
        inner class ViewHolder(binding: ItemListContentBinding) :
            RecyclerView.ViewHolder(binding.root) {

            val idView: TextView = binding.idText
            val contentView: TextView = binding.content
            val iv1 : ImageView = binding.iv1
        }





    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}