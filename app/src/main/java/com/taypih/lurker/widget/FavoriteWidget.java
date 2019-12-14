package com.taypih.lurker.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.taypih.lurker.R;
import com.taypih.lurker.repository.Repository;
import com.taypih.lurker.ui.MainActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Implementation of App Widget functionality.
 */
public class FavoriteWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_widget);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            String widgetText = String.valueOf(Repository.getInstance(context).getCount());
            views.setTextViewText(R.id.appwidget_text, widgetText);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        });

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, false);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Here the basic operations the remote view can do.
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

