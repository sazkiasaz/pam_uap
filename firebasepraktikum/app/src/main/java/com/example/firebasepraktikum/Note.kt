package com.example.firebasepraktikum

class Note {
    var title: String? = null
    var description: String? = null
    constructor() {}
    constructor(title: String?, description: String?) {
        this.title = title
        this.description = description
    }
}