package com.egorovsoft.myrain;

public interface Observer {
    public void updateError(String err);
    public void updateCity(String name);
    public void updateTemp(String name);
    public void updatePressure(String name);
    public void updateWind(String name);
    public void setVisibleWindSpeed(boolean isVisible);
    public void setVisiblePressure(boolean isVisible);
}
