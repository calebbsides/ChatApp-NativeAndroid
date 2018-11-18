package calebbsides.com.chatapp

class Message (val id: String, val data: String, val user: String) {
    constructor() : this("", "", "") {}
}