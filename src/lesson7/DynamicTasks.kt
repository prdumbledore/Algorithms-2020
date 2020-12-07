@file:Suppress("UNUSED_PARAMETER")

package lesson7

import java.lang.StringBuilder
import java.util.*
import kotlin.math.max

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
 * При сравнении подстрок, регистр символов *имеет* значение.
 *
 * Время: O(first.length * second.length)
 * Память: O(first.length * second.length)
 */
fun longestCommonSubSequence(first: String, second: String): String {
    if (first == second) return first
    var firstLen = first.length
    var secondLen = second.length
    if (firstLen == 0 || secondLen == 0) return ""

    val table = Array(firstLen + 1) { IntArray(secondLen + 1) { 0 } }

    for (row in 1..firstLen) {
        for (col in 1..secondLen) {
            if (first[row - 1] == second[col - 1]) table[row][col] = table[row - 1][col - 1] + 1
            else table[row][col] = max(table[row - 1][col], table[row][col - 1])
        }
    }

    val sb = StringBuilder()

    while (firstLen > 0 && secondLen > 0) {
        when {
            first[firstLen - 1] == second[secondLen - 1] -> {
                sb.append(first[firstLen - 1])
                firstLen--
                secondLen--
            }
            table[firstLen - 1][secondLen] > table[firstLen][secondLen - 1] -> firstLen--
            else -> secondLen--
        }
    }

    return sb.reverse().toString()
}

/**
 * Наибольшая возрастающая подпоследовательность
 * Сложная
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 *
 * Время: O(N * logN)
 * Память: O(N)
 */
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    val size = list.size

    if (size == 0) return emptyList()
    if (size == 1) return list

    val maxLengths = IntArray(size)
    val binarySearch = IntArray(size)
    val result = mutableListOf<Int>()

    var length = 0
    for (num in list.indices) {
        val number = list[num]
        var j = Arrays.binarySearch(binarySearch, 0, length, number)
        if (j < 0) {
            j = -(j + 1)
        }

        binarySearch[j] = number
        if (j == length) {
            length++
        }
        maxLengths[num] = j + 1
    }


    result.add(list[maxLengths.indexOf(length)])
    length--
    while (length != 0) {
        for (i in maxLengths.indices) {
            if (maxLengths[i] == length && list[i] < result.last()) {
                result.add(list[i])
                length--
                break
            }
        }
    }

    return result.reversed()
}


/**
 * Самый короткий маршрут на прямоугольном поле.
 * Средняя
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
fun shortestPathOnField(inputName: String): Int {
    TODO()
}

// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5