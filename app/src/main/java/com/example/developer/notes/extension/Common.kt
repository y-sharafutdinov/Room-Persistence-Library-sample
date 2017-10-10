package com.example.developer.notes.extension

import java.lang.ref.WeakReference


fun <T> T.weak() = WeakReference(this)