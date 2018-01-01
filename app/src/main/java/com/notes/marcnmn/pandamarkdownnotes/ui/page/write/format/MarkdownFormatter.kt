package com.notes.marcnmn.pandamarkdownnotes.ui.page.write.format

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.text.style.LeadingMarginSpan
import javax.inject.Inject


/*
 * Created by marcneumann on 28.12.17.
 */

class MarkdownFormatter @Inject constructor(
        bold: BoldHandler,
        underline: UnderlineHandler,
        headline: HeadlineHandler,
        italic: ItalicHandler,
        strike: StrikethroughHandler,
        check: DrawableHandler,
        std: StdHandler
) {
    private val handlers = listOf(bold, underline, headline, italic, strike, check, std)
    private val appliedStyles = mutableListOf<CharacterStyle>()

    fun formatText(s: Spannable) {
        for (span in appliedStyles) {
            s.removeSpan(span)
        }
        appliedStyles.clear()
        for (span in s.getSpans(0, s.length - 1, LeadingMarginSpan.Standard::class.java)) {
            s.removeSpan(span)
        }

        for (h in handlers) {
            appliedStyles.addAll(h.formatText(s))
        }
    }
}

fun easeStartEnd(ctx: Context, s: Spannable, start: Int, end: Int): List<CharacterStyle> {
    return easeRange(ctx, s, IntRange(start, start + 1)).plus(easeRange(ctx, s, IntRange(end, end + 1)))
}

fun easeRange(ctx: Context, s: Spannable, r: IntRange): List<CharacterStyle> {
    val span = ForegroundColorSpan(Color.LTGRAY)
    s.setSpan(span, r.start, r.endInclusive + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    return listOf(span)
}