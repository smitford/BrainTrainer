package com.example.braintrainercompose.game.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MathExpressions(val expressionList: List<MathExpression>):
    Parcelable

