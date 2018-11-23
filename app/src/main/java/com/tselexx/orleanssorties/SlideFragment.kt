package com.tselexx.orleanssorties
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tselexx.orleanssorties.Common_Util_functions.Companion.boolBackPressed
import com.tselexx.orleanssorties.Common_Util_functions.Companion.contx
import com.tselexx.orleanssorties.Common_Util_functions.Companion.imagedefaut
import com.tselexx.orleanssorties.Common_Util_functions.Companion.modelAffItemSlide
import com.tselexx.orleanssorties.Common_Util_functions.Companion.vS
import com.tselexx.orleanssorties.adapter.SlideAdapter
import com.tselexx.orleanssorties.interfacage.IOnBackPressed
import kotlinx.android.synthetic.main.regular_slide_fragment.view.*


class SlideFragment : Fragment(), IOnBackPressed {
    val TAG = "TAG SlideFragment"

    lateinit var  myadapter: SlideAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e(TAG,"  onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e(TAG,"  onCreateView")
        vS = inflater.inflate(R.layout.regular_slide_fragment, container, false)

        var imageTemp =""
        if(modelAffItemSlide.imageThumb == ""){
            imageTemp = imagedefaut
        }
        else{
            imageTemp = modelAffItemSlide.imageThumb
        }

        val option = RequestOptions.centerInsideTransform()
        Glide.with(contx).load(imageTemp).apply(option).into(vS!!.image_slide)

        vS!!.city_slide.setText(modelAffItemSlide.city)
        vS!!.title_slide.setText(modelAffItemSlide.title)
        setAdapter()
        return vS
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

        myadapter = SlideAdapter()
        vS!!.viewpager_slide.adapter = myadapter

    }
    override fun onBackPressed(): Boolean {
        boolBackPressed = true
        return boolBackPressed
    }


}