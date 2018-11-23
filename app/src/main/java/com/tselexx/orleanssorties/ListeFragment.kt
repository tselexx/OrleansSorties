package com.tselexx.orleanssorties
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.tselexx.orleanssorties.Common_Util_functions.Companion.boolBackPressed
import com.tselexx.orleanssorties.Common_Util_functions.Companion.contx
import com.tselexx.orleanssorties.Common_Util_functions.Companion.listEvents
import com.tselexx.orleanssorties.Common_Util_functions.Companion.listmodelAffItem
import com.tselexx.orleanssorties.Common_Util_functions.Companion.modelAffItemSlide
import com.tselexx.orleanssorties.Common_Util_functions.Companion.vL
import com.tselexx.orleanssorties.adapter.ListeAdapter
import com.tselexx.orleanssorties.interfacage.IOnBackPressed
import com.tselexx.orleanssorties.model.ModelAffItem
import kotlinx.android.synthetic.main.regular_liste_fragment.view.*


class ListeFragment : Fragment(), IOnBackPressed {
    val TAG = "TAG ListeFragment"

    lateinit var  myadapter: ListeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e(TAG,"  onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e(TAG,"  onCreateView")
        vL = inflater.inflate(R.layout.regular_liste_fragment, container, false)

        setAdapter()
        return vL
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.e(TAG,"  onAttach")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e(TAG,"  onActivityCreated")
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG,"  onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG,"  onPause")
    }

    override fun onStop() {
        super.onStop()
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
        Log.e(TAG,"  onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e(TAG,"  onDetach")
    }
    private fun setAdapter() {
        listmodelAffItem.clear()
        traitListeEvent()

        myadapter = ListeAdapter(listmodelAffItem) { it ->

            modelAffItemSlide = ModelAffItem(
                it.image,
                it.city,
                it.title,
                it.placename,
                it.address,
                it.pricingInfo,
                it.dateStart,
                it.link,
                it.freeText,
                it.timetable,
                it.imageThumb,
                it.dateEnd,
                it.description,
                it.registration,
                it.range
            )
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_main, SlideFragment())
                .addToBackStack("ListeRegularFragment")
                .commit()


        }


        vL!!.liste_date_recyclerview.layoutManager = LinearLayoutManager(contx, LinearLayout.VERTICAL,false)
        vL!!.liste_date_recyclerview.layoutManager.scrollToPosition(0)
        vL!!.liste_date_recyclerview.adapter = myadapter

    }

    private fun traitListeEvent() {
        listEvents.forEach{
            val modelAffItem = ModelAffItem(
                it.image,
                it.city,
                it.title.fr,
                it.locationName,
                it.address,
                it.conditions.fr,
                it.firstDate,
                it.canonicalUrl,
                it.longDescription.fr,
                it.timings,
                it.thumbnail,
                it.lastDate,
                it.description.fr,
                it.registration,
                it.range.fr
            )
            listmodelAffItem.add(modelAffItem)
        }
    }




    override fun onBackPressed(): Boolean {
        Log.e(TAG,"  onBackPressed")

        boolBackPressed = true
        return boolBackPressed
    }


}