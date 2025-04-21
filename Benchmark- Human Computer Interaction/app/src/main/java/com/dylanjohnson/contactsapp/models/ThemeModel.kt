package com.dylanjohnson.contactsapp.models

open class ThemeModel {
    // Class properties
    private var _theme: String

    // Default (Empty) Constructor
    constructor() {
        this._theme = "orange"
    }

    // Secondary (Input) Constructor
    constructor( theme: String
    ) {
        this._theme = theme
    }

    // Overwritten toString method to print in specific format
    override fun toString(): String {
        return "\n\tTheme: ${_theme}\n\t)"
    }

    fun getTheme() : String {
        return _theme
    }

    fun setTheme(newTheme : String) {
        if (newTheme == "blue" || newTheme == "green" || newTheme == "orange" || newTheme == "purple" || newTheme == "yellow" || newTheme == "red" || newTheme == "pink" || newTheme == "black" || newTheme == "white") {
            _theme = newTheme
        }
    }
}