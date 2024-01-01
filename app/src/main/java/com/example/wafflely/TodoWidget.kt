package com.example.wafflely

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.view.View
import android.widget.RemoteViews
import android.widget.EditText
import android.widget.TextView
import com.example.wafflely.databinding.TodoWidgetBinding


/**
 * Implementation of App Widget functionality.
 */
class TodoWidget : AppWidgetProvider() {
    private lateinit var binding: TodoWidgetBinding
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }

//        var todo_edit:EditText = binding.todoEdit
        var todo_text:TextView = binding.todoText
        var todo_button = binding.todoButton
        todo_text.visibility = View.INVISIBLE

        todo_button.setOnClickListener(View.OnClickListener {
//            var text:String = todo_edit.text.toString()
//            todo_text.setText(text)
//            todo_edit.visibility = View.INVISIBLE
            todo_text.visibility = View.VISIBLE
        })
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = ""
//     Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.todo_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}