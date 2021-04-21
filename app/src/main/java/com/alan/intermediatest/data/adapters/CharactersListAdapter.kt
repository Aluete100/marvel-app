package com.alan.intermediatest.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alan.intermediatest.R
import com.alan.intermediatest.data.models.*
import com.alan.intermediatest.utils.Constants
import com.alan.intermediatest.utils.glide.GlideHelper
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.characters_list_item.view.*
import kotlinx.coroutines.flow.merge

class CharactersListAdapter(
    private val characterItemActions: ICharacterAction
) :
    RecyclerView.Adapter<CharactersListAdapter.CharactersViewHolder>() {

    private var characters = listOf<Character>()

    fun setCharactersData(data: List<Character>) {
        characters = if (characters.isEmpty()) {
            data
        } else {
            characters + data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.characters_list_item, parent, false)
        return CharactersViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val currentCharacter = characters[position]
        holder.bind(currentCharacter)
    }

    inner class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(character: Character) {
            itemView.setOnClickListener {
                characterItemActions.onCharacterPress(character)
            }
            itemView.txt_marvel_character_name.text = character.name
            itemView.txt_marvel_character_description.text = character.description
            character.thumbnail?.path?.let { path ->
                //Check if thumbnail path exists
                character.thumbnail.extension?.let { extension ->
                    //Check if thumbnail extension
                    GlideHelper.loadImage(
                        itemView,
                        path, Constants.IMAGE_XL, extension, itemView.img_character
                    )
                }
            }

        }
    }

    interface ICharacterAction {
        fun onCharacterPress(character: Character)
    }
}