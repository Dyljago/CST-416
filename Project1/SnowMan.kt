package Project1

import java.time.LocalDate

class SnowMan ( val id: Int, var name: String, var hasTopHat: Boolean, var dateOfBirth: LocalDate, var weightKG: Float ) {
    override fun toString(): String {
        return "\n\tSnowMan $id(\n\t Name: '$name'\n\t Has Top Hat: $hasTopHat\n\t Date Born: $dateOfBirth\n\t Weight: $weightKG\n\t)"
    }
}