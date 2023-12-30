package com.cgvsu.render_scene;

import javax.vecmath.*;

public class Camera { // исходный класс от Косенко, тут какие-то математические преобразования :)
    // TODO не трогать!!! польностью готов и работает!!!

    private Vector3f position;
    private Vector3f target;
    private final float fov;
    private float aspectRatio;
    private final float nearPlane;
    private final float farPlane;

    public Camera() {
        this.position = new Vector3f(0, 0, 0);
        this.target = new Vector3f(0, 0, 0);
        this.fov = 0F;
        this.aspectRatio = 0F;
        this.nearPlane = 0F;
        this.farPlane = 0F;
    }

    public Camera(final Vector3f position, final Vector3f target, final float fov, final float aspectRatio, final float nearPlane, final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public boolean equalsEmptyCamera() {
        return (this.position.equals(new Vector3f(0, 0, 0))) && (this.target.equals(new Vector3f(0, 0, 0))) &&
                (this.fov == 0F) && (this.aspectRatio == 0F) && (this.nearPlane == 0F) && (this.farPlane == 0F);
    }

    public void setPosition(final Vector3f position) {
        this.position = position;
    }

    public void setTarget(final Vector3f target) {
        this.target = target;
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getTarget() {
        return target;
    }

    public void movePosition(final Vector3f translation) {
        this.position.add(translation);
    }

    public void moveTarget(final Vector3f translation) {
        this.target.add(translation);
    }

    Matrix4f getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    Matrix4f getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }
}