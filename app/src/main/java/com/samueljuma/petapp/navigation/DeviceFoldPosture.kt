package com.samueljuma.petapp.navigation

import android.graphics.Rect
import androidx.window.layout.FoldingFeature
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed interface DeviceFoldPosture {
    data class BookPosture(val hingePosition: Rect) : DeviceFoldPosture

    data class SeparatingPosture(
        val hingePosition: Rect,
        val orientation: FoldingFeature.Orientation,
    ) : DeviceFoldPosture

    object NormalPosture : DeviceFoldPosture
}

@OptIn(ExperimentalContracts::class)
fun isBookPosture(foldingFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldingFeature != null) }
    return foldingFeature?.state == FoldingFeature.State.HALF_OPENED &&
        foldingFeature.orientation == FoldingFeature.Orientation.VERTICAL
}

@OptIn(ExperimentalContracts::class)
fun isSeparatingPosture(foldingFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldingFeature != null) }
    return foldingFeature?.state == FoldingFeature.State.FLAT &&
        foldingFeature.isSeparating
}