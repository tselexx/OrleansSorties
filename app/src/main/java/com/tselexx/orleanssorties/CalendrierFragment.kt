package com.tselexx.orleanssorties

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.tselexx.orleanssorties.Common_Util_functions.Companion.backgroundColorAnimator
import com.tselexx.orleanssorties.Common_Util_functions.Companion.boolBackPressed
import com.tselexx.orleanssorties.Common_Util_functions.Companion.boolFirstTime
import com.tselexx.orleanssorties.Common_Util_functions.Companion.calendarDay
import com.tselexx.orleanssorties.Common_Util_functions.Companion.calendarsauv
import com.tselexx.orleanssorties.Common_Util_functions.Companion.contx
import com.tselexx.orleanssorties.Common_Util_functions.Companion.datedeb
import com.tselexx.orleanssorties.Common_Util_functions.Companion.datefin
import com.tselexx.orleanssorties.Common_Util_functions.Companion.dayDateOfDay
import com.tselexx.orleanssorties.Common_Util_functions.Companion.dayDateSelected
import com.tselexx.orleanssorties.Common_Util_functions.Companion.eventdateAff
import com.tselexx.orleanssorties.Common_Util_functions.Companion.float_button
import com.tselexx.orleanssorties.Common_Util_functions.Companion.hourDateOfDay
import com.tselexx.orleanssorties.Common_Util_functions.Companion.imagedefaut
import com.tselexx.orleanssorties.Common_Util_functions.Companion.limit
import com.tselexx.orleanssorties.Common_Util_functions.Companion.listEvents
import com.tselexx.orleanssorties.Common_Util_functions.Companion.minuteDateOfDay
import com.tselexx.orleanssorties.Common_Util_functions.Companion.monthDateOfDay
import com.tselexx.orleanssorties.Common_Util_functions.Companion.monthDateSelected
import com.tselexx.orleanssorties.Common_Util_functions.Companion.nbrday
import com.tselexx.orleanssorties.Common_Util_functions.Companion.themeChoix
import com.tselexx.orleanssorties.Common_Util_functions.Companion.titre_général
import com.tselexx.orleanssorties.Common_Util_functions.Companion.total
import com.tselexx.orleanssorties.Common_Util_functions.Companion.vC
import com.tselexx.orleanssorties.Common_Util_functions.Companion.yearDateOfDay
import com.tselexx.orleanssorties.Common_Util_functions.Companion.yearDateSelected
import com.tselexx.orleanssorties.api.bytag.generatedjson.*
import com.tselexx.orleanssorties.api.bytag.interfacage.GetListeTagSortiesAll
import com.tselexx.orleanssorties.api.bytag.interfacage.GetListeTagSortiesWithTag
import com.tselexx.orleanssorties.api.bytag.mainmodel.ListeTagSorties
import com.tselexx.orleanssorties.interfacage.IOnBackPressed
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.regular_calendrier_fragment.view.*
import java.text.SimpleDateFormat
import java.util.*


//https://medium.com/@elye.project/kotlin-and-retrofit-2-tutorial-with-working-codes-333a4422a890


class CalendrierFragment : Fragment(), IOnBackPressed


{

    private val getListeTagSortiesWithTag by lazy {
        GetListeTagSortiesWithTag.create()
    }
    private val getListeTagSortiesAll by lazy {
        GetListeTagSortiesAll.create()
    }

    val TAG = "TAG CalendrierFragment"
    lateinit var  disposable : Disposable

    var resultat : ListeTagSorties? = null

    override fun onBackPressed(): Boolean {
        Log.e(TAG,"  onBackPressed")

        boolBackPressed = false
        return boolBackPressed
    }



    override fun onResume() {
        super.onResume()

        //la date du jour peut changer à minuit donc on la réinitialise
        val cal = Calendar.getInstance()
        dayDateOfDay = cal.get(Calendar.DAY_OF_MONTH)
        monthDateOfDay = cal.get(Calendar.MONTH) + 1
        yearDateOfDay = cal.get(Calendar.YEAR)
        hourDateOfDay = cal.get(Calendar.HOUR_OF_DAY)
        minuteDateOfDay = cal.get(Calendar.MINUTE)


        //si premier affichage du calendrier on force
        //l'affichage du calendrier sur le mois en cours
        //l'affichage de la date séléectionnée sur la date du jour
        //le titre par défaut
        if(boolFirstTime){
            boolFirstTime = false
            vC!!.calendarView.setCurrentDate(cal.getTime())
            vC!!.calendarView.setSelectedDate(cal.getTime())
//            titre_général?.text = "tous les thèmes du $dayDateOfDay / $monthDateOfDay / $yearDateOfDay"
        }
        traitement_date_selected(calendarsauv!!)

        //sert à animer les couleurs du titre si ce n'est pas déjà fait
        if (backgroundColorAnimator != null && !backgroundColorAnimator.isRunning()) {
            backgroundColorAnimator.start()
        }

        Log.e(TAG,"  onResume")
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e(TAG,"  onCreateView")
        vC = inflater.inflate(R.layout.regular_calendrier_fragment, container, false)

        //action à faire suite à la sélection d'une date affichée dans le calendrier
        //on utilise une coroutine (light thread)
        vC!!.calendarView.setOnDateChangedListener {
            _, date, _ ->
//          on enlève le runBlocking qui n'a rien à faire là
//            runBlocking {
//                traitement_date_selected(date)
//            }
            traitement_date_selected(date)

        }
        //le floating button est toujours visible quand le calendrier est affiché
        float_button.visibility = View.VISIBLE

        //action suite à la sélection du floating button
        float_button.setOnClickListener {
            //le floating button est toujours invisible quand le calendrier n'est pas affiché
            float_button.visibility = View.INVISIBLE
            searchListeTagSorties(datedeb,datefin)
        }

        //animation des couleurs du titre
        backgroundColorAnimator = titre_général.background as AnimationDrawable
        backgroundColorAnimator.setEnterFadeDuration(2000)
        backgroundColorAnimator.setExitFadeDuration(2000)

        return vC
    }

    //traitement quand une date est selectionnée dans le calendrier (ou initialisée la première fois)
    public fun traitement_date_selected(date: CalendarDay) {

        calendarsauv = date
        yearDateSelected = date.year
        monthDateSelected = date.month + 1
        dayDateSelected = date.day


        //toutes les décorations éventuelles antérieures du calendrier sont effacées
        vC!!.calendarView.removeDecorators()

        //initialisation de la date de début de recherche à la date sélectionnée
        datedeb = yearDateSelected.toString() + "-" + monthDateSelected.toString() + "-" + dayDateSelected.toString()

        //initialisation de la de fin de recherche en fonction du nombre de jours à afficher et de la date sélectionnée
        if(nbrday == 0) {
            datefin = datedeb
        }
        else
        {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            var cal  = Calendar.getInstance()
            cal.set(Calendar.YEAR, yearDateSelected)
            cal.set(Calendar.MONTH, monthDateSelected - 1)
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)

            cal.set(Calendar.DAY_OF_MONTH, dayDateSelected + nbrday)
            datefin = sdf.format(cal.time)
        }
        trait_affichage_titre_general()

        trait_affichage_date(nbrday)

    }

    private fun trait_affichage_titre_general() {
        var debtitre = ""
        when(themeChoix)
        {
            "" -> debtitre = "tous les thèmes\ndu "//= tous les thèmes
            "balade-decouverte-visite" -> debtitre = "thème : balade-découverte-visite\ndu "
            "conference-rencontre-debat" -> debtitre = "thème : conférence-rencontre-débat\ndu "
            "exposition" -> debtitre = "thème : exposition\ndu "
            "fete-salon-marche" -> debtitre = "thème : fête-salon-marché\ndu "
            "musique" -> debtitre = "thème : musique\ndu "
            "projection-cinema" -> debtitre = "thème : projection-cinéma\ndu "
            "spectacle" -> debtitre = "thème : spectacle\ndu "
            "sport" -> debtitre = "thème : sport\ndu "
            "stage-atelier-jeu" -> debtitre = "thème : stage-atelier-jeu\ndu "
            "jeune-public" -> debtitre = "thème : jeune-public\ndu "
            else -> Log.e(TAG,"Erreur trait_affichage_titre_general themeChoix = $themeChoix ")
        }
        var midtitre = "$dayDateSelected / $monthDateSelected / $yearDateSelected"
        var fintitre = ""
        if(nbrday != 0){
            fintitre = " au " + datefin.substring(8) + " / " +
                    datefin.substring(5,7) + " / " + datefin.substring(0,4)
        }
        titre_général.text = debtitre + midtitre + fintitre
    }

    private fun trait_affichage_date(nbrday: Int) {
        //pour l'affichage on est obligé d'alimenter un arraylist de Calendarday (l'outil veut ça)
        //la date sélectionnée est d'office stockée dans cette arraylist
        eventdateAff.clear()
        calendarDay = CalendarDay.from(yearDateSelected,monthDateSelected-1,dayDateSelected)
        eventdateAff.add(calendarDay!!)

        //si nombre de jours à afficher = 0 on a pas besoin de faire la mayonnaise
        // à voir si l'outil CalendarDay peut éviter l'utilisation d'un formatage
        if (nbrday != 0)
        {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            var iter = 0

            var boolcont = true
            do{
                if(iter >= nbrday) boolcont=false
                else{
                    iter++
                    var cal  = Calendar.getInstance()
                    cal.set(Calendar.YEAR, yearDateSelected)
                    cal.set(Calendar.MONTH, monthDateSelected - 1)
                    cal.set(Calendar.HOUR_OF_DAY, 0)
                    cal.set(Calendar.MINUTE, 0)
                    cal.set(Calendar.SECOND, 0)
                    cal.set(Calendar.MILLISECOND, 0)

                    cal.set(Calendar.DAY_OF_MONTH, dayDateSelected + iter)
                    val dateformat = sdf.format(cal.time)
//                Log.e(TAG," date traité dateformat  $dateformat ")

                    val yeartrait = dateformat.substring(0,4).toString().toInt()
//                Log.e(TAG," date traité yeartrait  $yeartrait ")

                    val monthtrait = dateformat.substring(5,7).toString().toInt()
                    Log.e(TAG," date traité substring(5,7)  ${dateformat.substring(5,7)}")
                    val daytrait = dateformat.substring(8).toString().toInt()
                    Log.e(TAG," date traité substring(8)  ${dateformat.substring(8)}")


                    Log.e(TAG," date traité  = $yeartrait $monthtrait $daytrait")

                    calendarDay = CalendarDay.from(yeartrait,monthtrait-1,daytrait)

                    eventdateAff.add(calendarDay!!)
                }
            }
            while(boolcont)
        }
        vC!!.calendarView.addDecorator(DateDecorator(1, 0, eventdateAff))
    }

    //essai de coroutine
//    fun essai_coroutine(dtdeb: String,dtfin : String){
//
//         GlobalScope.launch(Dispatchers.Main) { // launch coroutine in the main thread
//            essai_api(dtdeb,dtfin)
//        }
//    }
//    fun essai_coroutine(dtdeb: String,dtfin : String) = runBlocking {
//        val job = launch { // launch coroutine in the main thread
//            essai_api(dtdeb,dtfin)
//        }
//        job.join()
//        if(resultat == null) Log.e(TAG,"ERREUR resultat = null")
//        else Log.e(TAG,"OK resultat = $resultat")
////        displayApiTagResult(resultat!!)
//
//    }
    //participe à l'essai de coroutine
//    private fun essai_api(dtdeb: String,dtfin : String){
//
//        val latNe: Double = 48.03764270715237
//        val lngNe: Double = 2.069944763183571
//        val latSw = 47.71525581207238
//        val lngSw = 1.7300552368163835
//        //les recherches sont différentes selon
//        //tous les thèmes sélectionnés (par défaut)
//        //un thème précis selectionné
//        if(themeChoix != ""){
//            //cas un thème précis selectionné
//            //l'interface + fonction "getListeTagSortiesWithTag.fungetListeTagSortiesWithTag" qui émet
//            //des résultats (une liste) est un Observable
//            //l'Observable émet des résultats (des évènements)
//            //le Disposable souscrit à l'Observable pour réagir selon les évènements émis par l'Observable
//            val disposable1 = getListeTagSortiesWithTag.fungetListeTagSortiesWithTag(
//                0,100,themeChoix,dtdeb,dtfin,latNe,lngNe,latSw,lngSw)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    { result ->
//                        trait_bon_result(result)},
//                    { error -> trait_mauvais_result(error)}
//                )
//            disposable1.dispose()
//
//        }
//        else{
//            //cas tous les thèmes sélectionnés
//            val disposable1 = getListeTagSortiesAll.fungetListeTagSortiesAll(
//                0,100,dtdeb,dtfin,latNe,lngNe,latSw,lngSw)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    { result -> trait_bon_result(result)},
//                    { error -> trait_mauvais_result(error)}
//                )
//            disposable1.dispose()
//        }
//
//    }
//    private fun trait_bon_result(result: ListeTagSorties?) {
//        Log.e(TAG,"result = $result")
//        resultat = result
//    }
//    private fun trait_mauvais_result(error: Throwable?) {
//        Log.e(TAG,"error = $error")
//    }


    private fun searchListeTagSorties(dtdeb: String,dtfin : String) {

        val latNe: Double = 48.03764270715237
        val lngNe: Double = 2.069944763183571
        val latSw = 47.71525581207238
        val lngSw = 1.7300552368163835
        //les recherches sont différentes selon
        //tous les thèmes sélectionnés (par défaut)
        //un thème précis selectionné
        if(themeChoix != ""){
            //cas un thème précis selectionné
            //l'interface + fonction "getListeTagSortiesWithTag.fungetListeTagSortiesWithTag" qui émet
            //des résultats (une liste) est un Observable
            //l'Observable émet des résultats (des évènements)
            //le Disposable "souscrit" aux évènements émis par l'Observable
            // ici "result" qui dit ok voici la liste ou "error" pour un plantage

            disposable = getListeTagSortiesWithTag.fungetListeTagSortiesWithTag(0,100,themeChoix,
                dtdeb,dtfin,latNe,lngNe,latSw,lngSw)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> displayApiTagResult(result) },
                    { error -> Toast.makeText(contx, error.message, Toast.LENGTH_LONG).show()
                        Log.e(TAG,"message erreur = $error.message")}
                )
        }
        else{
            //cas tous les thèmes sélectionnés
            disposable = getListeTagSortiesAll.fungetListeTagSortiesAll(
                0,100,dtdeb,dtfin,latNe,lngNe,latSw,lngSw)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> displayApiTagResult(result) },
                    { error -> Toast.makeText(contx, error.message, Toast.LENGTH_LONG).show()
                    Log.e(TAG,"message erreur = $error.message")}
                )
        }
    }

    fun displayApiTagResult(result : ListeTagSorties){
        listEvents.clear()

        if(result.total != 0){

            total = result.total
            limit = result.limit
            var iter = limit
            if(total < limit) iter = total

            for(i in 0..iter-1){
                if(result.events[i].image == null) result.events[i].image = imagedefaut
                if(result.events[i].city == null) result.events[i].city = ""
                if(result.events[i].title == null) result.events[i].title = Title("sans titre")
                if(result.events[i].locationName == null) result.events[i].locationName = "lieu inconnu"
                if(result.events[i].address == null) result.events[i].address = ""
                if(result.events[i].conditions == null) result.events[i].conditions = Conditions("condition inconnue")
                if(result.events[i].firstDate == null) result.events[i].firstDate = ""
                if(result.events[i].canonicalUrl == null) result.events[i].canonicalUrl = "sans lien internet"
                if(result.events[i].longDescription == null) result.events[i].longDescription = LongDescription("")
                if(result.events[i].timings == null) result.events[i].timings = listOf<Timing>()
                if(result.events[i].thumbnail == null) result.events[i].thumbnail = imagedefaut
                if(result.events[i].lastDate == null) result.events[i].lastDate = ""
                if(result.events[i].description == null) result.events[i].description = Description("sans description")

                listEvents.add(result.events[i])
            }
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_main, ListeFragment())
                .addToBackStack("CalendrierFragment")
                .commit()
        }
        else
        {
            if(nbrday == 0)
                Snackbar.make(vC!!,"aucun évènement pour cette date",Snackbar.LENGTH_LONG).show()
            else
                Snackbar.make(vC!!,"aucun évènement pour cette période",Snackbar.LENGTH_LONG).show()

            float_button.visibility = View.VISIBLE
        }


    }

    inner class DateDecorator constructor(cdecor:Int, ccolor: Int, cdates: Collection<CalendarDay>) :
        DayViewDecorator {
        private var dates = HashSet<CalendarDay>()
        var colored  = 0
        init {
            dates = HashSet(cdates)
            colored = cdecor
        }

        override fun shouldDecorate(day: CalendarDay): Boolean {
            return dates.contains(day);         }

        override fun decorate(dayDate: DayViewFacade) {
            dayDate.setBackgroundDrawable(ContextCompat.getDrawable(contx, R.drawable.date_decoration)!!)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e(TAG,"  onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.e(TAG,"  onAttach")
//        yearDateSelected  = 0
//        monthDateSelected  = 0
//        dayDateSelected  = 0
//        hourDateSelected = 0
//        minuteDateSelected  = 0
//        messageEnreg  = ""
//
//        calendar = Calendar.getInstance()
//        dayDateOfDay = calendar!!.get(Calendar.DAY_OF_MONTH)
//        monthDateOfDay = calendar!!.get(Calendar.MONTH) + 1
//        yearDateOfDay = calendar!!.get(Calendar.YEAR)
//        hourDateOfDay = calendar!!.get(Calendar.HOUR_OF_DAY)
//        minuteDateOfDay = calendar!!.get(Calendar.MINUTE)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e(TAG,"  onActivityCreated")
    }
    override fun onPause() {
        super.onPause()
        //sert à stopper l'animation des couleurs du titre si ce n'est pas déjà fait
        if (backgroundColorAnimator != null && !backgroundColorAnimator.isRunning()) {
            // start the animation
            backgroundColorAnimator.stop()
        }

        Log.e(TAG,"  onPause")
    }

    override fun onStop() {
        super.onStop()
        //pour éviter les fuites de mémoire
        Log.e(TAG,"  onStop")
    }
    override fun onStart() {
        super.onStart()
        Log.e(TAG,"  onStart")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e(TAG,"  onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        //pour éviter les fuites de mémoire

        Log.e(TAG,"  onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e(TAG,"  onDetach")
    }

}

