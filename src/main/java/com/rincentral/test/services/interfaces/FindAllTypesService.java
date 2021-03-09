package com.rincentral.test.services.interfaces;

import java.util.List;

public interface FindAllTypesService<T> {
     List<T> getFuelTypes();
     List<T> getBodyStyles();
     List<T> getEngineTypes();
     List<T> getWheelDrives();
     List<T> getGearboxTypes();
}
