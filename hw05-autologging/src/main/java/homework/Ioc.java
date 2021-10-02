package homework;



import java.lang.reflect.*;
import java.util.*;



class Ioc {

    private Ioc() {
    }

    static TestLoggingInterface createmyTestClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface myTestClass;

        private final List<Method> MethodList = new ArrayList();

        DemoInvocationHandler(TestLoggingInterface myTestClass) {
            this.myTestClass = myTestClass;
            for (Method m : TestLogging.class.getMethods()) {
                if (m.isAnnotationPresent(Log.class)) {
                    MethodList.add(m);
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String argList = "";
            List<String> listParamInvoke = new ArrayList();
            List<String> listParam = new ArrayList();
            //Узнаем параметры метода
            for (Parameter p : method.getParameters()) {
                listParamInvoke.add(p.getType().toString());
            }
            for (Method m : MethodList) {
                if (m.getName().equals(method.getName())) {
                    //Совпали по имени, далее проверим совпадения по параметрам методов
                    for (Parameter p : m.getParameters()) {
                        listParam.add(p.getType().toString());
                    }
                    if (listParamInvoke.equals(listParam)) {
                        for (Object arg : args) {
                            if (arg.getClass() == Object[].class) {
                                for (Object intArg : (Object[]) arg) {
                                    argList = argList + " " + intArg;
                                }
                            } else {
                                argList = argList + " " + arg;
                            }
                        }
                    }
                    listParam.clear();
                }

            }
            System.out.println("executed method:" + method.getName() + ", param: " + argList);
            return method.invoke(myTestClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myTestClass +
                    '}';
        }
    }
}
