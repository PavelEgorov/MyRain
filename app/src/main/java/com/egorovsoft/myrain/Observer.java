package com.egorovsoft.myrain;

public interface Observer {
    public void updateCity(String name);
    public void setVisibleWindSpeed(boolean isVisible);
    public void setVisiblePressure(boolean isVisible);
}
