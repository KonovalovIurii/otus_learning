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
        private final List<String> ListMethodParams = new ArrayList();

        DemoInvocationHandler(TestLoggingInterface myTestClass) {
            this.myTestClass = myTestClass;
            for (Method m : TestLogging.class.getMethods()) {
                if (m.isAnnotationPresent(Log.class)) {
                    ListMethodParams.add(getParamList(m));
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String argList = "";
            String listParamInvoke = getParamList(method);
            if (ListMethodParams.stream().anyMatch(listParamInvoke::equals)) {
                for (Object arg : args) {
                    if (arg.getClass() == Object[].class) {
                        for (Object intArg : (Object[]) arg) {
                            argList = argList + " " + intArg;
                        }
                    } else {
                        argList = argList + " " + arg;
                    }
                }
                System.out.println("executed method:" + method.getName() + ", param: " + argList);
            }
            return method.invoke(myTestClass, args);
        }

        public String getParamList(Method method) {
            String str = new String();
            str = method.getName();
            for (Parameter p : method.getParameters()) {
                str = str + "_"+ p.getType().toString();
            }
            return str;
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myTestClass +
                    '}';
        }
    }
}
