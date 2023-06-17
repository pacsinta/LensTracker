package com.cstcompany.lenstracker.model

interface ILensData {
    fun changeLens()
    fun getDays(side: EyeSide): Int
}