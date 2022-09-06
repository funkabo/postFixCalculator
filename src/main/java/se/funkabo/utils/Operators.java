package se.funkabo.utils;

/**
 * ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
 * ┃                            Exercise 1                        ┃
 * ┃      title: Postfix Calculator                               ┃
 * ┃    version: 1.0                                              ┃
 * ┃     author: Federico Sanders <federico.sanders@hotmail.com>  ┃
 * ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
 */

public enum Operators {
    ADDITION(1),
    SUBTRACTION(1),
    MULTIPLICATION(2),
    DIVISION(2);

    Operators(int p) { precedence = p; }

    final int precedence;
}
