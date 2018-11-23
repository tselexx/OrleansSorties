package com.tselexx.orleanssorties

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.tselexx.orleanssorties.Common_Util_functions.Companion.REQUEST_PERMISSIONS
import com.tselexx.orleanssorties.Common_Util_functions.Companion.backgroundColorAnimator
import com.tselexx.orleanssorties.Common_Util_functions.Companion.boolBackPressed
import com.tselexx.orleanssorties.Common_Util_functions.Companion.boolFirstTime
import com.tselexx.orleanssorties.Common_Util_functions.Companion.booltagNavChoix
import com.tselexx.orleanssorties.Common_Util_functions.Companion.calendar
import com.tselexx.orleanssorties.Common_Util_functions.Companion.calendarDay
import com.tselexx.orleanssorties.Common_Util_functions.Companion.calendarsauv
import com.tselexx.orleanssorties.Common_Util_functions.Companion.contx
import com.tselexx.orleanssorties.Common_Util_functions.Companion.datedeb
import com.tselexx.orleanssorties.Common_Util_functions.Companion.datefin
import com.tselexx.orleanssorties.Common_Util_functions.Companion.dayDateOfDay
import com.tselexx.orleanssorties.Common_Util_functions.Companion.dayDateSelected
import com.tselexx.orleanssorties.Common_Util_functions.Companion.float_button
import com.tselexx.orleanssorties.Common_Util_functions.Companion.hourDateOfDay
import com.tselexx.orleanssorties.Common_Util_functions.Companion.minuteDateOfDay
import com.tselexx.orleanssorties.Common_Util_functions.Companion.monthDateOfDay
import com.tselexx.orleanssorties.Common_Util_functions.Companion.monthDateSelected
import com.tselexx.orleanssorties.Common_Util_functions.Companion.nbrday
import com.tselexx.orleanssorties.Common_Util_functions.Companion.themeChoix
import com.tselexx.orleanssorties.Common_Util_functions.Companion.themeChoixSauv
import com.tselexx.orleanssorties.Common_Util_functions.Companion.titre_général
import com.tselexx.orleanssorties.Common_Util_functions.Companion.vC
import com.tselexx.orleanssorties.Common_Util_functions.Companion.vL
import com.tselexx.orleanssorties.Common_Util_functions.Companion.vS
import com.tselexx.orleanssorties.Common_Util_functions.Companion.yearDateOfDay
import com.tselexx.orleanssorties.Common_Util_functions.Companion.yearDateSelected
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val TAG = "TAG MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e(TAG,"  onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
//        supportActionBar!!.setDisplayShowTitleEnabled(false)
//
//        toolbar!!.setLogo(R.drawable.logo_metropole)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.setItemIconTintList(null)


        calendar = Calendar.getInstance()
        dayDateOfDay = calendar.get(Calendar.DAY_OF_MONTH)
        monthDateOfDay = calendar.get(Calendar.MONTH) + 1
        yearDateOfDay = calendar.get(Calendar.YEAR)
        hourDateOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        minuteDateOfDay = calendar.get(Calendar.MINUTE)
        contx = this
        
        titre_général = findViewById(R.id.main_title)
        float_button = findViewById(R.id.main_fab)

        nbrday = 0
        dayDateSelected = dayDateOfDay
        monthDateSelected = monthDateOfDay
        yearDateSelected = yearDateOfDay
        calendarsauv = CalendarDay.today()

        testPermission()

        afficheCalendrierFragment()
    }

    override fun onBackPressed() {
        Log.e(TAG,"  onBackPressed")

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // DateOfday DateSelected et  calendarsauv sont initialisés à la date du jour dans onCreate
        // DateSelected et  calendarsauv sont dynamiquement modifiés dans CalendrierFragment par la sélection d'une date affichée
        when (item.itemId) {
            R.id.zero_events -> {
                nbrday = 0
                trait_affichage_date(calendarsauv)
                return true
            }

            R.id.four_events -> {
                nbrday = 3
                trait_affichage_date(calendarsauv)
                return true
            }
            R.id.week_events -> {
                nbrday = 6
                trait_affichage_date(calendarsauv)

                return true
            }
            R.id.ten_events -> {
                nbrday = 9
                trait_affichage_date(calendarsauv)

                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        // Handle navigation view item clicks here.
        when (item.itemId) {
//            R.id.agenda -> {
//                trait_FragmentManager()
//                tagNavChoix = ""
//                booltagNavChoix = false
//                trait_agenda_titre()
//                trait_affichage_date(calendarsauv)
//

//            }

            R.id.nav_tout -> {
                themeChoix = ""
                trait_FragmentManager()

                booltagNavChoix = true
                titre_général.text = "Tous les thèmes"
                trait_affichage_date(calendarsauv)

            }
            R.id.nav_balade -> {

                themeChoix = "balade-decouverte-visite"
                trait_FragmentManager()

                booltagNavChoix = true
                titre_général.text = "Thème : balade-découverte-visite"
                trait_affichage_date(calendarsauv)
            }
            R.id.nav_conference -> {

                themeChoix = "conference-rencontre-debat"
                trait_FragmentManager()

                booltagNavChoix = true
                titre_général.text = "Thème : conférence-rencontre-débat"
                trait_affichage_date(calendarsauv)

            }
            R.id.nav_exposition -> {

                themeChoix = "exposition"
                trait_FragmentManager()

                booltagNavChoix = true
                titre_général.text = "Thème : " +themeChoix
                trait_affichage_date(calendarsauv)

            }
            R.id.nav_fete -> {

                themeChoix = "fete-salon-marche"
                trait_FragmentManager()

                booltagNavChoix = true
                titre_général.text = "Thème : fête-salon-marché"
                trait_affichage_date(calendarsauv)

            }
            R.id.nav_musique -> {

                themeChoix = "musique"
                trait_FragmentManager()

                booltagNavChoix = true
                titre_général.text = "Thème : " +themeChoix
                trait_affichage_date(calendarsauv)

            }
            R.id.nav_projection -> {

                themeChoix = "projection-cinema"
                trait_FragmentManager()

                booltagNavChoix = true
                titre_général.text = "Thème : projection-cinéma"
                trait_affichage_date(calendarsauv)

            }
            R.id.nav_spectacle -> {

                themeChoix = "spectacle"
                trait_FragmentManager()

                booltagNavChoix = true
                titre_général.text = "Thème : " +themeChoix
                trait_affichage_date(calendarsauv)

            }
            R.id.nav_sport -> {

                themeChoix = "sport"
                trait_FragmentManager()

                booltagNavChoix = true
                titre_général.text = "Thème : " +themeChoix
                trait_affichage_date(calendarsauv)

            }
            R.id.nav_stage -> {

                themeChoix = "stage-atelier-jeu"
                trait_FragmentManager()

                booltagNavChoix = true
                titre_général.text = "Thème : " +themeChoix
                trait_affichage_date(calendarsauv)

            }
            R.id.nav_jeune -> {

                themeChoix = "jeune-public"
                trait_FragmentManager()

                booltagNavChoix = true
                titre_général.text = "Thème : " +themeChoix
                trait_affichage_date(calendarsauv)

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun trait_affichage_date(calendarsauv: CalendarDay?) {
        val toto =  CalendrierFragment()
        toto.traitement_date_selected(Common_Util_functions.calendarsauv!!)
    }

//    private fun trait_agenda_titre() {
//        titre_général?.text = "Agenda du "
//        if(yearDateSelected == 0){
//            val cal = Calendar.getInstance()
//            titre_général?.text = "Agenda du ${cal.get(Calendar.DAY_OF_MONTH)} / " +
//                    "${cal.get(Calendar.MONTH) +1} / ${cal.get(Calendar.YEAR)}"
//
//        }
//        else{
//            titre_général?.text = "Agenda du $dayDateSelected / " +
//                    "$monthDateSelected / $yearDateSelected"
//        }
//    }

    private fun trait_FragmentManager() {
        if(themeChoix != themeChoixSauv){
            //si modification du thème sélectionné dans le menu drawer
            //abandon de l'action en cours et forçage du retour à l'affichage du calendrier

            themeChoixSauv = themeChoix
            supportFragmentManager
                .popBackStackImmediate("CalendrierFragment",POP_BACK_STACK_INCLUSIVE)

        }
    }


    private fun afficheCalendrierFragment() {
        themeChoix = ""
        booltagNavChoix = false

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container_main, CalendrierFragment())
            .commit()
    }

    private fun testPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
            if (((ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)) !=
                        PackageManager.PERMISSION_GRANTED)) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                    Snackbar.make(findViewById(android.R.id.content),
                        "Please Grant Permissions",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE"
                    ) {
                        ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.INTERNET),
                            REQUEST_PERMISSIONS)
                    }.show()
                } else {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.INTERNET), REQUEST_PERMISSIONS)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                if (!(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Snackbar.make(findViewById(android.R.id.content), "Enable Permissions from settings",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE"
                    ) {
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        intent.addCategory(Intent.CATEGORY_DEFAULT)
                        intent.data = Uri.parse("package:" + packageName)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                        startActivity(intent)
                    }.show()
                }
                return
            }
        }
    }

    override fun onDestroy() {
        Log.e(TAG,"  onDestroy")
        themeChoix = ""
        themeChoixSauv  = "init"

        booltagNavChoix = false
        boolFirstTime = true
        nbrday = 0



        dayDateOfDay  = 0
        monthDateOfDay  = 0
        yearDateOfDay  = 0
        hourDateOfDay  = 0
        minuteDateOfDay  = 0

        yearDateSelected  = 0
        monthDateSelected  = 0
        dayDateSelected  = 0

        datedeb = ""
        datefin = ""
        calendarDay  = null
        calendarsauv  = null



        boolBackPressed = false

//        calendar  = null
//        contx  = null


        super.onDestroy()
    }

    override fun onStart() {
        Log.e(TAG,"  onStart")

        super.onStart()
    }

    override fun onResume() {
        Log.e(TAG,"  onResume")

        super.onResume()
    }

    override fun onPause() {
        Log.e(TAG,"  onPause")

        super.onPause()
    }
}
