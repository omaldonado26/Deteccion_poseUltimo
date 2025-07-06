package com.example.deteccion_pose;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.google.mlkit.vision.pose.PoseLandmark;

import java.util.List;

public class PoseOverlay extends View {

    private List<PoseLandmark> landmarks;
    private final Paint dotPaint;
    private final Paint linePaint;
    private final Paint textPaint;
    private int previewWidth = 480;
    private int previewHeight = 640;
    private boolean isBadPosture = false;

    public PoseOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);

        dotPaint = new Paint();
        dotPaint.setStyle(Paint.Style.FILL);
        dotPaint.setStrokeWidth(12f);

        linePaint = new Paint();
        linePaint.setStrokeWidth(6f);
        linePaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(48f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);
        textPaint.setAntiAlias(true);
    }

    public void setLandmarks(List<PoseLandmark> landmarks) {
        this.landmarks = landmarks;
        invalidate();
    }

    public void setPreviewSize(int width, int height) {
        this.previewWidth = width;
        this.previewHeight = height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (landmarks == null || landmarks.isEmpty()) return;

        float viewWidth = getWidth();
        float viewHeight = getHeight();

        float scaleX = viewWidth / (float) previewWidth;
        float scaleY = viewHeight / (float) previewHeight;
        float scale = Math.min(scaleX, scaleY); // mantener proporción

        float offsetX = (viewWidth - (previewWidth * scale)) / 2f;
        float offsetY = (viewHeight - (previewHeight * scale)) / 2f;

        isBadPosture = detectBadPosture();

        int color = isBadPosture ? Color.RED : Color.GREEN;
        dotPaint.setColor(color);
        linePaint.setColor(color);
        textPaint.setColor(color);

        for (PoseLandmark landmark : landmarks) {
            float x = landmark.getPosition().x * scale + offsetX;
            float y = landmark.getPosition().y * scale + offsetY;
            canvas.drawCircle(x, y, 8f, dotPaint);
        }

        drawLine(canvas, PoseLandmark.LEFT_SHOULDER, PoseLandmark.RIGHT_SHOULDER, scale, offsetX, offsetY);
        drawLine(canvas, PoseLandmark.LEFT_SHOULDER, PoseLandmark.LEFT_ELBOW, scale, offsetX, offsetY);
        drawLine(canvas, PoseLandmark.LEFT_ELBOW, PoseLandmark.LEFT_WRIST, scale, offsetX, offsetY);
        drawLine(canvas, PoseLandmark.RIGHT_SHOULDER, PoseLandmark.RIGHT_ELBOW, scale, offsetX, offsetY);
        drawLine(canvas, PoseLandmark.RIGHT_ELBOW, PoseLandmark.RIGHT_WRIST, scale, offsetX, offsetY);
        drawLine(canvas, PoseLandmark.LEFT_SHOULDER, PoseLandmark.LEFT_HIP, scale, offsetX, offsetY);
        drawLine(canvas, PoseLandmark.RIGHT_SHOULDER, PoseLandmark.RIGHT_HIP, scale, offsetX, offsetY);
        drawLine(canvas, PoseLandmark.LEFT_HIP, PoseLandmark.RIGHT_HIP, scale, offsetX, offsetY);
        drawLine(canvas, PoseLandmark.LEFT_HIP, PoseLandmark.LEFT_KNEE, scale, offsetX, offsetY);
        drawLine(canvas, PoseLandmark.LEFT_KNEE, PoseLandmark.LEFT_ANKLE, scale, offsetX, offsetY);
        drawLine(canvas, PoseLandmark.RIGHT_HIP, PoseLandmark.RIGHT_KNEE, scale, offsetX, offsetY);
        drawLine(canvas, PoseLandmark.RIGHT_KNEE, PoseLandmark.RIGHT_ANKLE, scale, offsetX, offsetY);

        String mensaje = isBadPosture ? "Mala postura" : "Buena postura";
        canvas.drawText(mensaje, viewWidth / 2f, 80f, textPaint);
    }

    private void drawLine(Canvas canvas, int startType, int endType, float scale, float offsetX, float offsetY) {
        PoseLandmark start = getLandmarkByType(startType);
        PoseLandmark end = getLandmarkByType(endType);

        if (start != null && end != null) {
            float startX = start.getPosition().x * scale + offsetX;
            float startY = start.getPosition().y * scale + offsetY;
            float endX = end.getPosition().x * scale + offsetX;
            float endY = end.getPosition().y * scale + offsetY;

            canvas.drawLine(startX, startY, endX, endY, linePaint);
        }
    }

    private PoseLandmark getLandmarkByType(int type) {
        if (landmarks == null) return null;
        for (PoseLandmark landmark : landmarks) {
            if (landmark.getLandmarkType() == type) return landmark;
        }
        return null;
    }

    private boolean detectBadPosture() {
        PoseLandmark leftShoulder = getLandmarkByType(PoseLandmark.LEFT_SHOULDER);
        PoseLandmark rightShoulder = getLandmarkByType(PoseLandmark.RIGHT_SHOULDER);
        PoseLandmark leftHip = getLandmarkByType(PoseLandmark.LEFT_HIP);
        PoseLandmark rightHip = getLandmarkByType(PoseLandmark.RIGHT_HIP);

        if (leftShoulder == null || rightShoulder == null || leftHip == null || rightHip == null) return false;

        float midShoulderX = (leftShoulder.getPosition().x + rightShoulder.getPosition().x) / 2f;
        float midShoulderY = (leftShoulder.getPosition().y + rightShoulder.getPosition().y) / 2f;
        float midHipX = (leftHip.getPosition().x + rightHip.getPosition().x) / 2f;
        float midHipY = (leftHip.getPosition().y + rightHip.getPosition().y) / 2f;

        float dx = midHipX - midShoulderX;
        float dy = midHipY - midShoulderY;

        double angle = Math.abs(Math.toDegrees(Math.atan2(dx, dy)));

        return angle > 15;
    }
}
