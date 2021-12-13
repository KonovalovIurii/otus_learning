package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        try {
            Object annotatedClass = configClass.getConstructor().newInstance();
            // получим методы, отсортированные по order
            Method[] methods = Arrays.stream(configClass.getMethods())
                    .filter(method -> method.isAnnotationPresent(AppComponent.class))
                    .sorted(Comparator.comparingInt(o ->o.getAnnotation(AppComponent.class).order()))
                    .toArray(Method[]::new);
            for ( Method method : methods)
            {

                AppComponent appComponent = method.getAnnotation(AppComponent.class);
                if (appComponent == null) {
                    throw new RuntimeException("Метод " + method.getName() + "не имеет аннотации");
                }
                // проверим есть ли перегрузка
                if (appComponentsByName.get(appComponent.name()) != null){
                //if (getAppComponent(appComponent.name()) != null) {
                    throw new RuntimeException("Метод " + appComponent.name() + "имеет перегрузку");
                }
                // экземпляр класса
                Object exMethod = makeObj(annotatedClass,method);

                // добавляем метод в список
                appComponentsByName.put(appComponent.name(), exMethod);
                appComponents.add(exMethod);
            }
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    // создадим объект
    public Object makeObj(Object object, Method method) {
        try {
            method.setAccessible(true);
            Class<?>[] params = method.getParameterTypes();
            Object[] paramList = new Object[params.length];
            for (int i = 0; i < params.length; i++){
                paramList[i] = getAppComponent(params[i]);
            }
            return method.invoke(object,paramList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
      try {
          return (C) appComponents.stream().filter(p ->
                  componentClass.isAssignableFrom(p.getClass())
          ).findFirst().get();
      }catch (NullPointerException ex){
          return null;
      }
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
