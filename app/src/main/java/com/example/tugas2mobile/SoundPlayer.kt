package com.example.tugas2mobile

object SoundPlayer {
    fun click() {
        try {
            val tg = android.media.ToneGenerator(android.media.AudioManager.STREAM_MUSIC, 80)
            tg.startTone(android.media.ToneGenerator.TONE_PROP_BEEP, 90)
        } catch (e: Exception) {
        }
    }

    fun success(context: android.content.Context) {
        try {
            val mp = android.media.MediaPlayer.create(context, R.raw.success)
            mp?.setOnCompletionListener { it.release() }
            mp?.start()
        } catch (e: Exception) {
        }
    }
}
