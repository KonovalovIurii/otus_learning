package homework;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionValidator {

    public static void Validate(String сlazzForTest){
        try {
            Class clazz = Class.forName("homework." + сlazzForTest);

            //Найдем все методы отмеченные аннотациями Before, After и Test
            Method[] methodsBefore = Arrays.stream(clazz.getMethods())
                    .filter(method -> method.isAnnotationPresent(Before.class))
                    .toArray(Method[]::new);
            Method[] methodsAfter = Arrays.stream(clazz.getMethods())
                    .filter(method -> method.isAnnotationPresent(After.class))
                    .toArray(Method[]::new);
            Method[] methodsTest = Arrays.stream(clazz.getMethods())
                    .filter(method -> method.isAnnotationPresent(Test.class))
                    .toArray(Method[]::new);
            int all = 0;
            int fail = 0;
            int pass = 0;
            for (Method method : methodsTest) {
                all++;
                try {
                    Object testClazz = clazz.newInstance();
                    System.out.println("Экземпляр тестового класса: " + Integer.toHexString(testClazz.hashCode()));
                    try {
                        // Для каждого метода помеченного аннотацией Test, выполним методы c аннотацией Before
                        for (Method methodBefore : methodsBefore) {
                            exMethod(testClazz, methodBefore);
                        }
                        // Если не упали до этого момента значит все Before выполнились успешно
                        // Запустим Test
                        exMethod(testClazz, method);
                        // Запустим After
                        for (Method methodAfter : methodsAfter) {
                            exMethod(testClazz, methodAfter);
                        }
                        pass++;


                    } catch (Exception e) {
                        for (Method methodAfter : methodsAfter) {
                            exMethod(testClazz, methodAfter);
                        }
                        fail++;
                    }
                } catch (Exception e) {
                  e.printStackTrace();
                }

                System.out.println("Запущенно всего: "+ all+ " Выполнилось с ошибкой: "+fail+ " Выполнилось успешно: "+ pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exMethod(Object obj, Method method) {
        try {
            method.setAccessible(true);
            Parameter[] params = method.getParameters();
            List<Object> paramList = fillParams(params);
            method.invoke(obj, paramList.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List fillParams(Parameter[] params) {
        List<Object> paramList = new ArrayList<>();
        for (Parameter param : params) {
            int i = 0;
            if (param.getParameterizedType().toString().equals(String.class.toString())) {
                paramList.add("TestStr" + i);
            }
            if (param.getParameterizedType().toString().equals(int.class.toString())) {
                paramList.add(i);
            }
            i++;
        }
        return paramList;
    }
}

