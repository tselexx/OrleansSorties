package com.tselexx.orleanssorties
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.widget.TextView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.tselexx.orleanssorties.api.bytag.generatedjson.CustomEvent
import com.tselexx.orleanssorties.api.bytag.generatedjson.Registration
import com.tselexx.orleanssorties.api.bytag.generatedjson.Timing
import com.tselexx.orleanssorties.model.ModelAffItem
import io.reactivex.disposables.Disposable
import java.util.*
import kotlin.collections.ArrayList

class Common_Util_functions {

    companion object {

        lateinit var calendar : Calendar
        lateinit var contx : Context
        lateinit var titre_général : TextView
        lateinit var float_button : android.support.design.widget.FloatingActionButton
        lateinit var backgroundColorAnimator : AnimationDrawable


        var listmodelAffItem : java.util.ArrayList<ModelAffItem> = java.util.ArrayList()
        var modelAffItemSlide = ModelAffItem("","","","",
            "","","","","",
            listOf(Timing("","")),"","","",
            listOf(Registration("","","")),"")
        var listEvents : MutableList<CustomEvent> = ArrayList()
        var eventdateAff = ArrayList<CalendarDay>()

        var total = 0
        var limit = 0

        val imagedefaut = "https://www.france-voyage.com/visuals/photos/orleans-13970_w800.jpg"

        var themeChoix : String = ""
        var themeChoixSauv : String = "init"

        var booltagNavChoix = false
        var boolFirstTime = true
        var nbrday = 0


        val REQUEST_PERMISSIONS = 200

        var dayDateOfDay : Int = 0
        var monthDateOfDay : Int = 0
        var yearDateOfDay : Int = 0
        var hourDateOfDay : Int = 0
        var minuteDateOfDay : Int = 0

        var yearDateSelected : Int = 0
        var monthDateSelected : Int = 0
        var dayDateSelected : Int = 0

        var datedeb = ""
        var datefin = ""
        var calendarDay : CalendarDay? = null
        var calendarsauv : CalendarDay? = null



        var vC : View? = null
        var vL : View? = null
        var vS : View? = null
        var boolBackPressed = false

    }
}