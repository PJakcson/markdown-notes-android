package com.notes.marcnmn.pandamarkdownnotes

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.ParcelableSpan
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TextAppearanceSpan
import android.text.style.UnderlineSpan

/*
 * Created by marcneumann on 28.12.17.
 */

class MarkdownFormatter(private val ctx: Context) {

    val handlers = listOf<MdElementHandler>(BoldHandler(), UnderlineHandler(), HeadlineHandler(ctx))
    var appliedStyles = mutableListOf<ParcelableSpan>()

    fun formatText(s: Spannable) {
        for (span in appliedStyles) {
            s.removeSpan(span)
        }
        appliedStyles.clear()

        for (h in handlers) {
            appliedStyles.addAll(h.formatText(s))
        }
    }
}

interface MdElementHandler {
    fun formatText(s: Spannable): List<ParcelableSpan>
}

class BoldHandler : MdElementHandler {
    private val rxp = Regex("\\*(.*?)\\*")

    override fun formatText(s: Spannable): List<ParcelableSpan> {
        val spans = mutableListOf<ParcelableSpan>()
        for (m in rxp.findAll(s)) {
            val bspan = StyleSpan(Typeface.BOLD)
            s.setSpan(bspan, m.range.start + 1, m.range.endInclusive, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spans.add(bspan)

            spans.addAll(easeStartEnd(s, m.range.start, m.range.endInclusive))
        }
        return spans
    }
}

class UnderlineHandler : MdElementHandler {
    private val rxp = Regex("_(.*?)_")

    override fun formatText(s: Spannable): List<ParcelableSpan> {
        val spans = mutableListOf<ParcelableSpan>()
        for (m in rxp.findAll(s)) {
            val uspan = UnderlineSpan()
            s.setSpan(uspan, m.range.start + 1, m.range.endInclusive, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spans.add(uspan)

            spans.addAll(easeStartEnd(s, m.range.start, m.range.endInclusive))

        }
        return spans
    }
}

class HeadlineHandler(private val ctx: Context) : MdElementHandler {
    private val rxp = Regex("^# (.*)")

    override fun formatText(s: Spannable): List<ParcelableSpan> {
        val spans = mutableListOf<ParcelableSpan>()
        for (m in rxp.findAll(s)) {
            val span = TextAppearanceSpan(ctx, R.style.MarkdownH1)
            s.setSpan(span, m.range.start + 1, m.range.endInclusive, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spans.add(span)

        }
        return spans
    }
}

fun easeStartEnd(s: Spannable, start: Int, end: Int): List<ForegroundColorSpan> {
    val cspan1 = ForegroundColorSpan(Color.LTGRAY)
    s.setSpan(cspan1, start, start + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

    val cspan2 = ForegroundColorSpan(Color.LTGRAY)
    s.setSpan(cspan2, end, end + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

    return listOf(cspan1, cspan2)
}