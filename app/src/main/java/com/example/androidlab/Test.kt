package com.example.androidlab

fun main() {
    var x = 0
    var sum1 = 0
    while (x < 10) {
        sum1 += ++x
    }
    print(sum1)
}