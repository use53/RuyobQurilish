package uz.ruyobqurilish.myruyobqurilish.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.ruyobqurilish.myruyobqurilish.databinding.HomeItemBinding
import uz.ruyobqurilish.myruyobqurilish.model.HomeModel
import uz.ruyobqurilish.myruyobqurilish.onclick.IOnClickListener

class HomeAdapter(val iOnClickListener: IOnClickListener) : ListAdapter<HomeModel, HomeAdapter.VhHome>(Callback()){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VhHome {
       val view=HomeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
           return VhHome(view,iOnClickListener)  }

    override fun onBindViewHolder(holder: VhHome, position: Int) {
        val pos=getItem(position)
        holder.onBind(pos)
    }
    class VhHome(val view: HomeItemBinding,
                 iOnClickListener: IOnClickListener)
        :RecyclerView.ViewHolder(view.root){
         private var homeModel:HomeModel?=null
         init {
             itemView.setOnClickListener {
                 homeModel?.let { it1 -> iOnClickListener.onClick(it1) }
             }
         }

        fun onBind(homeModel: HomeModel){
            this.homeModel=homeModel
            Glide.with(view.root)
                    .load(homeModel.image)
                    .into(view.imageHistory)
            view.tvHistoryMoney.text="${homeModel.money} $"

        }
    }


}

class Callback:DiffUtil.ItemCallback<HomeModel>(){
    override fun areItemsTheSame(oldItem: HomeModel, newItem: HomeModel): Boolean =
            oldItem==newItem

    override fun areContentsTheSame(oldItem: HomeModel, newItem: HomeModel): Boolean =
            oldItem.id==newItem.id
}