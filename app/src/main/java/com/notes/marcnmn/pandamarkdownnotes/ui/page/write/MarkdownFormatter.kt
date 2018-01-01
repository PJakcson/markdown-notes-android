package com.notes.marcnmn.pandamarkdownnotes.ui.page.write

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.style.*
import com.notes.marcnmn.pandamarkdownnotes.R
import javax.inject.Inject
import javax.inject.Singleton


/*
 * Created by marcneumann on 28.12.17.
 */

@Singleton
class MarkdownFormatter @Inject constructor(ctx: Context) {

    private val handlers = listOf(
            BoldHandler(ctx),
            UnderlineHandler(ctx),
            HeadlineHandler(ctx),
            ItalicHandler(ctx),
            StrikethroughHandler(ctx),
            DrawableHandler(ctx),
            StdHandler(ctx)
    )

    private var appliedStyles = mutableListOf<CharacterStyle>()

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

interface MdElementHandler {
    fun formatText(s: Spannable): List<CharacterStyle>
}

class StdHandler(private val ctx: Context) : MdElementHandler {
    private val rxp = Regex("^[^#+\\-]+(.*?)", RegexOption.MULTILINE)

    override fun formatText(s: Spannable): List<CharacterStyle> {
        val spans = mutableListOf<CharacterStyle>()
        for (m in rxp.findAll(s)) {
            s.setSpan(LeadingMarginSpan.Standard(100), m.range.start, m.range.endInclusive, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }
        return spans
    }
}


class BoldHandler(private val ctx: Context) : MdElementHandler {
    private val rxp = Regex("\\*(.*?)\\*")

    override fun formatText(s: Spannable): List<CharacterStyle> {
        val spans = mutableListOf<CharacterStyle>()
        for (m in rxp.findAll(s)) {
            val bspan = StyleSpan(Typeface.BOLD)
            s.setSpan(bspan, m.range.start + 1, m.range.endInclusive, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spans.add(bspan)
            spans.addAll(easeStartEnd(ctx, s, m.range.start, m.range.endInclusive))
        }
        return spans
    }
}

class ItalicHandler(private val ctx: Context) : MdElementHandler {
    private val rxp = Regex("/(.*?)/")

    override fun formatText(s: Spannable): List<CharacterStyle> {
        val spans = mutableListOf<CharacterStyle>()
        for (m in rxp.findAll(s)) {
            val bspan = StyleSpan(Typeface.ITALIC)
            s.setSpan(bspan, m.range.start + 1, m.range.endInclusive, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spans.add(bspan)
            spans.addAll(easeStartEnd(ctx, s, m.range.start, m.range.endInclusive))
        }
        return spans
    }
}

class UnderlineHandler(private val ctx: Context) : MdElementHandler {
    private val rxp = Regex("_(.*?)_")

    override fun formatText(s: Spannable): List<CharacterStyle> {
        val spans = mutableListOf<CharacterStyle>()
        for (m in rxp.findAll(s)) {
            val uspan = UnderlineSpan()
            s.setSpan(uspan, m.range.start + 1, m.range.endInclusive, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spans.add(uspan)
            spans.addAll(easeStartEnd(ctx, s, m.range.start, m.range.endInclusive))

        }
        return spans
    }
}

class StrikethroughHandler(private val ctx: Context) : MdElementHandler {
    private val rxp = Regex("-(.*?)-")

    override fun formatText(s: Spannable): List<CharacterStyle> {
        val spans = mutableListOf<CharacterStyle>()
        for (m in rxp.findAll(s)) {
            val span = StrikethroughSpan()
            s.setSpan(span, m.range.start + 1, m.range.endInclusive, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spans.add(span)
            spans.addAll(easeStartEnd(ctx, s, m.range.start, m.range.endInclusive))
        }
        return spans
    }
}

class DrawableHandler(private val ctx: Context) : MdElementHandler {
    private val rxp = Regex("^(- )", RegexOption.MULTILINE)
    private val rxpChecked = Regex("^(\\+ )", RegexOption.MULTILINE)

    override fun formatText(s: Spannable): List<CharacterStyle> {
        val spans = mutableListOf<CharacterStyle>()
        for (m in rxp.findAll(s)) {
            val span = ImageSpan(ctx, R.drawable.ic_check_box_outline_blank_black_24dp)
            s.setSpan(span, m.range.start, m.range.endInclusive, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spans.add(span)
        }
        for (m in rxpChecked.findAll(s)) {
            val span = ImageSpan(ctx, R.drawable.ic_check_box_black_24dp)
            s.setSpan(span, m.range.start, m.range.endInclusive, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spans.add(span)
        }
        return spans
    }
}

class HeadlineHandler(private val ctx: Context) : MdElementHandler {
    private val rxp = Regex("^(#+) (.*)", RegexOption.MULTILINE)

    override fun formatText(s: Spannable): List<CharacterStyle> {
        val spans = mutableListOf<CharacterStyle>()
        for (m in rxp.findAll(s)) {
            val all = m.groups[0]
            val prefix = m.groups[1]
            val text = m.groups[2]
            if (all == null || prefix == null || text == null) continue
            spans.addAll(easeRange(ctx, s, prefix.range))

            val hStyle = when (prefix.value.length) {
                1 -> R.style.MarkdownH1
                2 -> R.style.MarkdownH2
                3 -> R.style.MarkdownH3
                4 -> R.style.MarkdownH4
                else -> R.style.MarkdownHx
            }
            if (text.range.isEmpty()) continue
            val span = TextAppearanceSpan(ctx, hStyle)
            s.setSpan(span, text.range.start, text.range.endInclusive, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            spans.add(span)
        }
        return spans
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