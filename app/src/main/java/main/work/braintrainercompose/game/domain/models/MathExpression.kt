package main.work.braintrainercompose.game.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MathExpression(
    val id: Int,
    val firstNumber: Int,
    val secondNumber: Int,
    val answer: Int
):
    Parcelable

