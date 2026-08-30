package dev.rohitverma882.quotee.presentation.features.quotes

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import dev.rohitverma882.quotee.domain.quotes.model.Quote

/**
 * Utility functions for common quote actions.
 */

fun Quote.copyToClipboard(context: Context) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Quote", "“$content” — $author")
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "Quote copied to clipboard", Toast.LENGTH_SHORT).show()
}

fun Quote.share(context: Context) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "“$content” — $author")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}
