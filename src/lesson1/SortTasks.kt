@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.File
import java.io.FileWriter
import java.lang.StringBuilder
import kotlin.math.abs

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
 * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
 *
 * Пример:
 *
 * 01:15:19 PM
 * 07:26:57 AM
 * 10:00:03 AM
 * 07:56:14 PM
 * 01:15:19 PM
 * 12:40:31 AM
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 12:40:31 AM
 * 07:26:57 AM
 * 10:00:03 AM
 * 01:15:19 PM
 * 01:15:19 PM
 * 07:56:14 PM
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 * время: O(N*logN)
 * память: O(N)
 */
fun sortTimes(inputName: String, outputName: String) {
    val sortedFile = StringBuilder()
    val amTime = mutableListOf<String>()
    val pmTime = mutableListOf<String>()
    for (timeLine in File(inputName).readLines()) {
        require(Regex("""^(0[1-9]|1[0-2]):([0-5]\d):([0-5]\d)\s[AP]M$""").matches(timeLine))
        var time = timeLine
        if (time.startsWith("12")) time = time.replaceFirst("12", "00")
        if (time.endsWith("AM")) amTime.add(time)
        else pmTime.add(time)
    }
    amTime.sort()
    pmTime.sort()
    for (timeLine in amTime) {
        if (timeLine.startsWith("00")) sortedFile.append(timeLine.replaceFirst("00", "12"))
        else sortedFile.append(timeLine)
        sortedFile.append(System.lineSeparator())
    }
    for (timeLine in pmTime) {
        if (timeLine.startsWith("00")) sortedFile.append(timeLine.replaceFirst("00", "12"))
        else sortedFile.append(timeLine)
        sortedFile.append(System.lineSeparator())
    }
    File(outputName).writeText(sortedFile.toString())
}

/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 * Время:O(N*logN)
 * Память:O(N)
 */
fun sortAddresses(inputName: String, outputName: String) {
    val addressBook = mutableMapOf<String, MutableSet<String>>()
    for (line in File(inputName).readLines()) {
        require(Regex("""^[\wа-яА-ЯЁё]+\s[\wа-яА-ЯЁё]+\s-\s[\wа-яА-ЯЁё-]+\s\d+$""").matches(line))
        val list = line.split(" - ")
        if (!addressBook.containsKey(list[1])) addressBook[list[1]] = mutableSetOf()
        addressBook[list[1]]!!.add(list[0])
    }
    val sb = StringBuilder()
    addressBook.keys.sortedWith(
        compareBy(
            { it.substringBeforeLast(" ") },
            { it.substringAfterLast(" ").toInt() }
        )
    )
        .forEach {
            sb.append(it).append(" - ")
            sb.append(addressBook[it]!!.sorted().joinToString(separator = ", "))
            sb.append(System.lineSeparator())
        }

    File(outputName).writeText(sb.toString())
}

/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 *
 * Память: O(N)
 * Время: O(N)
 */
fun sortTemperatures(inputName: String, outputName: String) {
    val inputTemperature = mutableListOf<Int>()

    var max = 5001
    var min = -2731

    for (line in File(inputName).readLines()) {
        inputTemperature.add(line.replace(".", "").toInt())
        if (inputTemperature[inputTemperature.size - 1] < min || min == -2731)
            min = inputTemperature[inputTemperature.size - 1]
        if (inputTemperature[inputTemperature.size - 1] > max || max == 5001)
            max = inputTemperature[inputTemperature.size - 1]
    }

    val sortTemp = IntArray(max - min + 1)

    for (i in 0..(max - min)) sortTemp[i] = 0

    for (temp in inputTemperature) sortTemp[temp - min]++

    File(outputName).bufferedWriter().use {
        for (i in 0..(max - min)) {
            for (j in 0 until sortTemp[i]) {
                it.write(((i + min) / 10.0).toString())
                it.newLine()
            }
        }
    }
}


/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 */
fun sortSequence(inputName: String, outputName: String) {
    TODO()
}

/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    TODO()
}

