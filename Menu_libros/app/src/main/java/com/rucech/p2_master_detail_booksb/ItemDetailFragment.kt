package com.rucech.p2_master_detail_booksb

import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.rucech.p2_master_detail_booksb.placeholder.PlaceholderContent
import com.rucech.p2_master_detail_booksb.databinding.FragmentItemDetailBinding


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class ItemDetailFragment : Fragment() {


    /**
     * The placeholder content this fragment is presenting.
     */
    private var item: BookItem? = null

    lateinit var itemDetailTextView: TextView
    private var toolbarLayout: CollapsingToolbarLayout? = null
    private var btn:FloatingActionButton? = null

    private var _binding: FragmentItemDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title="Detail"
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the placeholder content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.

                val idBook = it.getString(ARG_ITEM_ID)!!
                //We save the data from the books in the item variable
                item = BookItem.getBookItemById(idBook)

            }
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        toolbarLayout = binding.toolbarLayout
        itemDetailTextView = binding.descripcion
        btn=binding.fab
        //If we click on the information button we create a snackbar with the publication date
        btn?.setOnClickListener{
            val xd=Snackbar.make(it,"Fecha publicacion: ${item?.publication_date} ",Snackbar.LENGTH_LONG)
            xd.show()

        }

        updateContent()



        return rootView
    }

    private fun updateContent() {
        toolbarLayout?.title = item?.title

        // Show the placeholder content as text in a TextView.
        item?.let {
            itemDetailTextView.text = it.description
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}