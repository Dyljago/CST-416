package Project1

import java.time.LocalDate
import java.time.format.DateTimeParseException

fun main(args: Array<String>) {
    println("Welcome to snowman manager.")
    val man1 = SnowMan(1, "Frosty", false, LocalDate.parse("1952-12-25"), 88.1F)
    // create a new list and add Frosty as the default starter.
    var snowMenList = mutableListOf<SnowMan>()
        snowMenList.add(man1)
    var currentId = 2
    var userChoice = 0;
    while (userChoice != 9) {
        println("Please choose an option")
        println(
            "1. Add a new snowman to the inventory\n" +
                    "2. Display all snowmen in the inventory. \n" +
                    "3. Show a single snowman given its id number\n" +
                    "4. Search for a snowman by (partial) name\n" +
                    "5. Delete a snowman\n" +
                    "6. Change a snowman \n" +
                    "9. Quit the program\n"
        )
        userChoice = Integer.parseInt(readLine())
        when (userChoice) {
            1 -> {
                // add a snowman
                var name = ""
                var topHat = false
                var valid = false
                var dateBorn = LocalDate.now()
                var weight = 0.0F
                while (!valid) {
                    print("Enter the snowman's name: ")
                    val inputName = readLine()
                    if (inputName != null) {
                        name = inputName
                        valid = true
                    } else {
                        println("Invalid input. Please enter a name.")
                    }
                }
                valid = false
                while (!valid) {
                    print("Does $name have a top hat? ('true' or 'false'): ")
                    val inputBool = readLine()?.toBooleanStrictOrNull()
                    if (inputBool != null) {
                        topHat = inputBool
                        valid = true
                    } else {
                        println("Invalid input. Please enter true or false.")
                    }
                }
                valid = false
                while (!valid) {
                    print("What does $name weigh in KG?: ")
                    val inputWeight = readLine()?.toFloatOrNull()
                    if (inputWeight != null) {
                        weight = inputWeight
                        valid = true
                    } else {
                        println("Invalid input. Please enter a valid weight.")
                    }
                }
                var man = SnowMan(currentId++, name, topHat, dateBorn, weight)
                snowMenList.add(man)
            }

            2 -> {
                // print all snowmen
                println("You choose option 2, print all snowman")
                snowMenList.forEach( {
                    println("Here is a snowman: $it")
                })
            }

            3 -> {
                // get an id number and print the snowman who matches
                if (snowMenList.size != 0) {
                    var valid = false
                    var idNum = 1
                    while (!valid) {
                        print("Enter the id of the snowman you wish to search up: ")
                        val inputID = readLine()?.toIntOrNull()
                        if (inputID != null) {
                            val result = snowMenList.indexOfFirst { it.id == inputID }
                            if (result != -1) {
                                idNum = result
                                valid = true
                            } else {
                                println("No snowman found with ID: $inputID")
                            }
                        } else {
                            println("Invalid input. Please enter a valid ID number.")
                        }
                    }
                    println(snowMenList[idNum])
                } else {
                    println("No SnowMen in the list!")
                }
            }

            4 -> {
                if (snowMenList.size != 0) {
                    // Show the results of a partial name search.
                    print("Enter a name for a snowman you wish to search: ")
                    val inputSubString = readLine()
                    if (!inputSubString.isNullOrEmpty()) {
                        // Filter the list for strings containing the substring
                        val results = snowMenList.filter { it.name.contains(inputSubString, ignoreCase = true) }
                        println("SnowMen with that name: $results")
                    } else {
                        println("Invalid input. Please enter a valid name.")
                    }
                } else {
                    println("No SnowMen in the list!")
                }
            }

            5 -> {
                if (snowMenList.size != 0) {
                    // Delete a snowman
                    var valid = false
                    var idNum = 0
                    while (!valid) {
                        print("Enter the id of the snowman you wish to delete: ")
                        val inputID = readLine()?.toIntOrNull()
                        if (inputID != null) {
                            val result = snowMenList.indexOfFirst { it.id == inputID }
                            if (result != null) {
                                idNum = result
                                valid = true
                            } else {
                                println("No snowman found with ID: $inputID")
                            }
                        } else {
                            println("Invalid input. Please enter a valid ID number.")
                        }
                    }
                    snowMenList.removeAt(idNum)
                } else {
                    println("No SnowMen in the list!")
                }
            }

            6 -> {
                if (snowMenList.size != 0) {
                    // get an id number and print the snowman who matches
                    var valid = false
                    var idNum = 1
                    while (!valid) {
                        print("Enter the id of the snowman you wish to change: ")
                        val inputID = readLine()?.toIntOrNull()
                        if (inputID != null) {
                            val result = snowMenList.indexOfFirst { it.id == inputID }
                            if (result != null) {
                                idNum = result
                                valid = true
                            } else {
                                println("No snowman found with ID: $inputID")
                            }
                        } else {
                            println("Invalid input. Please enter a valid ID number.")
                        }
                    }
                    // Update a snowman
                    var name = ""
                    var topHat = false
                    valid = false
                    var dateBorn = LocalDate.now()
                    var weight = 0.0F
                    while (!valid) {
                        print("What should ${snowMenList[idNum].name}'s new name be: ")
                        val inputName = readLine()
                        if (inputName != null) {
                            name = inputName
                            valid = true
                        } else {
                            println("Invalid input. Please enter a name.")
                        }
                    }
                    valid = false
                    while (!valid) {
                        print("Should $name have a top hat? ('true' or 'false'): ")
                        val inputBool = readLine()?.toBooleanStrictOrNull()
                        if (inputBool != null) {
                            topHat = inputBool
                            valid = true
                        } else {
                            println("Invalid input. Please enter true or false.")
                        }
                    }
                    valid = false
                    while (!valid) {
                        print("When was $name born (format of yyyy-mm-dd)?: ")
                        val inputDate = readLine()
                        if (inputDate != null) {
                            try {
                                dateBorn = LocalDate.parse(inputDate)
                                valid = true // If parsing is successful, return true
                            } catch (e: DateTimeParseException) {
                                println("Invalid input. Please enter a valid date.")
                            }
                        } else {
                            println("Invalid input. Please enter a valid date.")
                        }
                    }
                    valid = false
                    while (!valid) {
                        print("What should $name weigh in KG?: ")
                        val inputWeight = readLine()?.toFloatOrNull()
                        if (inputWeight != null) {
                            weight = inputWeight
                            valid = true
                        } else {
                            println("Invalid input. Please enter a valid weight.")
                        }
                    }
                    snowMenList[idNum].name = name
                    snowMenList[idNum].hasTopHat = topHat
                    snowMenList[idNum].dateOfBirth = dateBorn
                    snowMenList[idNum].weightKG = weight
                    println(snowMenList[idNum])
                } else {
                    println("No SnowMen in the list!")
                }
            }

        }
    }
}