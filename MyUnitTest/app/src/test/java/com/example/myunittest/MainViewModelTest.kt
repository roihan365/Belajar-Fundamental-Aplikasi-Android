package com.example.myunittest

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

internal class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var cuboIdModel: CuboIdModel

    private val dummyWidth = 12.0
    private val dummyHeight = 7.0
    private val dummyLength = 6.0
    private val dummyVolume = 504.0

    private val dummyCircumference = 100.0
    private val dummySurfaceArea = 396.0

    @Before
    fun before() {
        cuboIdModel = mock(CuboIdModel::class.java)
        mainViewModel = MainViewModel(cuboIdModel)
    }

    @Test
    fun testVolume() {
        cuboIdModel = CuboIdModel()
        mainViewModel = MainViewModel(cuboIdModel)
        mainViewModel.save(dummyHeight, dummyLength, dummyWidth)
        val volume = mainViewModel.getVolume()
        assertEquals(dummyVolume, volume, 0.0001)
    }
    @Test
    fun testCircumference() {
        cuboIdModel = CuboIdModel()
        mainViewModel = MainViewModel(cuboIdModel)
        mainViewModel.save(dummyHeight, dummyLength, dummyWidth)
        val circumference = mainViewModel.getCircumference()
        assertEquals(dummyCircumference, circumference, 0.0001)
    }
    @Test
    fun testSurfaceArea() {
        cuboIdModel = CuboIdModel()
        mainViewModel = MainViewModel(cuboIdModel)
        mainViewModel.save(dummyHeight, dummyLength, dummyWidth)
        val surfaceArea = mainViewModel.getSurfaceArea()
        assertEquals(dummySurfaceArea, surfaceArea, 0.0001)
    }

    @Test
    fun testMockCircumference() {
        `when`(mainViewModel.getCircumference()).thenReturn(dummyCircumference)
        val circumference = mainViewModel.getCircumference()
        verify(cuboIdModel).getCircumference()
        assertEquals(dummyCircumference, circumference, 0.0001)
    }
    @Test
fun testMockVolume() {
        `when`(mainViewModel.getVolume()).thenReturn(dummyVolume)
        val volume = mainViewModel.getVolume()
        verify(cuboIdModel).getVolume()
        assertEquals(dummyVolume, volume, 0.0001)
    }
    @Test
fun testMockSurfaceArea() {
        `when`(mainViewModel.getSurfaceArea()).thenReturn(dummySurfaceArea)
        val surfaceArea = mainViewModel.getSurfaceArea()
        verify(cuboIdModel).getSurfaceArea()
        assertEquals(dummySurfaceArea, surfaceArea, 0.0001)
    }

}