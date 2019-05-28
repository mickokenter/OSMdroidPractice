package com.carloudy.osmdroidpractice;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;

import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.views.MapView;

public class MapViewMirrored extends MapView {

    public MapViewMirrored(Context context, MapTileProviderBase tileProvider, Handler tileRequestCompleteHandler, AttributeSet attrs) {
        super(context, tileProvider, tileRequestCompleteHandler, attrs);
    }

    public MapViewMirrored(Context context, MapTileProviderBase tileProvider, Handler tileRequestCompleteHandler, AttributeSet attrs, boolean hardwareAccelerated) {
        super(context, tileProvider, tileRequestCompleteHandler, attrs, hardwareAccelerated);
    }

    public MapViewMirrored(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapViewMirrored(Context context) {
        super(context);
    }

    public MapViewMirrored(Context context, MapTileProviderBase aTileProvider) {
        super(context, aTileProvider);
    }

    public MapViewMirrored(Context context, MapTileProviderBase aTileProvider, Handler tileRequestCompleteHandler) {
        super(context, aTileProvider, tileRequestCompleteHandler);
    }

    @Override
    public void onDraw(final Canvas canvas) {
        canvas.translate(getWidth(), 0);
        canvas.scale(-1, 1);
        super.onDraw(canvas);
    }
}
