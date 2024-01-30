package com.example.wafflely.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TodoInfo {
    var todoContent: String = ""
//    var isSucces: Boolean = true
    var insertDate: String = ""

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}