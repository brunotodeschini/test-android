package br.com.eventos.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import br.com.eventos.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * @author Todeschini
 * @author obruno1997@gmail.com
 * @since 03/08/20
 */

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String?) {
    if(!imageUrl.isNullOrEmpty()) {
        val imgUrl = imageUrl.toUri().buildUpon().scheme("http").build()
        Glide.with(imageView.context)
            .load(imgUrl)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
            )
            .into(imageView)
    }
}