package com.example.carpetcleaner.data
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.carpetcleaner.R
object ServiceStore {

    private val services = mutableListOf<Service>(
        Service(1L,"Carpet Cleaning", "High powered truck mounted hot water extraction technology, proprietary green cleaning solutions and awesome service technicians!\n" +
                "\n" +
                "Two step residue-free cleaning process giving you move-in ready results quickly!",
            99,
            listOf(
                SubService("Pet Treatment", 25),
                SubService("Spot Treatment", 10)
            ),
            R.drawable.carpet_program
            ),
        Service(2L,"Repair & Restoration",
                "Ripples in the carpet? We can stretch them. Holes/worn fibers, poor seams, transition strip required?",
                0,
            listOf(
                SubService("Seam/Edge Fix", 10),
                SubService("Carpet Stretching", 20),
                SubService("Patch", 30),
                SubService("Carpet Install", 300)
            ),
            R.drawable.carpet_repair
        ),
        Service(3L,"Color Restoration",
            "High traffic areas, tough stains, bleach and even sun faded carpets can be restored with our system! Clean Included!",
            150,
            null,
            R.drawable.color_restore
        ),
        Service(4L,
            "Pressure Washing",
            "There are serious threats to the building facade and curb appeal all posed by unsightly grime.",
            0,
            listOf(
                SubService("Deck",20),
                SubService("Patio", 20)
            ),
            R.drawable.pressure_washing
        ),
        Service(5L,
            "Hard Surface Cleaning",
            "Our hard surface cleaning formulas are specially designed to aggressively yet safely loosen dirt.",
            50,
            null,
            R.drawable.hard_surface
        )
    )

    private val __services: MutableLiveData<List<Service>> = MutableLiveData()

    val _services: LiveData<List<Service>>
        get() = __services

    init {
        __services.value = services.toList()
    }

    fun getAllServices() = _services

    fun getServiceById(id: Long): Service = _services.value!!.find { it.id == id }!!
}