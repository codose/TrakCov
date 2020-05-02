package com.harzzy.trakcov.utils

import android.graphics.Color
import android.graphics.Color.rgb
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.charts.Pie
import com.anychart.core.cartesian.series.Line
import com.anychart.data.Mapping
import com.anychart.data.Set
import com.anychart.enums.*
import com.anychart.graphics.vector.SolidFill
import com.anychart.graphics.vector.Stroke
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.harzzy.trakcov.R
import com.harzzy.trakcov.api.response.international.InternationalResponseItem
import com.harzzy.trakcov.api.response.trend.Timeline

/* Created by : Osemwingie Oshodin (codose)
*
* Date : 30th April, 2020
*
* */


object ChartSetup {
    fun setUpPieChart(active : Int, deaths : Int, recovered : Int) : Pie {
        val entries: MutableList<DataEntry> = ArrayList()
        entries.add(ValueDataEntry("Active", active.toFloat()))
        entries.add(ValueDataEntry("Deaths", deaths.toFloat()))
        entries.add(ValueDataEntry("Recovered", recovered.toFloat()))

        val pie = AnyChart.pie()
        pie.data(entries)

        pie.labels().position("outside")

        pie.palette().itemAt(0, SolidFill("#ffa000",100))
        pie.palette().itemAt(1, SolidFill("#c62828",100))
        pie.palette().itemAt(2, SolidFill("#00600f",100))


        pie.legend()
            .position("bottom")
            .itemsLayout(LegendLayout.HORIZONTAL_EXPANDABLE)
            .align(Align.CENTER)
        return pie
    }

    fun setUpLineChart(timeline: Timeline) : Cartesian{
        val cartesian: Cartesian = AnyChart.line()
        cartesian.animation(true)

        cartesian.padding(10.0, 20.0, 5.0, 20.0)

        cartesian.crosshair().enabled(true)
        cartesian.crosshair()
            .yLabel(true) // TODO ystroke
            .yStroke(null as Stroke?, null, null, null as String?, null as String?)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)

        cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)

        val seriesData: MutableList<DataEntry> = ArrayList()

        val cases = timeline.cases.toList().sortedBy { (_, value) -> value}.toMap().iterator()
        val recoveries = timeline.recovered.toList().sortedBy { (_, value) -> value}.toMap().iterator()
        val deaths = timeline.deaths.toList().sortedBy { (_, value) -> value}.toMap().iterator()

        while (cases.hasNext() && recoveries.hasNext() && deaths.hasNext()){
            val c = cases.next()
            val r = recoveries.next()
            val d = deaths.next()
            seriesData.add(CustomDataEntry(cases.next().key, c.value,r.value,d.value))
        }



        val set = Set.instantiate()
        set.data(seriesData)
        val series1Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value' }")
        val series2Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value2' }")
        val series3Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value3' }")

        val series1: Line = cartesian.line(series1Mapping)
        series1.name("Cases")
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)
        series1.color("#1976d2")

        val series2: Line = cartesian.line(series2Mapping)
        series2.name("Recoveries")
        series2.hovered().markers().enabled(true)
        series2.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series2.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)
        series2.color("#00600f")


        val series3: Line = cartesian.line(series3Mapping)
        series3.name("Deaths")
        series3.hovered().markers().enabled(true)
        series3.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series3.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)
        series3.color("#c62828")

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)
        cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)

        return cartesian
    }
    private class CustomDataEntry internal constructor(
        x: String?,
        value: Number?,
        value2: Number?,
        value3: Number?
    ) :
        ValueDataEntry(x, value) {
        init {
            setValue("value2", value2)
            setValue("value3", value3)
        }
    }
}