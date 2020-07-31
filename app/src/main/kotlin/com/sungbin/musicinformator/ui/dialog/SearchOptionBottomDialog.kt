package com.sungbin.musicinformator.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.edit
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.sungbin.musicinformator.R
import com.sungbin.musicinformator.utils.*
import com.sungbin.musicinformator.utils.PathManager.PER_PAGE
import com.sungbin.musicinformator.utils.PathManager.SEARCH_SORT_TYPE
import com.sungbin.musicinformator.utils.PathManager.SEARCH_TYPE
import com.sungbin.musicinformator.utils.TypeManager.ALBUM_SEARCH
import com.sungbin.musicinformator.utils.TypeManager.ARTIST_SEARCH
import com.sungbin.musicinformator.utils.TypeManager.LYRIC_SEARCH
import com.sungbin.musicinformator.utils.TypeManager.POPULARITY_SORT
import com.sungbin.musicinformator.utils.TypeManager.SONG_SEARCH
import com.sungbin.musicinformator.utils.TypeManager.TITLE_SORT
import org.jetbrains.anko.support.v4.defaultSharedPreferences

class SearchOptionBottomDialog : BottomSheetDialogFragment() {

    private lateinit var etSortType: TextInputEditText
    private lateinit var etSearchType: TextInputEditText
    private lateinit var etPerPageCount: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.layout_search_option_dialog, container, false)
        etSortType = layout[R.id.tiet_sort] as TextInputEditText
        etSearchType = layout[R.id.tiet_type] as TextInputEditText
        etPerPageCount = layout[R.id.tiet_per_page] as TextInputEditText

        etSortType.isFocusable = false
        etSearchType.isFocusable = false

        etSortType.setEndDrawableClickEvent {
            val menu = PopupMenu(context, it)
            menu.menu.add(0, 0, 0, TITLE_SORT)
            menu.menu.add(0, 1, 0, POPULARITY_SORT)
            menu.setOnMenuItemClickListener { item ->
                etSortType.text = item.title.toString().toEditable()
                true
            }
            menu.show()
        }

        etSearchType.setEndDrawableClickEvent {
            val menu = PopupMenu(context, it)
            menu.menu.add(0, 0, 0, SONG_SEARCH)
            menu.menu.add(0, 1, 0, LYRIC_SEARCH)
            menu.menu.add(0, 2, 0, ARTIST_SEARCH)
            menu.menu.add(0, 3, 0, ALBUM_SEARCH)
            menu.setOnMenuItemClickListener { item ->
                etSearchType.text = item.title.toString().toEditable()
                true
            }
            menu.show()
        }

        defaultSharedPreferences.run {
            etSortType.text = getString(SEARCH_SORT_TYPE, TITLE_SORT).toEditable()
            etSearchType.text = getString(SEARCH_TYPE, SONG_SEARCH).toEditable()
            etPerPageCount.text = getString(PER_PAGE, "10").toEditable()
        }

        return layout
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        defaultSharedPreferences.edit {
            putString(SEARCH_TYPE, etSearchType.text.toString())
            putString(SEARCH_SORT_TYPE, etSortType.text.toString())
            putString(PER_PAGE, etPerPageCount.text.toString())
        }
    }

    companion object {
        private val instance by lazy {
            SearchOptionBottomDialog()
        }

        fun instance() = instance
    }
}