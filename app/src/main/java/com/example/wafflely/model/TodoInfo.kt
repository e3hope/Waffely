package com.example.wafflely.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TodoInfo {
    var content: String = ""
    var is_succes: Boolean = true
    var insert_date: String = ""

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}