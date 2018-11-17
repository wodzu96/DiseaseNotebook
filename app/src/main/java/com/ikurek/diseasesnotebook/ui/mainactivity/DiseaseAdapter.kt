package com.ikurek.diseasesnotebook.ui.mainactivity

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.ikurek.diseasesnotebook.R
import com.ikurek.diseasesnotebook.communication.model.DiseaseModel
import com.ikurek.diseasesnotebook.ui.diseasedetailsactivity.DiseaseDetailsActivity
import kotlinx.android.synthetic.main.item_disease.view.*

/**
 * Created by wodzu on 12.07.2018.
 */

class DiseaseAdapter(applicationContext: Context, private var diseases: List<DiseaseModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return diseases.size
    }


    private var inflater: LayoutInflater = LayoutInflater.from(applicationContext)
    internal var context: Context = applicationContext

    init {
        Log.i("Inflater", inflater.toString())
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {

        val view = inflater.inflate(R.layout.item_disease, null)
        view.name.text = diseases[i].diseaseName
        view.icon.setImageDrawable(getShape())
        view.date_icon.text = diseases[i].startDate.substring(0,2)
        view.setOnClickListener {
            context.startActivity(DiseaseDetailsActivity.getStartingIntent(context, diseases[i].id))
        }
        return view
    }

    private fun getShape(): GradientDrawable{
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadii = floatArrayOf(200f, 200f, 200f, 200f, 200f, 200f, 200f, 200f)
        shape.setColor(getMatColor("400"))
        return  shape
    }

    private fun getMatColor(typeColor: String): Int {
        var returnColor = Color.BLACK
        val arrayId = context.resources.getIdentifier("mdcolor_" + typeColor, "array", context.packageName)

        if (arrayId != 0) {
            val colors = context.resources.obtainTypedArray(arrayId)
            val index = (Math.random() * colors.length()).toInt()
            returnColor = colors.getColor(index, Color.BLACK)
            colors.recycle()
        }
        return returnColor
    }
}