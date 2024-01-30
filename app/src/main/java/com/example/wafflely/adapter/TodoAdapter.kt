package com.example.wafflely.adapter

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.compose.material3.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.wafflely.databinding.ActivityListBinding
import com.example.wafflely.databinding.DialogEditBinding
import com.example.wafflely.databinding.ListItemTodoBinding
import com.example.wafflely.model.TodoInfo
import java.text.SimpleDateFormat
import java.util.Date

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private var listTodo : ArrayList<TodoInfo> = ArrayList()

    fun addListItem(todoItem: TodoInfo){
        listTodo.add(0, todoItem)
    }

    inner class TodoViewHolder(private var binding: ListItemTodoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(todoItem: TodoInfo){
            // 리스트 뷰 데이터를 UI에 연동
            binding.tvContent.setText(todoItem.todoContent)
            binding.tvDate.setText(todoItem.insertDate)

            // 리스트 삭제 버튼 클릭연동
            binding.btnRemove.setOnClickListener {

                AlertDialog.Builder(binding.root.context)
                    .setTitle("[주의]")
                    .setMessage("제거하시면 데이터는 복구되지 않습니다.\n정말 제거하시겠습니까?")
                    .setPositiveButton("제거",DialogInterface.OnClickListener{ dialogInterface, i ->
                        listTodo.remove(todoItem)
                        notifyDataSetChanged() // 새로고침
                        // 토스트 팝업 메세지 표시
                        Toast.makeText(binding.root.context,"제거되었습니다",Toast.LENGTH_SHORT).show()
                    })
                    .setNegativeButton("취소",DialogInterface.OnClickListener{ dialogInterface, i ->

                    })
                    .show()
            }

            // 리스트 수정 클릭 연동
            binding.root.setOnClickListener{
                val bindingDialog = DialogEditBinding.inflate(LayoutInflater.from(binding.root.context), binding.root, false)
                bindingDialog.etMemo.setText(todoItem.todoContent)

                AlertDialog.Builder(binding.root.context)
                    .setTitle("todo남기기")
                    .setView(bindingDialog.root)
                    .setPositiveButton("작성완료",DialogInterface.OnClickListener { DialogInterface, i ->
                        todoItem.todoContent = bindingDialog.etMemo.text.toString()
                        todoItem.insertDate = SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(Date())
                        // array list 수정
                        listTodo.set(adapterPosition, todoItem)
                        notifyDataSetChanged()  // 리스트 새로고침
                    })
                    .setNegativeButton("취소",DialogInterface.OnClickListener{ DialogInterface, i ->


                    })
                    .show()
            }
        }
    }

    //뷰 홀더가 생성됨.(각 리스트 아이템 1개씩 구성될때마다 이 오버라이드 메소드가 호출됨.)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.TodoViewHolder {
        val binding = ListItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    // 뷰홀더가 바인딩 (결합) 이루어질 때 해줘야 될 처리들을 구현.
    override fun onBindViewHolder(holder: TodoAdapter.TodoViewHolder, position: Int) {
        holder.bind(listTodo[position])
    }

    // 리스트 총 개수
    override fun getItemCount(): Int {
        return listTodo.size
    }

}