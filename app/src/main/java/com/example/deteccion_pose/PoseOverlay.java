package com.example.deteccion_pose;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.google.mlkit.vision.pose.PoseLandmark;

import java.util.List;

public class PoseOverlay extends View {

    private List<PoseLandmark> landmarks;
    private Paint dotPaint;
    private Paint linePaint;

    private int previewWidth = 480;  // Puedes ajustar a la resolución real
    private int previewHeight = 640;

    public PoseOverlay(Context context) {
        super(context);

        dotPaint = new Paint();
        dotPaint.setColor(Color.GREEN);
        dotPaint.setStyle(Paint.Style.FILL);
        dotPaint.setStrokeWidth(10f);

        linePaint = new Paint();
        linePaint.setColor(Color.GREEN);
        linePaint.setStrokeWidth(6f);
        linePaint.setStyle(Paint.Style.STROKE);
    }

    public void setLandmarks(List<PoseLandmark> landmarks) {
        this.landmarks = landmarks;
        invalidate();
    }

    // Si quieres actualizar resolución de preview (opcional)
    public void setPreviewSize(int width, int height) {
        this.previewWidth = width;
        this.previewHeight = height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (landmarks == null || landmarks.isEmpty()) return;

        float scaleX = getWidth() / (float) previewWidth;
        float scaleY = getHeight() / (float) previewHeight;

        for (PoseLandmark landmark : landmarks) {
            float x = landmark.getPosition().x * scaleX;
            float y = landmark.getPosition().y * scaleY;
            canvas.drawCircle(x, y, 8f, dotPaint);
        }

        drawLine(canvas, PoseLandmark.LEFT_SHOULDER, PoseLandmark.RIGHT_SHOULDER, scaleX, scaleY);
        drawLine(canvas, PoseLandmark.LEFT_SHOULDER, PoseLandmark.LEFT_ELBOW, scaleX, scaleY);
        drawLine(canvas, PoseLandmark.LEFT_ELBOW, PoseLandmark.LEFT_WRIST, scaleX, scaleY);
        drawLine(canvas, PoseLandmark.RIGHT_SHOULDER, PoseLandmark.RIGHT_ELBOW, scaleX, scaleY);
        drawLine(canvas, PoseLandmark.RIGHT_ELBOW, PoseLandmark.RIGHT_WRIST, scaleX, scaleY);
        drawLine(canvas, PoseLandmark.LEFT_SHOULDER, PoseLandmark.LEFT_HIP, scaleX, scaleY);
        drawLine(canvas, PoseLandmark.RIGHT_SHOULDER, PoseLandmark.RIGHT_HIP, scaleX, scaleY);
        drawLine(canvas, PoseLandmark.LEFT_HIP, PoseLandmark.RIGHT_HIP, scaleX, scaleY);
        drawLine(canvas, PoseLandmark.LEFT_HIP, PoseLandmark.LEFT_KNEE, scaleX, scaleY);
        drawLine(canvas, PoseLandmark.LEFT_KNEE, PoseLandmark.LEFT_ANKLE, scaleX, scaleY);
        drawLine(canvas, PoseLandmark.RIGHT_HIP, PoseLandmark.RIGHT_KNEE, scaleX, scaleY);
        drawLine(canvas, PoseLandmark.RIGHT_KNEE, PoseLandmark.RIGHT_ANKLE, scaleX, scaleY);
    }

    private void drawLine(Canvas canvas, int startType, int endType, float scaleX, float scaleY) {
        PoseLandmark start = getLandmark(startType);
        PoseLandmark end = getLandmark(endType);

        if (start != null && end != null) {
            canvas.drawLine(
                    start.getPosition().x * scaleX, start.getPosition().y * scaleY,
                    end.getPosition().x * scaleX, end.getPosition().y * scaleY,
                    linePaint
            );
        }
    }

    private PoseLandmark getLandmark(int type) {
        for (PoseLandmark landmark : landmarks) {
            if (landmark.getLandmarkType() == type) {
                return landmark;
            }
        }
        return null;
    }
}
