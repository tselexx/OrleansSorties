package com.tselexx.orleanssorties.adapter


import android.graphics.Color
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.LinearLayoutCompat
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tselexx.orleanssorties.Common_Util_functions.Companion.modelAffItemSlide
import com.tselexx.orleanssorties.R
import com.tselexx.orleanssorties.model.ModelTableau
import kotlinx.android.synthetic.main.regular_item_slide.view.*

class SlideAdapter : PagerAdapter {

    var tableau = Array<ModelTableau>(10){ModelTableau("","",0)}

    constructor(){
        initTableau()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (view == `object` as LinearLayoutCompat)

    }

    override fun getCount(): Int {

        //il peut n'y avoir qu 1 page ou 2 ou 3 ou 4 selon le nombre de rubrique
        if (modelAffItemSlide.freeText == "")
            return 3
        else
            return 4

    }

    override fun instantiateItem(parent: ViewGroup, position: Int): Any {
        val itemview = LayoutInflater.from(parent.context).inflate(
            R.layout.regular_item_slide,
            parent,
            false
        )
        var i = 0
        var boolFreeText = false

        if (modelAffItemSlide.freeText != "") {
           boolFreeText = true
        }


        when(position){
            0 -> {
                i = 0
                if(boolFreeText){   //
                    itemview.text1_libel_slide.setText(tableau[i].libel)
                    itemview.text1_slide.setText(tableau[i].textAff)
                }
                else{
                    itemview.text1_libel_slide.setText(tableau[i].libel)
                    itemview.text1_slide.setText(tableau[i].textAff)
                    i++
                    itemview.text2_libel_slide.setText(tableau[i].libel)
                    itemview.text2_slide.setText(tableau[i].textAff)
                    i++
                    itemview.text3_libel_slide.setText(tableau[i].libel)
                    itemview.text3_slide.setText(tableau[i].textAff)
                }
            }
            1 -> {
                if(boolFreeText){
                    i = 1
                    itemview.text1_libel_slide.setText(tableau[i].libel)
                    itemview.text1_slide.setText(tableau[i].textAff)
                    i++
                    itemview.text2_libel_slide.setText(tableau[i].libel)
                    itemview.text2_slide.setText(tableau[i].textAff)
                    i++
                    itemview.text3_libel_slide.setText(tableau[i].libel)
                    itemview.text3_slide.setText(tableau[i].textAff)
                }
                else{
                    i = 3
                    itemview.text1_libel_slide.setText(tableau[i].libel)
                    itemview.text1_slide.setText(tableau[i].textAff)
                    i++
                    itemview.text2_libel_slide.setText(tableau[i].libel)
                    itemview.text2_slide.setText(tableau[i].textAff)
                    Linkify.addLinks(itemview.text2_slide, Linkify.ALL)
                    itemview.text2_slide.setTextColor(Color.RED)
                    i++
                    itemview.text3_libel_slide.setText(tableau[i].libel)
                    itemview.text3_slide.setText(tableau[i].textAff)
                }
            }
            2 -> {
                if(boolFreeText){
                    i = 4
                    itemview.text1_libel_slide.setText(tableau[i].libel)
                    itemview.text1_slide.setText(tableau[i].textAff)
                    i++
                    itemview.text2_libel_slide.setText(tableau[i].libel)
                    itemview.text2_slide.setText(tableau[i].textAff)
//                    Linkify.addLinks(itemview.text2_slide, Linkify.ALL)
//                    itemview.text2_slide.setTextColor(Color.RED)

                    i++
                    itemview.text3_libel_slide.setText(tableau[i].libel)
                    itemview.text3_slide.setText(tableau[i].textAff)
                }
                else{
                    i = 6
                    itemview.text1_libel_slide.setText(tableau[i].libel)
                    itemview.text1_slide.setText(tableau[i].textAff)
                    i++
                    itemview.text2_libel_slide.setText(tableau[i].libel)
                    itemview.text2_slide.setText(tableau[i].textAff)
                }
            }
            3 -> {
                i = 7
                itemview.text1_libel_slide.setText(tableau[i].libel)
                itemview.text1_slide.setText(tableau[i].textAff)
                i++
                itemview.text2_libel_slide.setText(tableau[i].libel)
                itemview.text2_slide.setText(tableau[i].textAff)
            }
        }

        parent.addView(itemview)
        return  itemview
    }

    private fun initTableau() {
        var i = 0

        if (modelAffItemSlide.freeText != "") {
            tableau[i].libel= "Description\n"
            tableau[i].textAff = modelAffItemSlide.freeText
            tableau[i].longtext = modelAffItemSlide.freeText.length
            i++
        }

        if (modelAffItemSlide.placename != "") {
            tableau[i].libel= "Lieu\n"
            tableau[i].textAff = modelAffItemSlide.placename
            tableau[i].longtext = modelAffItemSlide.placename.length
            i++
        }

        if (modelAffItemSlide.address != "") {
            tableau[i].libel= "Adresse\n"
            tableau[i].textAff =  modelAffItemSlide.address
            tableau[i].longtext = modelAffItemSlide.address.length
            i++
        }

        if (modelAffItemSlide.timetable.isNotEmpty()) {
            var rupdate = ""
            var debut = ""
            var fin = ""
            tableau[i].libel = "horaires\n"
            for(j in 0..modelAffItemSlide.timetable.size-1){
                val toto = modelAffItemSlide.timetable[j]
                if(toto.start.substring(0,10) == rupdate){
                    tableau[i].textAff += "      de $debut  à  $fin\n"
                }
                else{
                    rupdate = toto.start.substring(0,10)
                    debut = toto.start.substring(11,16)
                    fin = toto.end.substring(11,16)
                    val dateaff = rupdate.substring(8)+ " / " +
                            rupdate.substring(5,7)+ " / " +  rupdate.substring(0,4)
                    tableau[i].textAff += "le $dateaff\n      de $debut  à  $fin\n"
                }
            }
            tableau[i].longtext = tableau[i].textAff.length
            tableau[i].textAff = tableau[i].textAff.replace(":","H")
            i++
        }

        if (modelAffItemSlide.description != "") {
            tableau[i].libel = "Précisions\n"
            tableau[i].textAff = modelAffItemSlide.description
            tableau[i].longtext = modelAffItemSlide.description.length
            i++
        }

        if (modelAffItemSlide.link != "") {
            tableau[i].libel = "Lien\n"
            tableau[i].textAff = modelAffItemSlide.link
            tableau[i].longtext = modelAffItemSlide.link.length
            i++
        }

        if (modelAffItemSlide.pricingInfo != "") {
            tableau[i].libel = "Info prix\n"
            tableau[i].textAff = modelAffItemSlide.pricingInfo
            tableau[i].longtext = modelAffItemSlide.pricingInfo.length
            i++
        }

        if (modelAffItemSlide.registration.isNotEmpty()) {
            tableau[i].libel = "Info contact\n"
            for(j in 0..modelAffItemSlide.registration.size-1){
                val toto = modelAffItemSlide.registration[j]
                var type = ""
                if (toto.type == "phone") type = "tel : "
                if (toto.type == "link") type = "lien : "
                if (toto.type == "email") type = "courriel : "
                tableau[i].textAff += "    ${type}  ${toto.value}\u0020\n\n\n"
            }
            tableau[i].longtext = tableau[i].textAff.length
            i++
        }

        if (modelAffItemSlide.range != "") {
            tableau[i].libel = "Info période\n"
            tableau[i].textAff = modelAffItemSlide.range
            tableau[i].longtext = modelAffItemSlide.range.length
        }
    }

    override fun destroyItem(parent: ViewGroup, position: Int, `object`: Any) {
        parent.removeView(`object` as LinearLayoutCompat)
    }
}
