package com.cstcompany.lenstracker.model

interface ILensData {
    fun changeLens()
    fun getDays(side: EyeSide): Int
    fun getLensData(): ArrayList<LensData>
    fun saveLensData(lensData: LensData)
}