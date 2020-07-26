package machine

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val coffeeMachine = CoffeeMachine()
    while (coffeeMachine.currentState != CoffeeMachine.State.OFF) {

        coffeeMachine.process(scanner.nextLine())

    }
}

class CoffeeMachine {

    private var water = 400
    private var milk = 540
    private var beans = 120
    private var cups = 9
    private var money = 550

    enum class State {
        BUY, OFF, ON,
        FILL_WATER, FILL_MILK, FILL_COFFEE, FILL_CUPS
    }

    companion object {
        private const val ESPRESSO_WATER = 250
        private const val ESPRESSO_COFFEE_BEANS = 16
        private const val ESPRESSO_PRICE = 4

        private const val LATTE_WATER = 350
        private const val LATTE_MILK = 75
        private const val LATTE_COFFEE_BEANS = 20
        private const val LATTE_PRICE = 7

        private const val CAPPUCCINO_WATER = 200
        private const val CAPPUCCINO_MILK = 100
        private const val CAPPUCCINO_COFFEE_BEANS = 12
        private const val CAPPUCCINO_PRICE = 6

        private const val WATER_MESSAGE = "Sorry, not enough water!\n\n"
        private const val MILK_MESSAGE = "Sorry, not enough milk!\n\n"
        private const val BEANS_MESSAGE = "Sorry, not enough beans!\n\n"
        private const val CUPS_MESSAGE = "Sorry, not enough cups!\n\n"

        private const val PRINT_SUCCESS_MESSAGE = "I have enough resources, making you a coffee!\n\n"

    }

    var currentState: State = State.ON
        private set

    init {
        printStateInformation()
    }

    fun process(input: String) {
        when (currentState) {
            State.ON -> {
                when (input) {
                    "buy" -> currentState = State.BUY
                    "fill" -> currentState = State.FILL_WATER
                    "take" -> take()
                    "remaining" -> remaining()
                    "exit" -> currentState = State.OFF
                }
            }
            State.FILL_WATER -> fillWater(input)
            State.FILL_MILK -> fillMilk(input)
            State.FILL_COFFEE -> fillCoffeeBeans(input)
            State.FILL_CUPS -> fillCups(input)
            State.BUY -> buy(input)
            else -> {
            }
        }

        printStateInformation()

    }

    private fun fillWater(input: String) {
        water += input.toInt()
        currentState = State.FILL_MILK
    }

    private fun fillMilk(input: String) {
        milk += input.toInt()
        currentState = State.FILL_COFFEE
    }

    private fun fillCoffeeBeans(input: String) {
        beans += input.toInt()
        currentState = State.FILL_CUPS
    }

    private fun fillCups(input: String) {
        cups += input.toInt()
        currentState = State.ON
    }

    private fun printStateInformation() {
        when (this.currentState) {
            State.ON -> print("Write action (buy, fill, take, remaining, exit): ")
            State.BUY -> print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ")
            State.FILL_WATER -> print("Write how many ml of water do you want to add: ")
            State.FILL_MILK -> print("Write how many ml of milk do you want to add: ")
            State.FILL_COFFEE -> print("Write how many grams of coffee beans do you want to add: ")
            State.FILL_CUPS -> print("Write how many disposable cups of coffee do you want to add: \n")
            State.OFF -> {
            }
        }
    }

    private fun take() {
        println("I gave you $money\n")
        money = 0
    }

    private fun remaining() {
        println("The coffee machine has:\n" +
                "$water of water\n" +
                "$milk of milk\n" +
                "$beans of coffee beans\n" +
                "$cups of disposable cups\n" +
                "$money of money\n")
    }


    private fun buy(input: String) {

        print(
                when (input) {
                    "1" -> {
                        currentState = State.ON
                        when {
                            water - ESPRESSO_WATER < 0 -> WATER_MESSAGE
                            beans - ESPRESSO_COFFEE_BEANS < 0 -> BEANS_MESSAGE
                            cups - 1 < 0 -> CUPS_MESSAGE
                            else -> {
                                water -= ESPRESSO_WATER
                                beans -= ESPRESSO_COFFEE_BEANS
                                money += ESPRESSO_PRICE
                                cups -= 1
                                PRINT_SUCCESS_MESSAGE
                            }
                        }
                    }

                    "2" -> {
                        currentState = State.ON
                        when {
                            water - LATTE_WATER < 0 -> WATER_MESSAGE
                            milk - LATTE_MILK < 0 -> MILK_MESSAGE
                            beans - LATTE_COFFEE_BEANS < 0 -> BEANS_MESSAGE
                            cups - 1 < 0 -> CUPS_MESSAGE
                            else -> {
                                water -= LATTE_WATER
                                milk -= LATTE_MILK
                                beans -= LATTE_COFFEE_BEANS
                                money += LATTE_PRICE
                                cups -= 1
                                PRINT_SUCCESS_MESSAGE
                            }
                        }
                    }

                    "3" -> {
                        currentState = State.ON
                        when {
                            water - CAPPUCCINO_WATER < 0 -> WATER_MESSAGE
                            milk - CAPPUCCINO_MILK < 0 -> MILK_MESSAGE
                            beans - CAPPUCCINO_COFFEE_BEANS < 0 -> BEANS_MESSAGE
                            cups - 1 < 0 -> CUPS_MESSAGE
                            else -> {
                                water -= CAPPUCCINO_WATER
                                milk -= CAPPUCCINO_MILK
                                beans -= CAPPUCCINO_COFFEE_BEANS
                                money += CAPPUCCINO_PRICE
                                cups -= 1
                                PRINT_SUCCESS_MESSAGE
                            }
                        }
                    }

                    "back" -> currentState = State.ON

                    else -> {
                        "Error input"
                    }
                }
        )
    }
}
