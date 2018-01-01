package com.notes.marcnmn.pandamarkdownnotes.ui.page.write.format

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.style.*
import com.notes.marcnmn.pandamarkdownnotes.R
import javax.inject.Inject

/*
 * Created by marcneumann on 01.01.18.
 */

interface MdElementHandler {
    fun formatText(s: Spannable): List<CharacterStyle>
}

class StdHandler @Inject constructor() : MdElementHandler {
    private val rxp = Regex("^[^#+\\-]+(.*?)", RegexOption.MULTILINE)

    override fun formatText(s: Spannable): List<CharacterStyle> {
        val spans = mutableListOf<CharacterStyle>()
        for (m in rxp.findAll(s)) {
            s.setSpan(LeadingMarginSpan.Standard(100), m.range.start, m.range.endInclusive, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }
        return spans
    }
}

class BoldHandler @Inject constructor(private val ctx: Context) : MdElementHandler {
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

class ItalicHandler @Inject constructor(private val ctx: Context) : MdElementHandler {
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

class UnderlineHandler @Inject constructor(private val ctx: Context) : MdElementHandler {
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

class StrikethroughHandler @Inject constructor(private val ctx: Context) : MdElementHandler {
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

class DrawableHandler @Inject constructor(private val ctx: Context) : MdElementHandler {
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

class HeadlineHandler @Inject constructor(private val ctx: Context) : MdElementHandler {
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