package cinema

import java.lang.Exception

fun main() {

    fun getTotalPlaces(): Array<Int> {
        println(
            "Enter the number of rows:"
        )
        print("> ")
        val r = readLine()!!.toInt()
        println(
            "Enter the number of seats in each row:"
        )
        print("> ")
        val s = readLine()!!.toInt()
        return arrayOf(r, s)
    }

    //=================================================
    fun menu(): String {
        println(
            "\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit"
        )
        print("> ")
        return readLine()!!
    }

    fun hallArray(rows: Int, seats: Int): Array<CharArray> {
        return Array(rows) { CharArray(seats) { 'S' } }
    }

    //=================================================
    fun showCinemaHall(cinema: Array<CharArray>, rows: Int, seats: Int) {
        println("\nCinema:")
        val seatsNumber = Array(seats) { e -> "${e + 1} " }
        print("  ")
        for (f in 0 until seats) {
            print(seatsNumber[f])
        }
        print("\n")
        for (i in 0 until rows) {
            for (j in 0 until seats) {
                if (j == 0) print("${i + 1} ")
                print(cinema[i][j] + " ")
            }
            print("\n")
        }
        print("\n")
    }

    //================================================

    fun buyTicket(): Array<Int> {
        println("\nEnter a row number:")
        print("> ")
        val rB = readLine()!!.toInt()
        println("Enter a seat number in that row:")
        print("> ")
        val sB = readLine()!!.toInt()
        return arrayOf(rB, sB)
    }

    fun checkPlace(cinema: Array<CharArray>, rB: Int, sB: Int): Int {
        if (rB < 1 || rB > cinema.size || sB < 1 || sB > cinema[0].size) throw ArrayIndexOutOfBoundsException()
        else if (cinema[rB - 1][sB - 1] == 'B') throw Exception()
        else return 0
    }

    //================================================
    fun ticketPrice(r: Int, s: Int, rB: Int) {
        val seats = r * s
        println(
            if (seats <= 60 || rB <= r / 2) "\nTicket price: \$10\n"
            else "\nTicket price: \$8\n"
        )
    }

    fun showStatistics(cinema: Array<CharArray>) {
        var sumBought = 0
        val totalPlaces = cinema.size * cinema[0].size
        var currIncome = 0

        cinema.indices.forEach { rows ->
            cinema[0].indices.forEach { seats ->
                if (cinema[rows][seats] == 'B') {
                    sumBought += 1
                    currIncome += if (totalPlaces <= 60 || rows < cinema.size / 2) 10
                    else 8
                }
            }
        }
        val percentage = sumBought.toDouble() / totalPlaces * 100.0

        val totalIncome = if (totalPlaces <= 60) {
            cinema.size * cinema[0].size * 10
        } else {
            (cinema.size / 2 * 10 + (cinema.size - cinema.size / 2) * 8) * cinema[0].size
        }

        println(
            "\nNumber of purchased tickets: $sumBought\n" +
                    "Percentage: %.2f".format(percentage) + "%\n" +
                    "Current income: \$$currIncome\n" +
                    "Total income: \$$totalIncome"
        )
    }
    val (r, s) = getTotalPlaces()
    val cinema = hallArray(r, s)

    do {
        val b = menu()

        when (b) {
            "1" -> {
                hallArray(r, s)
                showCinemaHall(cinema, r, s)
            }
            "2" -> do {
                val (rB, sB) = buyTicket()
                try {
                    checkPlace(cinema, rB, sB)
                } catch (e: ArrayIndexOutOfBoundsException) {
                    println("\nWrong input!")
                    continue
                } catch (e: Exception) {
                    println("\nThat ticket has already been purchased!")
                    continue
                }
                cinema[rB - 1][sB - 1] = 'B'
                ticketPrice(r, s, rB)
                break
            } while (true)
            "3" -> {
                showStatistics(cinema)
            }
            "0" -> "0"
            else -> println("Wrong choice")
        }
    } while (b != "0")
}
