package com.tselexx.orleanssorties.api.bytag.generatedjson

data class CustomEvent(
   var image: String,
   var city: String,
   var title: Title,      //
   var locationName: String,
   var address: String,
   var conditions: Conditions, //
   var firstDate: String,
   var canonicalUrl: String,
   var longDescription: LongDescription,  //
   var timings: List<Timing>, //
   var thumbnail: String,
   var lastDate: String,
   var description: Description, //
   var registration: List<Registration>, //
   var range: Range //


   )