package com.example.wafflely

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.compose.material3.AlertDialog
import com.example.wafflely.adapter.TodoAdapter
import com.example.wafflely.databinding.ActivityListBinding
import com.example.wafflely.databinding.DialogEditBinding
import com.example.wafflely.databinding.ListItemTodoBinding
import com.example.wafflely.model.TodoDatabase
import com.example.wafflely.model.TodoInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private lateinit var todoAdapter: TodoAdapter
    private  lateinit var roomDatabase: TodoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 어댑터 인스턴스 생성
        todoAdapter = TodoAdapter()

        // 리사이클리뷰에 어댑터 세팅
        binding.rvTodo.adapter = todoAdapter

        // 룸 데이터베이스 초기화
        roomDatabase = TodoDatabase.getInstance(applicationContext)!!

        // 전체 데이터 load
        CoroutineScope(Dispatchers.IO).launch {
            val listTodo = roomDatabase.todoDao().getAllReadData() as ArrayList<TodoInfo>
            for (todoItem in listTodo){
                todoAdapter.addListItem(todoItem)
            }
            // UI Thread에서 처리
            runOnUiThread {
                todoAdapter.notifyDataSetChanged()
            }
        }

        // 작성하기 버튼 클릭
        binding.btnWrite.setOnClickListener{
            val bindingDialog = DialogEditBinding.inflate(LayoutInflater.from(binding.root.context), binding.root, false)

            AlertDialog.Builder(this)
                .setTitle("todo남기기")
                .setView(bindingDialog.root)
                .setPositiveButton("작성완료",DialogInterface.OnClickListener { DialogInterface, i ->
                    val todoItem = TodoInfo()
                    todoItem.todoContent = bindingDialog.etMemo.text.toString()
                    todoItem.insertDate = SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(Date())
                    todoAdapter.addListItem(todoItem)   // 어댑터의 전역변수 arrylist 아이템 추가 메소드
                    CoroutineScope(Dispatchers.IO).launch {
                        roomDatabase.todoDao().insertTodoData(todoItem) // 데이터베이스 또한 클래스 데이터 삽입
                    }
                    runOnUiThread {
                        todoAdapter.notifyDataSetChanged()  // 리스트 새로고침
                    }
                })
                .setNegativeButton("취소",DialogInterface.OnClickListener{ DialogInterface, i ->
                })
                .show()
        }
    }
}