package com.tselexx.orleanssorties.model

import com.tselexx.orleanssorties.api.bytag.generatedjson.Registration
import com.tselexx.orleanssorties.api.bytag.generatedjson.Timing

data class ModelAffItem(
    var image : String,
    var city : String,
    var title : String,
    var placename : String,
    var address : String,
    var pricingInfo: String,
    var dateStart: String,
    var link: String,
    var freeText: String,
    var timetable : List<Timing>,
    var imageThumb: String,
    var dateEnd: String,
    var description: String,
    var registration: List<Registration>,
    var range: String
)

