package com.example.myunittest

class MainViewModel(private val cuboIdModel: CuboIdModel) {
    fun getCircumference() = cuboIdModel.getCircumference()
    fun getSurfaceArea() = cuboIdModel.getSurfaceArea()
    fun getVolume() = cuboIdModel.getVolume()

    fun save(w: Double, l:Double, h:Double) {
        cuboIdModel.save(w, l, h)
    }
}